package com.replyBoard.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.replyBoard.exception.ArticleNotFoundException;
import com.replyBoard.exception.CannotReplyArticleException;
import com.replyBoard.exception.LastChildAleadyExistsException;
import com.replyBoard.model.Article;
import com.replyBoard.model.ReplyingRequest;
import com.replyBoard.service.ReplyArticleService;

/**
 * Servlet implementation class ReplyArticleServlet
 */
@WebServlet("/reply")
public class ReplyArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url="reply_form.jsp";
		request.getRequestDispatcher(url).forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String url="";
		
		request.setCharacterEncoding("utf-8");
		
		ReplyingRequest reply = new ReplyingRequest();
		reply.setContent(request.getParameter("content"));
		reply.setParentArticleId(Integer.parseInt(request.getParameter("parentArticleId")));
		reply.setPassword(request.getParameter("password"));
		reply.setTitle(request.getParameter("title"));
		reply.setWriterName(request.getParameter("writerName"));
		
		try {
			Article postedArticle =ReplyArticleService.getInstance().reply(reply);
			url="reply_success.jsp";
			request.setAttribute("postedArticle", postedArticle);
		} catch (Exception e) {
			url="reply_error.jsp";
			request.setAttribute("replyException", e);
		} 
		request.getRequestDispatcher(url).forward(request, response);
	}

}
