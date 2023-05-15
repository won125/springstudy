<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>    
<%
   request.setCharacterEncoding("utf-8");
	
%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h2 align="center">회원정보</h2>
	<table align="center" width="80%" border="1">
		<tr align="center" bgcolor="lightgreen">
			<th>아이디</th><th>비밀번호</th><th>이름</th><th>이메일</th><th>가입일</th>
		</tr> 
		
		<c:forEach var="member" items="${memberList}">
		<tr>
			<td>${member.id}</td>
			<td>${member.pwd}</td>
			<td>${member.name}</td>
			<td>${member.email}</td>
			<td><fmt:formatDate value="${member.joindate}"/></td>
			<td><a href="${contextPath}/member/modMemberForm.do?id=${member.id}">수정</a></td>
			<td><a href="${contextPath}/member/removeMember.do?id=${member.id}">삭제</a></td>
		</tr>
		</c:forEach>
	</table>
	<h3 align="center"><a href="${contextPath}/member/memberForm.do">회원가입</a></h3>
</body>
</html>