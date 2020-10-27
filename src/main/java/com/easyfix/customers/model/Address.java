package com.easyfix.customers.model;

import org.codehaus.jackson.annotate.JsonProperty;

import com.easyfix.settings.model.City;

public class Address {
	
	private int addressId;
	private int customerId;
	private String address;
	private String pinCode;
	
	
	@JsonProperty("city")
	private City cityObj;
	
	@JsonProperty("gps")
	private String gpsLocation;
	
	@JsonProperty("insertedTimestamp")
	private String insertDate;
	@JsonProperty("updatedTimestamp")
	private String updateDate;
	private int updatedBy;
	
	private Integer addressType;
	private Integer createdBy;
	
	/*public Address(){
		
	}	
	*/
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public City getCityObj() {
		return cityObj;
	}
	public void setCityObj(City cityObj) {
		this.cityObj = cityObj;
	}
	public String getGpsLocation() {
		return gpsLocation;
	}
	public void setGpsLocation(String gpsLocation) {
		this.gpsLocation = gpsLocation;
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

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public Integer getAddressType() {
		return addressType;
	}
	public void setAddressType(Integer addressType) {
		this.addressType = addressType;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	
	

}
