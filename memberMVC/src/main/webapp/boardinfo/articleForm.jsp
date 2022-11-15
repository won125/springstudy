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
<title>게시글쓰기</title>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<style type="text/css">
	.center {
		text-align: center;
	}
	.right {
		text-align: right;
	}
</style>
<script type="text/javascript">
	function readImage(input) {
		if(input.files && input.files[0]){
			let reader = new FileReader();
			reader.onload = function (event) {
				$('#preview').attr('src',event.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
	
	//다른 액션을 submit
	function toList(obj) {
		obj.action="${contextPath}/board/listArticles.do";
		obj.submit();
	}
</script>
</head>
<body>
	<h2 align="center">글쓰기</h2>
	<form action="${contextPath}/board/addArticle.do" method="post" enctype="multipart/form-data">
		<table class="center">
			<tr >
				<td align="right" width="150" align="center" bgcolor="#ff9933">글제목 : </td>
				<td colspan="2"><input type="text" size=50 name="title"></td>
			</tr>
			<tr>
				<td align="right" width="150" align="center" bgcolor="#ff9933">글내용 : </td>
				<td colspan="2"><textarea rows="10" cols="50" maxlength="4000" name="content"></textarea></td>
			</tr>
			<tr>
				<td align="center" width="150" align="center" bgcolor="#ff9933">이미지 파일 첨부 : </td>
				<td><input type="file" name="image_filename" onchange="readImage(this)"></td>
				<td><img alt="이미지 미리보기" id="preview" src="#" width=200 height=200/></td>
			</tr>
			<tr>
				<td align="center" colspan="3" >
					<input type="submit" value="글쓰기">
					<input type="button" onclick="toList(this.form)" value="목록보기">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>