<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<%
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원정보 출력창</title>
<style type="text/css">
	h2{
		text-align: center;
	}
</style>
<c:choose>
	<c:when test='${msg=="addMember"}'>
		<script type="text/javascript">
			window.onload=function(){
				alert("회원을 등록했습니다.");
			}
		</script>
	</c:when>
	<c:when test='${msg=="modfied"}'>
		<script type="text/javascript">
			window.onload=function(){
				alert("회원정보를 수정했습니다.");
			}
		</script>
	</c:when>
	<c:when test='${msg=="delMember"}'>
		<script type="text/javascript">
			window.onload=function(){
				alert("회원을 삭제했습니다.");
			}
		</script>
	</c:when>
</c:choose>
</head>
<body>
	<h2>회원정보</h2>
	<table align="center" border="1">
		<tr align="center" bgcolor="lightgreen">
			<th>아이디</th>
			<th>패스워드</th>
			<th>이름</th>
			<th>이메일</th>
			<th>가입일</th>
			<th>수정</th>
			<th>삭제</th>
		</tr>
		<c:choose>
			<c:when test="${empty memberList}">
				<tr>
					<td colspan="5" align="center">
						등록된 회원이 없습니다.
					</td>
				</tr>
			</c:when>
			<c:when test="${!empty memberList}">
				<c:forEach var="mem" items="${memberList}">
					<tr align="center">
						<td>${mem.id}</td>
						<td>${mem.pwd}</td>
						<td>${mem.name}</td>
						<td>${mem.email}</td>
						<td>${mem.joinDate}</td>
						<td><a href="${contextPath}/member/modMemberForm.do?id=${mem.id}">수정하기</a></td>
						<td><a href="${contextPath}/member/delMember.do?id=${mem.id}">삭제하기</a></td>
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
	</table>
	<p align="center"><a href="${contextPath}/member/memberForm.do">회원가입하기</a></p>
</body>
</html>