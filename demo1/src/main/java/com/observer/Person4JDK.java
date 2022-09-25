package com.observer;

import java.util.Observable;
import java.util.Observer;

public class Person4JDK implements Observer {
	@Override
	public void update(Observable o, Object arg) {
		System.out.println("person---------");
	}
}
