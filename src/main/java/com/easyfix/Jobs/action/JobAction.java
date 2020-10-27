package com.easyfix.Jobs.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;


import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;



import org.codehaus.jackson.map.ObjectReader;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.SerializerFactory;
import org.codehaus.jettison.json.JSONObject;
import org.jfree.util.Log;

import com.easyfix.Jobs.delegate.JobService;
import com.easyfix.Jobs.model.JobImage;
import com.easyfix.Jobs.model.JobSelectedServices;
import com.easyfix.Jobs.model.Jobs;
import com.easyfix.Jobs.model.Notification;

import com.easyfix.Jobs.model.Recipients;
import com.easyfix.Jobs.model.Sms;
import com.easyfix.clients.delegate.ClientService;
import com.easyfix.clients.model.Clients;
import com.easyfix.customers.delegate.CustomerService;
import com.easyfix.customers.model.Address;
import com.easyfix.customers.model.Customers;
import com.easyfix.easyfixers.delegate.EasyfixerService;
import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.geocode.distance.ResultObject;
import com.easyfix.geocode.latlong.Latlong;
import com.easyfix.settings.delegate.CityService;
import com.easyfix.settings.delegate.ServiceTypeService;
import com.easyfix.settings.delegate.SkillService;
import com.easyfix.settings.model.City;
import com.easyfix.settings.model.ServiceType;
import com.easyfix.settings.model.Skill;
import com.easyfix.user.delegate.UserService;
import com.easyfix.user.model.User;
import com.easyfix.util.CommonAbstractAction;
import com.easyfix.util.Constants;
import com.easyfix.util.MailSender;
import com.easyfix.util.PropertyReader;
import com.easyfix.util.RestClient;
import com.easyfix.util.SmsSender;
import com.easyfix.util.TargetURISingleton;
import com.easyfix.util.UtilityFunctions;
import com.easyfix.util.emailSender;
import com.easyfix.util.JmsSenderAndReceiver.DemoMainAction;
import com.easyfix.util.clientIntegration.snapdeal.snapdealClient;
import com.easyfix.util.clientIntegration.snapdeal.snapdealDateFormat;
import com.easyfix.util.clientIntegration.snapdeal.snapdealUpdateServiceStatus;
import com.easyfix.util.utilityFunction.dao.ActiveUserJobListDao;
import com.opensymphony.xwork2.ModelDriven;

public class JobAction extends CommonAbstractAction implements ModelDriven<Jobs>{
	
	private static final long serialVersionUID = 1L;

	private String actMenu;
	private String actParent;
	private String title;
	private String msg;
	private Jobs jobsObj;
	private JobService jobService;
	private CustomerService customerService;
	private ClientService clientService;
	private ServiceTypeService serviceTypeService;
	private CityService cityService;
	private UserService userService;
	private EasyfixerService easyfixerService;
	private List<City> cityList;
	private Customers custObj;
	private List<Address> addressList;
	private List<Clients> clientList;
	//private Clients clientObj; 
	private List<ServiceType> serviceTypeList;
	private List<Clients> clientServiceList;
	private User userObj;
	private Address addObj;
	private List<JobSelectedServices> jobServiceList;
	private List<Jobs> jobList;
	private List<User> userlist;
	private List<Customers> customerList;
	private List<Easyfixers> easyfixerList;
	private List<Easyfixers> finalEfrList;
	private List<Jobs> commentList;
	private List<JobImage> jobImageList;
	private List<Jobs> enumReasonList;
	private ActiveUserJobListDao activeUserJobListDao;
	private List<Jobs> rejectReasonList;
	private String uploadPath = "/var/www/html/easydoc/easyfixer_documents/";
	private String uploadPathForJobAndRc = "/var/www/html/easydoc/upload_jobs/";
	//private String uploadPath = "D:/";
	private static final Logger logger = LogManager.getLogger(JobAction.class);
	private Latlong latLong;
	private List<Sms> crmSmsList;
	private String fileName;
	private List<Skill> skillList;
	private SkillService skillService;
	FileInputStream fileInputStream;
	//private static final Logger logger= Logger.getLogger(JobAction.class);
	
	@Override
	public Jobs getModel() {
		return setJobsObj(new Jobs());
	}
	public String uploadJobImage() throws Exception{
		
		userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
		logger.info("tryig to upoad job image id:"+jobsObj.getJobId());
		if(jobsObj.getJobImage()!=null && jobsObj.getJobImageName()!=null){
			String[] temp = jobsObj.getJobImageName().split("\\\\");
			String sysGeneratedFileName = "JobImg" + new Date().getTime() +"_"+ temp[temp.length-1];
			String fullFileName = uploadPathForJobAndRc + sysGeneratedFileName;

			File theFile = new File(fullFileName);
			FileUtils.copyFile(jobsObj.getJobImage(), theFile);
			jobsObj.setJobImageName(sysGeneratedFileName);
			JobImage jobImage = new JobImage();
			jobImage.setJobId(jobsObj.getJobId());
			jobImage.setImage(sysGeneratedFileName);
			jobImage.setCreatedDate(new Timestamp(new Date().getTime()));
			jobImage.setJobStage(jobsObj.getJobStatus());
			jobImage.setStatus(1);
			jobImage.setJobStage(jobsObj.getJobStatus());
			jobImage.setCeatedBy(userObj.getUserId());
			
			
			jobService.addJobImage(jobImage);
			
		}
		logger.info("completed upoad job image id:"+jobsObj.getJobId());
		return SUCCESS;
	}
	
	public String sendemailClitoClientUrgentRequest() throws Exception{
		int[] list = {
			
				31473	,
				31578	,
				31621	,
				31648	,
				31671	,
				31930	,
				31943	,
				31945	,
				
				32134	,
				
				32247	,
				32250	,
				32381	,
				
				32618	,
				32666	,
				32704	,
				32722	,
				32826	,
				
				32927	,
				32937	,
				33303	,
				33337	,
				33474	,
				33482	,
				33520	,
				33534	,
				33569	,
				33593	,
				33680	,
				33771	,
				33779	,
				33799	,
				33801	,
				33969	,
				33978	,
				34083	,
				34089	,
				34106	,
				34184	,
				34375	,
				34446	,
				34454	,
				34469	,
				34488	,
				34539	

		};
		for(int job=0;job<list.length;job++){
		try{
		Jobs j = jobService.getJobDetails(list[job]);
			String s= null;
			if(j.getClientRefId()!=null)
			{
			 s = "Ref id :"+ j.getClientRefId();
		}
		
		String subject = "Snapdeal Installation/Inspection - ";
		if(s!=null)
			subject = subject+s;
		
		String content = "<html><body> Dear "+j.getCustomerObj().getCustomerName()+",<br/> <br/>"+

"We tried contacting you several times but unable to reach you.<br/>"+

"Hence we are closing your installation request.<br/>"+

"Please mail us back if required installation support. <br/><br/>"
+"Thanks & Regards, <br/>"
+ "Team EasyFix";
		
		String recipient = j.getCustomerObj().getCustomerEmail();
		System.out.println("recipient:"+recipient+" job:"+j.getJobId()+":"+list[job]);
		Log.info("recipient:"+recipient+" job:"+j.getJobId()+":"+list[job]);
		String[] cc = {"furnitureinfo@snapdeal.com","solutions@easyfix.in","anil.bhatia@snapdeal.com"};
		//String[] cc = {"kundan.kumar@easyfix.in"};
		String sender = "nitika.aggarwal@easyfix.in";
		//send mail
		Properties mailProps = new Properties();
	        mailProps.put("mail.smtp.auth", "true");
	        mailProps.put("mail.smtp.starttls.enable", "true");
	        mailProps.put("mail.smtp.host", "smtp.gmail.com");
			mailProps.put("mail.smtp.port", "587");
			mailProps.put("mail.smtp.ssl.trust", "smtp.gmail.com");
	        
	        String user="nitika.aggarwal@easyfix.in";
	        String pwd= "vybnttgycxuovxbk";
	        Session mailSession = Session.getInstance(mailProps, new SMTPAuthenticator(user,pwd));
	        
	        MimeBodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText(content);
            textBodyPart.setContent(content, "text/html");
            MimeMultipart mimeMultipart = new MimeMultipart();
            mimeMultipart.addBodyPart(textBodyPart);
            
            //create the sender/recipient addresses
            InternetAddress iaSender = new InternetAddress(sender);
            InternetAddress iaRecipient = new InternetAddress(recipient);
            InternetAddress[] ccList=null;
            
            if(cc!=null && cc.length>0){
             ccList = new InternetAddress[cc.length] ;
            for(int i=0;i<cc.length;i++){
            	InternetAddress iaCc = new InternetAddress(cc[i]);
            		ccList[i]=iaCc;
            }
            }
            //construct the mime message
            MimeMessage mimeMessage = new MimeMessage(mailSession);
            mimeMessage.setFrom(iaSender);
            mimeMessage.setSender(iaSender);
            mimeMessage.setSubject(subject);
            mimeMessage.setRecipient(Message.RecipientType.TO, iaRecipient);
            mimeMessage.addRecipients(Message.RecipientType.CC, ccList);
            mimeMessage.addRecipient(Message.RecipientType.BCC, new InternetAddress("it-admin@easyfix.in"));
            mimeMessage.setContent(mimeMultipart);
             
            //send off the email
            Transport t = mailSession.getTransport("smtp");
            t.connect("smtp.gmail.com", 587, user, pwd);
            t.send(mimeMessage);
             
            logger.info("mail sent for job "+ j.getJobId() ); 
		}
		catch(Exception e){
			e.printStackTrace();
		}
		}
		return SUCCESS;
		
	}
	public String deleteJobAfterCheckout() throws Exception{
		System.out.println("in deleteJobAfterCheckout");
		return SUCCESS;
	}
   public String savedeleteJobAfterCheckout() throws Exception{
	   System.out.println("in savedeleteJobAfterCheckout");
	   userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
	   String msg = "unsuccessful";
	   int deleted = 0;
	   int jobId = jobsObj.getJobId();
	   Jobs jobFromDb = jobService.getJobDetails(jobId);
	   jobFromDb.setComments(jobsObj.getComments());
	   if(jobFromDb.getJobStatus()==3 || jobFromDb.getJobStatus()==4 || jobFromDb.getJobStatus()==5){
		   deleted =   jobService.deleteJobAfterCheckout(jobFromDb);
		   if(deleted>0)
		   msg="job id "+jobFromDb.getJobId()+" deleted successfully!!";
		  
	   }
	   jobFromDb.setUpdatedBy(userObj.getUserId());
	   jobFromDb.setJobFlag("delete job:"+msg);
	   jobService.updateUserActionLog(jobFromDb);
	   requestObject.setAttribute("msg", msg);
	   
	 //send email trigger to shaifali
	   
	 if(deleted>0){
		 String collected;
		 if(jobFromDb.getCollectedBy()==1)
			 collected = "Service man";
		 else if(jobFromDb.getCollectedBy()==1)
			 collected = "Easyfix";
		 else
			 collected = "client";
	   String content = "<html> <b> Hi Shaifali,</b><br/><br/>"
	   		+userObj.getUserName() +" has deleted JobId:"+jobId+".<br/>"
	   		+ " Client:"+jobFromDb.getClientObj().getClientName() +" <br/> "
	   		+ " Comments:"+jobFromDb.getComments() +" <br/> "
	   		+" Charge:"+jobFromDb.getTotalServiceAmount()+" <br/> "
	   		+" Material Charge:"+jobFromDb.getMaterialCharge()+" <br/> "
	   		+"Checkout time: "+jobFromDb.getCheckOutDateTime()+" <br/> "
	   		+"Collected By: "+collected +" <br/> "
	   				+ "From CRM using "
	   		+ "Delete job after checkout functionality.</html>";
	   String subject = "Job Deleted Post Checkout Id:"+jobId+" Client:"+jobFromDb.getClientObj().getClientName();
	   String[] cc = {userObj.getOfficialEmail()};
	   MailSender.email("shaifali@easyfix.in", content, subject, new PropertyReader(), null, cc);
	 }
		return SUCCESS;
	}
	public String manageJob() throws Exception {
//		Configurator.setLevel("com.easyfix.Jobs.action.JobAction",Level.ALL);
//		System.out.println(LogManager.getContext().getClass());
//		logger.trace("entering manage jobs");
//		if (logger.isDebugEnabled()) {
//			logger.debug("execute()!");
//		}
	
	//	System.out.println(logger);
//		System.out.println(logger.isInfoEnabled());
//		System.out.println(logger.isErrorEnabled());
		
	//	logger.info("kundan logger");
	//	logger.error("This is Error message");
		
			String acttitle= "Easyfix : Manage Jobs";
			setActMenu("Manage Jobs");
			setActParent("Jobs");
			setTitle(acttitle);
		try {
			
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			//easyfixerList = easyfixerService.getEasyfixerList(1); //1 for active 
			clientList = clientService.getClientList(1);
			cityList = cityService.getCityList();
			userlist = userService.getUserList(2); //2 for all user
			//customerList = customerService.getCustomerList();
			serviceTypeList = serviceTypeService.getSerTypeList(1);
			
			
//			jobList = jobService.getJobList(100); //100 for All Jobs
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}	
		
		return SUCCESS;
	}
	

public String getJobListByFilter() throws Exception {
		
	try {
		userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
		
		if(!jobsObj.getStartDate().equalsIgnoreCase("")) {
			String startDate = jobService.convertStringToSimpleDate(jobsObj.getStartDate());
			String endDate = jobService.convertStringToSimpleDate(jobsObj.getEndDate());
			
			jobsObj.setStartDate(startDate);
			jobsObj.setEndDate(endDate);
		}
		
		jobList = jobService.getJobListByFilter(jobsObj); //100 for All Jobs
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	}	
						
	return SUCCESS;
}

	
	
	public String addEditJobs() throws Exception {
		
		String loc = requestObject.getParameter("loc");
		requestObject.setAttribute("loc", loc);
		
		return SUCCESS;
	}
	
public String changeJobStatus() throws Exception {
		userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
		try{
		int status = jobsObj.getJobStatus();
		Jobs jobFromDb = jobService.getJobDetails(jobsObj.getJobId());
		if(jobFromDb.getJobStatus()==0){
			logger.info(userObj.getUserName()+" is updating job status from "+jobFromDb.getJobStatus()+" to "+status);
			jobService.changeJobStatus(jobFromDb, status);
		}
		Jobs job = new Jobs();
		job.setOwnerJobIds(jobFromDb.getJobId()+"");
		job.setJobOwner(userObj.getUserId());
		job.setOwnerChangeBy(53);
		job.setOwnerChangeReason("Retail job status enquiry from unowned jobs");
		jobService.updateJobOwner(job);
		}
		catch(Exception e){
			logger.catching(e);
			//jobsObj.getOwnerJobIds(),jobsObj.getJobOwner(),jobsObj.getOwnerChangeReason(),
			//jobsObj.getOwnerChangeBy()
		}
		return SUCCESS;
	}
	
	
	public String editJobs() throws Exception {
		
		jobsObj = jobService.getJobDetails(jobsObj.getJobId());
		String loc = requestObject.getParameter("loc");

		requestObject.setAttribute("loc", loc);
		
		return SUCCESS;
	}
	
	
	public String jobDetails() throws Exception {
		
try {		
			jobsObj = jobService.getJobDetails(jobsObj.getJobId());
			
			jobsObj.setCreatedDateTime(jobService.convertDateToString(jobsObj.getCreatedDateTime()));
			jobsObj.setRequestedDateTime(jobService.convertDateToString(jobsObj.getRequestedDateTime()));
			jobsObj.setScheduleDateTime(jobService.convertDateToString(jobsObj.getScheduleDateTime()));
			jobsObj.setCheckInDateTime(jobService.convertDateToString(jobsObj.getCheckInDateTime()));
			jobsObj.setOwnerChangeDateTime(jobService.convertDateToString(jobsObj.getOwnerChangeDateTime()));
			jobServiceList = jobService.getJobServiceList(jobsObj.getJobId(),1); // 1 for active service
			double total = 0;
			for (JobSelectedServices s : jobServiceList) {
				if(s.getJobChargeType() == 0)	
					
				total = total + Double.parseDouble(s.getTotalCharge());		
				
			}
			
			requestObject.setAttribute("totalSum", total);
						
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
			
		return SUCCESS;
	}
	
	
	public String getCustomerDetailsForJob() throws Exception {
		try {
			addressList = null;
			clientList = clientService.getClientList(1);
			//serviceTypeList = serviceTypeService.getSerTypeList(1);
			cityList = cityService.getCityList();
			skillList = skillService.getSkillList();
			custObj = customerService.getCustomerDetailsByMobileNo(jobsObj.getMobileNo());
			if(custObj != null) {
				addressList = customerService.getAddressList(custObj.getCustomerId());
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		return SUCCESS;
	}
	
	public String getServiceTypeListByClientId() throws Exception {
		try {
			serviceTypeList = serviceTypeService.getSerTypeListByClientId(jobsObj.getFkClientId(),1);					
	//		 clientObj = clientService.getClientDetails(jobsObj.getFkClientId());
	//		 requestObject.setAttribute("clientObj", clientObj);
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		return SUCCESS;
	}
	public String getCollectedbyByClientId() throws Exception {
		try {
			Clients clientObj = clientService.getClientDetails(jobsObj.getFkClientId());
			jobsObj.setClientObj(clientObj);
	//		 clientObj = clientService.getClientDetails(jobsObj.getFkClientId());
	//		 requestObject.setAttribute("clientObj", clientObj);
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		return SUCCESS;
	}
	
	public String getServiceListByClientIdAndServiceTypeId() throws Exception {
		try {
			
			clientServiceList = clientService.getClientServiceListByClientIdAndServiceTypeId(jobsObj.getFkClientId(),jobsObj.getFkServiceTypeId());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		return SUCCESS;
	}
	
	
	public String addUpdateJob() throws Exception {
		String[] responce; 
		String result;
		int status = 0;
		int job_id = 0;
		try {
		//	System.out.println("in addUpdateJob"+jobService.getSmsDetails());
			
			
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			logger.info(userObj.getUserName()+" started addUpdateJob id: "+jobsObj.getJobId()+" colletedBy from frontend:"+jobsObj.getCollectedBy()+" customer id:"+jobsObj.getFkCustomerId()+"mobile:"+jobsObj.getMobileNo());
			if(jobsObj.getJobImage()!=null && jobsObj.getJobImageName()!=null){
				String[] temp = jobsObj.getJobImageName().split("\\\\");
				String sysGeneratedFileName = "JobImg" + new Date().getTime() +"_"+ temp[temp.length-1];
				String fullFileName = uploadPathForJobAndRc + sysGeneratedFileName;

				File theFile = new File(fullFileName);
				FileUtils.copyFile(jobsObj.getJobImage(), theFile);
				jobsObj.setJobImageName(sysGeneratedFileName);
			}
			if(jobsObj.getJobStatus() == 7 || jobsObj.getJobStatus() == 9) // 7 for enquiry and 9 for call later
			{
				List<String> serviceList = new ArrayList<String>();
				if(jobsObj.getRequestedDateTime().equals(""))
				{
					jobsObj.setRequestedDateTime(null);					
				}
				else
				{
					String requestDate = jobService.convertStringToDate(jobsObj.getRequestedDateTime());
					jobsObj.setRequestedDateTime(requestDate);
				}
				result = jobService.addUpdateJob(jobsObj,serviceList, userObj.getUserId());
				responce = result.split(",");
				status = Integer.parseInt(responce[0]);
				job_id = Integer.parseInt(responce[1]);
				
				
				if(status > 2 ) {
					
					Jobs jObj = jobService.getJobDetails(job_id); 
					logger.info("Job id:"+job_id +" created by "+userObj.getUserName()+"with collectedBy:"+jObj.getCollectedBy()+" cus page:"+jobsObj.getFkCustomerId()+":"+jobsObj.getMobileNo());
					
				/*	if(jobsObj.getFkClientId() == 1) {
						String customerMsg = "Thanks for choosing EasyFix*Getting a Plumber Carpenter Electrician is no more a HASSLE* get it on a call anywhere in  India on local market prices.";
						SmsSender.sendSms(customerMsg, jobsObj.getMobileNo());
					}*/
					if(jObj.getJobStatus() == 7) //send enquiry sms to customer from ef
					jobService.sendSms(jObj.getFkClientId(), "enquiry", 1, 4, jObj.getCustomerObj().getCustomerMobNo(),jObj);	
					
					else if(jobsObj.getJobStatus() == 9) //send call later smsm to customer from ef
						jobService.sendSms(jObj.getFkClientId(), "callLater", 1, 4, jObj.getCustomerObj().getCustomerMobNo(),jObj);
				}
			}
			else 
			{
				if(jobsObj.getRequestedDateTime() != "")
				{
					String requestDate = jobService.convertStringToDate(jobsObj.getRequestedDateTime());
					jobsObj.setRequestedDateTime(requestDate);
				}
				
				String service = jobsObj.getClientServices();			
				List<String> serviceList = new ArrayList<String>(Arrays.asList(service.split(",")));
				
				result = jobService.addUpdateJob(jobsObj,serviceList, userObj.getUserId());
				
				responce = result.split(",");
				status = Integer.parseInt(responce[0]);
				job_id = Integer.parseInt(responce[1]);
				
				if(status > 2) {
					Jobs jObj = jobService.getJobDetails(job_id);
					logger.info("Job id:"+job_id +" created by "+userObj.getUserName()+"with collectedBy db:"+jObj.getCollectedBy()+" collectedBy page:"+jobsObj.getCollectedBy()+" cus page:"+jobsObj.getFkCustomerId()+":"+jobsObj.getMobileNo());
					
				/*	if(jobsObj.getJobStatus() == 0 && jobsObj.getFkClientId() == 1) {
						String customerMsg1 = "Your booking is confirmed. Delivery by EasyFix for " + jObj.getServiceTypeObj().getServiceTypeName() + " on " + jobsObj.getRequestedDateTime() 
								+ ". Confirmation on availability. No visiting charges.";
						SmsSender.sendSms(customerMsg1, jobsObj.getMobileNo()); 
					*/	
					jobService.sendSms(jObj.getFkClientId(), "bookCall", 1, 4, jobsObj.getMobileNo(),jObj);	
						
						//System.out.println(customerMsg1);
					}
				//	if(jobsObj.getJobStatus() == 0 && jobsObj.getFkClientId() > 1 && jobsObj.getFkClientId() != 25 && jobsObj.getFkClientId() != 64) {
					/*	String customerMsg2 = jObj.getServiceTypeObj().getServiceTypeName() + " booked from EasyFix for " + jObj.getClientObj().getClientName() 
								+ ". An EasyFixer will reach at " + jobsObj.getRequestedDateTime()  + ". Get Plumber Electrician Carpenter on 8882122666";
						SmsSender.sendSms(customerMsg2, jobsObj.getMobileNo());
					*/	
				//		jobService.sendSms(1, "bookCall", 1, 4, jobsObj.getMobileNo(),jObj);	
						//System.out.println(customerMsg2);
				//	}					
				}			
			//}
			
			if(jobsObj.getJobId() > 0) {
				jobService.updateLeadJob(jobsObj.getJobId(),1);
			}
			
			
			if(status == 0){
				setMsg("Customer already added in this system. Please try again!");
				logger.info("job Id: "+job_id+" Customer creation failed");
			}
			else if(status == 1){
				setMsg("Address already added in this system. Please try again!");
				logger.info("job Id: "+job_id+" Address creation failed");
			}
			else if(status == 1){
				setMsg("job already added in this system. Please try again!");
				logger.info("job Id: "+job_id+" job creation failed");
			}
			if(status > 2){
				setMsg(job_id+"");
				logger.info("Job:"+job_id+" has been created successfully.");
		//		jobService.autoJobSchedule(job_id);
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		
		return SUCCESS;
	}
	public String autoJobSchedule() throws Exception{
		try{
			/*delete later
			Jobs jobFromDb = jobService.getJobDetails(jobsObj.getJobId());
			System.out.println(jobFromDb.getFkCreatedBy());
			if(jobFromDb.getFkCreatedBy()==47 || jobFromDb.getFkCreatedBy()==53 || jobFromDb.getFkCreatedBy()==3 ||jobFromDb.getFkCreatedBy()==54){
				//jobService.saveScheduleJob(jobsObj.getJobId(), 445, 47, "10");
				Easyfixers efr= easyfixerService.getEasyfixerDetailsById(445);
				String notificationMessage = "Click to accept/reject job " + jobFromDb.getJobId();
				Notification notification = new Notification(notificationMessage, "New Job", "job", "High", "1",
						jobFromDb.getJobId() + "#" + jobFromDb.getFkClientId());
				UtilityFunctions.sendNotification(notification, efr.getDeviceId());
				logger.info("notification: "+notification+" sent to efr id: "+efr.getEasyfixerId());
			}
			delete later
			else{*/
			logger.info("trying to auto schedule job:"+jobsObj.getJobId());
			jobService.autoJobSchedule(jobsObj.getJobId());
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return "kundan";
	}
	
	public String scheduleJob() throws Exception{
		
		try {
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			logger.info(userObj.getUserName()+ "started scheduling job Id:"+jobsObj.getJobId());
			String reschedulingFlag=requestObject.getParameter("flag");
			String loc = requestObject.getParameter("loc");
			jobsObj = jobService.getJobDetails(jobsObj.getJobId());
			jobsObj.setCreatedDateTime(jobService.convertDateToString(jobsObj.getCreatedDateTime()));
			jobsObj.setRequestedDateTime(jobService.convertDateToString(jobsObj.getRequestedDateTime()));
			//addObj = customerService.getAddressDetailsById(jobsObj.getFkAddressId());
			jobServiceList = jobService.getJobServiceList(jobsObj.getJobId(),1); // 1 for active service
			double total = 0;
			int star = 0;
			for (JobSelectedServices s : jobServiceList) {
				if(s.getJobChargeType() == 0)
					star = 1;
				else 
					total = total + Double.parseDouble(s.getTotalCharge());					
			}
			
			Map<String,Easyfixers> appActionMap = easyfixerService.getEfrAppActions(jobsObj.getJobId(), 0);
			requestObject.setAttribute("totalSum", total);
			requestObject.setAttribute("star", star);
			requestObject.setAttribute("loc", loc);
			requestObject.setAttribute("flag", reschedulingFlag);
			requestObject.setAttribute("cancelledBy", appActionMap.get("cancelled"));
			requestObject.setAttribute("rejectedBy", appActionMap.get("rejected"));
			requestObject.setAttribute("appActionMap", appActionMap);
			
			
			List<Easyfixers> easyfixerList = jobService.getEasyfixerListByJobTypeId(jobsObj.getJobId(),jobsObj.getFkServiceTypeId(),jobsObj.getFkClientId());
		//	System.out.println("in action: "+easyfixerList);
		//	System.out.println(getMaxDisTravelledByEfr());
			if(jobsObj.getAddressObj().getGpsLocation()!=null && jobsObj.getAddressObj().getGpsLocation().trim().length()>5  ){
				
			
			List<Easyfixers> efrListWithLinearDis = jobService.getEasyfixerDistanceFromJobLocation(easyfixerList, jobsObj.getAddressObj().getGpsLocation(),getMaxDisTravelledByEfr());
			
	//		System.out.println("in action efrListWithLinearDis: "+efrListWithLinearDis);
			//List localList = new ArrayList(map.keySet());
			List<Easyfixers> efrListWithActualDis =null;
			try{

			efrListWithActualDis = jobService.getSuitableEasyfixers(efrListWithLinearDis, jobsObj);
	//		System.out.println("in action efrListWithActualDis: "+efrListWithActualDis);
			}
			catch(Exception e){
				e.printStackTrace();
				logger.catching(e);
			}
			
			if(efrListWithActualDis!=null)
				finalEfrList=efrListWithActualDis;
			else{
				System.out.println("google's max query limit reached");
				finalEfrList=efrListWithLinearDis;
			}
			for(Easyfixers e: finalEfrList){
				Easyfixers e1 = easyfixerService.getEfrMetaData(e.getEasyfixerId());
				e.setCurrentMonthCompletedJobs(e1.getCurrentMonthCompletedJobs());
				e.setOpenJobs(e1.getOpenJobs());
				e.setLastSixMonthCusRating(e1.getLastSixMonthCusRating());
			}
			}
		//		System.out.println("in action finalEfrList: "+finalEfrList);
		
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
			
		return SUCCESS;
	}
	public String requestApproval()throws Exception{
		try{
			jobService.UpdateJobServiceDetails(jobServiceList);			
			String approveJob = requestObject.getParameter("jobId");
			String loc = requestObject.getParameter("loc");
			requestObject.setAttribute("approveJobId", approveJob);
			jobServiceList = jobService.getJobServiceList(jobsObj.getJobId(),1); 
			double total = 0;
			for (JobSelectedServices s : jobServiceList) {
				total = total + Double.parseDouble(s.getTotalCharge()) + s.getServiceMaterialCharge();					
			}
			requestObject.setAttribute("totalSum", total);
			requestObject.setAttribute("loc", loc);
		}
		catch(Exception e){
			System.out.println(e);
		}
		return SUCCESS;
	}
	
	
	public String confirmApprovejob() throws Exception{
		userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
		try{
			jobService.JobApproveDetails(jobServiceList,jobsObj);
			
			Jobs jobsObject=jobService.getJobDetails(jobsObj.getJobId());
			jobServiceList = jobService.getJobServiceList(jobsObj.getJobId(),1);
			InvoiceApprovalPDF t=new InvoiceApprovalPDF();
			t.test(jobServiceList,jobsObject);
			
			final String uploadPathForEstimateApproval = "/var/www/html/easydoc/estimateapproval/";
		//	final String uploadPathForEstimateApproval = "C://Users//Anurag//Desktop";
			String jobId=""+jobsObj.getJobId();
			final String fileName = "Estimate_Approval_"+jobId+".pdf";
			String content = "<html> <b> Hi "+jobsObject.getClientObj().getClientName()+",</b><br/><br/>"
				   		+"Here we are enclosing an attachment providing estimate for the Job details.<br/>"
				   		+ "Please provide the Approval for the estimate in the attachment.<br/> "
				   		+ "Thank You <br/> "
				   		+"Regards,<br/> "
				   		+"EasyFix<br/>"+
				   		"</html>";
			
			String[] cc = {jobsObject.getOwner().getOfficialEmail()+"","solutions@easyfix.in"};
			String bcc="shaifali@easyfix.in";
			MailSender.email(jobsObject.getClientObj().getClientEmail(), content, "Client_Estimate Approval_"+jobId+"_"+jobsObject.getCustomerObj().getCustomerName()+"_"+jobsObject.getCustomerObj().getCustomerMobNo(), new PropertyReader(),uploadPathForEstimateApproval+fileName,cc,bcc);
			
			
		/*	String[] cc = {jobsObject.getOwner().getOfficialEmail()+"","anurag4395@gmail.com"};
			String bcc="anurag4395@gmail.com";
			MailSender.email("anurag.dabas@channelplay.in", content, "Client_Estimate Approval_"+jobsObject.getJobId()+"_"+jobsObject.getCustomerObj().getCustomerName()+"_"+jobsObject.getCustomerObj().getCustomerMobNo(), new PropertyReader(),uploadPathForEstimateApproval+fileName,cc,bcc);
			*/
			
			/*	jobService.confirmApprovejob(jobsObject);*/

			}
		catch(Exception ex){
			ex.printStackTrace();
			logger.catching(ex);
		}
	//	requestObject.setAttribute("msg", "emails sent");
		
		return SUCCESS;
		
	}
	public String reScheduleJob()throws Exception{
		try{
			String reschJobId = requestObject.getParameter("jobId");
			enumReasonList = jobService.getEnumReasonList(8);
			requestObject.setAttribute("reschJobId", reschJobId);
		}
		catch(Exception e){
			System.out.println(e);
			logger.catching(e);
		
		}
		return SUCCESS;
	}
	
	public String confirmRescheduleJob() throws Exception{
	//	String jobId = requestObject.getParameter("jobId");
		userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
//		int rescheduleReasonId = jobsObj.getEnumId();
//		String comment = 	jobsObj.getComments();	
		jobsObj.setJobOwner(userObj.getUserId());
//        System.out.println(rescheduleReasonId);
//        System.out.println(comment);
//        System.out.println(userObj);
        System.out.println(jobsObj.getJobId());
//        System.out.println(jobsObj.getJobId());
		jobService.updateRescheduleJob(jobsObj);
        
		
		return SUCCESS;
	}
	public String saveScheduleJob() throws Exception {
		try {
			
			String flag= requestObject.getParameter("jobFlag");
				userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
				jobService.saveScheduleJob(jobsObj.getJobId(), jobsObj.getFkEasyfixterId(), userObj.getUserId(),jobsObj.getDistanceTraveledByEasyfixer(),flag);
				
				//Jobs jObj = jobService.getJobDetails(jobsObj.getJobId());
				
				/*Jobs job = new Jobs();
				Easyfixers efr = new Easyfixers();
					efr.setEasyfixerId(jobsObj.getFkEasyfixterId());
			//	System.out.println(jobsObj.getJobFlag());
				
				job.setJobId(jobsObj.getJobId());
				job.setEasyfixterObj(efr);
				job.setJobScheduledBy(userObj);
				
				if(jobsObj.getJobFlag().trim().equalsIgnoreCase("reschedule")){
					job.setJobAction("rescheduleEfr");
				}
				else{
					
				job.setJobAction("schedule");
				}
				HttpPatch patchRequest = new HttpPatch(
						"http://localhost:8090/v1/jobs");
				
				ObjectWriter ow = new ObjectMapper().writer();
				String json = ow.writeValueAsString(job);
				StringEntity  entity = new StringEntity (json);
				patchRequest.setEntity(entity);
				patchRequest.addHeader("Content-Type","application/json");
				patchRequest.addHeader("clientId",jObj.getFkClientId()+"");
				CredentialsProvider credentialsProvider= new BasicCredentialsProvider();
				credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("easyfixcrm", "09dce46b-5b88-424f-a401-b20390aab6d5"));
				HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
				HttpResponse response =client.execute(patchRequest);
				
			 	if(response.getStatusLine().getStatusCode()==204){*/
				Jobs jObj = jobService.getJobDetails(jobsObj.getJobId());
				jObj.setJobFlag(jobsObj.getJobFlag());
			 	jobService.sendSms(jObj.getFkClientId(), "scheduled", 1, 4, jObj.getCustomerObj().getCustomerMobNo(),jObj);
			 	jobService.sendSms(jObj.getFkClientId(), "scheduled", 3, 4, jObj.getEasyfixterObj().getEasyfixerNo(),jObj);
			 	logger.info(userObj.getUserName()+ "completed scheduling job Id:"+jobsObj.getJobId()+" EFRid:"+jobsObj.getFkEasyfixterId());
			 	
			 	//Snap Deal API Integration 
			 	if(jObj.getFkClientId()==18){
					snapdealClient.updateSnapDealServiceStatus(jObj);
					}
			 	
			 	
			 	/*}
			 	else{
			 		logger.info("scheduling job failed for id:"+ jobsObj.getJobId()+" EFRid:"+jobsObj.getFkEasyfixterId());
			 	}*/
			 	/*	String customerMsg = "Hey " + jObj.getCustomerObj().getCustomerName() + " Your call is confirmed. EasyFixer " 
			 			+ jObj.getEasyfixterObj().getEasyfixerName() + " is coming for " + jObj.getServiceTypeObj().getServiceTypeName() + 
			 			" work at " + jObj.getRequestedDateTime() + ". For info call 8882122666";
			 	if(jObj.getFkClientId() != 25 && jObj.getFkClientId() != 64)
			 			SmsSender.sendSms(customerMsg, jObj.getCustomerObj().getCustomerMobNo());
				*/
				/*String efrMsg = "Booking Confirm! Name: " + jObj.getCustomerObj().getCustomerName() + ", DateTime " + 
						jObj.getRequestedDateTime() + ", Address: "  + jObj.getAddressObj().getAddress() + " " + jObj.getAddressObj().getCityObj().getCityName() + 
						", PhoneNo. " + jObj.getCustomerObj().getCustomerMobNo() + ", Go NOW!";
				*/
				//System.out.println(efrMsg);
				//System.out.println(customerMsg);
				
				//SmsSender.sendSms(efrMsg, jObj.getEasyfixterObj().getEasyfixerNo());
				
			 	
			 	
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		return "jhgjhgj";
	}
	
		
	
	public String checkInJob() throws Exception{
		
		try {
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			logger.info(userObj.getUserName()+ "started checkin job Id:"+jobsObj.getJobId());
			
			String loc = requestObject.getParameter("loc");
			jobsObj = jobService.getJobDetails(jobsObj.getJobId());
			
			String schedDt = jobsObj.getScheduleDateTime();
			jobsObj.setCreatedDateTime(jobService.convertDateToString(jobsObj.getCreatedDateTime()));
			jobsObj.setRequestedDateTime(jobService.convertDateToString(jobsObj.getRequestedDateTime()));
			jobsObj.setScheduleDateTime(jobService.convertDateToString(jobsObj.getScheduleDateTime()));
			jobServiceList = jobService.getJobServiceList(jobsObj.getJobId(),1); // 1 for active service
			double total = 0;
			int star = 0;
			for (JobSelectedServices s : jobServiceList) {
				if(s.getJobChargeType() == 0)					
					star = 1;
				
				total = total + Double.parseDouble(s.getTotalCharge()) + s.getServiceMaterialCharge();		
				
			}
			JobImage j = new JobImage();
			j.setJobId(jobsObj.getJobId());
			List<JobImage> imageList = jobService.jobImageList(j);
			requestObject.setAttribute("imageList", imageList);
			requestObject.setAttribute("totalSum", total);
			requestObject.setAttribute("star", star);
			requestObject.setAttribute("schedDt", schedDt);
			requestObject.setAttribute("loc", loc);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
			
		return SUCCESS;
	}
	
	
	public String saveCheckInJob() throws Exception {
		try {
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			//String checkInDate = jobService.convertStringToDate(jobsObj.getCheckInDateTime());
			jobService.saveCheckInJob(jobsObj.getJobId(), userObj.getUserId());
			jobService.UpdateJobServiceDetails(jobServiceList);
			Jobs jObj = jobService.getJobDetails(jobsObj.getJobId());
			
			int otp = generateOtp();
			jObj.setOtp(otp+"");
			int rowUpdated = jobService.updateCheckoutOtp(jObj.getJobId(), otp+"");
			
			if(rowUpdated>0)
				jobService.sendSms(jObj.getFkClientId(), "sendOtpToCustomerForCheckout", 1, 4, jObj.getCustomerObj().getCustomerMobNo(),jObj);
			
				
			/*String sms = "your otp for job verification is :"+otp;
			SmsSender.sendSms(sms, jObj.getCustomerObj().getCustomerMobNo());*/
			
			Long timeDiff = jobService.dateTimeDiff(jObj.getRequestedDateTime());
				if(timeDiff >= 0)
					jobService.sendSms(jObj.getFkClientId(), "checkInBeforeTime", 1, 4, jObj.getCustomerObj().getCustomerMobNo(),jObj);
				else
					jobService.sendSms(jObj.getFkClientId(), "checkInAfterTime", 1, 4, jObj.getCustomerObj().getCustomerMobNo(),jObj);
			/*String custSMS = "";
			if(jObj.getFkClientId() == 1) {
				if(timeDiff >= 0) {
					custSMS = "Hurray! EasyFixer " + jObj.getEasyfixterObj().getEasyfixerName() + " has arrived to you before time. Get quote&finalise rate before starting the work. Didn't reach?Call 8882122666";
				}
				else {
					custSMS = "EasyFixers regret the delay, let us make up &deliver excellent services. Get quote & finalise rate before starting the work. Didn't reach? Call 8882122666";
				}
			}
			else {
				 	if(timeDiff >= 0) {
						custSMS = "Hurray! EasyFixer has arrived to you before time. Choose EasyFix for Skilled & Reliable Plumber, Electrician, Carpenter. Didn't reach? Call 8882122666";
					}
					else {
						custSMS = "EasyFixers regret the delay, let us make up &deliver excellent services. Get quote & finalise rate before starting the work. Didn't reach? Call 8882122666";
					}
				
			}
			
			//System.out.println(custSMS);
			
			if(jObj.getFkClientId() != 25 && jObj.getFkClientId() != 64)
			SmsSender.sendSms(custSMS, jObj.getCustomerObj().getCustomerMobNo());
			*/
			logger.info(userObj.getUserName()+ "completed checkin job Id:"+jobsObj.getJobId());
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
			
		}
		
			
		return SUCCESS;
	}
	
	
	
public String approveJob() throws Exception{
		
		try {
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			logger.info(userObj.getUserName()+ "started checkin job Id:"+jobsObj.getJobId());
			
			String loc = requestObject.getParameter("loc");
			jobsObj = jobService.getJobDetails(jobsObj.getJobId());
			
			String schedDt = jobsObj.getScheduleDateTime();
			jobsObj.setCreatedDateTime(jobService.convertDateToString(jobsObj.getCreatedDateTime()));
			jobsObj.setRequestedDateTime(jobService.convertDateToString(jobsObj.getRequestedDateTime()));
			jobsObj.setScheduleDateTime(jobService.convertDateToString(jobsObj.getScheduleDateTime()));
			jobServiceList = jobService.getJobServiceList(jobsObj.getJobId(),1); // 1 for active service
			double total = 0;
			int star = 0;
			for (JobSelectedServices s : jobServiceList) {
				if(s.getJobChargeType() == 0)					
					star = 1;
				
				total = total + Double.parseDouble(s.getTotalCharge())+ s.getServiceMaterialCharge();		
				
			}
			JobImage j = new JobImage();
			j.setJobId(jobsObj.getJobId());
			List<JobImage> imageList = jobService.jobImageList(j);
			requestObject.setAttribute("imageList", imageList);
			requestObject.setAttribute("totalSum", total);
			requestObject.setAttribute("star", star);
			requestObject.setAttribute("schedDt", schedDt);
			requestObject.setAttribute("loc", loc);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
			
		return SUCCESS;
	}



public String saveApproveJob() throws Exception {
	try {
		userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
		jobService.saveApproveJob(jobsObj.getJobId());
		jobService.UpdateJobServiceDetails(jobServiceList);
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	}	
	return SUCCESS;
}
	
	
public String checkOutJob() throws Exception{
		
		try {
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			logger.info(userObj.getUserName()+ " started checkout job Id:"+jobsObj.getJobId());
			
			String loc = requestObject.getParameter("loc");
			jobsObj = jobService.getJobDetails(jobsObj.getJobId());
			
			jobsObj.setCreatedDateTime(jobService.convertDateToString(jobsObj.getCreatedDateTime()));
			jobsObj.setRequestedDateTime(jobService.convertDateToString(jobsObj.getRequestedDateTime()));
			jobsObj.setScheduleDateTime(jobService.convertDateToString(jobsObj.getScheduleDateTime()));
			jobsObj.setCheckInDateTime(jobService.convertDateToString(jobsObj.getCheckInDateTime()));
			jobServiceList = jobService.getJobServiceList(jobsObj.getJobId(),1); // 1 for active service
			double total = 0;
			int star = 0;
			for (JobSelectedServices s : jobServiceList) {
				if(s.getJobChargeType() == 0)					
					star = 1;
				
				total = total + Double.parseDouble(s.getTotalCharge())+ s.getServiceMaterialCharge();		
				
			}
			
			requestObject.setAttribute("totalSum", total);
			requestObject.setAttribute("star", star);
			requestObject.setAttribute("loc", loc);
						
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
			
		return SUCCESS;
	}
	
	public String saveCheckOutJob() throws Exception {
		try {
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			Jobs jobFromDd =  jobService.getJobDetails(jobsObj.getJobId());
			jobService.UpdateJobServiceDetails(jobServiceList);
			if(!(jobFromDd.getJobStatus()==10 || jobFromDd.getJobStatus()==2) ){
				this.msg = "0";
				return SUCCESS;
			}
			jobsObj.setFkCheckOutBy(userObj.getUserId());
			jobService.saveCheckOutJob(jobsObj,jobServiceList);
			
			Jobs jObj = jobService.getJobDetails(jobsObj.getJobId());
			jobService.sendSms(jObj.getFkClientId(), "checkOut", 1, 4, jObj.getCustomerObj().getCustomerMobNo(),jObj);
		//	jobService.sendSms(jObj.getFkClientId(), "checkOut", 3, 4, jObj.getEasyfixterObj().getEasyfixerNo(),jObj);
		
			//snap deal API Integration
			if(jObj.getFkClientId()==18){
				snapdealClient.updateSnapDealServiceStatus(jObj);
				}
			
			/*	String custSMS = "";
			if(jObj.getFkClientId() == 1) {
				
				Date today = new Date();
				String expDate = UtilityFunctions.addSubstractDaysInDate(today, 10, "dd-MM-yyyy");
				
				custSMS = "Work Done!Total money spent-Service Rs "+ jObj.getTotalServiceAmount() +" Material Rs "+ jObj.getMaterialCharge() +" For complaint call 8882122666 in 10 days, warranty expires on "+expDate;
				
			}
			else {
				
				custSMS = "Work Done! EasyFix, the priority partner for "+ jObj.getClientObj().getClientName() +" has finished task. For New Plumber, Electrician Carpenter Booking OR complaint call 8882122666";
				
			}
			
			//System.out.println(custSMS);
			if(jObj.getFkClientId() != 25 && jObj.getFkClientId() != 64)
				SmsSender.sendSms(custSMS, jObj.getCustomerObj().getCustomerMobNo());
			*/
			logger.info(userObj.getUserName()+ "completed checkout job Id:"+jobsObj.getJobId());
			this.msg = "1";
		} catch (Exception e) {
			this.msg = "0";
			e.printStackTrace();
			logger.catching(e);
			
		}
		
			
		return SUCCESS;
	}
	
	
	
	public String feedbackJob() throws Exception{
		
		try {
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			logger.info(userObj.getUserName()+ " started feedback job Id:"+jobsObj.getJobId());
			
			String loc = requestObject.getParameter("loc");
			jobsObj = jobService.getJobDetails(jobsObj.getJobId());
			
			jobsObj.setCreatedDateTime(jobService.convertDateToString(jobsObj.getCreatedDateTime()));
			jobsObj.setRequestedDateTime(jobService.convertDateToString(jobsObj.getRequestedDateTime()));
			jobsObj.setScheduleDateTime(jobService.convertDateToString(jobsObj.getScheduleDateTime()));
			jobsObj.setCheckInDateTime(jobService.convertDateToString(jobsObj.getCheckInDateTime()));
			jobsObj.setCheckOutDateTime(jobService.convertDateToString(jobsObj.getCheckOutDateTime()));
			
			requestObject.setAttribute("loc", loc);			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
			
		return SUCCESS;
	}
	
	public String saveFeedbackJob() throws Exception {
		try {
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			jobsObj.setFkFeedBackBy(userObj.getUserId());
			jobService.saveFeedbackJob(jobsObj);			
			
			Jobs jObj = jobService.getJobDetails(jobsObj.getJobId());
			jObj.setHappyWithService(jobsObj.getHappyWithService());
			
			jobService.sendSms(jObj.getFkClientId(), "feedBack", 1, 4, jObj.getCustomerObj().getCustomerMobNo(),jObj);
			jobService.sendSms(jObj.getFkClientId(), "feedBack", 3, 4, jObj.getEasyfixterObj().getEasyfixerNo(),jObj);
				
			/*
			String custSMS = jObj.getServiceTypeObj().getServiceTypeName() + " work done! EasyFixer " + jObj.getEasyfixterObj().getEasyfixerName() + " finished the job you rated him " + jobsObj.getEasyfixerRating() + "/5 Book for new work or complaint on 8882122666";
			String efrSMS = "Job Done! Work Rating " + jobsObj.getEasyfixerRating() + "/5 On time Yes/No Customer Happy " + jobsObj.getHappyWithService() + " Total A/c Balance * Call 01139595445 to know more";
				
			
			//System.out.println(custSMS);
			//System.out.println(efrSMS);
			if(jObj.getFkClientId() != 25 && jObj.getFkClientId() != 64)
				SmsSender.sendSms(custSMS, jObj.getCustomerObj().getCustomerMobNo());
			
			SmsSender.sendSms(efrSMS, jObj.getEasyfixterObj().getEasyfixerNo());
			*/
			logger.info(userObj.getUserName()+ "finished feedback job Id:"+jobsObj.getJobId());
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
			
		}
		
			
		return SUCCESS;
	}
	
	public String cancelCallLateJobAndShowNewBookingPage() throws Exception{
		Jobs job = jobService.getJobDetails(jobsObj.getJobId());
		userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
		job.setCancelBy(userObj.getUserId());
		
		
		job.setEnumId(36);
		job.setCanceledOn(10);
		job.setComments("canceling this call later job and booking another job"); 
		jobService.saveCancelJob(job);
		logger.info(userObj.getUserName()+ "completed cancling job Id:"+job.getJobId());
		requestObject.setAttribute("mobNo", job.getMobileNo());
		System.out.println(job.getMobileNo());
		return SUCCESS;
	}
	public String changeDate() throws Exception {
		
		//jobService.changeRequestedDate(jobsObj.getJobId());
		requestObject.setAttribute("flagDate", requestObject.getParameter("flag"));
						
		return SUCCESS;
	}
	
	public String updateDate() throws Exception {
		try {
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			jobsObj.setFkCreatedBy(userObj.getUserId());
			
			int dtflag = Integer.parseInt(requestObject.getParameter("dtflag")); // 1: Requested Date, 2: Schedule Date
			String oldDate = requestObject.getParameter("oldDate");
			
			String requestDate = jobService.convertStringToDate(jobsObj.getRequestedDateTime());			
			jobsObj.setRequestedDateTime(requestDate);
			
			int status = jobService.changeRequestedDate(jobsObj);
			String changeDate = jobService.convertDateToString(requestDate);
			
			if(status == 1) {
				requestObject.setAttribute("changedDate", changeDate);
			}
			else {
				requestObject.setAttribute("changedDate", oldDate);
			}
			
			requestObject.setAttribute("status", status);
			requestObject.setAttribute("jobId", jobsObj.getJobId());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
			
		}
		
			return SUCCESS;
		
	}
	public String changeJobDesc() throws Exception {
		
		//jobService.changeRequestedDate(jobsObj.getJobId());
	//	requestObject.setAttribute("flagDate", requestObject.getParameter("flag"));
		requestObject.setAttribute("flagJobDesc", requestObject.getParameter("flag"));
						
		return SUCCESS;
	}
	public String updateJobDesc() throws Exception {
		try {
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			jobsObj.setFkCreatedBy(userObj.getUserId());
			
			//int dtflag = Integer.parseInt(requestObject.getParameter("dtflag")); // 1: Requested Date, 2: Schedule Date
			String oldDesc = requestObject.getParameter("oldDesc");
			
			//String requestDate = jobService.convertStringToDate(jobsObj.getRequestedDateTime());			
			//jobsObj.setJobDesc(jobsObj.getJobDesc());
			
			int status = jobService.changeJobDesc(jobsObj);
						
			if(status == 1) {
				requestObject.setAttribute("changedDesc", jobsObj.getJobDesc());
			}
			else {
				requestObject.setAttribute("changedDesc", oldDesc);
			}
			
			requestObject.setAttribute("status", status);
			requestObject.setAttribute("jobId", jobsObj.getJobId());
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
			
		}
		
			return SUCCESS;
		
	}
	
	public String addEditJobService() throws Exception {
			
		try {		
			clientServiceList = clientService.getClientServiceListByClientIdAndServiceTypeId(jobsObj.getFkClientId(),jobsObj.getFkServiceTypeId());
			requestObject.setAttribute("flag", requestObject.getParameter("flag"));
			jobServiceList = jobService.getJobServiceList(jobsObj.getJobId(),1); // 1 for active service
			
			requestObject.setAttribute("total", requestObject.getParameter("total"));
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		return SUCCESS;
	}

	public String updateJobServices() throws Exception {
		
		try {
				String service = jobsObj.getClientServiceIds();			
				List<String> serviceList = new ArrayList<String>(Arrays.asList(service.split(",")));
				
				jobService.updateJobServices(jobsObj.getJobId(),jobsObj.getClientServiceIds(),serviceList);
				
				jobServiceList = jobService.getJobServiceList(jobsObj.getJobId(),1); // 1 for active service
				double total = 0;
				int star = 0;
				for (JobSelectedServices s : jobServiceList) {
					if(s.getJobChargeType() == 0 && Double.parseDouble(s.getTotalCharge()) <= 0)
						star = 1;
					else 
						total = total + Double.parseDouble(s.getTotalCharge());					
				}
				
				requestObject.setAttribute("totalSum", total);
				requestObject.setAttribute("star", star);
				requestObject.setAttribute("service", service);
				requestObject.setAttribute("flag", requestObject.getParameter("flag"));
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
			
		}

		return SUCCESS;
	}	

	public String addJobComment() throws Exception {
		int commentOn = jobsObj.getCommentedOn(); //flag chceckin page:2,checkout page: 3, schedule page:1
		//System.out.println("flag: " +jobsObj.getCommentedOn()+":"+ commentOn);
		if(commentOn==1)
			enumReasonList = jobService.getEnumReasonList(5);
		else if(commentOn==2)
			enumReasonList=	jobService.getEnumReasonList(6);
		else if(commentOn==3)
			enumReasonList =jobService.getEnumReasonList(7);
		else if(commentOn==15)
			enumReasonList =jobService.getEnumReasonList(15);
		else
			enumReasonList=jobService.getEnumReasonList(0);
		
		//System.out.println("enumReasonList"+enumReasonList);
		return SUCCESS;
	}
	
	public String saveJobComment() throws Exception {		
		try {
				userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
				jobsObj.setCommentedBy(userObj.getUserId());
				jobService.saveJobComment(jobsObj);
			
			logger.info(userObj.getUserName()+" saved job comment for job id: "+jobsObj.getJobId() );
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		return SUCCESS;
	}
	
	public String jobCommentList() throws Exception {		
		try {
				commentList = jobService.jobCommentList(jobsObj.getJobId());
							
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		return SUCCESS;
	}
	public String jobImageList() throws Exception {		
		try {
				JobImage  jobImage = new JobImage();
				jobImage.setJobId(jobsObj.getJobId());
				jobImageList = jobService.jobImageList(jobImage);
							
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		return SUCCESS;
	}
	
	
	public String cancelJob() throws Exception {
		try{
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			logger.info(userObj.getUserName()+ "started cancling job Id:"+jobsObj.getJobId());
			
		String loc = requestObject.getParameter("loc");
		enumReasonList = jobService.getEnumReasonList(1); // 1 for cancel reason
		requestObject.setAttribute("loc", loc);
		}
		catch(Exception e){
			e.printStackTrace();
			logger.catching(e);
		}
		return SUCCESS;
	}
	
	public String saveCancelJob() throws Exception {		
		try {
				userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
				jobsObj.setCancelBy(userObj.getUserId());
				jobService.saveCancelJob(jobsObj);
				logger.info(userObj.getUserName()+ "completed cancling job Id:"+jobsObj.getJobId());
				
				Jobs jObj = jobService.getJobDetails(jobsObj.getJobId());
				jobService.sendSms(jObj.getFkClientId(), "cancel", 1, 4, jObj.getCustomerObj().getCustomerMobNo(),jObj);
				jobService.sendSms(jObj.getFkClientId(), "cancel", 3, 4, jObj.getEasyfixterObj().getEasyfixerNo(),jObj);
		
				//snapdeal api integration
				if(jObj.getFkClientId()==18){
				snapdealClient.updateSnapDealServiceStatus(jObj);
				}
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
			
		}
		return SUCCESS;
	}
	public String changeJobOwner() throws Exception{
		
		String acttitle= "Easyfix : Change Job Owner";
		setActMenu("Change Job Owner");
		setActParent("Jobs");
		setTitle(acttitle);
		
		try {
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			
			jobList = jobService.getjobListForChangeOwner(userObj.getUserId(),userObj.getRoleId());
			userlist = userService.getUserListForChangeOwner();
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
			
		}
		return SUCCESS;
	}
	
	public String updateJobOwner() throws Exception{
		try {
			
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			jobsObj.setOwnerChangeBy(userObj.getUserId());
			jobService.updateJobOwner(jobsObj);
			logger.info("owner change job:"+jobsObj.getOwnerJobIds()+"::By:"+jobsObj.getOwnerChangeBy()+
					" current owner: "
					+jobsObj.getJobOwner());
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
			
		}
		
		return SUCCESS;
	}
	public String efrJobRejections() throws Exception{
		try {
			rejectReasonList = jobService.getEfrRejectReasonList();
			String divId = requestObject.getParameter("divId");
			requestObject.setAttribute("divId", divId);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		return SUCCESS;
	}
	public String recordEfrJobRejections() throws Exception{
		try {
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			jobsObj.setCommentedBy(userObj.getUserId());
			jobService.recordEfrJobRejections(jobsObj);
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
			
			
		}
		return SUCCESS;
	}
	
	public String uploadJobExcelFile() throws Exception{
		String acttitle= "Easyfix : Upload Jobs";
		setActMenu("Upload Jobs");
		setActParent("Jobs");
		setTitle(acttitle);
		
		return SUCCESS;
	}

	public String readExcelForJobUpload() throws Exception{
		try{
			//System.out.println(jobsObj.getJobUploadByExcelFile().getName());
			String[] temp =jobsObj.getJobUploadByExcelFile().getName().split("\\.");
			String sysGeneratedFileName = "ExcelJob" + new Date().getTime() + "." + temp[1];
			String fullFileName = uploadPathForJobAndRc+sysGeneratedFileName;
			//System.out.println(fullFileName);
			jobsObj.setJobUploadByExcelFileName(fullFileName);			
			File theFile = new File(fullFileName);
			
			FileUtils.copyFile(jobsObj.getJobUploadByExcelFile(), theFile);
			//jobsObj.setJobUploadByExcelFileName(sysGeneratedFileName);
			
			//String excelFilePath = "D:/job.xlsx";
		    int uploadedBy = ((User) requestObject.getSession().getAttribute(Constants.USER_OBJECT)).getUserId();
		   // List<Jobs> jobList = jobService.readBooksFromExcelFile(excelFilePath);
		    List<Jobs> jobList = jobService.readBooksFromExcelFile(fullFileName);
		    int noOfJobsUploaded =0;
		    for(Jobs j:jobList){
/*
		    	System.out.println(j.getCustomerObj().getCustomerMobNo());
		    	System.out.println(j.getCustomerObj().getCustomerName());
		    	System.out.println(j.getCustomerObj().getCustomerEmail());
		    	System.out.println(j.getFkClientId());
		    	System.out.println(j.getClientRefId());
		    	System.out.println(j.getFkServiceTypeId());
		    	System.out.println(j.getClientServiceIds());
		    	System.out.println(j.getJobDesc());
		    	System.out.println(j.getRequestedDateTime());
		    	System.out.println(j.getAddressObj().getAddress());
		    	System.out.println(j.getAddressObj().getCityObj().getCityId());
		    	System.out.println(j.getAddressObj().getGpsLocation());
		    	System.out.println(j.getAddressObj().getPinCode());
		    	System.out.println(j.getJobOwner());
		    	System.out.println("===========================================");
		    	
		    	List<String> serviceList=  new ArrayList<String>();
		    	if(j.getClientServiceIds()!=null){
		    	String[] serviceArray =  j.getClientServiceIds().split(",");
		    	for(int i=0;i<serviceArray.length;i++){
		    		String service = serviceArray[i].trim();
		    		serviceList.add(service);
		    	}
		    	}
		    	else
		    		serviceList.add("0");
		    	//System.out.println(serviceList);
		    	*/
		    	j.setFkCreatedBy(uploadedBy);
		    	int insertedJobId = jobService.addJobFromExcel(j);
		    	if(insertedJobId>0)
		    		++noOfJobsUploaded;
		    	//System.out.println("pojo made: "+insertedJobId);
		    }

		   // requestObject.setAttribute("msg","Total Jobs Uploaded: "+jobList.size()+",,"+noOfJobsUploaded);

			    msg = "Total valid Jobs Uploaded by Excel: "+jobList.size()+"    No of jobs created: "+noOfJobsUploaded;
			    /*requestObject.setAttribute("msg","Toal Jobs Uploaded: "+jobList.size());*/

			}
			catch(Exception e){
				e.printStackTrace();
				logger.catching(e);
			}
			
		return SUCCESS;
	}

	
public String confirmJob() throws Exception {
		int jobId = jobsObj.getJobId();
		String loc = requestObject.getParameter("loc");
		requestObject.setAttribute("loc", loc);
		jobsObj.setJobId(0);
		jobsObj = jobService.getLeadJobDetails(jobId);
		
		addressList = null;
		clientList = clientService.getClientList(1);
		cityList = cityService.getCityList();
		custObj = customerService.getCustomerDetailsByMobileNo(jobsObj.getMobileNo());
		if(custObj != null) {
			addressList = customerService.getAddressList(custObj.getCustomerId());
		}
		logger.info("excel job is being confirmed for customer "+jobsObj.getCustomerObj().getCustomerMobNo());
		
		return SUCCESS;
	}

public String getMaxDisTravelledByEfr(){
	String maxDis;
	String cityDis = jobsObj.getAddressObj().getCityObj().getMaxDistance();
	int clientDis =jobsObj.getClientObj().getTravelDist();
	int cityDisInt = Integer.valueOf(cityDis);
	int clientId = jobsObj.getFkClientId(); // skip for retail
	//if(clientId==1)
	//		maxDis= cityDis;
	//else{
			if(clientDis>cityDisInt)
					maxDis= String.valueOf(clientDis);
			else 
					maxDis = cityDis;
	//	}
	System.out.println("max dis travelled for efr for this job ="+maxDis);
	return maxDis;
}
public String pauseJob(){
	requestObject.setAttribute("flagDate", requestObject.getParameter("flag"));
	System.out.println(jobsObj.getJobId());
	System.out.println(jobsObj.getRequestedDateTime());
	return SUCCESS;
}

public String getCustomerJobHistory() {
	
	try {
		jobList = jobService.getCustomerJobList(jobsObj.getFkCustomerId());
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	return SUCCESS;
}

public String callLater() {
		String acttitle= "Easyfix : Call Later";
		setActMenu("Call Later");
		setActParent("Jobs");
		setTitle(acttitle);
		userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
		
	try {		
		
		//jobsObj.setJobStatus(9); //9 for Call Later Jobs
		int jobStatus = 9;
		jobList = jobService.getJobListByStatus(jobStatus,userObj.getUserId());
	} catch (Exception e) {
		e.printStackTrace();
	}
	return SUCCESS;
}
	
public String confirmCallLaterJob() {
	try {
			jobsObj = jobService.getJobDetails(jobsObj.getJobId());
			addressList = null;
			clientList = clientService.getClientList(1);
			cityList = cityService.getCityList();
			serviceTypeList = serviceTypeService.getSerTypeList(1);
			custObj = customerService.getCustomerDetailsById(jobsObj.getFkCustomerId());
			if(custObj != null) {
				addressList = customerService.getAddressList(custObj.getCustomerId());
			}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return SUCCESS;
}

//doubt in sms
public String addUpdateCallLaterJob(){
	try {
		userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
		logger.info(userObj.getUserName()+" started addUpdateCallLaterJob id: "+jobsObj.getJobId());
		if(jobsObj.getJobStatus() == 7 || jobsObj.getJobStatus() == 9) // 7 for enquiry and 9 for call later
		{
			List<String> serviceList = new ArrayList<String>();
			if(jobsObj.getRequestedDateTime() != "")
			{
				String requestDate = jobService.convertStringToDate(jobsObj.getRequestedDateTime());
				jobsObj.setRequestedDateTime(requestDate);
			}
			else
			{
				jobsObj.setRequestedDateTime(null);
			}
			int status = jobService.addUpdateCallLaterJob(jobsObj,serviceList, userObj.getUserId());
			if(status > 0) {
				Jobs jObj = jobService.getJobDetails(jobsObj.getJobId());
				jobService.sendSms(jObj.getFkClientId(), "callLater", 1, 4, jobsObj.getMobileNo(),jObj);
				
			/*	if(jobsObj.getFkClientId() == 1) {
					String customerMsg = "Thanks for choosing EasyFix*Getting a Plumber Carpenter Electrician is no more a HASSLE* get it on a call anywhere in  India on local market prices.";
					SmsSender.sendSms(customerMsg, jobsObj.getMobileNo());
				}
				*/
			}
		}
		else 
		{
			if(jobsObj.getRequestedDateTime() != "")
			{
				String requestDate = jobService.convertStringToDate(jobsObj.getRequestedDateTime());
				jobsObj.setRequestedDateTime(requestDate);
			}
			
			String service = jobsObj.getClientServices();			
			List<String> serviceList = new ArrayList<String>(Arrays.asList(service.split(",")));
			
			int status = jobService.addUpdateCallLaterJob(jobsObj,serviceList, userObj.getUserId());
			
			if(status > 0) {
				Jobs jObj = jobService.getJobDetails(status);
				jobService.sendSms(jObj.getFkClientId(), "bookCall", 1, 4, jobsObj.getMobileNo(),jObj);
				
				/*if(jobsObj.getJobStatus() == 0 && jobsObj.getFkClientId() == 1) {
					String customerMsg1 = "Your booking is confirmed. Delivery by EasyFix for " + jObj.getServiceTypeObj().getServiceTypeName() + " on " + jobsObj.getRequestedDateTime() 
							+ ". Confirmation on availability. No visiting charges.";
					SmsSender.sendSms(customerMsg1, jobsObj.getMobileNo());
				}
				if(jobsObj.getJobStatus() == 0 && jobsObj.getFkClientId() > 1) {
					String customerMsg2 = jObj.getServiceTypeObj().getServiceTypeName() + " booked from EasyFix for " + jObj.getClientObj().getClientName() 
							+ ". An EasyFixer will reach at " + jobsObj.getRequestedDateTime()  + ". Get Plumber Electrician Carpenter on 8882122666";
					if(jObj.getFkClientId() != 25 && jObj.getFkClientId() != 64)
						SmsSender.sendSms(customerMsg2, jobsObj.getMobileNo());
				}
				*/
			}
		
		}
		
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	}
	return SUCCESS;
		
			
}


public String inquiryJob() throws Exception {
	try{
		requestObject.setAttribute("enq", requestObject.getParameter("enq"));
		enumReasonList = jobService.getEnumReasonList(2); // 1 for enqury reason
		
	}
	catch(Exception e){
		e.printStackTrace();
		logger.catching(e);
	}
	return SUCCESS;
}
public String getRoadDistance() throws Exception {
	userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
	String easyfixerGps = requestObject.getParameter("custGPS");
	String cusGps = requestObject.getParameter("efrGPS");
	
	logger.info(userObj.getUserName()+" Calculated distance : "+easyfixerGps +" == "+cusGps);
	double distance =0;
	try{
	Map<String, ResultObject> m = latLong.getActualDistance(easyfixerGps,cusGps); // get distance from google server
	ResultObject distanceObj = m.get(easyfixerGps);
	
	if(Double.valueOf(distanceObj.getDistance())!=null)
	 distance = Math.round((Double.valueOf(distanceObj.getDistance()))/1000);
	}
	
	catch(Exception e){
		e.printStackTrace();
	}
		requestObject.setAttribute("distance", distance);
	//System.out.println(distance);		
		
	return SUCCESS;
}

public String getCrmSmsList(){
	try {
		
		 crmSmsList = jobService.getCrmSmsList();
		
		 requestObject.setAttribute("crmSmsList", crmSmsList);
		 requestObject.setAttribute("mobileNo", jobsObj.getMobileNo());
		 requestObject.setAttribute("jobId", jobsObj.getJobId());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return SUCCESS;
}

public String sendSmsFromCRM() {
	String mobileNo = jobsObj.getMobileNo();
	String smsId = requestObject.getParameter("smsId");
	int jobId = jobsObj.getJobId();
	int sms_id = Integer.valueOf(smsId);
	try {
		Jobs job = jobService.getJobDetails(jobId);
		String sms=null;
		if(crmSmsList==null){
				crmSmsList = jobService.getCrmSmsList();
			} 
		for(Sms s:crmSmsList ){
			if(s.getSmsId()==sms_id){

				sms = jobService.modifySmsToCustomer(s.getSms(), job);
			}
		}

	
	
		SmsSender.sendSms(sms, mobileNo);
		logger.info(sms+": sent to "+mobileNo);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return SUCCESS;
	
}
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Jobs getJobsObj() {
		return jobsObj;
	}

	public Jobs setJobsObj(Jobs jobsObj) {
		this.jobsObj = jobsObj;
		return jobsObj;
	}

	public JobService getJobService() {
		return jobService;
	}

	public ClientService getClientService() {
		return clientService;
	}

	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}

	public Customers getCustObj() {
		return custObj;
	}

	public void setCustObj(Customers custObj) {
		this.custObj = custObj;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public void setCustomerService(CustomerService customerService) {
		this.customerService = customerService;
	}

	public List<Address> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<Address> addressList) {
		this.addressList = addressList;
	}

	public List<Clients> getClientList() {
		return clientList;
	}

	public void setClientList(List<Clients> clientList) {
		this.clientList = clientList;
	}

	public List<ServiceType> getServiceTypeList() {
		return serviceTypeList;
	}

	public void setServiceTypeList(List<ServiceType> serviceTypeList) {
		this.serviceTypeList = serviceTypeList;
	}

	public ServiceTypeService getServiceTypeService() {
		return serviceTypeService;
	}

	public void setServiceTypeService(ServiceTypeService serviceTypeService) {
		this.serviceTypeService = serviceTypeService;
	}

	public List<Clients> getClientServiceList() {
		return clientServiceList;
	}

	public void setClientServiceList(List<Clients> clientServiceList) {
		this.clientServiceList = clientServiceList;
	}

	public CityService getCityService() {
		return cityService;
	}

	public List<City> getCityList() {
		return cityList;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	public void setCityList(List<City> cityList) {
		this.cityList = cityList;
	}

	public List<Jobs> getJobList() {
		return jobList;
	}

	public void setJobList(List<Jobs> jobList) {
		this.jobList = jobList;
	}

	public User getUserObj() {
		return userObj;
	}

	public void setUserObj(User userObj) {
		this.userObj = userObj;
	}

	public Address getAddObj() {
		return addObj;
	}

	public void setAddObj(Address addObj) {
		this.addObj = addObj;
	}

	public List<JobSelectedServices> getJobServiceList() {
		return jobServiceList;
	}

	public void setJobServiceList(List<JobSelectedServices> jobServiceList) {
		this.jobServiceList = jobServiceList;
	}

	public List<Easyfixers> getEasyfixerList() {
		return easyfixerList;
	}

	public void setEasyfixerList(List<Easyfixers> easyfixerList) {
		this.easyfixerList = easyfixerList;
	}

	public List<Easyfixers> getFinalEfrList() {
		return finalEfrList;
	}

	public void setFinalEfrList(List<Easyfixers> finalEfrList) {
		this.finalEfrList = finalEfrList;
	}

	public String getActMenu() {
		return actMenu;
	}

	public void setActMenu(String actMenu) {
		this.actMenu = actMenu;
	}

	public String getActParent() {
		return actParent;
	}

	public void setActParent(String actParent) {
		this.actParent = actParent;
	}

	public List<Jobs> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Jobs> commentList) {
		this.commentList = commentList;
	}
	@Override // for RestrictAccessToUnauthorizedActionInterceptor
    public String toString(){
    	return "JobAction";
    }

	public List<Jobs> getEnumReasonList() {
		return enumReasonList;
	}

	public void setEnumReasonList(List<Jobs> enumReasonList) {
		this.enumReasonList = enumReasonList;
	}

	public ActiveUserJobListDao getActiveUserJobListDao() {
		return activeUserJobListDao;
	}

	public void setActiveUserJobListDao(ActiveUserJobListDao activeUserJobListDao) {
		this.activeUserJobListDao = activeUserJobListDao;
	}

	public List<User> getUserlist() {
		return userlist;
	}

	public void setUserlist(List<User> userlist) {
		this.userlist = userlist;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public List<Jobs> getRejectReasonList() {
		return rejectReasonList;
	}

	public void setRejectReasonList(List<Jobs> rejectReasonList) {
		this.rejectReasonList = rejectReasonList;
	}

	public EasyfixerService getEasyfixerService() {
		return easyfixerService;
	}

	public void setEasyfixerService(EasyfixerService easyfixerService) {
		this.easyfixerService = easyfixerService;
	}

	public List<Customers> getCustomerList() {
		return customerList;
	}

	public void setCustomerList(List<Customers> customerList) {
		this.customerList = customerList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Latlong getLatLong() {
		return latLong;
	}

	public void setLatLong(Latlong latLong) {
		this.latLong = latLong;
	}

	/*public Clients getClientObj() {
		return clientObj;
	}

	public void setClientObj(Clients clientObj) {
		this.clientObj = clientObj;
	}
*/

	public void setCrmSmsList(List<Sms> crmSmsList) {
		this.crmSmsList = crmSmsList;
	}

	public List<JobImage> getJobImageList() {
		return jobImageList;
	}
	public void setJobImageList(List<JobImage> jobImageList) {
		this.jobImageList = jobImageList;
	}
	
	
	public List<Skill> getSkillList() {
		return skillList;
	}
	public void setSkillList(List<Skill> skillList) {
		this.skillList = skillList;
	}
	public SkillService getSkillService() {
		return skillService;
	}
	public void setSkillService(SkillService skillService) {
		this.skillService = skillService;
	}
	public static void main(String[] args)throws Exception{
		
		snapdealDateFormat d1 = new snapdealDateFormat();
		snapdealDateFormat d2 = new snapdealDateFormat();
		d1.setDay(1);d1.setHour(12);d1.setMinute(20);d1.setMonth(1);d1.setYear(2017);
		d2.setDay(2);d2.setHour(12);d2.setMinute(20);d2.setMonth(1);d2.setYear(2017);
		snapdealUpdateServiceStatus s = new snapdealUpdateServiceStatus();
		
		s.setCallType("abc");s.setCaseStatus("SERVICE_DELIVERED ");
		s.setCaseId("1000");s.setEndDate(d2);s.setOrderId("10001");s.setRemarks("remark");
		s.setStartDate(d1);s.setVendorCode("S0d02c");s.setVendorStatus("test");
		//jobsObj.getDistanceTraveledByEasyfixer();
		
	//	DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost postRequest = new HttpPost(
				"https://staging-apigateway.snapdeal.com/sts/api/sts/updateServiceStatus");
	//	ObjectMapper mapper = new ObjectMapper();
	//	HttpEntity entity=new StringEntity(mapper.writeValueAsString(job), "application/json");
	//	patchRequest.setEntity(entity);
		//Gson gson = new Gson();
//		SerializerFactory s= new SerializerFactory();
//		s.
		ObjectWriter ow = new ObjectMapper().writer();
		String json = ow.writeValueAsString(s);
		
		System.out.println(json);
		StringEntity  entity = new StringEntity (json);
		
		postRequest.setEntity(entity);
//		ObjectMapper mapper= new ObjectMapper();
//		mapper.
		postRequest.addHeader("Content-Type","application/json");
		postRequest.addHeader("x-seller-authz-token","9d002821-b47d-4763-9375-50c78ea0bcd5");
		postRequest.addHeader("x-auth-token","8f28b7d068f642858bca0f0af574ad55");

//		postRequest.addHeader("clientId", "18");
	//	patchRequest.addHeader("Accept","application/json");
		//CredentialsProvider credentialsProvider= new BasicCredentialsProvider();
		//credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("easyfixcrm", "09dce46b-5b88-424f-a401-b20390aab6d5"));
		HttpClient client = HttpClientBuilder.create().build();//.setDefaultCredentialsProvider(credentialsProvider).build();
		
		HttpResponse response =client.execute(postRequest);
		ObjectReader or = new ObjectMapper().reader();
		
		 JSONObject output = new JSONObject(EntityUtils.toString(response.getEntity()));
		// System.out.println(output);
		 System.out.println(output.toString());
		System.out.println(response.getEntity().toString());
		System.out.println(response.getStatusLine().toString());
		
	}
	class SMTPAuthenticator extends Authenticator {
		private String userName;
		private String password;
		
		public SMTPAuthenticator(String userName, String password)
		{
			this.userName = userName;
			this.password = password;
		}

		public PasswordAuthentication getPasswordAuthentication()
		{
			return new PasswordAuthentication(userName, password);
		}
	}
	
	
	private static int generateOtp()
	{
		Random r = new Random();
		int n = 100000 + r.nextInt(900000);
		return n;
	}
	
}
