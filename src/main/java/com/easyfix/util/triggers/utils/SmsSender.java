package com.easyfix.util.triggers.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easyfix.util.PropertyReader;
import com.easyfix.util.triggers.SMSReporting;


public class SmsSender
{
	static Logger log = LogManager.getLogger(SMSReporting.class);

	static PropertyReader props = null;

	public static void sendSms(String sms, String mobile) throws Exception
	{
		try
		{
			props = PropertyReader.getInstance("");

			log.info("Sending message to :" + mobile);
			HttpClient client = new DefaultHttpClient();
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("From", "<ExoPhone>"));
			postParameters.add(new BasicNameValuePair("To", mobile));
			String body = sms;
			String out = new String(body.getBytes("UTF-8"), "ISO-8859-1");
			postParameters.add(new BasicNameValuePair("Body", out));
			String sid = props.getValue("exotel.sid");
			// Replace <token> with your secret token
			String authStr = sid + ":" + props.getValue("exotel.token");
			String url = "https://" + authStr + props.getValue("exotel.dns") + sid + props.getValue("exotel.uri");

			byte[] authEncBytes = Base64.encodeBase64(authStr.getBytes());
			String authStringEnc = new String(authEncBytes);
			HttpPost post = new HttpPost(url);
			post.setHeader("Authorization", "Basic " + authStringEnc);
			try
			{
				post.setEntity(new UrlEncodedFormEntity(postParameters));
			}
			catch (UnsupportedEncodingException e)
			{
				log.error("Error while sending sms to: " + mobile + " :" + e.getMessage());
				e.printStackTrace();
			}
			try
			{
				HttpResponse response = client.execute(post);
				int httpStatusCode = response.getStatusLine().getStatusCode();
				log.info("Status while sending sms to mobile :" + mobile + " :" + httpStatusCode);
				HttpEntity entity = response.getEntity();
				log.info("Response from exotel for mobile: " + mobile + " :" + EntityUtils.toString(entity));
			}
			catch (Exception e)
			{
				log.error("Error while sending sms to: " + mobile + " :" + e.getMessage());
				e.printStackTrace();
			}

		}
		catch (Exception e)
		{
			log.error("Exception occuureed while sending sms to :" +mobile + " " +e.getMessage());
		}
	}

}
