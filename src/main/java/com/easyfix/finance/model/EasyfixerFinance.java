package com.easyfix.finance.model;

import java.sql.Timestamp;

import javax.persistence.Column;

import org.codehaus.jackson.annotate.JsonProperty;

import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.user.model.User;


public class EasyfixerFinance {

	@JsonProperty("id")
	private int recharge_id;
	
	@JsonProperty("handymen")
	private Easyfixers efr;
	
	@JsonProperty("ndm")
	private User ndm;
	
	@JsonProperty("amount")
	private float recharge_amount;
	
	@JsonProperty("type")
	private int recharge_type;
	
	@JsonProperty("date")
	private String recharge_date;
	
	@Column(name = "approved_by_finance")
	private int approved_by_finance;
	
	@Column(name = "comments")
	private String comments;
	
	@JsonProperty("added_by")
	private User insertedBy;
	
	@JsonProperty("updated_date")
	private String insertedTimestamp;
	
	
	@JsonProperty("mode")
	private String paymentMode;
	
	private String referenceId;
	
	private int ndmId;
	private int createdBy;
	private int efrId;
	
	@JsonProperty("approval_date")
	private String approvalDate;
	

	public int getRecharge_id() {
		return recharge_id;
	}

	public Easyfixers getEfr() {
		return efr;
	}

	public User getNdm() {
		return ndm;
	}

	public float getRecharge_amount() {
		return recharge_amount;
	}

	public int getRecharge_type() {
		return recharge_type;
	}

	public String getRecharge_date() {
		return recharge_date;
	}

	public int getApproved_by_finance() {
		return approved_by_finance;
	}

	public String getComments() {
		return comments;
	}

	public User getInsertedBy() {
		return insertedBy;
	}

	public String getInsertedTimestamp() {
		return insertedTimestamp;
	}

	public void setRecharge_id(int recharge_id) {
		this.recharge_id = recharge_id;
	}

	public void setEfr(Easyfixers efr) {
		this.efr = efr;
	}

	public void setNdm(User ndm) {
		this.ndm = ndm;
	}

	public void setRecharge_amount(float recharge_amount) {
		this.recharge_amount = recharge_amount;
	}

	public void setRecharge_type(int recharge_type) {
		this.recharge_type = recharge_type;
	}

	public void setRecharge_date(String recharge_date) {
		this.recharge_date = recharge_date;
	}

	public void setApproved_by_finance(int approved_by_finance) {
		this.approved_by_finance = approved_by_finance;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setInsertedBy(User insertedBy) {
		this.insertedBy = insertedBy;
	}

	public void setInsertedTimestamp(String insertedTimestamp) {
		this.insertedTimestamp = insertedTimestamp;
	}

	public int getNdmId() {
		return ndmId;
	}

	public void setNdmId(int ndmId) {
		this.ndmId = ndmId;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public int getEfrId() {
		return efrId;
	}

	public void setEfrId(int efrId) {
		this.efrId = efrId;
	}

	public String getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(String approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	
	
}
