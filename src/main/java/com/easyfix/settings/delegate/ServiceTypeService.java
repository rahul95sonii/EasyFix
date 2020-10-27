package com.easyfix.settings.delegate;

import java.util.List;

import com.easyfix.settings.model.ServiceType;

public interface ServiceTypeService {
	
	public List<ServiceType> getSerTypeList(int flag) throws Exception;
	public int addUpdateSerType(ServiceType serviceTypeObj) throws Exception;
	public ServiceType getSerTypeDetailsById(int serviceTypeId) throws Exception;
	public List<ServiceType> getSerTypeListByClientId(int fkClientId, int flag) throws Exception;

}
