package com.easyfix.util.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.easyfix.login.action.IndexAction;
import com.easyfix.user.model.User;
import com.easyfix.util.Constants;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AuthenticationInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 1L;
	public static final String USER_OBJECT = "user";

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		final ActionContext context = invocation.getInvocationContext();
	    //HttpServletRequest request = (HttpServletRequest) context.get(HTTP_REQUEST);
		HttpServletRequest request = ServletActionContext.getRequest();
	    HttpSession session = request.getSession(true);
	   // Object user = session.getAttribute(Constants.USER_HANDLE);

		/*String result=null;
		User user = (User) invocation.getInvocationContext().getSession().get(Constants.USER_OBJECT);
		Object action = invocation.getAction();
		if(user == null) {
			((ValidationAware) action).addActionMessage("This page requires Authentication, please logon    Or   Session Expired.");
			result= "MANEYAUTHENTICATIONERROR";
		}else{
		ActionContext context = invocation.getInvocationContext();
	    HttpServletRequest request = (HttpServletRequest) context.get(ServletActionContext.HTTP_REQUEST);
	    result=invocation.invoke();
		}*/
		
		User user =(User) session.getAttribute(Constants.USER_OBJECT);
       //except login action   
        Object action = invocation.getAction();   
       if (user!=null && action instanceof IndexAction) {   
           return invocation.invoke();   
        }   
       //check session   
       if(user==null ){   
           return "FIRSTLOGIN";   
        }   
       return invocation.invoke();//go on   
	}

}
