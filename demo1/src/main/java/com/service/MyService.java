package com.service;

import com.annotation.MyAnnotation;
import org.springframework.stereotype.Service;

@Service
public class MyService implements IMyService {
	public void a(Integer i) throws RuntimeException{
		System.out.println("method:a "+i);
	}

	public void b() throws RuntimeException{
		System.out.println("method:b");
	}
}
