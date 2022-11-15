package memberMVC.ex02;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Date;

public class ArticleVO {
	private int level; //글의 깊이를 저장하는 필드
	private int article_no; // 글번호 필드
	private int parent_no; // 부모글번호 필드
	private String title; // 제목
	private String content; // 본문
 	private String image_filename; // 이미지 파일 이름
	private Date write_date; // 작성일
	private String id; // 아이디
	
	//생성자
	public ArticleVO() {
		//System.out.println("articleVO 생성자 호출");
	}
	
	// 변수를 받아주는 생성자
		public ArticleVO(int level, int article_no, int parent_no, String title, String content, String image_filename, String id) {
			this.level = level;
			this.article_no = article_no;
			this.parent_no = parent_no;
			this.title = title;
			this.content = content;
			this.image_filename = image_filename;
			this.id = id;
		}

		//getter / setter 메서드 접근제한자에 접근하기위해 들어간다.
		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public int getArticle_no() {
			return article_no;
		}

		public void setArticle_no(int article_no) {
			this.article_no = article_no;
		}

		public int getParent_no() {
			return parent_no;
		}

		public void setParent_no(int parent_no) {
			this.parent_no = parent_no;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getImage_filename() {
			try {
				if(image_filename != null && image_filename.length() != 0) {
					image_filename = URLDecoder.decode(image_filename,"utf-8");
				}
			} catch (UnsupportedEncodingException e) {
				System.out.println("이미지 로딩 중 에러");
			}
			return image_filename;
		}

		public void setImage_filename(String image_filename) {
			try {
				if(image_filename != null && image_filename.length() != 0) {
					this.image_filename = URLEncoder.encode(image_filename,"utf-8");
				}
			} catch (UnsupportedEncodingException e) {
				System.out.println("이미지 저장 중 에러");
			}
		}

		public Date getWrite_date() {
			return write_date;
		}

		public void setWrite_date(Date write_date) {
			this.write_date = write_date;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}
	
		
}
