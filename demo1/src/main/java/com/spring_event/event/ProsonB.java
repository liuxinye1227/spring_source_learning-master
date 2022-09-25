package com.spring_event.event;

public class ProsonB implements MovieListener {

	@Override
	public void update(MovieEvent event) {
		if (event.getType()==1){
			System.out.println(ProsonB.class+"cry");
		}
	}
}
