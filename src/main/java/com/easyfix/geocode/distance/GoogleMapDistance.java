/*Objective: get distance and duration of  many sources and 1 destination
(Though more than 1 destination can be added easily and google would give
 destinations*Origins number of responses. But our business logic requires only 1 destination.)
*/
package com.easyfix.geocode.distance;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easyfix.Jobs.action.JobAction;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;


public class GoogleMapDistance {
/* I am formating a url using java.net package which will be sent to google map web services 
 * google responses in JSON or XML. Since JSON is lighter i am using it. we will parse JSON ,get
 * desired details and make an Object ResultObject and put it into a map as value whose key will
 * be the String address.  
 */
	private static final Logger logger = LogManager.getLogger(JobAction.class);
	Map<String,ResultObject> map;
	private final String key = "AIzaSyC7EpS1VTgU-EUEiz-J_yj1q8o6PUgXUcs";
	
	public GoogleMapDistance(){
		map = new HashMap<String, ResultObject>();
	}
	// many origins and single destination is passed to the method as argument.
	public Map getDistanceAndDuration(String origins, String destinations ) throws IOException{
		 try{
			// logger.info("getDistanceAndDuration for:"+ origins+"--"+destinations);
		final String URL = "https://maps.googleapis.com/maps/api/distancematrix/json";
		 URL url = new URL(URL+"?origins=");
		 
		 //encoding the addresses passed in origins array and saving it in encodedOrigin array.
		 //and adding it into url separated pipe.
		/*
		  String[] encodedOrigin= new String[origins.length];
		  for(int i =0; i<origins.length;i++){
			  encodedOrigin[i] = URLEncoder.encode(origins[i], "UTF-8");
			   url = new URL(url+encodedOrigin[i]+"|");
		  }
		  */
		 String encodedOrigin = URLEncoder.encode(origins, "UTF-8");
		 url = new URL(url+encodedOrigin);
		  
		  // adding destination to the url.
		  url = new URL(url+"&destinations="+URLEncoder.encode(destinations, "UTF-8"));
		 //add key
		  url = new URL(url+"&key="+URLEncoder.encode(key, "UTF-8"));
		  
		 /* URL url = new URL(URL + "?origins="
				    + URLEncoder.encode(origins, "UTF-8") +"&destinations="
						    + URLEncoder.encode(destinations, "UTF-8"));
						    */
		  
		 // System.out.println("url is: " +url);
		  
		  //sending the url to google and parsing JSON using ObjectMapper
		  
		  URLConnection conn = url.openConnection();
		  InputStream in = conn.getInputStream() ;
		  ObjectMapper mapper = new ObjectMapper();
		  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		  DistancePojo dis = (DistancePojo)mapper.readValue(in,DistancePojo.class);
		  
		  in.close();
		
	  /*
		    URL url = new URL("https://maps.googleapis.com/maps/api/distancematrix/json?origins=Vancouver+BC|Seattle&destinations=San+Francisco|Victoria+BC");
		 HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      conn.setRequestMethod("GET");
	      String line, outputString = "";
	      BufferedReader reader = new BufferedReader(
	      new InputStreamReader(conn.getInputStream()));
	      while ((line = reader.readLine()) != null) {
	          outputString += line;
	      }
	      System.out.println(outputString);
	      DistancePojo dis = new Gson().fromJson(outputString, DistancePojo.class);
	   */
	      
		  
		  //making ResultObject after getting desired details.
	      if(dis.getStatus().equals("OK")){
	    	  for(int i =0; i<dis.getOrigin_addresses().length;i++){ 
	    		  String address =  dis.getOrigin_addresses()[i];
	    		  String distance = dis.getRows()[i].getElements()[0].getDistance().getValue();
	    		  String distanceText  = dis.getRows()[i].getElements()[0].getDistance().getText();
	    		  String duration = dis.getRows()[i].getElements()[0].getDuration().getValue();
	    		  String durationText = dis.getRows()[i].getElements()[0].getDuration().getText();
	    		 
	    		  ResultObject result = new ResultObject();
	    		  result.setAddress(address);
	    		  result.setDistance(distance);
	    		  result.setDistanceText(distanceText);
	    		  result.setDuration(duration);
	    		  result.setDurationText(durationText);
	    		  
	    		  map.put(origins, result);
	    	     // System.out.print ("origin: ");
	    	      //System.out.print(address+" ,");
	    		  logger.info("getDistanceAndDuration for:"+ origins+"--"+destinations); 
	    	  }
	    	  
	    	  //System.out.println ("no of origins: "+dis.getOrigin_addresses().length);
	    	  //System.out.println ("destination: "+dis.getDestination_addresses()[0]);
	    	  /*
	    	  for(int i =0; i<dis.getRows().length;i++){
	    		 System.out.print ("Duration: "+dis.getRows()[i].getElements()[0].getDuration()); 
	    		 System.out.println ("&  Distance: "+dis.getRows()[i].getElements()[0].getDistance());
	    	 }
	    	 */
	    	
	    	 
	      }
	      else{
	    	  System.out.println(dis.getStatus());
	    	  logger.info("getDistanceAndDuration failed with response status:"+dis.getStatus());
	      }
		 }
		 catch(Exception e){
			 System.out.println(e);
			 logger.catching(e);
		 }
		 logger.info("road dis calculated successfully"+map);
	      return map;
	    
	}
	
	
  public static void main(String[] args) throws IOException {
	  GoogleMapDistance mapDis = new GoogleMapDistance();
	  String origins = "28.6139391,77.20902120000005";
	  String destinations = "28.4594965,77.02663830000006";
	  mapDis.getDistanceAndDuration(origins,destinations);
	  System.out.println("MAP:::: "+mapDis.map);
	  
	 // Set s = mapDis.map.keySet();
	  /*for(int i =0; i<origins.length;i++){
		  
	  ResultObject r1 = mapDis.map.get(s[i]);
	  System.out.println("Address: "+r1.getAddress()+", distance"+r1.getDistance()+" , durtaion"+r1.getDuration());
	  
  }
  */
  }
}
