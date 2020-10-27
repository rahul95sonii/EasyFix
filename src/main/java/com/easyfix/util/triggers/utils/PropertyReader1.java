package com.easyfix.util.triggers.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.easyfix.util.PropertyReader;

public class PropertyReader1
{

	static Log log = LogFactory.getLog(PropertyReader.class);

	private static PropertyReader1 instance = null;
	private Properties properties;

	protected PropertyReader1(String path) throws IOException
	{
		properties = new Properties();
		String finalPath = path + File.separator + "trigger.properties";
		InputStream in= null;
		try{
			in= new FileInputStream(finalPath);
			properties.load(in);
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			in.close();
		}
		
		
	}

	public static PropertyReader1 getInstance(String path)
	{
		if (instance == null)
		{
			try
			{
				instance = new PropertyReader1(path);
			}
			catch (IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
		return instance;
	}

	public String getValue(String key)
	{
		return properties.getProperty(key);
	}
}
