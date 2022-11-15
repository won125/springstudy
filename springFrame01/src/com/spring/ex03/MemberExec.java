package com.spring.ex03;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

public class MemberExec {

	public static void main(String[] args) {
		BeanFactory factory = new XmlBeanFactory(new FileSystemResource("member.xml")); // member.xml에서 bean생성 BeanFactory 임포트할때 주의
		MemberService service = (MemberService)factory.getBean("memberService");
		service.listMembers();
		
	}

}
