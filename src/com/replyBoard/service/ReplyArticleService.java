package com.replyBoard.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.Date;

import com.board.db.loader.ConnectionProvider;
import com.board.db.util.JdbcUtil;
import com.replyBoard.dao.ArticleDAO;
import com.replyBoard.exception.ArticleNotFoundException;
import com.replyBoard.exception.CannotReplyArticleException;
import com.replyBoard.exception.LastChildAleadyExistsException;
import com.replyBoard.model.Article;
import com.replyBoard.model.ReplyingRequest;

/**
 * 클래스 이름 지을때 작은 부분에서 큰 부분으로~
 * @author pc13
 *
 */
public class ReplyArticleService {
	private static ReplyArticleService instance = new ReplyArticleService();
	
	public static ReplyArticleService getInstance(){
		return instance;
	}
	
	private ReplyArticleService(){}
	
	public Article reply(ReplyingRequest replyingRequest) throws ArticleNotFoundException,CannotReplyArticleException,LastChildAleadyExistsException{
		Connection conn = null;
		
		Article article =replyingRequest.toArticle();
		
		try{
			conn = ConnectionProvider.getInstance().getConnection();
			conn.setAutoCommit(false);
			
			ArticleDAO articleDao = ArticleDAO.getInstance();
			Article parent = articleDao.selectById(conn, replyingRequest.getParentArticleId());
			
			try{
				checkParent(parent,replyingRequest.getParentArticleId());
			} catch(Exception e){
				JdbcUtil.rollback(conn);
				if(e instanceof ArticleNotFoundException){
					throw (ArticleNotFoundException)e;
				} else if(e instanceof CannotReplyArticleException) {
					throw (CannotReplyArticleException)e;
				}else if (e instanceof LastChildAleadyExistsException){
					throw (LastChildAleadyExistsException)e;
				}
			}
			
			String searchMaxSeqNum = parent.getSequenceNumber();
			String searchMinSeqNum = getSearchMinSeqNum(parent);
			
			String lastChildSeq = articleDao.selectLastSequenceNumber(conn, searchMaxSeqNum, searchMinSeqNum);
			
			String sequenceNumber = getSequenceNumber(parent,lastChildSeq);
			
			article.setGroupId(parent.getGroupId());
			article.setSequenceNumber(sequenceNumber);
			article.setPostingDate(new Date());
			
			int articleId = articleDao.insert(conn, article);
			if(articleId == -1){
				throw new RuntimeException("DB 삽입 실패: "+articleId);
			}
			conn.commit();
			
		}catch(SQLException e){
			JdbcUtil.rollback(conn);
			throw new RuntimeException("DB 에러: "+e.getMessage(),e);
		} finally{
			if(conn != null){
				try {
					conn.setAutoCommit(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			JdbcUtil.close(conn);
		}
		return article;
	}
	
	private String getSearchMinSeqNum(Article parent){
		String parentSeqNum = parent.getSequenceNumber();
		DecimalFormat decimalFormat = new DecimalFormat("0000000000000000");
		long ParentSeqLongValue = Long.parseLong(parentSeqNum);
		long searchMinLongValue = 0;
		
		switch(parent.getLevel()){
		case 0:
			searchMinLongValue = ParentSeqLongValue / 1000000L * 1000000L;
			break;
		case 1:
			searchMinLongValue = ParentSeqLongValue / 10000L * 10000L;
			break;
		case 2:
			searchMinLongValue = ParentSeqLongValue / 100L * 100L;
			break;
		
		}
		return decimalFormat.format(searchMinLongValue);
	}

	private String getSequenceNumber(Article parent, String lastChildSeq) throws
	LastChildAleadyExistsException
	{
		long parentSeqLong = Long.parseLong(parent.getSequenceNumber());
		int parentLevel = parent.getLevel();
		
		long decUnit = 0;
		if(parentLevel == 0){
			decUnit = 10000L;
		} else if(parentLevel == 1){
			decUnit = 100L;
		} else if(parentLevel == 2){
			decUnit = 1L;
		}
		
		String sequenceNumber = null;
		DecimalFormat decimalFormat = new DecimalFormat("0000000000000000");
		
		if(lastChildSeq == null){
			sequenceNumber = decimalFormat.format(parentSeqLong - decUnit);
		} else {
			String orderOfLastChildSeq = null;
			if(parentLevel == 0){
				orderOfLastChildSeq = lastChildSeq.substring(10,12)+"9999";
				sequenceNumber = lastChildSeq.substring(0,12)+"9999";
			} else if(parentLevel == 1){
				orderOfLastChildSeq = lastChildSeq.substring(12,14)+"9999";
				sequenceNumber = lastChildSeq.substring(0,14)+"99";
			} else if(parentLevel == 2){
				orderOfLastChildSeq = lastChildSeq.substring(14,16);
				sequenceNumber = lastChildSeq;
			}
			
			if(orderOfLastChildSeq.equals("00")){
				 throw new LastChildAleadyExistsException("마지막 댓글입니다.: "+lastChildSeq);
			}
			long seq = Long.parseLong(sequenceNumber) - decUnit;
			sequenceNumber =decimalFormat.format(seq);
		}
		
		return sequenceNumber;
	}

	private void checkParent(Article parent,int parentId) throws ArticleNotFoundException,
	CannotReplyArticleException
	{
		if(parent ==null){
			throw new ArticleNotFoundException("해당 article이 없음: "+parentId);
		}
		
		int parentLevel = parent.getLevel();
		if(parentLevel == 3){
			throw new CannotReplyArticleException("더이상 댓글 작성 불가: "+parent.getArticleId());
		}
	}
}
