package memberMVC.ex02;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/download.do")
public class FileDownloadController extends HttpServlet {
	private static String ART_IMAGE_REPO = "C:\\myJSP\\board\\article_image";
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String article_no = request.getParameter("article_no");
		String image_filename = request.getParameter("image_filename");
		OutputStream out = response.getOutputStream();
		String path = ART_IMAGE_REPO + "\\" + article_no + "\\" + image_filename;
		File image_file = new File(path);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Content-disposition", "attachment;file_name" + image_filename);
		FileInputStream in = new FileInputStream(image_file);
		byte[] buffer = new  byte[1024*8]; //버퍼를 이용해서 한번에 8k씩 전송
		while(true) {
			int count = in.read(buffer);
			if(count == -1) break;
			out.write(buffer,0,count);
		}
		in.close();
		out.close();
	}
	
}
