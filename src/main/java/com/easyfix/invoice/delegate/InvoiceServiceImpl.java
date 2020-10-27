package com.easyfix.invoice.delegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.codehaus.jettison.json.JSONArray;

import com.easyfix.Jobs.delegate.JobService;
import com.easyfix.Jobs.model.JobSelectedServices;
import com.easyfix.Jobs.model.Jobs;
import com.easyfix.invoice.dao.InvoiceDao;
import com.easyfix.invoice.model.Invoice;
import com.easyfix.util.RestClient;
import com.easyfix.util.UtilityFunctions;
import com.fasterxml.jackson.databind.ObjectMapper;

public class InvoiceServiceImpl implements InvoiceService {
	
	private InvoiceDao invoiceDao;

	private InvoiceDao invoiceDaoHibernate;
	private JobService jobService;
	@Override
	public List<Invoice> clientInvoiceList() throws Exception {
		//List<Invoice> invoiceList = invoiceDao.clientInvoiceList();		
		
		return invoiceDao.clientInvoiceList();
	}

	
	public Map<String, List<Invoice>> filterInvoiceListByClient(List<Invoice> invoiceList) throws Exception  {
		Map<String, List<Invoice>> clientInvoiceMap = new HashMap<String, List<Invoice>>();
		try {
			 for(Invoice inv : invoiceList){
				 
				 String cName = inv.getClient().getClientName();
				 
				 if(clientInvoiceMap.containsKey(cName)){
					 List<Invoice> existingInvoiceList = (List<Invoice>) clientInvoiceMap.get(cName);
					 existingInvoiceList.add(inv);
					 clientInvoiceMap.put(cName, existingInvoiceList);
				 }
				 else {
					 List<Invoice> invList = new ArrayList<Invoice>();
					 invList.add(inv);
					 clientInvoiceMap.put(cName, invList);
				 }				 
			 }
			 System.out.println("clientInvoiceMap:"+clientInvoiceMap);
			 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return clientInvoiceMap;
	}


	@Override
	public List<Invoice> clientInvoiceListByclientId(int clientId) throws Exception {
		return invoiceDao.clientInvoiceListByclientId(clientId);
	}
	
	
	public InvoiceDao getInvoiceDao() {
		return invoiceDao;
	}

	public void setInvoiceDao(InvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}


	@Override
	public void changeInvoiceStatus(int invId) throws Exception {
		invoiceDao.changeInvoiceStatus(invId);
		
	}
	
	@Override
	public void saveInvoicePaidAmount(int invId, float paidAmount, int paidBy) throws Exception {
		invoiceDao.saveInvoicePaidAmount(invId,paidAmount,paidBy);
		
	}

	@Override
	
	public void modifyJobServiceFromInvoice(Invoice inv) throws Exception{
		System.out.println(inv.getModificationAmount());
		Jobs j = jobService.getJobDetails(inv.getJobId());
		j.setFkCheckOutBy(inv.getInvoiceChangedBy());
		List<JobSelectedServices> list = jobService.getJobServiceList(inv.getJobId(), 1);
		JobSelectedServices addedService = null;
		int jobServiceId = inv.getJobServiceId();
		List<String> serviceList = new ArrayList<>();
		List<Integer> jobServiceList = new ArrayList<>();
		for(JobSelectedServices service : list){
			serviceList.add(service.getServiceId()+"");
			jobServiceList.add(service.getJobServiceId());
			if(service.getJobServiceId()==jobServiceId)
				addedService=service;
		}
		
		serviceList.add(addedService.getServiceId()+"");
		String clientServiceIds = j.getClientServiceIds()+","+addedService.getServiceId();	
		jobService.updateJobServices(inv.getJobId(), clientServiceIds, serviceList);
		List<JobSelectedServices> newList = jobService.getJobServiceList(inv.getJobId(), 1);
		for(JobSelectedServices s: newList){
			if(!(jobServiceList.contains(s.getJobServiceId()))){
				addedService = s;
			}
		}
		addedService.setTotalCharge(inv.getModificationAmount()+"");
		addedService.setClientCharge(inv.getModificationCL()+"");
		addedService.setEasyfixCharge(inv.getModificationEF()+"");
		addedService.setEasyfixerCharge(inv.getModificationEFR()+"");
		addedService.setIsModifiedFromInvoice(1);
		list.add(addedService);
		jobService.modifyJobFromInvoice(j, list);
	}

	public InvoiceDao getInvoiceDaoHibernate() {
		return invoiceDaoHibernate;
	}


	public void setInvoiceDaoHibernate(InvoiceDao invoiceDaoHibernate) {
		this.invoiceDaoHibernate = invoiceDaoHibernate;
	}


	@Override
	public List<Invoice> clientInvoiceListHibernate() throws Exception {
		return invoiceDaoHibernate.clientInvoiceListByclientId(1);
	}


	@Override
	public Invoice getInvoiceDetailById(Invoice inv) throws Exception {
		Invoice invoiceFromDb = invoiceDaoHibernate.getInvoiceDetailById(inv);
		System.out.println(invoiceFromDb.getInvoicedJobIds());
		List<Jobs> jobInvoiceList = new ArrayList<Jobs>();
		
		if(invoiceFromDb.getInvoicedJobIds()==null)
			return null;
		
		String[] ids = invoiceFromDb.getInvoicedJobIds().split(",");
		System.out.println(ids.length);
		for(int i =0; i<ids.length;i++){
			
			try{
				int id = Integer.valueOf(ids[i]);
			
			Jobs j = jobService.getJobDetails(id);
			jobInvoiceList.add(j);
		}
			catch(Exception e){
				e.printStackTrace();
			}
			}
		invoiceFromDb.setInvociedJobs(jobInvoiceList);
 		return invoiceFromDb;		
		
	}


	public JobService getJobService() {
		return jobService;
	}


	public void setJobService(JobService jobService) {
		this.jobService = jobService;
	}
	
	

/*
	@Override
	public List<Invoice> clientInvoiceList() throws Exception {
		List<Invoice> clientInvoiceList = new ArrayList<Invoice>();
		try {
			WebTarget target = new RestClient().apiResponse();
			WebTarget invoiceTarget = target.path("clientInvoice");
			
			if(invoiceObj.getInvClientId() > 0){
				invoiceTarget = invoiceTarget.queryParam("clientId", invoiceObj.getInvClientId());
			}
			if(invoiceObj.getBillingStartDate() != ""){
				invoiceTarget = invoiceTarget.queryParam("startDate", invoiceObj.getBillingStartDate());
			}
			if(invoiceObj.getBillingEndDate() != ""){
				invoiceTarget = invoiceTarget.queryParam("endDate", invoiceObj.getBillingEndDate());
			}
			
			String jsonResult = invoiceTarget.request().accept(MediaType.APPLICATION_JSON).get(String.class);
			
			JSONArray jArray = new JSONArray(jsonResult);
			ObjectMapper mapper = new ObjectMapper();
			
			for(int i=0; i<jArray.length(); i++){
				Invoice invObj = mapper.readValue(jArray.getString(i), Invoice.class);
				
				if(invObj.getBillingStartDate() != null){
					String fdate = UtilityFunctions.convertTimeStampToDateFormate(Long.parseLong(invObj.getBillingStartDate()), "dd-MM-yyyy");
					invObj.setBillingStartDate(fdate);
				}
				if(invObj.getBillingEndDate() != null){
					String tdate = UtilityFunctions.convertTimeStampToDateFormate(Long.parseLong(invObj.getBillingEndDate()), "dd-MM-yyyy");
					invObj.setBillingEndDate(tdate);
				}
				clientInvoiceList.add(invObj);
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clientInvoiceList;
	}
*/
}
