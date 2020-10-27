package com.easyfix.util.triggers.invoice;

import java.io.File;
import java.text.DateFormat;
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


public class GenerateInvoiceDetails
{
	private static String seperator = System.getProperty("file.separator");
	private static final Logger logger = LogManager.getLogger(GenerateInvoiceDetails.class);

	public static ClientInvoiceBean genInvoice(int clientId, ClientInvoiceBean c, PropertyReader props, String password, int flag) throws Exception
	{
		if (c == null)
		{
			System.out.println("Running on basis of client id");
			c = InvoiceUtils.fetchClientDetails(clientId, props, password);
		}
		String filePath = props.getValue("UPLOAD.PATH");
		Date curdate = new Date();
		String curMonth = new SimpleDateFormat("MMM_yyyy").format(curdate);
		String billCycle = c.getBillingCycle();
		ClientInvoiceBean clientInvoiceBean=null;
		int tempClientId = c.getClientId();
		int billRaise = c.getBillingRaised();
		String cName = c.getClientName().replaceAll(" ", "_").toLowerCase() + tempClientId;
		System.out.println("Billing cycle for client name: " +billCycle +" bill raise: " +billRaise);
		if (billCycle != null && !billCycle.isEmpty() && billRaise == 1)
		{
			
			File cDir = new File(filePath + seperator + curMonth + seperator + cName );
			System.out.println("c dir: " +cDir.toString());
			if (!cDir.exists())
			{
				try
				{
					cDir.mkdir();
					cDir.setReadable(true);
					cDir.setWritable(true);
					// seperator added after filePath
					File invoiceDir = new File(filePath + curMonth + seperator + cName + seperator + "invoice");
					File masterDir = new File(filePath  + curMonth + seperator + cName + seperator + "mastersheet");
					System.out.println("Invoice dir: " +invoiceDir);
					System.out.println("Master dir: " +masterDir);
					if (!invoiceDir.exists())
					{
						System.out.println("Doesnt exist: creating");
						invoiceDir.mkdir();
						invoiceDir.setReadable(true);
						invoiceDir.setWritable(true);
					}
					if (!masterDir.exists())
					{
						System.out.println("Doesnt exist: creating");
						masterDir.mkdir();
						masterDir.setReadable(true);
						masterDir.setWritable(true);
					}
					
					File invoiceDir1 = new File(filePath + curMonth + seperator + cName + seperator + "invoice");
					File masterDir1 = new File(filePath + curMonth + seperator + cName + seperator + "mastersheet");

					if (!invoiceDir1.exists())
					{
						invoiceDir1.mkdir();
						invoiceDir1.setReadable(true);
						invoiceDir1.setWritable(true);
					}
					if (!masterDir1.exists())
					{
						masterDir1.mkdir();
						masterDir1.setReadable(true);
						masterDir1.setWritable(true);
					}
					System.out.println("Invoice folder created for client Id : " + tempClientId);
					logger.info("Invoice folder created for client Id : " + tempClientId);

				}
				catch (Exception e)
				{
					e.printStackTrace();
					logger.catching(e);
				}

			}

			logger.info("Invoice and excel generation start for client Id : " + tempClientId);

			String[] cycleArray = billCycle.split(",");

			DateFormat dateFormat = new SimpleDateFormat("dd");
			// get current date time with Date()
			Date date = new Date();
			// System.out.println(dateFormat.format(date));

			int curDate = Integer.parseInt(dateFormat.format(date));

			for (int i = 0; i < cycleArray.length; i++)
			{
				int billingCycle = 0;

				billingCycle = Integer.parseInt(cycleArray[i].trim());
				if (billingCycle == 40)
					billingCycle = 0;

				//logger.info("Invoice generation start for client : " + tempClientId + " day : " + billingCycle);

				clientInvoiceBean = InvoiceUtils.generateInvoiceData(tempClientId, props, password, billingCycle, flag, c.getBillingFromDate());
//				if (curDate - billingCycle == 1)
//				{
//					System.out.println("here1");
//					// Insert new Invoice
//					//int flag = 0; // 0 for new insert, 1 for update
//					clientInvoiceBean = InvoiceUtils.generateInvoiceData(tempClientId, props, password, billingCycle, flag, c.getBillingFromDate());
//				}
//				else
//				{
//					System.out.println("here2" + tempClientId);
//					//int flag = 1; // 0 for new insert, 1 for update
//
//					clientInvoiceBean = InvoiceUtils.generateInvoiceData(tempClientId, props, password, billingCycle, flag, c.getBillingFromDate());
//				}
				System.out.println("Invoice data generation completed for client : " + tempClientId + " day : " + billingCycle);
				
				logger.info("Invoice data generation completed for client : " + tempClientId + " day : " + billingCycle);
			}

		}
		return clientInvoiceBean;
	}

}
