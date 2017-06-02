package com.replyBoard.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.replyBoard.model.DeleteRequest;
import com.replyBoard.service.DeleteArticleService;

@WebServlet("/delete")
public class DeleteArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String url = "delete_form.jsp";
		request.getRequestDispatcher(url).forward(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String url="delete_success.jsp";
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		DeleteRequest delReq = new DeleteRequest();
		delReq.setArticleId(Integer.parseInt(request.getParameter("articleId")));
		delReq.setPassword(request.getParameter("password"));
		
		try{
			DeleteArticleService.getInstance().deleteArticle(delReq);
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('해당 글을 삭제하였습니다.');");
			out.println("location.href='articleList'");
			out.println("</script>");
			
			//			response.sendRedirect("articleList");
		} catch(Exception e){
			url="delete_error.jsp";
			request.setAttribute("deleteException", e);
			request.getRequestDispatcher(url).forward(request, response);
		}
		
	}

}
