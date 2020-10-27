package com.easyfix.util.utilityFunction.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.easyfix.util.utilityFunction.dao.ContextRefreshedEventDao;

public class ContextRefreshedEventDaoImpl extends JdbcDaoSupport implements ContextRefreshedEventDao {

	@Override
	public void setUserLoginStatusAndDate() throws Exception {
		String query = "update tbl_user set login_status = 0 ";
		String setLogoutDate = " UPDATE tbl_user_login_logout_logs SET logout_date_time "
								+ "= NOW() WHERE logout_date_time IS NULL";
		getJdbcTemplate().execute(query);
		getJdbcTemplate().execute(setLogoutDate);
				
		
	}

}
