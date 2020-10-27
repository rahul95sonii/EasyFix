package com.easyfix.settings.delegate;

import java.util.List;

import com.easyfix.settings.dao.ServiceTypeDao;
import com.easyfix.settings.model.ServiceType;


public class ServiceTypeServiceImpl implements ServiceTypeService {
	
	private ServiceTypeDao serviceTypeDao;

	public ServiceTypeDao getServiceTypeDao() {
		return serviceTypeDao;
	}

	public void setServiceTypeDao(ServiceTypeDao serviceTypeDao) {
		this.serviceTypeDao = serviceTypeDao;
	}

	@Override
	public List<ServiceType> getSerTypeList(int flag) throws Exception {
		return serviceTypeDao.getSerTypeList(flag);
	}

	@Override
	public int addUpdateSerType(ServiceType serviceTypeObj) throws Exception {
		return serviceTypeDao.addUpdateSerType(serviceTypeObj);
	}

	@Override
	public ServiceType getSerTypeDetailsById(int serviceTypeId)
			throws Exception {
		return serviceTypeDao.getSerTypeDetailsById(serviceTypeId);
	}

	@Override
	public List<ServiceType> getSerTypeListByClientId(int fkClientId, int flag) throws Exception {
		return serviceTypeDao.getSerTypeListByClientId(fkClientId,flag);
	}
	
}
