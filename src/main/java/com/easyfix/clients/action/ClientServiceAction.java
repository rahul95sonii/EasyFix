package com.easyfix.clients.action;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easyfix.clients.delegate.ClientService;
import com.easyfix.clients.model.Clients;
import com.easyfix.settings.delegate.CityService;
import com.easyfix.settings.delegate.ClientRateCardService;
import com.easyfix.settings.delegate.ServiceTypeService;
import com.easyfix.settings.model.City;
import com.easyfix.settings.model.ClientRateCard;
import com.easyfix.settings.model.ServiceType;
import com.easyfix.user.model.User;
import com.easyfix.util.CommonAbstractAction;
import com.easyfix.util.Constants;
import com.opensymphony.xwork2.ModelDriven;

public class ClientServiceAction extends CommonAbstractAction implements ModelDriven<Clients> {
	private static final Logger logger = LogManager.getLogger(ClientServiceAction.class);
	private static final long serialVersionUID = 1L;
	
	private String actMenu;
	private String actParent;
	private String title;
	private ClientService clientService;
	private List<City> cityList;
	private CityService cityService;
	private ClientRateCardService clientRateCardService;
	private ServiceTypeService serviceTypeService;
	private String msg;

	private List<Clients> clientList;
	private List<ServiceType> serviceTypeList;
	private List<Clients> serviceList;
	private List<ClientRateCard> rateCardList;
	private Clients clientObj;
	private Clients serviceObj;
	private String uploadPathForJobAndRc = "/var/www/html/easydoc/client_rccard/";
	
	@Override
	public Clients getModel() {
		return clientObj = new Clients();
	}
	
	
	public String manageService() throws Exception {
		
		try {
			
			String acttitle= "Easyfix : Manage Client Service";
			setActMenu("Manage Clients");
			setActParent("Clients");
			setTitle(acttitle);
			
			clientObj = clientService.getClientDetails(clientObj.getClientId());
			serviceList = clientService.getServiceList(clientObj.getClientId());
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}		
		
		return SUCCESS;
	}
	
	public String addEditService() throws Exception {
		try {
				serviceTypeList = serviceTypeService.getSerTypeList(1);
				
				if(clientObj.getClientServiceId()>0)
				{
					serviceObj = clientService.getClientServicesDetails(clientObj.getClientServiceId());
				}
				requestObject.setAttribute("clientId", clientObj.getClientId());
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		return SUCCESS;
	}
	
	public String addUpdateClientServices() throws Exception {
		try {
			
			User  user  = ((User) requestObject.getSession().getAttribute(Constants.USER_OBJECT));
			clientObj.setUpdatedBy(user);
			clientService.addUpdateClientServices(clientObj);
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		
		
		return SUCCESS;
	}
	public String uploadRcExcelFile() throws Exception{
		String acttitle= "Easyfix : Upload ";
		setActMenu("Manage Clients");
		setActParent("Clients");
		setTitle(acttitle);
		
		return SUCCESS;
	}
	public String addUpdateClientServicesFromExcel() throws Exception {
		try {
			//String excelFilePath = "D:/rc.xlsx";
			//System.out.println(jobsObj.getJobUploadByExcelFile().getName());
			String[] temp =clientObj.getRcUploadByExcelFile().getName().split("\\.");
			String sysGeneratedFileName = "ExcelRc" + new Date().getTime() + "." + temp[1];
			String fullFileName = uploadPathForJobAndRc+sysGeneratedFileName;
			//System.out.println(fullFileName);
			clientObj.setRcUploadByExcelFileName(fullFileName);			
			File theFile = new File(fullFileName);
			
			FileUtils.copyFile(clientObj.getRcUploadByExcelFile(), theFile);
			//jobsObj.setJobUploadByExcelFileName(sysGeneratedFileName);
			
			//String excelFilePath = "D:/job.xlsx";
		    User uploadedBy = ((User) requestObject.getSession().getAttribute(Constants.USER_OBJECT));
		//	System.out.println("in action"+clientObj.getClientId());
			List<Clients> rcList =clientService.readClientRateCardFromExcelFile(fullFileName);
		//	System.out.println(rcList);
			
			int uploadedratecard=0;
			for(Clients service:rcList) {
				service.setUpdatedBy(uploadedBy);
				clientService.addUpdateClientServicesFromExcel(service);
				uploadedratecard++;
			}
			msg = "Total Rate Card in Excel: "+rcList.size()+" Ratecards Uploaded: "+uploadedratecard;
			
			//requestObject.setAttribute("msg",m);
			 
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		
		
		return SUCCESS;
	}
	
	
	
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ClientService getClientService() {
		return clientService;
	}


	public List<Clients> getClientList() {
		return clientList;
	}


	public Clients getClientObj() {
		return clientObj;
	}


	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}


	public void setClientList(List<Clients> clientList) {
		this.clientList = clientList;
	}
	
	public void setClientObj(Clients clientObj) {
		this.clientObj = clientObj;
	}


	public List<City> getCityList() {
		return cityList;
	}


	public CityService getCityService() {
		return cityService;
	}


	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}


	public void setCityService(CityService cityService) {
		this.cityService = cityService;
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


	public List<ServiceType> getServiceTypeList() {
		return serviceTypeList;
	}


	public void setServiceTypeList(List<ServiceType> serviceTypeList) {
		this.serviceTypeList = serviceTypeList;
	}


	public List<Clients> getServiceList() {
		return serviceList;
	}


	public void setServiceList(List<Clients> serviceList) {
		this.serviceList = serviceList;
	}


	public List<ClientRateCard> getRateCardList() {
		return rateCardList;
	}


	public void setRateCardList(List<ClientRateCard> rateCardList) {
		this.rateCardList = rateCardList;
	}


	public Clients getServiceObj() {
		return serviceObj;
	}


	public void setServiceObj(Clients serviceObj) {
		this.serviceObj = serviceObj;
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
	@Override //// for RestrictAccessToUnauthorizedActionInterceptor
	 public String toString(){
	    	return "ClientAction";
	    }


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}

}
