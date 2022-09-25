package com.spring_mvc.init;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;

public class SpringApplicationPYQ {


	public static void run() throws LifecycleException {

		//AnnotationConfigWebApplicationContext app = new AnnotationConfigWebApplicationContext();
		//AnnotationConfigWebApplicationContext app1 = new AnnotationConfigWebApplicationContext();

		DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
		defaultListableBeanFactory.registerSingleton("appConfig",AppConfig.class);
		ConfigurableWebApplicationContext app = new GenericWebApplicationContext(defaultListableBeanFactory);

		//app.register(AppConfig.class);
		app.refresh();

		File file = new File(System.getProperty("java.io.tmpdir"));

		Tomcat tomcat = new Tomcat();
		tomcat.setPort(9090);

		Context context = tomcat.addContext("/", file.getAbsolutePath());

		//tomcat启动的过程中会调用 DispatcherServlet 的 init 方法
		//用于初始化 controller 和请求映射
		DispatcherServlet dispatcherServlet = new DispatcherServlet(app);

		Tomcat.addServlet(context,"pyq",dispatcherServlet).setLoadOnStartup(1);
		context.addServletMapping("/","pyq");
		tomcat.start();
		tomcat.getServer().await();

	}

}
