package springMybatis.ex01.member.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import springMybatis.ex01.member.dao.MemberDAO;
import springMybatis.ex01.member.vo.MemberVO;

public class MemberServiceImpl implements MemberService{
	private MemberDAO memberDAO;
	
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	
	@Override
	public List<MemberVO> listMembers() throws DataAccessException {
		List<MemberVO> memberList=null;
		memberList = memberDAO.selectAllMemberList();
		return memberList;
	}
	@Override
	public int addMember(MemberVO memberVO) throws DataAccessException {
		return memberDAO.insertMember(memberVO);
	}
	@Override
	public int updateMember(MemberVO memberVO) throws DataAccessException {
		return memberDAO.updateMember(memberVO);
	}
	@Override
	public int removeMember(String id) throws DataAccessException {
		return memberDAO.deleteMember(id);
	}
}
