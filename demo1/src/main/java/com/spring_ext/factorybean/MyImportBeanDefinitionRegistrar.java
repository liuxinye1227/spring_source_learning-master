package com.spring_ext.factorybean;

import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

		BeanDefinitionBuilder definitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(PayService.class);
		AbstractBeanDefinition beanDefinition = definitionBuilder.getBeanDefinition();
		beanDefinition.setBeanClass(PayServiceImpl.class);
		//beanDefinition.setBeanClass(MyFactoryBean.class);
	//	beanDefinition.setFactoryBeanName("myFactoryBean");

		MethodOverrides methodOverrides = new MethodOverrides();
		methodOverrides.addOverride(new ReplaceOverride("pay","payServiceImpl2"){
			@Override
			public boolean matches(Method method) {
				return method.getName().equals("pay");
			}
		});

		beanDefinition.setMethodOverrides(methodOverrides);

		registry.registerBeanDefinition("payService",beanDefinition);

	}

}
