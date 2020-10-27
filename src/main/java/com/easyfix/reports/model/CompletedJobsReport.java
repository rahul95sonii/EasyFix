package com.easyfix.reports.model;

import java.sql.Timestamp;

import com.easyfix.Jobs.model.Jobs;

public class CompletedJobsReport {
	private Jobs jobObj;
	@Override
	public String toString() {
		return "CompletedJobsReport [jobId=" + jobId + "]";
	}


	private String startDate;
	private String endDate;
	private int clientId;
	private int flag;
	private String dateRange;

	public String getDateRange() {
		return dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}

	public Jobs getJobObj() {
		return jobObj;
	}

	public void setJobObj(Jobs jobObj) {
		this.jobObj = jobObj;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	
	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	
	
	private String cityName;	
	private int jobId;
	private String jobDesc;
	private int totalCharge; 
	private float EF_share; 
	private float EFR_share; 
	private float client_share;
	private int material_charge;
	private long completionTAT;
	private long  scheduledTAT;
	private long  checkinTAT;
	private int collectedBy;
	private String EfrDisTravelled;
	private String custName;
	private String custMobileNo;
	private int customerSatisfactionScore;
	private int easyfixerId;	
	private String easyfixerName;
	private String efPhoneNo;
	private String clientName;
	private String servicetype;	
	private Timestamp jobCreationDtae;
    private Timestamp requestedOn;
	private Timestamp feedbackDate;
	 private Timestamp chcekoutTime;
	 private Timestamp cancelTime;
	 private int noofrejection;
	 private int jobStatus;
	 private String jobRefId;
	 private String commentOnSchedule;
	 private String commentOnCheckIn;
	 private String commentOnCheckout;
	 private String commentOnFeedback;
	 private String commentOnCancel;
	 private String cancleReason;
	 private String enquiryReason;
	 private String commentOnEnquiry;
	 private String customerAddress;
	 private String customerPinCode;
	 private String scheduledBy;
	 private String checkinBy;
	 private String checkoutBy;
	 
	 private String rescheduleReason;
	 private String commentOnReschedule;
	 private Timestamp rescheduleTime;
	 private String rescheduleBy;
	 private String jobDocs;
	 private String efrAppCancelled;
	 private String efrAppRejected;
	 
	 
	
	public String getEfrAppCancelled() {
		return efrAppCancelled;
	}

	public void setEfrAppCancelled(String efrAppCancelled) {
		this.efrAppCancelled = efrAppCancelled;
	}

	public String getEfrAppRejected() {
		return efrAppRejected;
	}

	public void setEfrAppRejected(String efrAppRejected) {
		this.efrAppRejected = efrAppRejected;
	}

	public int getJobStatus() {
		return jobStatus;
	}

	public String getJobDocs() {
		return jobDocs;
	}

	public void setJobDocs(String jobDocs) {
		this.jobDocs = jobDocs;
	}

	public void setJobStatus(int jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCityName() {
		return cityName;
	}

	public int getJobId() {
		return jobId;
	}

	public String getJobDesc() {
		return jobDesc;
	}

	public int getTotalCharge() {
		return totalCharge;
	}

	public float getEF_share() {
		return EF_share;
	}

	public float getEFR_share() {
		return EFR_share;
	}

	public float getClient_share() {
		return client_share;
	}

	public String getCustMobileNo() {
		return custMobileNo;
	}

	public void setCustMobileNo(String custMobileNo) {
		this.custMobileNo = custMobileNo;
	}

	public int getMaterial_charge() {
		return material_charge;
	}

	public long getCompletionTAT() {
		return completionTAT;
	}

	public long getScheduledTAT() {
		return scheduledTAT;
	}

	public long getCheckinTAT() {
		return checkinTAT;
	}

	public int getCollectedBy() {
		return collectedBy;
	}

	public String getEfrDisTravelled() {
		return EfrDisTravelled;
	}

	public String getCustName() {
		return custName;
	}

	public int getCustomerSatisfactionScore() {
		return customerSatisfactionScore;
	}

	public int getEasyfixerId() {
		return easyfixerId;
	}

	public String getEasyfixerName() {
		return easyfixerName;
	}

	public String getClientName() {
		return clientName;
	}

	public String getServicetype() {
		return servicetype;
	}


	public int getNoofrejection() {
		return noofrejection;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}

	public void setTotalCharge(int totalCharge) {
		this.totalCharge = totalCharge;
	}

	public void setEF_share(float eF_share) {
		EF_share = eF_share;
	}

	public String getCommentOnCancel() {
		return commentOnCancel;
	}

	public void setCommentOnCancel(String commentOnCancel) {
		this.commentOnCancel = commentOnCancel;
	}

	public void setEFR_share(float eFR_share) {
		EFR_share = eFR_share;
	}

	public String getJobRefId() {
		return jobRefId;
	}

	public void setJobRefId(String jobRefId) {
		this.jobRefId = jobRefId;
	}

	public void setClient_share(float client_share) {
		this.client_share = client_share;
	}

	public void setMaterial_charge(int material_charge) {
		this.material_charge = material_charge;
	}

	public void setCompletionTAT(long completionTAT) {
		this.completionTAT = completionTAT;
	}

	public void setScheduledTAT(long scheduledTAT) {
		this.scheduledTAT = scheduledTAT;
	}

	public void setCheckinTAT(long checkinTAT) {
		this.checkinTAT = checkinTAT;
	}

	public void setCollectedBy(int collectedBy) {
		this.collectedBy = collectedBy;
	}

	public void setEfrDisTravelled(String efrDisTravelled) {
		EfrDisTravelled = efrDisTravelled;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public void setCustomerSatisfactionScore(int customerSatisfactionScore) {
		this.customerSatisfactionScore = customerSatisfactionScore;
	}

	public void setEasyfixerId(int easyfixerId) {
		this.easyfixerId = easyfixerId;
	}

	public void setEasyfixerName(String easyfixerName) {
		this.easyfixerName = easyfixerName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}


	public void setNoofrejection(int noofrejection) {
		this.noofrejection = noofrejection;
	}

	public Timestamp getJobCreationDtae() {
		return jobCreationDtae;
	}

	public Timestamp getRequestedOn() {
		return requestedOn;
	}

	public Timestamp getFeedbackDate() {
		return feedbackDate;
	}

	public Timestamp getChcekoutTime() {
		return chcekoutTime;
	}

	public void setJobCreationDtae(Timestamp jobCreationDtae) {
		this.jobCreationDtae = jobCreationDtae;
	}

	public void setRequestedOn(Timestamp requestedOn) {
		this.requestedOn = requestedOn;
	}

	public String getCommentOnSchedule() {
		return commentOnSchedule;
	}

	public String getCommentOnCheckIn() {
		return commentOnCheckIn;
	}

	public String getCommentOnCheckout() {
		return commentOnCheckout;
	}

	public String getCommentOnFeedback() {
		return commentOnFeedback;
	}

	public void setCommentOnSchedule(String commentOnSchedule) {
		this.commentOnSchedule = commentOnSchedule;
	}

	public void setCommentOnCheckIn(String commentOnCheckIn) {
		this.commentOnCheckIn = commentOnCheckIn;
	}

	public String getEfPhoneNo() {
		return efPhoneNo;
	}

	public void setEfPhoneNo(String efPhoneNo) {
		this.efPhoneNo = efPhoneNo;
	}

	public void setCommentOnCheckout(String commentOnCheckout) {
		this.commentOnCheckout = commentOnCheckout;
	}

	public void setCommentOnFeedback(String commentOnFeedback) {
		this.commentOnFeedback = commentOnFeedback;
	}

	public void setFeedbackDate(Timestamp feedbackDate) {
		this.feedbackDate = feedbackDate;
	}

	public void setChcekoutTime(Timestamp chcekoutTime) {
		this.chcekoutTime = chcekoutTime;
	}

	public String getCancleReason() {
		return cancleReason;
	}

	public void setCancleReason(String cancleReason) {
		this.cancleReason = cancleReason;
	}

	public String getEnquiryReason() {
		return enquiryReason;
	}

	public void setEnquiryReason(String enquiryReason) {
		this.enquiryReason = enquiryReason;
	}

	public String getCommentOnEnquiry() {
		return commentOnEnquiry;
	}

	public void setCommentOnEnquiry(String commentOnEnquiry) {
		this.commentOnEnquiry = commentOnEnquiry;
	}

	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerPinCode() {
		return customerPinCode;
	}

	public void setCustomerPinCode(String customerPinCode) {
		this.customerPinCode = customerPinCode;
	}

	public Timestamp getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Timestamp cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getScheduledBy() {
		return scheduledBy;
	}

	public void setScheduledBy(String scheduledBy) {
		this.scheduledBy = scheduledBy;
	}

	public String getCheckinBy() {
		return checkinBy;
	}

	public void setCheckinBy(String checkinBy) {
		this.checkinBy = checkinBy;
	}

	public String getCheckoutBy() {
		return checkoutBy;
	}

	public void setCheckoutBy(String checkoutBy) {
		this.checkoutBy = checkoutBy;
	}

	public String getRescheduleReason() {
		return rescheduleReason;
	}

	public String getCommentOnReschedule() {
		return commentOnReschedule;
	}

	public Timestamp getRescheduleTime() {
		return rescheduleTime;
	}

	public String getRescheduleBy() {
		return rescheduleBy;
	}

	public void setRescheduleReason(String rescheduleReason) {
		this.rescheduleReason = rescheduleReason;
	}

	public void setCommentOnReschedule(String commentOnReschedule) {
		this.commentOnReschedule = commentOnReschedule;
	}

	public void setRescheduleTime(Timestamp rescheduleTime) {
		this.rescheduleTime = rescheduleTime;
	}

	public void setRescheduleBy(String rescheduleBy) {
		this.rescheduleBy = rescheduleBy;
	}

	


}