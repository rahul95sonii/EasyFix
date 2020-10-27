package com.easyfix.settings.dao;

import java.util.List;

import com.easyfix.settings.model.RetailRateCard;

public interface RetailRateCardDao {
	
	public List<RetailRateCard> getRetailRateCardList() throws Exception;
	public RetailRateCard getRetailRateCardDetailsById(int retailRateCardId) throws Exception;
	public int addUpdateRetailRateCard(RetailRateCard retailRateCardObj) throws Exception;

}
