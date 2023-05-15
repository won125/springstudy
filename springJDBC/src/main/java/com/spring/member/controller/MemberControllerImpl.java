package com.spring.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.spring.member.service.MemberService;
import com.spring.member.vo.MemberVO;

public class MemberControllerImpl extends MultiActionController implements MemberController{
	private MemberService memberService;
	
	
	@Override
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		List memberList = memberService.listMembers();
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("memberList",memberList);
		return mav;
	}
	
	@Override
	public ModelAndView addMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		MemberVO memberVO = new MemberVO();
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		memberVO.setId(id);
		memberVO.setPwd(pwd);
		memberVO.setName(name);
		memberVO.setEmail(email);
		int result = memberService.addMember(memberVO);
		List memberList = memberService.listMembers();
		ModelAndView mav = new ModelAndView("listMembers");
		mav.addObject("memberList",memberList);
		return mav;
	}
	
	@Override
	public ModelAndView memberForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	private String getViewName(HttpServletRequest request) throws Exception{ 
		// 보여줄 뷰이름을 개발자가 일일이 직접 코딩하지 않고 URL요청명에서 뷰이름을 가져와 일정하게 연동 => 가독성을 높이고 유지관리도 용이하다
		String contextPath = request.getContextPath();
		String uri = (String)request.getAttribute("javax.servlet.include.request_uri");
		if(uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}
		int begin = 0, end;
		if(!((contextPath==null) || ("".equals(contextPath)))) {
			begin = contextPath.length(); // 전체 요청명의 길이
		}
		if(uri.indexOf(";") != -1) {
			end = uri.indexOf(";"); // 요청 URI의 ; 가 있을경우 ; 의 위치를 구하는 것
		}else if(uri.indexOf("?") != -1) {
			end = uri.indexOf("?"); // 요청 URI의 ? 가 있을경우 ? 의 위치를 구하는 것
		}else {
			end = uri.length();
		}
		String fileName = uri.substring(begin,end);
		if(fileName.lastIndexOf(".") != -1) { // .do 앞에 까지의 문자열을 구함
			fileName = fileName.substring(0,fileName.lastIndexOf("."));
		}
		if(fileName.lastIndexOf("/") != -1) {
			fileName = fileName.substring(fileName.lastIndexOf("/"),fileName.length());
		}
		return fileName;
		
	}
}
