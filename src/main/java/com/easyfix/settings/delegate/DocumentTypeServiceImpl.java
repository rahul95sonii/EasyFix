package com.easyfix.settings.delegate;

import java.util.List;

import com.easyfix.settings.dao.DocumentTypeDao;
import com.easyfix.settings.model.DocumentType;

public class DocumentTypeServiceImpl implements DocumentTypeService {
	
	private DocumentTypeDao documentTypeDao;

	public DocumentTypeDao getDocumentTypeDao() {
		return documentTypeDao;
	}

	public void setDocumentTypeDao(DocumentTypeDao documentTypeDao) {
		this.documentTypeDao = documentTypeDao;
	}

	@Override
	public List<DocumentType> getDocumentTypeList(int flag) throws Exception {
		return documentTypeDao.getDocumentTypeList(flag);
	}

	@Override
	public int addUpdateDocumentType(DocumentType documentTypeObj)
			throws Exception {
		return documentTypeDao.addUpdateDocumentType(documentTypeObj);
	}

	@Override
	public DocumentType getDocumentTypeDetailsById(int documentTypeId)
			throws Exception {
		return documentTypeDao.getDocumentTypeDetailsById(documentTypeId);
	}

}
