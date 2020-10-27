package com.easyfix.settings.delegate;

import java.util.List;

import com.easyfix.settings.dao.RetailRateCardDao;
import com.easyfix.settings.model.RetailRateCard;

public class RetailRateCardServiceImpl implements RetailRateCardService{
	
	private  RetailRateCardDao retailRateCardDao;

	public RetailRateCardDao getRetailRateCardDao() {
		return retailRateCardDao;
	}

	public void setRetailRateCardDao(RetailRateCardDao retailRateCardDao) {
		this.retailRateCardDao = retailRateCardDao;
	}

	@Override
	public List<RetailRateCard> getRetailRateCardList() throws Exception {
		return retailRateCardDao.getRetailRateCardList();
	}

	@Override
	public RetailRateCard getRetailRateCardDetailsById(int retailRateCardId)
			throws Exception {
		return retailRateCardDao.getRetailRateCardDetailsById(retailRateCardId);
	}

	@Override
	public int addUpdateRetailRateCard(RetailRateCard retailRateCardObj)
			throws Exception {
		return retailRateCardDao.addUpdateRetailRateCard(retailRateCardObj);
	}

}
