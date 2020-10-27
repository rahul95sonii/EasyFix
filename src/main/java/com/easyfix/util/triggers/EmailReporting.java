package com.easyfix.util.triggers;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import com.easyfix.util.PropertyReader;
import com.easyfix.util.triggers.service.EmailReportingService;



public class EmailReporting
{
	
	public static void main(String[] args){
		
		File file = new File(args[0]+"/log4j2.xml");
		
		LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
		//System.out.println(file.toURI().toString());
		context.setConfigLocation(file.toURI());
		PropertyReader props= PropertyReader.getInstance(args[0]);
		EmailReportingService.sendEmail(props, args[1]);
	}
	
}
