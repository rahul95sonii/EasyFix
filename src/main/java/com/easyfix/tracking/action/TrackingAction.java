package com.easyfix.tracking.action;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easyfix.Jobs.model.Jobs;
import com.easyfix.clients.model.Clients;
import com.easyfix.settings.model.City;
import com.easyfix.tracking.delegate.TarckingService;
import com.easyfix.tracking.model.Tracking;
import com.easyfix.user.delegate.UserService;
import com.easyfix.user.model.User;
import com.easyfix.util.CommonAbstractAction;
import com.easyfix.util.UtilityFunctions;
import com.easyfix.util.CommonMasterData;
import com.opensymphony.xwork2.ModelDriven;

public class TrackingAction extends CommonAbstractAction implements ModelDriven<Tracking> {
	private static final Logger logger = LogManager.getLogger(TrackingAction.class);

	private static final long serialVersionUID = 1L;
	private Tracking trackingObj;
	private String actMenu;
	private String actParent;
	private String title;
	private String actParent2;
	
	private String type;
	private String fileName;
	private InputStream fileInputStream;
	
	private CommonMasterData commonMasterData;
	private TarckingService trackingService;
	private UserService userService;
	
	private List<User> NDMList;
	private List<Tracking> userLoggingList;
	private List<String> columnDate = new ArrayList<String>();
	private List<City> cityList;
	private List<Clients> clientList;
	private List<Jobs> jobList;
	private Jobs jobObj;
	private List<Tracking> cityAnalsisList;
	private List<Tracking> clientAnalsisList;
	private List<User> userList;
	private List<Tracking> userJobTrackerList;
	private static List<Tracking> downloadTrackingList;
	

	public String userLoggingHours(){
		
		try {
			setActMenu("Logging Hours");			
			setActParent("Tracking");
			setActParent2("Call Center");
			setTitle("Easyfix : Tracking");	
			
			String sDate = "";
			String eDate = "";
			String flag = "findAll";
			
			userLoggingList = trackingService.userLoggingHours(flag,sDate,eDate);
			
			Date d1 = new Date();				
			
			for(int i=0; i <= 7; i++){
				columnDate.add(UtilityFunctions.addDays(d1, -i));				
			}
					
			/*System.out.println(columnDate);			
			System.out.println(jsonResult);
			System.out.println(trackingObj);*/
				
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		
		
		return SUCCESS;
	}
	
	public String downloadLoggingHourDate(){
		try {
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return SUCCESS;
	}
	
	
	public String downloadLoggingHour() throws Throwable {
		try {
			type="application/octet-stream";
			fileName="myReport.xlsx";

			String sDate = requestObject.getParameter("startDate");
			String eDate = requestObject.getParameter("endDate");
			
			String startDate = UtilityFunctions.convertStringToDate(sDate);
			String endDate = UtilityFunctions.convertStringToDate(eDate);
			
			String flag = "download";
			
			userLoggingList = trackingService.userLoggingHours(flag,startDate,endDate);	
						
			fileInputStream = trackingService.downloadUserLoggingReport(userLoggingList,startDate,endDate,fileName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	
		
public String clientTracking() {
		
		try {
			setActMenu("Client Tracking");			
			setActParent("Tracking");
			setActParent2("Client Tracking");
			setTitle("Easyfix : Tracking");		
			
			commonMasterData = new CommonMasterData();
			cityList = commonMasterData.cityList();
			clientList = commonMasterData.clientList();	
			
		} catch (Exception e) {
			logger.catching(e);
		}
		
		return SUCCESS;
	}


public String cityAnalysis() {
	try {
		setActMenu("City Analysis");			
		setActParent("Tracking");
		setActParent2("Call Center");
		setTitle("Easyfix : City Analysis");
		
		commonMasterData = new CommonMasterData();
		cityList = commonMasterData.cityList();
		clientList = commonMasterData.clientList();		
		NDMList = userService.userListByRole("12,13");
		System.out.println(NDMList);
	} catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	}
	
	return SUCCESS;
}
	
public String cityAnalysisDetails() {
	try {		
		
		jobObj = new Jobs();
		jobObj.setFkClientId(Integer.parseInt(requestObject.getParameter("fkClientId")));
		jobObj.setJobStatus(Integer.parseInt(requestObject.getParameter("jobStatus")));
		jobObj.setFkCityId(Integer.parseInt(requestObject.getParameter("fkCityId")));
		jobObj.setNdmId(Integer.parseInt(requestObject.getParameter("ndmId")));
		String startDate = requestObject.getParameter("startDate");
		String endDate = requestObject.getParameter("endDate");
		
		jobObj.setStartDate(UtilityFunctions.convertStringToDate(startDate));
		jobObj.setEndDate(UtilityFunctions.convertStringToDate(endDate));
		
			
		jobList = trackingService.jobListForTracking(jobObj);
		Map<String, List<Jobs>> cityJobMap = trackingService.filterJobListByCity(jobList);
		cityAnalsisList = trackingService.iteratingJobListByCity(cityJobMap);
		
		setDownloadTrackingList(cityAnalsisList);
		
		int jStatus = jobObj.getJobStatus();
		requestObject.setAttribute("job_status", jStatus);	
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	}
	
	return SUCCESS;
}


public String clientTrackingDetails() {
	try {		
		
		jobObj = new Jobs();
		jobObj.setFkClientId(Integer.parseInt(requestObject.getParameter("fkClientId")));
		jobObj.setJobStatus(Integer.parseInt(requestObject.getParameter("jobStatus")));
		jobObj.setFkCityId(Integer.parseInt(requestObject.getParameter("fkCityId")));
		String startDate = requestObject.getParameter("startDate");
		String endDate = requestObject.getParameter("endDate");
		
		jobObj.setStartDate(UtilityFunctions.convertStringToDate(startDate));
		jobObj.setEndDate(UtilityFunctions.convertStringToDate(endDate));
		
		jobList = trackingService.jobListForTracking(jobObj);
		Map<String, List<Jobs>> clientJobMap = trackingService.filterJobListByClient(jobList);
		clientAnalsisList = trackingService.iteratingJobListByClient(clientJobMap);
		
		setDownloadTrackingList(clientAnalsisList);
		
		int jStatus = jobObj.getJobStatus();
		requestObject.setAttribute("job_status", jStatus);	
			
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	}
	
	return SUCCESS;
}


public String jobTracking() {
	try {
		setActMenu("Job Tracking");			
		setActParent("Tracking");
		setActParent2("Call Center");
		setTitle("Easyfix : Tracking");	
		
		commonMasterData = new CommonMasterData();
		cityList = commonMasterData.cityList();
		clientList = commonMasterData.clientList();		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return SUCCESS;
}

public String jobTrackingDetails() {
	try {
			jobObj = new Jobs();
	//		userList = userService.getUserListForTracking();
			
			String startDate = requestObject.getParameter("startDate");
			String endDate = requestObject.getParameter("endDate");
			jobObj.setFkClientId(Integer.parseInt(requestObject.getParameter("fkClientId")));
			jobObj.setFkCityId(Integer.parseInt(requestObject.getParameter("fkCityId")));
			jobObj.setJobStatus(Integer.parseInt(requestObject.getParameter("jobStatus")));
			
			jobObj.setStartDate(UtilityFunctions.convertStringToDate(startDate));
			jobObj.setEndDate(UtilityFunctions.convertStringToDate(endDate));
			
			jobList = trackingService.jobListForTracking(jobObj);
	//		System.out.println(jobList);
		//	System.out.println(userList);
			
			userJobTrackerList = trackingService.jobTrackingByUser( jobList,jobObj);
	//		System.out.println(jobList.size()+" == "+jobList);
			setDownloadTrackingList(userJobTrackerList);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return SUCCESS;
}

public String downloadTracking(){
	
	try {
		type="application/octet-stream";
		String track = requestObject.getParameter("track");
		if(track.equalsIgnoreCase("clientTracking"))
			fileName="clientTrackingReport.xlsx";
		
		if(track.equalsIgnoreCase("cityTracking"))
			fileName="cityTrackingReport.xlsx";
		
		if(track.equalsIgnoreCase("jobTracking"))
			fileName="jobTrackingReport.xlsx";
		
		List<Tracking> downloadList = getDownloadTrackingList();		
					
		fileInputStream = trackingService.downloadTrackingReport(downloadList,fileName, track);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return SUCCESS;
	
	
}
	
	


	public String getActMenu() {
		return actMenu;
	}

	public String getActParent() {
		return actParent;
	}

	public String getTitle() {
		return title;
	}

	public void setActMenu(String actMenu) {
		this.actMenu = actMenu;
	}

	public void setActParent(String actParent) {
		this.actParent = actParent;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getActParent2() {
		return actParent2;
	}

	public void setActParent2(String actParent2) {
		this.actParent2 = actParent2;
	}



	@Override // for RestrictAccessToUnauthorizedActionInterceptor
    public String toString(){
    	return "TrackingAction";
    }


	public Tracking getTrackingObj() {
		return trackingObj;
	}


	public void setTrackingObj(Tracking trackingObj) {
		this.trackingObj = trackingObj;
	}


	@Override
	public Tracking getModel() {
		return trackingObj = new Tracking();
	}


	public TarckingService getTrackingService() {
		return trackingService;
	}


	public void setTrackingService(TarckingService trackingService) {
		this.trackingService = trackingService;
	}


	public List<Tracking> getUserLoggingList() {
		return userLoggingList;
	}


	public void setUserLoggingList(List<Tracking> userLoggingList) {
		this.userLoggingList = userLoggingList;
	}


	public List<String> getColumnDate() {
		return columnDate;
	}


	public void setColumnDate(List<String> columnDate) {
		this.columnDate = columnDate;
	}



	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(InputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}




	public List<City> getCityList() {
		return cityList;
	}




	public List<Clients> getClientList() {
		return clientList;
	}




	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}




	public void setClientList(List<Clients> clientList) {
		this.clientList = clientList;
	}
	
	public List<Jobs> getJobList() {
		return jobList;
	}




	public void setJobList(List<Jobs> jobList) {
		this.jobList = jobList;
	}




	public Jobs getJobObj() {
		return jobObj;
	}



	public void setJobObj(Jobs jobObj) {
		this.jobObj = jobObj;
	}




	public List<Tracking> getCityAnalsisList() {
		return cityAnalsisList;
	}




	public void setCityAnalsisList(List<Tracking> cityAnalsisList) {
		this.cityAnalsisList = cityAnalsisList;
	}




	public List<Tracking> getClientAnalsisList() {
		return clientAnalsisList;
	}




	public void setClientAnalsisList(List<Tracking> clientAnalsisList) {
		this.clientAnalsisList = clientAnalsisList;
	}




	public List<User> getUserList() {
		return userList;
	}




	public void setUserList(List<User> userList) {
		this.userList = userList;
	}




	public UserService getUserService() {
		return userService;
	}




	public void setUserService(UserService userService) {
		this.userService = userService;
	}




	public List<Tracking> getUserJobTrackerList() {
		return userJobTrackerList;
	}




	public void setUserJobTrackerList(List<Tracking> userJobTrackerList) {
		this.userJobTrackerList = userJobTrackerList;
	}




	public static List<Tracking> getDownloadTrackingList() {
		return downloadTrackingList;
	}




	public static void setDownloadTrackingList(List<Tracking> downloadTrackingList) {
		TrackingAction.downloadTrackingList = downloadTrackingList;
	}

	
	public CommonMasterData getCommonMasterData() {
		return commonMasterData;
	}

	public void setCommonMasterData(CommonMasterData commonMasterData) {
		this.commonMasterData = commonMasterData;
	}

	public List<User> getNDMList() {
		return NDMList;
	}

	public void setNDMList(List<User> nDMList) {
		NDMList = nDMList;
	}


	

}

