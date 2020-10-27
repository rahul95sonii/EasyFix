package com.easyfix.settings.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.easyfix.settings.model.ClientRateCard;
import com.easyfix.settings.model.ServiceType;
import com.easyfix.util.DBConfig;

public class ClientRateCardDaoImpl extends JdbcDaoSupport implements ClientRateCardDao{

	@Override
	public List<ClientRateCard> getClientRateCardList() throws Exception {
		List<ClientRateCard> clientRateCardList = new ArrayList<ClientRateCard>();
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		String query = "call sp_ef_clientratecard_getclientratecardlist()";
		clientRateCardList = getJdbcTemplate().query(query, new RowMapper<ClientRateCard>(){
			public ClientRateCard mapRow(ResultSet rs, int row) throws SQLException {
				
				ClientRateCard clientRateCardObj = new ClientRateCard();
				ServiceType serviceTypeObj = new ServiceType();
				clientRateCardObj.setClientRateCardId(rs.getInt("crc_id"));
				clientRateCardObj.setServiceTypeId(rs.getInt("crc_servicetype_id"));
				serviceTypeObj.setServiceTypeName(rs.getString("service_type_name"));
				clientRateCardObj.setClientRateCardName(rs.getString("crc_ratecard_name"));
				
				return clientRateCardObj; 
			}
			
			
		});
        return clientRateCardList;
	}

	@Override
	public ClientRateCard getClientRateCardDetailsById(int clientRateCardId)
			throws Exception {
		ClientRateCard clientRateCard = null;
		String query = "call sp_ef_clientratecard_getclientratecarddetails_by_id(?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		clientRateCard = getJdbcTemplate().queryForObject(query, new Object[]{clientRateCardId}, new RowMapper<ClientRateCard>(){
			public ClientRateCard mapRow(ResultSet rs, int row) throws SQLException {
				
				ClientRateCard clientRateCardObj = new ClientRateCard();
				ServiceType serviceTypeObj = new ServiceType();
				clientRateCardObj.setClientRateCardId(rs.getInt("crc_id"));
				clientRateCardObj.setServiceTypeId(rs.getInt("crc_servicetype_id"));
				serviceTypeObj.setServiceTypeName(rs.getString("service_type_name"));
				clientRateCardObj.setClientRateCardName(rs.getString("crc_ratecard_name"));
				
				return clientRateCardObj; 
			}			
			
		});
		 
		 return clientRateCard;
	}

	
	@Override
	public int addUpdateClientRateCard(ClientRateCard clientRateCardObj)
			throws Exception {

		String sql = "call sp_ef_clientratecard_add_update_clientratecard(?,?,?,?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		int insertId = getJdbcTemplate().update(sql, new Object[]{clientRateCardObj.getClientRateCardId(), clientRateCardObj.getServiceTypeId(), 
					clientRateCardObj.getClientRateCardName(), clientRateCardObj.getUpdatedBy()});
		return insertId;
		

}

	@Override
	public List<ClientRateCard> getRateCardListForClient(int clientId, int serviceTypeId) throws Exception {
		List<ClientRateCard> clientRateCardList = new ArrayList<ClientRateCard>();
		
		String query = "call sp_ef_clientratecard_getclientratecardlist_for_client(?,?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		clientRateCardList = getJdbcTemplate().query(query, new Object[]{clientId,serviceTypeId}, new RowMapper<ClientRateCard>(){
			public ClientRateCard mapRow(ResultSet rs, int row) throws SQLException {
				
				ClientRateCard clientRateCardObj = new ClientRateCard();
				clientRateCardObj.setClientRateCardId(rs.getInt("crc_id"));
				clientRateCardObj.setClientRateCardName(rs.getString("crc_ratecard_name"));
				
				return clientRateCardObj; 
			}
			
			
		});
        return clientRateCardList;
	}
	
}
