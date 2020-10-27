package com.easyfix.util.triggers;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import com.easyfix.util.PropertyReader;
import com.easyfix.util.triggers.invoice.GenerateInvoiceDetails;
import com.easyfix.util.triggers.invoice.GenerateInvoiceFiles;
import com.easyfix.util.triggers.invoice.InvoiceUtils;
import com.easyfix.util.triggers.model.ClientInvoiceBean;


public class GenerateClientInvoice
{
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

	public GenerateClientInvoice(){
		
	}
	public static void main(String[] args)
	{
		try
		{
			File file = new File(args[0] + "/log4j2.xml");
			LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
			// System.out.println(file.toURI().toString());
			context.setConfigLocation(file.toURI());
			PropertyReader props = PropertyReader.getInstance(args[0]);
//			GenerateClientInvoiceService.getInstance().clientInvoiceService(props, args[1]);
			
			List<ClientInvoiceBean> clientList= InvoiceUtils.fetchAllClientDetails(props, args[1]);
			System.out.println("fetched client list successfully");
			System.out.println("fetched client list successfully"+clientList.size());
			InvoiceUtils.createRootDirectory(props);
			for(int i=0;i<clientList.size();i++){
				String clientName= clientList.get(i).getClientName();
				clientName= clientName.replaceAll(" ", "_");
				System.out.println("startin for : " +clientName);
				int flag = Integer.parseInt(args[2]);
				ClientInvoiceBean clientInvoiceBean= GenerateInvoiceDetails.genInvoice(0, clientList.get(i), props, args[1],flag);
				//System.out.println("start date1111111111: " +clientInvoiceBean.getBillingFromDate());
				
				String fileName= clientName+"_"+clientInvoiceBean.getBillingFromDate()+"_"+clientInvoiceBean.getBillingToDate();
				GenerateInvoiceFiles.generateFiles(0, clientList.get(i), props, args[1], fileName, clientInvoiceBean.getBillingFromDate(), clientInvoiceBean.getBillingToDate());
				System.out.println("Ending for : " +clientName);
				
				
			}
			
			
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
		
			e.printStackTrace();
		}

	}
	
	public static void testIt(){
		System.out.println("reached here");
	}
	
	public static void generateAllInvoices(String Myflag){
		try
		{
			File file = new File( "/opt/triggers/props/" + "/log4j2.xml");
			LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
			// System.out.println(file.toURI().toString());
			context.setConfigLocation(file.toURI());
			PropertyReader props =PropertyReader.getInstance("D:/opt/triggers/props/");
//			GenerateClientInvoiceService.getInstance().clientInvoiceService(props, args[1]);
			String dbPass = props.getValue("db.PASSWORD");
			List<ClientInvoiceBean> clientList= InvoiceUtils.fetchAllClientDetails(props, dbPass);
			System.out.println("fetched client list successfully");
			System.out.println("fetched client list successfully"+clientList.size());
			InvoiceUtils.createRootDirectory(props);
			for(int i=0;i<clientList.size();i++){
				
				String clientName= clientList.get(i).getClientName();
				clientName= clientName.replaceAll(" ", "_");
				System.out.println("startin for : " +clientName);
				int flag = Integer.parseInt(Myflag);
				ClientInvoiceBean clientInvoiceBean= GenerateInvoiceDetails.genInvoice(0, clientList.get(i), props, dbPass,flag);
				//System.out.println("start date1111111111: " +clientInvoiceBean.getBillingFromDate());
				
				String fileName= clientName+"_"+clientInvoiceBean.getBillingFromDate()+"_"+clientInvoiceBean.getBillingToDate();
				GenerateInvoiceFiles.generateFiles(0, clientList.get(i), props, dbPass, fileName, clientInvoiceBean.getBillingFromDate(), clientInvoiceBean.getBillingToDate());
				
				System.out.println("Ending for : " +clientName);
			}
			
			
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
		
			e.printStackTrace();
		}
	}
	
	public static void generateInvoiceByClient(String Myflag, int clientId){
		try
		{
			File file = new File( "/opt/triggers/props/" + "/log4j2.xml");
			LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
			// System.out.println(file.toURI().toString());
			context.setConfigLocation(file.toURI());
			PropertyReader props =PropertyReader.getInstance("D:/opt/triggers/props/");
//			GenerateClientInvoiceService.getInstance().clientInvoiceService(props, args[1]);
			String dbPass = props.getValue("db.PASSWORD");
			List<ClientInvoiceBean> clientList= InvoiceUtils.fetchAllClientDetails(props, dbPass);
			System.out.println("fetched client list successfully");
			System.out.println("fetched client list successfully"+clientList.size());
			InvoiceUtils.createRootDirectory(props);
			for(int i=0;i<clientList.size();i++){
				if(clientList.get(i).getClientId()==clientId){
				String clientName= clientList.get(i).getClientName();
				clientName= clientName.replaceAll(" ", "_");
				System.out.println("startin for : " +clientName);
				int flag = Integer.parseInt(Myflag);
				ClientInvoiceBean clientInvoiceBean= GenerateInvoiceDetails.genInvoice(0, clientList.get(i), props, dbPass,flag);
				//System.out.println("start date1111111111: " +clientInvoiceBean.getBillingFromDate());
				
				String fileName= clientName+"_"+clientInvoiceBean.getBillingFromDate()+"_"+clientInvoiceBean.getBillingToDate();
				GenerateInvoiceFiles.generateFiles(0, clientList.get(i), props, dbPass, fileName, clientInvoiceBean.getBillingFromDate(), clientInvoiceBean.getBillingToDate());
				
				System.out.println("Ending for : " +clientName);
				}
				
			}
			
			
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
		
			e.printStackTrace();
		}
	}

}
