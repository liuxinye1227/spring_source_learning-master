package com.observer;

import java.util.Observable;

public class Movie4JDK extends Observable {

	public void end(){
		setChanged();
		notifyObservers();
		System.out.println("视频结束了");
	}
}
