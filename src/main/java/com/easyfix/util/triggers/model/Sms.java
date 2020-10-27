package com.easyfix.util.triggers.model;

public class Sms {
	private int smsId;
	private int clientId;
	private String smsText;
	private int status; 
	private String clientNameForSms;
	
	
	
	public String getClientNameForSms() {
		return clientNameForSms;
	}
	public void setClientNameForSms(String clientNameForSms) {
		this.clientNameForSms = clientNameForSms;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getSmsId() {
		return smsId;
	}
	public void setSmsId(int smsId) {
		this.smsId = smsId;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getSmsText() {
		return smsText;
	}
	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}
	
}
