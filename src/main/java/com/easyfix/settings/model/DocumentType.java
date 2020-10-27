package com.easyfix.settings.model;

public class DocumentType {
	
	private int documentTypeId;	
	private String documentName;
	private String documentMandatory; 
	private String documentTypeStatus;
	private String efrDocument;
	private String insertDate;
	private String updateDate;
	private int updatedBy;
	
	public DocumentType() {
		// TODO Auto-generated constructor stub
	}

	public int getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(int documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDocumentMandatory() {
		return documentMandatory;
	}

	public void setDocumentMandatory(String documentMandatory) {
		this.documentMandatory = documentMandatory;
	}

	public String getDocumentTypeStatus() {
		return documentTypeStatus;
	}

	public void setDocumentTypeStatus(String documentTypeStatus) {
		this.documentTypeStatus = documentTypeStatus;
	}

	public String getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getEfrDocument() {
		return efrDocument;
	}

	public void setEfrDocument(String efrDocument) {
		this.efrDocument = efrDocument;
	}
	
}