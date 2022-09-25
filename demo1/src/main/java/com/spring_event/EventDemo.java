package com.spring_event;

import org.springframework.context.ApplicationEvent;

public class EventDemo<T> extends ApplicationEvent  {
	public T t;

	public T getT() {
		return t;
	}

	/**
	 * Create a new ApplicationEvent.
	 *
	 * @param source the object on which the event initially occurred (never {@code null})
	 */
	public EventDemo(Object source,T t) {
		super(source);
		this.t=t;
	}

}
