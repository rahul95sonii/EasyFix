package com.easyfix.customers.action;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.util.Log;

import com.easyfix.customers.delegate.CustomerService;
import com.easyfix.customers.model.Address;
import com.easyfix.customers.model.Customers;
import com.easyfix.settings.delegate.CityService;
import com.easyfix.settings.model.City;
import com.easyfix.user.model.User;
import com.easyfix.util.CommonAbstractAction;
import com.easyfix.util.Constants;
import com.easyfix.util.SmsSender;
import com.opensymphony.xwork2.ModelDriven;

public class CustomerAction extends CommonAbstractAction implements ModelDriven<Customers>{
	private static final Logger logger = LogManager.getLogger(CustomerAction.class);
	
	private static final long serialVersionUID = 1L;

	private CustomerService customerService;

	private Customers customerObj;
	private String actMenu;
	private String actParent;
	private String title;
	private String redirectUrl;
	private CityService cityService;
	private User userObj;
	private List<City> cityList;
	
	private List<Customers> customerList;
	private List<Address> addressList;
	
	@Override
	public Customers getModel() {
			return setCustomerObj(new Customers());
	}
	
	public String customer() throws Exception {
		
		try {
			
			String acttitle= "Easyfix : Manage Customers";
			setActMenu("Manage Customers");
			setActParent("Customers");
			setTitle(acttitle);
			
			customerList = customerService.getCustomerList();

			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		
		return SUCCESS;
	}


	
	public String addEditCustomer() throws Exception {
		
		try {
				cityList = cityService.getCityList();
			
				if(customerObj.getCustomerId() != 0) {
					customerObj = customerService.getCustomerDetailsById(customerObj.getCustomerId());
					
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		
		
		return SUCCESS;
	}
	
	public String addUpdateCustomer() throws Exception {
		try {
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			int status = customerService.addUpdateCustomer(customerObj, userObj.getUserId());
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		 	
		return SUCCESS;
		
	}
	
public String addEditCustAddress() throws Exception {
		
		try {
				cityList = cityService.getCityList();
				int addressId = customerObj.getAddressObj().getAddressId();
				if(addressId != 0)
					customerObj = customerService.getCustomerAddressDetailsById(addressId);			
				
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		
		
		return SUCCESS;
	}

public String addUpdateCustomerAddress() throws Exception {
	String flag = "";
	try {
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			flag = requestObject.getParameter("flag");
			int status = customerService.addUpdateCustomerAddress(customerObj, userObj.getUserId());			
			addressList = customerService.getAddressList(customerObj.getCustomerId());
			
			requestObject.setAttribute("flag", flag);
			logger.info("updating address for cusId:"+customerObj.getCustomerId()+" to add:"+customerObj.getAddressObj().getAddressId());
	} catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	}
	
	
	return SUCCESS;
}


public String getCustomerDetails() throws Exception {
	try {
		addressList = null;
		cityList = cityService.getCityList();
		customerObj = customerService.getCustomerDetailsByMobileNo(customerObj.getCustomerMobNo());
		if(customerObj != null) {
			addressList = customerService.getAddressList(customerObj.getCustomerId());
		}
		
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	}
	return SUCCESS;
}

public String sendBulkSMSToNosIntbl_bulk_sms_nosTable(){
	try {
		String msg = "Are your diwali preparation on? Don't wait for last moment haggles and book your electrician, carpenter, plumber, now on 8882122666 at your day and time of convenience, avail diwali offer and get free visit for any service.";
		List<Customers> list = customerService.getnoListForBulkSMS();
		System.out.println("list size="+list.size());
		for(Customers c: list){
			System.out.println("sms will be sent to :"+c.getCustomerMobNo());
			SmsSender.sendSms(msg, c.getCustomerMobNo());
			//Thread.sleep(5);
		}
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return SUCCESS;
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
	
	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}
	

	public List<Customers> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customers> customerList) {
		this.customerList = customerList;
	}

	public Customers getCustomerObj() {
		return customerObj;
	}

	public Customers setCustomerObj(Customers customerObj) {
		this.customerObj = customerObj;
		return customerObj;
	}

	

	public List<City> getCityList() {
		return cityList;
	}

	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}


	public List<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
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

	public User getUserObj() {
		return userObj;
	}

	public void setUserObj(User userObj) {
		this.userObj = userObj;
	}
	@Override //// for RestrictAccessToUnauthorizedActionInterceptor
    public String toString(){
    	return "CustomerAction";
    }
	



}
