package com.spring_source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationTargetException;


public class Demo {

	public static void main(String[] args) {

		//AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext(Config.class);
		AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext("com.spring_source");

	}

	@Component
	public static class A {
		@Autowired
		private B b;

		@PostConstruct
		public void init() {
			System.out.println("a");
		}

	}
	@Component
	public static class B {
		@Autowired
		private A a;

		@PostConstruct
		public void init() {
			System.out.println("b");
		}
	}
}
