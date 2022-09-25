package com.znv.controller.spring;

import com.znv.controller.annotation.EnableCommonController;
import com.znv.controller.annotation.InjectCommonService;
import com.znv.controller.exception.CommonControllerException;
import com.znv.controller.spring.converter.AbstractControllerToServiceParamProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pengYongQiang
 * @date 2020/11/9 11:05
 * @see InjectCommonService
 */
public class InjectCommonServiceAnnotationBeanPostProcessor implements InstantiationAwareBeanPostProcessor, BeanFactoryAware {
    private final Set<Class<? extends Annotation>> injectCommonServiceAnnotationTypes = new LinkedHashSet<>();

    private final Map<String, InjectionMetadata> injectionMetadataCache = new ConcurrentHashMap<>();

    private ConfigurableListableBeanFactory beanFactory;

    private final static String REQUIRED_PARAMETER_NAME = "required";

    private final static String MAP_CONVERT_PROXY_PARAMETER_NAME = "mapConvertProxy";

    private final static String CUSTOM_CONVERTER_PARAMETER_NAME = "customConverter";

    private final static String VALIDATE_PROXY_PARAMETER_NAME = "validateProxy";
    private final static String VALIDATE_GROUP_PARAMETER_NAME = "validateGroup";

    private CommonControllerManager commonControllerManager;

    public InjectCommonServiceAnnotationBeanPostProcessor(CommonControllerManager commonControllerManager) {
        this.commonControllerManager = commonControllerManager;
        this.injectCommonServiceAnnotationTypes.add(InjectCommonService.class);
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (!commonControllerManager.containsBeanName(beanName)) {
            return pvs;
        }
        InjectionMetadata metadata = findInjectCommonServiceMetadata(beanName, bean.getClass(), pvs);

        try {
            metadata.inject(bean, beanName, pvs);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return pvs;
    }

    //查找和创建 注入元数据对象
    private InjectionMetadata findInjectCommonServiceMetadata(String beanName, Class<?> clazz, PropertyValues pvs) {

        String cacheKey = (StringUtils.hasLength(beanName) ? beanName : clazz.getName());

        InjectionMetadata metadata = this.injectionMetadataCache.get(cacheKey);
        if (InjectionMetadata.needsRefresh(metadata, clazz)) {
            synchronized (this.injectionMetadataCache) {
                metadata = this.injectionMetadataCache.get(cacheKey);
                if (InjectionMetadata.needsRefresh(metadata, clazz)) {
                    if (metadata != null) {
                        metadata.clear(pvs);
                    }

                    metadata = buildInjectCommonServiceMetadata(clazz);
                    this.injectionMetadataCache.put(cacheKey, metadata);
                }
            }
        }
        return metadata;
    }

    //创建 注入元数据对象
    private InjectionMetadata buildInjectCommonServiceMetadata(final Class<?> clazz) {
        List<InjectionMetadata.InjectedElement> elements = new ArrayList<>();
        Class<?> targetClass = clazz;

        do {
            final List<InjectionMetadata.InjectedElement> currElements = new ArrayList<>();

            ReflectionUtils.doWithLocalFields(targetClass, field -> {
                AnnotationAttributes ann = findInjectServiceAnnotation(field);
                if (ann != null) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        return;
                    }

                    InjectServiceFieldElement injectServiceFieldElement = new InjectServiceFieldElement(field, ann);

                    currElements.add(injectServiceFieldElement);
                }
            });

            elements.addAll(0, currElements);
            targetClass = targetClass.getSuperclass();
            //满足条件向上扫描
        } while (targetClass != null && targetClass != Object.class);

        return new InjectionMetadata(clazz, elements);
    }

    private Class<? extends AbstractControllerToServiceParamProxy> determineConverter(AnnotationAttributes ann) {
        return ann.getClass(CUSTOM_CONVERTER_PARAMETER_NAME);
    }

    private AnnotationAttributes findInjectServiceAnnotation(AccessibleObject ao) {
        if (ao.getAnnotations().length > 0) {
            for (Class<? extends Annotation> type : this.injectCommonServiceAnnotationTypes) {
                AnnotationAttributes attributes = AnnotatedElementUtils.getMergedAnnotationAttributes(ao, type);
                if (attributes != null) {
                    return attributes;
                }
            }
        }
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (!(beanFactory instanceof ConfigurableListableBeanFactory)) {
            throw new IllegalArgumentException(
                    " bean后置处理器 " + InjectCommonServiceAnnotationBeanPostProcessor.class.getName() + " ，应该需要一个类型为 ConfigurableListableBeanFactory : " + beanFactory);
        }
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
    }

    /**
     * 自定义属性注入逻辑
     */
    private class InjectServiceFieldElement extends InjectionMetadata.InjectedElement {

        private final InjectCommonServiceAnnConfig config;

        //需要代理包装
        private final boolean needProxyWrapper;

        public InjectServiceFieldElement(Member member, AnnotationAttributes ann) {
            super(member, null);
            //确定 RequiredStatus
            boolean required = ann.getBoolean(REQUIRED_PARAMETER_NAME);
            boolean mapConvertProxy = ann.getBoolean(MAP_CONVERT_PROXY_PARAMETER_NAME);
            boolean validateProxy = ann.getBoolean(VALIDATE_PROXY_PARAMETER_NAME);
            Class<?>[] validateGroup = ann.getClassArray(VALIDATE_GROUP_PARAMETER_NAME);
            Class<? extends AbstractControllerToServiceParamProxy> converter = null;
            if (mapConvertProxy) {
                converter = determineConverter(ann);
            }
            needProxyWrapper = mapConvertProxy || validateProxy;

            config = new InjectCommonServiceAnnConfig(required, mapConvertProxy, converter, validateProxy, validateGroup);

        }

        @Override
        protected void inject(Object bean, String beanName, PropertyValues pvs) throws Throwable {
            Field field = (Field) this.member;
            String fieldName = field.getName();
            Object baseService = null;
            Class<?> baseServiceInterface = null;
            synchronized (this) {
                CommonControllerInfo commonControllerInfo = commonControllerManager.getCommonControllerInfo(beanName);

                if (commonControllerInfo == null) {
                    return;
                }
                List<BaseServiceField> baseServiceFields = commonControllerInfo.getBaseServiceFields();
                if (baseServiceFields == null || baseServiceFields.size() == 0) {
                    return;
                }

                for (BaseServiceField baseServiceField : baseServiceFields) {
                    if (baseServiceField.getFieldName().equals(field.getName())) {
                        baseService = beanFactory.getBean(baseServiceField.getBeanBeanDefinitionName());
                        baseServiceInterface = baseServiceField.getBaseServiceInterface();
                        break;
                    }
                }
                if (baseService == null && config.getRequired()) {
                    throw new CommonControllerException("无法在 " + bean.getClass() + " 中注入属性: " + fieldName + "(建议:请在 " + EnableCommonController.class.toString() + " 注解中配置通用service参数，" +
                            "或将 " + InjectCommonService.class.toString() + " 注解中的 required 参数，设置为 false)");
                } else if (baseService != null) {
                    ReflectionUtils.makeAccessible(field);
                    if (needProxyWrapper) {
                        Constructor<?> constructor = config.getCustomConverter().getConstructors()[0];
                        InvocationHandler o = (InvocationHandler) constructor.newInstance(baseService, commonControllerManager, config);
                        Object o1 = Proxy.newProxyInstance(baseService.getClass().getClassLoader(), new Class[]{baseServiceInterface}, o);
                        field.set(bean, o1);
                    } else {
                        field.set(bean, baseService);
                    }
                }
            }
        }
    }
}
