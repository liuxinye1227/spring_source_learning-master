package com.spring_event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ListenerDemo implements ApplicationListener<EventDemo> {

	@Override
	public void onApplicationEvent(EventDemo event) {
		Map map = (Map)event.getSource();

		System.out.println("监听到事件"+map.get("name"));
	}
}
