package com.znv.controller.spring;

import com.znv.controller.annotation.CommonController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author pengYongQiang
 * @date 2020/10/28 22:52
 * 扩展 {@link RequestMappingHandlerMapping}
 * 因为没有在这个 请求映射器 中注册任何 请求处理器，
 * 所以不用作springMVC的 请求映射器 使用，
 * 而是向 {@link RequestMappingHandlerMapping} 中扩展请求处理器，
 * 这样做的好处是包含了springMVC自带的所有特性，包括拦截器，增强器，参数解析器等。
 * <p>
 * <p>
 * 继承{@link RequestMappingHandlerMapping}的原因：
 * 1.复用 RequestMappingHandlerMapping 的方法
 * 2.可以执行到 请求映射器 的标准初始化流程，以达到扩展的效果
 */

@Slf4j
public class ExtensionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
    private final RequestMappingHandlerMapping requestMappingHandlerMapping;

    private CommonControllerBeanDefinitionRegistrar controllerBeanDefinitionRegistrar;
    private CommonControllerManager commonControllerManager;

    //扩展
    public ExtensionRequestMappingHandlerMapping(RequestMappingHandlerMapping requestMappingHandlerMapping,
                                                 CommonControllerBeanDefinitionRegistrar controllerBeanDefinitionRegistrar,
                                                 CommonControllerManager commonControllerManager) {
        this.requestMappingHandlerMapping = requestMappingHandlerMapping;
        this.controllerBeanDefinitionRegistrar = controllerBeanDefinitionRegistrar;
        this.commonControllerManager = commonControllerManager;
    }

    @Override
    protected void detectHandlerMethods(Object handler) {
        super.detectHandlerMethods(handler);
        Class<?> handlerType = (handler instanceof String ?
                obtainApplicationContext().getType((String) handler) : handler.getClass());

        if (handlerType != null) {
            Class<?> userType = ClassUtils.getUserClass(handlerType);
            Map<Method, RequestMappingInfo> methods = MethodIntrospector.selectMethods(userType,
                    (MethodIntrospector.MetadataLookup<RequestMappingInfo>) method -> {
                        try {
                            return getMappingForMethod(method, userType);
                        } catch (Throwable ex) {
                            throw new IllegalStateException("处理器类上的映射无效 [" +
                                    userType.getName() + "]: " + method, ex);
                        }
                    });
            if (methods.size() == 0) {
                registerHandlerMethod(handler, null, null);
            }
        }
    }

    @Override
    protected void registerHandlerMethod(Object handler, Method method, RequestMappingInfo mapping) {

        AnnotationMetadata annotationMetadata = getAnnotationMetadata(handler);
        if (annotationMetadata==null){
            log.warn("没有找到【{}】的注解元数据"+handler.getClass().getName());
            return;
        }
        //是否有 EnableAutoController 注解 ， 没有的话不处理
        if (!annotationMetadata.hasAnnotation(CommonController.class.getName())) {
            return;
        }
        Class<?> defaultCommonController = controllerBeanDefinitionRegistrar.getDefaultCommonControllerClass();
        defaultCommonController = ClassUtils.getUserClass(defaultCommonController);
        ApplicationContext applicationContext = requestMappingHandlerMapping.getApplicationContext();

        //注册通用处理器
        Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(CommonController.class.getName());
        Class<?> genericType = (Class<?>) annotationAttributes.get("genericType");
        Class<?>[] commonControllers = (Class<?>[]) annotationAttributes.get("commonControllers");
        if (commonControllers.length != 0) {
            for (Class<?> commonController : commonControllers) {
                Map<Method, RequestMappingInfo> methods = getMethodRequestMappingInfoMap(handler, commonController, applicationContext);
                registerCommonHandlerMethod(commonController, methods, genericType);
            }
        } else {
            Map<Method, RequestMappingInfo> methods = getMethodRequestMappingInfoMap(handler, defaultCommonController, applicationContext);
            registerCommonHandlerMethod(defaultCommonController, methods, genericType);
        }

    }

    private Map<Method, RequestMappingInfo> getMethodRequestMappingInfoMap(Object handler, Class<?> commonController, ApplicationContext applicationContext) {
        Class<?> handlerType = (handler instanceof String ?
                applicationContext.getType((String) handler) : handler.getClass());

        //CommonController处理器 与当前处理器 合并起来的
        Map<Method, RequestMappingInfo> methods = MethodIntrospector.selectMethods(commonController,
                (MethodIntrospector.MetadataLookup<RequestMappingInfo>) method -> {
                    try {
                        return getMappingForMethod(method, handlerType);
                    } catch (Throwable ex) {
                        throw new IllegalStateException("处理器类上的映射无效 [" +
                                commonController.getName() + "]: " + method, ex);
                    }
                });
        //移除冲突的url
        Iterator<Map.Entry<Method, RequestMappingInfo>> iterator = methods.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Method, RequestMappingInfo> next = iterator.next();
            RequestMappingInfo requestMappingInfo = next.getValue();
            String mappingPattern = getMappingPatternInRegistered(requestMappingInfo, getRequestMappingHandlerMappingPatterns());
            if (mappingPattern != null) {
                iterator.remove();
            }

        }
        return methods;
    }

    /**
     * @param handler
     * @return true 需要跳过，false反之
     */
    private AnnotationMetadata getAnnotationMetadata(Object handler) {
        GenericWebApplicationContext applicationContext = (GenericWebApplicationContext) requestMappingHandlerMapping.getApplicationContext();
        Class<?> handlerType = (handler instanceof String ?
                applicationContext.getType((String) handler) : handler.getClass());

        String[] beanNamesForType = applicationContext.getBeanNamesForType(handlerType);
        for (String beanName : beanNamesForType) {
            BeanDefinition beanDefinition = applicationContext.getBeanDefinition(beanName);
            if (!(beanDefinition instanceof AnnotatedBeanDefinition)) {
                continue;
            }
            AnnotatedBeanDefinition annotatedBeanDefinition = (AnnotatedBeanDefinition) beanDefinition;
            AnnotationMetadata metadata = annotatedBeanDefinition.getMetadata();
            return metadata;
        }
        return null;
    }

    private String getMappingPatternInRegistered(RequestMappingInfo mapping, Set<String> requestMappingHandlerMappingPattern) {
        PatternsRequestCondition patternsCondition = mapping.getPatternsCondition();
        Set<String> patterns = patternsCondition.getPatterns();
        Iterator<String> iterator = patterns.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            if (requestMappingHandlerMappingPattern.contains(next)) {
                return next;
            }
        }
        return null;
    }

    /**
     * 注册通用处理器
     */
    public Map<Method, RequestMappingInfo> registerCommonHandlerMethod(
            Class<?> commonController,
            Map<Method, RequestMappingInfo> methods,
            Class<?> genericType) {
        if (methods.size() == 0) {
            return null;
        }
        ApplicationContext applicationContext = requestMappingHandlerMapping.getApplicationContext();
        String beanDefinitionName = commonControllerManager.getCommonControllerBeanNameGenerator().getBeanDefinitionName(genericType, commonController.getName());

        Object commonController1 = applicationContext.getBean(beanDefinitionName);
        methods.forEach((commonMethod, requestMappingInfo) -> {
            Method invocableCommonMethod = null;
            try {
                invocableCommonMethod = AopUtils.selectInvocableMethod(commonMethod, commonController);
            } catch (Exception e) {
                e.printStackTrace();
                throw e;
            }
            requestMappingHandlerMapping.registerMapping(requestMappingInfo, commonController1, invocableCommonMethod);
        });

        return methods;
    }


    public Set<String> getRequestMappingHandlerMappingPatterns() {
        Set<String> requestMappingHandlerMappingPattern = new HashSet<>();
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        Set<RequestMappingInfo> requestMappingInfos = handlerMethods.keySet();
        for (RequestMappingInfo requestMappingInfo : requestMappingInfos) {
            PatternsRequestCondition patternsCondition = requestMappingInfo.getPatternsCondition();
            Set<String> patterns = patternsCondition.getPatterns();
            requestMappingHandlerMappingPattern.addAll(patterns);
        }
        return requestMappingHandlerMappingPattern;
    }
}
