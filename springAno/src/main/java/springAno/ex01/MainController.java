package springAno.ex01;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller("mainController") // MainController클래스의 beanID를 mainController로 설정
@RequestMapping("/test") //class단계에서의 매핑설정
public class MainController {
	
	@RequestMapping(value="/main.do", method = RequestMethod.GET) // main1.do로 접근하면 get방식으로 받아온다
	public ModelAndView mainMethod1(HttpServletRequest request, HttpServletResponse response)throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg","대한민국");
		mav.addObject("main");
		return mav;
	}
	@RequestMapping(value="/main2.do", method = RequestMethod.GET)
	public ModelAndView mainMethod2(HttpServletRequest request, HttpServletResponse response)throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.addObject("msg","우리나라!!!!");
		mav.addObject("main");
		return mav;
	}
	
}
