package com.easyfix.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class SmsSender {

	public static String sendSms(String msg,String mobNumber) throws Exception {
		String postData="";
		String retval = "";
		String user = "retaility";
		String password = "sfs#426HSwLK34";
		String senderId = "EsyFix";
		String messageType = "N";
		String DReports = "N";
	//	boolean sendMSG = false;
		boolean sendMSG = true;
		
		if(sendMSG) {
			postData += "User=" + URLEncoder.encode(user,"UTF-8") + "&passwd=" +URLEncoder.encode(password,"UTF-8")  + "&mobilenumber=" + mobNumber + "&message=" + URLEncoder.encode(msg,"UTF-8") + "&sid=" + senderId + "&mtype=" + messageType + "&DR=" + DReports;
			URL Callurl = new URL("http://smscountry.com/SMSCwebservice_Bulk.aspx");
			//URL url = new URL("http://www.smscountry.com/SMSCwebservice.asp");
			
			HttpURLConnection urlconnection = (HttpURLConnection) Callurl.openConnection();
	
			// If You Are Behind The Proxy Server Set IP And PORT else Comment Below 4 Lines
			
			/*Properties sysProps = System.getProperties();
			sysProps.put("proxySet", "true");
			sysProps.put("proxyHost", "Proxy Ip");
			sysProps.put("proxyPort", "PORT");*/
	
			urlconnection.setRequestMethod("POST");
			urlconnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			urlconnection.setDoOutput(true);
			OutputStreamWriter out = new OutputStreamWriter(urlconnection.getOutputStream());
			out.write(postData);
			out.close();
			BufferedReader in = new BufferedReader(	new InputStreamReader(urlconnection.getInputStream()));
			String decodedString;
			while ((decodedString = in.readLine()) != null) {
				retval += decodedString;
			}
			in.close();

			System.out.println(retval);
		}
		
		return retval;
	}

}
