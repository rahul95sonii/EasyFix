package com.easyfix.login.action;

import com.easyfix.util.CommonAbstractAction;

public class LogoutAction extends CommonAbstractAction  {

	private static final long serialVersionUID = 1L;
	String msg;
	
	public String logout(){
		
		msg = "ReLogin";
		return SUCCESS;
	}
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override // for RestrictAccessToUnauthorizedActionInterceptor
    public String toString(){
    	return "LogoutAction";
    }
}
