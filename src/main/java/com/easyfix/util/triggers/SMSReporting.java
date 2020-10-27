package com.easyfix.util.triggers;

import java.io.File;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;



import com.easyfix.util.*;
import com.easyfix.util.triggers.service.SMSReportingService;

public class SMSReporting
{
	static Logger log = LogManager.getLogger(SMSReporting.class);
	
	public static void main(String[] args){
		
		File file = new File(args[0]+"/log4j2.xml");
		LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
		System.out.println(file.toURI().toString());
		context.setConfigLocation(file.toURI());
		PropertyReader props= PropertyReader.getInstance(args[0]);
		SMSReportingService.sendSms(props, args[1]);
		
	}
}
