<%@ page contentType="text/html; charset=euc-kr" %>
<html>
<head><title>�亯�� �ۼ�</title></head>
<body>
�亯���� ����߽��ϴ�.
<br/>
�ۼ���: ${postedArticle.writerName }<br/>
����: ${postedArticle.title }<br/>
����: ${postedArticle.content}<br/><br/>


<a href="articleList?p=${param.p}">��Ϻ���</a>
<a href="read?articleId=${postedArticle.articleId}&p=${param.p}">�Խñ� �б�</a>
</body>
</html>
