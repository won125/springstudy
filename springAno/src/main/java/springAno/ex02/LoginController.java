package springAno.ex02;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller("loginController")
public class LoginController {
	@RequestMapping(value = {"/test/loginForm.do","/test/loginForm2.do"},
			method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception{
		ModelAndView mav = new ModelAndView();
		mav.setViewName("loginForm");
		return mav;
	}
	
	/*
	@RequestMapping(value = "/test/login.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("result");
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		mav.addObject("id",id);
		mav.addObject("pwd",pwd);
		return mav;
	}
	@RequestMapping(value = "/test/login.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView login(@RequestParam("id") String id, @RequestParam("pwd") String pwd, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("result");
		mav.addObject("id",id);
		mav.addObject("pwd",pwd);
		return mav;
	}
	
	@RequestMapping(value = "/test/login.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView login(@RequestParam(value="id", required = true) String id, @RequestParam(value = "pwd", required = true) String pwd, @RequestParam(value = "email", required = false) String email, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("result");
		mav.addObject("id",id);
		mav.addObject("pwd",pwd);
		mav.addObject("email",email);
		return mav;
	}
	
	@RequestMapping(value = "/test/login.do", method = {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView login(@ModelAttribute("memInfo") LoginVO loginVO, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("result");
		
		return mav;
	}*/
	
	@RequestMapping(value = "/test/login.do", method = {RequestMethod.GET, RequestMethod.POST})
	public String login(Model model, HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();
		model.addAttribute("id","hong");
		model.addAttribute("pwd","1234");
		model.addAttribute("email","hong@naver.com");
		
		return "result";
	}
	
}
