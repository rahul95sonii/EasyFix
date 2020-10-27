package com.easyfix.util.triggers.utils;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easyfix.util.triggers.model.Tracking;


public class DBUtils
{
	static Logger log = LogManager.getLogger(DBUtils.class);
	
	
	public static Tracking getTrackingData(Connection conn, int clientId, String startdate, String endDate) throws SQLException
	{
		Tracking tracking = new Tracking();
		CallableStatement cstmt = null;
		try
		{
			cstmt = conn.prepareCall("{call sp_ef_jobs_get_job_details_for_client_sms_and_email(?,?,?,?,?,?,?,?)}");
			cstmt.setInt(1, clientId);
			cstmt.setString(2, startdate);
			cstmt.setString(3, endDate);
			cstmt.registerOutParameter(4, Types.INTEGER);
			cstmt.registerOutParameter(5, Types.INTEGER);
			cstmt.registerOutParameter(6, Types.INTEGER);
			cstmt.registerOutParameter(7, Types.FLOAT);
			cstmt.registerOutParameter(8, Types.INTEGER);
			cstmt.execute();

			int created = cstmt.getInt(4);
			System.out.println("created: " +created);
			int scheduled = cstmt.getInt(5);
			int checkedOut = cstmt.getInt(6);
			float amount = cstmt.getFloat(7);
			int requested = cstmt.getInt(8);
			int finalAmount = Math.round(amount);
			tracking.setClientShare(finalAmount);
			tracking.setCountJobCheckedout(checkedOut);
			tracking.setCountJobCreated(created);
			tracking.setCountJobScheduled(scheduled);
			tracking.setCountJobRequested(requested);
		}
		finally
		{
			cstmt.close();
		}
		return tracking;
	}
	
//	public static File getTrackingDataTempFile(Connection conn, int clientId, String startdate, String endDate) throws SQLException
//	{
//	File f= File.createTempFile("file", ".xlsx");
//	System.out.println(f.getAbsolutePath());
//	return f;
//	}

}
