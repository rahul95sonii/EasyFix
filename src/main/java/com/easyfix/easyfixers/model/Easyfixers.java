package com.easyfix.easyfixers.model;

import java.io.File;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.easyfix.settings.model.City;
import com.easyfix.settings.model.DocumentType;
import com.easyfix.settings.model.ServiceType;
import com.easyfix.user.model.User;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Easyfixers {
	
	@JsonProperty("id")
	private int easyfixerId;
	
	@JsonProperty("name")
	private String easyfixerName;
	
	@JsonProperty("mobile")
	private String easyfixerNo;
	
	@JsonProperty("alternateNo")
	private String easyfixerAlterNo;
	
	@JsonProperty("email")
	private String easyfixerEmail;
	
	@JsonProperty("efrAddress")
	private String easyfixerAddress;
	
	@JsonIgnore
	private int easyfixerCityId;
	
	@JsonProperty("pinNo")
	private String easyfixerPin;
	
	@JsonProperty("type")
	private String easyfixerType;
	
	@JsonIgnore
	private int easyfixerServiceTypeId;
	
	@JsonProperty("efrServiceType")
	private String easyfixerServiceType;
	
	@JsonProperty("baseGps")
	private String easyfixerBaseGps;
	
	@JsonProperty("currentGps")
	private String easyfixerCurrentGps;
	
	@JsonProperty("city")
	private City cityObj;
	
	@JsonIgnore
	private ServiceType serviceTypeObj;
	
	@JsonIgnore
	private String docTypeId;
	
	@JsonIgnore
	private DocumentType docTypeObj;
	
	@JsonProperty("status")
	private int isActive;
	
	@JsonProperty("efrSuspendDate")
	private String suspendedDate;
	
	@JsonIgnore
	private int enumId;
	@JsonIgnore
	private int enumType;
	@JsonIgnore
	private String enumDesc;
	
	@JsonIgnore
	private int efrInactiveReason;
	@JsonIgnore
	private String efrInactiveComments;
	
	@JsonIgnore
	private int assignStatus; 
	
	@JsonProperty("ndmName")
	private User NDMName;
	
	@JsonIgnore
	private int ndmId;
	@JsonIgnore
	private double linearDistanceFromCustomer;
	@JsonIgnore
	private double actualDistanceFromCustomer;
	@JsonIgnore
	private String dailyCounter;  //for scheduling
	@JsonIgnore
	private String auditRating;   //for scheduling
	@JsonIgnore
	private String customerRating; // for scheduling
	@JsonIgnore
	private String distanceRating; // for scheduling
	@JsonIgnore
	private String distanceFormCustomer;//for scheduling
	@JsonIgnore
	private String finalWeightage; // for scheduling
	@JsonIgnore
	private int easyfixerDocTypeId;
	@JsonIgnore
	private String easyfixerDocType;
	@JsonIgnore
	private int docSize;
	@JsonIgnore
	private int easyfixerDocId0;
	@JsonIgnore
	private File easyfixerDocFile0;
	@JsonIgnore
	private String easyfixerDocumentName0;
	@JsonIgnore
	private int easyfixerDocId1;
	@JsonIgnore
	private File easyfixerDocFile1;
	@JsonIgnore
	private String easyfixerDocumentName1;
	@JsonIgnore
	private int easyfixerDocId2;
	@JsonIgnore
	private File easyfixerDocFile2;
	@JsonIgnore
	private String easyfixerDocumentName2;
	@JsonIgnore
	private int easyfixerDocId3;
	@JsonIgnore
	private File easyfixerDocFile3;
	@JsonIgnore
	private String easyfixerDocumentName3;
	@JsonIgnore
	private int easyfixerDocId4;
	@JsonIgnore
	private File easyfixerDocFile4;
	@JsonIgnore
	private String easyfixerDocumentName4;
	@JsonIgnore
	private int easyfixerDocId5;
	@JsonIgnore
	private File easyfixerDocFile5;
	@JsonIgnore
	private String easyfixerDocumentName5;
	@JsonIgnore	
	private String easyfixerPrevDocName0;
	@JsonIgnore
	private String easyfixerPrevDocName1;
	@JsonIgnore
	private String easyfixerPrevDocName2;
	@JsonIgnore
	private String easyfixerPrevDocName3;
	@JsonIgnore
	private String easyfixerPrevDocName4;
	@JsonIgnore
	private String easyfixerPrevDocName5;
	
	
	@JsonProperty("currentBalance")
	private Float currentBalance;
	
	@JsonProperty("balanceUpdatedDate")
	private String balanceUpdateDate;
	
	@JsonProperty("insertedTimestamp")
	private String insertDate;
	
	@JsonProperty("updatedTimestamp")
	private String updateDate;
	
	@JsonIgnore
	private int updatedBy;
	@JsonIgnore
	private int insertedBy;

	@JsonIgnore
	private List<DocumentType> docTypeList;
	
	private ServicePayout servicePayoutObj;
	
	private Double roadDistanceFromCustomer;
	private Integer isAppUser;
	private String deviceId;
	private String appLoginPassword;
	
	//for reports (payoutSheet)
	private String startDate;
	private String endDate;
	private Integer reportFlag;
	private String dateRange;
	
	private String action;
	private String source;
	private Integer jobId;
	private String efrNameCSV;
	
	@JsonIgnore
	private int currentMonthCompletedJobs;
	@JsonIgnore
	private int openJobs;
	@JsonIgnore
	private float lastSixMonthCusRating;
	private Integer skillId;
	public Integer getSkillId() {
		return skillId;
	}

	public void setSkillId(Integer skillId) {
		this.skillId = skillId;
	}

	private String cityZoneName;
	private Integer cityZoneId;

	public String getCityZoneName() {
		return cityZoneName;
	}

	public Integer getCityZoneId() {
		return cityZoneId;
	}

	public void setCityZoneName(String cityZoneName) {
		this.cityZoneName = cityZoneName;
	}

	public void setCityZoneId(Integer cityZoneId) {
		this.cityZoneId = cityZoneId;
	}

	public int getCurrentMonthCompletedJobs() {
		return currentMonthCompletedJobs;
	}

	public int getOpenJobs() {
		return openJobs;
	}

	public float getLastSixMonthCusRating() {
		return lastSixMonthCusRating;
	}

	public void setCurrentMonthCompletedJobs(int currentMonthCompletedJobs) {
		this.currentMonthCompletedJobs = currentMonthCompletedJobs;
	}

	public void setOpenJobs(int openJobs) {
		this.openJobs = openJobs;
	}

	public void setLastSixMonthCusRating(float lastSixMonthCusRating) {
		this.lastSixMonthCusRating = lastSixMonthCusRating;
	}

	public String getEfrNameCSV() {
		return efrNameCSV;
	}

	public void setEfrNameCSV(String efrNameCSV) {
		this.efrNameCSV = efrNameCSV;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public String getFinalWeightage() {
		return finalWeightage;
	}

	public void setFinalWeightage(String finalWeightage) {
		this.finalWeightage = finalWeightage;
	}
	
	public String getDistanceFormCustomer() {
		return distanceFormCustomer;
	}

	public void setDistanceFormCustomer(String distanceFormCustomer) {
		this.distanceFormCustomer = distanceFormCustomer;
	}

	
	
	public String getEasyfixerPrevDocName0() {
		return easyfixerPrevDocName0;
	}

	public String getEasyfixerPrevDocName1() {
		return easyfixerPrevDocName1;
	}

	public String getEasyfixerPrevDocName2() {
		return easyfixerPrevDocName2;
	}

	public String getEasyfixerPrevDocName3() {
		return easyfixerPrevDocName3;
	}

	public String getEasyfixerPrevDocName4() {
		return easyfixerPrevDocName4;
	}

	public String getEasyfixerPrevDocName5() {
		return easyfixerPrevDocName5;
	}

	public void setEasyfixerPrevDocName0(String easyfixerPrevDocName0) {
		this.easyfixerPrevDocName0 = easyfixerPrevDocName0;
	}

	public void setEasyfixerPrevDocName1(String easyfixerPrevDocName1) {
		this.easyfixerPrevDocName1 = easyfixerPrevDocName1;
	}

	public void setEasyfixerPrevDocName2(String easyfixerPrevDocName2) {
		this.easyfixerPrevDocName2 = easyfixerPrevDocName2;
	}

	public void setEasyfixerPrevDocName3(String easyfixerPrevDocName3) {
		this.easyfixerPrevDocName3 = easyfixerPrevDocName3;
	}

	public void setEasyfixerPrevDocName4(String easyfixerPrevDocName4) {
		this.easyfixerPrevDocName4 = easyfixerPrevDocName4;
	}

	public void setEasyfixerPrevDocName5(String easyfixerPrevDocName5) {
		this.easyfixerPrevDocName5 = easyfixerPrevDocName5;
	}

	public int getEasyfixerDocId0() {
		return easyfixerDocId0;
	}

	public File getEasyfixerDocFile0() {
		return easyfixerDocFile0;
	}

	public String getEasyfixerDocumentName0() {
		return easyfixerDocumentName0;
	}

	public int getEasyfixerDocId1() {
		return easyfixerDocId1;
	}

	public File getEasyfixerDocFile1() {
		return easyfixerDocFile1;
	}

	public String getEasyfixerDocumentName1() {
		return easyfixerDocumentName1;
	}

	public int getEasyfixerDocId2() {
		return easyfixerDocId2;
	}

	public File getEasyfixerDocFile2() {
		return easyfixerDocFile2;
	}

	public String getEasyfixerDocumentName2() {
		return easyfixerDocumentName2;
	}

	public int getEasyfixerDocId3() {
		return easyfixerDocId3;
	}

	public File getEasyfixerDocFile3() {
		return easyfixerDocFile3;
	}

	public String getEasyfixerDocumentName3() {
		return easyfixerDocumentName3;
	}

	public int getEasyfixerDocId4() {
		return easyfixerDocId4;
	}

	public File getEasyfixerDocFile4() {
		return easyfixerDocFile4;
	}

	public String getEasyfixerDocumentName4() {
		return easyfixerDocumentName4;
	}

	public int getEasyfixerDocId5() {
		return easyfixerDocId5;
	}

	public File getEasyfixerDocFile5() {
		return easyfixerDocFile5;
	}

	public String getEasyfixerDocumentName5() {
		return easyfixerDocumentName5;
	}

	public void setEasyfixerDocId0(int easyfixerDocId0) {
		this.easyfixerDocId0 = easyfixerDocId0;
	}

	public void setEasyfixerDocFile0(File easyfixerDocFile0) {
		this.easyfixerDocFile0 = easyfixerDocFile0;
	}

	public void setEasyfixerDocumentName0(String easyfixerDocumentName0) {
		this.easyfixerDocumentName0 = easyfixerDocumentName0;
	}

	public void setEasyfixerDocId1(int easyfixerDocId1) {
		this.easyfixerDocId1 = easyfixerDocId1;
	}

	public void setEasyfixerDocFile1(File easyfixerDocFile1) {
		this.easyfixerDocFile1 = easyfixerDocFile1;
	}

	public void setEasyfixerDocumentName1(String easyfixerDocumentName1) {
		this.easyfixerDocumentName1 = easyfixerDocumentName1;
	}

	public void setEasyfixerDocId2(int easyfixerDocId2) {
		this.easyfixerDocId2 = easyfixerDocId2;
	}

	public void setEasyfixerDocFile2(File easyfixerDocFile2) {
		this.easyfixerDocFile2 = easyfixerDocFile2;
	}

	public void setEasyfixerDocumentName2(String easyfixerDocumentName2) {
		this.easyfixerDocumentName2 = easyfixerDocumentName2;
	}

	public void setEasyfixerDocId3(int easyfixerDocId3) {
		this.easyfixerDocId3 = easyfixerDocId3;
	}

	public void setEasyfixerDocFile3(File easyfixerDocFile3) {
		this.easyfixerDocFile3 = easyfixerDocFile3;
	}

	public void setEasyfixerDocumentName3(String easyfixerDocumentName3) {
		this.easyfixerDocumentName3 = easyfixerDocumentName3;
	}

	public void setEasyfixerDocId4(int easyfixerDocId4) {
		this.easyfixerDocId4 = easyfixerDocId4;
	}

	public void setEasyfixerDocFile4(File easyfixerDocFile4) {
		this.easyfixerDocFile4 = easyfixerDocFile4;
	}

	public void setEasyfixerDocumentName4(String easyfixerDocumentName4) {
		this.easyfixerDocumentName4 = easyfixerDocumentName4;
	}

	public void setEasyfixerDocId5(int easyfixerDocId5) {
		this.easyfixerDocId5 = easyfixerDocId5;
	}

	public void setEasyfixerDocFile5(File easyfixerDocFile5) {
		this.easyfixerDocFile5 = easyfixerDocFile5;
	}

	public void setEasyfixerDocumentName5(String easyfixerDocumentName5) {
		this.easyfixerDocumentName5 = easyfixerDocumentName5;
	}

	
	
	
	public int getEasyfixerId() {
		return easyfixerId;
	}
	public void setEasyfixerId(int easyfixerId) {
		this.easyfixerId = easyfixerId;
	}

	public String getEasyfixerName() {
		return easyfixerName;
	}

	public void setEasyfixerName(String easyfixerName) {
		this.easyfixerName = easyfixerName;
	}

	

	public String getEasyfixerEmail() {
		return easyfixerEmail;
	}

	public void setEasyfixerEmail(String easyfixerEmail) {
		this.easyfixerEmail = easyfixerEmail;
	}

	public String getEasyfixerAddress() {
		return easyfixerAddress;
	}

	public void setEasyfixerAddress(String easyfixerAddress) {
		this.easyfixerAddress = easyfixerAddress;
	}

	public String getEasyfixerPin() {
		return easyfixerPin;
	}

	public void setEasyfixerPin(String easyfixerPin) {
		this.easyfixerPin = easyfixerPin;
	}

	public String getEasyfixerType() {
		return easyfixerType;
	}

	public void setEasyfixerType(String easyfixerType) {
		this.easyfixerType = easyfixerType;
	}

	public String getEasyfixerDocType() {
		return easyfixerDocType;
	}

	public void setEasyfixerDocType(String easyfixerDocType) {
		this.easyfixerDocType = easyfixerDocType;
	}

	

	public City getCityObj() {
		return cityObj;
	}

	public void setCityObj(City cityObj) {
		this.cityObj = cityObj;
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

	public String getEasyfixerNo() {
		return easyfixerNo;
	}

	public String getEasyfixerAlterNo() {
		return easyfixerAlterNo;
	}

	public int getEasyfixerCityId() {
		return easyfixerCityId;
	}

	public int getEasyfixerServiceTypeId() {
		return easyfixerServiceTypeId;
	}

	public String getEasyfixerBaseGps() {
		return easyfixerBaseGps;
	}

	public String getEasyfixerCurrentGps() {
		return easyfixerCurrentGps;
	}

	public String getDocTypeId() {
		return docTypeId;
	}

	public DocumentType getDocTypeObj() {
		return docTypeObj;
	}

	public void setEasyfixerNo(String easyfixerNo) {
		this.easyfixerNo = easyfixerNo;
	}

	public void setEasyfixerAlterNo(String easyfixerAlterNo) {
		this.easyfixerAlterNo = easyfixerAlterNo;
	}

	public void setEasyfixerCityId(int easyfixerCityId) {
		this.easyfixerCityId = easyfixerCityId;
	}

	public void setEasyfixerServiceTypeId(int easyfixerServiceTypeId) {
		this.easyfixerServiceTypeId = easyfixerServiceTypeId;
	}

	public void setEasyfixerBaseGps(String easyfixerBaseGps) {
		this.easyfixerBaseGps = easyfixerBaseGps;
	}

	public void setEasyfixerCurrentGps(String easyfixerCurrentGps) {
		this.easyfixerCurrentGps = easyfixerCurrentGps;
	}

	public void setDocTypeId(String docTypeId) {
		this.docTypeId = docTypeId;
	}

	public void setDocTypeObj(DocumentType docTypeObj) {
		this.docTypeObj = docTypeObj;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public String getSuspendedDate() {
		return suspendedDate;
	}

	public void setSuspendedDate(String suspendedDate) {
		this.suspendedDate = suspendedDate;
	}


	public String getEasyfixerServiceType() {
		return easyfixerServiceType;
	}

	public void setEasyfixerServiceType(String easyfixerServiceType) {
		this.easyfixerServiceType = easyfixerServiceType;
	}

	public int getEasyfixerDocTypeId() {
		return easyfixerDocTypeId;
	}

	public void setEasyfixerDocTypeId(int easyfixerDocTypeId) {
		this.easyfixerDocTypeId = easyfixerDocTypeId;
	}

	

	public List<DocumentType> getDocTypeList() {
		return docTypeList;
	}

	public void setDocTypeList(List<DocumentType> docTypeList) {
		this.docTypeList = docTypeList;
	}

	public int getDocSize() {
		return docSize;
	}

	public void setDocSize(int docSize) {
		this.docSize = docSize;
	}

	public int getAssignStatus() {
		return assignStatus;
	}

	public void setAssignStatus(int assignStatus) {
		this.assignStatus = assignStatus;
	}


	public String getDailyCounter() {
		return dailyCounter;
	}

	public void setDailyCounter(String dailyCounter) {
		this.dailyCounter = dailyCounter;
	}

	public String getAuditRating() {
		return auditRating;
	}

	public void setAuditRating(String auditRating) {
		this.auditRating = auditRating;
	}

	public String getCustomerRating() {
		return customerRating;
	}

	public void setCustomerRating(String customerRating) {
		this.customerRating = customerRating;
	}

	public String getDistanceRating() {
		return distanceRating;
	}

	public void setDistanceRating(String distanceRating) {
		this.distanceRating = distanceRating;
	}

//	public String getDistanceFormCustomer() {
//		return distanceFormCustomer;
//	}
//
//	public void setDistanceFormCustomer(String distanceFormCustomer) {
//		this.distanceFormCustomer = distanceFormCustomer;
//	}


	public double getLinearDistanceFromCustomer() {
		return linearDistanceFromCustomer;
	}

	public double getActualDistanceFromCustomer() {
		return actualDistanceFromCustomer;
	}

	public void setLinearDistanceFromCustomer(double linearDistanceFromCustomer) {
		this.linearDistanceFromCustomer = linearDistanceFromCustomer;
	}

	public void setActualDistanceFromCustomer(double actualDistanceFromCustomer) {
		this.actualDistanceFromCustomer = actualDistanceFromCustomer;
	}

	@Override
	public String toString(){
		return (this.easyfixerId+"");
	}

	public int getInsertedBy() {
		return insertedBy;
	}

	public void setInsertedBy(int insertedBy) {
		this.insertedBy = insertedBy;
	}

	public Float getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Float currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getBalanceUpdateDate() {
		return balanceUpdateDate;
	}

	public void setBalanceUpdateDate(String balanceUpdateDate) {
		this.balanceUpdateDate = balanceUpdateDate;
	}

	public int getEfrInactiveReason() {
		return efrInactiveReason;
	}

	public void setEfrInactiveReason(int efrInactiveReason) {
		this.efrInactiveReason = efrInactiveReason;
	}

	public String getEfrInactiveComments() {
		return efrInactiveComments;
	}

	public void setEfrInactiveComments(String efrInactiveComments) {
		this.efrInactiveComments = efrInactiveComments;
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

	public int getNdmId() {
		return ndmId;
	}

	public void setNdmId(int ndmId) {
		this.ndmId = ndmId;
	}

	public User getNDMName() {
		return NDMName;
	}

	public void setNDMName(User nDMName) {
		NDMName = nDMName;
	}

	public ServicePayout getServicePayoutObj() {
		return servicePayoutObj;
	}

	public void setServicePayoutObj(ServicePayout servicePayoutObj) {
		this.servicePayoutObj = servicePayoutObj;
	}

	public Double getRoadDistanceFromCustomer() {
		return roadDistanceFromCustomer;
	}

	public void setRoadDistanceFromCustomer(Double roadDistanceFromCustomer) {
		this.roadDistanceFromCustomer = roadDistanceFromCustomer;
	}

	public Integer getIsAppUser() {
		return isAppUser;
	}

	public void setIsAppUser(Integer isAppUser) {
		this.isAppUser = isAppUser;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getAppLoginPassword() {
		return appLoginPassword;
	}

	public void setAppLoginPassword(String appLoginPassword) {
		this.appLoginPassword = appLoginPassword;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	

	public String getDateRange() {
		return dateRange;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}

	public Integer getReportFlag() {
		return reportFlag;
	}

	public void setReportFlag(Integer reportFlag) {
		this.reportFlag = reportFlag;
	}

	
	
}
