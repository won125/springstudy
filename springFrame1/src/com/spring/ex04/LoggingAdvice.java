package com.spring.ex04;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class LoggingAdvice implements MethodInterceptor{ //advice클래스
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		//메서드 호출 전에 수행
		System.out.println("메서드 호출 전 : LoggingAdvice");
		System.out.println(invocation.getMethod() + "메서드 호출 전");
		//메서드 호출
		Object object = invocation.proceed();
		//메서드 호출 후에 수행
		System.out.println("메서드 호출 후 : LoggingAdvice");
		System.out.println(invocation.getMethod() + "메서드 호출 후");
		return null;
	}
}
