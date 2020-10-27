package com.easyfix.settings.delegate;

import java.util.List;

import com.easyfix.settings.dao.SkillDao;
import com.easyfix.settings.model.Skill;


public class SkillServiceImpl implements SkillService {
	
	public SkillDao getSkillDao() {
		return skillDao;
	}

	public void setSkillDao(SkillDao skillDao) {
		this.skillDao = skillDao;
	}

	private SkillDao skillDao;
	
	@Override
	public List<Skill> getSkillList() throws Exception {
		return skillDao.getSkillList();
	}

	@Override
	public Skill getSkillDetailsById(int skillId) throws Exception {
		// TODO Auto-generated method stub
		return skillDao.getSkillDetailsById(skillId);
	}
	
	@Override
	public void saveAddEditSkill(Skill skillObj) throws Exception {
		// TODO Auto-generated method stub
		skillDao.saveAddEditSkill(skillObj);
	}
}
