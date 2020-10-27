package com.easyfix.util.clientIntegration.Idea;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

import com.easyfix.Jobs.model.Jobs;
import com.easyfix.util.clientIntegration.snapdeal.snapdealClient;

public class IdeaSendSms {
	private static final Logger logger = LogManager.getLogger(IdeaSendSms.class);
	static String url = "http://103.15.179.99:8282/QuickFixSmsApi/QuickFixSendSms";
	
	public static void sendSmsIdea(Jobs job,String jobType,String mobileNo){
		HttpParams parmas = new BasicHttpParams();
		parmas.setParameter("jobtype",jobType);
		parmas.setParameter("mobile", mobileNo);
		
		try{
		HttpGet get = new HttpGet(url);
		get.setParams(parmas);
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response =client.execute(get);
		//ObjectReader or = new ObjectMapper().reader();
		
		 	 
		 logger.info("for job id:"+job.getJobId()+" request sent to Idea."
		 		+ " response:"+response.getStatusLine().toString());
		
		 System.out.println(response.getEntity().toString());
		 System.out.println(response.getStatusLine().toString());
	}
		catch(Exception e){
			e.printStackTrace();
		}
}
	public static void main(String[] args){
		Jobs j = new Jobs();
		
		sendSmsIdea(j,"bookCall","919971273289");
	}
	
}
