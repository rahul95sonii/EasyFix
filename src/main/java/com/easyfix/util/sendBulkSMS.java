package com.easyfix.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.easyfix.user.model.User;

public class sendBulkSMS extends JdbcDaoSupport{
	

	public List<User> getnoList(){
		List<User> nos = new ArrayList<User>();
	String query = "select * from tbl_bulk_sms_nos";
	DataSource ds=DBConfig.getContextDataSource();			
	getJdbcTemplate().setDataSource(ds);
	nos = getJdbcTemplate().query(query, new RowMapper<User>(){
		public User mapRow(ResultSet rs, int row) throws SQLException {
			
			User userObj = new User();
			
			userObj.setMobileNo(rs.getString("mobile_no"));
			
			return userObj; 
		}
		
		
	});
    return nos;
	}
	
	public void sendSmstoAll(List<User> list){
		for(User u:list){
			String s = "Are your diwali preparation on? Don't wait for last moment haggles and book your electrician, carpenter, plumber, now on 8882122666 at your day and time of convenience, avail diwali offer and get free visit for any service.";
			try {
				SmsSender.sendSms(s, u.getMobileNo());
				System.out.println(s+": sent to:"+u.getMobileNo() );
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}
