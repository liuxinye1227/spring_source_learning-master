package com.spring_source.dto;

import jdk.nashorn.internal.ir.annotations.Ignore;

import javax.annotation.sql.DataSourceDefinition;

public class Dog {
	String  name ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
