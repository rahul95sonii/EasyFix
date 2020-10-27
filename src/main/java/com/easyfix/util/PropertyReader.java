package com.easyfix.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PropertyReader
{

	static Log log = LogFactory.getLog(PropertyReader.class);

	private static PropertyReader instance = null;
	private Properties properties;

	public PropertyReader(/*String path*/) throws IOException
	{
		properties = new Properties();
		//String finalPath = path + File.separator + "trigger.properties";
		InputStream in= null;
		try{
		//	in= new FileInputStream(finalPath);
			in = getClass().getClassLoader().getResourceAsStream("trigger.properties");
			properties.load(in);
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			in.close();
		}
		
		
	}
	public PropertyReader(String path) throws IOException
	{
		properties = new Properties();
		String finalPath = path + "trigger.properties";
		System.out.println(finalPath);
		InputStream in= null;
		try{
			in= new FileInputStream(finalPath);
			//in = getClass().getClassLoader().getResourceAsStream("trigger.properties");
			properties.load(in);
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			in.close();
		}
		
		
	}
	public static PropertyReader getInstance(String path)
	{
		if (instance == null)
		{
			try
			{
				instance = new PropertyReader(path);
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
