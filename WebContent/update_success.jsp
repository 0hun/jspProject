<%@ page contentType="text/html; charset=euc-kr" %>
<html>
<head><title>글 수정</title></head>
<body>
글을 수정했습니다.
<br/>
<a href="articleList?p=${param.p}">목록보기</a>
<a href="read?articleId=${updatedArticle.articleId}&p=${param.p}">게시글 읽기</a>
</body>
</html>
