package com.easyfix.settings.action;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easyfix.settings.delegate.RetailRateCardService;
import com.easyfix.settings.delegate.ServiceTypeService;
import com.easyfix.settings.model.RetailRateCard;
import com.easyfix.settings.model.ServiceType;
import com.easyfix.util.CommonAbstractAction;
import com.opensymphony.xwork2.ModelDriven;

public class RetailRateCardAction extends CommonAbstractAction implements ModelDriven<RetailRateCard> {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(RetailRateCardAction.class);
		private RetailRateCardService retailRateCardService;
		private ServiceTypeService serviceTypeService;
		
		private RetailRateCard retailRateCardObj;
		private String actMenu;
		private String actParent;
		private String title;
		private String redirectUrl;
		
		private List<RetailRateCard> retailRateCardList;
		private List<ServiceType> serviceTypeList;
		

		@Override
		public RetailRateCard getModel() {
			return setRetailRateCardObj(new RetailRateCard());
		}
		
		public String retailRateCard() throws Exception {
			
			try {

				String acttitle= "Easyfix : Manage Retail Rate Card";
				setActMenu("retailratecard");
				setActParent("Settings");
				setTitle(acttitle);
				
				serviceTypeList = serviceTypeService.getSerTypeList(1);
				
				setRetailRateCardList(retailRateCardService.getRetailRateCardList());
		//		System.out.println(retailRateCardList);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.catching(e);
			}
					
			return SUCCESS;
		}
		
		public String addEditRetailRateCard() throws Exception {
			try {
				serviceTypeList = serviceTypeService.getSerTypeList(1);
				
				if(retailRateCardObj.getRetailRateCardId() != 0)
				{
					retailRateCardObj = retailRateCardService.getRetailRateCardDetailsById(retailRateCardObj.getRetailRateCardId());
				}
			
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.catching(e);
			//	
			}
			
			return SUCCESS;
			}	
		
			public String addUpdateRetailRateCard() throws Exception {	
				
				try {
					
					int stauts = retailRateCardService.addUpdateRetailRateCard(retailRateCardObj);
					
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

			public ServiceTypeService getServiceTypeService() {
				return serviceTypeService;
			}

			public void setServiceTypeService(ServiceTypeService serviceTypeService) {
				this.serviceTypeService = serviceTypeService;
			}

			public RetailRateCard getRetailRateCardObj() {
				return retailRateCardObj;
			}

			public RetailRateCard setRetailRateCardObj(RetailRateCard retailRateCardObj) {
				 this.retailRateCardObj = retailRateCardObj;
				 return retailRateCardObj;
			}

			public String getRedirectUrl() {
				return redirectUrl;
			}

			public void setRedirectUrl(String redirectUrl) {
				this.redirectUrl = redirectUrl;
			}

			public List<RetailRateCard> getRetailRateCardList() {
				return retailRateCardList;
			}

			public void setRetailRateCardList(List<RetailRateCard> retailRateCardList) {
				this.retailRateCardList = retailRateCardList;
			}

			public List<ServiceType> getServiceTypeList() {
				return serviceTypeList;
			}

			public void setServiceTypeList(List<ServiceType> serviceTypeList) {
				this.serviceTypeList = serviceTypeList;
			}
			
			public RetailRateCardService getRetailRateCardService() {
				return retailRateCardService;
			}

			public void setRetailRateCardService(RetailRateCardService retailRateCardService) {
				this.retailRateCardService = retailRateCardService;
			}
			@Override // for RestrictAccessToUnauthorizedActionInterceptor
			public String toString(){
				return "RetailRateCardAction";
			}

		
	}

		
		