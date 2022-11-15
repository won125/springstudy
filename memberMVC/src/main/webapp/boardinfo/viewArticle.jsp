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
<title>글 상세 보기</title>
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	function readImage(input) { // 이미지 출력 함수
		if(input.files && input.files[0]){
			let reader = new FileReader();
			reader.onload = function () {
				$('#preview').attr('src',event.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
	function toList(obj) { //게시글 목록으로 돌가가는 함수
		obj.action = "${contextPath}/board/listArticles.do";
		obj.submit();
	}
	function fn_enable(obj) { //수정메뉴 출력 함수
		document.getElementById("id_title").disabled=false;
		document.getElementById("id_content").disabled=false;
		let img_name = document.getElementById("id_image_filename");
		if(img_name != null){
			document.getElementById("id_image_filename").disabled=false;
		}
		document.getElementById("tr_btn_modify").style.display="table-cell";
		document.getElementById("tr_btn").style.display="none";
	}
	//게시글 수정 함수
	function fn_modify_article(obj) { 
		obj.action = "${contextPath}/board/modArticle.do";
		obj.submit();
	}
	function fn_remove_article(url,article_no) { //게시글 삭제 함수
		let form = document.createElement("form");
		form.setAttribute("method","post");
		form.setAttribute("action",url);
		let article_no_input = document.createElement("input");
		article_no_input.setAttribute("type","hidden");
		article_no_input.setAttribute("name","article_no");
		article_no_input.setAttribute("value",article_no);
		form.appendChild(article_no_input);
		document.body.appendChild(form);
		form.submit();
	}
	function fn_reply_form(url,parent_no) {
		let form = document.createElement("form");
		form.setAttribute("method","post");
		form.setAttribute("action",url);
		let parent_no_input = document.createElement("input");
		parent_no_input.setAttribute("type","hidden");
		parent_no_input.setAttribute("name","parent_no");
		parent_no_input.setAttribute("value",parent_no);
		form.appendChild(parent_no_input);
		document.body.appendChild(form);
		form.submit();
	}
</script>
<style type="text/css">
	#tr_btn_modify{
		display: none;
	}
</style>
</head>
<body>
	<form action="${contextPath}" name="frmArticle" method="post" enctype="multipart/form-data">
		<table align="center">
			<tr>
				<td width="150" align="center" bgcolor="#ff9933">글번호</td>
				<td><input type="text" value="${article.article_no}" disabled="true"></td>
				<input type="hidden" name="article_no" value="${article.article_no}">
			</tr>
			<tr>
				<td width="150" align="center" bgcolor="#ff9933">글쓴이</td>
				<td><input type="text" value="${article.id}" name="writer" disabled="true"></td>
				
			</tr>
			<tr>
				<td width="150" align="center" bgcolor="#ff9933">작성일</td>
				<td><input type="text" value="<fmt:formatDate value='${article.write_date}'/>" disabled="true"></td>
			</tr>
			<tr>
				<td width="150" align="center" bgcolor="#ff9933">제목</td>
				<td><input type="text" value="${article.title}" id="id_title" name="title" disabled></td>
			</tr>
			<tr>
				<td width="150" align="center" bgcolor="#ff9933">내용</td>
				<td>
					<textarea rows="20" cols="60" id="id_content" name="content" disabled>${article.content}</textarea> 
				</td>
			</tr>
			<c:if test="${!empty article.image_filename && article.image_filename != 'null'}">
				<tr>
					<td width="150" rowspan="2" align="center" bgcolor="#ff9933">이미지</td>
					<input type="hidden" name="originalFilename" value="${article.image_filename}">
					<td>
						<img alt="이미지" src="${contextPath}/download.do?article_no=${article.article_no}&image_filename=${article.image_filename}" id="preview">
					</td>
				</tr>
				<tr>
				<td>
					<input = type="file" name="image_filename" id="id_image_filename" onchange="readImage(this)" disabled>
				</td>
			</tr>
			</c:if>
			<tr >
				<td colspan="3" align="center" id="tr_btn">
					<input type="button"  value="수정하기" onclick="fn_enable(frmArticle)">
					<input type="button"  value="삭제하기" onclick="fn_remove_article('${contextPath}/board/removeArticle.do',${article.article_no})">
					<input type="button"  value="목 록" onclick="toList(frmArticle)">
					<input type="button"  value="답글하기" onclick="fn_reply_form('${contextPath}/board/replyForm.do',${article.article_no})">
				</td>
				<td colspan="3" align="center" id="tr_btn_modify">
					<input type="button" value="수정 반영하기" onclick="fn_modify_article(frmArticle)">
					<input type="button" value="취소" onclick="toList(frmArticle)">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>