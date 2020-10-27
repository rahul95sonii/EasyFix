package com.easyfix.settings.action;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easyfix.user.delegate.UserService;
import com.easyfix.user.model.Role;
import com.easyfix.user.model.User;
import com.easyfix.util.CommonAbstractAction;
import com.easyfix.util.Constants;
import com.opensymphony.xwork2.ModelDriven;

public class UserTypeAction extends CommonAbstractAction implements ModelDriven<Role>{
	private static final Logger logger = LogManager.getLogger(UserTypeAction.class);
	private static final long serialVersionUID = 1L;
	
	private UserService userService;
	
	private Role roleObj;
	private String actMenu;
	private String actParent;
	private String title;
	private List<Role> roleList;
	private List<Role> menuList;
	
	
	@Override
	public Role getModel() {
		return setRoleObj(new Role());
	}
	
	public String userType() throws Exception {
		try {

			String acttitle= "Easyfix : Manage User Type";
			setActMenu("Manage Role");
			setActParent("Settings");
			setTitle(acttitle);
			
			roleList = userService.getRoleList(2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
				
		return SUCCESS;
	}
	
	public String addEditUserType() throws Exception {
		
		try {	
				menuList = userService.getMenuList(1);
				if(roleObj.getRoleId() != 0)
				{
					roleObj = userService.getRoleDetailsById(roleObj.getRoleId());
				}
			
			} catch (Exception e) {
				e.printStackTrace();
				logger.catching(e);
		}	
		
		return SUCCESS;
	}

public String addUpdateUserType() throws Exception {	
	
	try {
		int userId  = ((User) requestObject.getSession().getAttribute(Constants.USER_OBJECT)).getUserId();
		roleObj.setUpdatedBy(userId);
		int stauts = userService.addUpdateRole(roleObj);
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	}
		
	return SUCCESS;	
}
	
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	
	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public Role getRoleObj() {
		return roleObj;
	}

	public Role setRoleObj(Role roleObj) {
		this.roleObj = roleObj;
		return roleObj;
	}

	public List<Role> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Role> menuList) {
		this.menuList = menuList;
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

	@Override // for RestrictAccessToUnauthorizedActionInterceptor
	public String toString(){
		return "UserTypeAction";
	}

	

}
