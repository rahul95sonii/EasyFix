package com.easyfix.geocode.latlong;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Result {
 
 private String formatted_address;
 
 private boolean partial_match;
 
 private Geometry geometry;
 
 private String place_id;
 
 
 private addressComponent[] address_components;
 
 @JsonIgnore
 private Object types;
 
 @JsonIgnore
 private Object plus_code;
 
 public String getPlace_id() {
	return place_id;
}

public void setPlace_id(String place_id) {
	this.place_id = place_id;
}



 public String getFormatted_address() {
  return formatted_address;
 }

 public void setFormatted_address(String formatted_address) {
  this.formatted_address = formatted_address;
 }

 public boolean isPartial_match() {
  return partial_match;
 }

 public void setPartial_match(boolean partial_match) {
  this.partial_match = partial_match;
 }

 public Geometry getGeometry() {
  return geometry;
 }

 public void setGeometry(Geometry geometry) {
  this.geometry = geometry;
 }

 
 public Object getTypes() {
  return types;
 }

 public void setTypes(Object types) {
  this.types = types;
 }

public addressComponent[] getAddress_components() {
	return address_components;
}

public void setAddress_components(addressComponent[] address_components) {
	this.address_components = address_components;
}

public Object getPlus_code() {
	return plus_code;
}

public void setPlus_code(Object plus_code) {
	this.plus_code = plus_code;
}
 
 
}