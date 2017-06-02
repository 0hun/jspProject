package com.replyBoard.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.replyBoard.model.ArticleListModel;
import com.replyBoard.service.ListArticleService;


@WebServlet("/articleList")
public class ListArticleServlet extends HttpServlet {

	public static final int COUNT_NUMBERS_PAGE = 2;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String url="list_view.jsp";
	
		String pageNumberString = request.getParameter("p");
		int pageNumber = 1;
		if (pageNumberString != null && pageNumberString.length() > 0) {
			pageNumber = Integer.parseInt(pageNumberString);
		}
		ListArticleService listSerivce = ListArticleService.getInstance();
		ArticleListModel articleListModel = 
				listSerivce.getArticleList(pageNumber);
		request.setAttribute("listModel", articleListModel);
		
		if (articleListModel.getTotalPageCount() > 0) {
			int beginPageNumber = 
				(articleListModel.getRequestPage() - 1) / COUNT_NUMBERS_PAGE * COUNT_NUMBERS_PAGE + 1;
			int endPageNumber = beginPageNumber + COUNT_NUMBERS_PAGE-1;
			if (endPageNumber > articleListModel.getTotalPageCount()) {
				endPageNumber = articleListModel.getTotalPageCount();
			}
			request.setAttribute("beginPage", beginPageNumber);
			request.setAttribute("endPage", endPageNumber);
		}
		request.getRequestDispatcher(url).forward(request, response); 		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
