package com.easyfix.easyfixers.model;

import org.codehaus.jackson.annotate.JsonProperty;

import com.easyfix.user.model.User;

public class EasyfixerTransaction {

	private int jobId;

    private User createdBy;

    private int source;

    private Easyfixers easyfixer;

    @JsonProperty("type")
    private int efrTransType;

    @JsonProperty("amount")
    private Float efrAmount;

    @JsonProperty("description")
    private String efrTransDesc;

    @JsonProperty("id")
    private int efrTransactionId;

    @JsonProperty("transactionDate")
    private String createdDate;

    @JsonProperty("balance")
    private Float efrBalance;

    @JsonProperty("efrTransDate")
    private String efrTransDate;

    public int getJobId ()
    {
        return jobId;
    }

    public void setJobId (int jobId)
    {
        this.jobId = jobId;
    }

    public User getCreatedBy ()
    {
        return createdBy;
    }

    public void setCreatedBy (User createdBy)
    {
        this.createdBy = createdBy;
    }

    public int getSource ()
    {
        return source;
    }

    public void setSource (int source)
    {
        this.source = source;
    }

    public Easyfixers getEasyfixer ()
    {
        return easyfixer;
    }

    public void setEasyfixer (Easyfixers easyfixer)
    {
        this.easyfixer = easyfixer;
    }

    public int getEfrTransType ()
    {
        return efrTransType;
    }

    public void setEfrTransType (int efrTransType)
    {
        this.efrTransType = efrTransType;
    }

    public Float getEfrAmount ()
    {
        return efrAmount;
    }

    public void setEfrAmount (Float efrAmount)
    {
        this.efrAmount = efrAmount;
    }

    public String getEfrTransDesc ()
    {
        return efrTransDesc;
    }

    public void setEfrTransDesc (String efrTransDesc)
    {
        this.efrTransDesc = efrTransDesc;
    }

    public int getEfrTransactionId ()
    {
        return efrTransactionId;
    }

    public void setEfrTransactionId (int efrTransactionId)
    {
        this.efrTransactionId = efrTransactionId;
    }

    public String getCreatedDate ()
    {
        return createdDate;
    }

    public void setCreatedDate (String createdDate)
    {
        this.createdDate = createdDate;
    }

    public Float getEfrBalance ()
    {
        return efrBalance;
    }

    public void setEfrBalance (Float efrBalance)
    {
        this.efrBalance = efrBalance;
    }

    public String getEfrTransDate ()
    {
        return efrTransDate;
    }

    public void setEfrTransDate (String efrTransDate)
    {
        this.efrTransDate = efrTransDate;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [jobId = "+jobId+", createdBy = "+createdBy+", source = "+source+", easyfixer = "+easyfixer+", efrTransType = "+efrTransType+", efrAmount = "+efrAmount+", efrTransDesc = "+efrTransDesc+", efrTransactionId = "+efrTransactionId+", createdDate = "+createdDate+", efrBalance = "+efrBalance+", efrTransDate = "+efrTransDate+"]";
    }

}
