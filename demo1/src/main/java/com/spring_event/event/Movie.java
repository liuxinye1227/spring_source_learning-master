package com.spring_event.event;

import java.util.ArrayList;
import java.util.List;

public class Movie implements Runnable{


	private List<MovieListener> listeners =  new ArrayList<>();

	public void AddListener(MovieListener listener){
		this.listeners.add(listener);
	}

	@Override
	public void run() {
		System.out.println("开始看视频");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		play();
	}

	public void play(){
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		MovieEvent movieEvent = new MovieEvent();
		movieEvent.setContext("only");
		movieEvent.setType(1);
		for (MovieListener listener : listeners) {
			listener.update(movieEvent);
		}

	}

}
