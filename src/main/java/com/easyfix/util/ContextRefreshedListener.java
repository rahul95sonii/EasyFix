package com.easyfix.util;


import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.easyfix.util.utilityFunction.delegate.ContextRefreshedEventService;


@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
	private ContextRefreshedEventService contextRefreshedEventService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
	
//		System.out.println("---------------------------------------------------------------------------");
//		System.out.println("application listened");
		try {
			contextRefreshedEventService.setUserLoginStatusAndDate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public ContextRefreshedEventService getContextRefreshedEventService() {
		return contextRefreshedEventService;
	}

	public void setContextRefreshedEventService(
			ContextRefreshedEventService contextRefreshedEventService) {
		this.contextRefreshedEventService = contextRefreshedEventService;
	}

	

}
