package com.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

@Aspect()
@Component
public class MyAspectj {


	@Pointcut("execution(* com.service.*.*(..) throws RuntimeException) ")
	public void pointCutExecution() {

	}

	@Pointcut("args(Integer)")
	public void pointCutArgs() {
	}

	@Pointcut("@annotation(com.annotation.MyAnnotation)")
	public void pointCutAnnotation() {

	}

	@Pointcut("target(com.service.IMyService)")
	public void pointCutThis() {

	}

	@Before("pointCutThis()")
	public void before(JoinPoint joinPoint){
		System.out.println("-----aop-----");
		System.out.println(joinPoint.getTarget().toString());
		System.out.println("=====aop=====");
		System.out.println("\n");
	}

	@Around("pointCutThis()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("-----aop-----");
		System.out.println(joinPoint.getSignature().toLongString());
		Object proceed = joinPoint.proceed();
		System.out.println("=====aop====");
		System.out.println("\n");


		return proceed;
	}
}
