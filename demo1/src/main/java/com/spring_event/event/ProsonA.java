package com.spring_event.event;

public class ProsonA implements MovieListener {

	@Override
	public void update(MovieEvent event) {
		if (event.getType()==1){
			System.out.println(ProsonA.class+"cry");
		}
	}
}
