package com.easyfix.tracking.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Tracking
{
    private String loginDateTime;
    private int id;
    private String sessionId;
    private String logoutDateTime;
    private int userId;
    private String activeTime;
    private String userName;
    private String logoutAction;
    
    private String cityName;
    private int jobGiven;
    private float conversion;
    private double billing;
    private float customerRating;
    private int jobCompleted;
    private int jobCanceled;
    private int jobCheckOut;
    private String clientName;
    private float efShare;
    private float clientShare;
    private float material;
    private float margin;
    private String avgTicket;
    
    private int jobCreatedBy;
    private int jobScheduledBy;
    private int jobCheckInBy;
    private int jobCheckOutBy;
    private int jobFeedbackBy;
    private int jobCanceledBy;
    private int jobEnquiryCount;
    
    
    
    
    
    

    public String getLoginDateTime ()
    {
        return loginDateTime;
    }

    public void setLoginDateTime (String loginDateTime)
    {
        this.loginDateTime = loginDateTime;
    }

    public int getId ()
    {
        return id;
    }

    public void setId (int id)
    {
        this.id = id;
    }

    public String getSessionId ()
    {
        return sessionId;
    }

    public void setSessionId (String sessionId)
    {
        this.sessionId = sessionId;
    }

    public String getLogoutDateTime ()
    {
        return logoutDateTime;
    }

    public void setLogoutDateTime (String logoutDateTime)
    {
        this.logoutDateTime = logoutDateTime;
    }

    public int getUserId ()
    {
        return userId;
    }

    public void setUserId (int userId)
    {
        this.userId = userId;
    }

    public String getActiveTime ()
    {
        return activeTime;
    }

    public void setActiveTime (String activeTime)
    {
        this.activeTime = activeTime;
    }

    public String getUserName ()
    {
        return userName;
    }

    public void setUserName (String userName)
    {
        this.userName = userName;
    }

    public String getLogoutAction ()
    {
        return logoutAction;
    }

    public void setLogoutAction (String logoutAction)
    {
        this.logoutAction = logoutAction;
    }

    @Override
	public String toString() {
		return "Tracking [userId=" + userId + ", userName=" + userName
				+ ", jobCreatedBy=" + jobCreatedBy + ", jobScheduledBy="
				+ jobScheduledBy + ", jobCheckInBy=" + jobCheckInBy
				+ ", jobCheckOutBy=" + jobCheckOutBy + ", jobFeedbackBy="
				+ jobFeedbackBy + ", jobCanceledBy=" + jobCanceledBy
				+ ", jobEnquiryCount=" + jobEnquiryCount + "]";
	}

	public String getCityName() {
		return cityName;
	}

	public int getJobGiven() {
		return jobGiven;
	}

	public float getConversion() {
		return conversion;
	}

	public double getBilling() {
		return billing;
	}

	public float getCustomerRating() {
		return customerRating;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public void setJobGiven(int jobGiven) {
		this.jobGiven = jobGiven;
	}

	public void setConversion(float conversion) {
		this.conversion = conversion;
	}

	public void setBilling(double billing) {
		this.billing = billing;
	}

	public void setCustomerRating(float customerRating) {
		this.customerRating = customerRating;
	}

	public int getJobCompleted() {
		return jobCompleted;
	}

	public void setJobCompleted(int jobCompleted) {
		this.jobCompleted = jobCompleted;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public float getEfShare() {
		return efShare;
	}

	public float getClientShare() {
		return clientShare;
	}

	public float getMaterial() {
		return material;
	}

	public float getMargin() {
		return margin;
	}

	public String getAvgTicket() {
		return avgTicket;
	}

	public void setEfShare(float efShare) {
		this.efShare = efShare;
	}

	public void setClientShare(float clientShare) {
		this.clientShare = clientShare;
	}

	public void setMaterial(float material) {
		this.material = material;
	}

	public void setMargin(float margin) {
		this.margin = margin;
	}

	public void setAvgTicket(String avgTicket) {
		this.avgTicket = avgTicket;
	}

	public int getJobCreatedBy() {
		return jobCreatedBy;
	}

	public int getJobScheduledBy() {
		return jobScheduledBy;
	}

	public int getJobCheckInBy() {
		return jobCheckInBy;
	}

	public int getJobCheckOutBy() {
		return jobCheckOutBy;
	}

	public int getJobFeedbackBy() {
		return jobFeedbackBy;
	}

	public int getJobCanceledBy() {
		return jobCanceledBy;
	}

	public void setJobCreatedBy(int jobCreatedBy) {
		this.jobCreatedBy = jobCreatedBy;
	}

	public void setJobScheduledBy(int jobScheduledBy) {
		this.jobScheduledBy = jobScheduledBy;
	}

	public void setJobCheckInBy(int jobCheckInBy) {
		this.jobCheckInBy = jobCheckInBy;
	}

	public void setJobCheckOutBy(int jobCheckOutBy) {
		this.jobCheckOutBy = jobCheckOutBy;
	}

	public void setJobFeedbackBy(int jobFeedbackBy) {
		this.jobFeedbackBy = jobFeedbackBy;
	}

	public void setJobCanceledBy(int jobCanceledBy) {
		this.jobCanceledBy = jobCanceledBy;
	}

	public int getJobEnquiryCount() {
		return jobEnquiryCount;
	}

	public void setJobEnquiryCount(int jobEnquiryCount) {
		this.jobEnquiryCount = jobEnquiryCount;
	}

	public int getJobCanceled() {
		return jobCanceled;
	}

	public void setJobCanceled(int jobCanceled) {
		this.jobCanceled = jobCanceled;
	}

	public int getJobCheckOut() {
		return jobCheckOut;
	}

	public void setJobCheckOut(int jobCheckOut) {
		this.jobCheckOut = jobCheckOut;
	}

	

	
}
