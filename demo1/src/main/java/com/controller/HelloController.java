package com.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Component
public class HelloController {


	@RequestMapping("/t")
	@ResponseBody
	public String t() {
		return "hello!!";
	}


}
