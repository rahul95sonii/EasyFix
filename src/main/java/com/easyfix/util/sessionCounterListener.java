package com.easyfix.util;

import javax.servlet.http.HttpSession;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.easyfix.user.delegate.UserService;


public class sessionCounterListener implements HttpSessionListener  {

   /* private static int totalActiveSessions;
    
    static{
    	totalActiveSessions =0;
    }
    
    public static int getTotalActiveSession(){
          return totalActiveSessions;
    }
    */
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
//		HttpSession session = arg0.getSession();
//		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
//    	UserService userSr = (UserService) ctx.getBean("userService");
//    	try {
//    		System.out.println("login: "+arg0.getSession().getId());
//    		User user = (User)session.getAttribute(Constants.USER_OBJECT);
//			userSr.updateUserLogin(user.getUserId(), arg0.getSession().getId(), "Login");
//			//LoginAction sessiontimeOut = (LoginAction) ctx.getBean("sessionTimeOut");
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		//totalActiveSessions--;
      //  System.out.println("sessionDestroyed - deduct one session from counter");	
      //  printCounter(arg0);
    	HttpSession session = arg0.getSession();
//    	String localSessionId = session.getId();
//    	System.out.println(localSessionId);
    	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
    	UserService userSr = (UserService) ctx.getBean("userService");
    	try {
    //		System.out.println("logout: "+arg0.getSession().getId());
			userSr.updateUserLogin(0, arg0.getSession().getId(), "LogOut");
		//	System.out.println("Session Id "+arg0.getSession().getId());
			//LoginAction sessiontimeOut = (LoginAction) ctx.getBean("sessionTimeOut");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*public void printCounter(HttpSessionEvent event){
		HttpSession session = event.getSession();
		  ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
		  LogoutAction logoutAction = (LogoutAction) ctx.getBean("logout");
		  try {
			  logoutAction.logout();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	  counterSr.printCounter(totalActiveSessions);
	}
*/
}
