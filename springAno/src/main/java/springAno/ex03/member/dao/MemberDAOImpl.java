package springAno.ex03.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import springAno.ex03.member.vo.MemberVO;


@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO{
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<MemberVO> selectAllMemberList() throws DataAccessException {
		List<MemberVO> memberList=null;
		memberList=sqlSession.selectList("mapper.member.selectAllMemberList");
		return memberList;
	}

	@Override
	public int insertMember(MemberVO memberVO) throws DataAccessException {
		int result=sqlSession.insert("mapper.member.insertMember", memberVO);
		return result;
	}

	@Override
	public int deleteMember(String id) throws DataAccessException {
		int result=sqlSession.delete("mapper.member.deleteMember",id);
		return result;
	}
	
	@Override
	public MemberVO findMember(String id) throws DataAccessException {
		MemberVO memberVO;
		memberVO =(MemberVO)sqlSession.selectOne("mapper.member.findMember",id);
		return memberVO;
	}
	@Override
	public int updateMember(MemberVO memberVO) throws DataAccessException {		
		int result=sqlSession.update("mapper.member.updateMember", memberVO);
		return result;
	}


}
