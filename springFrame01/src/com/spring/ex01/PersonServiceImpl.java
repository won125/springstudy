package com.spring.ex01;

public class PersonServiceImpl implements PersonService{
	private String name;
	private int age;
	
	
	public void setName(String name) {
		this.name = name;
	}


	@Override
	public void Info() {
		System.out.println("이름은 " + name);
		System.out.println("나이는 " + age);
		
	}
	
}
