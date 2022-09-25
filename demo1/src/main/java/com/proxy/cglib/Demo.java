package com.proxy.cglib;

import org.springframework.cglib.core.SpringNamingPolicy;
import org.springframework.cglib.proxy.*;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * java -classpath "D: \Program Files\Java\jdk1. 8.0_ 181\1ib\sa- jdi. jar" sun.jvm.hotspot.HSDB
 */
public class Demo {

	public static void main(String[] args) throws IllegalAccessException, InstantiationException {
		Enhancer enhancer = new Enhancer();

		enhancer.setSuperclass(IndexDao.class);

		enhancer.setInterfaces(new Class[]{Dao.class});

		enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);

//		enhancer.setCallback(new MethodInterceptor() {
//			@Override
//			public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
//				System.out.println("intercept");
//				Object o1 = methodProxy.invokeSuper(o, objects);
//
//				return o1;
//			}
//		});


		//过滤执行第几个拦截器
		enhancer.setCallbackFilter(new CallbackFilter() {
			@Override
			public int accept(Method method) {
				String name = method.getName();
				return 1;
			}
		});

		enhancer.setCallbackTypes(new Class[]{MyMethodInterceptor.class,MyMethodInterceptor1.class});

		Class aClass = enhancer.createClass();

		Enhancer.registerCallbacks(aClass,new Callback[]{new MyMethodInterceptor(),new MyMethodInterceptor1()});

		IndexDao o = (IndexDao)  aClass.newInstance();
		//IndexDao o = (IndexDao) enhancer.create();

		o.query();
		o.query1();
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static class MyMethodInterceptor implements MethodInterceptor {

		@Override
		public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
			System.out.println("intercept");
			Object o1 = methodProxy.invokeSuper(o, objects);
			return o1;
		}
	}
	static class MyMethodInterceptor1 implements MethodInterceptor {

		@Override
		public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
			System.out.println("intercept1");
			Object o1 = methodProxy.invokeSuper(o, objects);
			return o1;
		}
	}
}
