package com.easyfix.clients.dao;

import java.util.List;

import com.easyfix.clients.model.ClientWebsite;
import com.easyfix.clients.model.Clients;
import com.easyfix.easyfixers.model.Easyfixers;


public interface ClientDao {
	
	Clients getClientDetails(int clientId) throws Exception;
	public int addUpdateClient(Clients client) throws Exception;
	public List<Clients> getClientList(int flag) throws Exception;
	public List<Clients> getServiceList(int clientId) throws Exception;
	public int addUpdateClientServices(Clients client) throws Exception;
	public int addUpdateClientServicesFromExcel(Clients client) throws Exception;
	Clients getClientServicesDetails(int clientServiceId) throws Exception;
	List<Clients> getClientServiceListByClientIdAndServiceTypeId(int fkClientId, int fkServiceTypeId) throws Exception;
	public int addDeleteClientEasyfixerMapping(Clients client) throws Exception;
	
	public List<Clients> getEasyfixerListForClient(int clientId, int mappingStatus) throws Exception;
	List<Easyfixers> getEasyfixerForMapping(int clientId, int serviceTypeId) throws Exception;
	public int saveMappedEasyfixer(int clientId, int serviceTypeId, List<String> list) throws Exception;
	public int updateMappedEasyfixer(int clientId, int efrId, String flag, List<String> list) throws Exception;
	public ClientWebsite getClientWebSiteDetails(int clientId) throws Exception;
}
