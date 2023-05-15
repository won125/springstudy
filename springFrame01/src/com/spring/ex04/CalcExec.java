package com.spring.ex04;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CalcExec {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("AOPCalc.xml");
		calculator calc = (calculator)context.getBean("proxyCal");
		System.out.println("=======================================");
		calc.add(54, 23);
		System.out.println("=======================================");
		calc.sub(100, 43);
		System.out.println("=======================================");
		calc.mul(23, 15);
		System.out.println("=======================================");
		calc.div(7482, 23);
		System.out.println("=======================================");

	}

}
