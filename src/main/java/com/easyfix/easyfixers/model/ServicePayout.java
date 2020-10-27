package com.easyfix.easyfixers.model;

public class ServicePayout {

	private int payoutId;
	private int efrId;
	private float efrBalance;
	private float opsAmount;
	private float opsApprovedAmount;
	private int isApprovedByFinance;
	private String opsApprovedDate;
	private String finApproveDate;
	private float finApprovedAmount;
	private String createdDate;
	private String approval;
	private String cityName;
	private String EfrName;
	private String efrNo;
	
	public int getPayoutId() {
		return payoutId;
	}
	public int getEfrId() {
		return efrId;
	}
	public float getEfrBalance() {
		return efrBalance;
	}
	public float getOpsAmount() {
		return opsAmount;
	}
	public float getOpsApprovedAmount() {
		return opsApprovedAmount;
	}
	public int getIsApprovedByFinance() {
		return isApprovedByFinance;
	}
	public String getOpsApprovedDate() {
		return opsApprovedDate;
	}
	public String getFinApproveDate() {
		return finApproveDate;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setPayoutId(int payoutId) {
		this.payoutId = payoutId;
	}
	public void setEfrId(int efrId) {
		this.efrId = efrId;
	}
	public void setEfrBalance(float efrBalance) {
		this.efrBalance = efrBalance;
	}
	public void setOpsAmount(float opsAmount) {
		this.opsAmount = opsAmount;
	}
	public void setOpsApprovedAmount(float opsApprovedAmount) {
		this.opsApprovedAmount = opsApprovedAmount;
	}
	public void setIsApprovedByFinance(int isApprovedByFinance) {
		this.isApprovedByFinance = isApprovedByFinance;
	}
	public void setOpsApprovedDate(String opsApprovedDate) {
		this.opsApprovedDate = opsApprovedDate;
	}
	public void setFinApproveDate(String finApproveDate) {
		this.finApproveDate = finApproveDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public float getFinApprovedAmount() {
		return finApprovedAmount;
	}
	public void setFinApprovedAmount(float finApprovedAmount) {
		this.finApprovedAmount = finApprovedAmount;
	}
	public String getApproval() {
		return approval;
	}
	public void setApproval(String approval) {
		this.approval = approval;
	}
	public String getCityName() {
		return cityName;
	}
	public String getEfrName() {
		return EfrName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public void setEfrName(String efrName) {
		EfrName = efrName;
	}
	public String getEfrNo() {
		return efrNo;
	}
	public void setEfrNo(String efrNo) {
		this.efrNo = efrNo;
	}
		

	
}
