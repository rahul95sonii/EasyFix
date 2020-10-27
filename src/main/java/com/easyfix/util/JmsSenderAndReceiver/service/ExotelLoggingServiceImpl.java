package com.easyfix.util.JmsSenderAndReceiver.service;

import com.easyfix.util.JmsSenderAndReceiver.ExotelCallDetails;
import com.easyfix.util.JmsSenderAndReceiver.dao.ExotelLoggingDao;

public class ExotelLoggingServiceImpl implements ExotelLoggingService {
	
	private ExotelLoggingDao exotelLoggingDao;
	@Override
	public void persistExotelCallDetails(ExotelCallDetails call)
			throws Exception {
		exotelLoggingDao.persistExotelCallDetails(call);

	}
	
	@Override
	public void updateExotelCallDetails(ExotelCallDetails call)
			throws Exception {
		exotelLoggingDao.updateExotelCallDetails(call);
		
	}
	
	
	
	
	public ExotelLoggingDao getExotelLoggingDao() {
		return exotelLoggingDao;
	}
	public void setExotelLoggingDao(ExotelLoggingDao exotelLoggingDao) {
		this.exotelLoggingDao = exotelLoggingDao;
	}
	

}
