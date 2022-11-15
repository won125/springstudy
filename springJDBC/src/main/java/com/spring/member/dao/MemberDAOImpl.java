package com.spring.member.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.spring.member.vo.MemberVO;

public class MemberDAOImpl implements MemberDAO{
	 private JdbcTemplate jdbcTemplate;
	 
	 
	 
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List selectAllMembers() throws DataAccessException {
		String query = "select * from membertbl order by joindate desc";
		List memberList = new ArrayList();
		memberList = this.jdbcTemplate.query(query, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int rownum) throws SQLException {
				MemberVO memberVO = new MemberVO();
				memberVO.setId(rs.getString("id"));
				memberVO.setPwd(rs.getString("pwd"));
				memberVO.setName(rs.getString("name"));
				memberVO.setEmail(rs.getString("email"));
				memberVO.setJoindate(rs.getDate("joindate"));
				return memberVO;
			}
		});
		return memberList;
	}
	
	@Override
	public int addMember(MemberVO memberVO) throws DataAccessException {
		String id   = memberVO.getId();
		String pwd  = memberVO.getPwd();
		String name = memberVO.getName();
		String email= memberVO.getEmail();
		String query = "insert into membertbl(id,pwd,name,email) values('" + id + "','" + pwd + "','" + name + "','" + email + "')";
		System.out.println("회원 등록 쿼리문 : " + query);
		int result = jdbcTemplate.update(query);
		return result;
	}
}
