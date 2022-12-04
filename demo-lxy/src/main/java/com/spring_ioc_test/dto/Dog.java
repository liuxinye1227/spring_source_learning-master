package com.spring_ioc_test.dto;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @auther LXY
 * @create 2022/10/15 21:05
 * @Description TODO
 */
public class Dog implements ApplicationContextAware, EnvironmentAware, BeanNameAware {

	private String name;
	private String age;
	private String color;
	private ApplicationContext applicationContext;
	private String beanName;

	public void init(){
		System.out.println("init");
		System.out.println(this.name);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}


	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext =applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setEnvironment(Environment environment) {

	}

	public String getBeanName() {
		return beanName;
	}

	@Override
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public void show(){
		System.out.println(this.beanName);
	//	this.applicationContext.getBean(name);

	}

}
