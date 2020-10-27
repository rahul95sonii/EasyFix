package com.easyfix.Jobs.dao;

import java.util.List;

import com.easyfix.Jobs.model.AppNotications;
import com.easyfix.Jobs.model.JobSelectedServices;
import com.easyfix.Jobs.model.Jobs;
import com.easyfix.Jobs.model.Notification;

import com.easyfix.Jobs.model.Recipients;
import com.easyfix.Jobs.model.Sms;
import com.easyfix.Jobs.model.email;
import com.easyfix.clients.model.Clients;
import com.easyfix.easyfixers.model.Easyfixers;

public interface JobDao {
	
	public Jobs getJobDetails(int jobId) throws Exception;

	public String addUpdateJob(Jobs jobsObj, List<String> list, int userId) throws Exception;

	public List<Jobs> getJobList(int flag) throws Exception;
	
	public List<Easyfixers> getEasyfixerListByJobTypeId(int jobId, int jobtypeid,int client_id) throws Exception;

	public List<JobSelectedServices> getJobServiceList(int jobId, int serviceStatus) throws Exception; 
	public int changeRequestedDate(Jobs jobsObj) throws Exception;
	public int changeJobDesc(Jobs jobsObj) throws Exception;
	public void updateJobServices(int jobId, String clientServiceIds, List<String> serviceList) throws Exception;
	public void saveScheduleJob(int jobId, int fkEasyfixterId, int userId,String disTravelled,String flag) throws Exception;
	public void saveCheckInJob(int jobId, int userId)throws Exception;

	public void saveApproveJob(int jobId)throws Exception;

	public Clients getjobServiceDetailsByJobServiceId(int jobServiceId) throws Exception;

	public void saveCheckOutJob(Jobs jobsObj ) throws Exception;

	public void saveJobComment(Jobs jobsObj) throws Exception;

	public List<Jobs> jobCommentList(int jobId) throws Exception;

	/*public void updateEasyfixerAccount(Jobs jobsObj) throws Exception;*/

	public void saveFeedbackJob(Jobs jobsObj) throws Exception;

	public List<Jobs> getEnumReasonList(int enumType) throws Exception;

	public void saveCancelJob(Jobs jobsObj) throws Exception;

	public void updateJobOwner(Jobs jobsObj) throws Exception;
	
	public void recordEfrJobRejections(Jobs jobObj) throws Exception;
	public List<Jobs> getEfrRejectReasonList() throws Exception;

	public List<Jobs> getJobListByFilter(Jobs jobsObj) throws Exception;

	public void saveJobServiceAmount(JobSelectedServices jobSelectObj) throws Exception;

	int addJobFromExcel(Jobs jobsObj) throws Exception;


	public Jobs getLeadJobDetails(int jobId) throws Exception;
	public void updateLeadJob(int jobId, int status) throws Exception;

	public List<Jobs> getCustomerJobList(int fkCustomerId) throws Exception;

	public List<Jobs> getjobListForChangeOwner(int userId, int roleId) throws Exception;

	public int addUpdateCallLaterJob(Jobs jobsObj, List<String> list, int userId) throws Exception;

	public List<Jobs> getJobListByStatus(int jobStatus, int userId) throws Exception;

	public void updateRescheduleJob(Jobs jobsObj) throws Exception;
	
	public String getServiceTaxRate() throws Exception;
	public List<Sms> getSmsDetails() throws Exception;
	public Easyfixers getAvgEfrRatingByCustomer(int efrId) throws Exception;
	public List<email> getEmailByClientJobStage(int clientId, String jobStage) throws Exception;
 
	public List<Recipients> getClientRecipientListForSmsEmail(int clientId) throws Exception;
	public Jobs getJobStatus(int jobId) throws Exception;
	public List<Sms> getCrmSmsList() throws Exception;
	public void updateAppNotificationLog(Jobs job,Easyfixers efr,Notification notification, String result);
	public int deleteJobAfterCheckout(Jobs job) throws Exception;
	public void updateUserActionLog(Jobs job) throws Exception;
	public void changeJobStatus(Jobs job, int status) throws Exception;
	public void updateTransactionFromInvoice(Jobs jobsObj) throws Exception;
	public int updateCheckoutOtp(int jobId, String otp) throws Exception;
	
	public void JobApproveDetails(List<JobSelectedServices> JobSelectedServicesList,Jobs job ) throws Exception;
	public void UpdateJobServiceDetails(List<JobSelectedServices> JobSelectedServicesList) throws Exception;
	public void updateJobStatus(Jobs jobsObj) throws Exception;


}
