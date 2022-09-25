package com.spring_source;

import com.entity.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Proxy;

//@Component 循环依赖
public class SpringConstructorDemo<T> {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(AppConfig.class);
		System.out.println(9999);
	}
	@Autowired
	private IConfig<Dog> iConfig;

	@PostConstruct
	public void init(){
		boolean proxyClass = Proxy.isProxyClass(iConfig.getClass());
		String descriptiveType = ClassUtils.getDescriptiveType(iConfig);
		System.out.println(123);
	}
}
