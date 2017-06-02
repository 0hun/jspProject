package com.board.db.loader;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class ConnectionProviderInit extends HttpServlet {

	@Override
	public void init(ServletConfig config) throws ServletException {
		String url=config.getInitParameter("jocl");
		if(url!=null){
			ConnectionProvider.getInstance().setUrl(url);
			System.out.println("ConnectionProviderInit:"+url);
		}
	}
	
}
