package com.spring_dependencies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @author pengYongQiang
 * @date 2021/12/23 10:05
 */
@Component
public class DemoServer1 {
	@Autowired
	private DemoServer2 demoServer2;

	@Async
	public void test1() {
		System.out.println("test1");
	}
}
