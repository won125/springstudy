<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>    
<%
   	request.setCharacterEncoding("utf-8");
	String id = request.getParameter("id");
%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보수정</title>
</head>
<body>
	<form name="frmMember" action="${contextPath}/member/modMember.do" method="post">
		<h2 align="center">회원정보 수정폼</h2>
		<table border="1" align="center">
			<tr>
				<td align="right" width=200>아이디</td>
				<td width=400><input type="text" name="id" value="<%=id%>" disabled></td>
			</tr>
			<tr>
				<td align="right" width=200>패스워드</td>
				<td width=400><input type="password" name="pwd"></td>
			</tr>
			<tr>
				<td align="right" width=200>이름</td>
				<td width=400><input type="text" name="name" ></td>
			</tr>
			<tr>
				<td align="right" width=200>이메일</td>
				<td width=400><input type="email" name="email"></td>
			</tr>
			
			<tr align="center">
				<td colspan="2">
					<input type="submit" value="수정하기">
					<input type="reset" value="다시입력">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>