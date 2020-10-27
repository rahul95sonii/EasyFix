package com.easyfix.user.dao;

import java.util.List;

import com.easyfix.user.model.Role;
import com.easyfix.user.model.User;

public interface UserDao {
	
	public List<User> getUserList(int status) throws Exception;
	public int addUpdateUser(User userObj) throws Exception;
	public User getUserDetailsByEmail(String email) throws Exception;
	public User getUserDetailsById(int userId) throws Exception;
	
	public List<Role> getRoleList(int status) throws Exception;
	public Role getRoleDetailsById(int roleId) throws Exception;
	public int addUpdateRole(Role roleObj) throws Exception;
	public User getFirstUserLoginDetails(String name, String email) throws Exception;
	
	public List<Role> getMenuList(int status) throws Exception;
	public List<Role> getUserMenuList(String menues) throws Exception;
	public void updateUserLogin(int userId, String sessionId, String flag) throws Exception;
	public List<User> getUserListForChangeOwner() throws Exception;
	
	public List<User> getUserLoginLogoutDetails(int userId) throws Exception;
	public List<User> updateUserLogin() throws Exception;
	public User getUserDetailsByMobleNo( String mobileNo) throws Exception;
	public List<User> userListByRole(String roleId) throws Exception;
	
	
}
