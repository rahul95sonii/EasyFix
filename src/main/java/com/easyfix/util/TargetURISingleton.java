package com.easyfix.util;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.UriBuilder;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

public class TargetURISingleton {

	private volatile static WebTarget TargetURISingleton;
	
	private TargetURISingleton() {
		//TargetURISingleton=s;
	}
	public static URIBuilder getUriForHttpClient() throws URISyntaxException{
		URIBuilder uribuilder = new URIBuilder().setScheme("http")
				.setHost("localhost:8090");				
		return uribuilder;
	}
	
	public static CredentialsProvider getCredentialProviderForCRM() {
		CredentialsProvider credentialsProvider= new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("easyfixcrm", "09dce46b-5b88-424f-a401-b20390aab6d5"));
		
		return credentialsProvider;
	}
	
	public static WebTarget getTargetURISingleton() {
		if(TargetURISingleton==null){
			synchronized(TargetURISingleton.class){
				if(TargetURISingleton==null){
					ClientConfig clientConfig = new ClientConfig();
					HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("easyfixcrm", "09dce46b-5b88-424f-a401-b20390aab6d5");
					
					clientConfig.register(JacksonFeature.class);
					Client client = ClientBuilder.newClient(clientConfig);
					client.register(feature);
					TargetURISingleton = client.target(UriBuilder.fromUri("http://localhost:8090/v1/").build());
				}
					
			}
		}
			
		return TargetURISingleton;
	}


}
