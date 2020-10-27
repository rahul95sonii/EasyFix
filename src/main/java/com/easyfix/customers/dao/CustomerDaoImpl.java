package com.easyfix.customers.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.easyfix.customers.model.Address;
import com.easyfix.customers.model.Customers;
import com.easyfix.settings.model.City;
import com.easyfix.user.model.User;
import com.easyfix.util.DBConfig;

public class CustomerDaoImpl extends JdbcDaoSupport implements CustomerDao {

	@Override
	public List<Customers> getCustomerList() throws Exception {
		
		List<Customers> customerList = new ArrayList<Customers>();
		
		String query = "call sp_ef_customer_getcustomerlist";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		customerList = getJdbcTemplate().query(query, new RowMapper<Customers>(){
			public Customers mapRow(ResultSet rs, int row) throws SQLException {
				
				Customers customerObj = new Customers();
				City cityObj = new City();
				Address addrObj = new Address();
				cityObj.setCityName(rs.getString("city_name"));
				addrObj.setCityObj(cityObj);
				customerObj.setAddressObj(addrObj);
				customerObj.setCustomerId(rs.getInt("customer_id"));
				customerObj.setCustomerMobNo(rs.getString("customer_mob_no"));
				customerObj.setCustomerName(rs.getString("customer_name"));
				customerObj.setCustomerEmail(rs.getString("customer_email"));				
				
				return customerObj; 
			}
			
		});
        return customerList;
		
		
	}

	@Override
	public int addUpdateCustomer(Customers customerObj, int userId) throws Exception {
		Connection conn=null;
		CallableStatement cstmtCust=null;
		CallableStatement cstmtAdd = null;
		int status=0;
		int customerId;
		int addressId;
		try{
			//conn = getJdbcTemplate().getDataSource().getConnection();
			DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();
			cstmtCust=conn.prepareCall("call sp_ef_customer_add_update_customer(?,?,?,?,?,?,?)");
			cstmtCust.setInt(1,customerObj.getCustomerId());
			cstmtCust.setString(2,customerObj.getCustomerMobNo());
			cstmtCust.setString(3,customerObj.getCustomerName());
			cstmtCust.setString(4,customerObj.getCustomerEmail());
			cstmtCust.setInt(5,1); //Customer Status
			cstmtCust.setInt(6,userId);
			cstmtCust.registerOutParameter(7, Types.INTEGER);
			cstmtCust.executeUpdate();
			customerId=cstmtCust.getInt(7);
			if(customerObj.getCustomerId() == 0) {
				cstmtAdd=conn.prepareCall("call  sp_ef_address_add_update_address(?,?,?,?,?,?,?,?)");
				cstmtAdd.setInt(1, customerObj.getAddressObj().getAddressId());
				cstmtAdd.setInt(2, customerId);
				cstmtAdd.setString(3, customerObj.getAddressObj().getAddress());
				cstmtAdd.setInt(4, customerObj.getAddressObj().getCityObj().getCityId());
				cstmtAdd.setString(5,customerObj.getAddressObj().getPinCode());
				cstmtAdd.setString(6,customerObj.getAddressObj().getGpsLocation());	
				cstmtAdd.setInt(7, userId);
				cstmtAdd.registerOutParameter(8, Types.INTEGER);	
				cstmtAdd.executeUpdate();
				addressId=cstmtAdd.getInt(8);	
			}
			status = customerId;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
			finally {
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
	            if (conn != null) {
	                try {
	                    conn.close();
	                } catch (SQLException e) { /*ignored*/ }
	            }
	        
		}
		
		return status;		
	}

	@Override
	public Customers getCustomerDetailsById(int customerId) throws Exception {
		
		Connection conn=null;
		CallableStatement cstmt=null;
		ResultSet rs = null;
		Customers customerObj = null;
		
		try {
			//conn = getJdbcTemplate().getDataSource().getConnection();
			DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();
			cstmt=conn.prepareCall("call sp_ef_customer_getcustomerdetails_by_id(?)");
			cstmt.setInt(1,customerId);
			rs = cstmt.executeQuery();
			if(rs!=null){
				while(rs.next()){
					customerObj = new Customers();
					customerObj.setCustomerId(rs.getInt("customer_id"));
					customerObj.setCustomerMobNo(rs.getString("customer_mob_no"));
					customerObj.setCustomerName(rs.getString("customer_name"));
					customerObj.setCustomerEmail(rs.getString("customer_email"));	
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} 	finally {
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
	
				 
		 return customerObj;	
		
	}

	@Override
	public int addUpdateCustomerAddress(Customers customerObj, int userId) throws Exception{
		Connection con=null;
		CallableStatement cstmt=null;
		int status=0;
		try{
			//con = getJdbcTemplate().getDataSource().getConnection();
			DataSource ds=DBConfig.getContextDataSource();			
			con = ds.getConnection();
			cstmt=con.prepareCall("call  sp_ef_address_add_update_address(?,?,?,?,?,?,?,?)");
			cstmt.setInt(1,customerObj.getAddressObj().getAddressId());
			cstmt.setLong(2,customerObj.getCustomerId());
			cstmt.setString(3,customerObj.getAddressObj().getAddress());
			cstmt.setInt(4,customerObj.getAddressObj().getCityObj().getCityId());
			cstmt.setString(5,customerObj.getAddressObj().getPinCode());
			cstmt.setString(6,customerObj.getAddressObj().getGpsLocation());
			cstmt.setInt(7, userId);
			cstmt.registerOutParameter(8, Types.INTEGER);		
			cstmt.executeUpdate();
			status=cstmt.getInt(8);
			
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
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) { /*ignored*/ }
            }
        
	}
		return status;
	}

	@Override
	public Customers getCustomerDetailsByMobileNo(String mobileNo)	throws Exception {
		Connection conn=null;
		CallableStatement cstmt=null;
		ResultSet rs = null;
		Customers customerObj = null;		
		try {
			//conn = getJdbcTemplate().getDataSource().getConnection();
			DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();
			cstmt=conn.prepareCall("call sp_ef_customer_getcustomerdetails_by_mobileno(?)");
			cstmt.setString(1,mobileNo);
			rs = cstmt.executeQuery();
			if(rs!=null){
				while(rs.next()){
					customerObj = new Customers();
					customerObj.setCustomerId(rs.getInt("customer_id"));
					customerObj.setCustomerMobNo(rs.getString("customer_mob_no"));
					customerObj.setCustomerName(rs.getString("customer_name"));
					customerObj.setCustomerEmail(rs.getString("customer_email"));	
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
				 
		 return customerObj;
	}

	@Override
	public List<Address> getAddressList(int customerId) throws Exception {
		List<Address> addressList = new ArrayList<Address>();
				
				String query = "call sp_ef_customer_getcustomer_address_list(?)";
				DataSource ds=DBConfig.getContextDataSource();			
				getJdbcTemplate().setDataSource(ds);
				addressList = getJdbcTemplate().query(query, new Object[]{customerId}, new RowMapper<Address>(){
					public Address mapRow(ResultSet rs, int row) throws SQLException {
						Address addrObj = new Address();
						City cityObj = new City();
						addrObj.setAddressId(rs.getInt("address_id"));
						addrObj.setCustomerId(rs.getInt("customer_id"));
						addrObj.setAddress(rs.getString("address"));
						addrObj.setPinCode(rs.getString("pin_code"));
						addrObj.setGpsLocation(rs.getString("gps_location"));
						cityObj.setCityId(rs.getInt("city_id"));
						cityObj.setCityName(rs.getString("city_name"));
						addrObj.setCityObj(cityObj);						
						
						return addrObj; 
					}
					
				});
		 return addressList;
	}

	@Override
	public Customers getCustomerAddressDetailsById(int addressId) throws Exception {
		Connection conn=null;
		CallableStatement cstmt=null;
		ResultSet rs = null;
		Address addressObj = null;
		City cityObj = null;
		Customers custObj = null;
		try {
			//conn = getJdbcTemplate().getDataSource().getConnection();
			DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();
			cstmt=conn.prepareCall("call sp_ef_customer_getcustomer_address_by_addressId(?)");
			cstmt.setInt(1,addressId);
			rs = cstmt.executeQuery();
			if(rs!=null){
				while(rs.next()){
					addressObj = new Address();
					cityObj = new City();
					custObj = new Customers();
					custObj.setCustomerId(rs.getInt("customer_id"));
					addressObj.setAddressId(rs.getInt("address_id"));
					addressObj.setCustomerId(rs.getInt("customer_id"));
					addressObj.setAddress(rs.getString("address"));					
					addressObj.setPinCode(rs.getString("pin_code"));	
					addressObj.setGpsLocation(rs.getString("gps_location"));
					cityObj.setCityId(rs.getInt("city_id"));
					cityObj.setCityName(rs.getString("city_name"));
					addressObj.setCityObj(cityObj);
					custObj.setAddressObj(addressObj);
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
				 
		 return custObj;
	}

	@Override
	public Address getAddressDetailsById(int addressId) throws Exception {
		Connection conn=null;
		CallableStatement cstmt=null;
		ResultSet rs = null;
		Address addressObj = null;
		City cityObj = null;
		try {
			//conn = getJdbcTemplate().getDataSource().getConnection();
			DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();
			cstmt=conn.prepareCall("call sp_ef_customer_getcustomer_address_by_addressId(?)");
			cstmt.setInt(1,addressId);
			rs = cstmt.executeQuery();
			if(rs!=null){
				while(rs.next()){
					addressObj = new Address();
					cityObj = new City();
					addressObj.setAddressId(rs.getInt("address_id"));
					addressObj.setCustomerId(rs.getInt("customer_id"));
					addressObj.setAddress(rs.getString("address"));					
					addressObj.setPinCode(rs.getString("pin_code"));	
					addressObj.setGpsLocation(rs.getString("gps_location"));
					cityObj.setCityId(rs.getInt("city_id"));
					cityObj.setCityName(rs.getString("city_name"));
					cityObj.setMaxDistance(rs.getString("city_max_distance"));
					addressObj.setCityObj(cityObj);
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
				 
		 return addressObj;
	}
	
	@Override
	public List<Customers> getnoListForBulkSMS() {
		List<Customers> nos = new ArrayList<Customers>();
	String query = "select * from tbl_bulk_sms_nos";
	DataSource ds=DBConfig.getContextDataSource();			
	getJdbcTemplate().setDataSource(ds);
	nos = getJdbcTemplate().query(query, new RowMapper<Customers>(){
		public Customers mapRow(ResultSet rs, int row) throws SQLException {
			
			Customers userObj = new Customers();
			
			userObj.setCustomerMobNo(rs.getString("mobile_no"));
			
			return userObj; 
		}
		
		
	});
    return nos;
	}
	


	
}
