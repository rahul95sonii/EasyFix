package com.easyfix.clients.action;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.opensymphony.xwork2.ModelDriven;
import com.easyfix.Jobs.action.JobAction;
import com.easyfix.clients.delegate.*;
import com.easyfix.clients.model.Clients;
import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.settings.delegate.CityService;
import com.easyfix.settings.delegate.ServiceTypeService;
import com.easyfix.settings.model.City;
import com.easyfix.settings.model.ServiceType;
import com.easyfix.user.model.User;
import com.easyfix.util.CommonAbstractAction;
import com.easyfix.util.Constants;
import com.easyfix.util.UtilityFunctions;



public class ClientAction extends CommonAbstractAction implements ModelDriven<Clients>{
	private static final Logger logger = LogManager.getLogger(ClientAction.class);
	private static final long serialVersionUID = 1L;
	
	private String actMenu;
	private String actParent;
	private String title;
	private Clients clientObj;
	private User userObj;
	
	private List<Clients> clientList;
	private List<City> cityList;
	
	private CityService cityService;
	private ClientService clientService;
	private ServiceTypeService serviceTypeService;
	private List<ServiceType> serviceTypeList;
	private List<Easyfixers> easyfixerList;
	private List<Clients> easyfixerListforClient;
	private FileInputStream fileInputStream;
	private String fileName; 
	
	@Override
	public Clients getModel() {
		return setClientObj(new Clients());
	}
	

	public String manageClient() throws Exception {
		
		try {
			String acttitle= "Easyfix : Manage Client";
			setActMenu("Manage Clients");
			setActParent("Clients");
			setTitle(acttitle);			
			
			clientList = clientService.getClientList(2); // 2 for all client
			
				
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		
		
		return SUCCESS;
	}
	
public String addEditClient() throws Exception {
	try {
		
		cityList = cityService.getCityList();
		if(clientObj.getClientId()!=0){
			
			clientObj = clientService.getClientDetails(clientObj.getClientId());
			
		}
	}
	catch(Exception e){
		e.printStackTrace();
		logger.catching(e);
		
	}
			
		
		return SUCCESS;
	}
	
public String addUpdateClient() throws Exception {
	try {
		userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
		clientObj.setUpdatedBy(userObj);
		
		if(clientObj.getInvoiceRaise() == 1) {
			String invStartDt = UtilityFunctions.changeDateFormatToFormat(clientObj.getInvoiceStartDate(), "dd-MM-yyyy", "yyyy-MM-dd");
			clientObj.setInvoiceStartDate(invStartDt);
		}
		else
		{
			clientObj.setInvoiceCycle("");
			clientObj.setInvoiceName("");
			clientObj.setInvoiceStartDate("");
		}
		
		
		clientService.addUpdateClient(clientObj);
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	}
		
	
	return SUCCESS;
}

public String manageClientEasyfixerMapping() throws Exception {
	
	try {

		String acttitle= "Easyfix : Manage Client";
		setActMenu("Manage Clients");
		setActParent("Clients");
		setTitle(acttitle);
		
		clientObj = clientService.getClientDetails(clientObj.getClientId());
		easyfixerListforClient = clientService.getEasyfixerListForClient(clientObj.getClientId(),2); //2 for all mapped easyfixer
		
			
	} catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	}
	
	
	return SUCCESS;
}

public String newEasyfixerMapping() throws Exception {
	
	try {
			clientObj = clientService.getClientDetails(clientObj.getClientId());
			serviceTypeList = serviceTypeService.getSerTypeList(1);
		
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	}
	return SUCCESS;
}


public String getEasyfixerListForMap() throws Exception {
	
	try {
		
		easyfixerList = clientService.getEasyfixerForMapping(clientObj.getClientId(), clientObj.getServiceTypeId());
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	}
	return SUCCESS;
}

public String saveMappedEasyfixer() throws Exception {
	
	try {
		
		String c = clientObj.getEasyfixerIds();
		
		List<String> list = new ArrayList<String>(Arrays.asList(c.split(",")));
		
		int status = clientService.saveMappedEasyfixer(clientObj.getClientId(), clientObj.getServiceTypeId(),list);
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	}
	return SUCCESS;
}

public String addUpdateMappingStatus() throws Exception {
	try {
		
		String c = requestObject.getParameter("serviceTypeIds");
		String flag = requestObject.getParameter("flag");
		List<String> list = new ArrayList<String>(Arrays.asList(c.split(",")));
		
		int status = clientService.updateMappedEasyfixer(clientObj.getClientId(), clientObj.getEasyfixerId(), flag, list);		
		
		requestObject.setAttribute("status", status);
		
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	}
	return SUCCESS;
	
}
public String downloadClientRateCard() throws Exception{
	fileName = "clientRateCard.xlsx";
	
	try{			
		int client = clientObj.getClientId();
		//System.out.println(client);
	fileInputStream= clientService.downloadClientRateCard(client);
	}
	catch(Exception e){
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

	public List<Clients> getClientList() {
		return clientList;
	}
	public void setClientList(List<Clients> clientList) {
		this.clientList = clientList;
	}
	public Clients getClientObj() {
		return clientObj;
	}


	public Clients setClientObj(Clients clientObj) {
		this.clientObj = clientObj;
		return clientObj;
	}

	public ClientService getClientService() {
		return clientService;
	}

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}


	public CityService getCityService() {
		return cityService;
	}


	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}


	public List<City> getCityList() {
		return cityList;
	}


	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}


	public List<Clients> getEasyfixerListforClient() {
		return easyfixerListforClient;
	}


	public void setEasyfixerListforClient(List<Clients> easyfixerListforClient) {
		this.easyfixerListforClient = easyfixerListforClient;
	}


	public List<ServiceType> getServiceTypeList() {
		return serviceTypeList;
	}


	public void setServiceTypeList(List<ServiceType> serviceTypeList) {
		this.serviceTypeList = serviceTypeList;
	}


	public ServiceTypeService getServiceTypeService() {
		return serviceTypeService;
	}


	public void setServiceTypeService(ServiceTypeService serviceTypeService) {
		this.serviceTypeService = serviceTypeService;
	}


	public List<Easyfixers> getEasyfixerList() {
		return easyfixerList;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public void setEasyfixerList(List<Easyfixers> easyfixerList) {
		this.easyfixerList = easyfixerList;
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
	@Override // for RestrictAccessToUnauthorizedActionInterceptor
    public String toString(){
    	return "ClientAction";
    }


	public FileInputStream getFileInputStream() {
		return fileInputStream;
	}


	public void setFileInputStream(FileInputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}


	public User getUserObj() {
		return userObj;
	}


	public void setUserObj(User userObj) {
		this.userObj = userObj;
	}
}
