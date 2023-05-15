package com.spring.ex04;

public class calculator { //target 클래스
	
	public void add(int a, int b) {
		int result = a+b;
		System.out.println("두 수의 합 = " + result);
	}
	public void sub(int a, int b) {
		int result = a-b;
		System.out.println("두 수의 차 = " + result);
	}
	public void mul(int a, int b) {
		int result = a*b;
		System.out.println("두 수의 곱 = " + result);
	}
	public void div(int a, int b) {
		int result = a/b;
		System.out.println("두 수의 몫 = " + result);
	}
}
