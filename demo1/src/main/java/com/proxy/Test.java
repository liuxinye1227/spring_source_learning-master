package com.proxy;

import com.service.IMyService;
import com.service.MyService;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.lang.reflect.Proxy;

public class Test {
	public static void main(String[] args) {
		IMyService myService = new MyService();

		IMyService iMyService = extracted1(myService);
		IMyService iMyService1 = extracted1(iMyService);

		iMyService1.b();
	}

	private static IMyService extracted1(IMyService myService) {
		DemoProxy demoProxy = new DemoProxy(myService);

		IMyService o = (IMyService) Proxy.newProxyInstance(Test.class.getClassLoader(), new Class[]{IMyService.class}, demoProxy);
		return o;
	}

	private static void extracted() {
		IMyService myService = new MyService();

		DemoProxy demoProxy = new DemoProxy(myService);

		IMyService o = (IMyService) Proxy.newProxyInstance(Test.class.getClassLoader(), new Class[]{IMyService.class}, demoProxy);

		o.b();
		System.out.println(o.getClass());

		boolean proxyClass = Proxy.isProxyClass(o.getClass());

		System.out.println(proxyClass);

		System.out.println(123);
	}
}
