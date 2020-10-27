package com.easyfix.util;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;

import com.easyfix.clients.model.Clients;
import com.easyfix.customers.model.Address;
import com.easyfix.customers.model.Customers;
import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.settings.model.City;
import com.easyfix.settings.model.ServiceType;
import com.easyfix.util.RestClient;

public class CommonMasterData {

/*  City List from API */
public List<City> cityList() throws Exception {
	List<City> cityList = new ArrayList<City>();
	try {
		
		WebTarget target = new RestClient().apiResponse();
		String jsonResult = target.path("cities").request().accept(MediaType.APPLICATION_JSON).get(String.class);
		//String jsonResult = new RestClient().apiResponse("cities");
		
		JSONArray jArray = new JSONArray(jsonResult);
		ObjectMapper mapper = new ObjectMapper();
		
		for(int i=0; i<jArray.length();i++) {
			
			City cityObj = mapper.readValue(jArray.getString(i), City.class);
			cityList.add(cityObj);
				
			//System.out.println(jArray.getString(i));
		}
		
			
	} catch (Exception e) {
		e.printStackTrace();
		
	}
	
	return cityList;
}


/* Client List from API */

public List<Clients> clientList() throws Exception {
	List<Clients> clientList = new ArrayList<Clients>();
	try {
		
		WebTarget target = new RestClient().apiResponse();
		String jsonResult = target.path("clients").request().accept(MediaType.APPLICATION_JSON).get(String.class);
		//String jsonResult = new RestClient().apiResponse("clients");
		
		JSONArray jArray = new JSONArray(jsonResult);
		ObjectMapper mapper = new ObjectMapper();
		
		for(int i=0; i<jArray.length();i++) {
			
			Clients clientObj = mapper.readValue(jArray.getString(i), Clients.class);
			clientList.add(clientObj);
				
			//System.out.println(jArray.getString(i));
		}
		
			
	} catch (Exception e) {
		e.printStackTrace();
		
	}
	
	return clientList;
}

/* Service Type List from API */

public List<ServiceType> serviceTypeList() throws Exception {
	List<ServiceType> serviceTypeList = new ArrayList<ServiceType>();
	try {
		
		WebTarget target = new RestClient().apiResponse();
		String jsonResult = target.path("serviceType").request().accept(MediaType.APPLICATION_JSON).get(String.class);
		//String jsonResult = new RestClient().apiResponse("serviceType");
		
		JSONArray jArray = new JSONArray(jsonResult);
		ObjectMapper mapper = new ObjectMapper();
		
		for(int i=0; i<jArray.length();i++) {
			
			ServiceType serviceTypeObj = mapper.readValue(jArray.getString(i), ServiceType.class);
			serviceTypeList.add(serviceTypeObj);
				
			//System.out.println(jArray.getString(i));
		}
		System.out.println("Tracking Service List Size " + serviceTypeList.size());
			
	} catch (Exception e) {
		e.printStackTrace();
		
	}
	
	return serviceTypeList;
}

/* Customer List from API */

public List<Customers> customerList() throws Exception {
	List<Customers> customerList = new ArrayList<Customers>();
	try {
		
		WebTarget target = new RestClient().apiResponse();
		String jsonResult = target.path("customer").request().accept(MediaType.APPLICATION_JSON).get(String.class);
		//String jsonResult = new RestClient().apiResponse("customer");
		
		JSONArray jArray = new JSONArray(jsonResult);
		ObjectMapper mapper = new ObjectMapper();
		
		for(int i=0; i<jArray.length();i++) {
			
			Customers customerObj = mapper.readValue(jArray.getString(i), Customers.class);
			customerList.add(customerObj);
				
			//System.out.println(jArray.getString(i));
		}
		//System.out.println("Tracking Service List Size " + customerList.size());
			
	} catch (Exception e) {
		e.printStackTrace();
		
	}
	
	return customerList;
}


/* Easyfixer List from API */


public List<Easyfixers> easyfixerList() throws Exception {
	List<Easyfixers> easyfixerList = new ArrayList<Easyfixers>();
	try {
		
		WebTarget target = new RestClient().apiResponse();
		String jsonResult = target.path("easyfixers").request().accept(MediaType.APPLICATION_JSON).get(String.class);
		//String jsonResult = new RestClient().apiResponse("easyfixers");
		
		JSONArray jArray = new JSONArray(jsonResult);
		ObjectMapper mapper = new ObjectMapper();
		
		for(int i=0; i<jArray.length();i++) {
			
			Easyfixers easyfixerObj = mapper.readValue(jArray.getString(i), Easyfixers.class);
			easyfixerList.add(easyfixerObj);				
			
		}
		//System.out.println("Tracking Service List Size " + easyfixerList.size());
			
	} catch (Exception e) {
		e.printStackTrace();
		
	}
	
	return easyfixerList;
}

/* Address List from API */

public List<Address> addressList() throws Exception {
	List<Address> addressList = new ArrayList<Address>();
	try {
		
		WebTarget target = new RestClient().apiResponse();
		String jsonResult = target.path("address").request().accept(MediaType.APPLICATION_JSON).get(String.class);
		//String jsonResult = new RestClient().apiResponse("address");
		
		JSONArray jArray = new JSONArray(jsonResult);
		ObjectMapper mapper = new ObjectMapper();
		
		for(int i=0; i<jArray.length();i++) {
			
			Address addressObj = mapper.readValue(jArray.getString(i), Address.class);
			addressList.add(addressObj);
			
		}
		//System.out.println("Tracking Service List Size " + addressList.size());
			
	} catch (Exception e) {
		e.printStackTrace();
		
	}
	
	return addressList;
}


}
