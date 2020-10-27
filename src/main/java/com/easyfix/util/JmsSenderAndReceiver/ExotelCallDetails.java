package com.easyfix.util.JmsSenderAndReceiver;

import org.codehaus.jackson.annotate.JsonProperty;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ExotelCallDetails {
	@JsonIgnore
	@JsonProperty("callTime")
	public String callTime;
	
	@JsonProperty("callSid")
	public String callSid;
	
	@JsonProperty("fromNumber")
	public String fromNumber;
	
	@JsonProperty("direction")
	public String direction;
	
	@JsonProperty("agentNumber")
	public String agentNumber;
	
	@JsonProperty("language")
	public String language;
	
	@JsonProperty("type")
	public String type;
	
	@JsonProperty("status")
	public String status;
	
	public int isSeenByCrmUser; //0: not seen ,1: seen
	
	
	private int crmUserId;
	
	
	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getCallTime()
	{
		return callTime;
	}

	public void setCallTime(String callTime)
	{
		this.callTime = callTime;
	}

	public String getCallSid()
	{
		return callSid;
	}

	public void setCallSid(String callSid)
	{
		this.callSid = callSid;
	}

	public String getFromNumber()
	{
		return fromNumber;
	}

	public void setFromNumber(String fromNumber)
	{
		this.fromNumber = fromNumber;
	}

	public String getDirection()
	{
		return direction;
	}

	public void setDirection(String direction)
	{
		this.direction = direction;
	}

	public String getAgentNumber()
	{
		return agentNumber;
	}

	public void setAgentNumber(String agentNumber)
	{
		this.agentNumber = agentNumber;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String language)
	{
		this.language = language;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}


	@Override
	public String toString() {
		return "ExotelCallDetails [calltime="+callTime+"callsid ="+callSid + ", fromNumber=" + fromNumber + ", direction="
				+ direction + ", agentNumber=" + agentNumber + ", language="
				+ language + ", type=" + type + ", status=" + status + "]";
	}


	public int getCrmUserId() {
		return crmUserId;
	}


	public void setCrmUserId(int crmUserId) {
		this.crmUserId = crmUserId;
	}


	public int getIsSeenByCrmUser() {
		return isSeenByCrmUser;
	}


	public void setIsSeenByCrmUser(int isSeenByCrmUser) {
		this.isSeenByCrmUser = isSeenByCrmUser;
	}




}
