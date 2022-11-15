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
<title>게시글 목록</title>
<style type="text/css">
	.ba {
		text-decoration: none;
	}
	span {
		padding-left:10px;
	}
	
</style>
</head>
<body>
	<table align="center" border="1" width="80%">
		<tr align="center" bgcolor="lightgreen">
			<th>글번호</th>
			<th>작성자</th>
			<th>제목</th>
			<th>작성일</th>
		</tr>
		<c:choose>
			<c:when test="${empty articleList}">
				<td colspan="4"><p>작성된 글이 없습니다.</p></td>
			</c:when>
			<%-- articleList로 포워딩한 글 목록을 forEach태그를 이용해서 표시 --%>
			<c:when test="${!empty articleList}">
				<c:forEach var="board" items="${articleList}" varStatus="article_num">
					<tr align="center">
					<%-- varStatus의 count속성을 이용해 글번호 1부터 자동표시 --%>
						<td width="5%">${article_num.count}</td>
						<td width="10%">${board.id}</td>
						<td align="left" width="50%">
							<c:choose>
								<c:when test="${board.level > 1}">
								<%-- 부모글 기준으로 왼쪽 여백을 level값만큼 채워 답글을 부모글에 대해 들여쓰기 --%>
									<c:forEach begin="1" end="${board.level}" step="1">
										<span></span>
									</c:forEach>
									<%-- 공백 다음에 자식 글을 표시 --%>
									[답변] <a class="ba" href="${contextPath}/board/viewArticle.do?article_no=${board.article_no}">${board.title}</a>
								</c:when>
								<c:otherwise>
									<a href="${contextPath}/board/viewArticle.do?article_no=${board.article_no}">${board.title}</a>
								</c:otherwise>
							</c:choose>
						</td>
						<td width="15%"><fmt:formatDate value="${board.write_date}"/></td>
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
	</table>
	<p align="center"><a class="ba" href="${contextPath}/board/articleForm.do">글쓰기</a></p>
</body>
</html>