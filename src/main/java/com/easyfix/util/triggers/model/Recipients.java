package com.easyfix.util.triggers.model;

import java.util.List;

public class Recipients {
	
	private int id;
	private int clientId;
	private String contactName;
	private String contactNo;
	private String email;
	private String frequency;
	private int status;
	private String emailCC;
	private String[] emaillCCList;
	
	
	
	public String[] getEmaillCCList() {
		return emaillCCList;
	}
	//custom
	private void setEmaillCCList() {
		if(emailCC!=null && !emailCC.equals("") && emailCC.length()>5 && emailCC.trim()!=null){
			System.out.println("here....................");
			this.emaillCCList = emailCC.split(",");
		}
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContactNo() {
		return contactNo;
	}
	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public String getEmailCC() {
		return emailCC;
	}
	public void setEmailCC(String emailCC) {
		this.emailCC = emailCC;
		setEmaillCCList();
	}
	
	
}
