package com.easyfix.util.JmsSenderAndReceiver.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.easyfix.Jobs.dao.JobDaoImpl;
import com.easyfix.util.JmsSenderAndReceiver.ExotelCallDetails;

public class ExotelLoggingDaoImpl extends JdbcDaoSupport implements ExotelLoggingDao  {
	private static final Logger logger = LogManager.getLogger(ExotelLoggingDaoImpl.class);
	@Override
	public void persistExotelCallDetails(ExotelCallDetails call)
			throws Exception {
		String sql = "INSERT INTO tbl_exotel_call_log(callTime,callSid,fromNumber,direction,agentNumber,"
				+ "language,type,status,update_date_time) "
				+ "VALUES( ?,?,?,?,?,?,?,?,NOW())";
		try{
		int insertId= getJdbcTemplate().update(sql, new Object[]{call.getCallTime(),call.getCallSid(),call.getFromNumber(),
				 call.getDirection(),call.getAgentNumber(),call.getLanguage(),call.getType(),
				 call.getStatus()});
		}
		catch(Exception e){
			logger.catching(e);
			e.printStackTrace();
			
		}
	}

	@Override
	public void updateExotelCallDetails(ExotelCallDetails call)
			throws Exception {
		String sql = "UPDATE tbl_exotel_call_log SET updated_by =? ,update_date_time = NOW() WHERE callSid =?";
		
		try{
			getJdbcTemplate().update(sql, new Object[]{call.getCrmUserId(), call.getCallSid()});
		}
		catch(Exception e){
			e.printStackTrace();
			logger.catching(e);
		}
		
	}

}
