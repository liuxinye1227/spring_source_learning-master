package com.spring_event.event;

import org.springframework.web.bind.annotation.ResponseBody;

public class Test {

	public static void main(String[] args) throws InterruptedException {


		ProsonA prosonA = new ProsonA();

		Movie movie = new Movie();
		movie.AddListener(prosonA);
		movie.AddListener(new ProsonB());

		Thread thread = new Thread(movie);
		thread.start();


	}
}
