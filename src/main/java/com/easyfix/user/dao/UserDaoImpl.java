package com.easyfix.user.dao;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.easyfix.settings.model.City;
import com.easyfix.user.dao.UserDao;
import com.easyfix.user.model.Role;
import com.easyfix.user.model.User;
import com.easyfix.util.DBConfig;


public class UserDaoImpl extends JdbcDaoSupport implements UserDao {
	
	public int addUpdateUser(User userObj){
		
		String sql = "call sp_ef_user_add_update_user(?,?,?,?,?,?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		int insertId = getJdbcTemplate().update(sql, new Object[]{userObj.getUserId(), userObj.getRoleId(), 
				userObj.getManageCities(), userObj.getUserStatus(),userObj.getUpdatedBy(),userObj.getLoginClient()});
		return insertId;
		

	}

	@Override
	public List<User> getUserList(int status) throws Exception {
		List<User> userList = new ArrayList<User>();
		
		String query = "call sp_ef_user_getuserlist(?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		userList = getJdbcTemplate().query(query, new Object[]{status}, new RowMapper<User>(){
			public User mapRow(ResultSet rs, int row) throws SQLException {
				
				User userObj = new User();
				City cityObj = new City();
				Role roleObj = new Role();
				userObj.setUserId(rs.getInt("user_id"));
				userObj.setUserCode(rs.getString("user_code"));
				userObj.setUserName(rs.getString("user_name"));
				userObj.setOfficialEmail(rs.getString("official_email"));
				userObj.setUserStatus(rs.getInt("user_status"));
				userObj.setCityId(rs.getInt("city_id"));
				userObj.setManageCities(rs.getString("cities"));
				userObj.setRoleId(rs.getInt("user_role"));
				userObj.setLoginClient(rs.getInt("client_id"));
				cityObj.setCityName(rs.getString("city_name"));
				userObj.setCityObj(cityObj);
				roleObj.setRoleName(rs.getString("role_name"));
				userObj.setRoleObj(roleObj);
				
				return userObj; 
			}
			
			
		});
        return userList;
	}

	@Override
	public User getUserDetailsByEmail(String email) throws Exception {
		User user = null;
		
		String query = "call sp_ef_user_getuserdetails(?)";
		try {			
			DataSource ds=DBConfig.getContextDataSource();			
			getJdbcTemplate().setDataSource(ds);
			 user = getJdbcTemplate().queryForObject(query, new Object[]{email}, new RowMapper<User>(){
				public User mapRow(ResultSet rs, int row) throws SQLException {
					
					User userObj = new User();
					City cityObj = new City();
					Role roleObj = new Role();
					userObj.setUserId(rs.getInt("user_id"));
					userObj.setUserCode(rs.getString("user_code"));
					userObj.setUserName(rs.getString("user_name"));
					userObj.setOfficialEmail(rs.getString("official_email"));
					userObj.setUserStatus(rs.getInt("user_status"));
					userObj.setCityId(rs.getInt("city_id"));
					userObj.setManageCities(rs.getString("cities"));
					userObj.setRoleId(rs.getInt("user_role"));
					userObj.setLoginClient(rs.getInt("client_id"));
					cityObj.setCityName(rs.getString("city_name"));
					userObj.setCityObj(cityObj);
					roleObj.setRoleName(rs.getString("role_name"));
					userObj.setRoleObj(roleObj);
					
					return userObj; 
				}			
			});			 
			
		} catch (EmptyResultDataAccessException  e) {
			return null;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User getUserDetailsById(int userId) throws Exception {
		User user = null;
		String query = "call sp_ef_user_getuserdetails_by_id(?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		 user = getJdbcTemplate().queryForObject(query, new Object[]{userId}, new RowMapper<User>(){
			public User mapRow(ResultSet rs, int row) throws SQLException {
				
				User userObj = new User();
				City cityObj = new City();
				Role roleObj = new Role();
				userObj.setUserId(rs.getInt("user_id"));
				userObj.setUserCode(rs.getString("user_code"));
				userObj.setUserName(rs.getString("user_name"));
				userObj.setOfficialEmail(rs.getString("official_email"));
				userObj.setUserStatus(rs.getInt("user_status"));
				userObj.setCityId(rs.getInt("city_id"));
				userObj.setManageCities(rs.getString("cities"));
				userObj.setRoleId(rs.getInt("user_role"));
				userObj.setLoginClient(rs.getInt("client_id"));
				cityObj.setCityName(rs.getString("city_name"));
				userObj.setCityObj(cityObj);
				roleObj.setRoleName(rs.getString("role_name"));
				userObj.setRoleObj(roleObj);
				
				return userObj; 
			}			
			
		});
		 
		 return user;
	}

	@Override
	public List<Role> getRoleList(int status) throws Exception {
		List<Role> roleList = new ArrayList<Role>();
		
		String query = "call sp_ef_role_getrolelist(?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		roleList = getJdbcTemplate().query(query, new Object[]{status}, new RowMapper<Role>(){
			public Role mapRow(ResultSet rs, int row) throws SQLException {
				
				Role roleObj = new Role();
				roleObj.setRoleId(rs.getInt("role_id"));
				roleObj.setRoleName(rs.getString("role_name"));
				roleObj.setRoleDesc(rs.getString("role_desc"));
				roleObj.setRoleStatus(rs.getInt("role_status"));
				roleObj.setMenuName(rs.getString("menus"));
				
				
				return roleObj; 
			}
			
			
		});
        return roleList;
	}

	@Override
	public Role getRoleDetailsById(int roleId) throws Exception {
		Role role = null;
		String query = "call sp_ef_role_getroledetails_by_id(?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		role = getJdbcTemplate().queryForObject(query, new Object[]{roleId}, new RowMapper<Role>(){
			public Role mapRow(ResultSet rs, int row) throws SQLException {
				
				Role roleObj = new Role();
				roleObj.setRoleId(rs.getInt("role_id"));
				roleObj.setRoleName(rs.getString("role_name"));
				roleObj.setRoleDesc(rs.getString("role_desc"));
				roleObj.setMenuIds(rs.getString("menu_ids"));
				roleObj.setRoleStatus(rs.getInt("role_status"));
			
				
				return roleObj; 
			}			
			
		});
		 
		 return role;
	}
	
	public int addUpdateRole(Role roleObj){
		
		String sql = "call sp_ef_role_add_update_role(?,?,?,?,?,?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		int insertId = getJdbcTemplate().update(sql, new Object[]{roleObj.getRoleId(), roleObj.getRoleName(), 
						roleObj.getRoleDesc(), roleObj.getMenuIds(), roleObj.getRoleStatus(), roleObj.getUpdatedBy()});
		return insertId;
		

	}

	@Override
	public User getFirstUserLoginDetails(String name, String email) throws Exception {
		User user = null;
		
		String query = "call sp_ef_user_getuser_credentials(?,?)";
		try {
			DataSource ds=DBConfig.getContextDataSource();			
			getJdbcTemplate().setDataSource(ds);		
			user = getJdbcTemplate().queryForObject(query, new Object[]{name,email}, new RowMapper<User>(){
				public User mapRow(ResultSet rs, int row) throws SQLException {
					User userObj = new User();
					userObj.setUserId(rs.getInt("user_id"));
					userObj.setUserCode(rs.getString("user_code"));
					userObj.setUserName(rs.getString("user_name"));
					userObj.setOfficialEmail(rs.getString("official_email"));
					userObj.setUserStatus(rs.getInt("user_status"));
					userObj.setCityId(rs.getInt("city_id"));
					userObj.setManageCities(rs.getString("cities"));
					userObj.setRoleId(rs.getInt("user_role"));
					userObj.setMenues(rs.getString("menus"));
					userObj.setLoginClient(rs.getInt("client_id"));
					userObj.setDisplayJobDashboard(rs.getInt("display_job_dashboard"));
					userObj.setMobileNo(rs.getString("mobile_no"));
					
					return userObj; 
				}			
			});		
			
		} catch (Exception  e) {
			e.printStackTrace();
			
		}	
		return user;
	}

	@Override
	public List<Role> getMenuList(int status) throws Exception {
		List<Role> menuList = new ArrayList<Role>();
		
		String query = "call sp_ef_menu_getmenulist(?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		menuList = getJdbcTemplate().query(query, new Object[]{status}, new RowMapper<Role>(){
			public Role mapRow(ResultSet rs, int row) throws SQLException {
				
				Role roleObj = new Role();
				roleObj.setMenuName(rs.getString("menu_name"));
				roleObj.setMenuId(rs.getInt("act_menu_id"));
				roleObj.setParentMenu(rs.getInt("parent_menu"));
				roleObj.setMenuUrl(rs.getString("url"));
				roleObj.setMenuDepth(rs.getInt("menu_depth"));
				roleObj.setHasChild(rs.getInt("has_child"));
			
				return roleObj; 
			}
			
			
		});
        return menuList;
	}

	
	public List<Role>getUserMenuList(String menues) throws Exception {
		List<Role> menuList = new ArrayList<Role>();
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
				String query = "call sp_ef_user_getuser_menuaccess_list(?)";
				menuList = getJdbcTemplate().query(query, new Object[]{menues}, new RowMapper<Role>(){
					public Role mapRow(ResultSet rs, int row) throws SQLException {
						
						Role roleObj = new Role();
						roleObj.setMenuName(rs.getString("menu_name"));
						roleObj.setMenuId(rs.getInt("menu_id"));
						roleObj.setParentMenu(rs.getInt("parent_menu"));
						roleObj.setMenuUrl(rs.getString("url"));
						roleObj.setIcons(rs.getString("icons"));
						roleObj.setActionName(rs.getString("action_name"));
						roleObj.setMenuDepth(rs.getInt("menu_depth"));
						roleObj.setHasChild(rs.getInt("has_child"));
						return roleObj; 
					}					
					
				});
		        return menuList;
	}

	@Override
	public void updateUserLogin(int userId, String sessionId, String flag) throws Exception {
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		String sql = "call sp_ef_user_update_login_logout(?,?,?)";		
		getJdbcTemplate().update(sql, new Object[]{userId,sessionId,flag});
		
	}

	@Override
	public List<User> getUserListForChangeOwner() throws Exception {
		List<User> userList = new ArrayList<User>();
		
		String query = "call sp_ef_user_get_active_userlist_for_jobOwner()";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		userList = getJdbcTemplate().query(query, new RowMapper<User>(){
			public User mapRow(ResultSet rs, int row) throws SQLException {
				
				User userObj = new User();
				Role roleObj = new Role();
				userObj.setUserId(rs.getInt("user_id"));
				userObj.setUserCode(rs.getString("user_code"));
				userObj.setUserName(rs.getString("user_name"));
				userObj.setOfficialEmail(rs.getString("official_email"));
				userObj.setUserStatus(rs.getInt("user_status"));
				userObj.setRoleId(rs.getInt("user_role"));
				roleObj.setRoleName(rs.getString("role_name"));
				userObj.setLoginClient(rs.getInt("client_id"));
				userObj.setRoleObj(roleObj);
				
				return userObj; 
			}
			
			
		});
        return userList;
	}

	@Override
	public List<User> getUserLoginLogoutDetails(int userId) throws Exception {
	  List<User> loginLogoutList = new ArrayList<User>(); 
	    String query = "call sp_ef_user_getuser_login_logout_details(?,?)";
	    loginLogoutList =  getJdbcTemplate().query(query,new Object[]{userId,1}, new RowMapper<User>(){
			public User mapRow(ResultSet rs, int row) throws SQLException {
				
				User userObj = new User();
				
				userObj.setUserId(rs.getInt("user_id"));
				userObj.setUserCode(rs.getString("user_code"));
				userObj.setUserName(rs.getString("user_name"));
				userObj.setOfficialEmail(rs.getString("official_email"));
				userObj.setUserStatus(rs.getInt("user_status"));
				userObj.setRoleId(rs.getInt("user_role"));
				userObj.setLoginStatus(rs.getInt("login_status"));
				userObj.setLoginLogoutDateTime(rs.getTimestamp("user_time"));
				userObj.setLoginLogoutAction(rs.getString("user_action"));
				
				return userObj; 
			}
			
			
		});
	  
        return loginLogoutList;
	}

	@Override
	public List<User> updateUserLogin() throws Exception {
		List<User> userList = new ArrayList<User>();
		String query = "call sp_ef_user_getuserlist_for_tracking()";

		userList = getJdbcTemplate().query(query, new Object[]{}, new RowMapper<User>(){
			public User mapRow(ResultSet rs, int row) throws SQLException {
				
				User userObj = new User();
				Role roleObj = new Role();
				userObj.setUserId(rs.getInt("user_id"));
				userObj.setUserCode(rs.getString("user_code"));
				userObj.setUserName(rs.getString("user_name"));
				userObj.setOfficialEmail(rs.getString("official_email"));
				userObj.setUserStatus(rs.getInt("user_status"));
				userObj.setCityId(rs.getInt("city_id"));
				userObj.setRoleId(rs.getInt("user_role"));
				userObj.setLoginClient(rs.getInt("client_id"));
				roleObj.setRoleName(rs.getString("role_name"));
				userObj.setRoleObj(roleObj);
				
				return userObj; 
			}
			
			
		});
        return userList;
	}

	@Override
	public User getUserDetailsByMobleNo(String mobileNo) throws Exception {
		String query = "SELECT * FROM `tbl_user` WHERE `alternate_no`=? OR `mobile_no`=?";
		User user =null;
		try{
		 user = getJdbcTemplate().queryForObject(query, new Object[]{mobileNo,mobileNo}, new RowMapper<User>(){
			public User mapRow(ResultSet rs, int row) throws SQLException {
				
				User userObj = new User();
				userObj.setUserId(rs.getInt("user_id"));
				userObj.setUserCode(rs.getString("user_code"));
				userObj.setUserName(rs.getString("user_name"));
				userObj.setAlternateNo(rs.getString("alternate_no"));
				userObj.setMobileNo(rs.getString("mobile_no"));
				userObj.setOfficialEmail(rs.getString("official_email"));
				userObj.setUserStatus(rs.getInt("user_status"));
				userObj.setCityId(rs.getInt("city_id"));
				userObj.setRoleId(rs.getInt("user_role"));
				userObj.setLoginClient(rs.getInt("client_id"));

				return userObj; 
			}			
		});			 
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public List<User> userListByRole(String roleId) throws Exception {
		List<User> userList = new ArrayList<User>();
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		User userObj = null;
		
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
			if(conn != null){
				cstmt = conn.prepareCall("call sp_ef_user_getuser_list_by_role(?)");
				if(cstmt != null){
					cstmt.setString(1, roleId);
					rs = cstmt.executeQuery();
					if(rs != null){
						while(rs.next()){
							userObj = new User();
							userObj.setUserId(rs.getInt("user_id"));
							userObj.setUserName(rs.getString("user_name"));
							userObj.setOfficialEmail(rs.getString("official_email"));
							userObj.setCityId(rs.getInt("city_id"));
							userObj.setRoleId(rs.getInt("user_role"));
							userList.add(userObj);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(conn!=null)
				rs.close();
			if(cstmt!=null)
				cstmt.close();
			if(rs!=null)
				conn.close();
		}
		
		return userList;
	}
}
