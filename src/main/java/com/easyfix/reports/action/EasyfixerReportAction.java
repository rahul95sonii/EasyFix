package com.easyfix.reports.action;

import java.io.FileInputStream;
import java.util.List;

import com.easyfix.clients.delegate.ClientService;
import com.easyfix.clients.model.Clients;
import com.easyfix.reports.delegate.EasyfixerReportService;
import com.easyfix.reports.model.EasyfixerReport;
import com.easyfix.util.CommonAbstractAction;
import com.opensymphony.xwork2.ModelDriven;

public class EasyfixerReportAction extends CommonAbstractAction implements ModelDriven<EasyfixerReport> {
	EasyfixerReport easyfixerReportObj;
private static final long serialVersionUID = 1L;
	
	private String actMenu;
	private String actParent;
	private String title;
	private EasyfixerReportService easyfixerReportService;
	private FileInputStream fileInputStream;
	private String fileName; 
	private ClientService clientService;
	private List<Clients> clientList;
	
	
	
	@Override
	public EasyfixerReport getModel() {
		return easyfixerReportObj =  new EasyfixerReport();  
		
	}
	public String manageEfrReport() throws Exception{
		String acttitle= "Easyfix : Easyfixer Report";
		setActMenu("Easyfixer Report");
		setActParent("Report");
		setTitle(acttitle);
		try{
		clientList = clientService.getClientList(2);//2 for all client
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;

	}
	public String downloadEasyfixerReport() throws Exception{
		fileName = "efrReport.xlsx";
		
		try{
			int flag = easyfixerReportObj.getFlag();
			
				
			int client = easyfixerReportObj.getClientId();
			String range = easyfixerReportObj.getDateRange();
			String[] temp = range.split("--");
			String start = temp[0].trim();
			String end = temp[1].trim();
			String startDate = easyfixerReportService.convertStringToDate(start);
			String endDate = easyfixerReportService.convertStringToDate(end);

		fileInputStream= easyfixerReportService.downloadEfrReport(startDate,endDate,flag,client);
		//flag =100: all, 1:active, 0: inactive
		//client 0 = all,
	//	fileInputStream = easyfixerReportService.downloadEfrReport("2015-08-01","2015-11-27",100,0);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
		
	}
	public EasyfixerReport getEasyfixerReportObj() {
		return easyfixerReportObj;
	}
	public void setEasyfixerReportObj(EasyfixerReport easyfixerReportObj) {
		this.easyfixerReportObj = easyfixerReportObj;
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
		public FileInputStream getFileInputStream() {
		return fileInputStream;
	}
	public void setFileInputStream(FileInputStream fileInputStream) {
		this.fileInputStream = fileInputStream;
	}
	public EasyfixerReportService getEasyfixerReportService() {
		return easyfixerReportService;
	}
	public void setEasyfixerReportService(
			EasyfixerReportService easyfixerReportService) {
		this.easyfixerReportService = easyfixerReportService;
	}
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public ClientService getClientService() {
		return clientService;
	}
	public void setClientService(ClientService clientService) {
		this.clientService = clientService;
	}
	public List<Clients> getClientList() {
		return clientList;
	}
	public void setClientList(List<Clients> clientList) {
		this.clientList = clientList;
	}
	@Override
	public String toString() {
		return "EasyfixerReportAction";
	}
	

}
