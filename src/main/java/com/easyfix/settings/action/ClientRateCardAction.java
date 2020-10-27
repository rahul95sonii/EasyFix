package com.easyfix.settings.action;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easyfix.settings.delegate.ClientRateCardService;
import com.easyfix.settings.delegate.ServiceTypeService;
import com.easyfix.settings.model.ClientRateCard;
import com.easyfix.settings.model.ServiceType;
import com.easyfix.user.model.User;
import com.easyfix.util.CommonAbstractAction;
import com.easyfix.util.Constants;
import com.opensymphony.xwork2.ModelDriven;

public class ClientRateCardAction extends CommonAbstractAction implements ModelDriven<ClientRateCard>{
	
	private static final Logger logger = LogManager.getLogger(ClientRateCardAction.class);
	private static final long serialVersionUID = 1L;
	
	private ClientRateCardService clientRateCardService;
	private ServiceTypeService serviceTypeService;
	
	private ClientRateCard clientRateCardObj;
	private String actMenu;
	private String actParent;
	private String title;
	private String redirectUrl;
	
	private List<ClientRateCard> clientRateCardList;
	private List<ServiceType> serviceTypeList;
	

	@Override
	public ClientRateCard getModel(){
		return setClientRateCardObj(new ClientRateCard());
	}
	
	public String clientRateCard() throws Exception {
		
		try {
			String acttitle= "Easyfix : Manage Client Rate Card";
			setActMenu("Manage Services");
			setActParent("Settings");
			setTitle(acttitle);
			
			serviceTypeList = serviceTypeService.getSerTypeList(1);
			
			setClientRateCardList(clientRateCardService.getClientRateCardList());
	//		System.out.println(clientRateCardList);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		
		return SUCCESS;
	}
	
	public String addEditClientRateCard() throws Exception {
		try {
			
			serviceTypeList = serviceTypeService.getSerTypeList(1);
										
			if(clientRateCardObj.getClientRateCardId() != 0)
			{
				clientRateCardObj = clientRateCardService.getClientRateCardDetailsById(clientRateCardObj.getClientRateCardId());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		
		return SUCCESS;
	}	
	
public String addUpdateClientRateCard() throws Exception {	
	
	try {
		
		int userId  = ((User) requestObject.getSession().getAttribute(Constants.USER_OBJECT)).getUserId();
		clientRateCardObj.setUpdatedBy(userId);
		int stauts = clientRateCardService.addUpdateClientRateCard(clientRateCardObj);
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	}
		
	return SUCCESS;		
}
		
public String getRateCardList() throws Exception {	
	
	try {
			clientRateCardList = clientRateCardService.getRateCardListForClient(clientRateCardObj.getClientId(), 
												clientRateCardObj.getServiceTypeId());
		
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

		public ClientRateCardService getClientRateCardService() {
			return clientRateCardService;
		}

		public void setClientRateCardService(ClientRateCardService clientRateCardService) {
			this.clientRateCardService = clientRateCardService;
		}

		public ServiceTypeService getServiceTypeService() {
			return serviceTypeService;
		}

		public void setServiceTypeService(ServiceTypeService serviceTypeService) {
			this.serviceTypeService = serviceTypeService;
		}

		public ClientRateCard getClientRateCardObj() {
			return clientRateCardObj;
		}

		public ClientRateCard setClientRateCardObj(ClientRateCard clientRateCardObj) {
			this.clientRateCardObj = clientRateCardObj;
			return clientRateCardObj;
		}

		public String getRedirectUrl() {
			return redirectUrl;
		}

		public void setRedirectUrl(String redirectUrl) {
			this.redirectUrl = redirectUrl;
		}

		public List<ClientRateCard> getClientRateCardList() {
			return clientRateCardList;
		}

		public void setClientRateCardList(List<ClientRateCard> clientRateCardList) {
			this.clientRateCardList = clientRateCardList;
		}

		public List<ServiceType> getServiceTypeList() {
			return serviceTypeList;
		}

		public void setServiceTypeList(List<ServiceType> serviceTypeList) {
			this.serviceTypeList = serviceTypeList;
		}
		@Override // for RestrictAccessToUnauthorizedActionInterceptor
		public String toString(){
			return "ClientRateCardAction";
		}
	

}
