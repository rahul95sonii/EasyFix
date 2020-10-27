package com.easyfix.util;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;



public class GoogleAutenticationUtil {

	private static GoogleAutenticationUtil instance=null;
	
	//private static final String CLIENT_ID = "460695262887-0a2vnrorr8a1jttei3rvhlpvaevr6ssg.apps.googleusercontent.com";
	//private static final String CLIENT_SECRET = "abN5xcJYK5i-myTdQNOhF-3k";

	private static final String CLIENT_ID = "661858814283-a2p121vne1rjiko2k17m4qq55l15vuku.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "y_dg-5NVk4TA-g2MYteQofZD";

	/**
	 * Callback URI that google will redirect to after successful authentication
	 */
	private static final String CALLBACK_URI = "http://uat.easyfix.in:8080/easyfix/home";
	
	// start google authentication constants
	private static final Iterable<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email".split(";"));
	private static final String USER_INFO_URL = "https://www.googleapis.com/oauth2/v1/userinfo";
	private static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

  private String stateToken;
  private static GoogleAuthorizationCodeFlow flow = null;

	
	
	private GoogleAutenticationUtil(){
		flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT,
				JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, SCOPE).build();
		
		generateStateToken();
		
	}
	
	public static GoogleAutenticationUtil getInstance(){
		if(instance==null){
			synchronized (GoogleAutenticationUtil.class) {
			          if(instance==null)
			        	  instance=new GoogleAutenticationUtil();
			}
		}		
		
		
		return instance;
	}
	
	public int authenticateLoginCredential(String emailId,String password){
		/*1=SUCESS 0=FAIL*/
		
		int authenticationStatus=0;		
		
		final GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
		
		String buildUrl = url.setRedirectUri(CALLBACK_URI).setState(stateToken).build();
		
		
		return authenticationStatus;
	}
	
	private void generateStateToken(){
		
		SecureRandom sr1 = new SecureRandom();
		
		stateToken = "google;"+sr1.nextInt();
		
	}
	
	public String getStateToken(){
		return stateToken;
	}
	
	
	public String getUserInfoJson(final String authCode) throws IOException {

		final GoogleTokenResponse response = flow.newTokenRequest(authCode).setRedirectUri(CALLBACK_URI).execute();
		final Credential credential = flow.createAndStoreCredential(response, null);
		final HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory(credential);
		// Make an authenticated request
		final GenericUrl url = new GenericUrl(USER_INFO_URL);
		final HttpRequest request = requestFactory.buildGetRequest(url);
		request.getHeaders().setContentType("application/json");
		final String jsonIdentity = request.execute().parseAsString();

		return jsonIdentity;

	}
	
	
	
}
