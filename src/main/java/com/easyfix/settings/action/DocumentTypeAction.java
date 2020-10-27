package com.easyfix.settings.action;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easyfix.settings.delegate.DocumentTypeService;
import com.easyfix.settings.model.DocumentType;
import com.easyfix.util.CommonAbstractAction;
import com.opensymphony.xwork2.ModelDriven;

public class DocumentTypeAction extends CommonAbstractAction implements ModelDriven<DocumentType> {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(DocumentTypeAction.class);
	private DocumentTypeService documentTypeService;
	
	private DocumentType documentTypeObj;
	private String actMenu;
	private String actParent;
	private String title;
	private String redirectUrl;
	
	private List<DocumentType> documentTypeList;
	
	@Override
	public DocumentType getModel() {
		return setDocumentTypeObj(new DocumentType());
	}
	
	public String documentType() throws Exception {
			
		try {

			String acttitle= "Easyfix : Manage Document Type";
			setActMenu("Manage Document Type");
			setActParent("Settings");
			setTitle(acttitle);	
			
			documentTypeList = documentTypeService.getDocumentTypeList(2);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
				
		return SUCCESS;
			
	}
	
public String addEditDocumentType() throws Exception {
		
		try {
						
			if(documentTypeObj.getDocumentTypeId() != 0){
				documentTypeObj = documentTypeService.getDocumentTypeDetailsById(documentTypeObj.getDocumentTypeId());
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		
		return SUCCESS;
	}
	
	public String addUpdateDocumentType() throws Exception {		
		try {
			
			int stauts = documentTypeService.addUpdateDocumentType(documentTypeObj);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		return SUCCESS;
		
	}
	
	public DocumentTypeService getDocumentTypeService() {
		return documentTypeService;
	}

	public void setDocumentTypeService(DocumentTypeService documentTypeService) {
		this.documentTypeService = documentTypeService;
	}

	public DocumentType getDocumentTypeObj() {
		return documentTypeObj;
	}

	public DocumentType setDocumentTypeObj(DocumentType documentTypeObj) {
		return this.documentTypeObj = documentTypeObj;
	}

	public String getActMenu() {
		return actMenu;
	}


	public void setActMenu(String actMenu) {
		this.actMenu = actMenu;
	}


	public String getActParent() {
		return actParent;
	}


	public void setActParent(String actParent) {
		this.actParent = actParent;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public List<DocumentType> getDocumentTypeList() {
		return documentTypeList;
	}

	public void setDocumentTypeList(List<DocumentType> documentTypeList) {
		this.documentTypeList = documentTypeList;
	}
	@Override // for RestrictAccessToUnauthorizedActionInterceptor
	public String toString(){
		return "DocumentTypeAction";
	}

}

