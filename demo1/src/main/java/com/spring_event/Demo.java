package com.spring_event;

import com.spring_event.entity.Cat;
import com.spring_event.entity.Dog;
import com.spring_event.event.earlyEvent;
import org.springframework.beans.BeansException;
import org.springframework.context.*;
import org.springframework.context.annotation.*;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class Demo {
	@Configuration
	@ComponentScan("com.spring_event")
	@Import({BeanFactory1.class,earlyEventApplicationListener.class})
	public static class Config {

	}

	public static class BeanFactory1 implements ApplicationContextAware {
		@Override
		public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
			applicationContext.publishEvent(new earlyEvent());

		}
	}

	public static class earlyEventApplicationListener implements ApplicationListener<earlyEvent> {
		@Override
		public void onApplicationEvent(earlyEvent event) {
			event.exec();
		}
	}

	public static void main(String[] args) {
		ApplicationContext app = new AnnotationConfigApplicationContext(Config.class);

		HashMap<Object, Object> map = new HashMap<>();
		map.put("name", "小明");

		Cat cat = new Cat("tom", "公");
		EventDemo<Cat> catEventDemo = new EventDemo<>(map, cat);
		app.publishEvent(catEventDemo);

		Dog dog = new Dog("啊黄", "母");
		EventDemo<Dog> dogEventDemo = new EventDemo<>(map, dog);
		app.publishEvent(dogEventDemo);

		System.out.println("发布事件完成");


	}


	@EventListener()
	public void processEventDemo(EventDemo<Cat> event) {
		System.out.println(event.getT());

	}

	@EventListener(ContextRefreshedEvent.class)
	public void processContextStartedEvent(ContextRefreshedEvent contextRefreshedEvent) {
		System.out.println(123);
	}


}
