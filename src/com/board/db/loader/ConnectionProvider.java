package com.board.db.loader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class ConnectionProvider{	
	
	private static ConnectionProvider instance=new ConnectionProvider();
	private ConnectionProvider(){}
	public static ConnectionProvider getInstance(){
		return instance;
	}
	
	private String url=null;
	
	public void setUrl(String url){
		this.url=url;
	}	
	
	public Connection getConnection() throws SQLException{
		String url="jdbc:apache:commons:dbcp:/com/board/db/loader/"
					+this.url;
		Connection conn=DriverManager.getConnection(url);
		return conn;
	}
}
