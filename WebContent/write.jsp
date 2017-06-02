<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	게시글을 등록했습니다.<br/>
	작성자 : ${postedArticle.writerName }<br/>
	제목 : ${postedArticle.title }<br/>
	내용 : ${postedArticle.content }<br/>
	<a href="<c:url value="/articleList" />">목록보기</a>
	<a href="<c:url value="/read?articleId=${postedArticleId }&get=true" />">작성글보기</a>
	
</body>
</html>