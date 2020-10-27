package com.easyfix.tracking.delegate;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;

import com.easyfix.Jobs.model.Jobs;
import com.easyfix.clients.model.Clients;
import com.easyfix.customers.model.Address;
import com.easyfix.customers.model.Customers;
import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.settings.model.City;
import com.easyfix.settings.model.ServiceType;
import com.easyfix.tracking.action.TrackingAction;
import com.easyfix.tracking.model.Tracking;
import com.easyfix.user.model.User;
import com.easyfix.util.ExcelGenerateReport;
import com.easyfix.util.RestClient;
import com.easyfix.util.UtilityFunctions;

public class TarckingServiceImpl implements TarckingService {
	private static final Logger logger = LogManager.getLogger(TarckingServiceImpl.class);
	
@Override
public List<Tracking> userLoggingHours(String flag, String startDate, String endDate){
		List<Tracking> userLoggingList = new ArrayList<Tracking>();
		try {
			
			WebTarget target = new RestClient().apiResponse();
			
			WebTarget loggingTarget = target.path("userLog/"+flag);
			
			if(startDate != ""){
				loggingTarget = loggingTarget.queryParam("startDate", startDate);
			}
			
			if(endDate != ""){
				loggingTarget = loggingTarget.queryParam("endDate", endDate);
			}
			
			String jsonResult = loggingTarget.request().accept(MediaType.APPLICATION_JSON).get(String.class);
			//String jsonResult = new RestClient().apiResponse("userLog");
			
			JSONArray jArray = new JSONArray(jsonResult);
			ObjectMapper mapper = new ObjectMapper();
			
			for(int i=0; i<jArray.length();i++) {
				Tracking trackingObj=null;
				try{
				 trackingObj = mapper.readValue(jArray.getString(i), Tracking.class);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				userLoggingList.add(trackingObj);
				System.out.println(jArray.getString(i));
			}
			
				
		} catch (Exception e) {
			logger.catching(e);
			
		}
		
		return userLoggingList;
		
	}

	
@Override
public FileInputStream downloadUserLoggingReport(List<Tracking> userLoggingList, String startDate, String endDate, String fileName) {	
		FileInputStream loggingReportFile=null;
		List<String> columnDayList = new ArrayList<String>();
	try {
				
		ExcelGenerateReport  myReport = new ExcelGenerateReport();
		
		long days =  UtilityFunctions.diffBetweenTwoDate(startDate,endDate);
		
		//Date d1 = new Date();	
		Date d1 = UtilityFunctions.convertStringToDateInDate(endDate,"yyyy-MM-dd");
		for(int i=0; i <= days; i++){
			columnDayList.add(UtilityFunctions.addDays(d1, -i));	
		}
		
				
		File reportFile= myReport.createExcel(fileName,columnDayList,userLoggingList, "userLogging");
		
		loggingReportFile=new FileInputStream(reportFile);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
		
	return loggingReportFile;
}


@Override
public List<Jobs> jobListForTracking(Jobs jobObj) throws Exception {
	List<Jobs> jobList = new ArrayList<Jobs>();
	//WebTarget finalTarget=null;
	try {
		
	///	WebTarget target = TargetURISingleton.getTargetURISingleton();
	//	WebTarget jobWebTarget = target.path("jobs").queryParam("id",1).q;
		
		
		WebTarget target = new RestClient().apiResponse();
		
		WebTarget jobTarget = target.path("jobs/tracking");
		
		if(jobObj.getJobId() > 0){
			jobTarget = jobTarget.queryParam("id", jobObj.getJobId());
		}
		if(jobObj.getJobStatus() != 100){
			jobTarget = jobTarget.queryParam("status", jobObj.getJobStatus());
		}
		if(jobObj.getFkClientId() > 0){
			jobTarget = jobTarget.queryParam("clientId", jobObj.getFkClientId());
		}
		if(jobObj.getFkCityId() > 0){
			jobTarget = jobTarget.queryParam("cityId", jobObj.getFkCityId());
		}
		if(jobObj.getStartDate() != null){
			jobTarget = jobTarget.queryParam("startDate", jobObj.getStartDate());
		}
		if(jobObj.getEndDate() != null){
			jobTarget = jobTarget.queryParam("endDate", jobObj.getEndDate());
		}
		if(jobObj.getNdmId() > 0){
			jobTarget = jobTarget.queryParam("ndmId", jobObj.getNdmId());
		}
		
	//	System.out.println("From Target URI == "+jobTarget.getUri());
		
		String jsonResult = jobTarget.request().accept(MediaType.APPLICATION_JSON).get(String.class);
	//			
	//	System.out.println("From Tracking Service == "+jsonResult);
		
		JSONArray jArray = new JSONArray(jsonResult);
		ObjectMapper mapper = new ObjectMapper();
		
		for(int i=0; i<jArray.length();i++) {
			try{
			Jobs jobResponce = mapper.readValue(jArray.getString(i), Jobs.class);
			jobList.add(jobResponce);
	//		System.out.println("in tracking serivceimpl"+jobResponce);
	//		System.out.println("in tracking serivceimpl"+jobList);
			}
			catch(Exception e){
				logger.catching(e);
				e.printStackTrace();
			}
							
		}
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	}
	
	logger.info("Tracking Service List Size " + jobList.size());
	
	return jobList;
}



/*
public static void main(String[] args)throws Exception{
	Jobs job = new Jobs();
	//job.setJobId(0);
	job.setJobStatus(0);
	job.setStartDate("2016-01-01");
	job.setEndDate("2016-02-02");
	TarckingServiceImpl s =new TarckingServiceImpl();
	List<Jobs> l =s.jobListForTracking(job);
	s.filterJobListByCity(l);
}
*/


public List<Tracking> iteratingJobListByCity(Map<String, List<Jobs>> cityJobMap) throws Exception {
	
	List<Tracking> cityAnalysisList = new ArrayList<Tracking>();
	Tracking trackObj = null;
	Set<String> cityNameSet = cityJobMap.keySet();	
	
	DecimalFormat df = new DecimalFormat("######.##");
	
	try {			
		for(String key : cityNameSet) {	
			
			trackObj = new Tracking();
			trackObj.setCityName(key);
			
			List<Jobs> cityJobs = cityJobMap.get(key);
			int jobCount = cityJobs.size();
			trackObj.setJobGiven(jobCount);
			int comletedJob=0;
			int cancelJob=0;
			double billing = 0;
			float efShare = 0;
			float clientShare = 0;
			float material = 0;
			float avgTicket = 0;
			float conversionper = 0;
			int checkOutJob = 0;
			
			for(Jobs j:cityJobs){
				if(j.getJobStatus()==5)
					comletedJob++;
				
				if(j.getJobStatus()==6)
					cancelJob++;
				
				if(j.getJobStatus()==3)
					checkOutJob++;
				
				if(j.getFinanceObj() != null)
				{
					billing = billing + j.getFinanceObj().getTotalCharge();
					efShare = efShare + j.getFinanceObj().getEfCharge();
					clientShare = clientShare + j.getFinanceObj().getClientCharge();
				}
				
				if(j.getMaterialCharge() != null)
					material = material + Integer.parseInt(j.getMaterialCharge());
			}
			
			
			/*if(jobCount > 0)
				 conversionper = comletedJob*100/(jobCount);*/
			if(checkOutJob > 0)
				avgTicket = (float) (billing/checkOutJob);
			
			trackObj.setJobCompleted(comletedJob);
			trackObj.setJobCanceled(cancelJob);
			trackObj.setConversion(Math.round(conversionper));	
			trackObj.setBilling(billing);
			trackObj.setEfShare(efShare);
			trackObj.setClientShare(clientShare);
			trackObj.setMaterial(material);
			trackObj.setJobCheckOut(checkOutJob);
			
			trackObj.setAvgTicket(df.format(avgTicket));
						
						
			//System.out.println("key: " + key + " value: " + cityJobMap.get(key));
	
			cityAnalysisList.add(trackObj);
		}
	
	} catch (Exception e) {
	System.out.println(e.getMessage());
		logger.catching(e);
	}

	return cityAnalysisList;
}

public Map<String, List<Jobs>> filterJobListByCity(List<Jobs> jobList) throws Exception {
	Map<String, List<Jobs>> jobMap = new HashMap<String, List<Jobs>>();
	try {
		for(Jobs j:jobList){
			
			String cityName = j.getAddressObj().getCityObj().getCityName();
			
			if(jobMap.containsKey(cityName)){
				List<Jobs> ExistingjobsList = (List<Jobs>)jobMap.get(cityName);
				ExistingjobsList.add(j);
				jobMap.put(cityName, ExistingjobsList);
			}
			else{
				List<Jobs> jobs = new ArrayList<Jobs>();
				jobs.add(j);
				jobMap.put(cityName,jobs);
			}
		}

		//System.out.println(jobMap);		
		
	} catch (Exception e) {
		System.out.println(e.getMessage());
		logger.catching(e);
	}
	
	return jobMap;
	
}


@Override
public Map<String, List<Jobs>> filterJobListByClient(List<Jobs> jobList) throws Exception {
	Map<String, List<Jobs>> jobMap = new HashMap<String, List<Jobs>>();
	try {
		for(Jobs j:jobList){
			
			String clientName = j.getClientObj().getClientName();
			
			if(jobMap.containsKey(clientName)){
				List<Jobs> ExistingjobsList = (List<Jobs>)jobMap.get(clientName);
				ExistingjobsList.add(j);
				jobMap.put(clientName, ExistingjobsList);
			}
			else{
				List<Jobs> jobs = new ArrayList<Jobs>();
				jobs.add(j);
				jobMap.put(clientName,jobs);
			}
		}

	//	System.out.println(jobMap);		
		
	} catch (Exception e) {
	//	e.printStackTrace();
		logger.catching(e);
	}
	
	return jobMap;
}


@Override
public List<Tracking> iteratingJobListByClient(Map<String, List<Jobs>> clientJobMap) throws Exception {
	List<Tracking> clientAnalysisList = new ArrayList<Tracking>();
	Tracking trackObj = null;
	Set<String> clientNameSet = clientJobMap.keySet();	
	DecimalFormat df = new DecimalFormat("######.##");
	try {			
		for(String key : clientNameSet) {	
			
			trackObj = new Tracking();
			trackObj.setClientName(key);
			
			List<Jobs> clientJobs = clientJobMap.get(key);
			int jobCount = clientJobs.size();
			trackObj.setJobGiven(jobCount);
			int comletedJob=0;
			int cancelJob=0;
			int checkOutJob = 0;
			double billing = 0;
			float efShare = 0;
			float clientShare = 0;
			float material = 0;
			float conversionper = 0;
			float avgTicket = 0;
			
			for(Jobs j:clientJobs){
				if(j.getJobStatus()==5)
					comletedJob++;
				
				if(j.getJobStatus()==3)
					checkOutJob++;
				
				if(j.getJobStatus()==6)
					cancelJob++;
				
				if(j.getFinanceObj() != null)
				{
					billing = billing + j.getFinanceObj().getTotalCharge();
					efShare = efShare + j.getFinanceObj().getEfCharge();
					clientShare = clientShare + j.getFinanceObj().getClientCharge();
				}
				
				if(j.getMaterialCharge() != null)
					material = material + Integer.parseInt(j.getMaterialCharge());
			}
			
			
			/*if(jobCount > 0)
				conversionper = comletedJob*100/(jobCount);*/
			if(checkOutJob > 0)
				avgTicket = (float) (billing/checkOutJob);
			
			trackObj.setJobCompleted(comletedJob);
			trackObj.setJobCanceled(cancelJob);
			trackObj.setConversion(Math.round(conversionper));	
			trackObj.setBilling(billing);
			trackObj.setEfShare(efShare);
			trackObj.setClientShare(clientShare);
			trackObj.setMaterial(material);
			trackObj.setAvgTicket(df.format(avgTicket));
			trackObj.setJobCheckOut(checkOutJob);
			
			//System.out.println("key: " + key + " value: " + cityJobMap.get(key));
	
			clientAnalysisList.add(trackObj);
		}
	
	} catch (Exception e) {
	//	e.printStackTrace();
		logger.catching(e);
	}

	return clientAnalysisList;
}


@Override
public List<Tracking> jobTrackingByUser( List<Jobs> jobList,Jobs jobObj) {
	List<Tracking> jobTrackingList = new ArrayList<Tracking>();
	Map<String,Tracking> map = new HashMap<String,Tracking>();
//	Tracking jobTrackObj = new Tracking();
//	int createCount = 0;
//	int checkInCount = 0;
//	int checkOutCount = 0;
//	int scheduleCount =  0;
//	int feedbackCount = 0;
//	int cancelCount = 0;
//	int enquiryCount = 0;
//	System.out.println(jobObj.getStartDate()+"::"+jobObj.getEndDate());
	Date startDate = UtilityFunctions.convertStringToDateInDate(jobObj.getStartDate(),"yyyy-MM-dd");
	Date end = UtilityFunctions.convertStringToDateInDate(jobObj.getEndDate(),"yyyy-MM-dd");
	String endDate1 = UtilityFunctions.addSubstractDaysInDate(end, 1, "yyyy-MM-dd");
	end = UtilityFunctions.convertStringToDateInDate(endDate1,"yyyy-MM-dd");
	
	Date createdTime = null;
	Date scheduledTime = null;
	Date checkinTime = null;
	Date checkoutTime = null;
	Date feedbackTime = null;
	Date cancleTime = null;
	for(Jobs j:jobList){
		createdTime = UtilityFunctions.convertStringToDateInDate(j.getCreatedDateTime(), "dd-MM-yyyy HH:mm");
		scheduledTime = UtilityFunctions.convertStringToDateInDate(j.getScheduleDateTime(), "dd-MM-yyyy HH:mm");
		checkinTime = UtilityFunctions.convertStringToDateInDate(j.getCheckInDateTime(), "dd-MM-yyyy HH:mm");
		checkoutTime = UtilityFunctions.convertStringToDateInDate(j.getCheckOutDateTime(), "dd-MM-yyyy HH:mm");
		feedbackTime = UtilityFunctions.convertStringToDateInDate(j.getFeedbackDateTime(), "dd-MM-yyyy HH:mm");
		cancleTime = UtilityFunctions.convertStringToDateInDate(j.getCancelDateTime(), "dd-MM-yyyy HH:mm");
		
		
		if(j.getJobStatus() == 7){	
		
//		System.out.println(d+"::"+(d.compareTo(startDate)>=0)+"::"+(d.compareTo(end)<0));
			if(j.getJobCreatedBy()!=null && 
					isDate1GreaterThanOrEqualToDate2(String.valueOf(createdTime.getTime()),String.valueOf(startDate.getTime()))
					&& !isDate1GreaterThanOrEqualToDate2(String.valueOf(createdTime.getTime()),String.valueOf(end.getTime()))
					)
					{
			//	System.out.println("yes");
				User user = j.getJobCreatedBy();
				if(map.containsKey(user.getUserName())){
					Tracking jobTrackObj =map.get(user.getUserName());
					jobTrackObj.setJobEnquiryCount(jobTrackObj.getJobEnquiryCount()+1);
					map.put(user.getUserName(), jobTrackObj);
					}
				else{
					Tracking jobTrackObj = new Tracking();
					jobTrackObj.setUserName(user.getUserName());
					jobTrackObj.setJobEnquiryCount(1);
					map.put(user.getUserName(), jobTrackObj);
				   }
				
				
		    	}
	    }
	else{
		if(j.getJobCreatedBy()!=null  && 
				isDate1GreaterThanOrEqualToDate2(String.valueOf(createdTime.getTime()),String.valueOf(startDate.getTime()))
				&& !isDate1GreaterThanOrEqualToDate2(String.valueOf(createdTime.getTime()),String.valueOf(end.getTime()))
							
				){
			
			User user = j.getJobCreatedBy();
			if(map.containsKey(user.getUserName())&& 
					isDate1GreaterThanOrEqualToDate2(String.valueOf(createdTime.getTime()),String.valueOf(startDate.getTime()))
					&& !isDate1GreaterThanOrEqualToDate2(String.valueOf(createdTime.getTime()),String.valueOf(end.getTime()))
					
					){
				Tracking jobTrackObj =map.get(user.getUserName());
				jobTrackObj.setJobCreatedBy(jobTrackObj.getJobCreatedBy()+1);
				map.put(user.getUserName(), jobTrackObj);
						}
			else{
				Tracking jobTrackObj = new Tracking();
				jobTrackObj.setUserName(user.getUserName());
				jobTrackObj.setJobCreatedBy(1);
				map.put(user.getUserName(), jobTrackObj);
	
			}
			
		}
		if(j.getJobScheduledBy()!=null && 
				isDate1GreaterThanOrEqualToDate2(String.valueOf(scheduledTime.getTime()),String.valueOf(startDate.getTime()))
				&& !isDate1GreaterThanOrEqualToDate2(String.valueOf(scheduledTime.getTime()),String.valueOf(end.getTime()))
				
				
				){
			User user = j.getJobScheduledBy();
			if(map.containsKey(user.getUserName())){
				Tracking jobTrackObj =map.get(user.getUserName());
				jobTrackObj.setJobScheduledBy((jobTrackObj.getJobScheduledBy())+1);
				map.put(user.getUserName(), jobTrackObj);
						}
			else{
				Tracking jobTrackObj = new Tracking();
				jobTrackObj.setUserName(user.getUserName());
				jobTrackObj.setJobScheduledBy(1);
				map.put(user.getUserName(), jobTrackObj);
			}	
		}

			if(j.getJobCheckInBy()!=null &&
					isDate1GreaterThanOrEqualToDate2(String.valueOf(checkinTime.getTime()),String.valueOf(startDate.getTime()))
					&& !isDate1GreaterThanOrEqualToDate2(String.valueOf(checkinTime.getTime()),String.valueOf(end.getTime()))
					
					){
				User user = j.getJobCheckInBy();
				if(map.containsKey(user.getUserName())){
					Tracking jobTrackObj =map.get(user.getUserName());
					jobTrackObj.setJobCheckInBy(jobTrackObj.getJobCheckInBy()+1);
					map.put(user.getUserName(), jobTrackObj);
				}
				else{
					Tracking jobTrackObj = new Tracking();
					jobTrackObj.setUserName(user.getUserName());
					jobTrackObj.setJobCheckInBy(1);
					map.put(user.getUserName(), jobTrackObj);
		
				}
				
			}
			if(j.getJobCheckOutBy()!=null &&
					isDate1GreaterThanOrEqualToDate2(String.valueOf(checkoutTime.getTime()),String.valueOf(startDate.getTime()))
					&& !isDate1GreaterThanOrEqualToDate2(String.valueOf(checkoutTime.getTime()),String.valueOf(end.getTime()))
										
					){
				User user = j.getJobCheckOutBy();
				if(map.containsKey(user.getUserName())){
					Tracking jobTrackObj =map.get(user.getUserName());
					jobTrackObj.setJobCheckOutBy(jobTrackObj.getJobCheckOutBy()+1);
					map.put(user.getUserName(), jobTrackObj);
				}
				else{
					Tracking jobTrackObj = new Tracking();
					jobTrackObj.setUserName(user.getUserName());
					jobTrackObj.setJobCheckOutBy(1);
					map.put(user.getUserName(), jobTrackObj);
		
				}
				
			}

			if(j.getJobFeedbackBy()!=null &&
					isDate1GreaterThanOrEqualToDate2(String.valueOf(feedbackTime.getTime()),String.valueOf(startDate.getTime()))
					&& !isDate1GreaterThanOrEqualToDate2(String.valueOf(feedbackTime.getTime()),String.valueOf(end.getTime()))
					
					){
				User user = j.getJobFeedbackBy();
				if(map.containsKey(user.getUserName())){
					Tracking jobTrackObj =map.get(user.getUserName());
					jobTrackObj.setJobFeedbackBy(jobTrackObj.getJobFeedbackBy()+1);
					map.put(user.getUserName(), jobTrackObj);
							}
				else{
					Tracking jobTrackObj = new Tracking();
					jobTrackObj.setUserName(user.getUserName());
					jobTrackObj.setJobFeedbackBy(1);
					map.put(user.getUserName(), jobTrackObj);
				}	
			}
			if(j.getJobCanceledBy()!=null && 
					isDate1GreaterThanOrEqualToDate2(String.valueOf(cancleTime.getTime()),String.valueOf(startDate.getTime()))
					&& !isDate1GreaterThanOrEqualToDate2(String.valueOf(cancleTime.getTime()),String.valueOf(end.getTime()))
					
					){
				User user = j.getJobCanceledBy();
				if(map.containsKey(user.getUserName())){
					Tracking jobTrackObj =map.get(user.getUserName());
					jobTrackObj.setJobCanceledBy(jobTrackObj.getJobCanceledBy()+1);
					map.put(user.getUserName(), jobTrackObj);
				}
				else{
					Tracking jobTrackObj = new Tracking();
					jobTrackObj.setUserName(user.getUserName());
					jobTrackObj.setJobCanceledBy(1);
					map.put(user.getUserName(), jobTrackObj);
		
				}
			}
			
	}

		
	}
/*	
	try {
		for(User u : userList) {
			
			jobTrackObj = new Tracking();
			int createCount = 0;
			int checkInCount = 0;
			int checkOutCount = 0;
			int scheduleCount =  0;
			int feedbackCount = 0;
			int cancelCount = 0;
			int enquiryCount = 0;
			
			jobTrackObj.setUserName(u.getUserName());
			
			for(Jobs j : jobList) {
				
				if(j.getJobStatus() == 7){
					if(j.getJobCreatedBy()!=null) {
						if(u.getUserId() == j.getJobCreatedBy().getUserId())
							enquiryCount++;
					}
				}
				else {
					if(j.getJobCreatedBy()!=null) {
						if(u.getUserId() == j.getJobCreatedBy().getUserId())
							createCount++;
					}
					if(j.getJobScheduledBy()!=null){
						if(u.getUserId() == j.getJobScheduledBy().getUserId())
							scheduleCount++;
					}
					if(j.getJobCheckInBy()!=null){
						if(u.getUserId() == j.getJobCheckInBy().getUserId())
							checkInCount++;
					}
					if(j.getJobCheckOutBy()!=null){
						if(u.getUserId() == j.getJobCheckOutBy().getUserId())
							checkOutCount++;
					}
					if(j.getJobFeedbackBy()!=null){
						if(u.getUserId() == j.getJobFeedbackBy().getUserId())
							feedbackCount++;
					}
					if(j.getJobCanceledBy()!=null){
						if(u.getUserId() == j.getJobCanceledBy().getUserId())
							cancelCount++;
					}
				}
			}
			
			jobTrackObj.setJobCreatedBy(createCount);
			jobTrackObj.setJobScheduledBy(scheduleCount);
			jobTrackObj.setJobCheckInBy(checkInCount);
			jobTrackObj.setJobCheckOutBy(checkOutCount);
			jobTrackObj.setJobFeedbackBy(feedbackCount);
			jobTrackObj.setJobCanceledBy(cancelCount);
			jobTrackObj.setJobEnquiryCount(enquiryCount);
			
			jobTrackingList.add(jobTrackObj);
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	*/
	
	
//	System.out.println("in serviceImpl"+map);
	for(Entry<String,Tracking> entry:map.entrySet() ){
		jobTrackingList.add(entry.getValue());
	}
//	System.out.println("in serviceImpl"+jobTrackingList);
	return jobTrackingList;
}


@Override
public FileInputStream downloadTrackingReport(List<Tracking> downloadList, String fileName, String flag) {
	FileInputStream downloadTracking = null;
	try {
		ExcelGenerateReport myReport = new ExcelGenerateReport();
		
		List<String> headerList = new ArrayList<String>();
		
		if(flag.equalsIgnoreCase("jobTracking"))
		{
			headerList.add("User");
			headerList.add("Booked");
			headerList.add("Scheduled");
			headerList.add("CheckIn");
			headerList.add("CheckOut");
			headerList.add("FeedBack");		
			headerList.add("Cancelled");
			headerList.add("Inquiry");
			headerList.add("Missed calls");
		}
		
		File reportFile = myReport.createExcel(fileName, headerList, downloadList, flag);
		
		downloadTracking = new FileInputStream(reportFile);
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return downloadTracking;
}


public static void main (String[] arg){
	
	
	System.out.println(isDate1GreaterThanOrEqualToDate2("1457597927000","1357597977000"));
}

public static boolean isDate1GreaterThanOrEqualToDate2(String dateinmill1, String dateinmill2){
	Date date1 =null;
	Date date2 =null;
	if(dateinmill1!=null && dateinmill2!=null){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(Long.valueOf(dateinmill1));
		date1 = calendar.getTime();
	//	System.out.println(date1);
		calendar.setTimeInMillis(Long.valueOf(dateinmill2));
		date2 = calendar.getTime();
	//	System.out.println(date2);
	//	System.out.println(date1.compareTo(date2));
	}
	
	if(date1.compareTo(date2)>=0)
		return true;
	else 
		return false;
}




















	
}
