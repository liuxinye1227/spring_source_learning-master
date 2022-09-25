package com.spring_ext.factorybean;


import org.springframework.beans.factory.support.MethodReplacer;
import org.springframework.beans.factory.support.ReplaceOverride;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class PayServiceImpl2 implements MethodReplacer {

	@Override
	public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {

		return obj;
	}
}
