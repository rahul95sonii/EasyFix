package com.easyfix.easyfixers.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.easyfix.Jobs.model.Jobs;
import com.easyfix.clients.model.Clients;
import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.easyfixers.model.ServicePayout;
import com.easyfix.settings.model.City;
import com.easyfix.settings.model.DocumentType;
import com.easyfix.settings.model.ZoneCityMapping;
import com.easyfix.user.model.User;
import com.easyfix.util.DBConfig;
import com.easyfix.util.UtilityFunctions;

public class EasyfixerDaoImpl extends JdbcDaoSupport implements EasyfixerDao  {

	private SessionFactory sessionFactory;
	@Override
	public List<Easyfixers> getEasyfixerList(int flag) throws Exception {
		List<Easyfixers> easyfixerList = new ArrayList<Easyfixers>();
		
		String query = "call sp_ef_easyfixer_geteasyfixerlist(?)";
			easyfixerList = getJdbcTemplate().query(query, new Object[]{flag}, new RowMapper<Easyfixers>(){
			public Easyfixers mapRow(ResultSet rs, int row) throws SQLException {
				
				Easyfixers easyfixerObj = new Easyfixers();
				City cityObj = new City();
				User uObj = new User();
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
				easyfixerObj.setEasyfixerServiceType(rs.getString("service_type"));
				easyfixerObj.setIsActive(rs.getInt("efr_status"));
				easyfixerObj.setNdmId(rs.getInt("ndm_id"));
				easyfixerObj.setCustomerRating(rs.getString("easyfixer_rating"));
				cityObj.setCityName(rs.getString("city_name"));
				easyfixerObj.setCurrentBalance(rs.getFloat("current_balance"));
				easyfixerObj.setBalanceUpdateDate(rs.getString("balance_updated"));
				easyfixerObj.setEnumDesc(rs.getString("enum_desc"));
				easyfixerObj.setEfrInactiveReason(rs.getInt("inactive_reason"));
				easyfixerObj.setEfrInactiveComments(rs.getString("inactive_comment"));
				easyfixerObj.setCityZoneName(rs.getString("zone_name"));
				uObj.setUserName(rs.getString("ndm_name"));
				easyfixerObj.setNDMName(uObj);
				easyfixerObj.setCityObj(cityObj);
				
				return easyfixerObj; 
			}
			
			
		});
        return easyfixerList;
	}

	
	public int addUpdateEasyFixer(Easyfixers easyfixerObj) throws Exception {
		
		Connection conn=null;
		CallableStatement cstmt=null;
		CallableStatement cstmtDoc=null;
		int status=0;
		try{
			conn = getJdbcTemplate().getDataSource().getConnection();

			cstmt=conn.prepareCall("call sp_ef_easyfixer_add_update_easyfixer(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			cstmt.setInt(1,easyfixerObj.getEasyfixerId());
			cstmt.setString(2,easyfixerObj.getEasyfixerName());
			cstmt.setString(3,easyfixerObj.getEasyfixerNo());
			cstmt.setString(4,easyfixerObj.getEasyfixerAlterNo());
			cstmt.setString(5,easyfixerObj.getEasyfixerEmail());
			cstmt.setString(6,easyfixerObj.getEasyfixerAddress());
			cstmt.setInt(7,easyfixerObj.getEasyfixerCityId());
			cstmt.setString(8,easyfixerObj.getEasyfixerPin());
			cstmt.setString(9,easyfixerObj.getEasyfixerBaseGps());
			cstmt.setString(10,easyfixerObj.getEasyfixerType());
			cstmt.setString(11,easyfixerObj.getEasyfixerServiceType());
			cstmt.setInt(12,easyfixerObj.getIsActive());
			cstmt.setInt(13, easyfixerObj.getNdmId());
			cstmt.setInt(14, easyfixerObj.getUpdatedBy());
			cstmt.setInt(15, easyfixerObj.getEfrInactiveReason());
			cstmt.setString(16, easyfixerObj.getEfrInactiveComments());
			cstmt.setInt(17,easyfixerObj.getCityZoneId() );
			cstmt.setInt(18,easyfixerObj.getSkillId());
			
			cstmt.registerOutParameter(19, Types.INTEGER);
			
			cstmt.executeUpdate();
			status=cstmt.getInt(19);
			if(status > 0){
				cstmtDoc = conn.prepareCall("call sp_ef_easyfixer_add_update_easyfixer_doc(?,?,?,?)");
				cstmtDoc.setInt(1, status);
				for(int i=0; i<easyfixerObj.getDocSize(); i++){
					if(i==0){
						cstmtDoc.setInt(2, easyfixerObj.getEasyfixerDocId0());
						cstmtDoc.setString(3, easyfixerObj.getEasyfixerDocumentName0());
						cstmtDoc.setInt(4, easyfixerObj.getUpdatedBy());
						cstmtDoc.executeUpdate();
					}
					if(i==1){
						cstmtDoc.setInt(2, easyfixerObj.getEasyfixerDocId1());
						cstmtDoc.setString(3, easyfixerObj.getEasyfixerDocumentName1());
						cstmtDoc.setInt(4, easyfixerObj.getUpdatedBy());
						cstmtDoc.executeUpdate();
					}
					if(i==2){
						cstmtDoc.setInt(2, easyfixerObj.getEasyfixerDocId2());
						cstmtDoc.setString(3, easyfixerObj.getEasyfixerDocumentName2());
						cstmtDoc.setInt(4, easyfixerObj.getUpdatedBy());
						cstmtDoc.executeUpdate();
					}
					if(i==3){
						cstmtDoc.setInt(2, easyfixerObj.getEasyfixerDocId3());
						cstmtDoc.setString(3, easyfixerObj.getEasyfixerDocumentName3());
						cstmtDoc.setInt(4, easyfixerObj.getUpdatedBy());
						cstmtDoc.executeUpdate();
					}
					if(i==4){
						cstmtDoc.setInt(2, easyfixerObj.getEasyfixerDocId4());
						cstmtDoc.setString(3, easyfixerObj.getEasyfixerDocumentName4());
						cstmtDoc.setInt(4, easyfixerObj.getUpdatedBy());
						cstmtDoc.executeUpdate();
					}
					if(i==5){
						cstmtDoc.setInt(2, easyfixerObj.getEasyfixerDocId5());
						cstmtDoc.setString(3, easyfixerObj.getEasyfixerDocumentName5());
						cstmtDoc.setInt(4, easyfixerObj.getUpdatedBy());
						cstmtDoc.executeUpdate();
					}
				}
				
			}
			
		}
		catch(Exception e){
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
                    conn.close();
                } catch (SQLException e) { /*ignored*/ }
            }
        }
		
		
		return status;
	}

	@Override
	public Easyfixers getEasyfixerDetailsById(int easyfixerId) throws Exception {
		Easyfixers easyfixers = null;
		String query = "call sp_ef_easyfixer_geteasyfixerdetails_by_id(?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		easyfixers = getJdbcTemplate().queryForObject(query, new Object[]{easyfixerId}, new RowMapper<Easyfixers>(){
			public Easyfixers mapRow(ResultSet rs, int row) throws SQLException {
				
				Easyfixers easyfixerObj = new Easyfixers();
				City cityObj = new City();
				User uObj = new User();
				
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
				easyfixerObj.setNdmId(rs.getInt("ndm_id"));				
				easyfixerObj.setCurrentBalance(rs.getFloat("current_balance"));
				easyfixerObj.setBalanceUpdateDate(rs.getString("balance_updated"));
				easyfixerObj.setEnumDesc(rs.getString("enum_desc"));
				easyfixerObj.setEfrInactiveReason(rs.getInt("inactive_reason"));
				easyfixerObj.setEfrInactiveComments(rs.getString("inactive_comment"));
				easyfixerObj.setAppLoginPassword(rs.getString("login_password"));
				easyfixerObj.setDeviceId(rs.getString("device_id"));
				easyfixerObj.setIsAppUser(rs.getInt("is_app_user"));
				easyfixerObj.setCityZoneId(rs.getInt("efr_zone_city_id"));
				easyfixerObj.setSkillId(rs.getInt("skill"));

				cityObj.setCityName(rs.getString("city_name"));
				easyfixerObj.setCityObj(cityObj);
				uObj.setUserName(rs.getString("ndm_name"));
				easyfixerObj.setNDMName(uObj);
				
				return easyfixerObj; 
			}			
			
		});
		 
		Session session = sessionFactory.openSession();
		Criteria cr = session.createCriteria(ZoneCityMapping.class);
		if(easyfixers!=null && easyfixers.getCityZoneId()!=null)
			cr.add(Restrictions.eq("cityZoneId", easyfixers.getCityZoneId()));
		ZoneCityMapping zone = (ZoneCityMapping) cr.uniqueResult();
		if(zone!=null && zone.getZone()!=null && zone.getZone().getZoneName()!=null)
			easyfixers.setCityZoneName(zone.getZone().getZoneName());
		
		return easyfixers;
	}


	@Override
	public List<DocumentType> getEasyfixerDocument(int easyfixerId) throws Exception {
		List<DocumentType> efrDocList = new ArrayList<DocumentType>();
		
		String query = "call sp_ef_easyfixer_geteasyfixer_document(?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		efrDocList = getJdbcTemplate().query(query, new Object[]{easyfixerId}, new RowMapper<DocumentType>(){
			public DocumentType mapRow(ResultSet rs, int row) throws SQLException {
				
				DocumentType docTypeObj = new DocumentType();
				docTypeObj.setDocumentTypeId(rs.getInt("document_type_id"));
				docTypeObj.setDocumentName(rs.getString("document_name"));
				docTypeObj.setDocumentMandatory(rs.getString("document_mandatory"));
				docTypeObj.setEfrDocument(rs.getString("efr_document_name"));
				
				return docTypeObj; 
			}			
			
		});
		 
		 return efrDocList;
	}


	@Override
	public List<Clients> getClientListForEasyfixer(int easyfixerId, int mappedStatus) throws Exception {
		List<Clients> clientList = new ArrayList<Clients>();
		
		String query = "call sp_ef_easyfixer_client_mapping_get_client_list(?,?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		clientList = getJdbcTemplate().query(query, new Object[]{easyfixerId,mappedStatus}, new RowMapper<Clients>(){
			public Clients mapRow(ResultSet rs, int row) throws SQLException {
				
				Clients clientObj = new Clients();
				
				clientObj.setClientId(rs.getInt("client_id"));
				clientObj.setClientName(rs.getString("client_name"));
				clientObj.setClientAddress(rs.getString("client_address"));
				clientObj.setEasyfixerId(rs.getInt("easyfixer_id"));
				clientObj.setMappingStatus(rs.getInt("mapping_status"));	
				clientObj.setIsActive(rs.getInt("client_status"));
				return clientObj; 
			}
						
		});
        return clientList;
	}


	@Override
	public List<Easyfixers> getEnumReasonList(int enumType) throws Exception {
		List<Easyfixers> enumReasonList = new ArrayList<Easyfixers>();
		Connection conn=null;
		CallableStatement cstmt=null;
		ResultSet rs = null;		
		Easyfixers enumObj = null;
		try{
			//conn = getJdbcTemplate().getDataSource().getConnection();
			DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();
			cstmt=conn.prepareCall("call sp_ef_get_enum_reason_list(?)");
			cstmt.setInt(1, enumType);
			rs = cstmt.executeQuery();			
			if(rs != null) {
				while(rs.next()){
					enumObj = new Easyfixers();					
					enumObj.setEnumId(rs.getInt("enum_id"));
					enumObj.setEnumType(rs.getInt("enum_type"));
					enumObj.setEnumDesc(rs.getString("enum_desc"));		
					enumReasonList.add(enumObj);
				}				
			}
		}
		catch(Exception e){
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
                    conn.close();
                } catch (SQLException e) { /*ignored*/ }
            }
        }
		
		return enumReasonList;
	}


	@Override
	public int saveServicePayOut(ServicePayout payoutObj) throws Exception {
		Connection conn=null;
		CallableStatement cstmt=null;
		int status=0;
		try{
			conn = getJdbcTemplate().getDataSource().getConnection();

			cstmt=conn.prepareCall("call sp_ef_save_service_payout(?,?,?,?,?)");
			cstmt.setInt(1, payoutObj.getPayoutId());
			cstmt.setInt(2,payoutObj.getEfrId());
			cstmt.setFloat(3,payoutObj.getEfrBalance());
			cstmt.setFloat(4,payoutObj.getOpsAmount());
			cstmt.setFloat(5,payoutObj.getOpsApprovedAmount());
			
			cstmt.executeUpdate();
			
						
		}
		catch(Exception e){
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
                    conn.close();
                } catch (SQLException e) { /*ignored*/ }
            }
        }
		
		
		return status;
	}


	@Override
	public List<Easyfixers> easyfixerListForPayOut(int cityId) throws Exception {
		List<Easyfixers> efrPayoutList = new ArrayList<Easyfixers>();
		Connection conn=null;
		CallableStatement cstmt=null;
		ResultSet rs = null;		
		Easyfixers efrObj = null;
		ServicePayout payoutObj = null;
		City cityObj = null;
		try{
			conn = getJdbcTemplate().getDataSource().getConnection();			
			cstmt=conn.prepareCall("call sp_ef_get_easyfixer_list_for_payout(?)");
			cstmt.setInt(1, cityId);
			rs = cstmt.executeQuery();			
			if(rs != null) {
				while(rs.next()){
					efrObj = new Easyfixers();
					payoutObj = new ServicePayout();
					cityObj = new City();
					String opsAprvDt = "";
					String finAprvDt = "";
					efrObj.setEasyfixerId(rs.getInt("easyfixerId"));
					efrObj.setEasyfixerName(rs.getString("efr_name"));
					efrObj.setEasyfixerNo(rs.getString("efr_no"));	
					cityObj.setCityId(rs.getInt("efr_cityId"));
					cityObj.setCityName(rs.getString("city_name"));
					efrObj.setIsActive(rs.getInt("efr_status"));
					efrObj.setCurrentBalance(rs.getFloat("current_balance"));
					payoutObj.setPayoutId(rs.getInt("payout_id"));
					payoutObj.setEfrId(rs.getInt("efr_id"));
					payoutObj.setEfrBalance(rs.getFloat("efr_balance"));
					payoutObj.setOpsAmount(rs.getFloat("ops_amount"));
					payoutObj.setOpsApprovedAmount(rs.getFloat("ops_approved_amount"));
					payoutObj.setOpsApprovedDate(rs.getString("ops_approved_date"));
					payoutObj.setFinApprovedAmount(rs.getFloat("fin_approved_amount"));
					payoutObj.setFinApproveDate(rs.getString("fin_apfoved_date"));
					payoutObj.setIsApprovedByFinance(rs.getInt("is_approved_by_fin"));
					efrObj.setServicePayoutObj(payoutObj);
					efrObj.setCityObj(cityObj);
					if(payoutObj.getOpsApprovedDate()!=null){
						opsAprvDt = UtilityFunctions.changeDateFormatToFormat(payoutObj.getOpsApprovedDate(), "yyyy-MM-dd HH:mm:ss", "dd-MM-YYYY");
						payoutObj.setOpsApprovedDate(opsAprvDt);
					}
					if(payoutObj.getFinApproveDate()!=null){
						finAprvDt = UtilityFunctions.changeDateFormatToFormat(payoutObj.getFinApproveDate(), "yyyy-MM-dd HH:mm:ss", "dd-MM-YYYY");
						payoutObj.setFinApproveDate(finAprvDt);
					}
					
					efrPayoutList.add(efrObj);
				}				
			}
		}
		catch(Exception e){
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
                    conn.close();
                } catch (SQLException e) { /*ignored*/ }
            }
        }
		
		return efrPayoutList;
	}


	@Override
	public int approvePayOutByFinance(int payoutId, int efrId, int userId, float finAprvAmnt, int status) throws Exception {
		Connection conn=null;
		CallableStatement cstmt=null;
		int sts=0;
		try{
			conn = getJdbcTemplate().getDataSource().getConnection();

			cstmt=conn.prepareCall("call sp_ef_approve_payout_by_finance(?,?,?,?,?)");
			cstmt.setInt(1, payoutId);
			cstmt.setInt(2, efrId);
			cstmt.setFloat(3, finAprvAmnt);
			cstmt.setInt(4, userId);
			cstmt.setInt(5, status);
			
			cstmt.executeUpdate();
			
						
		}
		catch(Exception e){
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
                    conn.close();
                } catch (SQLException e) { /*ignored*/ }
            }
        }
		
		
		return sts;
	}


	@Override
	public List<ServicePayout> downloadEfrPayoutSheet(String start, String end,
			int flag) {
		List<ServicePayout> payoutList = new ArrayList<ServicePayout>();
		
		Connection conn=null;
		CallableStatement cstmt=null;
		ResultSet rs = null;
		try{
			conn = getJdbcTemplate().getDataSource().getConnection();

			cstmt=conn.prepareCall("call sp_ef_easyfixer_payout_sheet(?,?,?)");
			cstmt.setString(1, start);
			cstmt.setString(2, end);
			cstmt.setInt(3, flag);
			
			rs=	cstmt.executeQuery();
		if(rs != null) {
			while(rs.next()){
				ServicePayout s = new ServicePayout();
				s.setEfrBalance(rs.getFloat("efr_balance"));
				s.setEfrId(rs.getInt("efr_id"));
				s.setFinApprovedAmount(rs.getFloat("fin_approved_amount"));
				s.setFinApproveDate(rs.getString("fin_apfoved_date"));
				s.setApproval(rs.getString("approval"));
				s.setOpsAmount(rs.getFloat("ops_amount"));
				s.setOpsApprovedAmount(rs.getFloat("ops_approved_amount"));
				s.setOpsApprovedDate(rs.getString("ops_approved_date"));
				s.setCityName(rs.getString("city_name"));
				s.setEfrName(rs.getString("efr_name"));
				s.setCreatedDate(rs.getString("created_date"));
				s.setEfrNo(rs.getString("efr_no"));
				
				if(s.getOpsApprovedDate()!=null){
					String opsAprvDt = UtilityFunctions.changeDateFormatToFormat(s.getOpsApprovedDate(), "yyyy-MM-dd HH:mm:ss", "dd-MM-YYYY");
					s.setOpsApprovedDate(opsAprvDt);
				}
				if(s.getFinApproveDate()!=null){
					String finAprvDt = UtilityFunctions.changeDateFormatToFormat(s.getFinApproveDate(), "yyyy-MM-dd HH:mm:ss", "dd-MM-YYYY");
					s.setFinApproveDate(finAprvDt);
				}
				if(s.getCreatedDate()!=null){
					String finAprvDt = UtilityFunctions.changeDateFormatToFormat(s.getCreatedDate(), "yyyy-MM-dd HH:mm:ss", "dd-MM-YYYY");
					s.setCreatedDate(finAprvDt);
				}
				
				payoutList.add(s);		
						
						
			}
		}
						
		}
		catch(Exception e){
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
                    conn.close();
                } catch (SQLException e) { /*ignored*/ }
            }
        }
		
		return payoutList;
	}


	@Override
	public List<Easyfixers> getEfrAppActions(int jobId, int efrId) {
		List<Easyfixers> list = new ArrayList<Easyfixers>();
		Connection conn=null;
		CallableStatement cstmt=null;
		ResultSet rs=null;
		try{
			conn = getJdbcTemplate().getDataSource().getConnection();			
			cstmt=conn.prepareCall("call sp_ef_efr_get_app_action_details(?,?)");
			cstmt.setInt(1, jobId);
			cstmt.setInt(2, efrId);
			rs = cstmt.executeQuery();			
			if(rs != null) {
				while(rs.next()){
					Easyfixers	efrObj = new Easyfixers();
					efrObj.setEasyfixerId(rs.getInt("efr_id"));
					efrObj.setEasyfixerName(rs.getString("efr_name"));
					efrObj.setEasyfixerNo(rs.getString("efr_no"));	
					efrObj.setAction(rs.getString("action"));
					efrObj.setJobId(rs.getInt("job_id"));
					efrObj.setSource(rs.getString("source"));
					
					list.add(efrObj);
				}				
			}
		}
		catch(Exception e){
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
                    conn.close();
                } catch (SQLException e) { /*ignored*/ }
            }
        }
		return list;
	}


	@Override
	public Easyfixers getEfrMetaData(int efrId) throws Exception {
		Easyfixers easyfixers = null;
		String query = "call sp_ef_easyfixer_get_efr_meta_data(?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		easyfixers = getJdbcTemplate().queryForObject(query, new Object[]{efrId}, new RowMapper<Easyfixers>(){
			public Easyfixers mapRow(ResultSet rs, int row) throws SQLException {
				
				Easyfixers easyfixerObj = new Easyfixers();
				
				easyfixerObj.setEasyfixerId(rs.getInt("efr_id"));
				easyfixerObj.setCurrentMonthCompletedJobs(rs.getInt("month_completed_job"));
				easyfixerObj.setOpenJobs(rs.getInt("open_job"));
				easyfixerObj.setLastSixMonthCusRating(rs.getFloat("cust_rating"));
				
				return easyfixerObj; 
			}			
			
		});
		 
		 return easyfixers;
	}


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	

}
