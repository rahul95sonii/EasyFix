package com.easyfix.settings.dao;

import java.util.List;

import com.easyfix.settings.model.Skill;

public interface SkillDao {
	
	public List<Skill> getSkillList() throws Exception;
	public Skill getSkillDetailsById(int skillId) throws Exception;
	public void saveAddEditSkill(Skill skillObj) throws Exception;

}
