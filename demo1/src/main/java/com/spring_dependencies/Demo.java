package com.spring_dependencies;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author pengYongQiang
 * @date 2021/12/23 10:04
 */
public class Demo {
	static {
		System.out.println(123);
	}

	public static void main(String[] args) throws ClassNotFoundException {


	//	Class<?> aClass = ClassLoader.getSystemClassLoader().loadClass("com.spring_dependencies.Demo");

		Class<?> aClass1 = Class.forName("com.spring_dependencies.Demo");


//		AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Config.class);
//
//		DemoServer1 bean = app.getBean(DemoServer1.class);
//		bean.test1();
	}

	@Configuration
	@EnableAsync
	@EnableAspectJAutoProxy
	@ComponentScan("com.spring_dependencies")
	public static class Config{

	}
}
