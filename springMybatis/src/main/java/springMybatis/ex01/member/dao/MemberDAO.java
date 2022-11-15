package springMybatis.ex01.member.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import springMybatis.ex01.member.vo.MemberVO;

public interface MemberDAO {
	public List selectAllMemberList() throws DataAccessException;
	public int insertMember(MemberVO memberVO) throws DataAccessException;
	public int deleteMember(String id) throws DataAccessException;
	public MemberVO findMember(String id) throws DataAccessException;
	public int updateMember(MemberVO memberVO) throws DataAccessException;
}
