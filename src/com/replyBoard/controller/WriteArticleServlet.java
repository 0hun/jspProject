package com.replyBoard.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.replyBoard.exception.IdGenerationFailedException;
import com.replyBoard.model.Article;
import com.replyBoard.model.WritingRequest;
import com.replyBoard.service.WriteArticleService;

@WebServlet("/write")
public class WriteArticleServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url="writeForm.jsp";
		request.getRequestDispatcher(url).forward(request, response);		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		
		String url="/write.jsp";
		
		WritingRequest writingReq=new WritingRequest();
		writingReq.setContent(request.getParameter("content"));
		writingReq.setPassword(request.getParameter("password"));
		writingReq.setTitle(request.getParameter("title"));
		writingReq.setWriterName(request.getParameter("writerName"));
		
		Article postedArticle=null;
		try {
			
				postedArticle=
						 WriteArticleService.getInstance().write(writingReq);
			
		} catch (Exception e) {			
			e.printStackTrace();
		} 
		
		request.setAttribute("postedArticle", postedArticle);
		request.getRequestDispatcher(url).forward(request, response);
	}

}



