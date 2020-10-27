package com.easyfix.util.JmsSenderAndReceiver.dao;

import com.easyfix.util.JmsSenderAndReceiver.ExotelCallDetails;

public interface ExotelLoggingDao {
			public void persistExotelCallDetails(ExotelCallDetails call) throws Exception;
			public void updateExotelCallDetails(ExotelCallDetails call) throws Exception;
}
