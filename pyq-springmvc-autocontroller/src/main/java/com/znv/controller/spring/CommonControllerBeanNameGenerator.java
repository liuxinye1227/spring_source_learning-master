package com.znv.controller.spring;

/**
 * @author pengYongQiang
 * @date 2020/11/19 21:48
 * //通用 Controller 的beanName生成器
 */
public interface CommonControllerBeanNameGenerator {
    default String getBeanDefinitionName(Class<?> commonServiceGenericType, String beanClassName) {
        return commonServiceGenericType.getSimpleName() + "@" + beanClassName;
    }
}
