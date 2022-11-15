package memberMVC.ex01;



import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class MemberDAO {
	private DataSource dataFactory;
	private Connection conn;//db연동
	private PreparedStatement pstmt;//쿼리문을 db에 입력해주는 역할
	
	public MemberDAO() {
		try {
			Context ctx = new InitialContext();
			Context envContext = (Context)ctx.lookup("java:/comp/env");
			dataFactory = (DataSource)envContext.lookup("jdbc/oracle");
			
		} catch (Exception e) {
			System.out.println("DB연결 에러!!!");
		}
	}
	
	//회원 목록 조회
	public List<MemberVO> memberList() {
		List<MemberVO> memberList = new ArrayList();
		try {
			conn = dataFactory.getConnection();//조회할때마다 데이터베이스 연결
			String query = "select * from membertbl order by joinDate desc";//회원 테이블 조회 쿼리문
			System.out.println("쿼리문 출력 : " + query);
			pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				String id = rs.getString("id");
				String pwd = rs.getString("pwd");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date joinDate = rs.getDate("joinDate");
				MemberVO memberVO = new MemberVO(id, pwd, name, email, joinDate);
				memberList.add(memberVO);
			}
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("DB 처리 중 에러");
		}
		return memberList;
	}
	
	//회원 등록 
	public void addMember(MemberVO memVO) {
		try {
			conn = dataFactory.getConnection();//조회할때마다 데이터베이스 연결
			String id = memVO.getId();
			String pwd = memVO.getPwd();
			String name = memVO.getName();
			String email = memVO.getEmail();
			String query = "insert into membertbl(id,pwd,name,email) values(?,?,?,?)";//회원 테이블에 데이터 추가하는 쿼리문
			System.out.println("쿼리문 출력 : " + query);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			System.out.println("쿼리문 출력 : " + query);
			pstmt.setString(2, pwd);
			pstmt.setString(3, name);
			pstmt.setString(4, email);
			pstmt.executeUpdate(); // 등록작업 실행
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("DB조회중 에러!!");
		}
	}
	
	//수정할 회원 정보 찾기
	public MemberVO findMember(String _id) {
		MemberVO memfindInfo = null;
		try {
			conn = dataFactory.getConnection();
			String query = "select * from membertbl where id=?";
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, _id);
			System.out.println(query);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			String id = rs.getString("id");
			String pwd = rs.getString("pwd");
			String name = rs.getString("name");
			String email = rs.getString("email");
			Date joinDate = rs.getDate("joinDate");
			memfindInfo = new MemberVO(id, pwd, name, email, joinDate);
			rs.close();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("수정할 자료를 찾지 못했습니다.");
		}
		return memfindInfo;
	}
	
	//회원정보 수정
	public void modMember(MemberVO memVO) {
		try {
			conn = dataFactory.getConnection();
			String id = memVO.getId();
			String pwd = memVO.getPwd();
			String name = memVO.getName();
			String email = memVO.getEmail();
			String query = "update membertbl set pwd=?,name=?,email=? where id=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, pwd);
			pstmt.setString(2, name);
			pstmt.setString(3, email);
			pstmt.setString(4, id);
			pstmt.executeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("DB 수정중 오류!!");
		}
	}
	//회원정보 삭제
	public void delMember(String _id) {
		try {
			conn = dataFactory.getConnection();
			String query = "delete from membertbl where id=?";
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, _id);
			pstmt.executeLargeUpdate();
			pstmt.close();
			conn.close();
		} catch (Exception e) {
			System.out.println("회원정보 삭제 에러");
		}
	}
}
