package com.spring_mvc.controller;

import com.znv.controller.annotation.CommonController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CommonController(genericType = User.class)
public class IndexController {

	@RequestMapping("demo")
	public String demo(User user){
		return user.toString();
	}

	@RequestMapping("demo/{id}")
	public String demo(@PathVariable("id") String id){
		return "hello   "+id;
	}

}
