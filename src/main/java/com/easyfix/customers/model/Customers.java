package com.easyfix.customers.model;

import org.codehaus.jackson.annotate.JsonProperty;

import com.easyfix.Jobs.model.Jobs;


public class Customers {
	@JsonProperty("id")
	private int customerId;
	
	@JsonProperty("mobile")
	private String customerMobNo;
	
	@JsonProperty("name")
	private String customerName;
	private String customerEmail;
	private Address addressObj;
	
	@JsonProperty("insertedTimestamp")
	private String insertDate;
	@JsonProperty("updatedTimestamp")
	private String updateDate;
	
	private int updatedBy;
	private int createdBy;
	private int isActive;	

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getCustomerMobNo() {
		return customerMobNo;
	}

	public void setCustomerMobNo(String customerMobNo) {
		this.customerMobNo = customerMobNo;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}


	public Address getAddressObj() {
		return addressObj;
	}

	public void setAddressObj(Address addressObj) {
		this.addressObj = addressObj;
	}

	public String getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	
	

}
