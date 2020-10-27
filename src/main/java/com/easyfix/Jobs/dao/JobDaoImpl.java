package com.easyfix.Jobs.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jfree.util.Log;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.easyfix.Jobs.model.AppNotications;
import com.easyfix.Jobs.model.CustomerFeedback;
import com.easyfix.Jobs.model.JobSelectedServices;
import com.easyfix.Jobs.model.Jobs;
import com.easyfix.Jobs.model.Notification;
import com.easyfix.Jobs.model.Recipients;
import com.easyfix.Jobs.model.Sms;
import com.easyfix.Jobs.model.email;
import com.easyfix.clients.model.Clients;
import com.easyfix.customers.model.Address;
import com.easyfix.customers.model.Customers;
import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.settings.model.City;
import com.easyfix.settings.model.ServiceType;
import com.easyfix.user.model.User;
import com.easyfix.util.DBConfig;

public class JobDaoImpl extends JdbcDaoSupport implements JobDao{
	private static final Logger logger = LogManager.getLogger(JobDaoImpl.class);
	private SessionFactory sessionFactory;
	
	public Jobs getJobDetails(int jobId) throws Exception {
		Connection conn=null;
		CallableStatement cstmt=null;
		ResultSet rs=null ;
		Jobs jobObj = new Jobs();
		Customers custObj = new Customers();
		Clients clientObj = new Clients();
		Easyfixers easyfixerObj = new Easyfixers();
		ServiceType serviceTypeObj = new ServiceType();
		Address addressObj = new Address();
		City cityObj = new City();
		CustomerFeedback feedObj = new CustomerFeedback();
		User owner = new User();
		User ownerChangeBy = new User();
		User scheduleBy = new User();
		User checkInBy = new User();
		User checkOutBy = new User();
		try{
			DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();
			cstmt=conn.prepareCall("call sp_ef_jobs_getjob_details(?)");
			cstmt.setInt(1, jobId);
			rs = cstmt.executeQuery();
			if(rs != null) {
				while(rs.next()){					
					
					jobObj.setJobId(rs.getInt("job_id"));
					jobObj.setClientServices(rs.getString("services"));
					jobObj.setJobDesc(rs.getString("job_desc"));
					jobObj.setFkCustomerId(rs.getInt("fk_customer_id"));
					jobObj.setFkAddressId(rs.getInt("fk_address_id"));
					jobObj.setFkClientId(rs.getInt("fk_client_id"));
					jobObj.setFkServiceTypeId(rs.getInt("fk_service_type_id"));
					jobObj.setFkEasyfixterId(rs.getInt("fk_easyfixter_id"));
					jobObj.setFkCreatedBy(rs.getInt("fk_created_by"));
					jobObj.setCreatedDateTime(rs.getString("created_date_time"));
					jobObj.setRequestedDateTime(rs.getString("requested_date_time"));
					jobObj.setScheduleDateTime(rs.getString("scheduled_date_time"));
					jobObj.setCheckInDateTime(rs.getString("checkin_date_time"));
					jobObj.setCheckOutDateTime(rs.getString("checkout_date_time"));
					jobObj.setClientServiceIds(rs.getString("client_services"));
					jobObj.setJobStatus(rs.getInt("job_status"));
					jobObj.setPaidBy(rs.getInt("paid_by"));
					jobObj.setCollectedBy(rs.getInt("collected_by"));
					jobObj.setMaterialCharge(rs.getString("material_charge"));
					jobObj.setClientRefId(rs.getString("client_ref_id"));
					jobObj.setTotalServiceAmount(rs.getString("total_charge"));
					jobObj.setEasyfixAmount(rs.getString("easyfix_charge"));
					jobObj.setEasyfixerAmount(rs.getString("easyfixer_charge"));
					jobObj.setClientAmount(rs.getString("client_charge"));
					
					jobObj.setEnumDesc(rs.getString("enum_desc"));
					jobObj.setCancleComment(rs.getString("cancel_comment"));
					jobObj.setEnumId(rs.getInt("cancel_reason_id"));
					jobObj.setComments(rs.getString("enquiry_comment"));
					jobObj.setCanceledOn(rs.getInt("cancel_on"));
					jobObj.setCancelDateTime(rs.getString("cancel_date_time"));
					jobObj.setOwnerChangeDateTime(rs.getString("owner_change_date"));
					jobObj.setMobileNo(rs.getString("customer_mob_no"));
					jobObj.setSkillId(rs.getInt("skill_id"));
					jobObj.setOtp(rs.getString("otp"));
					custObj.setCustomerName(rs.getString("customer_name"));
					jobObj.setAdditionalName(rs.getString("additional_name"));
					custObj.setCustomerMobNo(rs.getString("customer_mob_no"));
					custObj.setCustomerEmail(rs.getString("customer_email"));
					clientObj.setClientName(rs.getString("client_name"));
					clientObj.setPaidBy(rs.getInt("client_paid_by"));
					clientObj.setCollectedBy(rs.getInt("client_collected_by"));
					clientObj.setTravelDist(rs.getInt("travel_distance"));
					easyfixerObj.setEasyfixerNo(rs.getString("efr_no"));
					easyfixerObj.setEasyfixerName(rs.getString("efr_name"));
					easyfixerObj.setCurrentBalance(rs.getFloat("current_balance"));
					serviceTypeObj.setServiceTypeName(rs.getString("service_type_name"));
					addressObj.setAddressId(rs.getInt("fk_address_id"));
					addressObj.setAddress(rs.getString("address"));					
					addressObj.setPinCode(rs.getString("pin_code"));	
					addressObj.setGpsLocation(rs.getString("gps_location"));
					cityObj.setCityId(rs.getInt("city_id"));
					cityObj.setCityName(rs.getString("city_name"));
					cityObj.setMaxDistance(rs.getString("city_max_distance"));
					feedObj.setEfRating(rs.getInt("easyfix_rating"));
					feedObj.setEfrRating(rs.getInt("easyfixer_rating"));
					feedObj.setHappyWithService(rs.getString("happy_with_service"));
					owner.setUserName(rs.getString("job_owner_name"));
					owner.setOfficialEmail(rs.getString("job_owner_email"));
					owner.setMobileNo(rs.getString("job_owner_mobile_no"));
					ownerChangeBy.setUserName(rs.getString("job_owner_changer"));
					scheduleBy.setUserName(rs.getString("schedule_by"));
					checkInBy.setUserName(rs.getString("checkIn_by"));
					checkOutBy.setUserName(rs.getString("checkOut_by"));
					
					addressObj.setCityObj(cityObj);
					jobObj.setCustomerObj(custObj);
					jobObj.setClientObj(clientObj);
					jobObj.setEasyfixterObj(easyfixerObj);
					jobObj.setServiceTypeObj(serviceTypeObj);
					jobObj.setAddressObj(addressObj);
					jobObj.setCustomerFeedback(feedObj);
					jobObj.setOwner(owner);
					jobObj.setOwnerChangedByObj(ownerChangeBy);
					jobObj.setJobScheduledBy(scheduleBy);
					jobObj.setJobCheckInBy(checkInBy);
					jobObj.setJobCheckOutBy(checkOutBy);
				}				
			}
		}
		catch(Exception e){
			e.printStackTrace();
			logger.catching(e);
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
		return jobObj;
	}

	@Override
	public String addUpdateJob(Jobs jobsObj, List<String> list, int userId) throws Exception {
		Connection conn=null;
		CallableStatement cstmt=null;
		CallableStatement cstmtCust=null;
		CallableStatement cstmtAdd = null;
		CallableStatement cstmtServ = null;
		CallableStatement cstmtImage = null;
		int status=0;
		int customerId=0;
		int addressId=0;
		int jobId=0;
		int imageId =0;
		try{
			conn = getJdbcTemplate().getDataSource().getConnection();
			conn.setAutoCommit(false);
			if(jobsObj.getFkCustomerId() == 0) {
				cstmtCust=conn.prepareCall("call sp_ef_customer_add_update_customer(?,?,?,?,?,?,?)");
				cstmtCust.setInt(1,jobsObj.getFkCustomerId());
				cstmtCust.setString(2,jobsObj.getMobileNo());
				cstmtCust.setString(3,jobsObj.getCustomerObj().getCustomerName());
				cstmtCust.setString(4,jobsObj.getCustomerObj().getCustomerEmail());
				cstmtCust.setInt(5,1); //Customer Status
				cstmtCust.setInt(6, userId);
				cstmtCust.registerOutParameter(7, Types.INTEGER);
				cstmtCust.executeUpdate();
				customerId=cstmtCust.getInt(7);
				jobsObj.setFkCustomerId(customerId);
				status = 1;
			}
			if(jobsObj.getFkAddressId() == 0) {
				cstmtAdd=conn.prepareCall("call  sp_ef_address_add_update_address(?,?,?,?,?,?,?,?)");
				cstmtAdd.setInt(1, jobsObj.getFkAddressId());
				cstmtAdd.setInt(2, jobsObj.getFkCustomerId());
				cstmtAdd.setString(3,jobsObj.getAddressObj().getAddress());
				cstmtAdd.setInt(4,jobsObj.getAddressObj().getCityObj().getCityId());
				cstmtAdd.setString(5,jobsObj.getAddressObj().getPinCode());
				cstmtAdd.setString(6,jobsObj.getAddressObj().getGpsLocation());		
				cstmtAdd.setInt(7, userId);
				cstmtAdd.registerOutParameter(8, Types.INTEGER);	
				cstmtAdd.executeUpdate();
				addressId=cstmtAdd.getInt(8);				
				jobsObj.setFkAddressId(addressId);
				status = 2;
			}
			if(jobsObj.getFkCustomerId() > 0 && jobsObj.getFkAddressId() > 0){
				cstmt=conn.prepareCall("call sp_ef_job_add_new_job(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				//cstmt.setInt(1,jobsObj.getJobId());
				cstmt.setInt(1,0); // jobId = 0 for new job
				cstmt.setString(2,jobsObj.getJobDesc());
				cstmt.setInt(3,jobsObj.getFkCustomerId());
				cstmt.setInt(4,jobsObj.getFkAddressId());
				cstmt.setInt(5,jobsObj.getFkClientId());
				cstmt.setInt(6,jobsObj.getFkServiceTypeId());
				cstmt.setString(7,jobsObj.getClientServices());
				cstmt.setString(8,jobsObj.getRequestedDateTime());
				cstmt.setInt(9, userId);
				cstmt.setInt(10, jobsObj.getJobStatus());
				cstmt.setString(11, jobsObj.getClientRefId());
				cstmt.setInt(12, jobsObj.getEnumId());
				cstmt.setString(13, jobsObj.getComments());
				cstmt.setInt(14, jobsObj.getCollectedBy());
				cstmt.setInt(15, jobsObj.getSkillId());
				cstmt.setString(16, jobsObj.getAdditionalName());
				cstmt.setString(17,jobsObj.getJobType());
				cstmt.registerOutParameter(18, Types.INTEGER);
				cstmt.executeUpdate();
				jobId=cstmt.getInt(18);					
				status = 3;
			}
			if(jobId > 0){
				cstmtServ = conn.prepareCall("{call sp_ef_add_update_job_service(?,?)}");
				cstmtServ.setInt(1, jobId);
				for (int i = 0; i < list.size(); i++) {					
					cstmtServ.setInt(2, Integer.parseInt(list.get(i)));
					cstmtServ.executeUpdate();					
					}
				status = 4;
				}	
			if(jobId > 0 && jobsObj.getJobImageName()!=null && jobsObj.getJobImage()!=null){
				cstmtImage = conn.prepareCall("{call sp_ef_job_add_new_job_image(?,?,?,?)}");
				cstmtImage.setInt(1, jobId);
				cstmtImage.setInt(2, 0);
				cstmtImage.setString(3, jobsObj.getJobImageName());
				cstmtImage.registerOutParameter(4, Types.INTEGER);
				cstmtImage.executeUpdate();
				imageId = cstmtImage.getInt(4);
				status = 4;
				}
			
			conn.commit();
		
		}
		catch(Exception e){
			try {
				conn.rollback();
				logger.info("Rollback occure in jobdaoImpl addupdatejob for job::"+jobsObj.getJobId());
			} catch (SQLException e2) {
				e2.printStackTrace();
				logger.catching(e2);
			}
			e.printStackTrace();
			logger.catching(e);
		}
		
			finally {
				  if (cstmt != null) {
		                try {
		                	cstmt.close();
		                } catch (SQLException e) { /*ignored*/ }
		            }
	            if (cstmtCust != null) {
	                try {
	                	cstmtCust.close();
	                } catch (SQLException e) { /*ignored*/ }
	            }
	            if (cstmtAdd != null) {
	                try {
	                	cstmtAdd.close();
	                } catch (SQLException e) { /*ignored*/ }
	            }
	            if (cstmtServ != null) {
	                try {
	                	cstmtServ.close();
	                } catch (SQLException e) { /*ignored*/ }
	            }
	          
	            if (conn != null) {
	                try {
	                    conn.close();
	                } catch (SQLException e) { /*ignored*/ }
	           
	        }
		}
		
		
		return status +","+ jobId;
	}

	@Override
	public List<Jobs> getJobList(int flag) throws Exception {
		List<Jobs> jobList = new ArrayList<Jobs>();
		Connection conn=null;
		CallableStatement cstmt=null;
		ResultSet rs = null;
		try{
			//conn = getJdbcTemplate().getDataSource().getConnection();
			DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();
			cstmt=conn.prepareCall("call sp_ef_jobs_getjoblist(?)");
			cstmt.setInt(1, flag);
			rs = cstmt.executeQuery();
			if(rs != null) {
				while(rs.next()){
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
					jobObj.setCancelDateTime(rs.getString("cancel_date_time"));
					jobObj.setJobStatus(rs.getInt("job_status"));
					jobObj.setJobOwner(rs.getInt("job_owner"));
					jobObj.setEmpName(rs.getString("owner_user"));
					jobObj.setCollectedBy(rs.getInt("collected_by"));
					jobObj.setMaterialCharge(rs.getString("material_charge"));
					jobObj.setClientRefId(rs.getString("client_ref_id"));
					jobObj.setAdditionalName(rs.getString("additional_name"));
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
					
					jobList.add(jobObj);
				}				
			}
		}
		catch(Exception e){
			e.printStackTrace();
			logger.catching(e);
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

	@Override
	public List<Easyfixers> getEasyfixerListByJobTypeId(int jobId, int jobTypeId, int clientId) throws Exception {
		
		List<Easyfixers> easyfixerList = new ArrayList<Easyfixers>();
		try{
		String query = "call sp_ef_easyfixer_geteasyfixerlist_by_servicetype_client_balance(?,?,?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		easyfixerList = getJdbcTemplate().query(query, new Object[]{jobId,jobTypeId,clientId}, new RowMapper<Easyfixers>(){			
			
			public Easyfixers mapRow(ResultSet rs, int row) throws SQLException {
				
				Easyfixers easyfixerObj = new Easyfixers();
				City cityObj = new City();
				
				easyfixerObj.setEasyfixerId(rs.getInt("efr_id"));
				easyfixerObj.setEasyfixerName(rs.getString("efr_name"));
				easyfixerObj.setEasyfixerNo(rs.getString("efr_no"));
				easyfixerObj.setEasyfixerAlterNo(rs.getString("efr_alt_no"));
				easyfixerObj.setEasyfixerEmail(rs.getString("efr_email"));
				easyfixerObj.setEasyfixerAddress(rs.getString("efr_address"));
				easyfixerObj.setEasyfixerCityId(rs.getInt("efr_cityId"));				
				easyfixerObj.setEasyfixerPin(rs.getString("efr_pin_no"));
				easyfixerObj.setEasyfixerBaseGps(rs.getString("efr_base_gps"));
				easyfixerObj.setEasyfixerCurrentGps(rs.getString("efr_current_gps"));
				easyfixerObj.setEasyfixerType(rs.getString("efr_type"));
				easyfixerObj.setEasyfixerServiceType(rs.getString("efr_service_type"));
				easyfixerObj.setIsActive(rs.getInt("efr_status"));
				easyfixerObj.setCustomerRating(rs.getString("customer_rating"));
				easyfixerObj.setAuditRating(rs.getString("audit_rating"));
				easyfixerObj.setDailyCounter(rs.getString("daily_counter"));
				easyfixerObj.setAssignStatus(rs.getInt("reject"));
				easyfixerObj.setCurrentBalance(rs.getFloat("current_balance"));	
				easyfixerObj.setIsAppUser(rs.getInt("is_app_user"));
				easyfixerObj.setDeviceId(rs.getString("device_id"));
				easyfixerObj.setCityZoneName(rs.getString("zone_name"));
				cityObj.setCityName(rs.getString("city_name"));
				cityObj.setMaxDistance(rs.getString("city_max_distance"));
				
				return easyfixerObj; 
				}
							
			});		        
		}
		catch(Exception e){
			e.printStackTrace();
			logger.catching(e);
		}
		return easyfixerList;
	}

	
	
	public void JobApproveDetails(List<JobSelectedServices> JobSelectedServicesList,Jobs job) throws Exception {
		Connection conn=null;
		CallableStatement cstmt=null;
		CallableStatement cstmt1=null;
	try {
		conn = getJdbcTemplate().getDataSource().getConnection();
		if(!conn.isClosed()){
			cstmt = conn.prepareCall("{call sp_ef_update_job_service_list(?,?,?,?)}");
			for(JobSelectedServices el : JobSelectedServicesList) {
						cstmt.setInt(1, el.getJobServiceId());
						cstmt.setInt(2, Integer.parseInt(el.getTotalCharge()));
						cstmt.setString(3, el.getServiceDescription());
						cstmt.setInt(4, el.getServiceMaterialCharge());
						cstmt.addBatch();
			}
			cstmt.executeBatch();
			
			
			cstmt1 = conn.prepareCall("UPDATE tbl_job SET job_status=? where job_id=?");
			cstmt1.setInt(1, 15);
			cstmt1.setInt(2, job.getJobId());
			cstmt1.executeUpdate();
		}	
	}
	catch(Exception e){
		e.printStackTrace();
		logger.catching(e);
	}
	finally{
		cstmt.close();
		cstmt1.close();
		conn.close();
	}
	}
	
	
	
	
	
	public void UpdateJobServiceDetails(List<JobSelectedServices> JobSelectedServicesList) throws Exception {
		Connection conn=null;
		CallableStatement cstmt=null;
	try {
		conn = getJdbcTemplate().getDataSource().getConnection();
		if(!conn.isClosed()){
			cstmt = conn.prepareCall("{call sp_ef_update_job_service_list(?,?,?,?)}");
			for(JobSelectedServices el : JobSelectedServicesList) {
						cstmt.setInt(1, el.getJobServiceId());
						cstmt.setInt(2, Integer.parseInt(el.getTotalCharge()));
						cstmt.setString(3, el.getServiceDescription());
						cstmt.setInt(4, el.getServiceMaterialCharge());
						cstmt.addBatch();
			}
			cstmt.executeBatch();
			
		}	
	}
	catch(Exception e){
		e.printStackTrace();
		logger.catching(e);
	}
	finally{
		cstmt.close();
		conn.close();
	}
	}
	
	
	
	
	@Override
	public List<JobSelectedServices> getJobServiceList(int jobId, int serviceStatus) throws Exception {
		List<JobSelectedServices> jobServiceList = new ArrayList<JobSelectedServices>();
		
		try{
		String query = "call sp_ef_job_get_job_service_list(?,?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		jobServiceList = getJdbcTemplate().query(query, new Object[]{jobId,serviceStatus}, new RowMapper<JobSelectedServices>(){			
			
			public JobSelectedServices mapRow(ResultSet rs, int row) throws SQLException {
				
				JobSelectedServices jobServiceObj = new JobSelectedServices();
				jobServiceObj.setJobServiceId(rs.getInt("job_service_id"));
				jobServiceObj.setJobId(rs.getInt("job_id"));
				jobServiceObj.setServiceId(rs.getInt("service_id"));
				jobServiceObj.setServiceName(rs.getString("crc_ratecard_name"));
				jobServiceObj.setTotalCharge(rs.getString("total_charge"));
				jobServiceObj.setEasyfixCharge(rs.getString("easyfix_charge"));
				jobServiceObj.setEasyfixerCharge(rs.getString("easyfixer_charge"));
				jobServiceObj.setClientCharge(rs.getString("client_charge"));				
				jobServiceObj.setJobServiceStatus(rs.getInt("job_service_status"));
				jobServiceObj.setJobChargeType(rs.getInt("job_charge_type"));
				jobServiceObj.setServiceDescription(rs.getString("service_charge_description"));				
				jobServiceObj.setServiceMaterialCharge(rs.getInt("material_charge"));
				return jobServiceObj; 
				}
							
			});		        
		}
		catch(Exception e){
			e.printStackTrace();
			logger.catching(e);
		}
		return jobServiceList;
	}

	@Override
	public int changeRequestedDate(Jobs jobsObj) throws Exception {
		int status = 0;
		Connection conn = null;
		CallableStatement cstmt = null;
		
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
			if(conn!=null){
				cstmt = conn.prepareCall("{call sp_ef_job_update_requested_date(?,?,?,?,?)}");
				cstmt.setInt(1, jobsObj.getJobId());
				cstmt.setString(2, jobsObj.getRequestedDateTime());
				cstmt.setInt(3, jobsObj.getFkCreatedBy());
				cstmt.setString(4, jobsObj.getComments());
				cstmt.registerOutParameter(5, Types.INTEGER);
				cstmt.executeUpdate();
				status = cstmt.getInt(5);
			}			
			
		} catch (Exception e) {		
			e.printStackTrace();
			logger.catching(e);
		} finally{
			cstmt.close();
			conn.close();
		}
		
		return status;
	}

	@Override
	public int changeJobDesc(Jobs jobsObj) throws Exception {
		int status = 0;
		Connection conn = null;
		CallableStatement cstmt = null;
		
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
			if(conn!=null){
				cstmt = conn.prepareCall("{call sp_ef_job_update_job_desc(?,?,?,?,?)}");
				cstmt.setInt(1, jobsObj.getJobId());
				cstmt.setString(2, jobsObj.getJobDesc());
				cstmt.setInt(3, jobsObj.getFkCreatedBy());
				cstmt.setString(4, jobsObj.getComments());
				cstmt.registerOutParameter(5, Types.INTEGER);
				cstmt.executeUpdate();
				status = cstmt.getInt(5);
			}			
			
		} catch (Exception e) {		
			e.printStackTrace();
			logger.catching(e);
		} finally{
			cstmt.close();
			conn.close();
		}
		
		return status;
	}

	@Override
	public void updateJobServices(int jobId, String clientServiceIds, List<String> serviceList) throws Exception {
		try {
			String sql = "call sp_ef_job_update_job_service(?,?)";
			String sql1 = "call sp_ef_add_update_job_service(?,?)";
			DataSource ds=DBConfig.getContextDataSource();			
			getJdbcTemplate().setDataSource(ds);
			
			getJdbcTemplate().update(sql, new Object[]{jobId, clientServiceIds});
			
			for (int i = 0; i < serviceList.size(); i++) {
				getJdbcTemplate().update(sql1, new Object[]{jobId, Integer.parseInt(serviceList.get(i))});				
				}			
			
		} catch (Exception e) {	
			e.printStackTrace();
			logger.catching(e);
			}
		
	}

	@Override
	public void saveScheduleJob(int jobId, int fkEasyfixterId, int userId,String disTravelled, String flag) throws Exception {
		try {
			String sql = "call sp_ef_job_save_schedule_job(?,?,?,?)";
			DataSource ds=DBConfig.getContextDataSource();			
			getJdbcTemplate().setDataSource(ds);
			getJdbcTemplate().update(sql, new Object[]{jobId, fkEasyfixterId, userId, disTravelled});
			logger.info(userId+ " saved job scheduling in db for job: "+jobId);		
			
			if("schedule".equals(flag)){
			String query = "INSERT INTO scheduling_history( job_id,easyfixer_id,schedule_time) VALUES(?,?,NOW())";
		//	conn = getJdbcTemplate().update(query, new Object[]{(jobsObj.getJobId())});
			getJdbcTemplate().update(query, new Object[]{jobId,fkEasyfixterId});
			}
			else if("reschedule".equals(flag)){
			String query1 = "UPDATE scheduling_history	SET easyfixer_id=?, schedule_time=NOW()	WHERE job_id =? ORDER BY id DESC LIMIT 1 ";
			//	conn = getJdbcTemplate().update(query, new Object[]{(jobsObj.getJobId())});
				getJdbcTemplate().update(query1, new Object[]{fkEasyfixterId,jobId});
					

			}
			
		} catch (Exception e) {	
			e.printStackTrace();
			logger.catching(e);
			}
		
				
	}

	@Override
	public void saveCheckInJob(int jobId, int userId) throws Exception {
		Connection conn = null;
		CallableStatement cstmt = null;
		
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
			conn.setAutoCommit(false);
			if(conn!=null){
				cstmt = conn.prepareCall("{call sp_ef_job_update_job(?,?,?)}");
				cstmt.setInt(1, jobId);
				cstmt.setInt(2, userId);
				cstmt.setString(3, "CheckIn");
				cstmt.executeUpdate();
				
			}
			conn.commit();
			logger.info(userId+ " saved job scheduling in db for job: "+jobId);
			
		} catch (Exception e) {	
			try {
				conn.rollback();
				logger.info("Rollback occure in jobdaoImpl addupdatejob for job "+jobId);
			} catch (SQLException e2) {
				e2.printStackTrace();
				logger.catching(e2);
				}
			e.printStackTrace();
			logger.catching(e);
			
		} finally{
			if(cstmt!=null){
				try {
					cstmt.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		
			if(conn!=null){
				try {
					conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			}
		}		
		
	}
	
	
	
	@Override
	public void saveApproveJob(int jobId) throws Exception {
		Connection conn = null;
		CallableStatement cstmt = null;
		
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
			conn.setAutoCommit(false);
			if(conn!=null){
				cstmt = conn.prepareCall("UPDATE tbl_job SET job_status=? where job_id=?");
				cstmt.setInt(1, 2);
				cstmt.setInt(2, jobId);
				cstmt.executeUpdate();
				
			}
			conn.commit();

		} catch (Exception e) {	
			e.printStackTrace();
			logger.catching(e);
			
		} finally{
			if(cstmt!=null){
				try {
					cstmt.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
				
			if(conn!=null){
				try {
					conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
			}
		}		
		
	}
	
	

	@Override
	public Clients getjobServiceDetailsByJobServiceId(int jobServiceId)	throws Exception {		
		Connection conn=null;
		CallableStatement cstmt=null;
		ResultSet rs = null;
		
		Clients clientObj = null;
		try{
			//conn = getJdbcTemplate().getDataSource().getConnection();
			DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();
			cstmt=conn.prepareCall("call sp_ef_job_get_job_service_details_by_jobserviceid(?)");
			cstmt.setInt(1, jobServiceId);
			rs = cstmt.executeQuery();
			
			if(rs != null) {
				while(rs.next()){
					clientObj = new Clients();					
					clientObj.setClientId(rs.getInt("client_id"));
					clientObj.setChargeType(rs.getInt("charge_type"));
					clientObj.setTotalCharge(rs.getString("total_amount"));
					clientObj.setEasyfixDirectFixed(rs.getString("easyfix_direct_fixed"));
					clientObj.setEasyfixDirectVariable(rs.getString("easyfix_direct_variable"));
					clientObj.setOverheadFixed(rs.getString("overhead_fixed"));
					clientObj.setOverheadVariable(rs.getString("overhead_variable"));
					clientObj.setClientFixed(rs.getString("client_fixed"));
					clientObj.setClientVariable(rs.getString("client_variable"));				
					
				}				
			}
		}
		catch(Exception e){
			e.printStackTrace();
			logger.catching(e);
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
		
		return clientObj;
	}
	
	
	@Override
	public void saveJobServiceAmount(JobSelectedServices jobSelectObj)	throws Exception {
		Connection conn=null;
		CallableStatement cstmtServices=null;
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
			cstmtServices=conn.prepareCall("{call sp_ef_update_job_service_all_amount(?,?,?,?,?)}");
			if(cstmtServices!=null){
				cstmtServices.setInt(1, jobSelectObj.getJobServiceId());
				cstmtServices.setString(2, jobSelectObj.getTotalChargeAfterTax());
				cstmtServices.setString(3, jobSelectObj.getEasyfixCharge());
				cstmtServices.setString(4, jobSelectObj.getEasyfixerCharge());
				cstmtServices.setString(5, jobSelectObj.getClientCharge());
				cstmtServices.executeUpdate();				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}finally{
			if(cstmtServices!=null){
				try {
					cstmtServices.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		}
		
	}
	@Override
	public int updateCheckoutOtp(int jobId, String otp) throws Exception {
				
		//	conn = getJdbcTemplate().getDataSource().getConnection();
			String q = "update tbl_job set otp =? where job_id = ?"; 
			int updatedRow = getJdbcTemplate().update(q, new Object[]{otp,jobId});
			return updatedRow;
				
	}
	@Override
	public void updateTransactionFromInvoice(Jobs jobsObj) throws Exception{
		Connection conn=null;
		CallableStatement cstmtCheckOut = null;
		CallableStatement cstmt = null;
		int status=0;
		int checkOutSts=0;
		int transType = 0;
		int source = 1; // 1 for system
		String desc = "Job Id : "+jobsObj.getJobId();
		/*if(jobsObj.getCollectedBy() == 1) // 1 for easyfixer 2 for easyfix, 3 for client
			transType = 1;
		else 
			transType = 2;*/
		try{
			conn = getJdbcTemplate().getDataSource().getConnection();
			String sql = "call delete_job_data(?)";
			  int insertId = getJdbcTemplate().update(sql, new Object[]{jobsObj.getJobId()});
		//	conn.setAutoCommit(false);
			if(conn!=null) {
				/*cstmtCheckOut=conn.prepareCall("{call sp_ef_job_checkout_job(?,?,?,?,?)}");
				cstmtCheckOut.setInt(1, jobsObj.getJobId());
				cstmtCheckOut.setInt(2, jobsObj.getPaidBy());
				cstmtCheckOut.setInt(3, jobsObj.getCollectedBy());
				cstmtCheckOut.setString(4, jobsObj.getMaterialCharge());
				cstmtCheckOut.setInt(5, jobsObj.getFkCheckOutBy());
				cstmtCheckOut.executeUpdate();
				checkOutSts = 1;
			}
			if(checkOutSts == 1){*/
				cstmt = conn.prepareCall("{call sp_ef_update_transaction_from_invoice(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				cstmt.setInt(1, jobsObj.getFkEasyfixterId());
				cstmt.setDouble(2, Double.parseDouble(jobsObj.getEasyfixerAmount()));
				cstmt.setDouble(3, Double.parseDouble(jobsObj.getEasyfixAmount()));
				cstmt.setDouble(4, Double.parseDouble(jobsObj.getClientAmount()));
				cstmt.setInt(5, jobsObj.getCollectedBy());
				cstmt.setInt(6, source);
				cstmt.setString(7, desc);
				cstmt.setInt(8, jobsObj.getFkCheckOutBy());
				cstmt.setInt(9, jobsObj.getJobId());
				cstmt.setInt(10, jobsObj.getFkClientId());
				cstmt.setInt(11, jobsObj.getPaidBy());
				cstmt.setInt(12, Integer.parseInt(jobsObj.getMaterialCharge()));
				cstmt.setDouble(13, Double.parseDouble(jobsObj.getTotalServiceTax()));
				cstmt.registerOutParameter(14, Types.INTEGER);
				cstmt.executeQuery();
				status = cstmt.getInt(14);
			}
		//	conn.commit();
			System.out.println("committed");
			logger.info("checkout committed jobid: "+jobsObj.getJobId());
						
		}
		catch(Exception e){
			try{
				conn.rollback();
				System.out.println("roll backed");
				logger.info("roll backed job "+jobsObj.getJobId());
			}catch(Exception ex){
				ex.printStackTrace();
				logger.catching(ex);
			}
			e.printStackTrace();
			logger.catching(e);
		}
		
			finally {
				  
	            if (cstmtCheckOut != null) {
	                try {
	                	cstmtCheckOut.close();
	                } catch (SQLException e) { /*ignored*/ }
	            }
	            if (cstmt != null) {
	                try {
	                	cstmt.close();
	                } catch (SQLException e) { /*ignored*/ }
	            }
	      }
	}
	@Override
	public void saveCheckOutJob(Jobs jobsObj) throws Exception {
		Connection conn=null;
		CallableStatement cstmtCheckOut = null;
		CallableStatement cstmt = null;
		int status=0;
		int checkOutSts=0;
		int transType = 0;
		int source = 1; // 1 for system
		String desc = "Job Id : "+jobsObj.getJobId();
		/*if(jobsObj.getCollectedBy() == 1) // 1 for easyfixer 2 for easyfix, 3 for client
			transType = 1;
		else 
			transType = 2;*/
		try{
			conn = getJdbcTemplate().getDataSource().getConnection();
			
			conn.setAutoCommit(false);
			if(conn!=null) {
				/*cstmtCheckOut=conn.prepareCall("{call sp_ef_job_checkout_job(?,?,?,?,?)}");
				cstmtCheckOut.setInt(1, jobsObj.getJobId());
				cstmtCheckOut.setInt(2, jobsObj.getPaidBy());
				cstmtCheckOut.setInt(3, jobsObj.getCollectedBy());
				cstmtCheckOut.setString(4, jobsObj.getMaterialCharge());
				cstmtCheckOut.setInt(5, jobsObj.getFkCheckOutBy());
				cstmtCheckOut.executeUpdate();
				checkOutSts = 1;
			}
			if(checkOutSts == 1){*/
				cstmt = conn.prepareCall("{call sp_ef_checkout_job_and_update_transaction(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				cstmt.setInt(1, jobsObj.getFkEasyfixterId());
				cstmt.setDouble(2, Double.parseDouble(jobsObj.getEasyfixerAmount()));
				cstmt.setDouble(3, Double.parseDouble(jobsObj.getEasyfixAmount()));
				cstmt.setDouble(4, Double.parseDouble(jobsObj.getClientAmount()));
				cstmt.setInt(5, jobsObj.getCollectedBy());
				cstmt.setInt(6, source);
				cstmt.setString(7, desc);
				cstmt.setInt(8, jobsObj.getFkCheckOutBy());
				cstmt.setInt(9, jobsObj.getJobId());
				cstmt.setInt(10, jobsObj.getFkClientId());
				cstmt.setInt(11, jobsObj.getPaidBy());
				cstmt.setInt(12, Integer.parseInt(jobsObj.getMaterialCharge()));
				cstmt.setDouble(13, Double.parseDouble(jobsObj.getTotalServiceTax()));
				cstmt.registerOutParameter(14, Types.INTEGER);
				cstmt.executeQuery();
				status = cstmt.getInt(14);
			}
			conn.commit();
			System.out.println("committed");
			logger.info("checkout committed jobid: "+jobsObj.getJobId());
						
		}
		catch(Exception e){
			try{
				conn.rollback();
				System.out.println("roll backed");
				logger.info("roll backed job "+jobsObj.getJobId());
			}catch(Exception ex){
				ex.printStackTrace();
				logger.catching(ex);
			}
			e.printStackTrace();
			logger.catching(e);
		}
		
			finally {
				  
	            if (cstmtCheckOut != null) {
	                try {
	                	cstmtCheckOut.close();
	                } catch (SQLException e) { /*ignored*/ }
	            }
	            if (cstmt != null) {
	                try {
	                	cstmt.close();
	                } catch (SQLException e) { /*ignored*/ }
	            }
	      }
		
		
	}

	@Override
	public void saveJobComment(Jobs jobsObj) throws Exception {
		try {
			String sql = "call sp_ef_job_save_job_comment(?,?,?,?,?)";
			DataSource ds=DBConfig.getContextDataSource();			
			getJdbcTemplate().setDataSource(ds);
			getJdbcTemplate().update(sql, new Object[]{jobsObj.getJobId(),jobsObj.getComments(),
					jobsObj.getCommentedOn(), jobsObj.getCommentedBy(),jobsObj.getEnumId()});
			logger.info( "comments saved for job: "+jobsObj.getJobId()+" by "+ jobsObj.getCommentedBy());
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		
		
	}

	@Override
	public List<Jobs> jobCommentList(int jobId) throws Exception {
		List<Jobs> commentList = new ArrayList<Jobs>();
		Connection conn=null;
		CallableStatement cstmt=null;
		ResultSet rs = null;		
		Jobs jobObj = null;
		try{
			//conn = getJdbcTemplate().getDataSource().getConnection();
			DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();
			cstmt=conn.prepareCall("call sp_ef_job_get_job_comment_list(?)");
			cstmt.setInt(1, jobId);
			rs = cstmt.executeQuery();			
			if(rs != null) {
				while(rs.next()){
					jobObj = new Jobs();					
					jobObj.setComments(rs.getString("comments"));
					jobObj.setCommentedOn(rs.getInt("comment_on"));
					jobObj.setEmpName(rs.getString("user_name"));
					jobObj.setCreatedDateTime(rs.getString("created_on"));	
					jobObj.setEnumId(rs.getInt("enum_reason_id"));
					jobObj.setEnumDesc(rs.getString("enum_desc"));
					commentList.add(jobObj);
				}				
			}
		}
		catch(Exception e){
			e.printStackTrace();
			logger.catching(e);
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
		
		return commentList;
	}
/*
	@Override
	public void updateEasyfixerAccount(Jobs jobsObj) throws Exception {
		Connection conn = null;
		CallableStatement cstmt = null;
		int status = 0;
		int transType = 0;
		int source = 1; // 1 for system
		String desc = "Job Id : "+jobsObj.getJobId();
		if(jobsObj.getCollectedBy() == 1) // 1 for easyfixer 2 for easyfix
			transType = 1;
		else 
			transType = 2;
		try {		
			
			//conn = getJdbcTemplate().getDataSource().getConnection();
			DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();
			if(conn!=null){
				cstmt = conn.prepareCall("{call sp_ef_finance_add_update_easyfixer_transaction(?,?,?,?,?,?,?,?)}");
				cstmt.setInt(1, jobsObj.getFkEasyfixterId());
				cstmt.setDouble(2, Double.parseDouble(jobsObj.getEasyfixerAmount()));
				cstmt.setInt(3, transType);
				cstmt.setInt(4, source);
				cstmt.setString(5, desc);
				cstmt.setInt(6, jobsObj.getFkCheckOutBy());
				cstmt.setInt(7, jobsObj.getJobId());
				cstmt.registerOutParameter(8, Types.INTEGER);
				cstmt.executeQuery();
				status = cstmt.getInt(8);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} 
			finally {
	           	            if (cstmt != null) {
	                try {
	                	cstmt.close();
	                } catch (SQLException e) {  }
	            }
	            if (conn != null) {
	                try {
	                    conn.close();
	                } catch (SQLException e) {  }
	            }
	        }
		
		
		
	}
*/
	@Override
	public void saveFeedbackJob(Jobs jobsObj) throws Exception {
		Connection conn = null;
		CallableStatement cstmt = null;
		int status = 0;
		try {		
			
			//conn = getJdbcTemplate().getDataSource().getConnection();
			DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();
			if(conn!=null){
				cstmt = conn.prepareCall("{call sp_ef_job_save_feedback_job(?,?,?,?,?,?,?,?,?,?,?,?)}");
				cstmt.setInt(1, jobsObj.getJobId());
				cstmt.setDouble(2, Double.parseDouble(jobsObj.getTotalServiceAmount()));
				cstmt.setDouble(3, Double.parseDouble(jobsObj.getMaterialCharge()));
				cstmt.setInt(4, jobsObj.getFkEasyfixterId());
				cstmt.setInt(5, jobsObj.getEasyfixerRating());
				cstmt.setInt(6, jobsObj.getEasyfixRating());
				cstmt.setString(7, jobsObj.getHappyWithService());
				cstmt.setString(8, jobsObj.getServiceTaken());
				cstmt.setString(9, jobsObj.getAnotherCall());
				cstmt.setString(10, jobsObj.getCrossSelling());
				cstmt.setInt(11, jobsObj.getFkFeedBackBy()); 
				cstmt.registerOutParameter(12, Types.INTEGER);
				cstmt.executeQuery();
				status = cstmt.getInt(12);
				logger.info("feedback saved for job "+jobsObj.getJobId());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
			
		}finally {
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
		
	}

	@Override
	public List<Jobs> getEnumReasonList(int enumType) throws Exception {
		List<Jobs> enumReasonList = new ArrayList<Jobs>();
		Connection conn=null;
		CallableStatement cstmt=null;
		ResultSet rs = null;		
		Jobs jobObj = null;
		try{
			conn = getJdbcTemplate().getDataSource().getConnection();
			cstmt=conn.prepareCall("call sp_ef_get_enum_reason_list(?)");
			cstmt.setInt(1, enumType);
			rs = cstmt.executeQuery();			
			if(rs != null) {
				while(rs.next()){
					jobObj = new Jobs();					
					jobObj.setEnumId(rs.getInt("enum_id"));
					jobObj.setEnumType(rs.getInt("enum_type"));
					jobObj.setEnumDesc(rs.getString("enum_desc"));		
					enumReasonList.add(jobObj);
				}				
			}
		}
		catch(Exception e){
			e.printStackTrace();
			logger.catching(e);
		}
		finally {
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
		
		return enumReasonList;
		
	}

	@Override
	public void saveCancelJob(Jobs jobsObj) throws Exception {
		try {
			String sql = "call sp_ef_job_save_cancel_job(?,?,?,?,?)";
			DataSource ds=DBConfig.getContextDataSource();			
			getJdbcTemplate().setDataSource(ds);
			getJdbcTemplate().update(sql, new Object[]{jobsObj.getJobId(),jobsObj.getEnumId(),
					jobsObj.getCanceledOn(), jobsObj.getComments(), jobsObj.getCancelBy()});
			logger.info("cancled job :"+jobsObj.getJobId());
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		
	}

	@Override
	public void updateJobOwner(Jobs jobsObj) throws Exception {
		try {
			String sql = "call sp_ef_job_update_job_owner(?,?,?,?)";
			DataSource ds=DBConfig.getContextDataSource();			
			getJdbcTemplate().setDataSource(ds);
			getJdbcTemplate().update(sql, new Object[]{jobsObj.getOwnerJobIds(),jobsObj.getJobOwner(),jobsObj.getOwnerChangeReason(),
					jobsObj.getOwnerChangeBy()});
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		
	}

	@Override
	public void recordEfrJobRejections(Jobs jobObj) throws Exception {
		try{
			String sql = "call sp_ef_easyfixer_rejected_call_record(?,?,?,?,?)";
			getJdbcTemplate().update(sql,new Object[]{jobObj.getFkEasyfixterId(),
					jobObj.getJobId(),jobObj.getEfrRejectionReasonId(),jobObj.getEfrrejectionComment(),
					jobObj.getCommentedBy()});
		}
	 catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	 }
		
	}

	@Override
	public List<Jobs> getEfrRejectReasonList() throws Exception {
		List<Jobs> jobList=new ArrayList<Jobs>();
		try{
			String sql = "call sp_ef_efr_get_jobrejectedby_efr_reason_list()";
			jobList=getJdbcTemplate().query(sql, new RowMapper<Jobs>(){
						public Jobs mapRow(ResultSet rs, int row) throws SQLException {
							
							Jobs jobObj = new Jobs();
							jobObj.setEfrRejectionReasonId(rs.getInt("id"));
							jobObj.setEfrRejectReasonName(rs.getString("reject_name"));
							return jobObj; 
						}			
						
					}); 
		}
		catch(Exception e){
			e.printStackTrace();
			logger.catching(e);
		}
		return jobList;
	}

	@Override
	public List<Jobs> getJobListByFilter(Jobs jobsObj) throws Exception {
		List<Jobs> jobList = new ArrayList<Jobs>();
		Connection conn=null;
		CallableStatement cstmt=null;
		ResultSet rs = null;
		try{
			conn = getJdbcTemplate().getDataSource().getConnection();			
			cstmt=conn.prepareCall("call sp_ef_jobs_getjoblist_by_filter(?,?,?,?,?,?,?,?,?,?,?)");
			cstmt.setInt(1, jobsObj.getJobId());
			cstmt.setInt(2, jobsObj.getFkClientId());
			cstmt.setString(3, jobsObj.getEasyfixterObj().getEasyfixerName());
			cstmt.setInt(4, jobsObj.getJobStatus());
			cstmt.setInt(5, jobsObj.getFkCityId());
			cstmt.setInt(6, jobsObj.getJobOwner());
			cstmt.setString(7, jobsObj.getCustomerObj().getCustomerName());
			cstmt.setInt(8, jobsObj.getFkServiceTypeId());
			cstmt.setString(9, jobsObj.getStartDate());
			cstmt.setString(10, jobsObj.getEndDate());
			cstmt.setString(11, jobsObj.getAddressObj().getPinCode());
			rs = cstmt.executeQuery();
			if(rs != null) {
				while(rs.next()){
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
					jobObj.setCancelDateTime(rs.getString("cancel_date_time"));
					jobObj.setJobStatus(rs.getInt("job_status"));
					jobObj.setJobOwner(rs.getInt("job_owner"));
					jobObj.setEmpName(rs.getString("owner_user"));
					jobObj.setCollectedBy(rs.getInt("collected_by"));
					jobObj.setMaterialCharge(rs.getString("material_charge"));
					jobObj.setClientRefId(rs.getString("client_ref_id"));
					jobObj.setJobDocs(rs.getString("job_docs"));
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
					
					jobList.add(jobObj);
				}				
			}
		}
		catch(Exception e){
			e.printStackTrace();
			logger.catching(e);
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
	
	
	@Override
	public int addJobFromExcel(Jobs jobsObj)
			throws Exception {

		Connection conn=null;
		CallableStatement cstmt=null;
		int jobId=0;
		try{
			conn = getJdbcTemplate().getDataSource().getConnection();
			
			cstmt=conn.prepareCall("call sp_ef_job_add_new_job_from_excel(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			cstmt.setString(1,jobsObj.getCustomerObj().getCustomerMobNo());
			cstmt.setString(2, jobsObj.getCustomerObj().getCustomerName());
			cstmt.setString(3, jobsObj.getCustomerObj().getCustomerEmail());
			cstmt.setString(4, jobsObj.getClientObj().getClientName());
			cstmt.setString(5,jobsObj.getClientRefId());
			cstmt.setString(6,jobsObj.getServiceTypeObj().getServiceTypeName());
			cstmt.setString(7,jobsObj.getClientServiceIds());
			cstmt.setString(8,jobsObj.getJobDesc());
			cstmt.setString(9,jobsObj.getRequestedDateTime());
			cstmt.setString(10,jobsObj.getAddressObj().getAddress());
			cstmt.setString(11,jobsObj.getAddressObj().getCityObj().getCityName());
			//cstmt.setString(12,jobsObj.getAddressObj().getGpsLocation());
			cstmt.setString(12,jobsObj.getAddressObj().getPinCode());
			cstmt.setInt(13,jobsObj.getFkCreatedBy());
			cstmt.setInt(14,jobsObj.getJobOwner());
			cstmt.registerOutParameter(15, Types.INTEGER);
			cstmt.execute();
			jobId=cstmt.getInt(15);
			
		}
		catch(Exception e){           
			logger.catching(e);
			e.printStackTrace();
		}
		
			finally {
				  if (cstmt != null) {
		                try {
		                	cstmt.close();
		                } catch (SQLException e) { /*ignored*/ }
		            }
	           
	           
	          
	            if (conn != null) {
	                try {
	                	conn.setAutoCommit(true);
	                    conn.close();
	                } catch (SQLException e) { /*ignored*/ }
	           
	        }
		}
		
		
		return jobId;
	}

	@Override
	public Jobs getLeadJobDetails(int jobId) throws Exception {
		Connection conn=null;
		CallableStatement cstmt=null;
		ResultSet rs = null;
		Jobs leadObj = null;
		try{
			conn = getJdbcTemplate().getDataSource().getConnection();			
			cstmt=conn.prepareCall("call sp_ef_jobs_get_lead_job_details(?)");
			cstmt.setInt(1, jobId);
			rs = cstmt.executeQuery();
			if(rs != null) {
				while(rs.next()){
					leadObj = new Jobs();
					Customers custObj = new Customers();
					Address addObj = new Address();
					City cityObj = new City();
					ServiceType serviceTypeObj = new ServiceType();
					Clients clientObj = new Clients();
					
					
					leadObj.setJobId(rs.getInt("row_id"));
					leadObj.setMobileNo(rs.getString("mobile"));
					leadObj.setJobDesc(rs.getString("job_desc"));
					leadObj.setClientServices(rs.getString("services"));
					leadObj.setRequestedDateTime(rs.getString("req_date"));	
					leadObj.setClientRefId(rs.getString("ref_id"));
					serviceTypeObj.setServiceTypeName(rs.getString("service_type"));
					clientObj.setClientName(rs.getString("client_name"));
					
					custObj.setCustomerName(rs.getString("cus_name"));
					custObj.setCustomerMobNo(rs.getString("mobile"));
					custObj.setCustomerEmail(rs.getString("cust_email"));
					addObj.setAddress(rs.getString("address"));
					addObj.setPinCode(rs.getString("pin"));
					cityObj.setCityName(rs.getString("city_name"));
					
					addObj.setCityObj(cityObj);
					leadObj.setAddressObj(addObj);
					leadObj.setClientObj(clientObj);
					leadObj.setServiceTypeObj(serviceTypeObj);
					leadObj.setCustomerObj(custObj);
					
				}				
			}
		}
		catch(Exception e){
			logger.catching(e);
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
		
		return leadObj;
	}

	@Override
	public void updateLeadJob(int jobId, int status) throws Exception {
		try{
			String sql = "call sp_ef_jobs_update_lead_job(?,?)";
			getJdbcTemplate().update(sql,new Object[]{jobId,status});
		}
	 catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	 	}
	}

	@Override
	public List<Jobs> getCustomerJobList(int fkCustomerId) throws Exception {
		List<Jobs> jobList = new ArrayList<Jobs>();
		Connection conn=null;
		CallableStatement cstmt=null;
		ResultSet rs = null;
		try{
			conn = getJdbcTemplate().getDataSource().getConnection();			
			cstmt=conn.prepareCall("call sp_ef_jobs_get_customer_joblist(?)");
			cstmt.setInt(1, fkCustomerId);
			rs = cstmt.executeQuery();
			if(rs != null) {
				while(rs.next()){
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
					jobObj.setCancelDateTime(rs.getString("cancel_date_time"));
					jobObj.setJobStatus(rs.getInt("job_status"));
					jobObj.setJobOwner(rs.getInt("job_owner"));
					jobObj.setEmpName(rs.getString("owner_user"));
					jobObj.setCollectedBy(rs.getInt("collected_by"));
					jobObj.setMaterialCharge(rs.getString("material_charge"));
					jobObj.setClientRefId(rs.getString("client_ref_id"));
					
					custObj.setCustomerName(rs.getString("customer_name"));
					jobObj.setAdditionalName(rs.getString("additional_name"));
					custObj.setCustomerMobNo(rs.getString("customer_mob_no"));
					clientObj.setClientName(rs.getString("client_name"));
					easyfixerObj.setEasyfixerNo(rs.getString("efr_no"));
					easyfixerObj.setEasyfixerName(rs.getString("efr_name"));
					serviceTypeObj.setServiceTypeName(rs.getString("service_type_name"));
					jobObj.setCustomerObj(custObj);
					jobObj.setClientObj(clientObj);
					jobObj.setEasyfixterObj(easyfixerObj);
					jobObj.setServiceTypeObj(serviceTypeObj);
					
					jobList.add(jobObj);
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

	@Override
	public List<Jobs> getjobListForChangeOwner(int userId, int roleId)	throws Exception {
		List<Jobs> ownerChangeJobList = new ArrayList<Jobs>(); 
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Jobs jobObj = null;	
		Clients clientObj =null;
		ServiceType serviceTypeObj = null;
		Customers customerObj = null;
		Easyfixers efrObj = null;
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();

			if(conn!=null){
				cstmt = conn.prepareCall("{call sp_ef_change_user_owner_job_list(?,?)}");
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
						Address address = new Address();
						City city = new City();
						
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
						jobObj.setJobStatus(rs.getInt("job_status"));
						jobObj.setEmpName(rs.getString("user_name"));
						city.setCityName(rs.getString("city_name"));
						jobObj.setFkCityId(rs.getInt("city_id"));
						
						address.setCityObj(city);
						jobObj.setAddressObj(address);
						jobObj.setEasyfixterObj(efrObj);
						jobObj.setServiceTypeObj(serviceTypeObj);
						jobObj.setClientObj(clientObj);
						jobObj.setCustomerObj(customerObj);					
						
						
						ownerChangeJobList.add(jobObj);
						
					}					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
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
		
		return ownerChangeJobList;
	}

	@Override
	public int addUpdateCallLaterJob(Jobs jobsObj, List<String> list, int userId) throws Exception {
		Connection conn=null;
		CallableStatement cstmt=null;
		CallableStatement cstmtServ = null;
		int status=0;
		try{
			conn = getJdbcTemplate().getDataSource().getConnection();
			conn.setAutoCommit(false);
			
			if(jobsObj.getJobId() > 0){
				cstmt=conn.prepareCall("call sp_ef_job_update_call_later_job(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				cstmt.setInt(1,jobsObj.getJobId());
				cstmt.setString(2,jobsObj.getJobDesc());
				cstmt.setInt(3,jobsObj.getFkCustomerId());
				cstmt.setInt(4,jobsObj.getFkAddressId());
				cstmt.setInt(5,jobsObj.getFkClientId());
				cstmt.setInt(6,jobsObj.getFkServiceTypeId());
				cstmt.setString(7,jobsObj.getClientServices());
				cstmt.setString(8,jobsObj.getRequestedDateTime());
				cstmt.setInt(9, userId);
				cstmt.setInt(10, jobsObj.getJobStatus());
				cstmt.setString(11, jobsObj.getClientRefId());
				cstmt.setInt(12, jobsObj.getEnumId());
				cstmt.setString(13, jobsObj.getComments());
				cstmt.registerOutParameter(14, Types.INTEGER);
				cstmt.executeUpdate();
				status=cstmt.getInt(14);				
			}
			if(jobsObj.getJobId() > 0){
				cstmtServ = conn.prepareCall("{call sp_ef_add_update_job_service(?,?)}");
				cstmtServ.setInt(1, jobsObj.getJobId());
				for (int i = 0; i < list.size(); i++) {					
					cstmtServ.setInt(2, Integer.parseInt(list.get(i)));
					cstmtServ.executeUpdate();					
					}
				}
			
			conn.commit();
		}
		catch(Exception e){
			try {
				conn.rollback();
				logger.info("Rollback occure in jobdaoImpl addupdatecalllaterjob for job::"+jobsObj.getJobId());
			} catch (SQLException e2) {
				e2.printStackTrace();
				logger.catching(e2);
			}
			e.printStackTrace();
			logger.catching(e);
		}
		
		finally {
			if (cstmt != null) {
	                try {
	                	cstmt.close();
	                } catch (SQLException e) { /*ignored*/ }
	            }
            if (cstmtServ != null) {
                try {
                	cstmtServ.close();
                } catch (SQLException e) { /*ignored*/ }
            }
          
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /*ignored*/ }
	           
	        }
		}
		
		
		return status;
	}

	@Override
	public void updateRescheduleJob(Jobs jobsObj) throws Exception {
		
		String query = "INSERT INTO tbl_reschedule_job( job_id,reschedule_reason_id,"
				+ "reschedule_comment,rescheduled_by,reschedule_date_time) VALUES(?,?,?,?,NOW())";
	//	conn = getJdbcTemplate().update(query, new Object[]{(jobsObj.getJobId())});
		int insertId = getJdbcTemplate().update(query, new Object[]{jobsObj.getJobId(),
				jobsObj.getEnumId(),jobsObj.getComments(),jobsObj.getJobOwner()});
		
		String query2 = "INSERT INTO tbl_job_comment( job_id,enum_reason_id,"
				+ "comments,commented_by,created_on,comment_on) VALUES(?,?,?,?,NOW(),2)";
	//	conn = getJdbcTemplate().update(query, new Object[]{(jobsObj.getJobId())});
		int insertId2 = getJdbcTemplate().update(query2, new Object[]{jobsObj.getJobId(),
				jobsObj.getEnumId(),jobsObj.getComments(),jobsObj.getJobOwner()});
		
		try{
		String query3 = "INSERT INTO scheduling_history( job_id, reason_id, reschedule_reason) VALUES(?,?,?)";
	//	conn = getJdbcTemplate().update(query, new Object[]{(jobsObj.getJobId())});
		int insertId3=getJdbcTemplate().update(query3, new Object[]{jobsObj.getJobId(),
				jobsObj.getEnumId(),jobsObj.getComments()});
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}


	@Override
	public List<Jobs> getJobListByStatus(int jobStatus, int userId) throws Exception {
		List<Jobs> jobList = new ArrayList<Jobs>();
		Connection conn=null;
		CallableStatement cstmt=null;
		ResultSet rs = null;
		try{
			conn = getJdbcTemplate().getDataSource().getConnection();			
			cstmt=conn.prepareCall("call sp_ef_jobs_getjoblist_by_status(?,?)");
			cstmt.setInt(1, jobStatus);	
			cstmt.setInt(2, userId);
			rs = cstmt.executeQuery();
			if(rs != null) {
				while(rs.next()){
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
					jobObj.setCancelDateTime(rs.getString("cancel_date_time"));
					jobObj.setJobStatus(rs.getInt("job_status"));
					jobObj.setJobOwner(rs.getInt("job_owner"));
					jobObj.setEmpName(rs.getString("owner_user"));
					jobObj.setCollectedBy(rs.getInt("collected_by"));
					jobObj.setMaterialCharge(rs.getString("material_charge"));
					jobObj.setClientRefId(rs.getString("client_ref_id"));
					
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
					
					jobList.add(jobObj);
				}				
			}
		}
		catch(Exception e){
			e.printStackTrace();
			logger.catching(e);
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

	@Override
	public String getServiceTaxRate() throws Exception {
		String sql = "SELECT SUM(rate) AS rate FROM tbl_tax_rate WHERE STATUS =1";
		 Jobs j =getJdbcTemplate().queryForObject(sql, new RowMapper<Jobs>(){
			public Jobs mapRow(ResultSet rs, int row) throws SQLException {
				
				Jobs j = new Jobs();
				j.setServiceTaxRate(rs.getString("rate"));
				
				return j; 
				}
			
		});
		
		return j.getServiceTaxRate();
	}

	@Override
	public List<Sms> getSmsDetails() throws Exception {
		List<Sms> smsDetails = new ArrayList<Sms>();
		String query = "SELECT * FROM `tbl_sms_transational_meta` WHERE `status` = 1";
		smsDetails= getJdbcTemplate().query(query, new RowMapper<Sms>(){

			@Override
			public Sms mapRow(ResultSet rs, int row) throws SQLException {
				Sms sms = new Sms();
				sms.setClientId(rs.getInt("client_id"));
				sms.setIsExclusive(rs.getInt("is_exclusive"));
				sms.setJobStage(rs.getString("job_stage"));
				sms.setReceiver(rs.getInt("receiver"));
				sms.setSender(rs.getInt("sender"));
				sms.setSms(rs.getString("sms"));
				sms.setSmsId(rs.getInt("sms_id"));
				sms.setStatus(rs.getInt("status"));
				
				return sms;
			}
			
		});
		//System.out.println("in getSmsDetails dao");
		return smsDetails;
	}

	public Easyfixers getAvgEfrRatingByCustomer(int efrId) throws Exception{
		String query = "SELECT AVG(`easyfixer_rating`) AS efr_rating,"
				+ "j.`fk_easyfixter_id` FROM `tbl_customer_feedback`  "
				+ "f LEFT JOIN `tbl_job` j ON j.`job_id`=f.`job_id` "
				+ "WHERE j.`fk_easyfixter_id`=?";
		return getJdbcTemplate().queryForObject(query, new Object[]{efrId},new RowMapper<Easyfixers>(){
			public Easyfixers mapRow(ResultSet rs, int row) throws SQLException {
				
				Easyfixers v = new Easyfixers();
				v.setCustomerRating(rs.getString("efr_rating"));
				v.setEasyfixerId(rs.getInt("fk_easyfixter_id"));
				return v; 
				}
			
		});
	
		
	}

	@Override
	public List<email> getEmailByClientJobStage(int clientId, String jobStage)
			throws Exception {
	//	List<email> emailList = new ArrayList<email>();
		String query = "select * from tbl_email_transactional_meta where client_id=? and job_stage=? and  status =1";
		return  getJdbcTemplate().query(query,new Object[]{clientId,jobStage}, new RowMapper<email>(){

			@Override
			public email mapRow(ResultSet rs, int row) throws SQLException {
				email email = new email();
				email.setClientDisplayName(rs.getString("client_display_name"));
				email.setClientId(rs.getInt("client_id"));
				email.setEmailText(rs.getString("email_content"));
				email.setEmailType(rs.getString("job_stage"));
				email.setSubject(rs.getString("email_subject"));
				
				return email;
			}
			
		}
				
				);
		
		
	}

	@Override
	public List<Recipients> getClientRecipientListForSmsEmail(int clientId) throws Exception {
		String query = "select * from tbl_client_contacts where client_id=? and status=1";
		return getJdbcTemplate().query(query, new Object[]{clientId},new RowMapper<Recipients>(){

			@Override
			public Recipients mapRow(ResultSet rs, int row)
					throws SQLException {
				Recipients r = new Recipients();
				r.setClientId(rs.getInt("client_id"));
				r.setContactName(rs.getString("contact_name"));
				r.setContactNo(rs.getString("contact_no"));
				r.setEmail(rs.getString("contact_email"));
				r.setFrequency(rs.getString("frequency"));
				r.setRecipientType(rs.getString("client_type"));
				
				return r;
			}
			
	
		});
	}
	@Override
	public Jobs getJobStatus(int jobId) throws Exception {
		String query = "select job_status,job_id from tbl_job where job_id = ?";
		return  (Jobs) getJdbcTemplate().queryForObject(query,new Object[]{jobId}, new RowMapper<Jobs>(){

			@Override
			public Jobs mapRow(ResultSet rs, int row) throws SQLException {
				Jobs job = new Jobs();
				job.setJobId(rs.getInt("job_id"));
				job.setJobStatus(rs.getInt("job_status"));
				
				return job;
			}
			
		}
				
				);
	}

	@Override
	public List<Sms> getCrmSmsList() throws Exception {
		List<Sms> crmSmsList = new ArrayList<Sms>();
		String query = "SELECT * FROM `tbl_key_value_pair` WHERE `crm_key_type` = 1 AND `crm_key_status` =1";
		crmSmsList= getJdbcTemplate().query(query, new RowMapper<Sms>(){

			@Override
			public Sms mapRow(ResultSet rs, int row) throws SQLException {
				Sms sms = new Sms();
				
				sms.setSms(rs.getString("crm_value"));
				sms.setSmsId(rs.getInt("crm_key"));
				sms.setStatus(rs.getInt("crm_key_status"));
				
				return sms;
			}
			
		});
		//System.out.println("in getSmsDetails dao");		
		
		return crmSmsList;
	}

	@Override
	public void updateAppNotificationLog(Jobs job, Easyfixers efr,
			Notification notification, String result) {
		String sql = "INSERT INTO `tbl_log_app_notification`(`n_type`,`n_message`,`n_title`,`n_flag`,`n_priority`,`n_key`,`n_to`,`n_result`,`inserted_date`,`efr_id`,`job_id`) VALUES(?,?,?,?,?,?,?,?,NOW(),?,?)";
//		DataSource ds=DBConfig.getContextDataSource();			
//		getJdbcTemplate().setDataSource(ds);

		int insertId = getJdbcTemplate().update(sql, new Object[]{notification.getType(), notification.getMessage()
				,notification.getTitle(),notification.getFlag(),notification.getPriority(),notification.getKey(),efr.getDeviceId(),result,efr.getEasyfixerId(),job.getJobId()});
		
	
		
	}

	@Override
	public int deleteJobAfterCheckout(Jobs job) throws Exception {
		String sql = "call delete_job_data(?,?)";
  int affecteRows = getJdbcTemplate().update(sql, new Object[]{job.getJobId(),job.getComments()});
		return affecteRows; 
		
	}

	@Override
	public void updateUserActionLog(Jobs job) throws Exception {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO `tbl_user_action_log`(`job_id`,`user_id`,`action`,`insert_date`) VALUES(?,?,?,NOW())";

		int insertedId = getJdbcTemplate().update(sql,new Object[]{job.getJobId(),job.getUpdatedBy(),job.getJobFlag()});
	}

	@Override
	public void changeJobStatus(Jobs job, int status) throws Exception {
		String sql = "UPDATE tbl_job SET job_status = ? WHERE job_id = ?";

		int insertedId = getJdbcTemplate().update(sql,new Object[]{status,job.getJobId()});

		
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	
	@Override
	public void updateJobStatus(Jobs jobsObj) throws Exception{
	Connection conn=null;
	CallableStatement cstmt=null;
	try{
		conn = getJdbcTemplate().getDataSource().getConnection();

			cstmt=conn.prepareCall("call sp_ef_job_status_change(?,?)");
			cstmt.setInt(1,jobsObj.getJobId());
			cstmt.setInt(2,15);
			cstmt.executeUpdate();
		
	}
		catch (Exception e) {
			e.printStackTrace();
			
		} finally {
            	cstmt.close();
                conn.close();
               
            }
        
	}


	
	
}
