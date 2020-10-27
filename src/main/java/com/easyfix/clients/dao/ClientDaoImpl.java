package com.easyfix.clients.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import com.easyfix.clients.model.ClientWebsite;
import com.easyfix.clients.model.Clients;
import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.settings.model.City;
import com.easyfix.settings.model.ClientRateCard;
import com.easyfix.settings.model.ServiceType;
import com.easyfix.util.DBConfig;
import com.easyfix.util.UtilityFunctions;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;

public class ClientDaoImpl extends JdbcDaoSupport implements ClientDao{

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Clients getClientDetails(int clientId) throws Exception {
		Clients client;
		String query = "call sp_ef_client_getclientdetails_by_id(?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		 client = getJdbcTemplate().queryForObject(query, new Object[]{clientId}, new RowMapper<Clients>(){
			public Clients mapRow(ResultSet rs, int row) throws SQLException {
				
				Clients clientObj = new Clients();
				City cityObj = new City();
				String billStartDt = "";
				clientObj.setClientId(rs.getInt("client_id"));
				clientObj.setClientName(rs.getString("client_name"));
				clientObj.setClientEmail(rs.getString("client_email"));
				clientObj.setTanNumber(rs.getString("tan_number"));
				clientObj.setClientAddress(rs.getString("client_address"));
				clientObj.setCityId(rs.getInt("client_city_id"));				
				clientObj.setClientPincode(rs.getString("client_pincode"));
				cityObj.setCityName(rs.getString("city_name"));
				clientObj.setCityObj(cityObj);				
				clientObj.setIsActive(rs.getInt("client_status"));
				clientObj.setPaidBy(rs.getInt("paid_by"));
				clientObj.setCollectedBy(rs.getInt("collected_by"));
				clientObj.setTravelDist(rs.getInt("travel_distance"));
				clientObj.setInvoiceRaise(rs.getInt("billing_raised"));
				clientObj.setInvoiceCycle(rs.getString("billing_cycle"));
				clientObj.setInvoiceName(rs.getString("billing_name"));
				clientObj.setInvoiceStartDate(rs.getString("billing_start_date"));
				
				if(clientObj.getInvoiceStartDate()!=null) {
					billStartDt = UtilityFunctions.changeDateFormatToFormat(clientObj.getInvoiceStartDate(), "yyyy-MM-dd", "dd-MM-yyyy");
					clientObj.setInvoiceStartDate(billStartDt);
				}
				
				return clientObj; 
			}			
			
		});
		 
		 return client;
	}

	@Override
	public int addUpdateClient(Clients client) throws Exception {
		final Clients myClient = client;
		final String sql = "call sp_ef_client_add_update_client(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		final String insertQuery = "INSERT INTO tbl_client(client_name, `tan_number`, `client_email`,"
		 		+ " `client_address`, `client_city_id`, `client_pincode`, "
		 		+ "`client_status`, inserted_by, insert_date, `paid_by`,`collected_by`,`travel_distance`, `billing_raised`, `billing_cycle`,"
		 		+ "`billing_name`, `billing_start_date`,`updated_by`,`client_type`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)  ";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		int insertId = 0;
		if(client.getClientId()>0)
		 insertId = getJdbcTemplate().update(sql, new Object[]{client.getClientId(),client.getClientName(),client.getTanNumber(),
				client.getClientEmail(), client.getClientAddress(), client.getCityId(), client.getClientPincode(),client.getIsActive(), 
				client.getUpdatedBy().getUserId(), client.getPaidBy(), client.getCollectedBy(), client.getTravelDist(),client.getInvoiceRaise(),
				client.getInvoiceCycle(), client.getInvoiceName(), client.getInvoiceStartDate()});
		else{
			
			DateFormat wdf = new SimpleDateFormat("yyyy-MM-dd");
		final	String now = wdf.format(new Date());
		 
			getJdbcTemplate().update(
				    new PreparedStatementCreator() {
				        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				            PreparedStatement ps =
				                connection.prepareStatement(insertQuery, new String[] {"client_login_id"});
				            ps.setString(1,  myClient.getClientName());
				            ps.setString(2,  myClient.getTanNumber());
				            ps.setString(3,  myClient.getClientEmail());
				            ps.setString(4,  myClient.getClientAddress());
				            ps.setInt(5,  myClient.getCityId());
				            ps.setString(6,  myClient.getClientPincode());
				            ps.setInt(7,  myClient.getIsActive());
				            ps.setInt(8,  myClient.getUpdatedBy().getUserId());
				            ps.setString(9,  now);
				            ps.setInt(10,  myClient.getPaidBy());
				            ps.setInt(11,  myClient.getCollectedBy());
				            ps.setInt(12,  myClient.getTravelDist());
				            ps.setInt(13,  myClient.getInvoiceRaise());
				            if(myClient.getInvoiceRaise()==0){
				            	ps.setString(14,  null);
					            ps.setString(15, null);
					            ps.setString(16, null);
				            }else{
				            ps.setString(14,  myClient.getInvoiceCycle());
				            ps.setString(15,  myClient.getInvoiceName());
				            ps.setString(16,  myClient.getInvoiceStartDate());
				            }
				            ps.setInt(17,  myClient.getUpdatedBy().getUserId());
				            ps.setString(18, "b2b");
				            return ps;
				        }
				    },
				    keyHolder);
			 System.out.println("created client id:"+keyHolder.getKey().intValue());
			 client.setClientId(keyHolder.getKey().intValue());
		}
		
		//add client dashboard credentials through hibernate
			Session session = null;
			Transaction tx=null;
				try{
						session = sessionFactory.openSession();
						 tx = session.beginTransaction();
						if(client.getClientWebsite()!=null){
								ClientWebsite cw = getClientWebSiteDetails(client.getClientId());
								if(cw==null){
									cw = new ClientWebsite();
									cw.setClientId(client.getClientId());
									cw.setLoginName(client.getClientWebsite().getLoginName());
									cw.setLoginPassword(client.getClientWebsite().getLoginPassword());
									cw.setStatus(1);
									session.persist(cw);
									
								}
								else{
									cw.setLoginName(client.getClientWebsite().getLoginName());
									cw.setLoginPassword(client.getClientWebsite().getLoginPassword());
									session.saveOrUpdate(cw);
								}
						}
						tx.commit();
				}
				catch(Exception e){
					if (tx!=null) tx.rollback();
					e.printStackTrace();
				}
				finally{
					if(session!=null && session.isOpen())
						session.close();
				}
		
		return insertId;
	}

	@Override
	public List<Clients> getClientList(int flag) {
		List<Clients> clientList = new ArrayList<Clients>();
		
		String query = "call sp_ef_client_getclientlist(?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		clientList = getJdbcTemplate().query(query, new Object[]{flag}, new RowMapper<Clients>(){
			public Clients mapRow(ResultSet rs, int row) throws SQLException {
				
				Clients clientObj = new Clients();
				City cityObj = new City();
				
				clientObj.setClientId(rs.getInt("client_id"));
				clientObj.setClientName(rs.getString("client_name"));
				clientObj.setClientEmail(rs.getString("client_email"));
				clientObj.setTanNumber(rs.getString("tan_number"));
				clientObj.setClientAddress(rs.getString("client_address"));
				clientObj.setCityId(rs.getInt("client_city_id"));				
				clientObj.setClientPincode(rs.getString("client_pincode"));
				cityObj.setCityName(rs.getString("city_name"));
				clientObj.setCityObj(cityObj);				
				clientObj.setIsActive(rs.getInt("client_status"));
				clientObj.setPaidBy(rs.getInt("paid_by"));
				clientObj.setCollectedBy(rs.getInt("collected_by"));
				clientObj.setTravelDist(rs.getInt("travel_distance"));
				
				return clientObj; 
			}
						
		});
        return clientList;
	}

	@Override
	public List<Clients> getServiceList(int clientId) throws Exception {
		List<Clients> clientServiceList = new ArrayList<Clients>();	
		String query = "call sp_ef_client_getclientservicelist(?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		clientServiceList = getJdbcTemplate().query(query, new Object[]{clientId}, new RowMapper<Clients>(){
			public Clients mapRow(ResultSet rs, int row) throws SQLException {
				
				Clients clientObj = new Clients();
				ServiceType servicTypeObj = new ServiceType();
				ClientRateCard rateCardObj = new ClientRateCard();
				
				clientObj.setClientServiceId(rs.getInt("client_service_id"));
				clientObj.setClientId(rs.getInt("client_id"));
				clientObj.setServiceTypeId(rs.getInt("service_type_id"));
				clientObj.setRateCardId(rs.getInt("rate_card_id"));
				clientObj.setClientName(rs.getString("client_name"));
				
				clientObj.setChargeType(rs.getInt("charge_type"));
				clientObj.setTotalCharge(rs.getString("total_amount"));
				clientObj.setEasyfixDirectFixed(rs.getString("easyfix_direct_fixed"));
				clientObj.setEasyfixDirectVariable(rs.getString("easyfix_direct_variable"));
				clientObj.setOverheadFixed(rs.getString("overhead_fixed"));
				clientObj.setOverheadVariable(rs.getString("overhead_variable"));
				clientObj.setClientFixed(rs.getString("client_fixed"));
				clientObj.setClientVariable(rs.getString("client_variable"));
				clientObj.setServiceStatus(rs.getInt("service_status"));
				servicTypeObj.setServiceTypeName(rs.getString("service_type_name"));
				rateCardObj.setClientRateCardName(rs.getString("crc_ratecard_name"));				
				clientObj.setServiceTypeObj(servicTypeObj);
				clientObj.setClientRateCardObj(rateCardObj);
				
				return clientObj; 
			}
			
			
		});
        return clientServiceList;
	}
	
	@Override
	public int addUpdateClientServices(Clients service) throws Exception {
		Connection conn = null;
		CallableStatement cstmt = null;
		int activityId = 0;
		try {
			//conn = getJdbcTemplate().getDataSource().getConnection();
			DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();
			if(conn != null){
				cstmt = conn.prepareCall("{call sp_ef_client_add_update_client_service(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				if(cstmt != null) {
					cstmt.setInt(1, service.getClientServiceId());
					cstmt.setInt(2, service.getClientId());
					cstmt.setInt(3, service.getServiceTypeId());
					cstmt.setInt(4, service.getRateCardId());
					cstmt.setInt(5, service.getChargeType());
					cstmt.setDouble(6, Double.parseDouble(service.getTotalCharge()));
					cstmt.setDouble(7, Double.parseDouble(service.getEasyfixDirectFixed()));
					cstmt.setDouble(8, Double.parseDouble(service.getEasyfixDirectVariable()));
					cstmt.setDouble(9, Double.parseDouble(service.getOverheadFixed()));
					cstmt.setDouble(10, Double.parseDouble(service.getOverheadVariable()));
					cstmt.setDouble(11, Double.parseDouble(service.getClientFixed()));
					cstmt.setDouble(12, Double.parseDouble(service.getClientVariable()));
					cstmt.setInt(13, service.getServiceStatus());
					cstmt.setInt(14, service.getUpdatedBy().getUserId());
					cstmt.executeUpdate();
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
			
		return activityId;
		
	}
	public int addUpdateClientServicesFromExcel(Clients service) throws Exception{
		
		Connection conn = null;
		CallableStatement cstmt = null;
		int activityId = 0;
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
//			DataSource ds=DBConfig.getContextDataSource();			
//			conn = ds.getConnection();
			if(conn != null){
				cstmt = conn.prepareCall("{call sp_ef_client_add_update_client_service_from_excel(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				if(cstmt != null) {
					cstmt.setInt(1, service.getClientServiceId());
					cstmt.setInt(2, service.getClientId());
					cstmt.setInt(3, service.getServiceTypeId());
					cstmt.setInt(4, service.getRateCardId());
					cstmt.setInt(5, service.getChargeType());
					cstmt.setDouble(6, Double.parseDouble(service.getTotalCharge()));
					cstmt.setDouble(7, Double.parseDouble(service.getEasyfixDirectFixed()));
					cstmt.setDouble(8, Double.parseDouble(service.getEasyfixDirectVariable()));
					cstmt.setDouble(9, Double.parseDouble(service.getOverheadFixed()));
					cstmt.setDouble(10, Double.parseDouble(service.getOverheadVariable()));
					cstmt.setDouble(11, Double.parseDouble(service.getClientFixed()));
					cstmt.setDouble(12, Double.parseDouble(service.getClientVariable()));
					cstmt.setInt(13, service.getServiceStatus());
					cstmt.setInt(14, service.getUpdatedBy().getUserId());
					cstmt.registerOutParameter(15,Types.INTEGER);
					cstmt.executeUpdate();
					activityId= cstmt.getInt(15);
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
		//System.out.println(activityId);
		return activityId;
	}

	
	public Clients getClientServicesDetails(int clientServiceId) throws Exception {
		
		Clients client;
		String query = "call sp_ef_client_getclientservicedetails_by_id(?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		 client = getJdbcTemplate().queryForObject(query, new Object[]{clientServiceId}, new RowMapper<Clients>(){
			public Clients mapRow(ResultSet rs, int row) throws SQLException {
						
						Clients clientObj = new Clients();
						ServiceType servicTypeObj = new ServiceType();
						ClientRateCard rateCardObj = new ClientRateCard();
						
						clientObj.setClientServiceId(rs.getInt("client_service_id"));
						clientObj.setClientId(rs.getInt("client_id"));
						clientObj.setServiceTypeId(rs.getInt("service_type_id"));
						clientObj.setRateCardId(rs.getInt("rate_card_id"));						
						clientObj.setChargeType(rs.getInt("charge_type"));
						clientObj.setTotalCharge(rs.getString("total_amount"));
						clientObj.setEasyfixDirectFixed(rs.getString("easyfix_direct_fixed"));
						clientObj.setEasyfixDirectVariable(rs.getString("easyfix_direct_variable"));
						clientObj.setOverheadFixed(rs.getString("overhead_fixed"));
						clientObj.setOverheadVariable(rs.getString("overhead_variable"));
						clientObj.setClientFixed(rs.getString("client_fixed"));
						clientObj.setClientVariable(rs.getString("client_variable"));
						clientObj.setServiceStatus(rs.getInt("service_status"));
						servicTypeObj.setServiceTypeName(rs.getString("service_type_name"));
						rateCardObj.setClientRateCardName(rs.getString("crc_ratecard_name"));				
						clientObj.setServiceTypeObj(servicTypeObj);
						clientObj.setClientRateCardObj(rateCardObj);
						
						return clientObj; 
					}
			
		});
		 
		 return client;
	}
	
	@Override
	public List<Clients> getClientServiceListByClientIdAndServiceTypeId(int fkClientId, int fkServiceTypeId) throws Exception {
		List<Clients> clientServiceList = new ArrayList<Clients>();
		String query = "call sp_ef_client_getservicelist_by_clientid_servicetypeid(?,?)";
		try {
			DataSource ds=DBConfig.getContextDataSource();			
			getJdbcTemplate().setDataSource(ds);
			clientServiceList = getJdbcTemplate().query(query, new Object[]{fkClientId,fkServiceTypeId}, new RowMapper<Clients>(){
				public Clients mapRow(ResultSet rs, int row) throws SQLException {
					
					Clients clientObj = new Clients();
					ClientRateCard rateCardObj = new ClientRateCard();
					clientObj.setClientServiceId(rs.getInt("client_service_id"));
					clientObj.setRateCardId(rs.getInt("rate_card_id"));
					clientObj.setTotalCharge(rs.getString("total_amount"));
					rateCardObj.setClientRateCardName(rs.getString("crc_ratecard_name"));
					clientObj.setClientRateCardObj(rateCardObj);
					
					return clientObj; 
				}
				
				
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		
        return clientServiceList;
	}

	@Override
	public int addDeleteClientEasyfixerMapping(Clients client) throws Exception {
		String sql = "call sp_ef_clienteasyfixermapping_add_delete_easyfixer(?,?,?,?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		int insertId = getJdbcTemplate().update(sql, new Object[]{client.getMappingId(),client.getClientId(),client.getServiceTypeId(),
				client.getEasyfixerId()});
		
		return insertId;
	}

	@Override
	public List<Clients> getEasyfixerListForClient(int clientId, int mappingStatus) throws Exception {
		List<Clients> mappedEasyfixerList = new ArrayList<Clients>();
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
			//conn = getJdbcTemplate().getDataSource().getConnection();
			DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();
			if(conn != null){
				cstmt = conn.prepareCall("{call sp_ef_clienteasyfixermapping_geteasyfixerlist(?,?)}");
				cstmt.setInt(1, clientId);
				cstmt.setInt(2, mappingStatus);
				rs = cstmt.executeQuery();
				if(rs!=null){
					while(rs.next()){
						Clients clientObj = new Clients();
						Easyfixers efrObj = new Easyfixers();
						City cityObj = new City();
						cityObj.setCityName(rs.getString("city_name"));
						ServiceType serviceTypeObj = new ServiceType();
						clientObj.setMappingId(rs.getInt("mapping_id"));
						clientObj.setEasyfixerId(rs.getInt("efr_id"));
						clientObj.setClientId(rs.getInt("client_id"));
						//clientObj.setServiceTypeId(rs.getInt("service_type_id"));
						clientObj.setMappingStatus(rs.getInt("mapping_status"));
						efrObj.setEasyfixerName(rs.getString("efr_name"));
						efrObj.setEasyfixerNo(rs.getString("efr_no"));
						efrObj.setEasyfixerServiceType(rs.getString("service_type_ids"));
						efrObj.setEasyfixerId(rs.getInt("efr_id"));
						serviceTypeObj.setServiceTypeName(rs.getString("service_type_name"));
						efrObj.setCityObj(cityObj);
						clientObj.setEasyfixerObj(efrObj);
						clientObj.setServiceTypeObj(serviceTypeObj);
						mappedEasyfixerList.add(clientObj);
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			
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
			
		return mappedEasyfixerList;		
		
	}

	@Override
	public List<Easyfixers> getEasyfixerForMapping(int clientId, int serviceTypeId) throws Exception {
		List<Easyfixers> easyfixerList = new ArrayList<Easyfixers>();
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		try {
			//conn = getJdbcTemplate().getDataSource().getConnection();
			DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();
			if(conn!=null){
				cstmt = conn.prepareCall("{call sp_ef_easyfixer_listformapping(?,?)}");
				cstmt.setInt(1, clientId);
				cstmt.setInt(2, serviceTypeId);
				rs = cstmt.executeQuery();
				if(rs!=null){
					while(rs.next()){
						Easyfixers efrObj = new Easyfixers();
						efrObj.setEasyfixerId(rs.getInt("efr_id"));
						efrObj.setEasyfixerName(rs.getString("efr_name"));
						efrObj.setEasyfixerNo(rs.getString("efr_no"));
						easyfixerList.add(efrObj);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally {
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
			
		return easyfixerList;
	}

	@Override
	public int saveMappedEasyfixer(int clientId, int serviceTypeId,	List<String> list) throws Exception {
		Connection conn = null;
		CallableStatement cstmt = null;
		int status = 0;
		try {
			//conn = getJdbcTemplate().getDataSource().getConnection();
			DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();
			if(conn!=null){
				cstmt = conn.prepareCall("{call sp_ef_easyfixer_save_mapping(?,?,?)}");
				cstmt.setInt(1, clientId);
				cstmt.setInt(2, serviceTypeId);
				for (int i = 0; i < list.size(); i++) {					
					cstmt.setInt(3, Integer.parseInt(list.get(i)));
					cstmt.executeUpdate();					
					}
				}

		} catch (Exception e) {
			e.printStackTrace();
			
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
		return status;
	}

	@Override
	public int updateMappedEasyfixer(int clientId, int efrId, String flag, List<String> list) throws Exception {
		Connection conn = null;
		CallableStatement cstmt = null;
		int status = 0;
		try {
			//conn = getJdbcTemplate().getDataSource().getConnection();
			DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();
			if(conn!=null){
				if(flag.equalsIgnoreCase("Map") ) {
					cstmt = conn.prepareCall("{call sp_ef_easyfixer_save_mapping(?,?,?)}");
					cstmt.setInt(1, clientId);
					cstmt.setInt(2, efrId);
					for (int i = 0; i < list.size(); i++) {					
						cstmt.setInt(3, Integer.parseInt(list.get(i)));
						cstmt.executeUpdate();					
						}
					status = 1;
					}
				else {
					cstmt = conn.prepareCall("{call sp_ef_easyfixer_update_mapping(?,?)}");					
					cstmt.setInt(1, clientId);
					cstmt.setInt(2, efrId);
					cstmt.executeUpdate();
					status = 0;
				}
			}				
			
		} catch (Exception e) {
			e.printStackTrace();
			
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
			
		return status;
	}

	@Override
	public ClientWebsite getClientWebSiteDetails(int clientId) throws Exception {
		Session sesison = null;
		ClientWebsite cl = null;
		try{
		 sesison = sessionFactory.openSession();
		Criteria cr = sesison.createCriteria(ClientWebsite.class);
		
		if(clientId>0)
			cr.add(Restrictions.eq("clientId", clientId));
		
		cr.add(Restrictions.eq("status", 1));
		cl =  (ClientWebsite) cr.uniqueResult();
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if (sesison!=null && sesison.isOpen())
				sesison.close();
		}
		return cl;
	}

}
