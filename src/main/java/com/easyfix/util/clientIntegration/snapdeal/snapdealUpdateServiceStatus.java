package com.easyfix.util.clientIntegration.snapdeal;

public class snapdealUpdateServiceStatus {

	
	private String vendorCode;//":"S0d02c",

	private String caseId;//":"1000",

	private String orderId; //"orderId":"1000",

	private String caseStatus;	//"caseStatus":"SERVICE_DELIVERED ",

	private String remarks;//"remarks":"test",

	private String vendorStatus;//"vendorStatus":"test",

	private String callType; //"callType":null,

	private snapdealDateFormat startDate;
	private snapdealDateFormat endDate;
	public String getVendorCode() {
		return vendorCode;
	}
	public String getCaseId() {
		return caseId;
	}
	public String getOrderId() {
		return orderId;
	}
	public String getCaseStatus() {
		return caseStatus;
	}
	public String getRemarks() {
		return remarks;
	}
	public String getVendorStatus() {
		return vendorStatus;
	}
	public String getCallType() {
		return callType;
	}
	public snapdealDateFormat getStartDate() {
		return startDate;
	}
	public snapdealDateFormat getEndDate() {
		return endDate;
	}
	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}
	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setCaseStatus(String caseStatus) {
		this.caseStatus = caseStatus;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public void setVendorStatus(String vendorStatus) {
		this.vendorStatus = vendorStatus;
	}
	public void setCallType(String callType) {
		this.callType = callType;
	}
	public void setStartDate(snapdealDateFormat startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(snapdealDateFormat endDate) {
		this.endDate = endDate;
	}
		
	
}
