package com.replyBoard.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.board.db.loader.ConnectionProvider;
import com.board.db.util.JdbcUtil;
import com.replyBoard.dao.ArticleDAO;
import com.replyBoard.exception.ArticleNotFoundException;
import com.replyBoard.model.Article;


public class ReadArticleService {
	private static ReadArticleService instance=new ReadArticleService();
	
	public static ReadArticleService getInstance(){
		return instance;
	}
	
	private ReadArticleService(){}
	
	public Article readArticle(int articleId)throws ArticleNotFoundException, SQLException{
		return selectArticle(articleId,true);
	}
	
	public Article getArticle(int articleId)throws ArticleNotFoundException, SQLException{
		return selectArticle(articleId,false);
	}
	
	private Article selectArticle(int articleId,boolean increaseCount)throws SQLException,ArticleNotFoundException{
		Connection conn=null;
		try {
			conn=ConnectionProvider.getInstance().getConnection();
			ArticleDAO articleDao=ArticleDAO.getInstance();
			Article article=articleDao.selectById(conn, articleId);
			if(article==null){
				throw new ArticleNotFoundException("게시글이 존재하지 않음: "+articleId);
			}
			if(increaseCount){
				articleDao.increaseReadCount(conn, articleId);
				article.setReadCount(articleId);
			}
			return article;
		} catch (SQLException e) {
			throw new RuntimeException("DB 에러: "+e.getMessage(),e);
		}finally{
			JdbcUtil.close(conn);
		}
	}
}
