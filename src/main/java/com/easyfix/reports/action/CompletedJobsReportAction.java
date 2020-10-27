package com.easyfix.reports.action;

import java.io.FileInputStream;
import java.util.Date;
import java.util.List;

import com.easyfix.clients.delegate.ClientService;
import com.easyfix.clients.model.Clients;
import com.easyfix.reports.delegate.CompletedJobsReportService;
import com.easyfix.reports.model.CompletedJobsReport;
import com.easyfix.util.CommonAbstractAction;
import com.easyfix.util.UtilityFunctions;
import com.opensymphony.xwork2.ModelDriven;

public class CompletedJobsReportAction extends CommonAbstractAction implements ModelDriven<CompletedJobsReport> {
private static final long serialVersionUID = 1L;
	
	private String actMenu;
	private String actParent;
	private String title;
	private ClientService clientService;
	
	CompletedJobsReport completedJobsReportObj;
	CompletedJobsReportService completedJobsReportService;
	List<CompletedJobsReport> completedJobsReport;
	
	List<Clients> clientList;
	FileInputStream fileInputStream;
	   private String fileName; 
		
			public String getFileName() {
				return fileName;
			}

			public void setFileName(String fileName) {
				this.fileName = fileName;
			}
	public FileInputStream getFileInputStream() {
		return fileInputStream;
	}

	public void setFileInputStream(FileInputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}

	@Override
	public CompletedJobsReport getModel() {
		completedJobsReportObj= new CompletedJobsReport();
		return completedJobsReportObj;
	}
	
	public String manageCompletedJobsReport() throws Exception{
		String acttitle= "Easyfix : Completed Job Report";
		setActMenu("Complete Jobs Report");
		setActParent("Report");
		setTitle(acttitle);
		
		clientList = clientService.getClientList(2); // 2 for all client
		
		return SUCCESS;

	}
	public String downloadCompletedJobReport() throws Exception{
		
		fileName = "report.xlsx";
		try{
			int flag = completedJobsReportObj.getFlag();
			
				
			int client = completedJobsReportObj.getClientId();
			String range = completedJobsReportObj.getDateRange();
			String[] temp = range.split("--");
			String start = temp[0].trim();
			String end = temp[1].trim();

			String startDate = completedJobsReportService.convertStringToDate(start);
			String endDate = completedJobsReportService.convertStringToDate(end);
			Date d = UtilityFunctions.convertStringToDateInDate(endDate, "yyyy-MM-dd");
			endDate = UtilityFunctions.addSubstractDaysInDate(d, 1, "yyyy-MM-dd");
			//falg =100 all, client =0 all
		//	fileInputStream= completedJobsReportService.downloadCompletedJobReport("2015-07-01","2015-12-10",0,1);
		if(flag!=50){
			fileInputStream= completedJobsReportService.downloadCompletedJobReport(startDate,endDate,flag,client);
		}
		else if(flag==50){
			fileInputStream = completedJobsReportService.downloadRescheduleJobReport(startDate, endDate, flag, client);
		}
		}
		catch(Exception e){
			e.printStackTrace();
			/*
			 * <option value="100">All</option>
													<option value="0">New Created</option>
													<option value="1">Scheduled</option>
													<option value="2">CheckedIn</option>
													<option value="3">CheckedOut(Revenue)</option>
													<option value="5">Completed</option>
													<option value="6">Canceled</option>
													<option value="7">Enquiry</option>
													<option value="9">Call Later</option>
													<option value="50">Re Schedule</option>
			 */
		}
		return SUCCESS;
	}
	
	


	public CompletedJobsReportService getCompletedJobsReportService() {
		return completedJobsReportService;
	}


	public void setCompletedJobsReportService(
			CompletedJobsReportService completedJobsReportService) {
		this.completedJobsReportService = completedJobsReportService;
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
	public List<CompletedJobsReport> getCompletedJobsReport() {
		return completedJobsReport;
	}
	public void setCompletedJobsReport(List<CompletedJobsReport> completedJobsReport) {
		this.completedJobsReport = completedJobsReport;
	}
	@Override // for RestrictAccessToUnauthorizedActionInterceptor
	public String toString(){
		return "CompletedJobsReportAction";
	}

	public List<Clients> getClientList() {
		return clientList;
	}

	public void setClientList(List<Clients> clientList) {
		this.clientList = clientList;
	}

	public ClientService getClientService() {
		return clientService;
	}

	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}

	public CompletedJobsReport getCompletedJobsReportObj() {
		return completedJobsReportObj;
	}

	public void setCompletedJobsReportObj(CompletedJobsReport completedJobsReportObj) {
		this.completedJobsReportObj = completedJobsReportObj;
	}

}
