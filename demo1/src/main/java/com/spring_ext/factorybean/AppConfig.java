package com.spring_ext.factorybean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.spring_ext.factorybean")
@Import(MyImportBeanDefinitionRegistrar.class)
public class AppConfig  {

//	@Bean
//	public PayService payService(){
//		PayServiceImpl payService = new PayServiceImpl();
//		System.out.println(payService);
//		return payService;
//	}
//	@Bean
//	public UserSerivce userSerivce(){
//		PayService payService = payService();
//		System.out.println(payService);
//		return new UserSerivceImpl();
//	}

}
