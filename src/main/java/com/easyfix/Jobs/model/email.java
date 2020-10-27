package com.easyfix.Jobs.model;

public class email {
	private int emailId;
    private int clientId;
    private String clientDisplayName;
    private String emailText;
    private int status;
    private String subject;
    private String emailType;
    
    
	public String getClientDisplayName() {
		return clientDisplayName;
	}
	public void setClientDisplayName(String clientDisplayName) {
		this.clientDisplayName = clientDisplayName;
	}
	public String getEmailText() {
		return emailText;
	}
	public void setEmailText(String emailText) {
		this.emailText = emailText;
	}
	public String getEmailType() {
		return emailType;
	}
	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}
	public int getEmailId() {
		return emailId;
	}
	public int getClientId() {
		return clientId;
	}
	
	public int getStatus() {
		return status;
	}
	public String getSubject() {
		return subject;
	}
	public void setEmailId(int emailId) {
		this.emailId = emailId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
    
}
