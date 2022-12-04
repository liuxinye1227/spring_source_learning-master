package com.spring_ioc_test.dto;

import org.springframework.beans.factory.FactoryBean;

/**
 * @auther LXY
 * @create 2022/11/27 17:18
 * @Description TODO
 */
public class DogFactoryBean implements FactoryBean {
	//getObject是默认调用
	@Override
	public Dog getObject() throws Exception {
		return new Dog();
	}

	@Override
	public Class<?> getObjectType() {
		return Dog.class;
	}
}
