package com.easyfix.finance.action;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easyfix.easyfixers.delegate.EasyfixerService;
import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.finance.delegate.FinanceService;
import com.easyfix.finance.model.Finance;
import com.easyfix.user.delegate.UserService;
import com.easyfix.user.model.User;
import com.easyfix.util.CommonAbstractAction;
import com.easyfix.util.Constants;
import com.opensymphony.xwork2.ModelDriven;

public class FinanceAction extends CommonAbstractAction implements ModelDriven<Finance> {
	private static final Logger logger = LogManager.getLogger(FinanceAction.class);
	private static final long serialVersionUID = 1L;
	
	private Finance financeObj;
	private Easyfixers easyfixerObj;
	private String actMenu;
	private String actParent;
	private String actParent2;
	private String title;
	private FinanceService financeService;
	private EasyfixerService easyfixerService;
	private UserService userService;
	private List<Easyfixers> easyfixerList;
	private List<Finance> transactionList;
	private User userObj;
	private List<User> userList;
	private int status;
	private List<User> NDMList;
	private List<Finance> NDMRechargeList;

	@Override
	public Finance getModel() {
		return financeObj = new Finance();
	}
	
	public String easyfixerDebit() throws Exception {
		
		try {
			String acttitle= "Easyfix Finance : EasyFixer Debit";
			setActMenu("Easyfixer Debit");
			setActParent("Finance");
			setActParent2("Easyfixer Transaction");
			setTitle(acttitle);
			
			userObj = new User();
			
			userObj.setUserId(100);	//100 for anybody
			financeObj.setEasyfixerId(0);
			financeObj.setFromDate("");
			financeObj.setToDate("");
			financeObj.setTransactionType(0);	//0:All, 1:Debit, 2:Credit
			financeObj.setCreatedBy(userObj); 
			
			easyfixerList = easyfixerService.getEasyfixerList(2); // 2 for all easyfixer
			//userList = userService.getUserList(2); //2 for all user
			
			transactionList = financeService.getTransactionList(financeObj);	
			financeObj.setTransactionType(1);	//0:All, 1:Debit, 2:Credit
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		
		return SUCCESS;
		
	}
	
	
public String easyfixerCredit() throws Exception {
		
		try {
			String acttitle= "Easyfix Finance : EasyFixer Credit";
			setActMenu("Easyfixer Credit");
			setActParent("Finance");
			setActParent2("Easyfixer Transaction");
			setTitle(acttitle);
			
			userObj = new User();
			userObj.setUserId(100);	//100 for anybody
			financeObj.setEasyfixerId(0);
			financeObj.setFromDate("");
			financeObj.setToDate("");
			financeObj.setTransactionType(0);	//0:All, 1:Debit, 2:Credit
			financeObj.setCreatedBy(userObj); 
			
			easyfixerList = easyfixerService.getEasyfixerList(2); // 2 for all easyfixer
			//userList = userService.getUserList(2); //2 for all user
			
			transactionList = financeService.getTransactionList(financeObj);	
			financeObj.setTransactionType(2);	//0:All, 1:Debit, 2:Credit
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.catching(e);
		}
		
		return SUCCESS;
		
	}
	
	
	
public String transactionList() throws Exception {
	
	try {
		if(financeObj.getFromDate().equalsIgnoreCase(""))
		{
			financeObj.setFromDate("");
			financeObj.setToDate("");
		}
		else {
			financeObj.setFromDate(financeService.convertDateToString(financeObj.getFromDate()));	
			financeObj.setToDate(financeService.convertDateToString(financeObj.getToDate()));	
		}
		
		userObj = new User();
		userObj.setUserId(100);	//100 for anybody
		financeObj.setCreatedBy(userObj); 
		transactionList = financeService.getTransactionList(financeObj);	
		
		String currentBalance = financeService.getCurrentBalance(financeObj.getEasyfixerId());
		
		requestObject.setAttribute("currentBalance", currentBalance);
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	}
	
	return SUCCESS;
	
}	
	
	
public String addEditTransaction() throws Exception {
	
	try {
		
		easyfixerList = easyfixerService.getEasyfixerList(1); // 1 for all Active easyfixer
		int transType = Integer.parseInt(requestObject.getParameter("transType"));
				
		requestObject.setAttribute("transType", transType);		
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	}
	
	return SUCCESS;
	
}	
	
	
public String addUpdateEasyFixerAmount() throws Exception {
	
	try {
		userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
		financeObj.setCreatedBy(userObj);
				
		status = financeService.addUpdateEasyfixerAmount(financeObj);	
		
	} catch (Exception e) {
		e.printStackTrace();
		logger.catching(e);
	}
	
	return SUCCESS;	
}	



	public Finance getFinanceObj() {
		return financeObj;
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

	public FinanceService getFinanceService() {
		return financeService;
	}

	public EasyfixerService getEasyfixerService() {
		return easyfixerService;
	}

	public List<Easyfixers> getEasyfixerList() {
		return easyfixerList;
	}

	public List<Finance> getTransactionList() {
		return transactionList;
	}

	public void setFinanceObj(Finance financeObj) {
		this.financeObj = financeObj;
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

	public void setFinanceService(FinanceService financeService) {
		this.financeService = financeService;
	}

	public void setEasyfixerService(EasyfixerService easyfixerService) {
		this.easyfixerService = easyfixerService;
	}

	public void setEasyfixerList(List<Easyfixers> easyfixerList) {
		this.easyfixerList = easyfixerList;
	}

	public void setTransactionList(List<Finance> transactionList) {
		this.transactionList = transactionList;
	}


	public User getUserObj() {
		return userObj;
	}


	public void setUserObj(User userObj) {
		this.userObj = userObj;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	@Override //// for RestrictAccessToUnauthorizedActionInterceptor
    public String toString(){
    	return "FinanceAction";
    }

	public List<User> getNDMList() {
		return NDMList;
	}

	public void setNDMList(List<User> nDMList) {
		NDMList = nDMList;
	}

	public Easyfixers getEasyfixerObj() {
		return easyfixerObj;
	}

	public void setEasyfixerObj(Easyfixers easyfixerObj) {
		this.easyfixerObj = easyfixerObj;
	}

	public List<Finance> getNDMRechargeList() {
		return NDMRechargeList;
	}

	public void setNDMRechargeList(List<Finance> nDMRechargeList) {
		NDMRechargeList = nDMRechargeList;
	}

	public String getActParent2() {
		return actParent2;
	}

	public void setActParent2(String actParent2) {
		this.actParent2 = actParent2;
	}

	
}
