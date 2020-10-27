package com.easyfix.util.triggers.model;

public class emailContentForPostJob {

	private Integer messageId;
	private String emailContent;
	private String subject;
	private String from;
	
	
	
	
	
	
	public Integer getMessageId() {
		return messageId;
	}
	public String getEmailContent() {
		return emailContent;
	}
	public String getSubject() {
		return subject;
	}
	public String getFrom() {
		return from;
	}
	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	@Override
	public String toString() {
		return "emailContentForPostJob [messageId=" + messageId
				+ ", emailContent=" + emailContent + ", subject=" + subject
				+ ", from=" + from + "]";
	}
	
	
}
