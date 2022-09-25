package com.observer;

public class Test {
	public static void main(String[] args) {

		Movie4JDK movie4JDK = new Movie4JDK();

		Master4JDK master4JDK = new Master4JDK();
		Person4JDK person4JDK = new Person4JDK();

		movie4JDK.addObserver(master4JDK);
		movie4JDK.addObserver(person4JDK);


		movie4JDK.end();

	}
}
