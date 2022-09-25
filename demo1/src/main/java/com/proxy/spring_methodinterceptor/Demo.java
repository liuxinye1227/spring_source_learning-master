package com.proxy.spring_methodinterceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Method;

/**
 * @author pengYongQiang
 * @version 1.0
 * @date 2020/8/14 17:12
 * spring的方法拦截器,基于JDK代理或CGLIB代理
 */
public class Demo {
	public static void main(String[] args) {
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setTarget(new Demo());
		//添加通知
		proxyFactory.addAdvice(new AdviseMethodInterceptor());

		Demo proxy = (Demo) proxyFactory.getProxy();
		proxy.pay();

	}

	//切面
	static class AdviseMethodInterceptor implements MethodInterceptor {
		@Override
		public Object invoke(MethodInvocation invocation) throws Throwable {
			System.out.println("支付之前检查环境是否安全....");
			Object proceed = invocation.proceed();
			System.out.println("支付完成....");
			return proceed;
		}
	}

	//支付方法
	public void pay(){
		System.out.println("支付");
	}
}
