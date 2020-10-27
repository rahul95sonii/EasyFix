package com.easyfix.invoice.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.easyfix.Jobs.model.Jobs;
import com.easyfix.clients.model.Clients;

@Entity
@Table(name="tbl_client_invoice")
public class Invoice {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="id")
	private int invoiceId;
	
	@Transient
	private Clients client;
	
	@Column(name="billing_from_date")
	private String billingStartDate;
	
	@Column(name="billing_to_date")
	private String billingEndDate;
	
	@Column(name="current_due_amount")
	private Float curentDues;
	
	@Column(name="previous_due_amount")
	private Float previousDues;
	
	@Column(name="total_invoice_amount")
	private Float totalBillingAmount;
	
	@Column(name="is_raised")
	private int isRaised;
	
	@Column(name="is_paid")
	private int isPaid;
	
	@Column(name="amount_due_date")
	private String amountDueDate;
	
	@Column(name="fk_client_id")
	private int invClientId;
	
	@Column(name="file_path_pdf")
	private String invoiceFileName;
	
	@Transient
	private String filePath;
	
	@Column(name="file_path_excel")
	private String masterSheetFileName;

	@Column(name="total_paid_amount")
	private Float totalPaidAmount;
	
	@Transient
	private Float paidAmount;
	
	@Transient
	private String billingCycle;
	
	@Column(name="invoiced_job_ids")
	private String invoicedJobIds;
	
	private List<Jobs> invociedJobs;
	
	private int jobId;
	private int jobServiceId;
	private int modificationAmount;
	private int modificationEF;
	private int modificationEFR;
	private int modificationCL;
	private int invoiceChangedBy;
	
	public int getInvoiceChangedBy() {
		return invoiceChangedBy;
	}
	public void setInvoiceChangedBy(int invoiceChangedBy) {
		this.invoiceChangedBy = invoiceChangedBy;
	}
	public int getModificationEF() {
		return modificationEF;
	}
	public int getModificationEFR() {
		return modificationEFR;
	}
	public int getModificationCL() {
		return modificationCL;
	}
	public void setModificationEF(int modificationEF) {
		this.modificationEF = modificationEF;
	}
	public void setModificationEFR(int modificationEFR) {
		this.modificationEFR = modificationEFR;
	}
	public void setModificationCL(int modificationCL) {
		this.modificationCL = modificationCL;
	}
	public int getModificationAmount() {
		return modificationAmount;
	}
	public void setModificationAmount(int modificationAmount) {
		this.modificationAmount = modificationAmount;
	}
	public int getJobServiceId() {
		return jobServiceId;
	}
	public void setJobServiceId(int jobServiceId) {
		this.jobServiceId = jobServiceId;
	}
	public int getJobId() {
		return jobId;
	}
	public void setJobId(int jobId) {
		this.jobId = jobId;
	}
	public List<Jobs> getInvociedJobs() {
		return invociedJobs;
	}
	public void setInvociedJobs(List<Jobs> invociedJobs) {
		this.invociedJobs = invociedJobs;
	}
	public String getInvoicedJobIds() {
		return invoicedJobIds;
	}
	public void setInvoicedJobIds(String invoicedJobIds) {
		this.invoicedJobIds = invoicedJobIds;
	}
	public int getInvoiceId() {
		return invoiceId;
	}
	public Clients getClient() {
		return client;
	}
	public String getBillingStartDate() {
		return billingStartDate;
	}
	public String getBillingEndDate() {
		return billingEndDate;
	}

	public String getAmountDueDate() {
		return amountDueDate;
	}
	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}
	public void setClient(Clients client) {
		this.client = client;
	}
	public void setBillingStartDate(String billingStartDate) {
		this.billingStartDate = billingStartDate;
	}
	public void setBillingEndDate(String billingEndDate) {
		this.billingEndDate = billingEndDate;
	}

	public void setAmountDueDate(String amountDueDate) {
		this.amountDueDate = amountDueDate;
	}
	public int getInvClientId() {
		return invClientId;
	}
	public void setInvClientId(int invClientId) {
		this.invClientId = invClientId;
	}
	public Float getCurentDues() {
		return curentDues;
	}
	public void setCurentDues(Float curentDues) {
		this.curentDues = curentDues;
	}
	public String getMasterSheetFileName() {
		return masterSheetFileName;
	}

	public void setMasterSheetFileName(String masterSheetFileName) {
		this.masterSheetFileName = masterSheetFileName;
	}


	public Float getPreviousDues() {
		return previousDues;
	}
	public void setPreviousDues(Float previousDues) {
		this.previousDues = previousDues;
	}
	public Float getTotalBillingAmount() {
		return totalBillingAmount;
	}
	public void setTotalBillingAmount(Float totalBillingAmount) {
		this.totalBillingAmount = totalBillingAmount;
	}
	public String getInvoiceFileName() {
		return invoiceFileName;
	}
	public void setInvoiceFileName(String invoiceFileName) {
		this.invoiceFileName = invoiceFileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getBillingCycle() {
		return billingCycle;
	}
	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}
	public int getIsRaised() {
		return isRaised;
	}
	public Float getTotalPaidAmount() {
		return totalPaidAmount;
	}
	public Float getPaidAmount() {
		return paidAmount;
	}
	public void setIsRaised(int isRaised) {
		this.isRaised = isRaised;
	}
	public void setTotalPaidAmount(Float totalPaidAmount) {
		this.totalPaidAmount = totalPaidAmount;
	}
	public void setPaidAmount(Float paidAmount) {
		this.paidAmount = paidAmount;
	}
	public int getIsPaid() {
		return isPaid;
	}
	public void setIsPaid(int isPaid) {
		this.isPaid = isPaid;
	}
	
	
	

}
