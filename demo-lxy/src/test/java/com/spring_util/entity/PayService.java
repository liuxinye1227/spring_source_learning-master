package com.spring_util.entity;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author pengYongQiang
 * @date 2021/3/29 13:49
 */
@Component
public class PayService {

	@Async
	public void pay(){
		System.out.println("支付业务...");
	}
	@RequestMapping
	public void pay(String a){
		System.out.println("支付业务...");
	}
}
