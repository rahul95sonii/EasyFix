package com.easyfix.login.action;

import java.util.List;

import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

import com.easyfix.user.delegate.UserService;
import com.easyfix.user.model.Role;
import com.easyfix.user.model.User;
import com.easyfix.util.CommonAbstractAction;
import com.easyfix.util.Constants;
import com.easyfix.util.GoogleAutenticationUtil;
import com.opensymphony.xwork2.ModelDriven;

public class LoginAction extends CommonAbstractAction implements ModelDriven<User>{
	private static final Logger logger = LogManager.getLogger(LoginAction.class);
	private static final long serialVersionUID = 1L;
	
	private User userObj;
	private String title;
	private String msg;
	private String redirectUrl;
	
	
	private UserService userService;
	final GoogleAuthHelper helperObj= new GoogleAuthHelper();
	private String jsonString;
	JSONObject jObject  = new JSONObject();	


	public User getModel(){
		return userObj = new User();
	}
	
	
	public String login() throws Exception {
		
		//userObj = userServiceObj.getUserDetails(userObj.getUserId());
		String acttitle= "Easyfix : Login";
		setTitle(acttitle);
		
		
		
		String state = requestObject.getParameter("state");
		String code = requestObject.getParameter("code");
		
		if (code == null || state == null) {
			requestObject.getSession().setAttribute("state", helperObj.getStateToken());
			
			redirectUrl = "login";
			msg = "";
		
		}else if (code != null && state != null	) {
			
			requestObject.getSession().removeAttribute("state");
			
			String jsonIdentity = helperObj.getUserInfoJson(code);

			JSONObject object = new JSONObject(jsonIdentity);
			String email = object.getString("email");
	        String name = object.getString("name");
	        String picture = object.getString("picture");
	        
	        if(email != null){
	        	String[] userName = email.split("@");
	        	String tempEF = userName[1];
	        	if(tempEF.equalsIgnoreCase("easyfix.in")){
	        		userObj = userService.getFirstUserLoginDetails(name, email);
//	        		if(alreadyLogedIn(userObj.getUserId())){
//	        			 
//	        		}
	        		
	        		if(userObj != null){
		        		if(userObj.getUserId() != 0 && email.equalsIgnoreCase(userObj.getOfficialEmail())) {
		        			userObj.setPicture(picture);    			
		        			String localsessionId= requestObject.getSession().getId();
		        			
		        			userService.updateUserLogin( userObj.getUserId(), localsessionId, "logIn");
		        			//updateUserLogin
		        			List<Role> menuList = userService.getUserMenuList(userObj.getMenues());
		        			userObj.setMenuList(menuList);
	        				requestObject.getSession().setAttribute(Constants.USER_OBJECT,userObj);      				
	        			
	        				userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
		        		
			        		requestObject.getSession().setAttribute("userId", userObj.getUserId());
			        		requestObject.getSession().setAttribute("email", userObj.getOfficialEmail());
			        		requestObject.getSession().setAttribute("name", userObj.getUserName());
			        		requestObject.getSession().setAttribute("picture", picture);
			        		requestObject.getSession().setAttribute("manageCities", userObj.getManageCities());
			        		requestObject.getSession().setAttribute("menues", userObj.getMenues());
			        		requestObject.getSession().setAttribute("menuList", userObj.getMenuList());
			        		requestObject.getSession().setAttribute("role", userObj.getRoleId());
			        		
			        		
			        		redirectUrl = "index";		        		
			        		msg = "Y";
		        		}
		        		else {
		        			redirectUrl = "invalidlogin";
			        		msg = "InactiveUser";
		        		}
		        		
		        	}
	        		else {
	        			redirectUrl = "invalidlogin";
		        		msg = "InactiveUser";
	        		}
	        	}
	        	else {
	        		redirectUrl = "invalidlogin";
	        		msg = "ReLogin";
	        	}
	        }
	        else {
	        	redirectUrl = "invalidlogin";
        		msg = "E";
	        }
			
		}
		
		
		//sessionObj.put("state", helperObj.getStateToken());
		
		
		return redirectUrl;
		
	}
	

	
	
public String logout() throws Exception {
	
	try {
	
		HttpSession localSession = requestObject.getSession(false);

		
		if(localSession!=null){
			
			int userId = (Integer) requestObject.getSession(false).getAttribute("userId");
		//	userService.updateUserLogin(userId,requestObject.getSession(false).getId(),"logOut");
			requestObject.getSession(false).invalidate();
			msg = "Logout";
		}
		else {
			
			msg = "SessionOut";		
		}
		redirectUrl = "relogin";
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
		
	}
	
	
	return redirectUrl;
}


public String clientLogin() throws Exception {
	String acttitle= "Easyfix : Client Login";
	setTitle(acttitle);
	try {
		
		redirectUrl = "cLogin";
			/*HttpSession localSession = requestObject.getSession();
		
			if(localSession!=null){
				
				int userId = (Integer) requestObject.getSession(false).getAttribute("userId");
				userService.updateUserLogin(userId,requestObject.getSession(false).getId(),"logOut");
				requestObject.getSession(false).invalidate();
				msg = "Logout";
			}
			else {
				
				msg = "SessionOut";		
			}
			redirectUrl = "relogin";*/
	} catch (Exception e) {
		// TODO: handle exception
	}	
	
	return redirectUrl;
}

public String clientLoginAuthentication() throws Exception {
	
	try {
			/*HttpSession localSession = requestObject.getSession();
		
			if(localSession!=null){
				
				int userId = (Integer) requestObject.getSession(false).getAttribute("userId");
				userService.updateUserLogin(userId,requestObject.getSession(false).getId(),"logOut");
				requestObject.getSession(false).invalidate();
				msg = "Logout";
			}
			else {
				
				msg = "SessionOut";		
			}
			redirectUrl = "relogin";*/
	} catch (Exception e) {
		// TODO: handle exception
	}	
	
	return SUCCESS;
}
	


public String googleAuthenticate() throws Exception {
	
	//userObj = userServiceObj.getUserDetails(userObj.getUserId());
	String authenticationResult=ERROR;
	userObj.getOfficialEmail();
	userObj.getPassword();
	
	int authentication=GoogleAutenticationUtil.getInstance().authenticateLoginCredential(userObj.getOfficialEmail(), userObj.getPassword());
	
	if(authentication==1){
		authenticationResult=SUCCESS;
	}

	return authenticationResult;
	
}


	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getJsonString() {
		return jsonString;
	}


	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}


	public User getUserObj() {
		return userObj;
	}


	public void setUserObj(User userObj) {
		this.userObj = userObj;
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


	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Override // for RestrictAccessToUnauthorizedActionInterceptor
    public String toString(){
    	return "LoginAction";
    }
	


}
