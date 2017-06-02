package com.replyBoard.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.board.db.loader.ConnectionProvider;
import com.board.db.util.JdbcUtil;
import com.replyBoard.dao.ArticleDAO;
import com.replyBoard.exception.ArticleNotFoundException;
import com.replyBoard.exception.InvalidPasswordException;
import com.replyBoard.model.Article;
import com.replyBoard.model.UpdateRequest;


public class UpdateArticleService {

	private static UpdateArticleService instance = new UpdateArticleService();

	public static UpdateArticleService getInstance() {
		return instance;
	}

	private UpdateArticleService() {
	}

	public Article update(UpdateRequest updateRequest)
			throws ArticleNotFoundException, InvalidPasswordException {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getInstance().getConnection();
			conn.setAutoCommit(false);

			ArticleCheckHelper checkHelper = new ArticleCheckHelper();
			checkHelper.checkExistsAndPassword(conn, updateRequest
					.getArticleId(), updateRequest.getPassword());

			Article updatedArticle = new Article();
			updatedArticle.setArticleId(updateRequest.getArticleId());
			updatedArticle.setTitle(updateRequest.getTitle());
			updatedArticle.setContent(updateRequest.getContent());

			ArticleDAO articleDao = ArticleDAO.getInstance();
			int updateCount = articleDao.update(conn, updatedArticle);
			if (updateCount == 0) {
				throw new ArticleNotFoundException(
				"�Խñ��� �������� ����: " + updateRequest.getArticleId());
			}

			Article article = articleDao.selectById(conn, updateRequest
					.getArticleId());

			conn.commit();

			return article;
		} catch (SQLException e) {
			JdbcUtil.rollback(conn);
			throw new RuntimeException("DB ����: " + e.getMessage(), e);
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
				JdbcUtil.close(conn);
			}
		}
	}

}
