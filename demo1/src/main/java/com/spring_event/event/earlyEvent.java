package com.spring_event.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author pengYongQiang
 * @date 2021/1/29 15:00
 */
public class earlyEvent extends ApplicationEvent {
	public earlyEvent() {
		super("123");
	}
	public void exec(){
		System.out.println("早期事件执行");
	}
}
