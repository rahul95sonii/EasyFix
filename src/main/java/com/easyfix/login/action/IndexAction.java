package com.easyfix.login.action;

import com.easyfix.user.model.User;
import com.easyfix.util.CommonAbstractAction;
import com.easyfix.util.Constants;
import com.opensymphony.xwork2.ModelDriven;

public class IndexAction extends CommonAbstractAction implements ModelDriven<User> {
	
	
	private static final long serialVersionUID = 1L;
	private User userObj;
	private String title;
	private String msg;
	private String actMenu;
	private String actParent;
	private String redirectUrl;

	@Override
	public User getModel() {
		userObj = new User();
		return userObj;
	}
	
public String index() throws Exception {
		
		String acttitle= "Easyfix : Dashboard";
		setActMenu("Home");
		setActParent("Home");
		setTitle(acttitle);
		
		userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
		//User userObj = (User) session.get(Constants.USER_OBJECT);
		
		
		if(userObj != null){
			if(userObj.getRoleId() == 10)
				return "clientLogin";
			else
				return "home";
		}
		return "FIRSTLOGIN";


}

	public User getUserObj() {
		return userObj;
	}
	
	public void setUserObj(User userObj) {
		this.userObj = userObj;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getRedirectUrl() {
		return redirectUrl;
	}
	
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
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
    	return "IndexAction";
    }
	
}
