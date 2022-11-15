<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/> <%-- 상대경로 현제 문서 위치를 기준으로 경로를 인식하는 방법--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보수정</title>
</head>
<body>
	<form action="${contextPath}/member/modMember.do?id=${memfindInfo.id}" method="post" >
		<h2 align="center">회원정보수정</h2>
		<table align="center">
			<tr align="center">
				<td width="200">아이디</td>
				<td width="400"><input type="text" name="id" value="${memfindInfo.id}" disabled></td>
			</tr>
			<tr align="center">
				<td width="200">비밀번호</td>
				<td width="400"><input type="password" name="pwd" value="${memfindInfo.pwd}"></td>
			</tr>
			<tr align="center">
				<td width="200">이름</td>
				<td width="400"><input type="text" name="name" value="${memfindInfo.name}"></td>
			</tr>
			<tr align="center">
				<td width="200">이메일</td>
				<td width="400"><input type="text" name="email" value="${memfindInfo.email}"></td>
			</tr>
			<tr align="center">
				<td width="200">가입일</td>
				<td width="400"><input type="text" name="email" value="${memfindInfo.joinDate}" disabled></td>
			</tr>
			<tr align="center">
				<td colspan="2">
					<input type="submit" value="수정하기">
					<input type="reset" value="다시입력하기">
				</td>
			</tr>
		</table>
	</form>
	
</body>
</html>