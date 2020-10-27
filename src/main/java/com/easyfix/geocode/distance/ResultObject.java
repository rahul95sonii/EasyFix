package com.easyfix.geocode.distance;

public class ResultObject {
	private String address;
	private String distance;
	private String DistanceText;
	private String duration;
	private String durationText;
	private String durationWithTraffic;
	private String durationWithTrafficText;
	
	public String getDurationWithTraffic() {
		return durationWithTraffic;
	}
	public void setDurationWithTraffic(String durationWithTraffic) {
		this.durationWithTraffic = durationWithTraffic;
	}
	public String getDurationWithTrafficText() {
		return durationWithTrafficText;
	}
	public void setDurationWithTrafficText(String durationWithTrafficText) {
		this.durationWithTrafficText = durationWithTrafficText;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getDistanceText() {
		return DistanceText;
	}
	public void setDistanceText(String distanceText) {
		DistanceText = distanceText;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getDurationText() {
		return durationText;
	}
	public void setDurationText(String durationText) {
		this.durationText = durationText;
	}
	@Override
	public String toString() {
		return "ResultObject [ distance=" + distance
				+ ", DistanceText=" + DistanceText + ", duration=" + duration
				+ ", durationText=" + durationText + ", durationWithTraffic="
				+ durationWithTraffic + ", durationWithTrafficText="
				+ durationWithTrafficText + "]";
	}
	
	
}
