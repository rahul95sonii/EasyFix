package com.easyfix.util.triggers.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.easyfix.util.PropertyReader;

public class ConnectionProvider
{

	static Log logger = LogFactory.getLog(ConnectionProvider.class);
	private static ConnectionProvider instance = null;
	private static Connection conn;
	String userName = null;
	String password = null;
	String urlMySQL = null;
	String driverName = null;
	static String liveDB = null;

	private ConnectionProvider(PropertyReader props, String password)
	{
		this.userName = props.getValue("db.USERNAME");
		this.password = password;
		this.urlMySQL = props.getValue("urlMySQL");
		this.driverName = props.getValue("DriverName");
		liveDB = props.getValue("Db.live");

		try
		{
			Class.forName(driverName);
			logger.debug("*************DataBase UserName is " + userName + " Password " + password + " Url " + urlMySQL
					+ "****************");
			conn = DriverManager.getConnection(urlMySQL + "?noAccessToProcedureBodies=true", userName, password);
			// logger.info("Data Base connection created ");
		}
		catch (ClassNotFoundException cnfErr)
		{
			logger.error(cnfErr.getMessage());
			cnfErr.printStackTrace();
		}
		catch (SQLException err)
		{
			logger.error(err.getMessage());
			err.printStackTrace();
		}
	}

	public static ConnectionProvider getInstance(PropertyReader props, String password)
	{
		if (instance == null)
		{
			return new ConnectionProvider(props, password);
		}
		else
		{
			return instance;

		}

	}

	public static Connection getConnection(PropertyReader props, String password)
	{
		System.out.println("opening connection");
		getInstance(props, password);
		return conn;
	}

	
	public static String getLiveDB()
	{
		return liveDB;
	}

	public static void setLiveDB(String liveDB)
	{
		ConnectionProvider.liveDB = liveDB;
	}

}