package com.easyfix.settings.model;

import com.easyfix.settings.model.ServiceType;

public class RetailRateCard {
	
	private int retailRateCardId;
	private int serviceTypeId;
	private String retailRateCardName;
	private int retailRateCardPrice;
	
	private ServiceType serviceTypeObj;
	
	private String insertDate;
	private String updateDate;
	private int updatedBy;
	
	public RetailRateCard(){
		
	}

	public int getRetailRateCardId() {
		return retailRateCardId;
	}

	public void setRetailRateCardId(int retailRateCardId) {
		this.retailRateCardId = retailRateCardId;
	}

	public int getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(int serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public String getRetailRateCardName() {
		return retailRateCardName;
	}

	public void setRetailRateCardName(String retailRateCardName) {
		this.retailRateCardName = retailRateCardName;
	}

	public int getRetailRateCardPrice() {
		return retailRateCardPrice;
	}

	public void setRetailRateCardPrice(int retailRateCardPrice) {
		this.retailRateCardPrice = retailRateCardPrice;
	}

	public ServiceType getServiceTypeObj() {
		return serviceTypeObj;
	}

	public void setServiceTypeObj(ServiceType serviceTypeObj) {
		this.serviceTypeObj = serviceTypeObj;
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

}
