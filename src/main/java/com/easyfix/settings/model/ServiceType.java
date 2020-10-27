package com.easyfix.settings.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class ServiceType {

	@JsonProperty("service_type_id")
	private int serviceTypeId;	
	
	@JsonProperty("service_type_name")
	private String serviceTypeName;
	private String serviceTypeDesc;
	private String serviceTypeStatus;
	private String insertDate;
	@JsonProperty("display")
	private int display;
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

	private String updateDate;
	private int updatedBy;
	
	
	public ServiceType(){
		
	}
	
	public int getServiceTypeId() {
		return serviceTypeId;
	}
	
	public void setServiceTypeId(int serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	
	public String getServiceTypeDesc() {
		return serviceTypeDesc;
	}
	
	public void setServiceTypeDesc(String serviceTypeDesc) {
		this.serviceTypeDesc = serviceTypeDesc;
	}
	
	public String getServiceTypeStatus() {
		return serviceTypeStatus;
	}
	
	public void setServiceTypeStatus(String serviceTypeStatus) {
		this.serviceTypeStatus = serviceTypeStatus;
	}

	public int getDisplay() {
		return display;
	}

	public void setDisplay(int display) {
		this.display = display;
	}
	
	
	
}
