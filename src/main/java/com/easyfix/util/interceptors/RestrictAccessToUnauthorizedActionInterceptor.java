package com.easyfix.util.interceptors;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.easyfix.user.model.Role;
import com.easyfix.user.model.User;
import com.easyfix.util.Constants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class RestrictAccessToUnauthorizedActionInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	public static final String USER_OBJECT = "user";
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		try{
		HttpServletRequest request = ServletActionContext.getRequest();
	    HttpSession session = request.getSession(false);
	    User userObj =(User) session.getAttribute(Constants.USER_OBJECT);
	    Action action = (Action) invocation.getAction();
	    //System.out.println(action.toString());
	    List<Role> menuList = userObj.getMenuList();
	    boolean hasAccessToAction=false;
	    for(Role r:menuList){
	    	if(r.getActionName().equals(action.toString()))
	    		hasAccessToAction=true;
	    		    }
	   // System.out.println();
	    if(!hasAccessToAction)
	    	return "MENUAUTHENTICATIONERROR";
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	    
		return invocation.invoke();
	}

}
