package com.easyfix.util.JmsSenderAndReceiver.service;

import com.easyfix.util.JmsSenderAndReceiver.ExotelCallDetails;

public interface ExotelLoggingService {
	public void persistExotelCallDetails(ExotelCallDetails call) throws Exception;
	public void updateExotelCallDetails(ExotelCallDetails call) throws Exception;
}
