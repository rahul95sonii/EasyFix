package com.easyfix.easyfixers.delegate;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import com.easyfix.clients.model.Clients;
import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.easyfixers.model.ServicePayout;
import com.easyfix.finance.model.EasyfixerTransaction;
import com.easyfix.settings.model.DocumentType;

public interface EasyfixerService {
	
	public List<Easyfixers> getEasyfixerList(int flag) throws Exception;	
	public int addUpdateEasyFixer(Easyfixers easyfixerObj) throws Exception;
	public Easyfixers getEasyfixerDetailsById(int easyfixerId) throws Exception;
	public List<DocumentType> getEasyfixerDocument(int easyfixerId) throws Exception;
	public List<Clients> getClientListForEasyfixer(int easyfixerId, int mappedStatus) throws Exception;
	public List<Easyfixers> easyfixerBalance(Easyfixers easyfixerObj) throws Exception;	
	public List<Easyfixers> getEnumReasonList(int enumType) throws Exception;
	public List<EasyfixerTransaction> efrTransactionList(String efrId, String startDate, String endDate) throws Exception;
	public void updateRechargeAmount(EasyfixerTransaction efrTransObj) throws Exception;
	public List<Easyfixers> getEasyfixerListForPayout(int cityId) throws Exception;
	public int saveServicePayOut(ServicePayout payoutObj) throws Exception;
	public int approvePayOutByFinance(int payoutId, int efrId, int userId, float finAprvAmnt, int status) throws Exception;
	public FileInputStream downloadEfrPayoutSheet(String start,String end, int flag) throws Exception;
	public Map<String,Easyfixers> getEfrAppActions(int jobId, int efrId) throws Exception;
	public Easyfixers getEfrMetaData(int efrId) throws Exception;
}
