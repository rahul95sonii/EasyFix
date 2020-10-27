package com.easyfix.util.triggers.model;

import java.sql.Timestamp;

public class Jobs
{
	
	public String id;
	
	public Timestamp createdTimestamp;
	
	public Timestamp cancelledTimestamp;
	
	public Timestamp scheduledTimestamp;
	
	public Timestamp requestedTimestamp;
	
	public Timestamp completedTimestamp;
	
	public String currentStatus;
	
	public String clientReferenceId;
	
	public String city;
	
	public String customerName;
	
	public String pinCode;
	
	public String address;
	
	public String totalCharge;
	
	public String enumDesc;
	
	
	public String getTotalCharge()
	{
		return totalCharge;
	}

	public void setTotalCharge(String totalCharge)
	{
		this.totalCharge = totalCharge;
	}

	public String getPinCode()
	{
		return pinCode;
	}

	public void setPinCode(String pinCode)
	{
		this.pinCode = pinCode;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public Timestamp getCreatedTimestamp()
	{
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Timestamp createdTimestamp)
	{
		this.createdTimestamp = createdTimestamp;
	}

	public Timestamp getScheduledTimestamp()
	{
		return scheduledTimestamp;
	}

	public void setScheduledTimestamp(Timestamp scheduledTimestamp)
	{
		this.scheduledTimestamp = scheduledTimestamp;
	}

	public Timestamp getRequestedTimestamp()
	{
		return requestedTimestamp;
	}

	public void setRequestedTimestamp(Timestamp requestedTimestamp)
	{
		this.requestedTimestamp = requestedTimestamp;
	}

	public Timestamp getCompletedTimestamp()
	{
		return completedTimestamp;
	}

	public void setCompletedTimestamp(Timestamp completedTimestamp)
	{
		this.completedTimestamp = completedTimestamp;
	}

	public String getClientReferenceId()
	{
		return clientReferenceId;
	}

	public void setClientReferenceId(String clientReferenceId)
	{
		this.clientReferenceId = clientReferenceId;
	}

	public String getCity()
	{
		return city;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	public String getCustomerName()
	{
		return customerName;
	}

	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getCurrentStatus()
	{
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus)
	{
		this.currentStatus = currentStatus;
	}

	public Timestamp getCancelledTimestamp()
	{
		return cancelledTimestamp;
	}

	public void setCancelledTimestamp(Timestamp cancelledTimestamp)
	{
		this.cancelledTimestamp = cancelledTimestamp;
	}

	public String getEnumDesc() {
		return enumDesc;
	}

	public void setEnumDesc(String enumDesc) {
		this.enumDesc = enumDesc;
	}

	
}
