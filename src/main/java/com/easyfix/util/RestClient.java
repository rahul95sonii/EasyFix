package com.easyfix.util;

import javax.ws.rs.client.WebTarget;

public class RestClient {

	public RestClient() {
		// TODO Auto-generated constructor stub
	}
	
	
	public WebTarget apiResponse() throws Exception {		
	//	String jsonResult = "";
		WebTarget target=null;
		try {
			
				target = TargetURISingleton.getTargetURISingleton();
			
		//	jsonResult = target.path(urlpath).request().accept(MediaType.APPLICATION_JSON).get(String.class);
			
		/*	System.out.println(target.path("userLog").request()
			         .accept(MediaType.APPLICATION_JSON).get(String.class));*/
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return target;
	}
	
//	private static URI getBaseURI() {
//	    return UriBuilder.fromUri("http://localhost:8089/api/v1/").build();
//	  }

}
