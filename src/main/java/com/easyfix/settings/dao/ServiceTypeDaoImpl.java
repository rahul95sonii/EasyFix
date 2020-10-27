package com.easyfix.settings.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.easyfix.settings.model.ServiceType;
import com.easyfix.settings.dao.ServiceTypeDao;
import com.easyfix.util.DBConfig;

public class ServiceTypeDaoImpl extends JdbcDaoSupport implements ServiceTypeDao  {

	@Override
	public List<ServiceType> getSerTypeList(int flag) throws Exception {
		List<ServiceType> serviceTypeList = new ArrayList<ServiceType>();
		
		String query = "call sp_ef_service_type_getservicetypelist(?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		serviceTypeList = getJdbcTemplate().query(query, new Object[]{flag}, new RowMapper<ServiceType>(){
			public ServiceType mapRow(ResultSet rs, int row) throws SQLException {
				
				ServiceType serviceTypeObj = new ServiceType();
				serviceTypeObj.setServiceTypeId(rs.getInt("service_type_id"));
				serviceTypeObj.setServiceTypeName(rs.getString("service_type_name"));
				serviceTypeObj.setServiceTypeDesc(rs.getString("service_type_desc"));
				serviceTypeObj.setServiceTypeStatus(rs.getString("service_type_status"));
				serviceTypeObj.setDisplay(rs.getInt("display"));
				return serviceTypeObj; 
			}
			
			
		});
        return serviceTypeList;
	}

	@Override
	public int addUpdateSerType(ServiceType serviceTypeObj) throws Exception {
		String sql = "call sp_ef_service_type_add_update(?,?,?,?,?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		int insertId = getJdbcTemplate().update(sql, new Object[]{serviceTypeObj.getServiceTypeId(), 
						serviceTypeObj.getServiceTypeName(), serviceTypeObj.getServiceTypeDesc(), 
						serviceTypeObj.getServiceTypeStatus(),serviceTypeObj.getDisplay()});
		return insertId;
	}

	@Override
	public ServiceType getSerTypeDetailsById(int serviceTypeId)
			throws Exception {
		ServiceType serviceType = null;
		String query = "call sp_ef_service_type_getdetails_by_id(?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		serviceType = getJdbcTemplate().queryForObject(query, new Object[]{serviceTypeId}, new RowMapper<ServiceType>(){
			public ServiceType mapRow(ResultSet rs, int row) throws SQLException {
				
				ServiceType serviceTypeObj = new ServiceType();
				serviceTypeObj.setServiceTypeId(rs.getInt("service_type_id"));
				serviceTypeObj.setServiceTypeName(rs.getString("service_type_name"));
				serviceTypeObj.setServiceTypeDesc(rs.getString("service_type_desc"));
				serviceTypeObj.setServiceTypeStatus(rs.getString("service_type_status"));
				serviceTypeObj.setDisplay(rs.getInt("display"));
				return serviceTypeObj; 
			}			
			
		});
		 
		 return serviceType;
	}

	@Override
	public List<ServiceType> getSerTypeListByClientId(int fkClientId, int flag) throws Exception {
		List<ServiceType> serviceTypeList = new ArrayList<ServiceType>();
		
		String query = "call sp_ef_service_type_getservicetypelist_byclientId(?,?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		serviceTypeList = getJdbcTemplate().query(query, new Object[]{fkClientId,flag}, new RowMapper<ServiceType>(){
			public ServiceType mapRow(ResultSet rs, int row) throws SQLException {
				
				ServiceType serviceTypeObj = new ServiceType();
				serviceTypeObj.setServiceTypeId(rs.getInt("service_type_id"));
				serviceTypeObj.setServiceTypeName(rs.getString("service_type_name"));
				serviceTypeObj.setServiceTypeDesc(rs.getString("service_type_desc"));
				serviceTypeObj.setServiceTypeStatus(rs.getString("service_type_status"));
				serviceTypeObj.setDisplay(rs.getInt("display"));
				return serviceTypeObj; 
			}
			
			
		});
        return serviceTypeList;
	}
	


}
