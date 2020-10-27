package com.easyfix.util.triggers.invoice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easyfix.util.PropertyReader;
import com.easyfix.util.triggers.model.ClientInvoiceBean;

;

public class GenerateInvoiceFiles
{
	private static final Logger logger = LogManager.getLogger(GenerateInvoiceDetails.class);
	private static String seperator = System.getProperty("file.separator");

	public static void generateFiles(int clientId, ClientInvoiceBean c,  PropertyReader props, String password, String fName, String billingStartDate, String billingEndDate)throws Exception
	{
		
		/* Generate Excel */
		if(c==null){
			c= InvoiceUtils.fetchClientDetails(clientId, props, password);
		}
		System.out.println("reached here ............");
		String cName = c.getClientName().replaceAll(" ", "_").toLowerCase() + c.getClientId();
		List<ClientInvoiceBean> jobList = InvoiceUtils.generateExcelData(c.getClientId(), props, password, billingStartDate, billingEndDate);
		System.out.println("getting job list from start date: " +billingStartDate +" end date: " +billingEndDate+ " and size is " + jobList.size() +" for file: " +fName);
		logger.info("Job list size for Invoice excel : " + jobList.size());
		String filePath = props.getValue("UPLOAD.PATH");
		Date curdate = new Date();
		String curMonth = new SimpleDateFormat("MMM_yyyy").format(curdate);
		if (jobList.size() > 0)
		{
			Map<String, List<ClientInvoiceBean>> serviceTypeJobMap = filterJobListByServiceType(jobList);

			logger.info("Start Excel generation for client : " + clientId + " file name : " + fName);

			String type = "application/octet-stream";
			String masterDirName = filePath + curMonth + seperator + cName + seperator + "mastersheet" + seperator + fName;
			System.out.println("masterDirName:"+masterDirName);
			String invoiceDirName = filePath + curMonth + seperator + cName + seperator + "invoice" + seperator + fName;
			System.out.println("invoiceDirName:"+invoiceDirName);
			String excelFileNameforDb= curMonth + seperator + cName + seperator + "mastersheet" + seperator + fName+".xlsx";
			String pdfFileNameforDb = curMonth + seperator + cName + seperator + "invoice" + seperator + fName+".pdf";
			ExcelGenerator.getInstance().createInvoiceFiles(masterDirName, invoiceDirName, serviceTypeJobMap, c.getBillingName(),
					c.getBilingAddress(), billingStartDate, billingEndDate, props);
			//logger.info("Competed Excel generation for client : " + clientId + " file name : " + fileName);
			InvoiceUtils.updateInvoiceFiles(c.getClientId(),props,password,excelFileNameforDb,pdfFileNameforDb);
		}
	}

	private static Map<String, List<ClientInvoiceBean>> filterJobListByServiceType(List<ClientInvoiceBean> jobListForExcel)
			throws Exception
	{
		Map<String, List<ClientInvoiceBean>> jobMap = new HashMap<String, List<ClientInvoiceBean>>();

		try
		{
			for (ClientInvoiceBean j : jobListForExcel)
			{
				String serviceTypeName = j.getServiceTypeName();

				if (jobMap.containsKey(serviceTypeName))
				{
					List<ClientInvoiceBean> existingJobList = (List<ClientInvoiceBean>) jobMap.get(serviceTypeName);
					existingJobList.add(j);
					jobMap.put(serviceTypeName, existingJobList);
				}
				else
				{
					List<ClientInvoiceBean> jobs = new ArrayList<ClientInvoiceBean>();
					jobs.add(j);
					jobMap.put(serviceTypeName, jobs);
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return jobMap;
	}

}
