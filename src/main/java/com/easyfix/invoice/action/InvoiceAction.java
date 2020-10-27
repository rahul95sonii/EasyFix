package com.easyfix.invoice.action;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jfree.util.Log;












import com.easyfix.Jobs.action.JobAction;
import com.easyfix.Jobs.delegate.JobService;
import com.easyfix.Jobs.model.JobSelectedServices;
import com.easyfix.Jobs.model.Jobs;
import com.easyfix.clients.model.Clients;
import com.easyfix.invoice.delegate.InvoiceService;
import com.easyfix.invoice.model.Invoice;
//import com.easyfix.triggers.GenerateClientInvoice;
import com.easyfix.user.model.User;
import com.easyfix.util.CommonAbstractAction;
import com.easyfix.util.CommonMasterData;
import com.easyfix.util.Constants;
import com.easyfix.util.Zipping;
import com.easyfix.util.triggers.GenerateClientInvoice;
import com.opensymphony.xwork2.ModelDriven;

public class InvoiceAction extends CommonAbstractAction implements ModelDriven<Invoice> {

	private static final long serialVersionUID = 1L;
	private Invoice invoiceObj;
	private Jobs jobObj;
	private JobService jobService;
	private InvoiceService invoiceService;
	private String actMenu;
	private String actParent;
	private String title;
	private String actParent2;
	
	private CommonMasterData commonMasterData;
	private List<Clients> clientList;
	private List<Invoice> invoiceList;
	private Map<String, List<Invoice>> clientInvoiceMap;
	private static final Logger logger = LogManager.getLogger(InvoiceAction.class);
	private List<JobSelectedServices> jobServiceList;
	private Jobs jobsObj;
	public String clientInvoice(){
		
		setActMenu("Client Invoice");			
		setActParent("Finance");
		setActParent2("Invoice");
		setTitle("Easyfix : Invoice");	
		
		try {
			commonMasterData = new CommonMasterData();
			//clientList = commonMasterData.clientList();	
			invoiceList = invoiceService.clientInvoiceList();
			
			clientInvoiceMap = invoiceService.filterInvoiceListByClient(invoiceList);			
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
public String clientInvocieDetail(){
		
		
		System.out.println("in clientInvocieDetail");
		System.out.println(invoiceObj.getInvClientId());
		System.out.println(invoiceObj.getInvoiceId());
		try {
			invoiceObj  = invoiceService.getInvoiceDetailById(invoiceObj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SUCCESS;
	}


public String clientInvocieList(){

	User userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
	//logger.info(userObj.getUserName()+ "started checkin job Id:"+invoiceObj.getJobId());
	try{

		String loc = requestObject.getParameter("loc");
		jobsObj = jobService.getJobDetails(invoiceObj.getJobId());
		jobServiceList = jobService.getJobServiceList(jobsObj.getJobId(),1); // 1 for active service
		double total = 0;
		int star = 0;
		for (JobSelectedServices s : jobServiceList) {
			if(s.getJobChargeType() == 0)					
				star = 1;

			total = total + Double.parseDouble(s.getTotalCharge());		

		}

		requestObject.setAttribute("totalSum", total);
		requestObject.setAttribute("star", star);

		requestObject.setAttribute("loc", loc);
	}
	catch(Exception e){
		e.printStackTrace();
	}
	return SUCCESS;
}
public String modifyJobServiceFromInvoice() throws Exception{
	
	String msg= "SUCCESSFULL!!";
	requestObject.setAttribute("msg", msg);
	User userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
	invoiceObj.setInvoiceChangedBy(userObj.getUserId());
	invoiceService.modifyJobServiceFromInvoice(invoiceObj);
	return SUCCESS;
	
}

	public String generateClientInvoice(){
		//private GenerateClientInvoice GenerateClientInvoice;
		System.out.println("in genertaeClientInvoice ");
		try {
		//List<Invoice> list = 	invoiceService.clientInvoiceListHibernate();
		//System.out.println(list.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	

	public String generateAllInvoices() {
		System.out.println("in generateAllInvoices");
		User userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
		logger.info("invoice generation requetsed by:"+userObj.getUserName()+userObj.getUserId());
		String msg = "UNSUCCESSFUL";
		try{	
			Jobs job = new Jobs();
			job.setJobId(0);
			job.setUpdatedBy(userObj.getUserId());
			job.setJobFlag("generate All Invoice");
				jobService.updateUserActionLog(job);
		

			
					
			GenerateClientInvoice.generateAllInvoices("0");
			
		
			msg = "Invoice generation successful";
			}
		catch(Exception e){
			e.printStackTrace();
			logger.catching(e);
		}
		requestObject.setAttribute("msg", msg);
		logger.info("invoice generation completed by:"+userObj.getUserName()+userObj.getUserId());
		return SUCCESS;
	}
	
	public String generateInvoiceByClient() {
		System.out.println("in generateInvoiceByClient");
		User userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
		logger.info("client id: "+invoiceObj.getInvClientId()+ " invoice generation requetsed by :"+userObj.getUserName()+userObj.getUserId());
		String msg = "UNSUCCESSFUL";
		try{	
			Jobs job = new Jobs();
			job.setJobId(0);
			job.setUpdatedBy(userObj.getUserId());
			job.setJobFlag("generate Invoice for Client:"+invoiceObj.getInvClientId());
				jobService.updateUserActionLog(job);
			
			GenerateClientInvoice.generateInvoiceByClient("0",invoiceObj.getInvClientId() );
			
			msg = "Invoice generation successful";
			}
		catch(Exception e){
			e.printStackTrace();
			logger.catching(e);
		}
		requestObject.setAttribute("msg", msg);
		logger.info("client id: "+invoiceObj.getInvClientId()+" invoice generation completed by:"+userObj.getUserName()+userObj.getUserId());
		return SUCCESS;
	}
	
	
	public String zipAndDownloadAllInvoices(){
		System.out.println("in zipAndDownloadAllInvoices");
		User userObj = (User) requestObject.getSession().getAttribute(Constants.USER_OBJECT);
		logger.info("invoice zip and download requetsed by:"+userObj.getUserName()+userObj.getUserId());
		String msg = "UNSUCCESSFUL";
		try{

			Jobs job = new Jobs();
			job.setJobId(0);
			job.setUpdatedBy(userObj.getUserId());
			job.setJobFlag("zip and download invoice");
				jobService.updateUserActionLog(job);
		Zipping.zip();
		msg = "zipping successful";
			//GenerateClientInvoice.generateAllInvoices("kundan", "0");
			//GenerateClientInvoice.generateAllInvoices("kundan","0");
		
		}
		catch(Exception e){
			e.printStackTrace();
			logger.catching(e);
		}
		requestObject.setAttribute("msg", msg);
		logger.info("invoice zip and download completed by:"+userObj.getUserName()+userObj.getUserId());
		return SUCCESS;
	}
/*public String clientInvoiceList(){
				
		try {
			int clientId = Integer.parseInt(requestObject.getParameter("clientId"));	
			invoiceList = invoiceService.clientInvoiceListByclientId(clientId);
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
*/
public String changeInvoiceStatus(){
	try {
		int invId = Integer.parseInt(requestObject.getParameter("invId"));
		invoiceService.changeInvoiceStatus(invId);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return SUCCESS;
}
	
public String saveInvoicePaidAmount(){
	try {
		int userId = ((User) requestObject.getSession().getAttribute(Constants.USER_OBJECT)).getUserId();
		int invId = Integer.parseInt(requestObject.getParameter("invId"));
		Float paidAmount = Float.parseFloat(requestObject.getParameter("paidAmount"));
		invoiceService.saveInvoicePaidAmount(invId,paidAmount,userId);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return SUCCESS;
}	



	@Override
	public Invoice getModel() {
		return setInvoiceObj(new Invoice());
	}

	public Invoice getInvoiceObj() {
		return invoiceObj;
	}

	public Invoice setInvoiceObj(Invoice invoiceObj) {
		this.invoiceObj = invoiceObj;
		return invoiceObj;
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

	public String getActParent2() {
		return actParent2;
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
	
	public void setActParent2(String actParent2) {
		this.actParent2 = actParent2;
	}

	public Jobs getJobObj() {
		return jobObj;
	}

	public void setJobObj(Jobs jobObj) {
		this.jobObj = jobObj;
	}



	public InvoiceService getInvoiceService() {
		return invoiceService;
	}



	public void setInvoiceService(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}



	public CommonMasterData getCommonMasterData() {
		return commonMasterData;
	}



	public void setCommonMasterData(CommonMasterData commonMasterData) {
		this.commonMasterData = commonMasterData;
	}



	public List<Clients> getClientList() {
		return clientList;
	}



	public void setClientList(List<Clients> clientList) {
		this.clientList = clientList;
	}



	public List<Invoice> getInvoiceList() {
		return invoiceList;
	}



	public void setInvoiceList(List<Invoice> invoiceList) {
		this.invoiceList = invoiceList;
	}



	@Override
	public String toString() {
		return "InvoiceAction";
	}


	public Map<String, List<Invoice>> getClientInvoiceMap() {
		return clientInvoiceMap;
	}


	public void setClientInvoiceMap(Map<String, List<Invoice>> clientInvoiceMap) {
		this.clientInvoiceMap = clientInvoiceMap;
	}

	public static void main(String[] arg){
		/*GenerateClientInvoice g = new GenerateClientInvoice();
		GenerateClientInvoice.testIt();
				
		GenerateClientInvoice.generateAllInvoices("0");
		*/
		System.out.println("reached here");
		Zipping.zip();
	}


	public JobService getJobService() {
		return jobService;
	}


	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}


	public List<JobSelectedServices> getJobServiceList() {
		return jobServiceList;
	}


	public Jobs getJobsObj() {
		return jobsObj;
	}


	public void setJobServiceList(List<JobSelectedServices> jobServiceList) {
		this.jobServiceList = jobServiceList;
	}


	public void setJobsObj(Jobs jobsObj) {
		this.jobsObj = jobsObj;
	}
	

}
