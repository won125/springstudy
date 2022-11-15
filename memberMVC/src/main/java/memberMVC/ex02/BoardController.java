package memberMVC.ex02;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;

@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private static String ART_IMAGE_REPO = "C:\\myJSP\\board\\article_image";
	BoardService boardService;
	ArticleVO articleVO;
	PrintWriter out; 
	
	public void init(ServletConfig config) throws ServletException {
		boardService = new BoardService();
		articleVO = new ArticleVO();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}
	
	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nextPage = null; // 포워딩 시켜줄 위치를 저장하는 변수
		HttpSession session;
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String action = request.getPathInfo(); // URL 매핑 요청이름을 가져오는 메서드
		System.out.println("요청 매핑이름 : " + action);
		try {
			List<ArticleVO> articleList = new ArrayList<ArticleVO>();
			if(action == null || action.equals("/listArticles.do")) {
				String _section = request.getParameter("section");
				String _pageNum = request.getParameter("pageNum");
				int section = Integer.parseInt((_section == null)?"1":_section);
				int pageNum = Integer.parseInt((_pageNum == null)?"1":_pageNum);
				Map<String, Integer> pagingMap = new HashMap<String, Integer>();
				pagingMap.put("section", section);
				pagingMap.put("pageNum", pageNum);
				Map articleMap = boardService.listArticles(pagingMap);
				articleMap.put("section", section);
				articleMap.put("pageNum", pageNum);
				request.setAttribute("articleMap", articleMap);
				nextPage="boardinfo/listArticles.jsp";
				
			}/*else if(action.equals("/listArticles.do")) { // 전체 글 조회
				articleList = boardService.listArticles();
				request.setAttribute("articleList", articleList); // 조회된 글 목록을 articleList로 바인딩 후 
				nextPage = "/boardinfo/listArticles.jsp"; // 이 주소로 포워딩한다.
				
			}*/else if(action.equals("/addArticle.do")) { // 새 글 등록 작업 수행
				int article_no = 0;
				//파일 업로드 기능을 사용하기 위해 upload로 요청을 전달
				Map<String, String> articleMap = upload(request, response);
				String title = articleMap.get("title");
				String content = articleMap.get("content");
				String image_filename = articleMap.get("image_filename");
				articleVO.setParent_no(0);
				articleVO.setId("admin");
				articleVO.setTitle(title);
				articleVO.setContent(content);
				articleVO.setImage_filename(image_filename);
				article_no = boardService.addArticle(articleVO);
				//이미지를 첨부했을때만 적용
				if(image_filename != null && image_filename.length() != 0) {
					// temp 폴더에 임시로 업로드된 파일 객체를 생성
					File srcFile = new File(ART_IMAGE_REPO + "\\" + "temp\\" + image_filename);
					// 글 번호로 폴더를 생성
					File destDir = new File(ART_IMAGE_REPO + "\\" + article_no);
					destDir.mkdir();
					// temp폴더의 파일을 글 번호를 이름으로 하는 폴더로 이동
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
					srcFile.delete();
				}
				out = response.getWriter();
				out.print("<script>" + "alert('새 글을 추가했습니다.');" + "location.href='" + request.getContextPath() + "/board/listArticles.do';" + "</script>");
				out.close();
				return;
				
			}else if(action.equals("/viewArticle.do")) {
				String article_no = request.getParameter("article_no");
				articleVO = boardService.viewArticle(Integer.parseInt(article_no));
				request.setAttribute("article", articleVO);
				nextPage = "/boardinfo/viewArticle.jsp";
				
			}else if(action.equals("/articleForm.do")) { // 글쓰기 창으로 이동
				nextPage = "/boardinfo/articleForm.jsp";	
			}else if(action.equals("/modArticle.do")){
				Map<String, String> articleMap = upload(request,response);
				int article_no = Integer.parseInt(articleMap.get("article_no"));
				articleVO.setArticle_no(article_no);
				String title = articleMap.get("title");
				String content = articleMap.get("content");
				String image_filename = articleMap.get("image_filename");
				articleVO.setParent_no(0);
				articleVO.setId("admin");
				articleVO.setTitle(title);
				articleVO.setContent(content);
				articleVO.setImage_filename(image_filename);
				boardService.modArticle(articleVO);
				//이미지를 첨부했을때만 적용
				if(image_filename != null && image_filename.length() != 0) {
					String originalFilename = articleMap.get("originalFilename");
					// temp 폴더에 임시로 업로드된 파일 객체를 생성
					File srcFile = new File(ART_IMAGE_REPO + "\\" + "temp\\" + image_filename);
					// 글 번호로 폴더를 생성
					File destDir = new File(ART_IMAGE_REPO + "\\" + article_no);
					destDir.mkdir();
					// temp폴더의 파일을 글 번호를 이름으로 하는 폴더로 이동
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
					File oldFile = new File(ART_IMAGE_REPO + "\\" + article_no + "\\" + originalFilename);
					oldFile.delete();
				}
				out = response.getWriter();
				out.print("<script>" + "alert('글을 수정했습니다.');" + "location.href='" + request.getContextPath() 
				+ "/board/viewArticle.do?article_no=" + article_no + "';" + "</script>");
				return;
			}else if(action.equals("/removeArticle.do")){
				int article_no = Integer.parseInt(request.getParameter("article_no"));
				List<Integer> article_no_list = boardService.removeArticle(article_no);
				for(int no : article_no_list) {
					File imgDir = new File(ART_IMAGE_REPO + "\\" + no);
					if(imgDir.exists()) {
						FileUtils.deleteDirectory(imgDir);
					}
				}
				out = response.getWriter();
				out.print("<script>" + "alert('글 삭제 완료했습니다.');" + "location.href='" + request.getContextPath() + "/board/listArticles.do';" + "</script>");
				out.close();
				return;
			}else if(action.equals("/replyForm.do")){
				int parent_no = Integer.parseInt(request.getParameter("parent_no"));
				session = request.getSession();
				session.setAttribute("parent_no", parent_no);
				nextPage="/boardinfo/replyForm.jsp";
			}else if(action.equals("/addReply.do")){
				session = request.getSession();
				int parent_no = (Integer)session.getAttribute("parent_no");
				session.removeAttribute("parent_no");
				Map<String, String> articleMap = upload(request, response);
				String title = articleMap.get("title");
				String content = articleMap.get("content");
				String image_filename = articleMap.get("image_filename");
				articleVO.setParent_no(parent_no);
				articleVO.setId("동동");
				articleVO.setTitle(title);
				articleVO.setContent(content);
				articleVO.setImage_filename(image_filename);
				int article_no = boardService.addReply(articleVO);
				System.out.println(article_no);
				//이미지를 첨부했을때만 적용
				if(image_filename != null && image_filename.length() != 0) {
					// temp 폴더에 임시로 업로드된 파일 객체를 생성
					File srcFile = new File(ART_IMAGE_REPO + "\\temp\\" + image_filename);
					// 글 번호로 폴더를 생성
					File destDir = new File(ART_IMAGE_REPO + "\\" + article_no);
					destDir.mkdir();
					// temp폴더의 파일을 글 번호를 이름으로 하는 폴더로 이동
					FileUtils.moveFileToDirectory(srcFile, destDir, true);
					srcFile.delete();
				}
				out = response.getWriter();
				out.print("<script>" + "alert('답글을 추가했습니다.');" + "location.href='" 
				+ request.getContextPath() + "/board/viewArticle.do?article_no=" + article_no +"';</script>");
				return;
			}else {
				articleList = boardService.listArticles();
				request.setAttribute("articleList", articleList);
				nextPage = "/boardinfo/listArticles.jsp";
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
			dispatcher.forward(request, response);
		} catch (Exception e) {
			System.out.println("요청 처리 중 에러" + e.getMessage());
		}
	}
	
	//이미지 파일 업로드 및 새글 관련 정보 저장
	private Map<String, String> upload(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, String> articleMap = new HashMap<String, String>();
		String encoding = "utf-8";
		File currentDirPath = new File(ART_IMAGE_REPO);
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setRepository(currentDirPath);
		factory.setSizeThreshold(1024*1024);
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List items = upload.parseRequest(request);
			for(int i=0; i<items.size(); i++) {
				FileItem fileItem = (FileItem)items.get(i);
				if(fileItem.isFormField()) {
					System.out.println(fileItem.getFieldName() + " = " + fileItem.getString(encoding));
					//파일 업로드로 같이 전송된 새 글 관련 (제목, 내용) 매개변수를 저장한 후 반환
					articleMap.put(fileItem.getFieldName(), fileItem.getString(encoding));//title과 content값을 가져와주는 기능
				}else {
					System.out.println("파라미터이름 : " + fileItem.getFieldName());
					System.out.println("파일이름 : " + fileItem.getName());
					System.out.println("파일크기 : " + fileItem.getSize() + "bytes");
					if(fileItem.getSize() > 0) {
						int index = fileItem.getName().lastIndexOf("\\");
						if(index == -1) {
							index = fileItem.getName().lastIndexOf("/");
						}
						String fileName = fileItem.getName().substring(index+1);
						articleMap.put(fileItem.getFieldName(), fileName);
						//업로드한 이미지를 일단 temp폴더에 저장
						File uploadFile = new File(currentDirPath + "\\temp\\" + fileName);
						fileItem.write(uploadFile);
					}
				}
			}
		} catch (Exception e) {
			System.out.println("파일 업로드 에러!!" + e.getMessage());
		}
		return articleMap;
	}

}
