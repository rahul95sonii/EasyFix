package com.easyfix.finance.delegate;

import java.util.List;

import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.finance.model.EasyfixerFinance;
import com.easyfix.finance.model.Finance;

public interface FinanceService {

	public List<Finance> getTransactionList(Finance financeObj) throws Exception;

	public int addUpdateEasyfixerAmount(Finance financeObj) throws Exception;
	
	public String convertDateToString(String dateString) throws Exception;

	public String getCurrentBalance(int easyfixerId) throws Exception;

	public List<Easyfixers> ndmEasyfixerList(EasyfixerFinance efrFinObj) throws Exception;

	public List<EasyfixerFinance> ndmRechargeList(int efrId, int ndmId, int flag) throws Exception;
	
	public List<EasyfixerFinance> submitToFinanceList(int is_submitted) throws Exception;

	public int addUpdateNdmRechargeAmount(EasyfixerFinance efrFinObj) throws Exception;



	
}
