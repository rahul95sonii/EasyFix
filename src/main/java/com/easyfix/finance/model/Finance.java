package com.easyfix.finance.model;

import org.codehaus.jackson.annotate.JsonProperty;

import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.user.model.User;


public class Finance {
	
	@JsonProperty("transaction_id")
	private int transactionId;

	private int easyfixerId;
	
	@JsonProperty("jobId")
	private int jobId;

	private int clientid;
	
	@JsonProperty("type")
	private int transactionType;  	//1:Debit, 2:Credit, 0:Both
	private int source; 			//1:from system, 2: cash, 3:cheque
	

	private String transactionDate;
	

	private String fromDate;
	

	private String toDate;
	
	@JsonProperty("amount")
	private String transactionAmount;
	

	@JsonProperty("balance")
	private String balaceAmount;
	
	@JsonProperty("created_by")
	private User createdBy;			//0:BySystem
	

	private String createdDate;
	
	@JsonProperty("description")
	private String Description;
	
	@JsonProperty("handymen")
	private Easyfixers easyfixer;
	
	@JsonProperty("recharge_id")
	private int rechargeId;
	

	@JsonProperty("name")
	private String easyfixerName;
	

	@JsonProperty("mobile")
	private String easyfixerNo;
	

	@JsonProperty("ndmName")
	private User NDMName;
	

	private int ndmId;
	private String updateType;
	

	private String clientName;

	private String empName;
	

	@JsonProperty("total_amount")
	private float totalCharge;
	

	@JsonProperty("easyfix_amount")
	private float efCharge;
	

	@JsonProperty("handymen_amount")
	private float efrCharge;
	

	@JsonProperty("partner_amount")
	private float clientCharge;
	

	@JsonProperty("jobTransactionId")
	private float jobTransactionId;
	

	@JsonProperty("collected_by")
	private float collected_by;
	
	
	public int getTransactionId() {
		return transactionId;
	}
	public int getEasyfixerId() {
		return easyfixerId;
	}
	public int getJobId() {
		return jobId;
	}
	public int getClientid() {
		return clientid;
	}
	public int getTransactionType() {
		return transactionType;
	}
	public int getSource() {
		return source;
	}
	public String getTransactionDate() {
		return transactionDate;
	}
	public String getFromDate() {
		return fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public String getTransactionAmount() {
		return transactionAmount;
	}
	public String getBalaceAmount() {
		return balaceAmount;
	}
	public User getCreatedBy() {
		return createdBy;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public String getDescription() {
		return Description;
	}
	public String getEasyfixerName() {
		return easyfixerName;
	}
	public String getEasyfixerNo() {
		return easyfixerNo;
	}
	public String getClientName() {
		return clientName;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public void setEasyfixerId(int easyfixerId) {
		this.easyfixerId = easyfixerId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public void setClientid(int clientid) {
		this.clientid = clientid;
	}
	public void setTransactionType(int transactionType) {
		this.transactionType = transactionType;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public void setTransactionAmount(String transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public void setBalaceAmount(String balaceAmount) {
		this.balaceAmount = balaceAmount;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public void setEasyfixerName(String easyfixerName) {
		this.easyfixerName = easyfixerName;
	}
	public void setEasyfixerNo(String easyfixerNo) {
		this.easyfixerNo = easyfixerNo;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public float getTotalCharge() {
		return totalCharge;
	}
	public float getEfCharge() {
		return efCharge;
	}
	public float getEfrCharge() {
		return efrCharge;
	}
	public float getClientCharge() {
		return clientCharge;
	}
	public void setTotalCharge(float totalCharge) {
		this.totalCharge = totalCharge;
	}
	public void setEfCharge(float efCharge) {
		this.efCharge = efCharge;
	}
	public void setEfrCharge(float efrCharge) {
		this.efrCharge = efrCharge;
	}
	public void setClientCharge(float clientCharge) {
		this.clientCharge = clientCharge;
	}
	public float getJobTransactionId() {
		return jobTransactionId;
	}
	public float getCollected_by() {
		return collected_by;
	}
	public void setJobTransactionId(float jobTransactionId) {
		this.jobTransactionId = jobTransactionId;
	}
	public void setCollected_by(float collected_by) {
		this.collected_by = collected_by;
	}
	public User getNDMName() {
		return NDMName;
	}
	public int getNdmId() {
		return ndmId;
	}
	public void setNDMName(User nDMName) {
		NDMName = nDMName;
	}
	public void setNdmId(int ndmId) {
		this.ndmId = ndmId;
	}
	public Easyfixers getEasyfixer() {
		return easyfixer;
	}
	public void setEasyfixer(Easyfixers easyfixer) {
		this.easyfixer = easyfixer;
	}
	public int getRechargeId() {
		return rechargeId;
	}
	public void setRechargeId(int rechargeId) {
		this.rechargeId = rechargeId;
	}
	public String getUpdateType() {
		return updateType;
	}
	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

}
