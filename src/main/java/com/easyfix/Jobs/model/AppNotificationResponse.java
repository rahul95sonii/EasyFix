package com.easyfix.Jobs.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AppNotificationResponse {

	private String multicast_id;
	private Integer success;

	private Integer failure;
	private String canonical_ids;
	public String getMulticast_id() {
		return multicast_id;
	}
	public void setMulticast_id(String multicast_id) {
		this.multicast_id = multicast_id;
	}
	public Integer getSuccess() {
		return success;
	}
	public void setSuccess(Integer success) {
		this.success = success;
	}
	public Integer getFailure() {
		return failure;
	}
	public void setFailure(Integer failure) {
		this.failure = failure;
	}
	public String getCanonical_ids() {
		return canonical_ids;
	}
	public void setCanonical_ids(String canonical_ids) {
		this.canonical_ids = canonical_ids;
	}
	@Override
	public String toString() {
		return "AppNotificationResponse [multicast_id=" + multicast_id
				+ ", success=" + success + ", failure=" + failure
				+ ", canonical_ids=" + canonical_ids + "]";
	}
	
	
}
