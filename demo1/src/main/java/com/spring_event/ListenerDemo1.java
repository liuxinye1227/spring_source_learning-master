package com.spring_event;

import org.springframework.context.ApplicationListener;

//@Component
public class ListenerDemo1 implements ApplicationListener<EventDemo> {
	@Override
	public void onApplicationEvent(EventDemo event) {
		System.out.println("22222222222222");
	}
}
