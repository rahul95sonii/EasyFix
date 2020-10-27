package com.easyfix.settings.action;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easyfix.settings.delegate.SkillService;
import com.easyfix.settings.model.Skill;
import com.easyfix.util.CommonAbstractAction;
import com.opensymphony.xwork2.ModelDriven;

public class SkillAction extends CommonAbstractAction implements ModelDriven<Skill> {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LogManager.getLogger(SkillAction.class);
		private String actMenu;
		private String actParent;
		private String title;
		private String redirectUrl;
		
		private Skill skillObj;
		

		@Override
		public Skill getModel() {
			return setSkillObj(new Skill());
		}
		
		private List<Skill> skillList;
		private SkillService skillService;
		
		public String skill() throws Exception {
			
			try {

				String acttitle= "Easyfix : Manage Skill";
				setActMenu("Skill");
				setActParent("Settings");
				setTitle(acttitle);
				
				skillList = skillService.getSkillList();
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.catching(e);
			}
					
			return SUCCESS;
		}
		
		
		public String addEditSkill(){
			
			try {
				
					if(skillObj.getSkillId() != 0) {
						skillObj = skillService.getSkillDetailsById(skillObj.getSkillId());
					}
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.catching(e);
			}
			
			
			return SUCCESS;
		}
		
		
		
		
		public String saveAddEditSkill(){
			
			try {
				
				 skillService.saveAddEditSkill(skillObj);
				
			} catch (Exception e) {
				e.printStackTrace();
				logger.catching(e);
			}
			
			
			return SUCCESS;
		}
		
		
	
		public List<Skill> getSkillList() {
			return skillList;
		}

		public void setSkillList(List<Skill> skillList) {
			this.skillList = skillList;
		}

		public SkillService getSkillService() {
			return skillService;
		}

		public void setSkillService(SkillService skillService) {
			this.skillService = skillService;
		}

		public String getActMenu() {
			return actMenu;
		}


		public void setActMenu(String actMenu) {
			this.actMenu = actMenu;
		}


		public String getActParent() {
			return actParent;
		}


		public void setActParent(String actParent) {
			this.actParent = actParent;
		}
		public String getTitle() {
			return title;
		}
	
		public void setTitle(String title) {
			this.title = title;
		}

		public String getRedirectUrl() {
			return redirectUrl;
		}

		public void setRedirectUrl(String redirectUrl) {
			this.redirectUrl = redirectUrl;
		}
		public Skill getSkillObj() {
			return skillObj;
		}

		public Skill setSkillObj(Skill skillObj) {
			this.skillObj = skillObj;
			return skillObj;
		}

		@Override // for RestrictAccessToUnauthorizedActionInterceptor
		public String toString(){
			return "SkillAction";
		}

		
	}

		
		