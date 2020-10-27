package com.easyfix.util.triggers.model;

import java.sql.Blob;



public class Email
{
	private int emailId;
	private int clientId;
	private Blob email_text;
	private int status;
	private String displayName;
	private String subject;

	public int getClientId()
	{
		return clientId;
	}

	public void setClientId(int clientId)
	{
		this.clientId = clientId;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public int getEmailId()
	{
		return emailId;
	}

	public void setEmailId(int emailId)
	{
		this.emailId = emailId;
	}

	public Blob getEmail_text()
	{
		return email_text;
	}

	public void setEmail_text(Blob email_text)
	{
		this.email_text = email_text;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

}
