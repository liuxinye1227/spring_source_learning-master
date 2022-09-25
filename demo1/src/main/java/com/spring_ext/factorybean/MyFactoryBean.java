package com.spring_ext.factorybean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

//@Component
public class MyFactoryBean implements FactoryBean {

	@Override
	public Object getObject() throws Exception {
		Class[] classes = {PayService.class};
		return  Proxy.newProxyInstance(Demo.class.getClassLoader(), classes, new ProxyHandler());
	}

	@Override
	public Class<?> getObjectType() {
		return PayService.class;
	}
}
