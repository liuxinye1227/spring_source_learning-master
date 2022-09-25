package com.znv.controller.spring;

import com.znv.controller.annotation.CommonController;
import com.znv.controller.annotation.EnableCommonController;
import com.znv.controller.exception.CommonControllerException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Controller;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @author pengYongQiang
 * @date 2020/10/31 15:33
 */
@Slf4j
@Getter
public class CommonControllerBeanDefinitionRegistrar implements BeanDefinitionRegistryPostProcessor {

    private Class<?> defaultCommonControllerClass;
    private Set<Class<?>> injectCommonControllerServices;
    private DefaultListableBeanFactory beanFactory;

    private final CommonControllerManager commonControllerManager;

    public CommonControllerBeanDefinitionRegistrar(CommonControllerManager commonControllerManager) {
        this.commonControllerManager = commonControllerManager;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        /*
         * 1.初始化
         */
        init(registry);

        /*
         * 2.找到加了 CommonController，Controller 这两个注解的beanName
         */
        Set<String> standardControllerBeanNames = findCommonControllerBeanNames();

        /*
         * 3.构建CommonControllerInfo对象
         */
        List<CommonControllerInfo> commonControllerInfos = buildCommonControllerInfo(standardControllerBeanNames);
        /*
         * 4.添加到 commonControllerInfo 到 commonControllerManager
         *   和 使用CommonControllerInfo对象的信息注册到IOC容器
         */
        for (CommonControllerInfo commonControllerInfo : commonControllerInfos) {
            //添加到 CommonControllerInfo 管理器
            commonControllerManager.addAutoControllerInfo(commonControllerInfo);
            //注册 CommonControllerInfo 到 IOC 容器
            registerCommonControllerToIOC(commonControllerInfo);
        }
    }

    private void init(BeanDefinitionRegistry registry) {
        if (!(registry instanceof DefaultListableBeanFactory)) {
            throw new CommonControllerException(registry.getClass() + " 必须是一个 " + DefaultListableBeanFactory.class.toString() + " 类型的");
        }
        beanFactory = (DefaultListableBeanFactory) registry;
        Map<String, Object> annotationConfigData = getAnnotationConfigData(beanFactory);

        this.defaultCommonControllerClass = (Class<?>) annotationConfigData.get("defaultCommonController");
        Class<?>[] injectCommonControllerServices = (Class<?>[]) annotationConfigData.get("injectCommonControllerService");
        List<Class<?>> classes = Arrays.asList(injectCommonControllerServices);
        this.injectCommonControllerServices = new HashSet<>(classes);

        commonControllerManager.setDefaultCommonController(defaultCommonControllerClass);
        commonControllerManager.setInjectCommonControllerServices(this.injectCommonControllerServices);

        //验证commonService的合法性
        commonControllerManager.validateCommonService();
    }

    private List<CommonControllerInfo> buildCommonControllerInfo(Set<String> standardControllerBeanNames) {
        List<CommonControllerInfo> commonControllerInfos = new ArrayList<>();
        for (String standardControllerBeanName : standardControllerBeanNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(standardControllerBeanName);
            if (!(beanDefinition instanceof AnnotatedBeanDefinition)) {
                continue;
            }
            AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanDefinition;
            AnnotationMetadata metadata = annotatedBeanDefinition.getMetadata();
            //已经配置注入的通用Service
            Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(CommonController.class.getName());
            //泛型
            Class<?> genericType = (Class<?>) annotationAttributes.get("genericType");
            //通用controller
            Class<?>[] commonControllers = (Class<?>[]) annotationAttributes.get("commonControllers");
            List<Class<?>> classes = Arrays.asList(commonControllers);
            Set<Class<?>> commonControllerSet = new LinkedHashSet<>(classes);

            if (commonControllerSet.size() != 0) {
                for (Class<?> commonController : commonControllerSet) {
                    CommonControllerInfo commonControllerInfo = doBuildCommonControllerInfo(genericType, commonController);
                    commonControllerInfos.add(commonControllerInfo);
                }
            } else {
                CommonControllerInfo commonControllerInfo = doBuildCommonControllerInfo(genericType, defaultCommonControllerClass);
                commonControllerInfos.add(commonControllerInfo);
            }

        }
        return commonControllerInfos;
    }

    // 取CommonController 与 Controller 的交集
    private Set<String> findCommonControllerBeanNames() {
        String[] commonControllerBeanNames = beanFactory.getBeanNamesForAnnotation(CommonController.class);
        String[] controllerBeanNames = beanFactory.getBeanNamesForAnnotation(Controller.class);
        List<String> commonControllerBeanNameList = Arrays.asList(commonControllerBeanNames);
        List<String> controllerBeanNamesBeanNameList = Arrays.asList(controllerBeanNames);
        Set<String> commonControllerBeanNameSet = new HashSet<>(commonControllerBeanNameList);
        Set<String> controllerBeanNameSet = new HashSet<>(controllerBeanNamesBeanNameList);
        controllerBeanNameSet.retainAll(commonControllerBeanNameSet);
        return controllerBeanNameSet;
    }


    private CommonControllerInfo doBuildCommonControllerInfo(Class<?> genericType, Class<?> commonController) {
        String registerBeanName = commonControllerManager.getCommonControllerBeanNameGenerator().getBeanDefinitionName(genericType, commonController.getName());
        List<BaseServiceField> baseServiceFields = getCommonServiceFieldsByCommonController(genericType,commonController);
        CommonControllerInfo commonControllerInfo = new CommonControllerInfo();
        commonControllerInfo.setMixedBeanDefinitionName(registerBeanName);
        commonControllerInfo.setGenericTypeClass(genericType);
        commonControllerInfo.setBaseServiceFields(baseServiceFields);
        commonControllerInfo.setCommonControllerClass(commonController);
        return commonControllerInfo;
    }

    private Map<String, Object> getAnnotationConfigData(DefaultListableBeanFactory beanFactory) {
        String[] BeanNamesForEnableAutoController = beanFactory.getBeanNamesForAnnotation(EnableCommonController.class);
        if (BeanNamesForEnableAutoController.length > 1) {
            throw new IllegalStateException("注解：" + EnableCommonController.class.getSimpleName() + " 出现多次。");
        }
        String beanName = BeanNamesForEnableAutoController[0];
        AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanFactory.getBeanDefinition(beanName);
        AnnotationMetadata metadata = annotatedBeanDefinition.getMetadata();
        return metadata.getAnnotationAttributes(EnableCommonController.class.getName());
    }

    public void registerCommonControllerToIOC(CommonControllerInfo commonControllerInfo) {
        String registerBeanName = commonControllerInfo.getMixedBeanDefinitionName();
        Class<?> commonControllerClass = commonControllerInfo.getCommonControllerClass();
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(commonControllerClass);
        beanDefinitionBuilder.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE);
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        beanFactory.registerBeanDefinition(registerBeanName, beanDefinition);
        log.info("=========>注册CommonController BeanDefinition: {}", registerBeanName);
    }

    /**
     * 获取IOC容器中，通用service的，beanClass 和 beanName的映射关系
     *
     * @param commonServiceGenericType
     * @return K: commonServiceBeanClass的类全名
     * V: commonServiceBeanClass在容器中的beanName
     */
    private List<BaseServiceField> getCommonServiceFieldsByCommonController(Class<?> commonServiceGenericType, Class<?> commonController) {
        List<BaseServiceField> results = new ArrayList<>();
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            String beanDefinitionClassName = beanDefinition.getBeanClassName();
            if (beanDefinitionClassName == null) {
                continue;
            }
            try {
                Class<?> baseServiceImpl = Class.forName(beanDefinitionClassName);
                for (Class<?> baseServiceInterface : injectCommonControllerServices) {
                    //与IOC容器中的某个baseService类型一样
                    if (baseServiceInterface.isAssignableFrom(baseServiceImpl)) {
                        //获取通用controller中，所有可以被注入的通用service的属性名称。
                        List<String> baseServiceFieldNames = getBaseServiceFieldName(commonServiceGenericType, baseServiceImpl,commonController);
                        Class<?> beanDefinitionClassGenericType = GenericTypeResolver.resolveTypeArgument(baseServiceImpl, baseServiceInterface);
                        if (commonServiceGenericType.isAssignableFrom(beanDefinitionClassGenericType)) {
                            for (String baseServiceFieldName : baseServiceFieldNames) {
                                BaseServiceField baseServiceField = new BaseServiceField();
                                baseServiceField.setFieldName(baseServiceFieldName);
                                baseServiceField.setBeanBeanDefinitionName(beanDefinitionName);
                                baseServiceField.setBaseServiceImpl(baseServiceImpl);
                                baseServiceField.setBaseServiceInterface(baseServiceInterface);
                                results.add(baseServiceField);
                            }
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return results;
    }


    private List<String> getBaseServiceFieldName( Class<?> commonServiceGenericType, Class<?> baseServiceImpl,Class<?> commonController) {
        List<String> baseServiceFieldNames = new ArrayList<>();
        //找到通用controller中，所有可以被自动注入通用service的属性
        ReflectionUtils.doWithLocalFields(commonController, field -> {
            if (Modifier.isStatic(field.getModifiers())) {
                return;
            }
            Class<?> type = field.getType();
            for (Class<?> commonService : injectCommonControllerServices) {
                if (type.isAssignableFrom(commonService)) {
                    Class<?> genericType = GenericTypeResolver.resolveTypeArgument(baseServiceImpl, commonService);
                    if (commonService.isAssignableFrom(baseServiceImpl) && commonServiceGenericType.isAssignableFrom(genericType)) {
                        baseServiceFieldNames.add(field.getName());
                    }
                }
            }
        });


        return baseServiceFieldNames;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}

