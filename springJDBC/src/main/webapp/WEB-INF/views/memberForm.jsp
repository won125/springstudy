<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/> <%-- 상대경로 현제 문서 위치를 기준으로 경로를 인식하는 방법--%>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입 폼</title>
</head>
<body>
	<form action="${contextPath}/member/addMember.do" method="post">
		<h2 align="center">회원등록</h2>
		<table>
			<tr>
				<td width="200"><p align="right">아이디</p></td>
				<td width="400"><input type="text" name="id"></td>
			</tr>
			<tr>
				<td width="200"><p align="right">비밀번호</p></td>
				<td width="400"><input type="password" name="pwd"></td>
			</tr>
			<tr>
				<td width="200"><p align="right">이름</p></td>
				<td width="400"><input type="text" name="name"></td>
			</tr>
			<tr>
				<td width="200"><p align="right">이메일</p></td>
				<td width="400"><input type="text" name="email"></td>
			</tr>
			<tr>
				<td align="center" colspan="2">
					<input type="submit" value="가입하기">
					<input type="reset" value="다시입력">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>