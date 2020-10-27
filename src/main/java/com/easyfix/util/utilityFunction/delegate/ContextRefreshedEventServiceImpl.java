package com.easyfix.util.utilityFunction.delegate;

import com.easyfix.util.utilityFunction.dao.ContextRefreshedEventDao;

public class ContextRefreshedEventServiceImpl implements ContextRefreshedEventService {

	private ContextRefreshedEventDao contextRefreshedEventDao;
	@Override
	public void setUserLoginStatusAndDate() throws Exception {
		contextRefreshedEventDao.setUserLoginStatusAndDate();
		
	}
	public ContextRefreshedEventDao getContextRefreshedEventDao() {
		return contextRefreshedEventDao;
	}
	public void setContextRefreshedEventDao(
			ContextRefreshedEventDao contextRefreshedEventDao) {
		this.contextRefreshedEventDao = contextRefreshedEventDao;
	}

}
