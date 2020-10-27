package com.easyfix.Jobs.model;




import java.io.File;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.easyfix.clients.model.Clients;
import com.easyfix.customers.model.Address;
import com.easyfix.customers.model.Customers;
import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.finance.model.Finance;
import com.easyfix.settings.model.ServiceType;
import com.easyfix.user.model.User;




@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
//@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
public class Jobs {
	//alert:: put all properties in String or wrapper classes so as POST/PUT entity can be serialized with JsonSerialize.Inclusion.NON_NULL 
	@JsonProperty("id")
	private int jobId;
	private String mobileNo;
	private String additionalName;
	private String jobDesc;
	private String jobType = "";
	private String clientServices;
	private Integer skillId = 0;
	@JsonProperty("created_date")
	private String createdDateTime;
	private String otp;
	
	@JsonProperty("requested_date")
	private String requestedDateTime;
	
	@JsonProperty("scheduled_date")
	private String scheduleDateTime;
	
	@JsonProperty("start_date")
	private String checkInDateTime;
	
	@JsonProperty("completion_date")
	private String checkOutDateTime;
	
	@JsonProperty("feedback_date")
	private String feedbackDateTime;
	
	@JsonProperty("cancelled_date")
	private String cancelDateTime;
	
	private String ownerChangeDateTime;
	
	@JsonProperty("currentStatus")
	private int jobStatus;
	
	
	private String clientServiceIds;
	
	@JsonProperty("reference_id")
	private String clientRefId;
	
	@JsonIgnore
	private int paidBy;
	@JsonIgnore
	private int collectedBy = 0;
	
	private int ndmId;
	@JsonProperty("materialCharge")
	private String MaterialCharge;
	private String easyfixerAmount;
	private String totalServiceAmount;
	private String clientAmount;
	private String easyfixAmount;
	@JsonIgnore
	private int easyfixerRating;
	@JsonIgnore
	private int easyfixRating;
	private String happyWithService;
	private String serviceTaken;
	private String anotherCall;
	private String crossSelling;
	@JsonIgnore
	private long timeDifference;
	@JsonIgnore
	private int fkCustomerId;
	@JsonIgnore
	private int fkAddressId;
	@JsonIgnore
	private int fkClientId;
	@JsonIgnore
	private int fkClientServiceId;
	@JsonIgnore
	private int fkServiceTypeId;
	@JsonIgnore
	private int fkEasyfixterId;
	@JsonIgnore
	private int fkCityId;
	@JsonIgnore
	private int fkCreatedBy;
	@JsonIgnore
	private int fkScheduledBy;
	@JsonIgnore
	private int fkCheckInBy;
	@JsonIgnore
	private int fkCheckOutBy;
	@JsonIgnore
	private int fkFeedBackBy;
	@JsonIgnore
	private int fkCanceledBy;
	private Integer updatedBy;
	@JsonIgnore
	private int fkCancelReasonId;
	
	@JsonProperty("customer")
	private Customers customerObj;
	
	@JsonProperty("handymen")
	private Easyfixers easyfixterObj;
	
	@JsonProperty("client")
	private Clients clientObj;
	
	@JsonProperty("service_type")
	private ServiceType serviceTypeObj;
	
	@JsonProperty("address")
	private Address addressObj;
	
	@JsonProperty("charges")
	private Finance financeObj;
	
	@JsonIgnore
	private int commentedOn;
	private String comments;
	@JsonIgnore
	private int commentedBy;
	private String empName;
	
	@JsonProperty("distanceTravelled")
	private String distanceTraveledByEasyfixer;// for reporting
	
	@JsonProperty("feedback")
	private CustomerFeedback customerFeedback;
	
	@JsonProperty("cancel_stage")
	private int canceledOn;
	@JsonIgnore
	private int enumId;
	@JsonIgnore
	private int enumType;
	@JsonIgnore
	private String enumDesc;
	@JsonIgnore
	private int cancelBy;	
	@JsonProperty("cancel_comment")
	private String cancleComment;
	
	private List<Jobs> jobLeadList;
	private List<Jobs> scheduleList;
	private List<Jobs> checkInList;
	private List<Jobs> approvalList;
	private List<Jobs> checkOutList;
	private List<Jobs> feedbackList;
	private List<Jobs> callLaterList;
	private List<Jobs> unownedJobs;
	private List<Jobs> appCheckoutJobs;
	
	public List<Jobs> getAppCheckoutJobs() {
		return appCheckoutJobs;
	}
	public void setAppCheckoutJobs(List<Jobs> appCheckoutJobs) {
		this.appCheckoutJobs = appCheckoutJobs;
	}
	@JsonIgnore
	private int jobOwner;
	
	@JsonProperty("source")
	private Integer source;
	
	private User owner;
	private User ownerChangedByObj;
	
//	@JsonIgnore
	private Integer OwnerChangeBy;
	
	private String OwnerChangeReason;
	private String ownerJobIds;
	@JsonIgnore
	private int efrRejectionReasonId;
	private String efrRejectReasonName;
	private String efrrejectionComment;
	private File jobImage;
	private String jobImageName;
//	private int rejectId;
//	private String rejectReason;
	
//	  private File ExcelFileForJobUpload;
//	  private String ExcelFileForJobUploadContentType;
//	  private String myFileName;
//	  private String destPath;
	  
	  private String startDate;
	  private String endDate;
	  
	  private String jobUploadByExcelFileName; // for excel upload
	  private File jobUploadByExcelFile; // for excel upload
	  
	  private String serviceTaxRate;
	  private String totalServiceTax;
	  
	private String jobFlag;
	  private String jobDocs;
	  
	  
//====== For API ==================
	  @JsonProperty("createdBy")
	  private User jobCreatedBy;
	  
	  @JsonProperty("scheduledBy")
	  private User jobScheduledBy;
	  
	  @JsonProperty("checkinBy")
	  private User jobCheckInBy;
	  
	  @JsonProperty("checkoutBy")
	  private User jobCheckOutBy;
	  
	  @JsonProperty("feedbackBy")
	  private User jobFeedbackBy;
	  
	  @JsonProperty("jobCancelBy")
	  private User jobCanceledBy;
	  
	  @JsonProperty("action")
	  private String jobAction;
	  
	 
	private JobSelectedServices jobServ;
	  
	  
	  
	public JobSelectedServices getJobServ() {
	return jobServ;
	}
	public void setJobServ(JobSelectedServices jobServ) {
		this.jobServ = jobServ;
	}

	public String getAdditionalName() {
		return additionalName;
	}
	public void setAdditionalName(String additionalName) {
		this.additionalName = additionalName;
	}  
	  
	public String getDistanceTraveledByEasyfixer() {
		return distanceTraveledByEasyfixer;
	}
	public void setDistanceTraveledByEasyfixer(String distanceTraveledByEasyfixer) {
		this.distanceTraveledByEasyfixer = distanceTraveledByEasyfixer;
	}

	public int getJobId() {
		return jobId;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public String getJobDesc() {
		return jobDesc;
	}
	public String getCreatedDateTime() {
		return createdDateTime;
	}
	public String getRequestedDateTime() {
		return requestedDateTime;
	}
	public String getScheduleDateTime() {
		return scheduleDateTime;
	}
	public String getCheckInDateTime() {
		return checkInDateTime;
	}
	public String getCheckOutDateTime() {
		return checkOutDateTime;
	}
	public String getCancelDateTime() {
		return cancelDateTime;
	}
	public int getJobStatus() {
		return jobStatus;
	}
	public int getFkCustomerId() {
		return fkCustomerId;
	}
	public String getCancleComment() {
		return cancleComment;
	}
	public void setCancleComment(String cancleComment) {
		this.cancleComment = cancleComment;
	}
	public int getFkAddressId() {
		return fkAddressId;
	}
	public List<Jobs> getJobLeadList() {
		return jobLeadList;
	}
	public void setJobLeadList(List<Jobs> jobLeadList) {
		this.jobLeadList = jobLeadList;
	}
	public int getFkClientId() {
		return fkClientId;
	}
	public int getFkClientServiceId() {
		return fkClientServiceId;
	}
	public int getFkServiceTypeId() {
		return fkServiceTypeId;
	}
	public int getFkEasyfixterId() {
		return fkEasyfixterId;
	}
	public int getFkCreatedBy() {
		return fkCreatedBy;
	}
	public int getFkScheduledBy() {
		return fkScheduledBy;
	}
	public int getFkCheckInBy() {
		return fkCheckInBy;
	}
	public int getFkCheckOutBy() {
		return fkCheckOutBy;
	}
	public int getFkCanceledBy() {
		return fkCanceledBy;
	}
	public Customers getCustomerObj() {
		return customerObj;
	}
	public Easyfixers getEasyfixterObj() {
		return easyfixterObj;
	}
	public Clients getClientObj() {
		return clientObj;
	}
	public ServiceType getServiceTypeObj() {
		return serviceTypeObj;
	}
	public String getJobUploadByExcelFileName() {
		return jobUploadByExcelFileName;
	}
	public void setJobUploadByExcelFileName(String jobUploadByExcelFileName) {
		this.jobUploadByExcelFileName = jobUploadByExcelFileName;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	public void setCreatedDateTime(String createdDateTime) {
		this.createdDateTime = createdDateTime;
	}
	public void setRequestedDateTime(String requestedDateTime) {
		this.requestedDateTime = requestedDateTime;
	}
	public void setScheduleDateTime(String scheduleDateTime) {
		this.scheduleDateTime = scheduleDateTime;
	}
	public void setCheckInDateTime(String checkInDateTime) {
		this.checkInDateTime = checkInDateTime;
	}
	public void setCheckOutDateTime(String checkOutDateTime) {
		this.checkOutDateTime = checkOutDateTime;
	}
	public void setCancelDateTime(String cancelDateTime) {
		this.cancelDateTime = cancelDateTime;
	}
	public void setJobStatus(int jobStatus) {
		this.jobStatus = jobStatus;
	}
	public void setFkCustomerId(int fkCustomerId) {
		this.fkCustomerId = fkCustomerId;
	}
	public void setFkAddressId(int fkAddressId) {
		this.fkAddressId = fkAddressId;
	}
	public void setFkClientId(int fkClientId) {
		this.fkClientId = fkClientId;
	}
	public void setFkClientServiceId(int fkClientServiceId) {
		this.fkClientServiceId = fkClientServiceId;
	}
	public void setFkServiceTypeId(int fkServiceTypeId) {
		this.fkServiceTypeId = fkServiceTypeId;
	}
	public void setFkEasyfixterId(int fkEasyfixterId) {
		this.fkEasyfixterId = fkEasyfixterId;
	}
	public void setFkCreatedBy(int fkCreatedBy) {
		this.fkCreatedBy = fkCreatedBy;
	}
	public void setFkScheduledBy(int fkScheduledBy) {
		this.fkScheduledBy = fkScheduledBy;
	}
	public void setFkCheckInBy(int fkCheckInBy) {
		this.fkCheckInBy = fkCheckInBy;
	}
	public void setFkCheckOutBy(int fkCheckOutBy) {
		this.fkCheckOutBy = fkCheckOutBy;
	}
	public void setFkCanceledBy(int fkCanceledBy) {
		this.fkCanceledBy = fkCanceledBy;
	}
	public void setCustomerObj(Customers customerObj) {
		this.customerObj = customerObj;
	}
	public void setEasyfixterObj(Easyfixers easyfixterObj) {
		this.easyfixterObj = easyfixterObj;
	}
	public void setClientObj(Clients clientObj) {
		this.clientObj = clientObj;
	}
	public void setServiceTypeObj(ServiceType serviceTypeObj) {
		this.serviceTypeObj = serviceTypeObj;
	}
	public int getFkCancelReasonId() {
		return fkCancelReasonId;
	}
	public void setFkCancelReasonId(int fkCancelReasonId) {
		this.fkCancelReasonId = fkCancelReasonId;
	}
	public String getClientServices() {
		return clientServices;
	}
	public void setClientServices(String clientServices) {
		this.clientServices = clientServices;
	}
	public Address getAddressObj() {
		return addressObj;
	}
	public void setAddressObj(Address addressObj) {
		this.addressObj = addressObj;
	}
	public String getClientServiceIds() {
		return clientServiceIds;
	}
	public void setClientServiceIds(String clientServiceIds) {
		this.clientServiceIds = clientServiceIds;
	}

	public int getCommentedOn() {
		return commentedOn;
	}
	public String getComments() {
		return comments;
	}
	public int getCommentedBy() {
		return commentedBy;
	}
	public void setCommentedOn(int commentedOn) {
		this.commentedOn = commentedOn;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public void setCommentedBy(int commentedBy) {
		this.commentedBy = commentedBy;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public int getJobOwner() {
		return jobOwner;
	}
	public void setJobOwner(int jobOwner) {
		this.jobOwner = jobOwner;
	}
	public int getCollectedBy() {
		return collectedBy;
	}
	public void setCollectedBy(int collectedBy) {
		this.collectedBy = collectedBy;
	}
	public String getMaterialCharge() {
		return MaterialCharge;
	}
	public void setMaterialCharge(String materialCharge) {
		MaterialCharge = materialCharge;
	}
	public int getPaidBy() {
		return paidBy;
	}
	public void setPaidBy(int paidBy) {
		this.paidBy = paidBy;
	}
	public String getEasyfixerAmount() {
		return easyfixerAmount;
	}
	public void setEasyfixerAmount(String easyfixerAmount) {
		this.easyfixerAmount = easyfixerAmount;
	}
	public String getTotalServiceAmount() {
		return totalServiceAmount;
	}
	public String getClientAmount() {
		return clientAmount;
	}
	public String getEasyfixAmount() {
		return easyfixAmount;
	}
	public void setTotalServiceAmount(String totalServiceAmount) {
		this.totalServiceAmount = totalServiceAmount;
	}
	public void setClientAmount(String clientAmount) {
		this.clientAmount = clientAmount;
	}
	public void setEasyfixAmount(String easyfixAmount) {
		this.easyfixAmount = easyfixAmount;
	}
	public int getEasyfixerRating() {
		return easyfixerRating;
	}
	public int getEasyfixRating() {
		return easyfixRating;
	}
	public String getHappyWithService() {
		return happyWithService;
	}
	public String getServiceTaken() {
		return serviceTaken;
	}
	public String getAnotherCall() {
		return anotherCall;
	}
	public void setEasyfixerRating(int easyfixerRating) {
		this.easyfixerRating = easyfixerRating;
	}
	public void setEasyfixRating(int easyfixRating) {
		this.easyfixRating = easyfixRating;
	}
	public void setHappyWithService(String happyWithService) {
		this.happyWithService = happyWithService;
	}
	public void setServiceTaken(String serviceTaken) {
		this.serviceTaken = serviceTaken;
	}
	public void setAnotherCall(String anotherCall) {
		this.anotherCall = anotherCall;
	}
	public int getFkFeedBackBy() {
		return fkFeedBackBy;
	}
	public void setFkFeedBackBy(int fkFeedBackBy) {
		this.fkFeedBackBy = fkFeedBackBy;
	}
	public List<Jobs> getScheduleList() {
		return scheduleList;
	}
	public void setScheduleList(List<Jobs> scheduleList) {
		this.scheduleList = scheduleList;
	}
	public List<Jobs> getCheckInList() {
		return checkInList;
	}
	public void setCheckInList(List<Jobs> checkInList) {
		this.checkInList = checkInList;
	}
	public List<Jobs> getApprovalList() {
		return approvalList;
	}
	public void setApprovalList(List<Jobs> approvalList) {
		this.approvalList = approvalList;
	}
	public List<Jobs> getCheckOutList() {
		return checkOutList;
	}
	public void setCheckOutList(List<Jobs> checkOutList) {
		this.checkOutList = checkOutList;
	}
	public List<Jobs> getFeedbackList() {
		return feedbackList;
	}
	public void setFeedbackList(List<Jobs> feedbackList) {
		this.feedbackList = feedbackList;
	}
	public int getCanceledOn() {
		return canceledOn;
	}
	
	public int getCancelBy() {
		return cancelBy;
	}
	
	public void setCanceledOn(int canceledOn) {
		this.canceledOn = canceledOn;
	}
	
	public void setCancelBy(int cancelBy) {
		this.cancelBy = cancelBy;
	}
	
	public Integer getOwnerChangeBy() {
		return OwnerChangeBy;
	}
	public void setOwnerChangeBy(Integer ownerChangeBy) {
		OwnerChangeBy = ownerChangeBy;
	}
	public String getOwnerChangeReason() {
		return OwnerChangeReason;
	}
	public void setOwnerChangeReason(String ownerChangeReason) {
		OwnerChangeReason = ownerChangeReason;
	}
	public File getJobUploadByExcelFile() {
		return jobUploadByExcelFile;
	}
	public void setJobUploadByExcelFile(File jobUploadByExcelFile) {
		this.jobUploadByExcelFile = jobUploadByExcelFile;
	}
	public int getEfrRejectionReasonId() {
		return efrRejectionReasonId;
	}
	
	public void setEfrRejectionReasonId(int efrRejectionReasonId) {
		this.efrRejectionReasonId = efrRejectionReasonId;
	}
	public String getEfrrejectionComment() {
		return efrrejectionComment;
	}
	public void setEfrrejectionComment(String efrrejectionComment) {
		this.efrrejectionComment = efrrejectionComment;
	}
	public String getClientRefId() {
		return clientRefId;
	}
	public void setClientRefId(String clientRefId) {
		this.clientRefId = clientRefId;
	}
	public String getEfrRejectReasonName() {
		return efrRejectReasonName;
	}
	public void setEfrRejectReasonName(String efrRejectReasonName) {
		this.efrRejectReasonName = efrRejectReasonName;
	}
	public long getTimeDifference() {
		return timeDifference;
	}
	public void setTimeDifference(long timeDifference) {
		this.timeDifference = timeDifference;
	}
	public int getFkCityId() {
		return fkCityId;
	}
	public void setFkCityId(int fkCityId) {
		this.fkCityId = fkCityId;
	}
	public String getStartDate() {
		return startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getCrossSelling() {
		return crossSelling;
	}
	public void setCrossSelling(String crossSelling) {
		this.crossSelling = crossSelling;
	}
	public Integer getSkillId() {
		return skillId;
	}
	public void setSkillId(Integer skillId) {
		this.skillId = skillId;
	}
	public String getOwnerJobIds() {
		return ownerJobIds;
	}
	public void setOwnerJobIds(String ownerJobIds) {
		this.ownerJobIds = ownerJobIds;
	}
	public String getFeedbackDateTime() {
		return feedbackDateTime;
	}
	public void setFeedbackDateTime(String feedbackDateTime) {
		this.feedbackDateTime = feedbackDateTime;
	}
	public int getNdmId() {
		return ndmId;
	}
	public void setNdmId(int ndmId) {
		this.ndmId = ndmId;
	}
	@Override
	public String toString() {
		return "Jobs [jobId=" + jobId + ", clientObj=" + clientObj + "]";
	}
	public Finance getFinanceObj() {
		return financeObj;
	}
	public void setFinanceObj(Finance financeObj) {
		this.financeObj = financeObj;
	}
	public User getJobCreatedBy() {
		return jobCreatedBy;
	}
	public User getJobScheduledBy() {
		return jobScheduledBy;
	}
	public User getJobCheckInBy() {
		return jobCheckInBy;
	}
	public User getJobCheckOutBy() {
		return jobCheckOutBy;
	}
	public User getJobFeedbackBy() {
		return jobFeedbackBy;
	}
	public User getJobCanceledBy() {
		return jobCanceledBy;
	}
	public void setJobCreatedBy(User jobCreatedBy) {
		this.jobCreatedBy = jobCreatedBy;
	}
	public void setJobScheduledBy(User jobScheduledBy) {
		this.jobScheduledBy = jobScheduledBy;
	}
	public void setJobCheckInBy(User jobCheckInBy) {
		this.jobCheckInBy = jobCheckInBy;
	}
	public void setJobCheckOutBy(User jobCheckOutBy) {
		this.jobCheckOutBy = jobCheckOutBy;
	}
	public void setJobFeedbackBy(User jobFeedbackBy) {
		this.jobFeedbackBy = jobFeedbackBy;
	}
	public void setJobCanceledBy(User jobCanceledBy) {
		this.jobCanceledBy = jobCanceledBy;
	}
	public CustomerFeedback getCustomerFeedback() {
		return customerFeedback;
	}
	public void setCustomerFeedback(CustomerFeedback customerFeedback) {
		this.customerFeedback = customerFeedback;
	}
	public int getEnumId() {
		return enumId;
	}
	public int getEnumType() {
		return enumType;
	}
	public String getEnumDesc() {
		return enumDesc;
	}
	public void setEnumId(int enumId) {
		this.enumId = enumId;
	}
	public void setEnumType(int enumType) {
		this.enumType = enumType;
	}
	public void setEnumDesc(String enumDesc) {
		this.enumDesc = enumDesc;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public String getServiceTaxRate() {
		return serviceTaxRate;
	}
	public void setServiceTaxRate(String serviceTaxRate) {
		this.serviceTaxRate = serviceTaxRate;
	}
	public String getTotalServiceTax() {
		return totalServiceTax;
	}
	public void setTotalServiceTax(String totalServiceTax) {
		this.totalServiceTax = totalServiceTax;
	}
	public List<Jobs> getCallLaterList() {
		return callLaterList;
	}
	public void setCallLaterList(List<Jobs> callLaterList) {
		this.callLaterList = callLaterList;
	}
	public User getOwnerChangedByObj() {
		return ownerChangedByObj;
	}
	public void setOwnerChangedByObj(User ownerChangedByObj) {
		this.ownerChangedByObj = ownerChangedByObj;
	}
	public String getOwnerChangeDateTime() {
		return ownerChangeDateTime;
	}
	public void setOwnerChangeDateTime(String ownerChangeDateTime) {
		this.ownerChangeDateTime = ownerChangeDateTime;
	}
	public String getJobAction() {
		return jobAction;
	}
	public void setJobAction(String jobAction) {
		this.jobAction = jobAction;
	}
	public String getJobFlag() {
		return jobFlag;
	}
	public List<Jobs> getUnownedJobs() {
		return unownedJobs;
	}
	public void setUnownedJobs(List<Jobs> unownedJobs) {
		this.unownedJobs = unownedJobs;
	}
	public void setJobFlag(String jobFlag) {
		this.jobFlag = jobFlag;
	}
	public String getJobDocs() {
		return jobDocs;
	}
	public void setJobDocs(String jobDocs) {
		this.jobDocs = jobDocs;
	}
	public Integer getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}
	public File getJobImage() {
		return jobImage;
	}
	public void setJobImage(File jobImage) {
		this.jobImage = jobImage;
	}
	public String getJobImageName() {
		return jobImageName;
	}
	public void setJobImageName(String jobImageName) {
		this.jobImageName = jobImageName;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getJobType() {
		return jobType;
	}
	public void setJobType(String jobType) {
		this.jobType = jobType;
	}
	
	
	
	
	
	
}
