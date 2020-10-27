package com.easyfix.user.action;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easyfix.clients.delegate.ClientService;
import com.easyfix.clients.model.Clients;
import com.easyfix.settings.delegate.CityService;
import com.easyfix.settings.model.City;
import com.easyfix.user.delegate.*;
import com.easyfix.user.model.Role;
import com.easyfix.user.model.User;
import com.easyfix.util.CommonAbstractAction;
import com.easyfix.util.Constants;
import com.opensymphony.xwork2.ModelDriven;

 
public class UserAction extends CommonAbstractAction implements ModelDriven<User>{
	private static final Logger logger = LogManager.getLogger(UserAction.class);
	private static final long serialVersionUID = 1L;
	
	private UserService userService;
	private CityService cityService;
	private ClientService clientService;
	
	
	private User userObj;
	private String actMenu;
	private String actParent;
	private String title;
	private String redirectUrl;
	
	private List<User> userList;
	private List<Role> roleList;
	private List<City> cityList;
	private List<Clients> clientList;
	
	
	@Override
	public User getModel() {
		return setUserObj(new User());
	}

	public String user() throws Exception {
		
		try {

			String acttitle= "Easyfix : Manage Users";
			setActMenu("Manage User");
			setActParent("User");
			setTitle(acttitle);				
			
			userList = userService.getUserList(2); //2 for all user
	//		System.out.println(userList);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
				
		return SUCCESS;
		
	}
	
	public String addEditUser() throws Exception {
		
		try {
			
			roleList = userService.getRoleList(1);
			cityList = cityService.getCityList();
			clientList = clientService.getClientList(1);
			
			if(userObj.getUserId() != 0)
			{
				userObj = userService.getUserDetailsById(userObj.getUserId());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		
		return SUCCESS;
	}
	
	public String addUpdateUser() throws Exception {	
		
		try {
			int id  = ((User) requestObject.getSession().getAttribute(Constants.USER_OBJECT)).getUserId();
			userObj.setUpdatedBy(id);
			int stauts = userService.addUpdateUser(userObj);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
			
		return SUCCESS;		
	}
	public String getUserCRMActiveTime() throws Exception{
	/*	try{
		List<User> userList = userService.getUserLoginLogoutDetails(7);
		Timestamp previousLogin = null;
		Timestamp previousLogout = null;
		long activeTime=0;
		for(User u:userList){
			if(u.getLoginLogoutAction().trim().equals("logIn")){
				previousLogin=u.getLoginLogoutDateTime();
				System.out.println(u.getLoginLogoutAction().trim()+" :"+previousLogin);
			}
			else {
				previousLogout = u.getLoginLogoutDateTime();
				System.out.println(u.getLoginLogoutAction().trim()+":"+previousLogout);
			}
			
			if(previousLogin!=null && previousLogout!=null && 
					u.getLoginLogoutDateTime().getTime()>previousLogin.getTime()){
				
			long diffMilliSec = previousLogout.getTime()-previousLogin.getTime();
			System.out.println("diff: "+diffMilliSec );
			 activeTime=activeTime +Math.abs(diffMilliSec);
			 System.out.println("active time: "+activeTime );

			}
		}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		*/
		try{
			List<User> userList = userService.getUserList(2); //2 for all user
			for(User u:userList){
			long l = userService.getUserCRMActiveTime(u.getUserId());
			System.out.println(u.getUserName()+": "+l/3600000);
			u.setCrmActiveTime(l);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			logger.catching(e);
		}
		return SUCCESS;
	}

	
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public User getUserObj() {
		return userObj;
	}


	public User setUserObj(User userObj) {
		this.userObj = userObj;
		return userObj;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<City> getCityList() {
		return cityList;
	}

	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public String getActMenu() {
		return actMenu;
	}

	public void setActMenu(String actMenu) {
		this.actMenu = actMenu;
	}

	public String getActParent() {
		return actParent;
	}

	public void setActParent(String actParent) {
		this.actParent = actParent;
	}
	@Override
	public String toString(){
		return "UserAction";
	}

	public ClientService getClientService() {
		return clientService;
	}

	public List<Clients> getClientList() {
		return clientList;
	}

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}

	public void setClientList(List<Clients> clientList) {
		this.clientList = clientList;
	}
}