package com.replyBoard.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;

import com.board.db.loader.ConnectionProvider;
import com.board.db.util.JdbcUtil;
import com.replyBoard.dao.ArticleDAO;
import com.replyBoard.exception.IdGenerationFailedException;
import com.replyBoard.model.Article;
import com.replyBoard.model.WritingRequest;

public class WriteArticleService {
	private static WriteArticleService instance 
						= new WriteArticleService();
	public static WriteArticleService getInstance() {
		return instance;
	}
	private WriteArticleService() {}
	
	public Article write(WritingRequest writingRequest)
			throws IdGenerationFailedException,SQLException {

		Article article = writingRequest.toArticle();		
		
		Connection conn = null;
		
		try {
			conn = ConnectionProvider.getInstance().getConnection();
			conn.setAutoCommit(false);
			
			int groupId = 
					IdGenerator.getInstance().generateNextId("article",conn);

			article.setGroupId(groupId);
			article.setPostingDate(new Date());
			DecimalFormat decimalFormat = new DecimalFormat("0000000000");
			article.setSequenceNumber(
					decimalFormat.format(groupId) + "999999");

			int articleId = ArticleDAO.getInstance().insert(conn, article);
			if (articleId == -1) {
				JdbcUtil.rollback(conn);
				throw new RuntimeException("DB 삽입 실패: " + articleId);
			}
			conn.commit();

			article.setArticleId(articleId);
			return article;
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
				} catch (SQLException e) {
				}
			}
			JdbcUtil.close(conn);
		}
	}
}
