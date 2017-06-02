package com.replyBoard.model;

import java.util.Date;

public class Article {
	private int articleId;
	private int groupId;
	private String sequenceNumber;
	private Date postingDate;
	private int readCount;
	private String writerName;
	private String password;
	private String title;
	private String content;

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public Date getPostingDate() {
		return postingDate;
	}

	public void setPostingDate(Date postingDate) {
		this.postingDate = postingDate;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public String getWriterName() {
		return writerName;
	}

	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLevel() {
		if (sequenceNumber == null) {
			return -1;
		} else if (sequenceNumber.length() != 16) {
			return -1;
		} else if (sequenceNumber.endsWith("999999")) {
			return 0;
		} else if (sequenceNumber.endsWith("9999")) {
			return 1;
		} else if (sequenceNumber.endsWith("99")) {
			return 2;
		}
		return 3;
	}

}
