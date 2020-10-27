package com.easyfix.Jobs.delegate;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;



















import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.format.CellDateFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.util.Log;

import com.easyfix.Jobs.dao.JobDao;
import com.easyfix.Jobs.dao.JobImageDao;
import com.easyfix.Jobs.model.JobImage;
import com.easyfix.Jobs.model.JobSelectedServices;
import com.easyfix.Jobs.model.Jobs;
import com.easyfix.Jobs.model.Notification;
import com.easyfix.Jobs.model.Recipients;
import com.easyfix.Jobs.model.Sms;
import com.easyfix.Jobs.model.email;
import com.easyfix.clients.model.Clients;
import com.easyfix.customers.model.Address;
import com.easyfix.customers.model.Customers;
import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.geocode.distance.ResultObject;
import com.easyfix.geocode.latlong.Latlong;
import com.easyfix.settings.model.City;
import com.easyfix.settings.model.ServiceType;
import com.easyfix.util.MailSender;
import com.easyfix.util.PropertyReader;
import com.easyfix.util.RestClient;
import com.easyfix.util.SmsSender;
import com.easyfix.util.UtilityFunctions;
import com.easyfix.util.emailSender;
import com.easyfix.util.scheduling.CalculateEasyfixerRating;
import com.easyfix.util.scheduling.RateCardCalculations;
import com.easyfix.util.scheduling.RateCardCalculationsImpl;
import com.easyfix.util.utilityFunction.delegate.EasyfixerRatingParametersService;
import com.easyfix.util.utilityFunction.model.EasyfixerRatingParameters;


public class JobServiceImpl implements JobService{
	private static final Logger logger = LogManager.getLogger(JobServiceImpl.class);
	private JobDao jobDao;
	private JobImageDao jobImageDao;
	private Latlong latLong;
	private CalculateEasyfixerRating calculateEasyfixerRating;
	private EasyfixerRatingParametersService easyfixerRatingParametersService;  
				//for getting parameters weightage
	public static AtomicReference<List<Sms>> cache= new AtomicReference<List<Sms>>();
	public static AtomicReference<Long> time= new AtomicReference<Long>(0l);
	
	public static volatile Map<Integer,List<Sms>> smsCacheMap;
	public static  List<Sms> smsCacheList =  new ArrayList<Sms>();
	
	@Override
	public void JobApproveDetails(List<JobSelectedServices> JobSelectedServicesList,Jobs job) throws Exception {
		jobDao.JobApproveDetails(JobSelectedServicesList,job);
	}
	
	@Override
	public void UpdateJobServiceDetails(List<JobSelectedServices> JobSelectedServicesList) throws Exception {
		jobDao.UpdateJobServiceDetails(JobSelectedServicesList);
	}
	
	@Override
	public List<JobImage> jobImageList(JobImage jobImage) throws Exception{
		List<JobImage> list= jobImageDao.getJobImage(jobImage);
		return list;
 	}
	@Override
	public void addJobImage(JobImage jobImage) throws Exception{
		jobImageDao.addJobImage(jobImage);
	}
	
	@Override
	public void autoJobSchedule(int jobId) throws Exception{
		Jobs job = getJobDetails(jobId);
		
		scheduleTask(job);
		if(job.getJobStatus()==1){
			logger.info("automatic scheduling successfull for job"+job.getJobId());
		}
		else{
			logger.info("failed : automatic scheduling for job"+job.getJobId());
		}
	/*	WebTarget target = new RestClient().apiResponse();
		Jobs jobFromDB= getJobDetails(jobId);
		int clientId= jobFromDB.getFkClientId();
		
		WebTarget autoScheduleTarget = target.path("jobs/listEfr");
		if(jobId!=0){
			autoScheduleTarget = autoScheduleTarget.queryParam("id",jobId);
		}
		//String jsonResult = autoScheduleTarget.request().accept(MediaType.APPLICATION_JSON).get(String.class);
		int responseStatus= autoScheduleTarget.request().header("clientId", clientId).accept(MediaType.APPLICATION_JSON).get().getStatus();
		logger.info("auto scheduling response status from API "+responseStatus);
		if(responseStatus==200){
			Jobs j = getJobDetails(jobId);
			
			if(j.getJobStatus()==1){
				logger.info("sending mail to onwer for successfull scheduling");
				String content = "job :"+jobId+" is successfuly automtically scheduled.";
				String subject = "job:"+jobId+" auto scheduled";
			MailSender.email(j.getOwner().getOfficialEmail(), content, subject, new PropertyReader(), null);
			}
			else{
				logger.info("sending mail to onwer for failed  auto scheduling");
				String content = "job :"+jobId+" could not be scheduled automatically.please schedule yourself.";
				String subject = "job:"+jobId+" Action Required: Scheduling";
			MailSender.email(j.getOwner().getOfficialEmail(), content, subject, new PropertyReader(), null);
			}
		}
		//	System.out.println(jsonResult);
		
		*/
	}
	@Override
	public void getSmsDetails() throws Exception {
		long currentTime= System.currentTimeMillis();

		if(currentTime-time.get()>(10*60*1000)){ //24*60*60*1000

		System.out.println("in getSmsDetails getting sms from db");
	//		System.out.println(currentTime);
	//		System.out.println(time.get().toString());
			smsCacheList =  jobDao.getSmsDetails();
	//		System.out.println(smsCacheList);
			cache.set(smsCacheList);
			time.set(currentTime);
			sortSms();
			logger.info("smsCacheList loaded");
			System.out.println("smsCacheList loaded");
			
		}
	
		smsCacheList= cache.get();
	}
	
	
	public Map<Integer, List<Sms>> sortSms() throws Exception{
		smsCacheMap  = new HashMap<Integer,List<Sms>>();
		for(Sms s : smsCacheList){
			if(smsCacheMap.containsKey(s.getClientId())){
				List<Sms> locallist = smsCacheMap.get(s.getClientId());
				locallist.add(s);
				smsCacheMap.put(s.getClientId(), locallist);
			}
			else{
				List<Sms> locallist  = new ArrayList<Sms>();
				locallist.add(s);
				smsCacheMap.put(s.getClientId(), locallist);
			}
		}
		return smsCacheMap;
	}
	@Override
	public void sendSms(int clientId, String stage, int receiver, int sender,String mobileNo,Jobs job) throws Exception{
//		List<Sms> list = getSmsDetails();
		getSmsDetails();
		 
	//	System.out.println("in sendSms size:"+smsCacheList.size()+smsCacheList);
	//	System.out.println("in sendSms :"+"key size: "+smsCacheMap.keySet().size()+"::"+smsCacheMap);
		List<Sms> smsTobeSent;// = new ArrayList<Sms>();
		try{
		if(smsCacheMap.containsKey(clientId)){
			smsTobeSent = smsCacheMap.get(clientId);
		
		for(Sms s:smsTobeSent){
			if(s.getClientId()==clientId && s.getJobStage().equalsIgnoreCase(stage) && s.getReceiver()==receiver
					&& s.getSender()==sender){
				if(receiver==1 && mobileNo!=null){
					SmsSender.sendSms(modifySmsToCustomer(s.getSms(),job),mobileNo);
					System.out.println(modifySmsToCustomer(s.getSms(),job)+" was sent to "+mobileNo);
					logger.info(modifySmsToCustomer(s.getSms(),job)+" was sent to customer "+mobileNo+" at stage "+stage);
				}
				else if(receiver==3 && mobileNo!=null){
					SmsSender.sendSms(modifySmsToEfr(s.getSms(),job),mobileNo);
					System.out.println(modifySmsToEfr("receiver==3"+s.getSms(),job)+" was sent to "+mobileNo);
					logger.info(modifySmsToEfr(s.getSms(),job)+" was sent to efr "+mobileNo+" at stage "+stage);
				}
			//logger.info("sms sent to "+mobileNo+" at stage "+stage);
			}
		}
		
		}
		else
			sendDefaultSms(stage,receiver,  sender, mobileNo,job);
	//	SmsSender.sendSms(smsTobeSent.get(0).getSms(),mobileNo);
		}
		catch(Exception e){
			e.printStackTrace();
		//	logger.catching(e);
		}
		}
	
	public void sendDefaultSms(String stage, int receiver, int sender,String mobileNo,Jobs job) {
		List<Sms> smsTobeSent=smsCacheMap.get(0);
		try{
		for(Sms s:smsTobeSent){
			if(s.getJobStage().equalsIgnoreCase(stage) && s.getReceiver()==receiver && 
					s.getSender()==sender){
				if(receiver==1 && mobileNo!=null){
					SmsSender.sendSms(modifySmsToCustomer(s.getSms(),job),mobileNo);
					logger.info(modifySmsToCustomer(s.getSms(),job)+" was sent to customer "+mobileNo+" at stage "+stage);
					System.out.println("receiver==1"+modifySmsToCustomer(s.getSms(),job)+" ::default msg was sent to "+mobileNo);
				}
				else if(receiver==3 && mobileNo!=null){
					SmsSender.sendSms(modifySmsToEfr(s.getSms(),job),mobileNo);
					logger.info(modifySmsToEfr(s.getSms(),job)+" was sent to efr "+mobileNo+" at stage "+stage);
					System.out.println("receiver==3"+modifySmsToEfr(s.getSms(),job)+" ::default msg was sent to "+mobileNo);
				}
			//	SmsSender.sendSms(s.getSms(),mobileNo);
				logger.info("default sms sent to "+mobileNo+" at stage "+stage);
			//	System.out.println(s.getSms()+" ::default sms sent to "+mobileNo+" at stage "+stage);
			}
		}
		}
		catch(Exception ex){
		//	logger.catching(ex);
			ex.printStackTrace();
		}
	}
	
	@Override
	public String modifySmsToCustomer(String sms,Jobs job) throws Exception{
		String localsms = sms;
		//StringBuilder originalSms = new StringBuilder();
		if(localsms.contains("<customer_first_name>"))
			localsms = localsms.replaceAll("<customer_first_name>", job.getCustomerObj().getCustomerName());
		if(localsms.contains("<easyFixer_name>"))
			localsms = localsms.replaceAll("<easyFixer_name>", job.getEasyfixterObj().getEasyfixerName());
		if(localsms.contains("<efr_rating_by_customer>"))
			localsms = localsms.replaceAll("<efr_rating_by_customer>",job.getCustomerFeedback().getEfrRating()+"");
		if(localsms.contains("<requested_date_time>"))
			localsms = localsms.replaceAll("<requested_date_time>",job.getRequestedDateTime());
		if(localsms.contains("<service_charge>"))
			localsms = localsms.replaceAll("<service_charge>",job.getTotalServiceAmount());
		if(localsms.contains("<material_charge>"))
			localsms = localsms.replaceAll("<material_charge>",job.getMaterialCharge());
		if(localsms.contains("<service_type>"))
			localsms = localsms.replaceAll("<service_type>",job.getServiceTypeObj().getServiceTypeName());
		if(localsms.contains("<client_name>"))
			localsms = localsms.replaceAll("<client_name>",job.getClientObj().getClientName());
		if(localsms.contains("<otp>"))
			localsms = localsms.replaceAll("<otp>",job.getOtp());
		
		if(localsms.contains("<warranty_expiry_date>")){
			int warrentyDays=0;
			if(localsms.contains("days")){
				int position = localsms.indexOf("days");
				String s = localsms.substring(position-3,position).trim();
				try{
					if (s!=null){
						warrentyDays= Integer.parseInt(s);
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			localsms = localsms.replaceAll("<warranty_expiry_date>",UtilityFunctions.addDays(new Date(), warrentyDays));
		}
		
		//	System.out.println(localsms);efr_rating_by_customer
		return localsms;
	}
	public String modifySmsToEfr(String sms,Jobs job){
		String localsms = sms;
		//StringBuilder originalSms = new StringBuilder();
		if(localsms.contains("<efr_name>"))
			localsms = localsms.replaceAll("<efr_name>", job.getEasyfixterObj().getEasyfixerName());
		
		if(localsms.contains("<job_id>"))
			localsms = localsms.replaceAll("<job_id>", job.getJobId()+"");
		
		if(localsms.contains("<customer_first_name>"))
			localsms = localsms.replaceAll("<customer_first_name>", job.getCustomerObj().getCustomerName());
		
		if(localsms.contains("<requested_date_time>"))
		localsms = localsms.replaceAll("<requested_date_time>", job.getRequestedDateTime());

		if(localsms.contains("<service_charge>"))
			localsms = localsms.replaceAll("<service_charge>", job.getEasyfixerAmount());
	
		if(localsms.contains("<material_charge>"))
			localsms = localsms.replaceAll("<material_charge>", job.getMaterialCharge());
		
		if(localsms.contains("<service_type>"))
			localsms = localsms.replaceAll("<service_type>", job.getServiceTypeObj().getServiceTypeName());
	
		if(localsms.contains("<address>"))
			localsms = localsms.replaceAll("<address>", job.getAddressObj().getAddress()+","+job.getAddressObj().getCityObj().getCityName());
	//land mark not replaced
		if(localsms.contains("<landmark>"))
			localsms = localsms.replaceAll("<landmark>", "");
	
		if(localsms.contains("<efr_Rating_by_customer>"))
			localsms = localsms.replaceAll("<efr_Rating_by_customer>",job.getCustomerFeedback().getEfrRating()+"" );
		
		if(localsms.contains("<customer_happy_with_efr>"))
			localsms = localsms.replaceAll("<customer_happy_with_efr>",job.getHappyWithService());
		
		if(localsms.contains("<current_balance>"))
			localsms = localsms.replaceAll("<current_balance>",job.getEasyfixterObj().getCurrentBalance()+"");
		
		if(localsms.contains("<reason_for_cancellation>"))
			localsms = localsms.replaceAll("<reason_for_cancellation>",job.getEnumDesc());
		
		if(localsms.contains("<efr_rating>"))
			localsms = localsms.replaceAll("<efr_rating>",getAvgEfrRatingByCustomer(job.getFkEasyfixterId()));
		//	System.out.println(localsms);<customer_mobile_no>
		if(localsms.contains("<customer_mobile_no>"))
			localsms = localsms.replaceAll("<customer_mobile_no>",job.getCustomerObj().getCustomerMobNo());
		
		if(localsms.contains("<efr_app_login_password>"))
			localsms = localsms.replaceAll("<efr_app_login_password>",job.getEasyfixterObj().getAppLoginPassword());
		
		
		return localsms;
	}

	public String getAvgEfrRatingByCustomer(int efrId){
		Easyfixers e=null;
		try {
			if(efrId!=0)
			e = jobDao.getAvgEfrRatingByCustomer(efrId);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return e.getCustomerRating();
	}
	@Override
	public Jobs getJobDetails(int jobId) throws Exception {
		return jobDao.getJobDetails(jobId);
	}


	@Override
	public String addUpdateJob(Jobs jobsObj, List<String> list, int userId) throws Exception {
		return jobDao.addUpdateJob(jobsObj,list, userId);
	}

	@Override
	public String convertStringToDate(String dateString) throws Exception {
		String formatteddate = null;
	    DateFormat rdf = new SimpleDateFormat( "dd MMM yyyy - HH:mm");
	    DateFormat wdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date date = null;
	    try {
	    	date = rdf.parse(dateString);
	     } catch ( ParseException e ) {
	         e.printStackTrace();
	         logger.catching(e);
	     }
	    
	    if( date != null ) {
	    	formatteddate = wdf.format( date );
	        }
	    
	    return formatteddate;
	}
	
	@Override
	public String convertStringToSimpleDate(String dateString) throws Exception {
		String formatteddate = null;
	    DateFormat rdf = new SimpleDateFormat( "dd MMM, yyyy");
	    DateFormat wdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = null;
	    try {
	    	date = rdf.parse(dateString);
	     } catch ( ParseException e ) {
	         e.printStackTrace();
	  //       logger.catching(e);
	     }
	    
	    if( date != null ) {
	    	formatteddate = wdf.format( date );
	        }
	    
	    return formatteddate;
	}	
public JobDao getJobDao() {
		
		return jobDao;
	}

	public void setJobDao(JobDao jobDao) {
		this.jobDao = jobDao;
	}

	public String convertDateToString(String dateString) throws Exception {
		String formatteddate = null;
		DateFormat wdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    DateFormat rdf = new SimpleDateFormat( "dd MMM yyyy - hh:mm a");	    
	    Date date = null;
	    if(dateString == null || dateString =="") {
	    	formatteddate = "NA";	    
	    }
	    else {
		    try {
		    	date = wdf.parse(dateString);
		     } catch ( ParseException e ) {
		    	 logger.catching(e);
		     }
		    
		    if( date != null ) {
		    	formatteddate = rdf.format( date );
		     }
	    }
	    
	    return formatteddate;
	}
	
	
/*public static void main(String[] args)throws Exception{
		
		JobServiceImpl s =new JobServiceImpl();
		Date today = new Date();
		String dd = s.convertStringToDate("10 March 2016 - 00:05");
		String ddd = s.convertDateToString("2016-03-10 00:05:00");
		System.out.println(dd);
		System.out.println(ddd);
		
	}*/
	

	@Override
	public List<Jobs> getJobList(int flag) throws Exception {
		return jobDao.getJobList(flag);
	}

	@Override
	public List<Easyfixers> getEasyfixerListByJobTypeId(int jobId, int jobtypeid, int client_id) throws Exception {
		return jobDao.getEasyfixerListByJobTypeId(jobId, jobtypeid, client_id);
		
	}


	@Override
	public List<Easyfixers> getEasyfixerDistanceFromJobLocation(
			List<Easyfixers> l, String CustomerGPS, String maxDistance) throws Exception {
		List<Easyfixers> efrListWithLinearDis = new ArrayList<Easyfixers>();
		//Map<Easyfixers,Double> easyfixerDistance= new HashMap<Easyfixers, Double>();
		try{
		String customerGPS =CustomerGPS;
		for(Easyfixers e:l){
			try{
			double dis = calculateLinearDistance(customerGPS,e.getEasyfixerBaseGps()); //get linear distance first
			if(dis<=Integer.parseInt(maxDistance)) 
				{
				e.setLinearDistanceFromCustomer(dis);
				efrListWithLinearDis.add(e);
				}
			}
			catch(Exception ex){
				logger.info(ex.toString());
				ex.printStackTrace();
			}
		}
		
		 Collections.sort(efrListWithLinearDis, new sortListByLinearDistance(efrListWithLinearDis)); //sortByValue(efrListWithLinearDis); //sort map by distance
		
		//System.out.println("ingetEasyfixerDistanceFromJobLocation "+easyfixerDistance);
		}
		catch(Exception e){
		//System.out.println("ingetEasyfixerDistanceFromJobLocation"+e);	
		logger.info(e.toString());
		}
		return efrListWithLinearDis;
	}
	
	public Double calculateLinearDistance(String cusgps, String EasyFixergps) throws Exception{
		
		//String s1 = cusgps.substring(1,cusgps.length()-1);
		String s1 = cusgps;
		String part1[] = s1.split(",");
		
		Double customerLat = Double.valueOf(part1[0].trim());
		Double customerLong = Double.valueOf(part1[1].trim());
		
		//String s2 = EasyFixergps.substring(1, EasyFixergps.length()-1);
		String s2 = EasyFixergps;
		String part2[] = s2.split(",");
		Double easyfixerLat = Double.valueOf(part2[0].trim()); Double easyfixerLong = Double.valueOf(part2[1].trim());
		
		return (Double)(latLong.getLinearDistance(customerLat, customerLong, easyfixerLat, easyfixerLong));
		
		
	}

	public static Map<Easyfixers, Double> sortByValue(Map<Easyfixers, Double> unsortedMap) {
		Map<Easyfixers, Double> sortedMap = new TreeMap<Easyfixers, Double>(new ValueComparator(unsortedMap));
		sortedMap.putAll(unsortedMap);
		return sortedMap;
	}
	
	
	public static List<Easyfixers> sortListByValue(List<Easyfixers> l) {
		Collections.sort(l,new ValueComparatorforlist(l) );
		
		return l; 
		
	}


	public Latlong getLatLong() {
		return latLong;
	}

	public JobImageDao getJobImageDao() {
		return jobImageDao;
	}
	public void setJobImageDao(JobImageDao jobImageDao) {
		this.jobImageDao = jobImageDao;
	}
	public void setLatLong(Latlong latLong) {
		this.latLong = latLong;
	}
	@Override
	public List<Easyfixers> getSuitableEasyfixersWithoutGoogle(Map<Easyfixers, Double> originalefList, Address addObj) throws Exception {
		List<Easyfixers> eflist = new ArrayList<Easyfixers>();
		//Set<Easyfixers> originalEfSet = originalefList.keySet();
		for(Easyfixers e:originalefList.keySet()){
			double distance =0;
			try{
			 distance = calculateLinearDistance(addObj.getGpsLocation(),e.getEasyfixerBaseGps());
			}
			catch(Exception ex){
			//System.out.println(ex);
			logger.catching(ex);
			}
			e.setDistanceFormCustomer(String.valueOf(distance));
			double MaxDis = Double.valueOf(addObj.getCityObj().getMaxDistance());
			if(distance>MaxDis){
				continue;
			}
			Double distanceParam = ((MaxDis-(distance))*10)/MaxDis;
			e.setDistanceRating(distanceParam.toString());
			List<EasyfixerRatingParameters> paramlist = getParamList();
			BigDecimal index = calculateEasyfixerRating.getRating(e,paramlist);
			eflist.add(e);
		//	System.out.println(e);
		}
		List<Easyfixers> sortedList = sortListByValue(eflist);
		//System.out.println(sortedList);
		return sortedList;
	
	}
		@Override
	public List<Easyfixers> getSuitableEasyfixers(List<Easyfixers> originalefList, Jobs job) throws Exception {
		String cusGps = job.getAddressObj().getGpsLocation();
		//String customerLoc = cusGps.substring(1,cusGps.length()-1);
		//String customerLoc = cusGps;
		//Map<Easyfixers,Double> easyfixerMap = new HashMap<Easyfixers, Double>();
		List<Easyfixers> eflist = new ArrayList<Easyfixers>();
		
		for(Easyfixers e:originalefList ){
			try{
			double distance =e.getLinearDistanceFromCustomer();
//			String easyfixerGps = e.getEasyfixerBaseGps();
//			try{
//			Map<String, ResultObject> m = latLong.getActualDistance(easyfixerGps,cusGps); // get distance from google server
//			ResultObject distanceObj = m.get(easyfixerGps);
//			
//			if(Double.valueOf(distanceObj.getDistance())!=null)
//			 distance = (Double.valueOf(distanceObj.getDistance()))/1000;
//			}
//			catch(Exception ex){
//				ex.printStackTrace();
//			}
			 e.setActualDistanceFromCustomer(distance);
			
			
			//set actual distance for display
			e.setDistanceFormCustomer(String.valueOf(Math.round(distance)));
		
			double MaxDis = Double.valueOf(getMaxDisTravelledByEfr(job));
			//consider only those who are under maxCityDistance(on road)
			if((distance)>MaxDis){
				continue;
			}
			//create distance parameter
			Double distanceParam = ((MaxDis-(distance))*10)/MaxDis;
		//	System.out.println("in jobserviceImpl distanceParam:"+distanceParam);
			//set distance parameter
			e.setDistanceRating(distanceParam.toString());
			// get parameter name and weightage from database
			List<EasyfixerRatingParameters> paramlist = getParamList();
			//calculate ratings
			BigDecimal index = calculateEasyfixerRating.getRating(e,paramlist);
			
			//easyfixerMap.put(e,index );
			
			eflist.add(e);
			}
			catch(Exception ex){
				ex.printStackTrace();
				logger.catching(ex);
			}
		}
		
		//sort map by value
		//Map<Easyfixers, Double> sortedMap = sortByValue(easyfixerMap);
		//System.out.println(sortedMap);
		//List<Easyfixers> sortedList = new ArrayList<Easyfixers>(sortedMap.keySet());
		
		List<Easyfixers> sortedList = sortListByValue(eflist);
		//System.out.println("in getSuitableEasyfixers"+sortedList);
		return sortedList;
		
	}
	

	@Override
	public List<EasyfixerRatingParameters> getParamList() throws Exception {
		return easyfixerRatingParametersService.getParamList();
	}

	public CalculateEasyfixerRating getCalculateEasyfixerRating() {
		return calculateEasyfixerRating;
	}

	public void setCalculateEasyfixerRating(
			CalculateEasyfixerRating calculateEasyfixerRating) {
		this.calculateEasyfixerRating = calculateEasyfixerRating;
	}


	@Override
	public List<JobSelectedServices> getJobServiceList(int jobId, int serviceStatus) throws Exception {
		return jobDao.getJobServiceList(jobId, serviceStatus);
	}

	@Override
	public int changeRequestedDate(Jobs jobsObj) throws Exception {
		return jobDao.changeRequestedDate(jobsObj);
		
	}


	public EasyfixerRatingParametersService getEasyfixerRatingParametersService() {
		return easyfixerRatingParametersService;
	}

	public void setEasyfixerRatingParametersService(
			EasyfixerRatingParametersService easyfixerRatingParametersService) {
		this.easyfixerRatingParametersService = easyfixerRatingParametersService;
	}

	
	@Override
	public void updateJobServices(int jobId, String clientServiceIds, List<String> serviceList)throws Exception {
		jobDao.updateJobServices(jobId,clientServiceIds,serviceList);
		
	}

	@Override
	public void saveScheduleJob(int jobId, int fkEasyfixterId, int userId,String disTravelled, String flag) throws Exception {
		jobDao.saveScheduleJob(jobId,fkEasyfixterId, userId,disTravelled,flag);
		
	}

	@Override
	public void saveCheckInJob(int jobId, int userId) throws Exception {
	
		jobDao.saveCheckInJob(jobId, userId);
		
	}
	
	
	@Override
	public void saveApproveJob(int jobId) throws Exception {
				
		jobDao.saveApproveJob(jobId);
		
	}
	
	
	@Override
	public void modifyJobFromInvoice(Jobs jobsObj,List<JobSelectedServices> list) throws Exception{

	//	List<JobSelectedServices> jobServiceList = new ArrayList<JobSelectedServices>();
		JobSelectedServices jobSelectObj = null;
		String minEasyfixerFee = "";
		List<EasyfixerRatingParameters> paramlist = getParamList();
		for(EasyfixerRatingParameters e : paramlist){
			if(e.getParamId() == 6){
				minEasyfixerFee = e.getParamWeightage();
			}
		}
		//String[] dataArray = jobsObj.getClientServices().split("~");
		//System.out.println("no of services for jobId : "+jobsObj.getJobId()+" is:"+dataArray.length);
		logger.info("no of services for job id : "+jobsObj.getJobId()+" is  :"+list.size());
		double totalEfrAmount = 0.00; 
		double totalEfAmount = 0.00;
		double totalClientAmount = 0.00;
		String serviceTaxRate = jobDao.getServiceTaxRate();
	//	System.out.println(serviceTaxRate);
		double totalserviceTax = 0.00;
		for(JobSelectedServices s:list){
			
			//System.out.println(i+" inside saveCheckOutJob inside for loop for saving job services for jobID "+jobsObj.getJobId());
			logger.info(s.getJobServiceId()+" inside saveCheckOutJob inside for loop for saving job services for jobID "+jobsObj.getJobId());
			//String[] data = (dataArray[i].trim()).split("#");
			jobSelectObj = new JobSelectedServices();
			try{
				
			jobSelectObj.setJobId(jobsObj.getJobId());
			jobSelectObj.setJobServiceId(s.getJobServiceId());
			Clients clientObj = jobDao.getjobServiceDetailsByJobServiceId(jobSelectObj.getJobServiceId());
			String totalCharge = s.getTotalCharge();
			double individualServiceTax= Double.valueOf(totalCharge)*Double.valueOf(serviceTaxRate)/100;
	//		System.out.println("individualServiceTax"+individualServiceTax);
			String totalChargeAfterTax = String.valueOf((Double.valueOf(totalCharge)-individualServiceTax));
	//		System.out.println("totalChargeAfterTax"+totalChargeAfterTax);
			
			//jobSelectObj.setTotalCharge(totalChargeAfterTax);
			jobSelectObj.setTotalChargeAfterTax(totalChargeAfterTax);
			if(Integer.valueOf(totalCharge)<0)
				
			
			
			jobSelectObj.setTotalCharge(totalCharge);
			
			RateCardCalculations r = new RateCardCalculationsImpl.CalculatorBuilder(jobSelectObj.getTotalChargeAfterTax())
			.clientfeeFixed(clientObj.getClientFixed()).clientfeeVariable(clientObj.getClientVariable())
			.easyfixFeeFixed(clientObj.getEasyfixDirectFixed()).easyfixFeeVariable(clientObj.getEasyfixDirectVariable())
			.minEasyfixerFee(minEasyfixerFee).overheadFixed(clientObj.getOverheadFixed()).overheadVariable(clientObj.getOverheadVariable()).build();
			
		//	System.out.println("in jobserviceImp "+r);			
			if(s.getIsModifiedFromInvoice()!=1){
					jobSelectObj.setClientCharge(r.getClientShare().toString());
					jobSelectObj.setEasyfixCharge(r.getEasyfixShare().toString());
					jobSelectObj.setEasyfixerCharge(r.getEasyfixerShare().toString());
			}
			else
			{
				jobSelectObj.setClientCharge(s.getClientCharge().toString());
				jobSelectObj.setEasyfixCharge(s.getEasyfixCharge().toString());
				jobSelectObj.setEasyfixerCharge(s.getEasyfixerCharge().toString());
			}
				
			
			
			totalEfrAmount = totalEfrAmount + Double.parseDouble(jobSelectObj.getEasyfixerCharge());
			totalEfAmount = totalEfAmount + Double.parseDouble(jobSelectObj.getEasyfixCharge());
			totalClientAmount = totalClientAmount + Double.parseDouble(jobSelectObj.getClientCharge());
			totalserviceTax = totalserviceTax+individualServiceTax;
			}
			catch(Exception e){
				e.printStackTrace();
				logger.catching(e);
			}
			jobDao.saveJobServiceAmount(jobSelectObj);
			logger.info("jobDao.saveJobServiceAmount done for job "+jobsObj.getJobId());
						
			//jobServiceList.add(jobSelectObj);
		}
		jobsObj.setEasyfixerAmount(Double.toString(totalEfrAmount));
		jobsObj.setEasyfixAmount(Double.toString(totalEfAmount));
		jobsObj.setClientAmount(Double.toString(totalClientAmount));
		jobsObj.setTotalServiceTax(String.valueOf(totalserviceTax));
	//	System.out.println("total service tax" +jobsObj.getTotalServiceTax());
		jobDao.updateTransactionFromInvoice(jobsObj);
		logger.info("jobDao.saveCheckOutJob done for job "+jobsObj.getJobId());
		//jobDao.updateEasyfixerAccount***Not_Use(jobsObj);	
	}
	@Override
	public void saveCheckOutJob(Jobs jobsObj, List<JobSelectedServices> jobServiceList) throws Exception {
	
		JobSelectedServices jobSelectObj = null;
		String minEasyfixerFee = "";
		List<EasyfixerRatingParameters> paramlist = getParamList();
		for(EasyfixerRatingParameters e : paramlist){
			if(e.getParamId() == 6){
				minEasyfixerFee = e.getParamWeightage();
			}
		}
		//System.out.println("no of services for jobId : "+jobsObj.getJobId()+" is:"+dataArray.length);
		logger.info("no of services for job id : "+jobsObj.getJobId()+" is  :"+jobServiceList.size());
		double totalEfrAmount = 0.00; 
		double totalEfAmount = 0.00;
		double totalClientAmount = 0.00;
		String serviceTaxRate = jobDao.getServiceTaxRate();
	//	System.out.println(serviceTaxRate);
		double totalserviceTax = 0.00;
		for(int i=0; i<jobServiceList.size(); i++){
			
			//System.out.println(i+" inside saveCheckOutJob inside for loop for saving job services for jobID "+jobsObj.getJobId());
			logger.info(i+" inside saveCheckOutJob inside for loop for saving job services for jobID "+jobsObj.getJobId());
			jobSelectObj = new JobSelectedServices();
			try{
			jobSelectObj.setJobId(jobsObj.getJobId());
			jobSelectObj.setJobServiceId(jobServiceList.get(i).getJobServiceId());
			Clients clientObj = jobDao.getjobServiceDetailsByJobServiceId(jobSelectObj.getJobServiceId());
			String totalCharge = jobServiceList.get(i).getTotalCharge();
			double individualServiceTax= Double.valueOf(totalCharge)*Double.valueOf(serviceTaxRate)/100;
	//		System.out.println("individualServiceTax"+individualServiceTax);
			String totalChargeAfterTax = String.valueOf((Double.valueOf(totalCharge)-individualServiceTax));
	//		System.out.println("totalChargeAfterTax"+totalChargeAfterTax);
			
			//jobSelectObj.setTotalCharge(totalChargeAfterTax);
			jobSelectObj.setTotalChargeAfterTax(totalChargeAfterTax);
			jobSelectObj.setTotalCharge(totalCharge);
			RateCardCalculations r = new RateCardCalculationsImpl.CalculatorBuilder(jobSelectObj.getTotalChargeAfterTax())
			.clientfeeFixed(clientObj.getClientFixed()).clientfeeVariable(clientObj.getClientVariable())
			.easyfixFeeFixed(clientObj.getEasyfixDirectFixed()).easyfixFeeVariable(clientObj.getEasyfixDirectVariable())
			.minEasyfixerFee(minEasyfixerFee).overheadFixed(clientObj.getOverheadFixed()).overheadVariable(clientObj.getOverheadVariable()).build();
			
		//	System.out.println("in jobserviceImp "+r);			
			
			jobSelectObj.setClientCharge(r.getClientShare().toString());
			jobSelectObj.setEasyfixCharge(r.getEasyfixShare().toString());
			jobSelectObj.setEasyfixerCharge(r.getEasyfixerShare().toString());
			
			totalEfrAmount = totalEfrAmount + Double.parseDouble(jobSelectObj.getEasyfixerCharge());
			totalEfAmount = totalEfAmount + Double.parseDouble(jobSelectObj.getEasyfixCharge());
			totalClientAmount = totalClientAmount + Double.parseDouble(jobSelectObj.getClientCharge());
			totalserviceTax = totalserviceTax+individualServiceTax;
			}
			catch(Exception e){
				e.printStackTrace();
				logger.catching(e);
			}
			jobDao.saveJobServiceAmount(jobSelectObj);
			logger.info("jobDao.saveJobServiceAmount done for job "+jobsObj.getJobId());
						
			//jobServiceList.add(jobSelectObj);
		}
		jobsObj.setEasyfixerAmount(Double.toString(totalEfrAmount));
		jobsObj.setEasyfixAmount(Double.toString(totalEfAmount));
		jobsObj.setClientAmount(Double.toString(totalClientAmount));
		jobsObj.setTotalServiceTax(String.valueOf(totalserviceTax));
	//	System.out.println("total service tax" +jobsObj.getTotalServiceTax());
		jobDao.saveCheckOutJob(jobsObj);
		logger.info("jobDao.saveCheckOutJob done for job "+jobsObj.getJobId());
		//jobDao.updateEasyfixerAccount***Not_Use(jobsObj);
		
	}
	private Object getCellValue(Cell cell) {
	    switch (cell.getCellType()) {
	    case Cell.CELL_TYPE_STRING:
	        return cell.getStringCellValue();
	 
	    case Cell.CELL_TYPE_BOOLEAN:
	        return cell.getBooleanCellValue();
	 
	    case Cell.CELL_TYPE_NUMERIC:
	        return cell.getNumericCellValue();
	    }
	 
	    return null;
	}
	@Override
	public List<Jobs> readBooksFromExcelFile(String excelFilePath) throws IOException {
		   
		List<Jobs> joblist = new ArrayList<Jobs>();
		 try{
	    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	 
	    Workbook workbook = new XSSFWorkbook(inputStream);
	    Sheet firstSheet = workbook.getSheetAt(0);
	    Iterator<Row> iterator = firstSheet.iterator();
	   // System.out.println("no of rows: "+firstSheet.getPhysicalNumberOfRows());
	    outerloop:
	    while (iterator.hasNext()) {
	    	Row  nextRow =  iterator.next();
	    	
	        if(nextRow.getRowNum()==0) // skip 1st row 
	        	continue;
	        if(nextRow.getFirstCellNum()!=0){ // if customer mobile no is not given dont add the job
	        	continue;
	        }
	        Iterator<Cell> cellIterator = nextRow.cellIterator();
	        Jobs jobObj = new Jobs();
	        Customers cusObj = new Customers();
	        Address addObj= new Address();
	        City cityObj = new City();
	        Clients clientObj = new Clients();
	        ServiceType serviceTypeObj= new ServiceType();
	 
	        while (cellIterator.hasNext()) {
	            Cell nextCell = cellIterator.next();
	            
	            int columnIndex = nextCell.getColumnIndex();
	            //firstSheet.getRow(0).getCell(nextCell.getColumnIndex());
	           
	            switch (columnIndex) {
	            case 0:
	            	nextCell.setCellType(Cell.CELL_TYPE_STRING);
	            	try{
	            		String s = (String) getCellValue(nextCell);
	            		if(s !=null && s.trim().length()!=10 ){
	            			//System.out.println("breaking:"+s);
	            			continue outerloop;
	            			
	            		}
	            		
	            	cusObj.setCustomerMobNo((String) getCellValue(nextCell));
	            	}
	            	catch(Exception e){
	            		e.printStackTrace();
	            	//	logger.catching(e);
	            	}
	            	//System.out.println(cusObj.getCustomerMobNo());
	                break;
	              
	            case 1:
	            	nextCell.setCellType(Cell.CELL_TYPE_STRING);
	            	try{
	            	cusObj.setCustomerName((String) getCellValue(nextCell));
	            	}
	            	catch(Exception e){
	            		e.printStackTrace();
	            		//logger.catching(e);
	            	}
	                break;
	            case 2:
	            	nextCell.setCellType(Cell.CELL_TYPE_STRING);
	            	try{
	            	cusObj.setCustomerEmail((String) getCellValue(nextCell));
	            	}
	            	catch(Exception e){
	            		e.printStackTrace();
	            		//logger.catching(e);
	            	}
	                break;
	            case 3:
	            	nextCell.setCellType(Cell.CELL_TYPE_STRING);
	            	try{
	            		String d = (String)getCellValue(nextCell);
	            		clientObj.setClientName(d);
	            	}
	            	catch(Exception e){
	            		e.printStackTrace();
	            		//logger.catching(e);
	            	}
	                break;
	            case 4:
	            	nextCell.setCellType(Cell.CELL_TYPE_STRING);
	            	try{
	            	jobObj.setClientRefId((String)getCellValue(nextCell));
	            	}
	            	catch(Exception e){
	            		e.printStackTrace();
	            		//logger.catching(e);
	            	}
	            	break;
	            case 5:
	            	nextCell.setCellType(Cell.CELL_TYPE_STRING);
	            	try{
	            	String d1 = (String)getCellValue(nextCell);
	            	serviceTypeObj.setServiceTypeName(d1);
	            	}
	            	catch(Exception e){
	            		e.printStackTrace();
	            		//logger.catching(e);
	            	}
	            	break;
	            case 6:
	            	nextCell.setCellType(Cell.CELL_TYPE_STRING);
	            	try{
	            	jobObj.setClientServiceIds((String)getCellValue(nextCell));
	            	}
	            	catch(Exception e){
	            		e.printStackTrace();
	            		//logger.catching(e);
	            	}
	            	break;
	            case 7:
	            	nextCell.setCellType(Cell.CELL_TYPE_STRING);
	            	try{
	            	jobObj.setJobDesc((String)getCellValue(nextCell));
	            	}
	            	catch(Exception e){
	            		e.printStackTrace();
	            		//logger.catching(e);
	            	}
	            	break;
	            case 8:
	            	try{

	            	nextCell.setCellType(Cell.CELL_TYPE_NUMERIC);
	            	double dv = (Double)getCellValue(nextCell);

	            	String strValue="";
	            	if (HSSFDateUtil.isCellDateFormatted(nextCell)) {
	            	    Date date = HSSFDateUtil.getJavaDate(dv);

	            	    String dateFmt = nextCell.getCellStyle().getDataFormatString();
	            	    /* strValue = new SimpleDateFormat(dateFmt).format(date); - won't work as 
	            	    Java fmt differs from Excel fmt. If Excel date format is mm/dd/yyyy, Java 
	            	    will always be 00 for date since "m" is minutes of the hour.*/

	            	    strValue = new CellDateFormatter(dateFmt).format(date); 
	            	    // takes care of idiosyncrasies of Excel
	            	}
	            //	nextCell.setCellValue(new Date());
	            //	nextCell.setCellType(Cell.CELL_TYPE_STRING);
	            //	Date date = (Date)getCellValue(nextCell);
	            	
	            	jobObj.setRequestedDateTime(strValue);
	            	}
	            	catch(Exception e){
	            		//logger.catching(e);
	            		e.printStackTrace();
	            	}
	                break;
	            case 9:
	            	nextCell.setCellType(Cell.CELL_TYPE_STRING);
	            	try{
	            	addObj.setAddress((String)getCellValue(nextCell));
	            	}
	            	catch(Exception e){
	            		e.printStackTrace();
	            		//logger.catching(e);
	            	}
	                break;
	            case 10:
	            	nextCell.setCellType(Cell.CELL_TYPE_STRING);
	            	try{
	            	
	            	cityObj.setCityName((String)getCellValue(nextCell));
	            	}catch(Exception e){
	            		e.printStackTrace();
	            		//logger.catching(e);
	            	}
	                break;
//	            case 11:
//	            	nextCell.setCellType(Cell.CELL_TYPE_STRING);
//	            	try{
//	            	addObj.setGpsLocation((String)getCellValue(nextCell));
//	            	}catch(Exception e){
//	            		e.printStackTrace();
//	            	}
//	                break;
	            case 11:
	            	nextCell.setCellType(Cell.CELL_TYPE_STRING);
	            	try{
	            	addObj.setPinCode((String)getCellValue(nextCell));
	            	}catch(Exception e){
	            		e.printStackTrace();
	            		//logger.catching(e);
	            	}
	                break;
	            case 12:
	            	nextCell.setCellType(Cell.CELL_TYPE_NUMERIC);
	            	try{
	            		Double d2 = (Double)getCellValue(nextCell);
		            	jobObj.setJobOwner(d2.intValue());
	            	}catch(Exception e){
	            	//	logger.catching(e);
	            		e.printStackTrace();
	            	}
	                break;
	            }
	            
	           
	        }
	        
	        
	        addObj.setCityObj(cityObj);
	        jobObj.setServiceTypeObj(serviceTypeObj);
            jobObj.setCustomerObj(cusObj);
	        jobObj.setAddressObj(addObj);
	        jobObj.setClientObj(clientObj);
	        joblist.add(jobObj);
	    }
	 
	     workbook.close();
	    inputStream.close();
		 }
		 catch(Exception e){
			 logger.catching(e);
		 }
	    return joblist;
	}
	
	
	
	public long dateTimeDiff(String dateStart) throws Exception {
		long diffMinutes = 0;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date d1 = null;
		Date d2 = null;
		Date date = new Date();

		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(format.format(date));

			//in milliseconds
			long diff = d1.getTime() - d2.getTime();

			//long diffSeconds = diff / 1000;
			diffMinutes = diff / 60000;
			//long diffHours = diff / (60 * 60 * 1000) % 24;
			//long diffDays = diff / (24 * 60 * 60 * 1000);

			/*System.out.print(diffDays + " days, ");
			System.out.print(diffHours + " hours, ");
			System.out.print(diffMinutes + " minutes, ");
			System.out.print(diffSeconds + " seconds.");*/

		} catch (Exception e) {
			logger.catching(e);
		}

		
		return diffMinutes;
	}
	
	
	

	@Override
	public void saveJobComment(Jobs jobsObj) throws Exception {
		jobDao.saveJobComment(jobsObj);
		
	}

	@Override
	public List<Jobs> jobCommentList(int jobId) throws Exception {
		return jobDao.jobCommentList(jobId);
	}

	@Override
	public void saveFeedbackJob(Jobs jobsObj) throws Exception {
		jobDao.saveFeedbackJob(jobsObj);
		
	}

	@Override
	public List<Jobs> getEnumReasonList(int enumType) throws Exception {
		return jobDao.getEnumReasonList(enumType);
	}

	@Override
	public void saveCancelJob(Jobs jobsObj) throws Exception {
		jobDao.saveCancelJob(jobsObj);
		
	}

	@Override
	public void updateJobOwner(Jobs jobsObj) throws Exception {
		jobDao.updateJobOwner(jobsObj);
		
	}

	@Override
	public void recordEfrJobRejections(Jobs jobObj) throws Exception {
		jobDao.recordEfrJobRejections(jobObj);
		
	}

	@Override
	public List<Jobs> getEfrRejectReasonList() throws Exception {
		return jobDao.getEfrRejectReasonList();
		
	}

	@Override
	public List<Jobs> getJobListByFilter(Jobs jobsObj) throws Exception {
		return jobDao.getJobListByFilter(jobsObj);
	}

	@Override
	public int addJobFromExcel(Jobs j) throws Exception{
		return jobDao.addJobFromExcel(j);
	}

	@Override
	public Jobs getLeadJobDetails(int jobId) throws Exception {
		return jobDao.getLeadJobDetails(jobId);
	}

	@Override
	public void updateLeadJob(int jobId, int status) throws Exception {
		jobDao.updateLeadJob(jobId,status);
		
	}

	@Override
	public List<Jobs> getCustomerJobList(int fkCustomerId) throws Exception {
		return jobDao.getCustomerJobList(fkCustomerId);
	}

	@Override
	public List<Jobs> getjobListForChangeOwner(int userId, int roleId)	throws Exception {
		return jobDao.getjobListForChangeOwner(userId, roleId);
	}

	@Override
	public int addUpdateCallLaterJob(Jobs jobsObj, List<String> list, int userId) throws Exception {
		return jobDao.addUpdateCallLaterJob(jobsObj, list, userId);
	}

	@Override
	public List<Jobs> getJobListByStatus(int jobStatus, int userId) throws Exception {
		return jobDao.getJobListByStatus(jobStatus,userId);
	}


	@Override
	public void updateRescheduleJob(Jobs jobsObj) throws Exception {
		 jobDao.updateRescheduleJob(jobsObj);
		
	}


	@Override
	public boolean confirmApprovejob(Jobs job) throws Exception {
		Jobs jobFromDB=null;
		if(job.getJobId()!=0){
			jobFromDB = getJobDetails(job.getJobId());
			jobFromDB.setMaterialCharge(job.getMaterialCharge());
			jobFromDB.setTotalServiceAmount(job.getTotalServiceAmount());
		}
		if(jobFromDB!=null){
		List<email> clientEmailList = jobDao.getEmailByClientJobStage(jobFromDB.getFkClientId(),"approval");
		List<Recipients> clientRecipientsList = jobDao.getClientRecipientListForSmsEmail(jobFromDB.getFkClientId());
		try{
		for(Recipients r:clientRecipientsList){
			if(r.getRecipientType().equalsIgnoreCase("approval")){
				for(email e:clientEmailList){
					//emailSender emailSender = new emailSender();
					MailSender.email(r.getEmail(),modifyEmailToClient(e.getEmailText(),jobFromDB,r), modifyEmailSubjectToCliet(e.getSubject(),jobFromDB),  new PropertyReader(),null);
					//emailSender.email(r.getEmail(),"kundan.kumar2@channelplay.in", modifyEmailSubjectToCliet(e.getSubject(),jobFromDB), modifyEmailToClient(e.getEmailText(),jobFromDB,r));
		//	System.out.println(modifyEmailToClient(e.getEmailText(),jobFromDB,r)+" email was sent to "+r.getContactName());
			}
			}
		}
	}catch(Exception e){
		e.printStackTrace();
	}
		}
		
		return true;
	}
	public String modifyEmailSubjectToCliet(String subject, Jobs job){
		String localsub = null;
		if(subject!=null){
			if(subject.contains("#client_reference_id#")){
				if(job.getClientRefId()!=null)
				localsub = subject.replace("#client_reference_id#", job.getClientRefId());
				else
					localsub = subject.replace("#client_reference_id#", "");
			}if(subject.contains("#job_id#"))
				localsub = localsub.replace("#job_id#", job.getJobId()+"");
		}
		System.out.println("sub:"+localsub);
		return localsub;
	}
	
	public String modifyEmailToClient(String email, Jobs job, Recipients r){
		String localEmail =null;
		if(email!=null){
			if(email.contains("#name#")) 
				localEmail = email.replace("#name#", r.getContactName());
			if(email.contains("#customer_first_name#")) 
				localEmail = localEmail.replace("#customer_first_name#", job.getCustomerObj().getCustomerName());
			if(email.contains("#service_type#")) 
				localEmail = localEmail.replace("#service_type#", job.getServiceTypeObj().getServiceTypeName());
			
			if(email.contains("#address#")) 
				localEmail = localEmail.replace("#address#", job.getAddressObj().getAddress()+","+job.getAddressObj().getCityObj().getCityName());
			
			if(email.contains("#total_charge#")) 
				localEmail = localEmail.replace("#total_charge#",String.valueOf((Integer.valueOf(job.getTotalServiceAmount())+Integer.valueOf(job.getMaterialCharge()))));
			
			if(email.contains("#material_charge#")) 
				localEmail = localEmail.replace("#material_charge#", job.getMaterialCharge());
			if(email.contains("#service_charge#")) 
				localEmail = localEmail.replace("#service_charge#", job.getTotalServiceAmount());
			if(email.contains("#job_id#")) 
				localEmail = localEmail.replace("#job_id#", job.getJobId()+"");
			if(email.contains("#client_reference_id#")){
				if(job.getClientRefId()!=null)
				localEmail = localEmail.replace("#client_reference_id#", job.getClientRefId());
				else
					localEmail = localEmail.replace("#client_reference_id#","");
			}
			}
		System.out.println("mail: "+localEmail);
		return localEmail;
	}


	@Override
	public List<Recipients> getClientRecipientListForSmsEmail(int clinetId)throws Exception {
		return jobDao.getClientRecipientListForSmsEmail(clinetId);
	}
	
	public List<Easyfixers> getSortedEfrRoadDistanceFromJobLocation(
			List<Easyfixers> sortedList, String CustomerGPS,int jobId) throws Exception {
		List<Easyfixers> sortedListByRoadDistance=null;
		try{
		if(sortedList!=null && sortedList.size()>5){
			sortedListByRoadDistance=sortedList.subList(0,5);
		}
		else{
			sortedListByRoadDistance=sortedList;
		}
		logger.info("calculating road dis for: "+sortedListByRoadDistance.size()+" efrs for jobid "+jobId);
		for(Easyfixers e:sortedListByRoadDistance){
			String easyfixerGps = e.getEasyfixerBaseGps();
			double distance =0;
			try{
				Map<String, ResultObject> m = latLong.getActualDistance(easyfixerGps,CustomerGPS); // get distance from google server
				ResultObject distanceObj = m.get(easyfixerGps);
				
				if(Double.valueOf(distanceObj.getDistance())!=null)
				 distance = (Double.valueOf(distanceObj.getDistance()))/1000;
				e.setRoadDistanceFromCustomer(distance);
				}
				catch(Exception ex){
					logger.catching(ex);
					ex.printStackTrace();
				}
		}
		sortedListByRoadDistance.sort(new Comparator<Easyfixers>(){
			@Override
			public int compare(Easyfixers e1, Easyfixers e2){
				double valuee1 = Double.valueOf(e1.getRoadDistanceFromCustomer());
				double valuee2 = Double.valueOf(e2.getRoadDistanceFromCustomer());
				//return valueB.compareTo(valueA);
		       if (valuee1 >= valuee2) {
		           return 1;
		       } else {
		           return -1;
		       }
				
	}
	});
	System.out.println("------------after road dis sorting-------------");
	System.out.println("sortedListByRoadDistance"+sortedListByRoadDistance);
	logger.info("road distance calculated for "+sortedListByRoadDistance.size()+" efrs.");
		}
		catch(Exception e){
			e.printStackTrace();
			logger.catching(e);
		}
		return sortedListByRoadDistance;
	}
	public String getMaxDisTravelledByEfr(Jobs jobsObj ){
		String maxDis;
		String cityDis = jobsObj.getAddressObj().getCityObj().getMaxDistance();
		int clientDis =jobsObj.getClientObj().getTravelDist();
		int cityDisInt = Integer.valueOf(cityDis);
		int clientId = jobsObj.getFkClientId(); // skip for retail
		//if(clientId==1)
			//	maxDis= cityDis;
		//else{
				if(clientDis>cityDisInt)
						maxDis= String.valueOf(clientDis);
				else 
						maxDis = cityDis;
		//	}
		return maxDis;
	}
	public Jobs scheduleTask(Jobs job) throws Exception, InterruptedException{
		//	Jobs resultJob = job;
		try{
		ThreadPoolExecutor executor=(ThreadPoolExecutor)Executors.newCachedThreadPool();
	//	Jobs j = findJob(1,6253,null,null);
		List<Easyfixers> easyfixerList = getEasyfixerListByJobTypeId(job.getJobId(),job.getFkServiceTypeId(),job.getFkClientId());
		List<Easyfixers> efrListWithLinearDis = getEasyfixerDistanceFromJobLocation(easyfixerList, job.getAddressObj().getGpsLocation(),getMaxDisTravelledByEfr(job));
		//List<Easyfixers> efrListWithActualDis = getSortedEfrRoadDistanceFromJobLocation(efrListWithLinearDis, job.getAddressObj().getGpsLocation(),job.getJobId());
		List<Easyfixers> efrListWithActualDis= null;
		/*if(efrListWithLinearDis!=null && efrListWithLinearDis.size()>5){
			efrListWithActualDis=efrListWithLinearDis.subList(0,5);
		}
		else
		*/
			efrListWithActualDis=efrListWithLinearDis;
	//	List<Easyfixers> efrList =  getEfrListForScheduling(job);
		logger.info("job:"+ job.getJobId()+" automatic schedling trial for "+efrListWithActualDis);
		
		for(Easyfixers e:efrListWithActualDis){
			if(e.getIsAppUser().equals(1)){
				Jobs josStatus =jobDao.getJobStatus(job.getJobId()) ;
				
				job.setJobStatus(josStatus.getJobStatus());
				System.out.println("job Status :"+job.getJobStatus());
					if(job.getJobStatus()==1){
							logger.info(job.getJobId()+": job is already scheduled no notififaction sent ");	
							break;
						}
						schedlingTask schedlingTask = new schedlingTask(job,e);
						Future future = executor.submit(schedlingTask);
						//System.out.println("active thread: "+executor.getActiveCount());
						
						TimeUnit.SECONDS.sleep(30);
		}
			else{
				logger.info(e+" is not app user so not sending notification for job:"+job.getJobId());
			}
		}
		executor.shutdown();
			logger.info("shuting down executor");
		try {
			logger.info("waiting termination");
			executor.awaitTermination(1, TimeUnit.HOURS);
			} catch (InterruptedException e) {
			e.printStackTrace();
			}
		}
		catch(Exception e){
			logger.catching(e);
		}
		return job;
	}

class schedlingTask implements Runnable{
	 
	private Jobs job;
	private Easyfixers efr;
	public schedlingTask(Jobs job, Easyfixers efr){
		this.job=job;
		this.efr= efr;
		
	}
	@Override
	public void run(){
	//	System.out.println("inside call");

		JobServiceImpl.logger.info(job.getJobId()+": job Sending scheduling/rescheduling notification to efr id:" + efr.getEasyfixerId());
		try {
			
		
				String notificationMessage = "Click to accept/reject job " + job.getJobId();
				Notification notification = new Notification(notificationMessage, "New Job", "job", "High", "1",
				job.getJobId() + "#" + job.getFkClientId());
				String result = UtilityFunctions.sendNotification(notification, efr.getDeviceId());
	//			Thread.sleep(delay);
	//			TimeUnit.SECONDS.sleep(30);
				jobDao.updateAppNotificationLog(job, efr, notification, result);

		//return job;
		} 
		
		catch (Exception e) {
			e.printStackTrace();
}
	}
	
	
 }

@Override
public List<Sms> getCrmSmsList() throws Exception {
	return jobDao.getCrmSmsList();
}
@Override
public void updateAppNotificationLog(Jobs job, Easyfixers efr,
		Notification notification, String result) {
	jobDao.updateAppNotificationLog(job, efr, notification, result);
	
}
@Override
public int changeJobDesc(Jobs jobsObj) throws Exception {
	return jobDao.changeJobDesc(jobsObj);
}
@Override
public int deleteJobAfterCheckout(Jobs job) throws Exception {
	return jobDao.deleteJobAfterCheckout(job);
	
	
	
}
@Override
public void updateUserActionLog(Jobs job) throws Exception {
	jobDao.updateUserActionLog( job);
	
}
@Override
public void changeJobStatus(Jobs job, int status) throws Exception {
	jobDao.changeJobStatus(job, status);
	
}
@Override
public void updateTransactionFromInvoice(Jobs jobsObj) throws Exception {
	List<JobSelectedServices> jobServiceList = new ArrayList<JobSelectedServices>();
	JobSelectedServices jobSelectObj = null;
	String minEasyfixerFee = "";
	List<EasyfixerRatingParameters> paramlist = getParamList();
	for(EasyfixerRatingParameters e : paramlist){
		if(e.getParamId() == 6){
			minEasyfixerFee = e.getParamWeightage();
		}
	}
	String[] dataArray = jobsObj.getClientServices().split("~");
	//System.out.println("no of services for jobId : "+jobsObj.getJobId()+" is:"+dataArray.length);
	logger.info("no of services for job id : "+jobsObj.getJobId()+" is  :"+dataArray.length);
	double totalEfrAmount = 0.00; 
	double totalEfAmount = 0.00;
	double totalClientAmount = 0.00;
	String serviceTaxRate = jobDao.getServiceTaxRate();
//	System.out.println(serviceTaxRate);
	double totalserviceTax = 0.00;
	for(int i=0; i<dataArray.length; i++){
		
		//System.out.println(i+" inside saveCheckOutJob inside for loop for saving job services for jobID "+jobsObj.getJobId());
		logger.info(i+" inside saveCheckOutJob inside for loop for saving job services for jobID "+jobsObj.getJobId());
		String[] data = (dataArray[i].trim()).split("#");
		jobSelectObj = new JobSelectedServices();
		try{
		jobSelectObj.setJobId(jobsObj.getJobId());
		jobSelectObj.setJobServiceId(Integer.parseInt(data[0].trim()));
		Clients clientObj = jobDao.getjobServiceDetailsByJobServiceId(jobSelectObj.getJobServiceId());
		String totalCharge = data[1].trim();
		double individualServiceTax= Double.valueOf(totalCharge)*Double.valueOf(serviceTaxRate)/100;
//		System.out.println("individualServiceTax"+individualServiceTax);
		String totalChargeAfterTax = String.valueOf((Double.valueOf(totalCharge)-individualServiceTax));
//		System.out.println("totalChargeAfterTax"+totalChargeAfterTax);
		
		//jobSelectObj.setTotalCharge(totalChargeAfterTax);
		jobSelectObj.setTotalChargeAfterTax(totalChargeAfterTax);
		jobSelectObj.setTotalCharge(totalCharge);
		RateCardCalculations r = new RateCardCalculationsImpl.CalculatorBuilder(jobSelectObj.getTotalChargeAfterTax())
		.clientfeeFixed(clientObj.getClientFixed()).clientfeeVariable(clientObj.getClientVariable())
		.easyfixFeeFixed(clientObj.getEasyfixDirectFixed()).easyfixFeeVariable(clientObj.getEasyfixDirectVariable())
		.minEasyfixerFee(minEasyfixerFee).overheadFixed(clientObj.getOverheadFixed()).overheadVariable(clientObj.getOverheadVariable()).build();
		
	//	System.out.println("in jobserviceImp "+r);			
		
		jobSelectObj.setClientCharge(r.getClientShare().toString());
		jobSelectObj.setEasyfixCharge(r.getEasyfixShare().toString());
		jobSelectObj.setEasyfixerCharge(r.getEasyfixerShare().toString());
		
		totalEfrAmount = totalEfrAmount + Double.parseDouble(jobSelectObj.getEasyfixerCharge());
		totalEfAmount = totalEfAmount + Double.parseDouble(jobSelectObj.getEasyfixCharge());
		totalClientAmount = totalClientAmount + Double.parseDouble(jobSelectObj.getClientCharge());
		totalserviceTax = totalserviceTax+individualServiceTax;
		}
		catch(Exception e){
			e.printStackTrace();
			logger.catching(e);
		}
		jobDao.saveJobServiceAmount(jobSelectObj);
		logger.info("jobDao.saveJobServiceAmount done for job "+jobsObj.getJobId());
					
		//jobServiceList.add(jobSelectObj);
	}
	jobsObj.setEasyfixerAmount(Double.toString(totalEfrAmount));
	jobsObj.setEasyfixAmount(Double.toString(totalEfAmount));
	jobsObj.setClientAmount(Double.toString(totalClientAmount));
	jobsObj.setTotalServiceTax(String.valueOf(totalserviceTax));
//	System.out.println("total service tax" +jobsObj.getTotalServiceTax());
	jobDao.updateTransactionFromInvoice(jobsObj);
	logger.info("jobDao.saveCheckOutJob done for job "+jobsObj.getJobId());
	//jobDao.updateEasyfixerAccount***Not_Use(jobsObj);
	
}
@Override
public int updateCheckoutOtp(int jobId, String otp) throws Exception {
	return jobDao.updateCheckoutOtp(jobId, otp);
}


	
	
}
 /*class CacheReminder {
    Timer timer;

    public CacheReminder(long seconds) {
        timer = new Timer();
        timer.scheduleAtFixedRate(new RemindTask(),new Date(),seconds*1000); 
	}

    class RemindTask extends TimerTask {
        public void run() {
            System.out.format("Time's up!%n");
            
           // timer.cancel(); //Terminate the timer thread
        }
    }
 }*/
class ValueComparator implements Comparator<Object> {
	 //sorts map into ascending order of values.
	Map<Easyfixers, Double> map;
 
	public ValueComparator(Map<Easyfixers, Double> map) {
		this.map = map;
	}
 
	public int compare(Object keyA, Object keyB) {
		double valueA = (Double)map.get(keyA);
		double valueB = (Double) map.get(keyB);
		//return valueB.compareTo(valueA);
        if (valueA >= valueB) {
            return 1;
        } else {
            return -1;
        }
	}
}

class ValueComparatorforlist implements Comparator<Easyfixers> {
	 //sorts list into descending order of finalweightage
	List<Easyfixers> list;
 
	public ValueComparatorforlist(List<Easyfixers> list) {
		this.list = list;
	}
 
	public int compare(Easyfixers A, Easyfixers B) {
		double valueA = Double.valueOf(A.getFinalWeightage());
		double valueB = Double.valueOf(B.getFinalWeightage());
		//return valueB.compareTo(valueA);
        if (valueA <= valueB) {
            return 1;
        } else {
            return -1;
        }
	}
}


class sortListByLinearDistance implements Comparator<Easyfixers> {
	 //sorts list into descending order of finalweightage
	List<Easyfixers> list;

	public sortListByLinearDistance(List<Easyfixers> list) {
		this.list = list;
	}

	public int compare(Easyfixers A, Easyfixers B) {
		double valueA = Double.valueOf(A.getLinearDistanceFromCustomer());
		double valueB = Double.valueOf(B.getLinearDistanceFromCustomer());
		//return valueB.compareTo(valueA);
       if (valueA >= valueB) {
           return 1;
       } else {
           return -1;
       }
	}
}

