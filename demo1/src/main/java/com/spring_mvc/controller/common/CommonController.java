package com.spring_mvc.controller.common;

import com.spring_mvc.service.IService;
import com.znv.controller.annotation.InjectCommonService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author pengYongQiang
 * @date 2021/2/7 13:39
 */
public class CommonController {

	@InjectCommonService
	private IService<Object> baseService;

	@RequestMapping("commonList")
	@ResponseBody
	public List<Object> commonList() {
		return baseService.list();
	}
}
