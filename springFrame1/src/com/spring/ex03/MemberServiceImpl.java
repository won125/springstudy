package com.spring.ex03;

public class MemberServiceImpl implements MemberService{
	private MemberDAO memberDAO;
	
	
	
	public void setMemberDAO(MemberDAO memberDAO) { 
		this.memberDAO = memberDAO;
	}



	@Override
	public void listMembers() {
		memberDAO.listMembers(); //멤버의 실제 목록을 불러온다
		
	}
	
}
