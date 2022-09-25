package com.spring_ext.factorybean;

import org.springframework.stereotype.Component;

@Component
public class UserSerivceImpl implements UserSerivce {
	@Override
	public String query() {
		System.out.println("aaaa");
		return "aaaaa";
	}
}
