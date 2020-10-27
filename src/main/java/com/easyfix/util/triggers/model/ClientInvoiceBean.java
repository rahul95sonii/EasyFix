package com.easyfix.util.triggers.model;

public class ClientInvoiceBean
{
	private int clientId;
	private String clientName;
	private String jobDesc;
	private String clientCityName;
	private String paidBy;
	private String collectedBy;
	private String billingCycle;
	//private String billingStartDate;
	private int billingRaised;
	private String lastBillingDate;
	private String nextBillingDate;
	private Float amountDue;
	private Boolean isPaid;
	private String amountDueDate;

	private int jobId;
	private int serviceTypeId;
	private String clientRefId;
	private String customerName;
	private String customerNo;
	private String cityName;
	private String checkOutDate;
	private String serviceTypeName;
	private String jobStatus;
	private String jobServices;
	private Float totalAmount;
	private Float installationCharge;
	private Float repairCharge;
	private String fileName;
	private String bilingAddress;
	private String billingName;
	private String billingFromDate;
	private String billingToDate;
	
	
	
	public String getBillingFromDate()
	{
		return billingFromDate;
	}

	public void setBillingFromDate(String billingFromDate)
	{
		this.billingFromDate = billingFromDate;
	}

	public String getBillingToDate()
	{
		return billingToDate;
	}

	public void setBillingToDate(String billingToDate)
	{
		this.billingToDate = billingToDate;
	}

	public String getBillingName()
	{
		return billingName;
	}

	public void setBillingName(String billingName)
	{
		this.billingName = billingName;
	}

	public String getBilingAddress()
	{
		return bilingAddress;
	}

	public void setBilingAddress(String bilingAddress)
	{
		this.bilingAddress = bilingAddress;
	}

	public int getClientId()
	{
		return clientId;
	}

	public String getClientName()
	{
		return clientName;
	}

	public String getClientCityName()
	{
		return clientCityName;
	}

	public String getPaidBy()
	{
		return paidBy;
	}

	public String getCollectedBy()
	{
		return collectedBy;
	}

	public String getBillingCycle()
	{
		return billingCycle;
	}

	public String getLastBillingDate()
	{
		return lastBillingDate;
	}

	public String getNextBillingDate()
	{
		return nextBillingDate;
	}

	public Float getAmountDue()
	{
		return amountDue;
	}

	public Boolean getIsPaid()
	{
		return isPaid;
	}

	public String getAmountDueDate()
	{
		return amountDueDate;
	}

	public void setClientId(int clientId)
	{
		this.clientId = clientId;
	}

	public void setClientName(String clientName)
	{
		this.clientName = clientName;
	}

	public void setClientCityName(String clientCityName)
	{
		this.clientCityName = clientCityName;
	}

	public void setPaidBy(String paidBy)
	{
		this.paidBy = paidBy;
	}

	public void setCollectedBy(String collectedBy)
	{
		this.collectedBy = collectedBy;
	}

	public void setBillingCycle(String billingCycle)
	{
		this.billingCycle = billingCycle;
	}

	public void setLastBillingDate(String lastBillingDate)
	{
		this.lastBillingDate = lastBillingDate;
	}

	public void setNextBillingDate(String nextBillingDate)
	{
		this.nextBillingDate = nextBillingDate;
	}

	public void setAmountDue(Float amountDue)
	{
		this.amountDue = amountDue;
	}

	public void setIsPaid(Boolean isPaid)
	{
		this.isPaid = isPaid;
	}

	public void setAmountDueDate(String amountDueDate)
	{
		this.amountDueDate = amountDueDate;
	}

	public int getJobId()
	{
		return jobId;
	}

	public int getServiceTypeId()
	{
		return serviceTypeId;
	}

	public String getClientRefId()
	{
		return clientRefId;
	}

	public String getCustomerName()
	{
		return customerName;
	}

	public String getCustomerNo()
	{
		return customerNo;
	}

	public String getCityName()
	{
		return cityName;
	}

	public String getCheckOutDate()
	{
		return checkOutDate;
	}

	public String getServiceTypeName()
	{
		return serviceTypeName;
	}

	public String getJobStatus()
	{
		return jobStatus;
	}

	public String getJobServices()
	{
		return jobServices;
	}

	public Float getTotalAmount()
	{
		return totalAmount;
	}

	public Float getInstallationCharge()
	{
		return installationCharge;
	}

	public Float getRepairCharge()
	{
		return repairCharge;
	}

	public void setJobId(int jobId)
	{
		this.jobId = jobId;
	}

	public void setServiceTypeId(int serviceTypeId)
	{
		this.serviceTypeId = serviceTypeId;
	}

	public void setClientRefId(String clientRefId)
	{
		this.clientRefId = clientRefId;
	}

	public void setCustomerName(String customerName)
	{
		this.customerName = customerName;
	}

	public void setCustomerNo(String customerNo)
	{
		this.customerNo = customerNo;
	}

	public void setCityName(String cityName)
	{
		this.cityName = cityName;
	}

	public void setCheckOutDate(String checkOutDate)
	{
		this.checkOutDate = checkOutDate;
	}

	public void setServiceTypeName(String serviceTypeName)
	{
		this.serviceTypeName = serviceTypeName;
	}

	public void setJobStatus(String jobStatus)
	{
		this.jobStatus = jobStatus;
	}

	public void setJobServices(String jobServices)
	{
		this.jobServices = jobServices;
	}

	public void setTotalAmount(Float totalAmount)
	{
		this.totalAmount = totalAmount;
	}

	public void setInstallationCharge(Float installationCharge)
	{
		this.installationCharge = installationCharge;
	}

	public void setRepairCharge(Float repairCharge)
	{
		this.repairCharge = repairCharge;
	}

	@Override
	public String toString()
	{
		return this.clientId + "";
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public int getBillingRaised()
	{
		return billingRaised;
	}

	public void setBillingRaised(int billingRaised)
	{
		this.billingRaised = billingRaised;
	}

	public String getJobDesc()
	{
		return jobDesc;
	}

	public void setJobDesc(String jobDesc)
	{
		this.jobDesc = jobDesc;
	}

}
