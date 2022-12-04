package com.spring_ioc_test;

import com.spring_ioc_test.dto.Dog;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @auther LXY
 * @create 2022/10/15 21:02
 * @Description TODO
 */
public class MyBeanTest {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("tx.xml");
		Dog dog = applicationContext.getBean(Dog.class);
		System.out.println(dog.getApplicationContext());
		System.out.println(dog);
		dog.show();
		Dog dogFactoryBean = (Dog) applicationContext.getBean("dogFactoryBean");
		System.out.println(dogFactoryBean);
	}
}
