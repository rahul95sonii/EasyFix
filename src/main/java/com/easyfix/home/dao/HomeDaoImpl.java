package com.easyfix.home.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.easyfix.Jobs.model.Jobs;
import com.easyfix.clients.model.Clients;
import com.easyfix.customers.model.Address;
import com.easyfix.customers.model.Customers;
import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.settings.model.City;
import com.easyfix.settings.model.ServiceType;

public class HomeDaoImpl extends JdbcDaoSupport implements HomeDao {
	private static final Logger logger = LogManager.getLogger(HomeDaoImpl.class);
	public HomeDaoImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Jobs getUserJobsList(int userId, int roleId) throws Exception {
		List<Jobs> scheduleList = new ArrayList<Jobs>();
		List<Jobs> checkInList = new ArrayList<Jobs>();
		List<Jobs> approvalList = new ArrayList<Jobs>();
		List<Jobs> checkOutList = new ArrayList<Jobs>();
		List<Jobs> feedbackList = new ArrayList<Jobs>();
		List<Jobs> callLaterList = new ArrayList<Jobs>();
		List<Jobs> unownedJobs = new ArrayList<Jobs>();
		List<Jobs> appCheckOutJobs = new ArrayList<Jobs>();
		
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Jobs jobObj = null;	
		Jobs userJobObj =new Jobs();
		Clients clientObj =null;
		ServiceType serviceTypeObj = null;
		Customers customerObj = null;
		Easyfixers efrObj = null;
		long timeDiff = 0;
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
			/*DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();*/
			if(conn!=null){
				cstmt = conn.prepareCall("{call sp_ef_user_owner_job_list(?,?)}");
				cstmt.setInt(1, userId);
				cstmt.setInt(2, roleId);
				rs = cstmt.executeQuery();
				if(rs!=null){
					while(rs.next()){
						jobObj = new Jobs();
						customerObj = new Customers();
						clientObj = new Clients();
						serviceTypeObj = new ServiceType();
						efrObj = new Easyfixers();
						
						jobObj.setJobId(rs.getInt("job_id"));
						jobObj.setJobDesc(rs.getString("job_desc"));
						jobObj.setRequestedDateTime(rs.getString("requested_date_time"));
						jobObj.setCreatedDateTime(rs.getString("created_date_time"));
						jobObj.setScheduleDateTime(rs.getString("scheduled_date_time"));
						jobObj.setCheckInDateTime(rs.getString("checkin_date_time"));
						jobObj.setCheckOutDateTime(rs.getString("checkout_date_time"));
						customerObj.setCustomerName(rs.getString("customer_name"));
						customerObj.setCustomerMobNo(rs.getString("customer_mob_no"));
						clientObj.setClientName(rs.getString("client_name"));
						serviceTypeObj.setServiceTypeName(rs.getString("service_type_name"));
						efrObj.setEasyfixerName(rs.getString("efr_name"));
						efrObj.setIsAppUser(rs.getInt("is_app_user"));
						jobObj.setEasyfixterObj(efrObj);
						jobObj.setServiceTypeObj(serviceTypeObj);
						jobObj.setClientObj(clientObj);
						jobObj.setCustomerObj(customerObj);
						jobObj.setJobStatus(rs.getInt("job_status"));
						jobObj.setEmpName(rs.getString("user_name"));
						jobObj.setJobOwner(rs.getInt("job_owner"));
						
						if(jobObj.getJobOwner() == 0){
							timeDiff = dateTimeDiff(jobObj.getRequestedDateTime());
							jobObj.setTimeDifference(timeDiff);
							unownedJobs.add(jobObj);
						}
						else{
						if(jobObj.getJobStatus() == 0) {
							timeDiff = dateTimeDiff(jobObj.getRequestedDateTime());
							jobObj.setTimeDifference(timeDiff);
							scheduleList.add(jobObj);
						}
						if(jobObj.getJobStatus() == 1) {
							timeDiff = dateTimeDiff(jobObj.getRequestedDateTime());
							jobObj.setTimeDifference(timeDiff);
							checkInList.add(jobObj);
						}
						if(jobObj.getJobStatus() == 2) {
							timeDiff = dateTimeDiff(jobObj.getCheckInDateTime());
							jobObj.setTimeDifference(timeDiff);
							checkOutList.add(jobObj);
						}
						if(jobObj.getJobStatus() == 3) {
							timeDiff = dateTimeDiff(jobObj.getCheckOutDateTime());
							jobObj.setTimeDifference(timeDiff);
							feedbackList.add(jobObj);
						}
						if(jobObj.getJobStatus() == 9) {
							timeDiff = dateTimeDiff(jobObj.getRequestedDateTime());
							jobObj.setTimeDifference(timeDiff);
							callLaterList.add(jobObj);
						}
						if(jobObj.getJobStatus() == 10) {
							timeDiff = dateTimeDiff(jobObj.getRequestedDateTime());
							jobObj.setTimeDifference(timeDiff);
							appCheckOutJobs.add(jobObj);
						}
						if(jobObj.getJobStatus() == 15) {
							timeDiff = dateTimeDiff(jobObj.getRequestedDateTime());
							jobObj.setTimeDifference(timeDiff);
							approvalList.add(jobObj);
						}
						}
						
					}
					userJobObj.setScheduleList(scheduleList);
					userJobObj.setCheckInList(checkInList);
					userJobObj.setCheckOutList(checkOutList);
					userJobObj.setFeedbackList(feedbackList);
					userJobObj.setCallLaterList(callLaterList);
					userJobObj.setUnownedJobs(unownedJobs);
					userJobObj.setAppCheckoutJobs(appCheckOutJobs);
					userJobObj.setApprovalList(approvalList);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			logger.info("getUserJobsList failed for userid:"+userId);
			
		} finally {
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
		
		return userJobObj;
	}
	
	
	public long dateTimeDiff(String dateStart) throws Exception {
		long diffMinutes = 0;
		//System.out.println(dateStart+"==");
		
		if(dateStart == "" || dateStart == "0000-00-00 00:00:00" || dateStart == null)
		{
			diffMinutes = 0;
		}
		else {
		
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Date d1 = null;
			Date d2 = null;
			Date date = new Date();
	
			try {
				d1 = format.parse(dateStart);
				d2 = format.parse(format.format(date));
	
				//in milliseconds
				long diff = d1.getTime() - d2.getTime();
	
				//long diffSeconds = diff / 1000;
				diffMinutes = diff / 60000;
				//long diffHours = diff / (60 * 60 * 1000) % 24;
				//long diffDays = diff / (24 * 60 * 60 * 1000);
	
				/*System.out.print(diffDays + " days, ");
				System.out.print(diffHours + " hours, ");
				System.out.print(diffMinutes + " minutes, ");
				System.out.print(diffSeconds + " seconds.");*/
	
			} catch (Exception e) {
				e.printStackTrace();
				logger.catching(e);
				System.out.println(dateStart);
			}	
		}	
		
		return diffMinutes;		
	}

	@Override
	public List<Jobs> getClientJobsList(Jobs jobObj, int loginClient) throws Exception {
			List<Jobs> jobsList = new ArrayList<Jobs>();
				
			Connection conn = null;
			CallableStatement cstmt = null;
			ResultSet rs = null;
			Jobs jobsObj = null;	
			Clients clientObj =null;
			ServiceType serviceTypeObj = null;
			Customers customerObj = null;
			Easyfixers efrObj = null;
			try {
				conn = getJdbcTemplate().getDataSource().getConnection();
				if(conn!=null){
					cstmt = conn.prepareCall("{call sp_ef_user_client_job_list(?,?,?,?,?,?,?)}");
					cstmt.setInt(1, loginClient);
					cstmt.setInt(2, jobObj.getJobStatus());
					cstmt.setInt(3, jobObj.getFkCityId());
					cstmt.setInt(4, jobObj.getFkServiceTypeId());
					cstmt.setString(5, jobObj.getStartDate());
					cstmt.setString(6, jobObj.getEndDate());
					cstmt.setString(7, jobObj.getClientRefId());
					rs = cstmt.executeQuery();
					if(rs!=null){
						while(rs.next()){
							jobsObj = new Jobs();
							customerObj = new Customers();
							serviceTypeObj = new ServiceType();
							efrObj = new Easyfixers();
							
							jobsObj.setJobId(rs.getInt("job_id"));
							jobsObj.setClientServices(rs.getString("services"));
							jobsObj.setJobDesc(rs.getString("job_desc"));
							jobsObj.setFkCustomerId(rs.getInt("fk_customer_id"));
							jobsObj.setFkClientId(rs.getInt("fk_client_id"));
							jobsObj.setFkServiceTypeId(rs.getInt("fk_service_type_id"));
							jobsObj.setFkEasyfixterId(rs.getInt("fk_easyfixter_id"));
							jobsObj.setCreatedDateTime(rs.getString("created_date_time"));
							jobsObj.setRequestedDateTime(rs.getString("requested_date_time"));
							jobsObj.setScheduleDateTime(rs.getString("scheduled_date_time"));
							jobsObj.setCheckInDateTime(rs.getString("checkin_date_time"));
							jobsObj.setCheckOutDateTime(rs.getString("checkout_date_time"));
							jobsObj.setCancelDateTime(rs.getString("cancel_date_time"));
							jobsObj.setJobStatus(rs.getInt("job_status"));
							jobsObj.setJobOwner(rs.getInt("job_owner"));
							jobsObj.setEmpName(rs.getString("owner_user"));
							jobsObj.setCollectedBy(rs.getInt("collected_by"));
							jobsObj.setMaterialCharge(rs.getString("material_charge"));
							jobsObj.setClientRefId(rs.getString("client_ref_id"));
							customerObj.setCustomerName(rs.getString("customer_name"));
							customerObj.setCustomerMobNo(rs.getString("customer_mob_no"));
							efrObj.setEasyfixerNo(rs.getString("efr_no"));
							efrObj.setEasyfixerName(rs.getString("efr_name"));
							serviceTypeObj.setServiceTypeName(rs.getString("service_type_name"));
							jobsObj.setCustomerObj(customerObj);
							jobsObj.setEasyfixterObj(efrObj);
							jobsObj.setServiceTypeObj(serviceTypeObj);
							jobsList.add(jobsObj);
							
						}
					}
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.catching(e);
				
			} finally {
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
			
		return jobsList;
	}


	@Override
	public Jobs getUserJobsListFromExcel(int userId, int roleId) throws Exception {

		List<Jobs> jobLeadList = new ArrayList<Jobs>();
		
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Jobs userJobObj = new Jobs();
		long timeDiff = 0;
		
	//	long timeDiff = 0;
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
			/*DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();*/
			if(conn!=null){
				cstmt = conn.prepareCall("{call sp_ef_user_owner_job_list_from_excel_upload(?,?)}");
				cstmt.setInt(1, userId);
				cstmt.setInt(2, roleId);
				rs = cstmt.executeQuery();
				if(rs!=null){
					while(rs.next()){
						Jobs jobObj = new Jobs();	
						
						Clients clientObj = new Clients();
						ServiceType serviceTypeObj = new ServiceType();
						Customers customerObj = new Customers();;
						Address addObj = new Address();
						City cityObj=new City();

						
						jobObj.setJobId(rs.getInt("row_id"));
						jobObj.setJobDesc(rs.getString("job_desc"));
						jobObj.setClientRefId(rs.getString("ref_id"));
						jobObj.setClientServiceIds(rs.getString("services"));
						jobObj.setRequestedDateTime(rs.getString("req_date"));
//						jobObj.setCreatedDateTime(rs.getString("created_date_time"));
//						jobObj.setScheduleDateTime(rs.getString("scheduled_date_time"));
//						jobObj.setCheckInDateTime(rs.getString("checkin_date_time"));
//						jobObj.setCheckOutDateTime(rs.getString("checkout_date_time"));
						jobObj.setJobOwner(rs.getInt("owner"));
						cityObj.setCityName(rs.getString("city_name"));
						jobObj.setEmpName(rs.getString("user_name"));
						
						addObj.setAddress(rs.getString("address"));
					//	addObj.setGpsLocation(rs.getString("gps"));
						addObj.setPinCode(rs.getString("pin"));
						
						customerObj.setCustomerName(rs.getString("cus_name"));
						customerObj.setCustomerMobNo(rs.getString("mobile"));
						customerObj.setCustomerEmail(rs.getString("cust_email"));
						
					//	clientObj.setClientId(rs.getInt("client_id"));
						clientObj.setClientName(rs.getString("client_name"));
						
						//serviceTypeObj.setServiceTypeId(rs.getInt("service_type_id"));
						serviceTypeObj.setServiceTypeName(rs.getString("service_type"));
						
						addObj.setCityObj(cityObj);
						customerObj.setAddressObj(addObj);
						jobObj.setAddressObj(addObj);
						jobObj.setServiceTypeObj(serviceTypeObj);
						jobObj.setClientObj(clientObj);
						jobObj.setCustomerObj(customerObj);
						jobObj.setJobStatus(-1);
						timeDiff = dateTimeDiff(jobObj.getRequestedDateTime());
						jobObj.setTimeDifference(timeDiff);
						
						jobLeadList.add(jobObj);
						
						
					}
					userJobObj.setJobLeadList(jobLeadList);
					
			}
			
		}
		}
			catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
			
		} finally {
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
		
		return userJobObj;
	}

	@Override
	public void deactiveLeadJob(int jobId, String comments, int userId) throws Exception {
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
			if(conn!=null){
				cstmt = conn.prepareCall("{call sp_ef_deactivate_lead_job(?,?,?)}");
				if(cstmt!=null){
					cstmt.setInt(1, jobId);
					cstmt.setString(2, comments);
					cstmt.setInt(3, userId);
					cstmt.executeUpdate();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(cstmt!=null)
				cstmt.close();
			if(conn!=null)
				conn.close();
		}
		
	}

	@Override
	public Jobs getAppjobList(int userId, int roleId) throws Exception {
		List<Jobs> scheduleList = new ArrayList<Jobs>();
		List<Jobs> checkInList = new ArrayList<Jobs>();
		List<Jobs> checkOutList = new ArrayList<Jobs>();
		List<Jobs> feedbackList = new ArrayList<Jobs>();
		List<Jobs> callLaterList = new ArrayList<Jobs>();
		
		
		
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Jobs jobObj = null;	
		Jobs userJobObj = null;
		userJobObj = new Jobs();
		Clients clientObj =null;
		ServiceType serviceTypeObj = null;
		Customers customerObj = null;
		Easyfixers efrObj = null;
		long timeDiff = 0;
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
			/*DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();*/
			if(conn!=null){
				cstmt = conn.prepareCall("{call sp_ef_app_job_list(?,?)}");
				cstmt.setInt(1, userId);
				cstmt.setInt(2, roleId);
				rs = cstmt.executeQuery();
				if(rs!=null){
					while(rs.next()){
						jobObj = new Jobs();
						customerObj = new Customers();
						clientObj = new Clients();
						serviceTypeObj = new ServiceType();
						efrObj = new Easyfixers();
						
						jobObj.setJobId(rs.getInt("job_id"));
						jobObj.setJobDesc(rs.getString("job_desc"));
						jobObj.setRequestedDateTime(rs.getString("requested_date_time"));
						jobObj.setCreatedDateTime(rs.getString("created_date_time"));
						jobObj.setScheduleDateTime(rs.getString("scheduled_date_time"));
						jobObj.setCheckInDateTime(rs.getString("checkin_date_time"));
						jobObj.setCheckOutDateTime(rs.getString("checkout_date_time"));
						customerObj.setCustomerName(rs.getString("customer_name"));
						customerObj.setCustomerMobNo(rs.getString("customer_mob_no"));
						clientObj.setClientName(rs.getString("client_name"));
						serviceTypeObj.setServiceTypeName(rs.getString("service_type_name"));
						efrObj.setEasyfixerName(rs.getString("efr_name"));
						jobObj.setEasyfixterObj(efrObj);
						jobObj.setServiceTypeObj(serviceTypeObj);
						jobObj.setClientObj(clientObj);
						jobObj.setCustomerObj(customerObj);
						jobObj.setJobStatus(rs.getInt("job_status"));
						jobObj.setEmpName(rs.getString("user_name"));
					//	System.out.println(jobObj);
						if(jobObj.getJobStatus() == 0) {
							timeDiff = dateTimeDiff(jobObj.getRequestedDateTime());
							jobObj.setTimeDifference(timeDiff);
							scheduleList.add(jobObj);
						}
						if(jobObj.getJobStatus() == 1) {
							timeDiff = dateTimeDiff(jobObj.getRequestedDateTime());
							jobObj.setTimeDifference(timeDiff);
							checkInList.add(jobObj);
						}
						if(jobObj.getJobStatus() == 2) {
							timeDiff = dateTimeDiff(jobObj.getCheckInDateTime());
							jobObj.setTimeDifference(timeDiff);
							checkOutList.add(jobObj);
						}
						if(jobObj.getJobStatus() == 3 || jobObj.getJobStatus() == 4||jobObj.getJobStatus() == 5) {
							timeDiff = dateTimeDiff(jobObj.getCheckOutDateTime());
							jobObj.setTimeDifference(timeDiff);
							feedbackList.add(jobObj);
						}
						if(jobObj.getJobStatus() == 9) {
							timeDiff = dateTimeDiff(jobObj.getRequestedDateTime());
							jobObj.setTimeDifference(timeDiff);
							callLaterList.add(jobObj);
						}
						
					}
					userJobObj.setScheduleList(scheduleList);
					userJobObj.setCheckInList(checkInList);
					userJobObj.setCheckOutList(checkOutList);
					userJobObj.setFeedbackList(feedbackList);
					userJobObj.setCallLaterList(callLaterList);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
			logger.info("getUserJobsList failed for userid:"+userId);
			
		} finally {
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
		
		return userJobObj;
	}
	
	

}
