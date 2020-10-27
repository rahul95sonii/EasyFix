package com.easyfix.util.utilityFunction.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.easyfix.Jobs.delegate.JobService;
import com.easyfix.Jobs.model.Jobs;
import com.easyfix.user.model.User;
import com.easyfix.util.DBConfig;

public class ActiveUserJobListDaoImpl extends JdbcDaoSupport implements ActiveUserJobListDao {
private JobService jobService;

 	@Override
 	public List<Jobs> getActiveJobList(int... flag) throws Exception {
 		List<Jobs> activeJobs = new ArrayList<Jobs>();
 		for(int x: flag){
 			activeJobs.addAll(jobService.getJobList(x));
 		}
 		return activeJobs;
 		}
	

	@Override
	public List<User> getACtiveUserList(int loginStatus) throws Exception {
		List<User> userList = new ArrayList<User>();
		
		String query = "call sp_ef_user_getactiveuserlist(?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		userList = getJdbcTemplate().query(query,new Object[]{loginStatus}, new RowMapper<User>(){
			public User mapRow(ResultSet rs, int row) throws SQLException {
				
				User userObj = new User();
				
				userObj.setUserId(rs.getInt("user_id"));
				userObj.setUserCode(rs.getString("user_code"));
				userObj.setUserName(rs.getString("user_name"));
				userObj.setOfficialEmail(rs.getString("official_email"));
				userObj.setUserStatus(rs.getInt("user_status"));
				//userObj.setCityId(rs.getInt("city_id"));
				//userObj.setManageCities(rs.getString("cities"));
				userObj.setRoleId(rs.getInt("user_role"));
				userObj.setLoginStatus(rs.getInt("login_status"));
				userObj.setSessionId(rs.getString("session_id"));
				//cityObj.setCityName(rs.getString("city_name"));
				//userObj.setCityObj(cityObj);
				//roleObj.setRoleName(rs.getString("role_name"));
				//userObj.setRoleObj(roleObj);
				
				return userObj; 
			}
			
			
		});
        return userList;
	}
	
	public void assignJobToUser(User user,Jobs job){
		int userid = user.getUserId();
		int jobid = job.getJobId();
		String query = "call sp_ef_job_update_job(?,?,?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		getJdbcTemplate().update(query,new Object[]{jobid,userid,"changeOwner"});
		
	}
	
	public JobService getJobService() {
	return jobService;
	}

	public void setJobService(JobService jobService) {
	this.jobService = jobService;
	}

}
