package com.spring_dependencies;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

//@Aspect()
//@Component
public class MyAspectj {


	@Pointcut("execution(* com.spring_dependencies.DemoServer1.*(..)) ")
	public void pointCutExecution() {

	}

	@Around("pointCutExecution()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("-----aop-----");
		System.out.println(joinPoint.getSignature().toLongString());
		Object proceed = joinPoint.proceed();
		System.out.println(Thread.currentThread().getName());
		System.out.println("=====aop====");
		System.out.println("\n");
		return proceed;
	}
}
