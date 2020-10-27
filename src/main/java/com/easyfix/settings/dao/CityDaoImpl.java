package com.easyfix.settings.dao;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;











import com.easyfix.settings.model.City;
import com.easyfix.settings.model.ZoneCityMapping;
import com.easyfix.settings.dao.CityDao;
import com.easyfix.util.DBConfig;

public class CityDaoImpl extends JdbcDaoSupport implements CityDao {

	private SessionFactory sessionFactory;
	
	@Override
	public List<City> getCityList() throws Exception {
		List<City> cityList = new ArrayList<City>();
		
		String query = "call sp_ef_city_getcitylist()";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		cityList = getJdbcTemplate().query(query, new RowMapper<City>(){
			
			public City mapRow(ResultSet rs, int row) throws SQLException {
				
				City cityObj = new City();
				cityObj.setCityId(rs.getInt("city_id"));
				cityObj.setCityName(rs.getString("city_name"));
				cityObj.setCityStatus(rs.getString("city_status"));
				cityObj.setMaxDistance(rs.getString("city_max_distance"));
				cityObj.setDisplay(rs.getInt("display"));
				return cityObj; 
			
			
			}
		});
        return cityList;
	}

	@Override
	public int addUpdateCity(City cityObj) throws Exception {

		
		String sql = "call sp_ef_city_add_update_city(?,?,?,?,?)";
//		DataSource ds=DBConfig.getContextDataSource();			
//		getJdbcTemplate().setDataSource(ds);

		int insertId = getJdbcTemplate().update(sql, new Object[]{cityObj.getCityId(), cityObj.getCityName(),cityObj.getCityStatus(),cityObj.getMaxDistance(),cityObj.getDisplay()});
		
		return insertId;
	
	}
	
	
	@Override
	public void mapZoneCity(City cityObj) throws Exception {
		
		
		String sql = "call sp_ef_city_zone_mapping()";

		getJdbcTemplate().update(sql, new Object[]{});
		
	}
	

	@Override
	public City getCityDetailsById(int cityId) throws Exception {
		City city = null;
		String query = "call sp_ef_city_getcity_details(?)";
		DataSource ds=DBConfig.getContextDataSource();			
		getJdbcTemplate().setDataSource(ds);
		 city = getJdbcTemplate().queryForObject(query, new Object[]{cityId}, new RowMapper<City>(){
			public City mapRow(ResultSet rs, int row) throws SQLException {
				
				City cityObj = new City();
				cityObj.setCityId(rs.getInt("city_id"));
				cityObj.setCityName(rs.getString("city_name"));
				cityObj.setCityStatus(rs.getString("city_status"));
				cityObj.setMaxDistance(rs.getString("city_max_distance"));
				cityObj.setDisplay(rs.getInt("display"));
				return cityObj; 
				}
			
		});
		 
		 return city;
	}

	@Override
	public List<ZoneCityMapping> getZoneCityListByCity(City city) throws Exception {
		List<ZoneCityMapping> list = new ArrayList<>();
		Session session = sessionFactory.openSession();
		try{
		Criteria cr = session.createCriteria(City.class);
		if(city.getCityId()>0)
			cr.add(Restrictions.eq("cityId", city.getCityId()));
		City c = (City) cr.uniqueResult();
		System.out.println(c.getZones().size());
		 list.addAll(c.getZones());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		 return list;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	

}

