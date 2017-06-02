package com.replyBoard.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.board.db.loader.ConnectionProvider;
import com.board.db.util.JdbcUtil;
import com.replyBoard.dao.ArticleDAO;
import com.replyBoard.exception.ArticleNotFoundException;
import com.replyBoard.exception.InvalidPasswordException;
import com.replyBoard.model.DeleteRequest;


public class DeleteArticleService {

	private static DeleteArticleService instance = new DeleteArticleService();

	public static DeleteArticleService getInstance() {
		return instance;
	}

	private DeleteArticleService() {
	}

	public void deleteArticle(DeleteRequest deleteRequest)
			throws ArticleNotFoundException, InvalidPasswordException {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getInstance().getConnection();
			conn.setAutoCommit(false);

			ArticleCheckHelper checkHelper = new ArticleCheckHelper();
			checkHelper.checkExistsAndPassword(conn, deleteRequest
					.getArticleId(), deleteRequest.getPassword());
			
			ArticleDAO articleDao = ArticleDAO.getInstance();
			articleDao.delete(conn, deleteRequest.getArticleId());

			conn.commit();
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException(e);
		} catch (ArticleNotFoundException e) {
			JdbcUtil.rollback(conn);
			throw e;
		} catch (InvalidPasswordException e) {
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
