package com.easyfix.settings.action;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easyfix.settings.delegate.ServiceTypeService;
import com.easyfix.settings.model.ServiceType;
import com.easyfix.util.CommonAbstractAction;
import com.opensymphony.xwork2.ModelDriven;

public class ServiceTypeAction extends CommonAbstractAction implements ModelDriven<ServiceType>{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(ServiceTypeAction.class);
	private ServiceTypeService serviceTypeService;
	
	private ServiceType serviceTypeObj;
	private String actMenu;
	private String actParent;
	private String title;
	private String redirectUrl;
	
	private List<ServiceType> serviceTypeList;
	
	public String serviceType() throws Exception {
			
		try {
			String acttitle= "Easyfix : Manage Service Type";
			setActMenu("Manage Service Type");
			setActParent("Settings");
			setTitle(acttitle);	
			
			serviceTypeList = serviceTypeService.getSerTypeList(2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
				
		return SUCCESS;
			
	}
	
	public String addEditServiceType() throws Exception {
		
		try {
						
			if(serviceTypeObj.getServiceTypeId() != 0){
				serviceTypeObj = serviceTypeService.getSerTypeDetailsById(serviceTypeObj.getServiceTypeId());
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		
		return SUCCESS;
	}
	
	public String addUpdateServiceType() throws Exception {		
		try {
			
			int stauts = serviceTypeService.addUpdateSerType(serviceTypeObj);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		return SUCCESS;
		
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
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	@Override
	public ServiceType getModel() {
		// TODO Auto-generated method stub
		return setServiceTypeObj(new ServiceType());
	}

	public List<ServiceType> getServiceTypeList() {
		return serviceTypeList;
	}

	public void setServiceTypeList(List<ServiceType> serviceTypeList) {
		this.serviceTypeList = serviceTypeList;
	}

	public ServiceType getServiceTypeObj() {
		return serviceTypeObj;
	}

	public ServiceType setServiceTypeObj(ServiceType serviceType) {
		this.serviceTypeObj = serviceType;
		return serviceTypeObj;
	}

	public ServiceTypeService getServiceTypeService() {
		return serviceTypeService;
	}

	public void setServiceTypeService(ServiceTypeService serviceTypeService) {
		this.serviceTypeService = serviceTypeService;
	}

	@Override // for RestrictAccessToUnauthorizedActionInterceptor
	public String toString(){
		return "ServiceTypeAction";
	}

}
