package com.easyfix.settings.model;

public class Skill {

	private int skillId;
	private String skillName;
	private String skillDesc;
	private int skillStatus;
	private String insertDate;
	
	public int getSkillId() {
		return skillId;
	}
	public void setSkillId(int skillId) {
		this.skillId = skillId;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	public String getSkillDesc() {
		return skillDesc;
	}
	public void setSkillDesc(String skillDesc) {
		this.skillDesc = skillDesc;
	}
	public int getSkillStatus() {
		return skillStatus;
	}
	public void setSkillStatus(int skillStatus) {
		this.skillStatus = skillStatus;
	}
	public String getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
	
}
