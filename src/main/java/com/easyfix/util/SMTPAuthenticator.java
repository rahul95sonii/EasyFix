package com.easyfix.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {
	private String userName;
	private String password;
	
	public SMTPAuthenticator(String userName, String password)
	{
		this.userName = userName;
		this.password = password;
	}

	public PasswordAuthentication getPasswordAuthentication()
	{
		return new PasswordAuthentication(userName, password);
	}
}