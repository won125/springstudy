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
<title>회원가입창</title>
</head>
<body>
	<form name="frmMember" action="${contextPath }/member/memberInfo.do" method="post">
		<h2 align="center">회원가입 창</h2>
		<table border="1" align="center">
			<tr>
				<td align="right" width=200>아이디</td>
				<td width=400><input type="text" name="id"></td>
			</tr>
			<tr>
				<td align="right" width=200>패스워드</td>
				<td width=400><input type="password" name="pwd"></td>
			</tr>
			<tr>
				<td align="right" width=200>이름</td>
				<td width=400><input type="text" name="name"></td>
			</tr>
			<tr>
				<td align="right" width=200>이메일</td>
				<td width=400><input type="email" name="email"></td>
			</tr>
			
			<tr align="center">
				<td colspan="2">
					<input type="submit" value="회원가입">
					<input type="reset" value="다시입력">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>