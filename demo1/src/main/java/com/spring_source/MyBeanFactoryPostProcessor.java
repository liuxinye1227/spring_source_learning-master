package com.spring_source;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;


public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		BeanDefinition dog = beanFactory.getBeanDefinition("dog");

		GenericBeanDefinition genericBeanDefinition = (GenericBeanDefinition) dog;

		genericBeanDefinition.getConstructorArgumentValues().addGenericArgumentValue(10);




	}
}
