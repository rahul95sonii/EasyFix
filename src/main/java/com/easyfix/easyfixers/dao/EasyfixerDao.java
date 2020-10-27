package com.easyfix.easyfixers.dao;

import java.util.List;
import java.util.Map;

import com.easyfix.clients.model.Clients;
import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.easyfixers.model.ServicePayout;
import com.easyfix.settings.model.DocumentType;

public interface EasyfixerDao {
	
	public List<Easyfixers> getEasyfixerList(int flag) throws Exception;	
	public int addUpdateEasyFixer(Easyfixers easyfixerObj) throws Exception;
	public Easyfixers getEasyfixerDetailsById(int easyfixerId) throws Exception;
	public List<DocumentType> getEasyfixerDocument(int easyfixerId) throws Exception;
	public List<Clients> getClientListForEasyfixer(int easyfixerId, int mappedStatus) throws Exception;
	public List<Easyfixers> getEnumReasonList(int enumType) throws Exception;
	public int saveServicePayOut(ServicePayout payoutObj) throws Exception;
	public List<Easyfixers> easyfixerListForPayOut(int cityId) throws Exception;
	public int approvePayOutByFinance(int payoutId, int efrId, int userId, float finAprvAmnt, int status) throws Exception;
	public List<ServicePayout> downloadEfrPayoutSheet(String start, String end, int flag );
	public List<Easyfixers> getEfrAppActions(int jobId, int efrId);
	public Easyfixers getEfrMetaData(int efrId) throws Exception;
}
