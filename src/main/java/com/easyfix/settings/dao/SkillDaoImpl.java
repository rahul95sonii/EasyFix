package com.easyfix.settings.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.easyfix.settings.model.Skill;
import com.easyfix.util.DBConfig;

public class SkillDaoImpl  extends JdbcDaoSupport implements SkillDao {
	
	private SessionFactory sessionFactory;

	
	@Override
	public List<Skill> getSkillList() throws Exception {
		List<Skill> skillList = new ArrayList<Skill>();
		
		String query = "SELECT skill_id, skill_name, skill_desc, skill_status FROM tbl_skill_master ";
		skillList = getJdbcTemplate().query(query, new RowMapper<Skill>(){
			public Skill mapRow(ResultSet rs, int row) throws SQLException {
				
				Skill skillObj = new Skill();
				skillObj.setSkillId(rs.getInt("skill_id"));
				skillObj.setSkillName(rs.getString("skill_name"));
				skillObj.setSkillDesc(rs.getString("skill_desc"));
				skillObj.setSkillStatus(rs.getInt("skill_status"));
				return skillObj; 
			}
			
			
		});
        return skillList;
	}
	
	

	@Override
	public Skill getSkillDetailsById(int skillId) throws Exception {
		Skill skill = null;
		String query = "SELECT skill_id, skill_name, skill_desc, skill_status FROM `tbl_skill_master` WHERE skill_id = ? ";
		skill = getJdbcTemplate().queryForObject(query, new Object[]{skillId}, new RowMapper<Skill>(){
			public Skill mapRow(ResultSet rs, int row) throws SQLException {
				
				Skill skillObj = new Skill();
				skillObj.setSkillId(rs.getInt("skill_id"));
				skillObj.setSkillName(rs.getString("skill_name"));
				skillObj.setSkillDesc(rs.getString("skill_desc"));
				skillObj.setSkillStatus(rs.getInt("skill_status"));
				return skillObj; 
				}
			
		});
		 
		 return skill;
	}
	
	
	@Override
	public void saveAddEditSkill(Skill skillObj) throws Exception {
		String sql = "call sp_ef_skill_add_update_skill(?,?,?,?)";

		getJdbcTemplate().update(sql, new Object[]{skillObj.getSkillId(), skillObj.getSkillName(),skillObj.getSkillDesc(),skillObj.getSkillStatus()});
		
	}


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


}
