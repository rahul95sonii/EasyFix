package com.easyfix.finance.action;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easyfix.easyfixers.delegate.EasyfixerService;
import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.finance.delegate.FinanceService;
import com.easyfix.finance.model.EasyfixerFinance;
import com.easyfix.finance.model.EasyfixerTransaction;
import com.easyfix.user.delegate.UserService;
import com.easyfix.user.model.User;
import com.easyfix.util.CommonAbstractAction;
import com.easyfix.util.Constants;
import com.opensymphony.xwork2.ModelDriven;

public class EasyfixerFinanceAction extends CommonAbstractAction implements ModelDriven<EasyfixerFinance> {

	private static final Logger logger = LogManager.getLogger(EasyfixerFinance.class);
	private static final long serialVersionUID = 1L;
	
	private String actMenu;
	private String actParent;
	private String title;
	private int status;
	
	private EasyfixerFinance efrFinObj;	
	private User userObj;
	private Easyfixers easyfixerObj;
	private EasyfixerTransaction efrTransObj;
	
	private UserService userService;
	private FinanceService financeService;
	private EasyfixerService easyfixerService;
	
	
	private List<User> NDMList;
	private List<EasyfixerFinance> NDMRechargeList;
	private List<Easyfixers> efrList;
	private List<EasyfixerTransaction> efrTransList;
	
	
	public String ndmCollection() {
		String acttitle= "Easyfix Finance : NDM Collection";
		setActMenu("NDM Collection");
		setActParent("Finance");
		setTitle(acttitle);
		try {
				userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
				NDMList = userService.userListByRole("12,13"); // 12,13 for NDM
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
		
		
	public String ndmEasyfixerList() {
		try {
				userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
				efrList = financeService.ndmEasyfixerList(efrFinObj);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}	

	public String efrRechargeAccount(){
		try {
			int efrId = Integer.parseInt(requestObject.getParameter("efrId"));
			requestObject.setAttribute("efrName", requestObject.getParameter("name"));		
			requestObject.setAttribute("efrId", efrId);
			int ndmId = 0;
			int flag = 1;
			
			NDMRechargeList = financeService.ndmRechargeList(efrId,ndmId,flag);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
		
	}


	public String addUpdateRechargeAmount(){
		try {
					
			userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
			efrFinObj.setCreatedBy(userObj.getUserId());
			efrFinObj.setNdmId(userObj.getUserId());
			status = financeService.addUpdateNdmRechargeAmount(efrFinObj);	
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
		
	}


	public String updateRecharge() {
		String acttitle= "Easyfix Finance : Collection Approval";
		setActMenu("Collection Approval");
		setActParent("Finance");
		setTitle(acttitle);
		try {
			int is_submitted = 0;
			
			NDMRechargeList = financeService.submitToFinanceList(is_submitted);
		//	System.out.println(NDMRechargeList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}
	
	
	
	public String efrTransactionList(){
		try {
			requestObject.setAttribute("efrName", requestObject.getParameter("name"));
			String efrId = requestObject.getParameter("efrId");		
			String startDate = "";
			String endDate = "";
			
			efrTransList = easyfixerService.efrTransactionList(efrId,startDate,endDate);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String approveRecharge(){
		System.out.println("in approveRecharge");
		userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
		try {
			efrTransObj = new EasyfixerTransaction();
			easyfixerObj = new Easyfixers();
			efrTransObj.setCreatedBy(userObj);
			efrTransObj.setJobId(0);
			efrTransObj.setUpdateType("recharge");
			efrTransObj.setRechargeId(Integer.parseInt(requestObject.getParameter("rechargeId")));
			easyfixerObj.setEasyfixerId(Integer.parseInt(requestObject.getParameter("efrId")));
			efrTransObj.setEasyfixer(easyfixerObj);
			efrTransObj.setEfrAmount(Float.valueOf(requestObject.getParameter("efrAmount")));
			efrTransObj.setEfrTransType(Integer.parseInt(requestObject.getParameter("efrTransType")));
			efrTransObj.setEfrTransDesc(requestObject.getParameter("efrTransDesc"));
			
			easyfixerService.updateRechargeAmount(efrTransObj);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}



	public String rechargeListNdm(){
		try {
			int ndmId = Integer.parseInt(requestObject.getParameter("ndmId"));
			int efrId = 0;
			int flag = 2;
			
			NDMRechargeList = financeService.ndmRechargeList(efrId,ndmId,flag);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	public String approveRechargeList(){
		try {
			int ndmId = 0;
			int efrId = 0;
			int flag = 4;
			
			NDMRechargeList = financeService.ndmRechargeList(efrId,ndmId,flag);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Override
	public EasyfixerFinance getModel() {
		return efrFinObj = new EasyfixerFinance();
	}

	public EasyfixerFinance getEfrFinObj() {
		return efrFinObj;
	}

	public EasyfixerFinance setEfrFinObj(EasyfixerFinance efrFinObj) {
		this.efrFinObj = efrFinObj;
		return efrFinObj;
	}
	
	public String toString(){
		return "EasyfixerFinanceAction";
	}


	public String getActMenu() {
		return actMenu;
	}


	public String getActParent() {
		return actParent;
	}


	public String getTitle() {
		return title;
	}


	public User getUserObj() {
		return userObj;
	}


	public Easyfixers getEasyfixerObj() {
		return easyfixerObj;
	}


	public List<User> getNDMList() {
		return NDMList;
	}


	public void setActMenu(String actMenu) {
		this.actMenu = actMenu;
	}


	public void setActParent(String actParent) {
		this.actParent = actParent;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public void setUserObj(User userObj) {
		this.userObj = userObj;
	}


	public void setEasyfixerObj(Easyfixers easyfixerObj) {
		this.easyfixerObj = easyfixerObj;
	}


	public void setNDMList(List<User> nDMList) {
		NDMList = nDMList;
	}




	public UserService getUserService() {
		return userService;
	}


	public void setUserService(UserService userService) {
		this.userService = userService;
	}


	public FinanceService getFinanceService() {
		return financeService;
	}


	public EasyfixerService getEasyfixerService() {
		return easyfixerService;
	}


	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}


	public void setEasyfixerService(EasyfixerService easyfixerService) {
		this.easyfixerService = easyfixerService;
	}


	public List<Easyfixers> getEfrList() {
		return efrList;
	}


	public void setEfrList(List<Easyfixers> efrList) {
		this.efrList = efrList;
	}


	public int getStatus() {
		return status;
	}


	public List<EasyfixerFinance> getNDMRechargeList() {
		return NDMRechargeList;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public void setNDMRechargeList(List<EasyfixerFinance> nDMRechargeList) {
		NDMRechargeList = nDMRechargeList;
	}
	
	public EasyfixerTransaction getEfrTransObj() {
		return efrTransObj;
	}

	public void setEfrTransObj(EasyfixerTransaction efrTransObj) {
		this.efrTransObj = efrTransObj;
	}

	public List<EasyfixerTransaction> getEfrTransList() {
		return efrTransList;
	}

	public void setEfrTransList(List<EasyfixerTransaction> efrTransList) {
		this.efrTransList = efrTransList;
	}

}
