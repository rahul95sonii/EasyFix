package com.easyfix.util;

/**
 * Example to whitelist numbers using Exotel API.
 * Recommended JRE 1.5 or greater
* @author Ritesh Shrivastav
+ */

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.commons.codec.binary.Base64;

@SuppressWarnings("deprecation")
public class WhitelistRequest extends CommonAbstractAction {
	

	private static final long serialVersionUID = 1L;
	public  String makeRequest() {
		String phoneNumber=new String("9899953302");
		
	    HttpClient client = new DefaultHttpClient();
    
	    String exotelSid = "easyfix";
	    String token = "9749447a9421366e15841e8d9ad48d2381bdcf04";
	    String exophone = "01133138127";
	   // String numbers = {"9971273289"};//, "Number2", "...", "NumberN"};
	    String numbers = phoneNumber.toString();
	    String authStr = exotelSid + ":" + token;
	    String url = "https://" + authStr +
	    			 "@twilix.exotel.in/v1/Accounts/" +
				 exotelSid + "/CustomerWhitelist";
	    System.out.println("url: "+url);
	    byte[] authEncBytes = Base64.encodeBase64(authStr.getBytes());
	    String authStringEnc = new String(authEncBytes);
    HttpPost post = new HttpPost(url);
	    post.setHeader("Authorization", "Basic " + authStringEnc);
	    ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    nameValuePairs.add(new BasicNameValuePair("VirtualNumber", exophone));
	  //  for(int i = 0; i < numbers.length; i++) {
		    nameValuePairs.add(new BasicNameValuePair("Number[]", numbers));
	//    }
	    try {
	      post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	      HttpResponse response = client.execute(post);
              int httpStatusCode = response.getStatusLine().getStatusCode();
              System.out.println(httpStatusCode + " is the status code."+response.getStatusLine());
              HttpEntity entity = response.getEntity();
              System.out.println(EntityUtils.toString(entity));
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	 return SUCCESS;
	}
	@Override
	public String toString(){
		return "WhitelistRequest";
	}
}

