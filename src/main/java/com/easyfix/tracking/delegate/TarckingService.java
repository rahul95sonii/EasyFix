package com.easyfix.tracking.delegate;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import com.easyfix.Jobs.model.Jobs;
import com.easyfix.clients.model.Clients;
import com.easyfix.customers.model.Address;
import com.easyfix.customers.model.Customers;
import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.settings.model.City;
import com.easyfix.settings.model.ServiceType;
import com.easyfix.tracking.model.Tracking;
import com.easyfix.user.model.User;

public interface TarckingService {
	
	public List<Tracking> userLoggingHours(String flag, String startDate, String endDate) throws Exception;
	
	public FileInputStream downloadUserLoggingReport(List<Tracking> userLoggingList, String fDate, String tDate, String fileName) throws Exception;

	/*public List<City> cityList() throws Exception;

	public List<Clients> clientList() throws Exception;
	
	public List<ServiceType> serviceTypeList() throws Exception;
	
	public List<Customers> customerList() throws Exception;
	
	public List<Easyfixers> easyfixerList() throws Exception;
	
	public List<Address> addressList() throws Exception;*/

	public List<Jobs> jobListForTracking(Jobs jobObj) throws Exception;

	public List<Tracking> iteratingJobListByCity(Map<String, List<Jobs>> cityJobMap) throws Exception;

	public Map<String, List<Jobs>> filterJobListByCity(List<Jobs> jobList) throws Exception;

	public Map<String, List<Jobs>> filterJobListByClient(List<Jobs> jobList) throws Exception;

	public List<Tracking> iteratingJobListByClient(Map<String, List<Jobs>> clientJobMap) throws Exception;

	public List<Tracking> jobTrackingByUser(List<Jobs> jobList, Jobs jobObj);

	public FileInputStream downloadTrackingReport(List<Tracking> downloadList, String fileName, String flag);

	//public List<Tracking> iteratingJobListByCity(Map<String, List<Jobs>> cityJobMap) throws Exception;

}
