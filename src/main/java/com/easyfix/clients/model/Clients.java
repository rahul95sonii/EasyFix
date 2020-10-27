package com.easyfix.clients.model;

import java.io.File;
import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.settings.model.City;
import com.easyfix.settings.model.ClientRateCard;
import com.easyfix.settings.model.ServiceType;
import com.easyfix.user.model.User;

public class Clients implements Serializable{
	
	private int clientId;
	private String clientName;
	private String tanNumber;
	private String clientEmail;
	private String clientAddress;
	private int cityId;
	private String clientPincode;

	private int isActive;
	private City cityObj;
	private int paidBy;
	private int collectedBy;
	private int travelDist;
	private int billingCycle;
	
	private int clientServiceId;
	private int rateCardId;
	private int serviceTypeId;
	private ServiceType serviceTypeObj;
	private ClientRateCard clientRateCardObj;
	
	
	private Easyfixers easyfixerObj; //for mapping client,servicetype and easyfixer.
	private int easyfixerId; //for mapping client,servicetype and easyfixer.
	private int mappingId;    //for mapping client,servicetype and easyfixer.
	private int mappingStatus;    //for mapping client,servicetype and easyfixer.
	private String easyfixerIds; //for mapping client,servicetype and easyfixer.
	
	private int chargeType;
	private String totalCharge;
	private String easyfixDirectFixed;
	private String easyfixDirectVariable;
	private String overheadFixed;
	private String overheadVariable;
	private String clientFixed;
	private String clientVariable;
	private int serviceStatus;
	
	private int invoiceRaise;
	private String invoiceCycle;
	private String invoiceName;
	private String invoiceStartDate;

	@JsonProperty("insertDate")
	private String insertDate;
	@JsonProperty("updateDate")
	private String updateDate;

	
	@JsonProperty("updatedBy")
	private User updatedBy;
	
	@JsonProperty("insertedBy")
	private User insertedBy;
	
	private ClientWebsite clientWebsite;
	
	
	public ClientWebsite getClientWebsite() {
		return clientWebsite;
	}
	public void setClientWebsite(ClientWebsite clientWebsite) {
		this.clientWebsite = clientWebsite;
	}
	public User getUpdatedBy() {
		return updatedBy;
	}
	public User getInsertedBy() {
		return insertedBy;
	}
	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}
	public void setInsertedBy(User insertedBy) {
		this.insertedBy = insertedBy;
	}
	
//	private int updatedBy;
//	private int insertedBy;
	
	private String rcUploadByExcelFileName; // for excel upload
	private File rcUploadByExcelFile; // for excel upload
	
	public int getClientId() {
		return clientId;
	}
	public String getRcUploadByExcelFileName() {
		return rcUploadByExcelFileName;
	}
	public File getRcUploadByExcelFile() {
		return rcUploadByExcelFile;
	}
	public void setRcUploadByExcelFileName(String rcUploadByExcelFileName) {
		this.rcUploadByExcelFileName = rcUploadByExcelFileName;
	}
	public void setRcUploadByExcelFile(File rcUploadByExcelFile) {
		this.rcUploadByExcelFile = rcUploadByExcelFile;
	}
	public String getClientName() {
		return clientName;
	}
	public String getTanNumber() {
		return tanNumber;
	}
	public String getClientEmail() {
		return clientEmail;
	}
	public String getClientAddress() {
		return clientAddress;
	}
	public int getCityId() {
		return cityId;
	}
	public String getClientPincode() {
		return clientPincode;
	}
	public int getIsActive() {
		return isActive;
	}
	public City getCityObj() {
		return cityObj;
	}
	public int getClientServiceId() {
		return clientServiceId;
	}
	public int getRateCardId() {
		return rateCardId;
	}
	public int getServiceTypeId() {
		return serviceTypeId;
	}
	public ServiceType getServiceTypeObj() {
		return serviceTypeObj;
	}
	public ClientRateCard getClientRateCardObj() {
		return clientRateCardObj;
	}
	public int getChargeType() {
		return chargeType;
	}
	public String getTotalCharge() {
		return totalCharge;
	}
	public String getEasyfixDirectFixed() {
		return easyfixDirectFixed;
	}
	public String getEasyfixDirectVariable() {
		return easyfixDirectVariable;
	}
	public String getOverheadFixed() {
		return overheadFixed;
	}
	public String getOverheadVariable() {
		return overheadVariable;
	}
	public String getClientFixed() {
		return clientFixed;
	}
	public String getClientVariable() {
		return clientVariable;
	}
	public int getServiceStatus() {
		return serviceStatus;
	}
	public String getInsertDate() {
		return insertDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
//	public int getUpdatedBy() {
//		return updatedBy;
//	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public void setTanNumber(String tanNumber) {
		this.tanNumber = tanNumber;
	}
	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}
	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public void setClientPincode(String clientPincode) {
		this.clientPincode = clientPincode;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public void setCityObj(City cityObj) {
		this.cityObj = cityObj;
	}
	public void setClientServiceId(int clientServiceId) {
		this.clientServiceId = clientServiceId;
	}
	public void setRateCardId(int rateCardId) {
		this.rateCardId = rateCardId;
	}
	public void setServiceTypeId(int serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	public void setServiceTypeObj(ServiceType serviceTypeObj) {
		this.serviceTypeObj = serviceTypeObj;
	}
	public void setClientRateCardObj(ClientRateCard clientRateCardObj) {
		this.clientRateCardObj = clientRateCardObj;
	}
	public void setChargeType(int chargeType) {
		this.chargeType = chargeType;
	}
	public void setTotalCharge(String totalCharge) {
		this.totalCharge = totalCharge;
	}

	public void setEasyfixDirectFixed(String easyfixDirectFixed) {
		this.easyfixDirectFixed = easyfixDirectFixed;
	}
	public void setEasyfixDirectVariable(String easyfixDirectVariable) {
		this.easyfixDirectVariable = easyfixDirectVariable;
	}
	public void setOverheadFixed(String overheadFixed) {
		this.overheadFixed = overheadFixed;
	}
	public void setOverheadVariable(String overheadVariable) {
		this.overheadVariable = overheadVariable;
	}
	public void setClientFixed(String clientFixed) {
		this.clientFixed = clientFixed;
	}
	public void setClientVariable(String clientVariable) {
		this.clientVariable = clientVariable;
	}
	public void setServiceStatus(int serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
//	public void setUpdatedBy(int updatedBy) {
//		this.updatedBy = updatedBy;
//	}

	public Easyfixers getEasyfixerObj() {
		return easyfixerObj;
	}
	public void setEasyfixerObj(Easyfixers easyfixerObj) {
		this.easyfixerObj = easyfixerObj;
	}
	
	public int getEasyfixerId() {
		return easyfixerId;
	}
	public void setEasyfixerId(int easyfixerId) {
		this.easyfixerId = easyfixerId;
	}
	public int getMappingId() {
		return mappingId;
	}
	public void setMappingId(int mappingId) {
		this.mappingId = mappingId;
	}
	public int getMappingStatus() {
		return mappingStatus;
	}
	public void setMappingStatus(int mappingStatus) {
		this.mappingStatus = mappingStatus;
	}

	public String getEasyfixerIds() {
		return easyfixerIds;
	}
	public void setEasyfixerIds(String easyfixerIds) {
		this.easyfixerIds = easyfixerIds;
	}
	public int getPaidBy() {
		return paidBy;
	}
	public int getCollectedBy() {
		return collectedBy;
	}
	public int getTravelDist() {
		return travelDist;
	}
	public void setPaidBy(int paidBy) {
		this.paidBy = paidBy;
	}
	public void setCollectedBy(int collectedBy) {
		this.collectedBy = collectedBy;
	}
	public void setTravelDist(int travelDist) {
		this.travelDist = travelDist;
	}
	public int getBillingCycle() {
		return billingCycle;
	}
	public void setBillingCycle(int billingCycle) {
		this.billingCycle = billingCycle;
	}
	public int getInvoiceRaise() {
		return invoiceRaise;
	}
	public String getInvoiceCycle() {
		return invoiceCycle;
	}
	public String getInvoiceName() {
		return invoiceName;
	}
	public String getInvoiceStartDate() {
		return invoiceStartDate;
	}
	public void setInvoiceRaise(int invoiceRaise) {
		this.invoiceRaise = invoiceRaise;
	}
	public void setInvoiceCycle(String invoiceCycle) {
		this.invoiceCycle = invoiceCycle;
	}
	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}
	public void setInvoiceStartDate(String invoiceStartDate) {
		this.invoiceStartDate = invoiceStartDate;
	}
	
	
	
}
