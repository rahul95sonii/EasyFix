package com.easyfix.user.delegate;

import java.sql.Timestamp;
import java.util.List;

import com.easyfix.user.dao.UserDao;
import com.easyfix.user.model.Role;
import com.easyfix.user.model.User;

public class UserServiceImpl implements UserService {	
	
	private  UserDao userDao;
	
	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	@Override
	public List<User> getUserList(int status) throws Exception {
		
		return userDao.getUserList(status);
	}
	
	@Override
	public int addUpdateUser(User userObj) throws Exception {
		return userDao.addUpdateUser(userObj);
	}

	@Override
	public User getUserDetailsByEmail(String email) throws Exception {
		return userDao.getUserDetailsByEmail(email);
	}

	@Override
	public User getUserDetailsById(int userId) throws Exception {
		return userDao.getUserDetailsById(userId);
	}

	@Override
	public List<Role> getRoleList(int status) throws Exception {
		return userDao.getRoleList(status);
	}

	@Override
	public Role getRoleDetailsById(int roleId) throws Exception {
		return userDao.getRoleDetailsById(roleId);
	}

	@Override
	public int addUpdateRole(Role roleObj) throws Exception {
		return userDao.addUpdateRole(roleObj);
	}

	@Override
	public User getFirstUserLoginDetails(String name, String email) throws Exception {
		return userDao.getFirstUserLoginDetails(name, email);
	}

	@Override
	public List<Role> getMenuList(int status) throws Exception {
		return userDao.getMenuList(status);
	}

	@Override
	public List<Role> getUserMenuList(String menues) throws Exception {
		return userDao.getUserMenuList(menues);
	}

	@Override
	public void updateUserLogin(int userId, String sessionId, String flag) throws Exception {
		 userDao.updateUserLogin(userId, sessionId, flag);
		
	}

	@Override
	public List<User> getUserListForChangeOwner() throws Exception {
		return userDao.getUserListForChangeOwner();
	}

	@Override
	public List<User> getUserLoginLogoutDetails(int userId) throws Exception {
		return userDao.getUserLoginLogoutDetails( userId);
		
	}
	public long getUserCRMActiveTime(int userId)throws Exception{
		List<User> userList = getUserLoginLogoutDetails(userId);
		Timestamp previousLogin = null;
		Timestamp previousLogout = null;
		long activeTime=0;
		try{
				for(User u:userList){
				if(u.getLoginLogoutAction().trim().equals("logIn")){
					previousLogin=u.getLoginLogoutDateTime();
				//	System.out.println(u.getLoginLogoutAction().trim()+" :"+previousLogin);
				}
				else {
					previousLogout = u.getLoginLogoutDateTime();
				//	System.out.println(u.getLoginLogoutAction().trim()+":"+previousLogout);
				}
				
				if(previousLogin!=null && previousLogout!=null && 
						u.getLoginLogoutDateTime().getTime()>previousLogin.getTime()){
					
				long diffMilliSec = previousLogout.getTime()-previousLogin.getTime();
			//	System.out.println("diff: "+diffMilliSec );
				 activeTime=activeTime +Math.abs(diffMilliSec);
			//	 System.out.println("active time: "+activeTime );

				}
				//u.setCrmActiveTime(activeTime);
			}
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
	return activeTime;	
	}
	public User getUserDetailsByMobleNo( String mobileNo) throws Exception{
		return userDao.getUserDetailsByMobleNo(mobileNo);
		
	}
	@Override
	public List<User> getUserListForTracking() throws Exception {
		return userDao.updateUserLogin();
	}
	
	public static void main(String[] arg){
		UserServiceImpl userServiceImpl = new UserServiceImpl();
		try {
			User u =userServiceImpl.getUserDetailsByMobleNo("123");
			System.out.println(u.getUserName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<User> userListByRole(String roleId) throws Exception {
		return userDao.userListByRole(roleId);
	}

}
