package com.easyfix.settings.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.easyfix.settings.model.DocumentType;
import com.easyfix.util.DBConfig;

public class DocumentTypeDaoImpl extends JdbcDaoSupport implements DocumentTypeDao {

	@Override
	public List<DocumentType> getDocumentTypeList(int flag) throws Exception {
		List<DocumentType> documentTypeList = new ArrayList<DocumentType>();
		
		String query = "call sp_ef_document_type_getdocumenttypelist(?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		documentTypeList = getJdbcTemplate().query(query, new Object[]{flag}, new RowMapper<DocumentType>(){
			public DocumentType mapRow(ResultSet rs, int row) throws SQLException {
				
				DocumentType documentTypeObj = new DocumentType();
				documentTypeObj.setDocumentTypeId(rs.getInt("document_type_id"));
				documentTypeObj.setDocumentName(rs.getString("document_name"));
				documentTypeObj.setDocumentMandatory(rs.getString("document_mandatory"));
				documentTypeObj.setDocumentTypeStatus(rs.getString("document_type_status"));
				
				return documentTypeObj; 
			}
			
			
		});
        return documentTypeList;
	}
	
	@Override
	public int addUpdateDocumentType(DocumentType documentTypeObj)
			throws Exception {
		String sql = "call sp_ef_document_type_add_update(?,?,?,?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		int insertId = getJdbcTemplate().update(sql, new Object[]{documentTypeObj.getDocumentTypeId(), 
				documentTypeObj.getDocumentName(), documentTypeObj.getDocumentMandatory(), 
				documentTypeObj.getDocumentTypeStatus()});
		return insertId;
	}

	@Override
	public DocumentType getDocumentTypeDetailsById(int documentTypeId)
			throws Exception {
			DocumentType documentType = null;
			String query = "call sp_ef_document_type_getdetails_by_id(?)";
			DataSource ds=DBConfig.getContextDataSource();			
			getJdbcTemplate().setDataSource(ds);
			documentType = getJdbcTemplate().queryForObject(query, new Object[]{documentTypeId}, new RowMapper<DocumentType>(){
				public DocumentType mapRow(ResultSet rs, int row) throws SQLException {
					
					DocumentType documentTypeObj = new DocumentType();
					documentTypeObj.setDocumentTypeId(rs.getInt("document_type_id"));
					documentTypeObj.setDocumentName(rs.getString("document_name"));
					documentTypeObj.setDocumentMandatory(rs.getString("document_mandatory"));
					documentTypeObj.setDocumentTypeStatus(rs.getString("document_type_status"));
					
					return documentTypeObj; 
				}			
				
			});
			 
			 return documentType;
		}
}
