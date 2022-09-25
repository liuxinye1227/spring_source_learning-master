package com.spring_dependencies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author pengYongQiang
 * @date 2021/12/23 10:05
 */
@Component
public class DemoServer2 {
	@Autowired
	private DemoServer1 demoServer1;
	//@Async
	public void a(){

	}
}
