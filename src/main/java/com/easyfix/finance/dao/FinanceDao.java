package com.easyfix.finance.dao;

import java.util.List;

import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.finance.model.EasyfixerFinance;
import com.easyfix.finance.model.Finance;

public interface FinanceDao {

	List<Finance> getTransactionList(Finance financeObj) throws Exception;

	int addUpdateEasyfixerAmount(Finance financeObj) throws Exception;

	String getCurrentBalance(int easyfixerId) throws Exception;

	List<EasyfixerFinance> ndmRechargeList(int efrId, int ndmId, int flag) throws Exception;

	int addUpdateNdmRechargeAmount(EasyfixerFinance efrFinObj) throws Exception;

	
}
