package com.spring_source;


import com.entity.Dog;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

@ComponentScan("com.spring_source")
@EnableAsync
public class AppConfig implements IConfig<Dog> {

	@Override
	@Async
	public void aa(Dog dog){
		System.out.println(123);
	}
}
