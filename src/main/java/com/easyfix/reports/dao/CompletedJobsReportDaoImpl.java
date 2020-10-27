package com.easyfix.reports.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.easyfix.Jobs.model.Jobs;
import com.easyfix.clients.model.Clients;
import com.easyfix.customers.model.Customers;
import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.reports.model.CompletedJobsReport;
import com.easyfix.settings.model.ServiceType;
//import com.easyfix.util.DBConfig;

public class CompletedJobsReportDaoImpl extends JdbcDaoSupport implements CompletedJobsReportDao {

/*	@Override
	public List<CompletedJobsReport> getCompletedJobList(String start, String end, int flag) throws Exception
	{
		List<CompletedJobsReport> jobList = new ArrayList<CompletedJobsReport>();
		Connection conn=null;
		CallableStatement cstmt=null;
		ResultSet rs = null;
		try{
			//conn = getJdbcTemplate().getDataSource().getConnection();
			conn = getJdbcTemplate().getDataSource().getConnection();
			cstmt=conn.prepareCall("call sp_ef_report_completedjobreport(?,?,?)");
			//cstmt.setD
			cstmt.setString(1, start);
			cstmt.setString(2, end);
			cstmt.setInt(3, flag);
			rs = cstmt.executeQuery();
			if(rs != null) {
				while(rs.next()){
					CompletedJobsReport completedJobsReportObj = new CompletedJobsReport();
					Jobs jobObj = new Jobs();
					Customers custObj = new Customers();
					Clients clientObj = new Clients();
					Easyfixers easyfixerObj = new Easyfixers();
					ServiceType serviceTypeObj = new ServiceType();
					
					jobObj.setJobId(rs.getInt("job_id"));
					jobObj.setClientServices(rs.getString("services"));
					jobObj.setJobDesc(rs.getString("job_desc"));
					jobObj.setFkCustomerId(rs.getInt("fk_customer_id"));
					jobObj.setFkClientId(rs.getInt("fk_client_id"));
					jobObj.setFkServiceTypeId(rs.getInt("fk_service_type_id"));
					jobObj.setFkEasyfixterId(rs.getInt("fk_easyfixter_id"));
					jobObj.setCreatedDateTime(rs.getString("created_date_time"));
					jobObj.setRequestedDateTime(rs.getString("requested_date_time"));
					jobObj.setScheduleDateTime(rs.getString("scheduled_date_time"));
					jobObj.setCheckInDateTime(rs.getString("checkin_date_time"));
					jobObj.setCheckOutDateTime(rs.getString("checkout_date_time"));
					jobObj.setJobStatus(rs.getInt("job_status"));
					jobObj.setJobOwner(rs.getInt("job_owner"));
					jobObj.setCollectedBy(rs.getInt("collected_by"));
					jobObj.setMaterialCharge(rs.getString("material_charge"));
					jobObj.setCancelDateTime(rs.getString("cancel_date_time"));
					
					custObj.setCustomerName(rs.getString("customer_name"));
					custObj.setCustomerMobNo(rs.getString("customer_mob_no"));
					clientObj.setClientName(rs.getString("client_name"));
					easyfixerObj.setEasyfixerNo(rs.getString("efr_no"));
					easyfixerObj.setEasyfixerName(rs.getString("efr_name"));
					serviceTypeObj.setServiceTypeName(rs.getString("service_type_name"));
					jobObj.setCustomerObj(custObj);
					jobObj.setClientObj(clientObj);
					jobObj.setEasyfixterObj(easyfixerObj);
					jobObj.setServiceTypeObj(serviceTypeObj);
					completedJobsReportObj.setJobObj(jobObj);
					
					jobList.add(completedJobsReportObj);
				}				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { ignored }
            }
            if (cstmt != null) {
                try {
                	cstmt.close();
                } catch (SQLException e) { ignored }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { ignored }
            }
        }
		
		
		return jobList;
		
	}*/

	@Override
	public List<CompletedJobsReport> downloadCompletedJobReport(String start, String end, int flag, int client)
			throws Exception {
		Connection conn = null;
		CallableStatement cstmt=null;
		ResultSet rs = null;
		List<CompletedJobsReport> jobList = new ArrayList<CompletedJobsReport>();
		try{
			conn = getJdbcTemplate().getDataSource().getConnection();
			cstmt=conn.prepareCall("call sp_ef_report_completedjobreport(?,?,?,?)");
			cstmt.setString(1, start);
			cstmt.setString(2, end);
			cstmt.setInt(3, flag);
			cstmt.setInt(4, client);
			System.out.println(cstmt);
			rs = cstmt.executeQuery();
			if(rs != null) {
				while(rs.next()){
					CompletedJobsReport completedJobsReportObj = new CompletedJobsReport();
				
					completedJobsReportObj.setChcekoutTime(rs.getTimestamp("checkout_date_time"));
					completedJobsReportObj.setCheckinTAT(rs.getLong("checkinTAT"));
					completedJobsReportObj.setCityName(rs.getString("city_name"));
					completedJobsReportObj.setClient_share(rs.getFloat("client_charge"));
					completedJobsReportObj.setClientId(rs.getInt("fk_client_id"));
					completedJobsReportObj.setClientName(rs.getString("client_name"));
					completedJobsReportObj.setCollectedBy(rs.getInt("collected_by"));
					completedJobsReportObj.setCompletionTAT(rs.getLong("completionTAT"));
					completedJobsReportObj.setCustName(rs.getString("customer_name"));
					completedJobsReportObj.setCustMobileNo(rs.getString("customer_mob_no"));
					completedJobsReportObj.setCustomerSatisfactionScore(rs.getInt("customer_rating"));
					completedJobsReportObj.setEasyfixerId(rs.getInt("fk_easyfixter_id"));
					completedJobsReportObj.setEasyfixerName(rs.getString("efr_name"));
					completedJobsReportObj.setEF_share(rs.getFloat("easyfix_charge"));
					completedJobsReportObj.setEfPhoneNo(rs.getString("efr_no"));
					completedJobsReportObj.setEFR_share(rs.getFloat("easyfixer_charge"));
					completedJobsReportObj.setEfrDisTravelled(rs.getString("Efr_dis_travelled"));
					completedJobsReportObj.setFeedbackDate(rs.getTimestamp("feedback_date_time"));
					//System.out.println(completedJobsReportObj.getFeedbackDate());
					
					completedJobsReportObj.setJobCreationDtae(rs.getTimestamp("created_date_time"));
					completedJobsReportObj.setCancelTime(rs.getTimestamp("cancel_date_time"));
					//System.out.println(completedJobsReportObj.getJobCreationDtae());
					
					completedJobsReportObj.setJobDesc(rs.getString("job_desc"));
					completedJobsReportObj.setJobId(rs.getInt("job_id"));
					
					completedJobsReportObj.setMaterial_charge(rs.getInt("material_charge"));
					completedJobsReportObj.setNoofrejection(rs.getInt("noofrejection"));
					completedJobsReportObj.setRequestedOn(rs.getTimestamp("requested_date_time"));
					completedJobsReportObj.setScheduledTAT(rs.getLong("scheduledTAT"));
					completedJobsReportObj.setServicetype(rs.getString("service_type_name"));
					completedJobsReportObj.setTotalCharge(rs.getInt("total_charge"));
					completedJobsReportObj.setJobStatus(rs.getInt("job_status"));
					completedJobsReportObj.setJobRefId(rs.getString("client_ref_id"));
					completedJobsReportObj.setCommentOnSchedule(rs.getString("comment_on_schedule"));
					completedJobsReportObj.setCommentOnCheckIn(rs.getString("comment_on_checkin"));
					completedJobsReportObj.setCommentOnCheckout(rs.getString("comment_on_checkout"));
					completedJobsReportObj.setCommentOnFeedback(rs.getString("comment_on_feedback"));
					completedJobsReportObj.setCommentOnCancel(rs.getString("cancel_comment"));
					completedJobsReportObj.setCommentOnEnquiry(rs.getString("enquiry_comment"));
					completedJobsReportObj.setCancleReason(rs.getString("cancle_reason"));
					completedJobsReportObj.setEnquiryReason(rs.getString("enquiry_reason"));
					completedJobsReportObj.setCustomerAddress(rs.getString("address"));
					completedJobsReportObj.setCustomerPinCode(rs.getString("pin_code"));
					completedJobsReportObj.setScheduledBy(rs.getString("scheduled_by"));
					completedJobsReportObj.setCheckinBy(rs.getString("checkin_by"));
					completedJobsReportObj.setCheckoutBy(rs.getString("checkout_by"));
					completedJobsReportObj.setJobDocs(rs.getString("job_docs"));
					completedJobsReportObj.setEfrAppCancelled(rs.getString("efrAppCancelled"));
					completedJobsReportObj.setEfrAppRejected(rs.getString("efrAppRejected"));
					
					jobList.add(completedJobsReportObj);
				}				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /*ignored*/ }
            }
            if (cstmt != null) {
                try {
                	cstmt.close();
                } catch (SQLException e) { /*ignored*/ }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /*ignored*/ }
            }
        }
//	System.out.println(jobList.size()+":in report dao : "+jobList);
		return jobList;
	}

	@Override
	public List<CompletedJobsReport> downloadRescheduleJobReport(String start,
			String end, int flag, int client) throws Exception {
		List<CompletedJobsReport> jobList = new ArrayList<CompletedJobsReport>();
		Connection conn=null;
		CallableStatement cstmt=null;
		ResultSet rs = null;
		try{
			//conn = getJdbcTemplate().getDataSource().getConnection();
			conn = getJdbcTemplate().getDataSource().getConnection();
			cstmt=conn.prepareCall("CALL sp_ef_report_reschedule_job(?,?,?,?)");
			//cstmt.setD
			cstmt.setString(1, start);
			cstmt.setString(2, end);
			cstmt.setInt(3, flag);
			cstmt.setInt(4,client );
			rs = cstmt.executeQuery();
			if(rs != null) {
				while(rs.next()){
					
					
					CompletedJobsReport completedJobsReportObj = new CompletedJobsReport();
					
					completedJobsReportObj.setChcekoutTime(rs.getTimestamp("checkout_date_time"));
					
					completedJobsReportObj.setCityName(rs.getString("city_name"));
					
					completedJobsReportObj.setClientId(rs.getInt("fk_client_id"));
					completedJobsReportObj.setClientName(rs.getString("client_name"));
					completedJobsReportObj.setCollectedBy(rs.getInt("collected_by"));
				
					completedJobsReportObj.setCustName(rs.getString("customer_name"));
					completedJobsReportObj.setCustMobileNo(rs.getString("customer_mob_no"));
					completedJobsReportObj.setEasyfixerId(rs.getInt("fk_easyfixter_id"));
					completedJobsReportObj.setEasyfixerName(rs.getString("efr_name"));
					
					completedJobsReportObj.setEfPhoneNo(rs.getString("efr_no"));
				
					
					completedJobsReportObj.setFeedbackDate(rs.getTimestamp("feedback_date_time"));
					//System.out.println(completedJobsReportObj.getFeedbackDate());
					
					completedJobsReportObj.setJobCreationDtae(rs.getTimestamp("created_date_time"));
					completedJobsReportObj.setCancelTime(rs.getTimestamp("cancel_date_time"));
					//System.out.println(completedJobsReportObj.getJobCreationDtae());
					
					completedJobsReportObj.setJobDesc(rs.getString("job_desc"));
					completedJobsReportObj.setJobId(rs.getInt("job_id"));
					
					completedJobsReportObj.setMaterial_charge(rs.getInt("material_charge"));
			
					completedJobsReportObj.setRequestedOn(rs.getTimestamp("requested_date_time"));
					
					completedJobsReportObj.setServicetype(rs.getString("service_type_name"));
					
					completedJobsReportObj.setJobStatus(rs.getInt("job_status"));
					completedJobsReportObj.setJobRefId(rs.getString("client_ref_id"));
					completedJobsReportObj.setRescheduleBy(rs.getString("reschedule_by"));
					completedJobsReportObj.setCommentOnReschedule(rs.getString("reschedule_comment"));
					completedJobsReportObj.setRescheduleReason(rs.getString("reschedule_reason"));
					completedJobsReportObj.setRescheduleTime(rs.getTimestamp("reschedule_date_time"));
					
					jobList.add(completedJobsReportObj);
					
				}				
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /*ignored*/ }
            }
            if (cstmt != null) {
                try {
                	cstmt.close();
                } catch (SQLException e) { /*ignored*/ }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /*ignored*/ }
            }
        }
		
		
		return jobList;
		
	}

}
