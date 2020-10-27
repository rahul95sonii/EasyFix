package com.easyfix.Jobs.model;

public class Sms {
	private int smsId;
	private int clientId;
	private String jobStage;
	private int isExclusive;
	private int receiver;
	private int sender;
	/*private int sendToCustomer;
	private int sendToClient;
	private int sendToEfr;
	private int sendToEf;*/
	private String sms;
	private int status;
	private String updateDateTime;
	private int updatedBy;
	private String insertedTimeStamp; 
	
	
	
	
	
	public int getReceiver() {
		return receiver;
	}
	public void setReceiver(int receiver) {
		this.receiver = receiver;
	}
	public int getSender() {
		return sender;
	}
	public void setSender(int sender) {
		this.sender = sender;
	}
	public String getInsertedTimeStamp() {
		return insertedTimeStamp;
	}
	public void setInsertedTimeStamp(String insertedTimeStamp) {
		this.insertedTimeStamp = insertedTimeStamp;
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
	
	
	public String getSms() {
		return sms;
	}
	public void setSms(String sms) {
		this.sms = sms;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(String updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public String getJobStage() {
		return jobStage;
	}
	public void setJobStage(String jobStage) {
		this.jobStage = jobStage;
	}
	public int getIsExclusive() {
		return isExclusive;
	}
	public void setIsExclusive(int isExclusive) {
		this.isExclusive = isExclusive;
	}
	
	
}
