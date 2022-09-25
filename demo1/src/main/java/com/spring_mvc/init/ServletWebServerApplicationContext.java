package com.spring_mvc.init;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.web.context.support.GenericWebApplicationContext;

/**
 * @author pengYongQiang
 * @date 2021/2/7 14:54
 */
public class ServletWebServerApplicationContext extends GenericWebApplicationContext{

	@Override
	protected void onRefresh() {
		super.onRefresh();
		createWebServer();
	}

	private void createWebServer() {


	}
}
