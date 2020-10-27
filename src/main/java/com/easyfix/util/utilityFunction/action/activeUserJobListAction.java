package com.easyfix.util.utilityFunction.action;

import java.util.List;

import com.easyfix.Jobs.model.Jobs;
import com.easyfix.user.model.User;
import com.easyfix.util.CommonAbstractAction;
import com.easyfix.util.utilityFunction.delegate.ActiveUserJobListService;

public class activeUserJobListAction extends CommonAbstractAction{

	private static final long serialVersionUID = 1L;
	private ActiveUserJobListService activeUserJobListService;
	
	public void assignJobsToUser(){
		try{
			//System.out.println("in activeUserJobListAction");
		List<Jobs> activeJobs = activeUserJobListService.getActiveJobList(0,1,2,3,4);
		//System.out.println(activeJobs);
		List<User> activeUser = activeUserJobListService.getACtiveUserList(1);
		//System.out.println(activeUser);
		activeUserJobListService.assignJobsInloginOrder(activeJobs, activeUser);
		//System.out.println("scheduling done");
		}
		catch(Exception e){
			System.out.println(e);
		}
		
	}
	public ActiveUserJobListService getActiveUserJobListService() {
		return activeUserJobListService;
	}
	public void setActiveUserJobListService(
			ActiveUserJobListService activeUserJobListService) {
		this.activeUserJobListService = activeUserJobListService;
	}
	@Override // for RestrictAccessToUnauthorizedActionInterceptor
	public String toString(){
		return "activeUserJobListAction";
	}
}
