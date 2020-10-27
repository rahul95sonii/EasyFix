package com.easyfix.home.action;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easyfix.Jobs.action.JobAction;
import com.easyfix.Jobs.delegate.JobService;
import com.easyfix.Jobs.model.JobSelectedServices;
import com.easyfix.Jobs.model.Jobs;
import com.easyfix.clients.delegate.ClientService;
import com.easyfix.clients.model.Clients;
import com.easyfix.customers.delegate.CustomerService;
import com.easyfix.customers.model.Customers;
import com.easyfix.home.delegate.HomeService;
import com.easyfix.settings.delegate.CityService;
import com.easyfix.settings.delegate.ServiceTypeService;
import com.easyfix.settings.model.City;
import com.easyfix.settings.model.ServiceType;
import com.easyfix.user.model.User;
import com.easyfix.util.CommonAbstractAction;
import com.easyfix.util.Constants;
import com.opensymphony.xwork2.ModelDriven;

public class HomeAction extends CommonAbstractAction implements ModelDriven<Jobs> {
	private static final Logger logger = LogManager.getLogger(HomeAction.class);
	private static final long serialVersionUID = 1L;
	private String actMenu;
	private String actParent;
	private String title;
	private User userObj;
	private Jobs jobObj;
	private Clients clientObj;
	
	private HomeService homeService;
	private JobService jobService;
	private CustomerService customerService;
	private ServiceTypeService serviceTypeService;
	private CityService cityService;
	private ClientService clientService;
	private List<Jobs> jobList;
	private List<City> cityList;
	private List<Customers> customerList;
	private List<ServiceType> serviceTypeList;
	private List<JobSelectedServices> jobServiceList;
	
	
	@Override
	public Jobs getModel() {
		return jobObj = new Jobs();
	}
	
	public String dashboard() throws Exception {
		
		String acttitle= "Easyfix : Dashboard";
		setActMenu("Home");
		setActParent("Home");
		setTitle(acttitle);
		try{
		 userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
		 jobObj = homeService.getUserJobsList(userObj.getUserId(),userObj.getRoleId());
		 
		 List<Jobs> l =(homeService.getUserJobsListFromExcel(userObj.getUserId(), userObj.getRoleId())).getJobLeadList();
	
			jobObj.setJobLeadList(l);
			/*System.out.println("scheduled::"+jobObj.getScheduleList().size());
			System.out.println("checkedIn::"+jobObj.getCheckInList().size());
			System.out.println("checkout::"+jobObj.getCheckOutList().size());
			System.out.println("feedback::"+jobObj.getFeedbackList().size());
			System.out.println("unowned::"+jobObj.getUnownedJobs().size());*/
			
		//	 if(l.size()==0)
		//		 logger.info(userObj.getUserName()+ "has 0 pending for confrimation jobs");
		}
		catch(Exception e){
			e.printStackTrace();
			logger.catching(e);
		}
		
		return SUCCESS;
	}
	
	public String userOwnerJob() {
		try {
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			jobObj = homeService.getUserJobsList(userObj.getUserId(),userObj.getRoleId());
			List<Jobs> l =(homeService.getUserJobsListFromExcel(userObj.getUserId(), userObj.getRoleId())).getJobLeadList();
			jobObj.setJobLeadList(l);
			// if(l.size()==0)
			//	 logger.info(userObj.getUserName()+ "has 0 pending for confrimation jobs");
			//System.out.println(l);
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		return SUCCESS;
	}
	
	public String androidAppJob() {
		try {
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
		//	System.out.println(userObj.getUserName()+":"+userObj.getUserId());
			jobObj = homeService.getAppjobList(53, 2);
			
			// if(l.size()==0)
			//	 logger.info(userObj.getUserName()+ "has 0 pending for confrimation jobs");
		//	System.out.println("reached here::"+jobObj);
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		return SUCCESS;
	}
	
	
	public String clientDashboard() throws Exception {
		try {
			String acttitle= "Easyfix : Client Dashboard";
			setActMenu("Home");
			setActParent("Home");
			setTitle(acttitle);
			
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			cityList = cityService.getCityList();
			serviceTypeList = serviceTypeService.getSerTypeList(1);	
/*			clientObj = clientService.getClientDetails(userObj.getLoginClient());
			requestObject.setAttribute("clientName", clientObj.getClientName());*/
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		
		
		return SUCCESS;
	}
	
	public String getClientJobListByFilter() throws Exception {
		
		try {
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			
			if(!jobObj.getStartDate().equalsIgnoreCase("")) {
				String startDate = jobService.convertStringToSimpleDate(jobObj.getStartDate());
				String endDate = jobService.convertStringToSimpleDate(jobObj.getEndDate());
				
				jobObj.setStartDate(startDate);
				jobObj.setEndDate(endDate);
			}
			System.out.println(userObj.getLoginClient());
			jobList = homeService.getClientJobsList(jobObj,userObj.getLoginClient());
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}	
							
		return SUCCESS;
	}	
	
	
	public String clientJobDetails() throws Exception {
		
	try {		
		jobObj = jobService.getJobDetails(jobObj.getJobId());
				
		jobObj.setCreatedDateTime(jobService.convertDateToString(jobObj.getCreatedDateTime()));
		jobObj.setRequestedDateTime(jobService.convertDateToString(jobObj.getRequestedDateTime()));
		jobObj.setScheduleDateTime(jobService.convertDateToString(jobObj.getScheduleDateTime()));
		jobObj.setCheckInDateTime(jobService.convertDateToString(jobObj.getCheckInDateTime()));
		jobServiceList = jobService.getJobServiceList(jobObj.getJobId(),1); // 1 for active service
		double total = 0;
		for (JobSelectedServices s : jobServiceList) {
			if(s.getJobChargeType() == 0)	
				
			total = total + Double.parseDouble(s.getTotalCharge());		
			
		}
				
				requestObject.setAttribute("totalSum", total);
							
			} catch (Exception e) {
				e.printStackTrace();
				logger.catching(e);
			}
				
			return SUCCESS;
		}
	
	
	public void deactivateLeadJob(){
		userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
		try {
			homeService.deactiveLeadJob(jobObj.getJobId(),jobObj.getComments(), userObj.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public User getUserObj() {
		return userObj;
	}


	public void setUserObj(User userObj) {
		this.userObj = userObj;
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
    	return "HomeAction";
    }

	public Jobs getJobObj() {
		return jobObj;
	}

	public void setJobObj(Jobs jobObj) {
		this.jobObj = jobObj;
	}

	public List<Jobs> getJobList() {
		return jobList;
	}

	public void setJobList(List<Jobs> jobList) {
		this.jobList = jobList;
	}

	public HomeService getHomeService() {
		return homeService;
	}

	public void setHomeService(HomeService homeService) {
		this.homeService = homeService;
	}

	public JobService getJobService() {
		return jobService;
	}

	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}

	public List<City> getCityList() {
		return cityList;
	}

	public List<Customers> getCustomerList() {
		return customerList;
	}

	public List<ServiceType> getServiceTypeList() {
		return serviceTypeList;
	}

	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}

	public void setCustomerList(List<Customers> customerList) {
		this.customerList = customerList;
	}

	public void setServiceTypeList(List<ServiceType> serviceTypeList) {
		this.serviceTypeList = serviceTypeList;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public ServiceTypeService getServiceTypeService() {
		return serviceTypeService;
	}

	public CityService getCityService() {
		return cityService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public void setServiceTypeService(ServiceTypeService serviceTypeService) {
		this.serviceTypeService = serviceTypeService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public List<JobSelectedServices> getJobServiceList() {
		return jobServiceList;
	}

	public void setJobServiceList(List<JobSelectedServices> jobServiceList) {
		this.jobServiceList = jobServiceList;
	}

	public Clients getClientObj() {
		return clientObj;
	}

	public ClientService getClientService() {
		return clientService;
	}

	public void setClientObj(Clients clientObj) {
		this.clientObj = clientObj;
	}

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}



	

}
