package com.easyfix.util.utilityFunction.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.easyfix.util.DBConfig;
import com.easyfix.util.utilityFunction.model.EasyfixerRatingParameters;

public class EasyfixerRatingParametersDaoImpl extends JdbcDaoSupport implements EasyfixerRatingParametersDao{

	@Override
	public EasyfixerRatingParameters getParamDetails(
			int paramId) throws Exception {
		EasyfixerRatingParameters param = null;
		String query = "call sp_ef_easyfixer_rating_parameters_weightage_get_param_details(?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		 param = getJdbcTemplate().queryForObject(query, new Object[]{paramId}, new RowMapper<EasyfixerRatingParameters>(){
			public EasyfixerRatingParameters mapRow(ResultSet rs, int row) throws SQLException {
				
				EasyfixerRatingParameters paramObj = new EasyfixerRatingParameters();
				paramObj.setParamId(rs.getInt("param_id"));
				paramObj.setParamName(rs.getString("param_name"));
				paramObj.setParamWeightage(rs.getString("param_weightage"));
				
				return paramObj; 
			}			
			
		});
		 
		 return param;
	}

	@Override
	public int addUpdateParam(EasyfixerRatingParameters paramObj) throws Exception {
		String sql = "call sp_ef_easyfixer_rating_parameters_weightage_add_update_param(?,?,?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		int insertId = getJdbcTemplate().update(sql, new Object[]{paramObj.getParamId(), paramObj.getParamName(), paramObj.getParamWeightage()});
		
		return insertId;
	}

	@Override
	public List<EasyfixerRatingParameters> getParamList() throws Exception {
		List<EasyfixerRatingParameters> paramList = new ArrayList<EasyfixerRatingParameters>();
		
		String query = "call sp_ef_easyfixer_rating_parameters_weightage_getparamlist()";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		paramList = getJdbcTemplate().query(query, new RowMapper<EasyfixerRatingParameters>(){
			public EasyfixerRatingParameters mapRow(ResultSet rs, int row) throws SQLException {
				
				EasyfixerRatingParameters paramObj = new EasyfixerRatingParameters();
				paramObj.setParamId(rs.getInt("param_id"));
				paramObj.setParamName(rs.getString("param_name"));
				paramObj.setParamWeightage(rs.getString("param_weightage"));
				
				return paramObj; 
			}
			
			
		});
        return paramList;
	}

}
