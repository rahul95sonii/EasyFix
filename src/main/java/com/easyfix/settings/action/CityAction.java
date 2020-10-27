package com.easyfix.settings.action;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easyfix.settings.delegate.CityService;
import com.easyfix.settings.model.City;
import com.easyfix.settings.model.ZoneCityMapping;
import com.easyfix.util.CommonAbstractAction;
import com.opensymphony.xwork2.ModelDriven;

public class CityAction extends CommonAbstractAction implements ModelDriven<City>{
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(CityAction.class);
	private CityService cityService;
	
	private City cityObj;
	private String actMenu;
	private String actParent;
	private String title;
	private String redirectUrl;
	
	private List<City> cityList;
	
	@Override
	public City getModel() {
		return setCityObj(new City());
	}
	
	public String city() throws Exception {
		
		try {
			
			String acttitle= "Easyfix : Manage City";
			setActMenu("Manage Cities");
			setActParent("Settings");
			setTitle(acttitle);
			
			cityList = cityService.getCityList();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.catching(e);
		}
		
		return SUCCESS;
	}
	
	public String addEditCity() throws Exception {
		
		try {
			
				if(cityObj.getCityId() != 0) {
					cityObj = cityService.getCityDetailsById(cityObj.getCityId());
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		
		
		return SUCCESS;
	}
	
	public String addUpdateCity() throws Exception {		
		
		cityService.getZoneCityListByCity(cityObj);
		
		return SUCCESS;
		
	}
	
	
	
	public String addCity() throws Exception {		
			
		cityService.addUpdateCity(cityObj);
		
		cityService.mapZoneCity(cityObj);
			
			return SUCCESS;
			
		}
	
	
	
public String getZoneListByCity() throws Exception {		
	try{
		System.out.println(cityObj.getCityId());
	 List<ZoneCityMapping> zoneList=	cityService.getZoneCityListByCity(cityObj);
	 cityObj.setZones(zoneList);
	
	}
	catch(Exception e){
		e.printStackTrace();
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

	public List<City> getCityList() {
		return cityList;
	}

	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}

	public City getCityObj() {
		return cityObj;
	}

	public City setCityObj(City cityObj) {
		this.cityObj = cityObj;
		return cityObj;
	}

	public CityService getCityService() {
		return cityService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}
	
	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	@Override // for RestrictAccessToUnauthorizedActionInterceptor
	public String toString(){
		return "CityAction";
	}

}
