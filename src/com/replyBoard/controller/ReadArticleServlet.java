package com.replyBoard.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.replyBoard.exception.ArticleNotFoundException;
import com.replyBoard.model.Article;
import com.replyBoard.service.ReadArticleService;

@WebServlet("/read")
public class ReadArticleServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean get=Boolean.parseBoolean(request.getParameter("get"));
		
		int articleId=Integer.parseInt(request.getParameter("articleId"));
		String viewPage=null;
		Article article=null;
		try{
			if(get){
				article=ReadArticleService.getInstance().getArticle(articleId);
			}else{
				article=ReadArticleService.getInstance().readArticle(articleId);
			}
			viewPage="/read_view.jsp";
		}catch(SQLException e){
			e.printStackTrace();
			//viewPage="/sql_error.jsp";
		}catch(ArticleNotFoundException e){
			e.printStackTrace();
			//viewPage="/article_not_found.jsp";
		}
		request.setAttribute("article", article);
		request.getRequestDispatcher(viewPage).forward(request, response);
	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
