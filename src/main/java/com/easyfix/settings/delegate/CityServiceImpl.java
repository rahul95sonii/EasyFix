package com.easyfix.settings.delegate;

import java.util.List;

import com.easyfix.settings.dao.CityDao;
import com.easyfix.settings.model.City;
import com.easyfix.settings.model.ZoneCityMapping;

public class CityServiceImpl implements CityService{
	
	private CityDao cityDao;
	
	public CityDao getCityDao() {
		return cityDao;
	}

	public void setCityDao(CityDao cityDao) {
		this.cityDao = cityDao;
	}

	@Override
	public List<City> getCityList() throws Exception {
		// TODO Auto-generated method stub
		return cityDao.getCityList();
	}

	@Override
	public int addUpdateCity(City cityObj) throws Exception {
		// TODO Auto-generated method stub
		return cityDao.addUpdateCity(cityObj);
	}
	
	@Override
	public void mapZoneCity(City cityObj) throws Exception {
		// TODO Auto-generated method stub
		cityDao.mapZoneCity(cityObj);
	}

	@Override
	public City getCityDetailsById(int cityId) throws Exception {
		// TODO Auto-generated method stub
		return cityDao.getCityDetailsById(cityId);
	}

	@Override
	public List<ZoneCityMapping> getZoneCityListByCity(City city)
			throws Exception {
		return cityDao.getZoneCityListByCity(city);
	}

	
	
	
}
