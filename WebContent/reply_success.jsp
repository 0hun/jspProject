<%@ page contentType="text/html; charset=euc-kr" %>
<html>
<head><title>답변글 작성</title></head>
<body>
답변글을 등록했습니다.
<br/>
작성자: ${postedArticle.writerName }<br/>
제목: ${postedArticle.title }<br/>
내용: ${postedArticle.content}<br/><br/>


<a href="articleList?p=${param.p}">목록보기</a>
<a href="read?articleId=${postedArticle.articleId}&p=${param.p}">게시글 읽기</a>
</body>
</html>
