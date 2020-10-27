package com.easyfix.settings.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.easyfix.settings.model.RetailRateCard;
import com.easyfix.settings.model.ServiceType;
import com.easyfix.util.DBConfig;

public class RetailRateCardDaoImpl extends JdbcDaoSupport implements RetailRateCardDao{

	@Override
	public List<RetailRateCard> getRetailRateCardList() throws Exception {
		List<RetailRateCard> retailRateCardList = new ArrayList<RetailRateCard>();
		
		String query = "call sp_ef_retailratecard_getretailratecardlist()";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		retailRateCardList = getJdbcTemplate().query(query, new RowMapper<RetailRateCard>(){
			public RetailRateCard mapRow(ResultSet rs, int row) throws SQLException {
				
				RetailRateCard retailRateCardObj = new RetailRateCard();
				ServiceType serviceTypeObj = new ServiceType();
				retailRateCardObj.setRetailRateCardId(rs.getInt("rrc_id"));
				retailRateCardObj.setServiceTypeId(rs.getInt("rrc_servicetype_id"));
				serviceTypeObj.setServiceTypeName(rs.getString("service_type_name"));
				retailRateCardObj.setRetailRateCardName(rs.getString("rrc_ratecard_name"));
				retailRateCardObj.setRetailRateCardPrice(rs.getInt("rrc_ratecard_price"));
				
				return retailRateCardObj; 
			}
			
			
		});
        return retailRateCardList;
	}

	@Override
	public RetailRateCard getRetailRateCardDetailsById(int retailRateCardId)
			throws Exception {
		RetailRateCard retailRateCard = null;
		String query = "call sp_ef_retailratecard_getretailratecarddetails_by_id(?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		retailRateCard = getJdbcTemplate().queryForObject(query, new Object[]{retailRateCardId}, new RowMapper<RetailRateCard>(){
			public RetailRateCard mapRow(ResultSet rs, int row) throws SQLException {
				
				RetailRateCard retailRateCardObj = new RetailRateCard();
				ServiceType serviceTypeObj = new ServiceType();
				retailRateCardObj.setRetailRateCardId(rs.getInt("rrc_id"));
				retailRateCardObj.setServiceTypeId(rs.getInt("rrc_servicetype_id"));
				serviceTypeObj.setServiceTypeName(rs.getString("service_type_name"));
				retailRateCardObj.setRetailRateCardName(rs.getString("rrc_ratecard_name"));
				retailRateCardObj.setRetailRateCardPrice(rs.getInt("rrc_ratecard_price"));
				
				return retailRateCardObj; 
			}			
			
		});
		 
		 return retailRateCard;
	}

	@Override
	public int addUpdateRetailRateCard(RetailRateCard retailRateCardObj)
			throws Exception {
		String sql = "call sp_ef_retailratecard_add_update_retailratecard(?,?,?,?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		int insertId = getJdbcTemplate().update(sql, new Object[]{retailRateCardObj.getRetailRateCardId(), retailRateCardObj.getServiceTypeId(),
				retailRateCardObj.getRetailRateCardName(), retailRateCardObj.getRetailRateCardPrice()});
		
		return insertId;
	}

}
