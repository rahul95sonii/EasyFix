package com.easyfix.util;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAware;

public class CommonAbstractAction extends ActionSupport implements 
ParameterAware, RequestAware, ServletContextAware, ServletRequestAware,SessionAware,ValidationAware,Validateable {
	
	private static final long serialVersionUID = 1L;
	public Map<String, String[]> parameters;
	public Map<String, Object> request;
	public Map<String, Object> session;
	public ServletContext servletContext;
	public HttpServletRequest requestObject;
	public HttpSession sessionObj;
	//public HttpServletResponse responseObject;
	
	
	
	/**
	 * @return ServletRequest Host header value
	 */
	public String getHost() {
		return requestObject.getHeader("Host");
	}
	
	public String getRealPath(){
		String basePath = ServletActionContext.getServletContext().getRealPath("/");
		
		return basePath;

	}

	public Map<String, String[]> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String[]> parameters) {
		this.parameters = parameters;
	}

	public Map<String, Object> getRequest() {
		return request;
	}

	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public HttpServletRequest getRequestObject() {
		return requestObject;
	}

	public void setRequestObject(HttpServletRequest requestObject) {
		this.requestObject = requestObject;
	}

	public HttpSession getSessionObj() {
		return sessionObj;
	}

	public void setSessionObj(HttpSession sessionObj) {
		this.sessionObj = sessionObj;
	}

	
	public void setServletRequest(HttpServletRequest requestObject) {
		this.requestObject = requestObject;
		
	}

}
