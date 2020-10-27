package com.easyfix.Jobs.delegate;

import java.util.List;
import java.util.Map;

























import com.easyfix.Jobs.model.JobImage;
import com.easyfix.Jobs.model.JobSelectedServices;
import com.easyfix.Jobs.model.Jobs;
import com.easyfix.Jobs.model.Notification;
import com.easyfix.Jobs.model.Recipients;
import com.easyfix.Jobs.model.Sms;
import com.easyfix.customers.model.Address;
import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.util.utilityFunction.model.EasyfixerRatingParameters;

public interface JobService {
	
	
	public Jobs getJobDetails(int jobId) throws Exception;

	public String addUpdateJob(Jobs jobsObj, List<String> list, int userId) throws Exception;
	
	public String convertStringToDate(String dateString) throws Exception;
	public String convertDateToString(String createdDateTime) throws Exception;

	public List<Jobs> getJobList( int flag) throws Exception;
	
	List<Easyfixers> getEasyfixerListByJobTypeId(int jobId,int jobtypeid,int client_id) throws Exception;
	
	public List<Easyfixers> getEasyfixerDistanceFromJobLocation(List<Easyfixers> l,String CustomerGPS,String maxDistance)throws Exception;

	public List<Easyfixers> getSuitableEasyfixers(List<Easyfixers> efList, Jobs job)throws Exception;
	public List<Easyfixers> getSuitableEasyfixersWithoutGoogle(Map<Easyfixers, Double> originalefList, Address addObj) throws Exception;

	
	public List<JobSelectedServices> getJobServiceList(int jobId, int serviceStatus) throws Exception;

	public int changeRequestedDate(Jobs jobsObj) throws Exception;

	public void updateJobServices(int jobId, String clientServiceIds, List<String> serviceList) throws Exception;

	public void saveScheduleJob(int jobId, int fkEasyfixterId, int userId,String disTravelled, String flag) throws Exception;


	public List<EasyfixerRatingParameters> getParamList() throws Exception;

	public void saveCheckInJob(int jobId, int userId) throws Exception;

	public void saveApproveJob(int jobId) throws Exception;

	public void saveCheckOutJob(Jobs jobsObj, List<JobSelectedServices> jobServiceList ) throws Exception;
	

	public void saveJobComment(Jobs jobsObj) throws Exception;

	public List<Jobs> jobCommentList(int jobId) throws Exception;

	public void saveFeedbackJob(Jobs jobsObj) throws Exception;

	public List<Jobs> getEnumReasonList(int enumType) throws Exception;

	public void saveCancelJob(Jobs jobsObj) throws Exception;

	public void updateJobOwner(Jobs jobsObj) throws Exception;
	public void recordEfrJobRejections(Jobs jobObj) throws Exception;
	public List<Jobs> getEfrRejectReasonList() throws Exception;

	public List<Jobs> getJobListByFilter(Jobs jobsObj) throws Exception;

	public String convertStringToSimpleDate(String startDate) throws Exception;

	List<Jobs> readBooksFromExcelFile(String excelFilePath) throws Exception;

	public int addJobFromExcel(Jobs j) throws Exception;
	
	public long dateTimeDiff(String dateStart) throws Exception;

	public Jobs getLeadJobDetails(int jobId) throws Exception;

	public void updateLeadJob(int jobId, int status) throws Exception;

	public List<Jobs> getCustomerJobList(int fkCustomerId) throws Exception;

	public List<Jobs> getjobListForChangeOwner(int userId, int roleId) throws Exception;

	public int addUpdateCallLaterJob(Jobs jobsObj, List<String> serviceList, int userId) throws Exception;

	public List<Jobs> getJobListByStatus(int jobStatus, int userId) throws Exception;

	public void updateRescheduleJob(Jobs jobsObj) throws Exception;
	
	public void getSmsDetails() throws Exception;
	
	public void sendSms(int clientId, String stage, int receiver, int sender, String mobileNo,Jobs job) throws Exception;
	
	public boolean confirmApprovejob(Jobs job) throws Exception;
	public List<Recipients> getClientRecipientListForSmsEmail(int clinetId)throws Exception;
	public void autoJobSchedule(int jobId) throws Exception;

	public List<Sms> getCrmSmsList() throws Exception;
	public void updateAppNotificationLog(Jobs job,Easyfixers efr,Notification notification, String result);
	public int changeJobDesc(Jobs jobsObj) throws Exception;

	String modifySmsToCustomer(String sms, Jobs job) throws Exception;
	public int deleteJobAfterCheckout(Jobs job) throws Exception;
	public void updateUserActionLog(Jobs job) throws Exception;
	public void changeJobStatus(Jobs job, int status) throws Exception;
	public void updateTransactionFromInvoice(Jobs jobsObj) throws Exception;

	void modifyJobFromInvoice(Jobs jobsObj, List<JobSelectedServices> list)
			throws Exception;
	List<JobImage> jobImageList(JobImage jobImage) throws Exception; 
	void addJobImage(JobImage jobImage) throws Exception; 
	public int updateCheckoutOtp(int jobId, String otp) throws Exception;

	public void JobApproveDetails(List<JobSelectedServices> JobSelectedServicesList,Jobs job) throws Exception;
	public void UpdateJobServiceDetails(List<JobSelectedServices> JobSelectedServicesList) throws Exception;
	
}
	

