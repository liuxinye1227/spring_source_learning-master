package com.spring_event;

import org.springframework.context.ApplicationEvent;

public class EventDemo1 extends ApplicationEvent {

	private String a;
	public EventDemo1(Object source, String a) {
		super(source);
		this.a = a;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}
}
