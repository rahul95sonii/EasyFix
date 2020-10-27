package com.easyfix.settings.dao;

import java.util.List;

import com.easyfix.settings.model.City;
import com.easyfix.settings.model.ZoneCityMapping;

public interface CityDao {
	
	public List<City> getCityList() throws Exception;	
	public int addUpdateCity(City userObj) throws Exception;
	public void mapZoneCity(City userObj) throws Exception;
	public City getCityDetailsById(int cityId) throws Exception;
	List<ZoneCityMapping> getZoneCityListByCity(City city) throws Exception;
	}
	