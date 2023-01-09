package com.spring_source;

import com.spring_source.dto.Dog;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyBeanFactoryPostProcessTest {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("tx.xml");
		Dog bean = context.getBean(Dog.class);
		System.out.println(bean);


	}
}
