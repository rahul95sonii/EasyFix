package com.easyfix.util.utilityFunction.action;

import java.util.List;

import com.easyfix.util.CommonAbstractAction;
import com.easyfix.util.utilityFunction.delegate.EasyfixerRatingParametersService;
import com.easyfix.util.utilityFunction.model.EasyfixerRatingParameters;
import com.opensymphony.xwork2.ModelDriven;

public class EasyfixerRatingParametersAction extends CommonAbstractAction implements ModelDriven<EasyfixerRatingParameters> {
	private static final long serialVersionUID = 1L;

	private EasyfixerRatingParametersService easyfixerRatingParametersService;
	private EasyfixerRatingParameters paramObj;
	private String menu;
	private String title;
	private String redirectUrl;
	
	private List<EasyfixerRatingParameters> paramList;
	
	@Override
	public EasyfixerRatingParameters getModel() {
		return setParamObj(new EasyfixerRatingParameters());
	}
	
	public String param() throws Exception {
		
		try {
			
			String actmenu= "parameters";
			String acttitle= "Easyfix : Manage parameters";
			setMenu(actmenu);
			setTitle(acttitle);
			
			paramList = easyfixerRatingParametersService.getParamList();
			System.out.println(paramList);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		return SUCCESS;
	}
	
	public String addEditParam() throws Exception {
		
		try {
			
				if(paramObj.getParamId() != 0) {
					paramObj = easyfixerRatingParametersService.getParamDetails(paramObj.getParamId());
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		
		return SUCCESS;
	}
	
	public String addUpdateParam() throws Exception {		
		
		int stauts = easyfixerRatingParametersService.addUpdateParam(paramObj);
		return SUCCESS;
		
	}
	



	public EasyfixerRatingParametersService getEasyfixerRatingParametersService() {
		return easyfixerRatingParametersService;
	}

	public void setEasyfixerRatingParametersService(
			EasyfixerRatingParametersService easyfixerRatingParametersService) {
		this.easyfixerRatingParametersService = easyfixerRatingParametersService;
	}

	public EasyfixerRatingParameters getParamObj() {
		return paramObj;
	}

	public EasyfixerRatingParameters setParamObj(EasyfixerRatingParameters paramObj) {
		this.paramObj = paramObj;
		return paramObj;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
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

	public List<EasyfixerRatingParameters> getParamList() {
		return paramList;
	}

	public void setParamList(List<EasyfixerRatingParameters> paramList) {
		this.paramList = paramList;
	}
	@Override // for RestrictAccessToUnauthorizedActionInterceptor
	public String toString(){
		return "EasyfixerRatingParametersAction";
	}
	
}
