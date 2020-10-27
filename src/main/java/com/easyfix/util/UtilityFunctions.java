package com.easyfix.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easyfix.Jobs.action.JobAction;
import com.easyfix.Jobs.model.AppNotications;
import com.easyfix.Jobs.model.AppNotificationResponse;
import com.easyfix.Jobs.model.Data;
import com.easyfix.Jobs.model.Notification;
import com.easyfix.easyfixers.delegate.EasyfixerServiceImpl;
import com.easyfix.easyfixers.model.Easyfixers;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UtilityFunctions {
	private static final Logger logger = LogManager.getLogger(UtilityFunctions.class);
	public static String convertStringToDate(String dateString) throws Exception {
		
		String formatteddate = null;
	    DateFormat rdf = new SimpleDateFormat( "dd MMM, yyyy");
	    DateFormat wdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = null;
	    try {
	    	date =  rdf.parse(dateString);
	     } catch ( ParseException e ) {
	         e.printStackTrace();
	     }
	    
	    if( date != null ) {
	    	formatteddate = wdf.format( date );
	        }
	    
	    return formatteddate;
	}
	
	
	public static long diffBetweenTwoDate(String fdate, String tdate) {
		
		String dateStart = fdate;
		String dateStop = tdate;
	
		//HH converts hour in 24 hours format (0-23), day calculation
		//SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
		Date d1 = null;
		Date d2 = null;
		long diffDays = 0;
	
		try {
			d1 = format.parse(dateStart);
			d2 = format.parse(dateStop);
	
			//in milliseconds
			long diff = d2.getTime() - d1.getTime();
	
		//	long diffSeconds = diff / 1000 % 60;
		//	long diffMinutes = diff / (60 * 1000) % 60;
		//	long diffHours = diff / (60 * 60 * 1000) % 24;
			diffDays = diff / (24 * 60 * 60 * 1000);
	
		/*	System.out.print(diffDays + " days, ");
			System.out.print(diffHours + " hours, ");
			System.out.print(diffMinutes + " minutes, ");
			System.out.print(diffSeconds + " seconds.");*/
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return diffDays;
	
	}


	public static Date convertStringToDateInDate(String dateString, String inFormat)
	{
	    Date date = null;
	    DateFormat df = new SimpleDateFormat(inFormat);
	    try{
	        date = df.parse(dateString);
	    }
	    catch ( Exception ex ){
	        System.out.println(ex);
	    }
	    return date;	   
	}
	
	public static String addDays(Date date, int days)
	{
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DATE, days); //minus number would decrement the days
	    Date myDate = cal.getTime();
	    String convertDate = new SimpleDateFormat("dd-MMM").format(myDate);
	   // return cal.getTime();
	    return convertDate;
	}
	
	public static String convertTimeStampToDateFormate(Long timeStamp, String formate)
	{
		String convertDate = "";
		DateFormat df = new SimpleDateFormat(formate);	    
	    try {
	    		Date date = new Date(timeStamp);
	    		convertDate = df.format(date);
	 	    
		} catch (Exception e) {
			e.printStackTrace();
		}
	   
	   return convertDate;
	}
	
	
	public static String addSubstractDaysInDate(Date curDate, int days, String outputFormat)
	{
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(curDate);
	    cal.add(Calendar.DATE, days); //minus number would decrement the days
	    Date myDate = cal.getTime();
	    String convertDate = new SimpleDateFormat(outputFormat).format(myDate);
	   // return cal.getTime();
	    return convertDate;
	}
	
	public static String dateToFormat(Date curDate, String outputFormat)
	{
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(curDate);
	    Date myDate = cal.getTime();	    
	    String convertDate = new SimpleDateFormat(outputFormat).format(myDate);

	    return convertDate;
	}
	
	public static String changeDateFormatToFormat(String dateString, String inputFormat, String outputFormat)
	{
		String formatteddate = null;
	    DateFormat rdf = new SimpleDateFormat(inputFormat);
	    DateFormat wdf = new SimpleDateFormat(outputFormat);
	    Date date = null;
	    try {
	    	date =  rdf.parse(dateString);
	     } catch ( ParseException e ) {
	         e.printStackTrace();
	     }
	    
	    if( date != null ) {
	    	formatteddate = wdf.format( date );
	        }
	    
	    return formatteddate;
	}
	
	
		
	
	public static void main(String[] args)throws Exception{
		
	
		String notificationMessage = "Click to accept/reject job " + "123";
		Notification notification = new Notification(notificationMessage, "New Job", "job", "High", "1",
		"123" + "#" + "1");
		System.out.println(sendNotification(notification,""));
		
	}
	
	public static String sendNotification(Notification notification, String deviceId)
	{
		DataOutputStream output = null;
		DataInputStream input = null;
		AppNotificationResponse res=null;
		try
		{
			URL url = new URL("https://gcm-http.googleapis.com/gcm/send");
			HttpsURLConnection urlConn = null;

			urlConn = (HttpsURLConnection) url.openConnection();

			urlConn.setDoInput(true);

			urlConn.setDoOutput(true);

			urlConn.setRequestMethod("POST");

			urlConn.setRequestProperty("Content-Type", "application/json");
			urlConn.setRequestProperty("Authorization", "Key=AIzaSyD9iXTQ65bB1p7U7rprvrWGNkZr4_k6Wzs");

			urlConn.connect();

			output = new DataOutputStream(urlConn.getOutputStream());

			ObjectMapper mapper = new ObjectMapper();
			// mapper.writeValue(resultFile, value);
			AppNotications appNotications = new AppNotications();
			Data d = new Data();
			// Notification notification = new Notification();
			// notification.setFlag(flag);
			// notification.setType(type);
			// notification.setTitle(title);
			// notification.setKey(key);
			// notification.setMessage(message);
			d.setNotification(notification);
			appNotications.setData(d);
			appNotications.setTo(deviceId);
			String content = mapper.writeValueAsString(appNotications);
			output.writeBytes(content);
			input = new DataInputStream(urlConn.getInputStream());
			String responseString = getStringFromInputStream(input);
			
			 res= mapper.readValue(responseString, AppNotificationResponse.class);
			System.out.println("notification response is :"+res);
			
			logger.info("Notification response for flag: " + notification.getFlag() + " ;key" + notification.getKey() + " is" + responseString);
		}
		catch(Exception e){
			logger.info("An exception occurred while sending notification "+ e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			if (output != null)
			{
				try
				{
					output.close();
					output.flush();
				}
				catch (IOException e)
				{
					logger.info("An exception occurred while closing out stream for notification "+ e.getMessage());
					e.printStackTrace();
				}
				
			}
			if (input != null)
			{
				try
				{
					input.close();
				}
				catch (IOException e)
				{
					logger.info("An exception occurred while closing out stream for notification "+ e.getMessage());
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		String result="unknown";
		if(res.getFailure()==1)
			result= "failed";
		else if(res.getSuccess()==1)
			result= "success";
		
		return result;

	}

	private static String getStringFromInputStream(InputStream is)
	{

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try
		{

			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null)
			{
				sb.append(line);
			}

		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (br != null)
			{
				try
				{
					br.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}

		return sb.toString();

	}
	public static String generateRandomNo(int length)
	{
		StringBuilder s = new StringBuilder();
		int[] number = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		char[] letters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
				'y', 'z' };
		Random r = new Random();
		s.append(letters[r.nextInt(24)]);
		for (int i = 0; i < length - 1; i++)
		{
			s.append(number[r.nextInt(10)]);
		}
		return s.toString();

	}

}
