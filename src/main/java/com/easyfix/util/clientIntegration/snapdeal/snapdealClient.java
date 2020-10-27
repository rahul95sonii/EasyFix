package com.easyfix.util.clientIntegration.snapdeal;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.easyfix.Jobs.action.JobAction;
import com.easyfix.Jobs.model.Jobs;

public class snapdealClient {
	private static final Logger logger = LogManager.getLogger(snapdealClient.class);

	public static void updateSnapDealServiceStatus( Jobs job) throws JsonGenerationException, JsonMappingException, IOException, ParseException, JSONException{
		snapdealDateFormat start=null; //= new snapdealDateFormat();
		snapdealDateFormat end=null; //= new snapdealDateFormat();
		String caseStatus=null;
		String remarks =null;
		final Set<Integer> DENIED_BY_VENDOR_cancelReasonId = new HashSet<Integer>(Arrays.asList(1,37,36,53,10,9,4,2,8));
		final Set<Integer> REFUSED_BY_CUSTOMER_cancelReasonId = new HashSet<Integer>(Arrays.asList(52,60,55,7,5,3));
		final Set<Integer> PARENT_PRODUCT_FAULTY_cancelReasonId = new HashSet<Integer>(Arrays.asList(54));
		final Set<Integer> CUSTOMER_NOT_AVAILABLE_cancelReasonId = new HashSet<Integer>(Arrays.asList(6));
		snapdealUpdateServiceStatus s = new snapdealUpdateServiceStatus();
		try{
			switch(job.getJobStatus()){
			
			case 1: 
				start = convertStringTosnapDealDate(job.getScheduleDateTime());
				end= convertStringTosnapDealDate(job.getRequestedDateTime());
				if(job.getJobFlag().equalsIgnoreCase("reschedule")){
					caseStatus = "SERVICE_RESCHEDULED";
					remarks = job.getEnumDesc();
				}
				else{
				caseStatus = "SERVICE_SCHEDULED";
				}
				
				break;
				
			case 3:
				
				caseStatus = "SERVICE_DELIVERED";
				break;
			case 6:
				//start = convertStringTosnapDealDate(job.getScheduleDateTime());
				//end= convertStringTosnapDealDate(job.getRequestedDateTime());
				
				if(DENIED_BY_VENDOR_cancelReasonId.contains(job.getEnumId())){
					caseStatus = "DENIED_BY_VENDOR";
					
				}
				else if(REFUSED_BY_CUSTOMER_cancelReasonId.contains(job.getEnumId())){
					caseStatus = "REFUSED_BY_CUSTOMER";
				}
				else if(PARENT_PRODUCT_FAULTY_cancelReasonId.contains(job.getEnumId())){
					caseStatus = "PARENT_PRODUCT_FAULTY";
				}
				else if(CUSTOMER_NOT_AVAILABLE_cancelReasonId.contains(job.getEnumId())){
					caseStatus = "CUSTOMER_NOT_AVAILABLE";
				}
					
				remarks = job.getEnumDesc();
			}
			
			
			s.setStartDate(start);
			s.setEndDate(end);
			s.setVendorCode("Sdum30");
			s.setCaseId(job.getJobId()+"");
			s.setCaseStatus(caseStatus);
			s.setOrderId(job.getClientRefId());
			s.setRemarks(remarks);
			
		//d1.setDay(1);d1.setHour(12);d1.setMinute(20);d1.setMonth(1);d1.setYear(2017);
		//d2.setDay(2);d2.setHour(12);d2.setMinute(20);d2.setMonth(1);d2.setYear(2017);
		
		
		//s.setCallType("abc");s.setCaseStatus("SERVICE_DELIVERED ");
		//s.setCaseId("1000");s.setEndDate(d2);s.setOrderId("10001");s.setRemarks("remark");
		//s.setStartDate(d1);s.setVendorCode("S0d02c");s.setVendorStatus("test");
		
		HttpPost postRequest = new HttpPost(
				/*"https://staging-apigateway.snapdeal.com/sts/api/sts/updateServiceStatus"*/
				"https://apigateway.snapdeal.com:443/sts/api/sts/updateServiceStatus");

		ObjectWriter ow = new ObjectMapper().writer();
		String json = ow.writeValueAsString(s);

		System.out.println(json);
		StringEntity  entity = new StringEntity (json);
		
		postRequest.setEntity(entity);

		postRequest.addHeader("Content-Type","application/json");
		//postRequest.addHeader("x-seller-authz-token","9d002821-b47d-4763-9375-50c78ea0bcd5");
		//postRequest.addHeader("x-auth-token","8f28b7d068f642858bca0f0af574ad55");
		postRequest.addHeader("x-seller-authz-token","76b33073-d663-421f-8df7-3ec6c810ae72");
		postRequest.addHeader("x-auth-token","09122d24-a157-3227-b97a-77cd746ca1aa");
		HttpClient client = HttpClientBuilder.create().build();//.setDefaultCredentialsProvider(credentialsProvider).build();
		
		HttpResponse response =client.execute(postRequest);
		//ObjectReader or = new ObjectMapper().reader();
		
		 JSONObject output = new JSONObject(EntityUtils.toString(response.getEntity()));
		 
		 logger.info("for job id:"+job.getJobId()+" request sent to Snapdeal.output:"+output.toString()
		 		+ " response:"+response.getStatusLine().toString());
		 System.out.println(output.toString());
		 System.out.println(response.getEntity().toString());
		 System.out.println(response.getStatusLine().toString());
		}
		catch(Exception e){
				logger.catching(e);
		}
	}
	
	
	public static snapdealDateFormat convertStringTosnapDealDate(String stringDate) throws java.text.ParseException{
		Date date =null;
		snapdealDateFormat snapDate= new snapdealDateFormat();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
		if(stringDate!=null){
			
			date = format.parse(stringDate);
			System.out.println(date);
			snapDate.setDay(date.getDate());
			snapDate.setHour(date.getHours());
			snapDate.setMinute(date.getMinutes());
			snapDate.setMonth(date.getMonth()+1);
			snapDate.setYear(date.getYear()+1900);
			
			//System.out.println(snapDate);
			
			/*
			System.out.println(date.getHours());
			
			System.out.println(date.getMinutes());
			System.out.println(date.getSeconds());
			System.out.println(date.getDate());
			System.out.println(date.getMonth()+1);
			System.out.println(date.getYear()+1900);
			*/
		}
		return snapDate;
		
	}
	
	public static void main(String arg[]){
		try {
			convertStringTosnapDealDate("2017-01-10 13:52:06");
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
