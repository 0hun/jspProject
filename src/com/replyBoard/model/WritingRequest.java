package com.replyBoard.model;

public class WritingRequest {
	
	private String writerName;
	private String password;
	private String title;
	private String content;
	
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Article toArticle(){
		Article article=new Article();
		article.setWriterName(this.writerName);
		article.setTitle(this.title);
		article.setContent(this.content);
		article.setPassword(this.password);
		
		return article;
	}
	
}




