package com.easyfix.Jobs.model;

public class JobSelectedServices {
	
	private int jobServiceId;
	private int jobId;
	private int serviceId;
	private String serviceName;
	private String totalCharge;
	private String totalChargeAfterTax;
	
	private String easyfixCharge;
	private String clientCharge;
	private String easyfixerCharge;
	private int jobServiceStatus;
	private int jobChargeType;
	
	private String effixed;;
	private String clfixed;
	private String efrfixed;
	private String efvar;
	private String clvar;
	private String efrvar;
	
	private String totalSum;
	private int isModifiedFromInvoice;
	
	private String serviceDescription;
	private int serviceMaterialCharge;
	public int getServiceMaterialCharge() {
		return serviceMaterialCharge;
	}
	public void setServiceMaterialCharge(int serviceMaterialCharge) {
		this.serviceMaterialCharge = serviceMaterialCharge;
	}
	public String getServiceDescription() {
		return serviceDescription;
	}
	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}
  
	public int getIsModifiedFromInvoice() {
		return isModifiedFromInvoice;
	}
	public void setIsModifiedFromInvoice(int isModifiedFromInvoice) {
		this.isModifiedFromInvoice = isModifiedFromInvoice;
	}
	public String getTotalChargeAfterTax() {
		return totalChargeAfterTax;
	}
	public void setTotalChargeAfterTax(String totalChargeAfterTax) {
		this.totalChargeAfterTax = totalChargeAfterTax;
	}
	public int getJobServiceId() {
		return jobServiceId;
	}
	public int getJobId() {
		return jobId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public String getTotalCharge() {
		return totalCharge;
	}
	public String getEasyfixCharge() {
		return easyfixCharge;
	}
	public String getClientCharge() {
		return clientCharge;
	}
	public String getEasyfixerCharge() {
		return easyfixerCharge;
	}
	public int getJobServiceStatus() {
		return jobServiceStatus;
	}
	public void setJobServiceId(int jobServiceId) {
		this.jobServiceId = jobServiceId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public void setTotalCharge(String totalCharge) {
		this.totalCharge = totalCharge;
	}
	public void setEasyfixCharge(String easyfixCharge) {
		this.easyfixCharge = easyfixCharge;
	}
	public void setClientCharge(String clientCharge) {
		this.clientCharge = clientCharge;
	}
	public void setEasyfixerCharge(String easyfixerCharge) {
		this.easyfixerCharge = easyfixerCharge;
	}
	public void setJobServiceStatus(int jobServiceStatus) {
		this.jobServiceStatus = jobServiceStatus;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public String getTotalSum() {
		return totalSum;
	}
	public void setTotalSum(String totalSum) {
		this.totalSum = totalSum;
	}
	public int getJobChargeType() {
		return jobChargeType;
	}
	public void setJobChargeType(int jobChargeType) {
		this.jobChargeType = jobChargeType;
	}
	public String getEffixed() {
		return effixed;
	}
	public void setEffixed(String effixed) {
		this.effixed = effixed;
	}
	public String getClfixed() {
		return clfixed;
	}
	public void setClfixed(String clfixed) {
		this.clfixed = clfixed;
	}
	public String getEfrfixed() {
		return efrfixed;
	}
	public void setEfrfixed(String efrfixed) {
		this.efrfixed = efrfixed;
	}
	public String getEfvar() {
		return efvar;
	}
	public void setEfvar(String efvar) {
		this.efvar = efvar;
	}
	public String getClvar() {
		return clvar;
	}
	public void setClvar(String clvar) {
		this.clvar = clvar;
	}
	public String getEfrvar() {
		return efrvar;
	}
	public void setEfrvar(String efrvar) {
		this.efrvar = efrvar;
	}
	

}
