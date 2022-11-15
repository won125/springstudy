package memberMVC.ex01;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@WebServlet("/member/*")
public class MemberController extends HttpServlet {
	MemberDAO memberDAO;
	
	
	@Override
	public void init() throws ServletException { //데이터 베이스 초기화 -> 서버를 킬때 단 한번만 실행
		memberDAO = new MemberDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = null;
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String action = request.getPathInfo(); // URL 매핑 요청이름을 가져오는 메서드
		System.out.println("요청 매핑이름 : " + action);
		if(action == null || action.equals("/listMembers.do")) {
			List<MemberVO> memberList = memberDAO.memberList();
			request.setAttribute("memberList", memberList);
			nextPage = "/meminfo/listMembers.jsp";
		}else if(action.equals("/addMember.do")) {
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			MemberVO memberVO = new MemberVO(id,pwd,name,email);
			memberDAO.addMember(memberVO);
			request.setAttribute("msg", "addMember");
			nextPage = "/member/listMembers.do";
		}else if(action.equals("/modMember.do")) {
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			MemberVO memberVO = new MemberVO(id,pwd,name,email);
			memberDAO.modMember(memberVO);
			request.setAttribute("msg", "modfied");
			nextPage = "/member/listMembers.do";
		}else if(action.equals("/delMember.do")) {
			String id = request.getParameter("id");
			memberDAO.delMember(id);
			request.setAttribute("msg", "delMember");
			nextPage = "/member/listMembers.do";
		}else if(action.equals("/memberForm.do")) {
			nextPage = "/meminfo/memberForm.jsp";
		}else if(action.equals("/modMemberForm.do")){
			String id = request.getParameter("id");
			MemberVO memfindInfo = memberDAO.findMember(id);
			request.setAttribute("memfindInfo", memfindInfo);
			nextPage="/meminfo/modMemberForm.jsp";
		}else {
			List<MemberVO> memberList = memberDAO.memberList();
			request.setAttribute("memberList", memberList);
			nextPage = "/meminfo/listMembers.jsp";
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}

}
