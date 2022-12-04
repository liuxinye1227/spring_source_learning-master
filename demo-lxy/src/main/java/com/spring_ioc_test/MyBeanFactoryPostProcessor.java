package com.spring_ioc_test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @auther LXY
 * @create 2022/10/3 15:20
 * @Description TODO
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		BeanDefinition dog = beanFactory.getBeanDefinition("dog");

		//dog.setLazyInit(true);

		System.out.println("It`s MyBeanFactoryPostProcessor generate dog!");

	}
}
