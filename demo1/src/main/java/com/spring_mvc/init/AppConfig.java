package com.spring_mvc.init;


import com.spring_mvc.controller.common.CommonController;
import com.spring_mvc.service.IService;
import com.znv.controller.annotation.EnableCommonController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
@ComponentScan("com.spring_mvc")
@EnableCommonController(defaultCommonController = CommonController.class,injectCommonControllerService = IService.class)
public class AppConfig {

	@Bean
	public RequestMappingHandlerMapping requestMappingHandlerMapping(){
		RequestMappingHandlerMapping requestMappingHandlerMapping = new RequestMappingHandlerMapping();
		return requestMappingHandlerMapping;
	}

}
