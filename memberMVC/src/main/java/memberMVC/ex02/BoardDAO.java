package memberMVC.ex02;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import java.net.URLEncoder;

public class BoardDAO {
	private DataSource dataFactory;
	private Connection conn;//db연동
	private PreparedStatement pstmt;//쿼리문을 db에 입력해주는 역할
	
	public BoardDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context)ctx.lookup("java:/comp/env");
			dataFactory = (DataSource)envContext.lookup("jdbc/oracle");
			
		} catch (Exception e) {
			System.out.println("DB연결 에러!!!");
		}
	}
	
	// 글목록 페이징 메서드
		public List selectAllArticles(Map<String , Integer> pagingMap){
			List<ArticleVO> articleList = new ArrayList<ArticleVO>();
			int section = Integer.valueOf(pagingMap.get("section"));
			int pageNum = Integer.valueOf(pagingMap.get("pageNum"));
			try {
				conn = dataFactory.getConnection();
				String query = "SELECT * FROM ( SELECT ROWNUM AS recNum, LVL, article_no, parent_no, title, id, write_date from (SELECT LEVEL AS LVL, article_no, parent_no, title, id, write_date from boardtbl START WITH parent_no=0 CONNECT BY PRIOR article_no = parent_no ORDRT SIBLINGS BY article_no DESC)) WHERE recNum BETWEEN (?-1)*100+(?-1)*10+1 AND (?-1)*100+?*10";
				//페이징 처리 section이 3 pagingNum이 4면 (3-1)*100 +(4-1)*10+1 해서 231번재글 즉 3섹션의 31번쨰글
				System.out.println("쿼리문 : " + query);
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, section);
				pstmt.setInt(2, pageNum);
				pstmt.setInt(3, section);
				pstmt.setInt(4, pageNum);
				ResultSet rs = pstmt.executeQuery();
				while(rs.next()) {
					int level =rs.getInt("lvl"); // 각 글의 깊이(계층) level 속성을 지정
					int article_no = rs.getInt("article_no"); // 글 번호는 숫자형 getInt
					int parent_no  = rs.getInt("parent_no");
					String title = rs.getString("title");
					String id = rs.getString("id");
					Date write_date = rs.getDate("write_date");
					ArticleVO article = new ArticleVO(); // 글 정보를 ArticleVO에 설정
					article.setLevel(level);
					article.setArticle_no(article_no);
					article.setParent_no(parent_no);
					article.setTitle(title);
					article.setId(id);
					article.setWrite_date(write_date);
					articleList.add(article);
				}
				rs.close();
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				System.out.println("글목록 페이징 조회중 에러" + e.getMessage());
			}
			return articleList;
		}
	
	// 게시글 목록 전부 출력
	public List<ArticleVO> selectAllArticles(){
		List<ArticleVO> listArticles = new ArrayList();
		try {
			conn = dataFactory.getConnection();
			String query = "select LEVEL, article_no, parent_no, title, content, write_date, id "+
			"from boardtbl START WITH parent_no=0 CONNECT BY PRIOR article_no=parent_no ORDER SIBLINGS BY article_no DESC";
			System.out.println("쿼리문 : " + query);
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int level = rs.getInt("level"); // 각 글의 깊이(계층) level 속성을 지정
				int article_no = rs.getInt("article_no"); // 글 번호는 숫자형 getInt
				int parent_no  = rs.getInt("parent_no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String id = rs.getString("id");
				Date write_date = rs.getDate("write_date");
				ArticleVO article = new ArticleVO(); // 글 정보를 ArticleVO에 설정
				article.setLevel(level);
				article.setArticle_no(article_no);
				article.setParent_no(parent_no);
				article.setTitle(title);
				article.setContent(content);
				article.setId(id);
				article.setWrite_date(write_date);
				listArticles.add(article);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("글목록 조회 에러");
		}
		return listArticles;
	}
	
	// 글번호 생성 메서드
	private int getNewArticleNo() {
		try {
			conn = dataFactory.getConnection();
			String query = "select max(article_no) from boardtbl";
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				return (rs.getInt(1)+1);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("글 번호 받아오기 실패!!" + e.getMessage());
		}
		return 0;
	}
	
	
	// 게시글 작성 메서드
	public int insertNewArticle(ArticleVO article) {
		int article_no = getNewArticleNo(); // 새 글을 추가하기 전에 새 글에 대한 글번호를 가져온다.
		try {
			conn = dataFactory.getConnection();
			int parent_no = article.getParent_no();
			String title = article.getTitle();
			String content = article.getContent();
			String id = article.getId();
			String image_filename = article.getImage_filename();
			String query = "insert into boardtbl(article_no,parent_no,title,content";
			if(image_filename != null && image_filename.length() != 0) {
				query += ",image_filename,id) values (?,?,?,?,?,?)";
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, article_no);
				pstmt.setInt(2, parent_no);
				pstmt.setString(3, title);
				pstmt.setString(4,content);
				pstmt.setString(5,image_filename);
				pstmt.setString(6, id);
			}else {
				query += ",id) values (?,?,?,?,?)";
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, article_no);
				pstmt.setInt(2, parent_no);
				pstmt.setString(3, title);
				pstmt.setString(4,content);
				pstmt.setString(5, id);
			}
			
			pstmt.executeQuery();
			pstmt.close();
			conn.close();
			
		} catch (Exception e) {
			System.out.println("새 글 추가중 에러!!" + e.getMessage());
		}
		return article_no;
	}
	
	//게시글 상세보기
	public ArticleVO selectArticles(int a_no){
		ArticleVO article = new ArticleVO();
		try {
			conn = dataFactory.getConnection();
			String query = "select article_no, parent_no, title, content, NVL(image_filename,'null') as image_filename, id, write_date from boardtbl where article_no=?";
			System.out.println("쿼리문 : " + query);
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, a_no);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int article_no = rs.getInt("article_no"); // 글 번호는 숫자형 getInt
				int parent_no  = rs.getInt("parent_no");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String id = rs.getString("id");
				String image_filename = URLEncoder.encode(rs.getString("image_filename"),"utf-8");
				if(image_filename.equals("null")) {
					image_filename=null;
				}
				Date write_date = rs.getDate("write_date");
				article.setArticle_no(article_no);
				article.setParent_no(parent_no);
				article.setTitle(title);
				article.setContent(content);
				article.setId(id);
				article.setImage_filename(image_filename);
				article.setWrite_date(write_date);				
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("글 상세기능 구현 중 에러");
		}
		
		return article;
	}
	
	// 게시글 수정
	public void updateArticle(ArticleVO article) {
		int article_no = article.getArticle_no();
		String title = article.getTitle();
		String content = article.getContent();
		String image_filename = article.getImage_filename();
		try {
			conn = dataFactory.getConnection();
			String query = "update boardtbl set title=?, content=?";
			if(image_filename != null && image_filename.length() != 0) {
				query += ",image_filename=?";
			}
			query+=" where article_no=?";
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			if(image_filename != null && image_filename.length() != 0) {
				pstmt.setString(3, image_filename);
				pstmt.setInt(4, article_no);
			}else {
				pstmt.setInt(3, article_no);
			}
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("DB 수정 중 에러!! " + e.getMessage());
		}
	}
	
	//삭제할 게시글 번호 목록
	public List<Integer> selectRemoveArticles(int article_no){
		List<Integer> article_no_list = new ArrayList<Integer>();
		try {
			conn = dataFactory.getConnection();
			String query = "select article_no from boardtbl start with article_no=?";
			query += " connect by prior article_no=parent_no"; // 
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, article_no);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				article_no = rs.getInt("article_no");
				article_no_list.add(article_no);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("삭제할 글 번호 목록 조회 오류!!");
		}
		
		return article_no_list;
	}
	
	// 게시글 삭제
	public void deleteArticle(int article_no) {
		try {
			conn = dataFactory.getConnection();
			String query = "delete from boardtbl where article_no in (select article_no from boardtbl start with article_no=? connect by prior article_no=parent_no)";
			System.out.println(query);
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, article_no);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("게시글 삭제 중 에러!!" + e.getMessage());
		}
	}
	
	// 답글 작성 메서드
		public int insertNewReply(ArticleVO article) {
			int article_no = getNewArticleNo(); // 새 글을 추가하기 전에 새 글에 대한 글번호를 가져온다.
			try {
				conn = dataFactory.getConnection();
				int parent_no = article.getParent_no();
				String title = article.getTitle();
				String content = article.getContent();
				String id = article.getId();
				String image_filename = article.getImage_filename();
				String query = "insert into boardtbl(article_no,parent_no,title,content";
				if(image_filename != null && image_filename.length() != 0) {
					query += ",image_filename,id) values (?,?,?,?,?,?)";
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, article_no);
					pstmt.setInt(2, parent_no);
					pstmt.setString(3, title);
					pstmt.setString(4,content);
					pstmt.setString(5,image_filename);
					pstmt.setString(6, id);
				}else {
					query += ",id) values (?,?,?,?,?)";
					pstmt = conn.prepareStatement(query);
					pstmt.setInt(1, article_no);
					pstmt.setInt(2, parent_no);
					pstmt.setString(3, title);
					pstmt.setString(4,content);
					pstmt.setString(5, id);
				}
				
				pstmt.executeQuery();
				pstmt.close();
				conn.close();
				
			} catch (Exception e) {
				System.out.println("새 글 추가중 에러!!" + e.getMessage());
			}
			return article_no;
		}
		
		//전체 글 목록 수 메서드
		public int selectToArticles() {
			try {
				conn = dataFactory.getConnection();
				String query = "select count(article_no) from boardtbl";
				System.out.println(query);
				pstmt=conn.prepareStatement(query);
				ResultSet rs = pstmt.executeQuery();
				if (rs.next()) {
					return (rs.getInt(1));
				}
				rs.close();
				pstmt.close();
				conn.close();
			} catch (Exception e) {
				System.out.println("글 목록 수 처리 중 에러");
			}
			return 0;
		}
	
	
}
