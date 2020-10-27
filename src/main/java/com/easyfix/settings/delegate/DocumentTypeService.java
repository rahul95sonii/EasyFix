package com.easyfix.settings.delegate;

import java.util.List;

import com.easyfix.settings.model.DocumentType;

public interface DocumentTypeService {
		
		public List<DocumentType> getDocumentTypeList(int flag) throws Exception;
		public int addUpdateDocumentType(DocumentType documentTypeObj) throws Exception;
		public DocumentType getDocumentTypeDetailsById(int documentTypeId) throws Exception;

}

