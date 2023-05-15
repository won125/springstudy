package com.spring.ex02;

public class PersonServiceImpl implements PersonService{
	private String name;
	private int age;
	
	
	public PersonServiceImpl(String name) {
		this.name = name;
	}
	
	public PersonServiceImpl(String name, int age) {
		this.name = name;
		this.age = age;
	}

	@Override
	public void Info() {
		System.out.println("이름은 " + name);
		System.out.println("나이는 " + age);
		
	}
	
		
	
}
