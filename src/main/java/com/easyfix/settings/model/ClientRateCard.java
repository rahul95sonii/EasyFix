package com.easyfix.settings.model;

import com.easyfix.settings.model.ServiceType;

public class ClientRateCard {
	
	private int clientRateCardId;
	private int serviceTypeId;
	private String clientRateCardName;
	private int clientId;
	
	private ServiceType serviceTypeObj;
	
	private String insertDate;
	private String updateDate;
	private int updatedBy;
	
	public ClientRateCard(){
		
	}

	public int getClientRateCardId() {
		return clientRateCardId;
	}

	public void setClientRateCardId(int clientRateCardId) {
		this.clientRateCardId = clientRateCardId;
	}

	public int getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(int serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}

	public String getClientRateCardName() {
		return clientRateCardName;
	}

	public void setClientRateCardName(String clientRateCardName) {
		this.clientRateCardName = clientRateCardName;
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

	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

}
