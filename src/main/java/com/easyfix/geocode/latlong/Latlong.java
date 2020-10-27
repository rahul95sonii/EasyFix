package com.easyfix.geocode.latlong;

import java.util.List;
import java.util.Map;

import com.easyfix.customers.model.Address;
import com.easyfix.geocode.distance.ResultObject;

public interface Latlong {

	public List<Address> getLatLong(String address) throws Exception;
	public double getLinearDistance(double lat1, double lon1, double lat2, double lon2) throws Exception;
	public Map<String,ResultObject> getActualDistance(String GpsOrigins, String GPSDestinations ) throws Exception;

}
