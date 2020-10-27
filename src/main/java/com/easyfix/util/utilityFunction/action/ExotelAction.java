package com.easyfix.util.utilityFunction.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLPeerUnverifiedException;
import javax.security.cert.X509Certificate;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.HttpsURLConnection;

import com.easyfix.util.CommonAbstractAction;


public class ExotelAction extends CommonAbstractAction {

	
	private static final long serialVersionUID = 1L;
	private String actMenu;
	private String actParent;
	private String title;
	private String msg;


	
	public String whiteListing() throws Exception {
		try {
			String acttitle= "Easyfix : White Listing Request";
			setActMenu("WhiteListing");
			setActParent("Jobs");
			setTitle(acttitle);
			requestObject.setAttribute("msg", requestObject.getParameter("msg"));

		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		
		return SUCCESS;
	}
	 private void testIt(){

	      String https_url = "https://core.easyfix.in/v1/jobs/123";
	      URL url;
	      try {

		     url = new URL(https_url);
		     HttpsURLConnection con = (HttpsURLConnection)url.openConnection();

		     //dumpl all cert info
		     print_https_cert(con);

		     
		     HttpClient client =new DefaultHttpClient();//HttpClientBuilder.create().build(); //new DefaultHttpClient();// 
		     String surl = "http://uat.easyfix.in/v1/jobs";
			 String authStr = "easyfixcrm" + ":" + "09dce46b-5b88-424f-a401-b20390aab6d5";
			 byte[] authEncBytes = Base64.encodeBase64(authStr.getBytes());
			    String authStringEnc = new String(authEncBytes);
			    HttpPost get = new HttpPost(surl);
			    get.setHeader("Authorization", "Basic " + authStringEnc);
			    get.addHeader("Content-type", "application/json");
			    List <NameValuePair> data = new ArrayList <NameValuePair>();
			    data.add(new BasicNameValuePair("jobDesc","Geyser wire plug change work"));
			    
			    
			    get.setEntity(new UrlEncodedFormEntity(data));
			    
			    try {
					HttpResponse response = client.execute(get);
					System.out.println(response);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		     //dump all the content
		//     print_content(con);

	      } catch (MalformedURLException e) {
		     e.printStackTrace();
	      } catch (IOException e) {
		     e.printStackTrace();
	      }

	   }
	 private void print_https_cert(HttpsURLConnection con){

		    if(con!=null){

		      try {

			System.out.println("Response Code : " + con.getResponseCode());
			System.out.println("Cipher Suite : " + con.getCipherSuite());
			System.out.println("\n");

			Certificate[] certs = con.getServerCertificates();
			for(Certificate cert : certs){
			   System.out.println("Cert Type : " +cert.getType());
			   System.out.println("Cert Hash Code : " + cert.hashCode());
			   System.out.println("Cert Public Key Algorithm : "
		                                    + cert.getPublicKey().getAlgorithm());
			   System.out.println("Cert Public Key Format : "
		                                    + cert.getPublicKey().getFormat());
			   System.out.println("\n");
			}

			} catch (SSLPeerUnverifiedException e) {
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			}

		     }

		   }
	 private void print_content(HttpsURLConnection con){
			if(con!=null){

			try {

			   System.out.println("****** Content of the URL ********");
			   BufferedReader br =
				new BufferedReader(
					new InputStreamReader(con.getInputStream()));

			   String input;

			   while ((input = br.readLine()) != null){
			      System.out.println(input);
			   }
			   br.close();

			} catch (IOException e) {
			   e.printStackTrace();
			}

		       }

		   }

	public static void main(String[] arg){
		new ExotelAction().testIt();
		
		 /*HttpClient client = HttpClientBuilder.create().build();//new DefaultHttpClient();
		 String url = "https://core.easyfix.in/v1/jobs/122";
		 String authStr = "easyfixcrm" + ":" + "09dce46b-5b88-424f-a401-b20390aab6d5";
		 byte[] authEncBytes = Base64.encodeBase64(authStr.getBytes());
		    String authStringEnc = new String(authEncBytes);
		    HttpGet get = new HttpGet(url);
		    get.addHeader("Authorization", "Basic " + authStringEnc);
		    get.addHeader("Content-type", "application/json");
		    try {
				HttpResponse response = client.execute(get);
				System.out.println(response);
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	}
	
	public  String makeRequest() {
		
		String phoneNumber=requestObject.getParameter("mobileNo");
		
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
	   // System.out.println("url: "+url);
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
              String massege = ""+response.getStatusLine().getReasonPhrase();
             System.out.println(httpStatusCode + " is the status code.");
              HttpEntity entity = response.getEntity();
//              String massege = EntityUtils.toString(entity);
//              System.out.println(EntityUtils.toString(entity));
              requestObject.setAttribute("response", massege);
              requestObject.setAttribute("code", httpStatusCode);
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	   
	 return SUCCESS;
	}
	
	
	@Override
	public String toString(){
		return "ExotelAction";
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


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}

}
