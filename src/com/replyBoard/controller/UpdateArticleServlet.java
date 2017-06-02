package com.replyBoard.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.replyBoard.model.Article;
import com.replyBoard.model.UpdateRequest;
import com.replyBoard.service.ReadArticleService;
import com.replyBoard.service.UpdateArticleService;

@WebServlet("/update")
public class UpdateArticleServlet extends HttpServlet {
   
	private static final long serialVersionUID = 1L;

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String url="reply_form.jsp";
      int articleId=Integer.parseInt(request.getParameter("articleId"));
      try{
         Article article=ReadArticleService.getInstance().getArticle(articleId);
      }catch(Exception e){
         url="article_not_found.jsp";
      }
      request.getRequestDispatcher(url).forward(request, response);
   }
   
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    request.setCharacterEncoding("utf-8");
    String url = "update_success.jsp";
    UpdateRequest updateRequest = new UpdateRequest();
    updateRequest.setArticleId(Integer.parseInt(request.getParameter("articleId")));
    updateRequest.setContent(request.getParameter("content"));
    updateRequest.setPassword(request.getParameter("password"));
    updateRequest.setTitle(request.getParameter("title"));
    try{
       Article article = UpdateArticleService.getInstance().update(updateRequest);
       request.setAttribute("updatedArticle", article);
    }catch(Exception e){
       url="update_error.jsp";
       request.setAttribute("updateException", e);
    }
    request.getRequestDispatcher(url).forward(request, response);
 }

}