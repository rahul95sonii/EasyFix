package com.easyfix.util.triggers.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.easyfix.util.PropertyReader;
import com.easyfix.util.triggers.model.Email;
import com.easyfix.util.triggers.model.Jobs;
import com.easyfix.util.triggers.model.Recipients;
import com.easyfix.util.triggers.model.Tracking;
import com.easyfix.util.triggers.utils.ConnectionProvider;
import com.easyfix.util.triggers.utils.DBUtils;
import com.easyfix.util.triggers.utils.DateUtils;
import com.easyfix.util.triggers.utils.MailSender;



public class EmailReportingService
{

	private static Connection conn = null;
	public static AtomicReference<List<Email>> emailcache = new AtomicReference<List<Email>>();
	public static AtomicReference<Long> emailTime = new AtomicReference<Long>(0l);
	public static AtomicReference<Long> receipientTime = new AtomicReference<Long>(0l);
	public static AtomicReference<List<Recipients>> recipientscache = new AtomicReference<List<Recipients>>();
	public static Map<Integer, List<Recipients>> recipientsMap;
	static Logger log = LogManager.getLogger(EmailReportingService.class);

	public static void sendEmail(PropertyReader props, String password)
	{
		conn = ConnectionProvider.getConnection(props, password);
		List<Email> emailList = getEmailDetails();
		log.info("Total emails to be sent: " + emailList.size());
		if (emailList.size() == 0)
		{
			log.info("No emails being sent");
			return;
		}
		Map<Integer, List<Recipients>> recipientMap = getRecipientsDetails();
		String name = "";
		String contact = "";
		
		try
		{
			for (int i = 0; i < emailList.size(); i++)
			{
				int clientId = emailList.get(i).getClientId();
				log.info("Seding for client id:" + clientId);
				Blob emailText = emailList.get(i).getEmail_text();
				String text = new String(emailText.getBytes(1l, (int) emailText.length()));
				String clientDisplayName = emailList.get(i).getDisplayName();
				String subject = emailList.get(i).getSubject();
				String filePath = "";
				try
				{
					File f = new File(props.getValue("reporting.file.location") + DateUtils.getTodaysDate());
					if (!f.exists())
					{
						f.mkdir();
					}
					Calendar cal = Calendar.getInstance();
				if(cal.get(Calendar.DATE)==1){
					String a=DateUtils.getFirstDayOfPreviousMonth();
					String b= DateUtils.getLastDayOfPreviousMonth();
					String firstDateOfPreviousMonth = a + " 00:00:01";
					String lastDateOfPreviousMonth = b + " 00:00:01";
					Tracking MonthlyTracking = DBUtils.getTrackingData(conn, clientId, firstDateOfPreviousMonth, lastDateOfPreviousMonth);
					text = text.replaceFirst(" summary of ##client_display_name##", " summary of " + clientDisplayName);
					//text = text.replaceFirst("between ##yesterday## and ##today##", "on " + DateUtils.getYesterdayDate());
					text = text.replaceFirst("##yesterday##" ,a);
					text = text.replaceFirst("##today##",b);
					text = text.replaceFirst("New Jobs : ##created##", "New Jobs : " + String.valueOf(MonthlyTracking.getCountJobCreated()));
					text = text.replaceFirst("Completed Jobs : ##created##",
							"Completed Jobs : " + String.valueOf(MonthlyTracking.getCountJobCheckedout()));
					text = text.replaceFirst("Scheduled Jobs : ##created##",
							"Scheduled Jobs : " + String.valueOf(MonthlyTracking.getCountJobScheduled()));
					text = text.replaceFirst("Requested Jobs : ##created##",
							"Requested Jobs : " + String.valueOf(MonthlyTracking.getCountJobRequested()));
					List<Jobs> jobListMonthlyTracking = getJobList(clientId, firstDateOfPreviousMonth, lastDateOfPreviousMonth);
					if (jobListMonthlyTracking.size() > 0)
					{
						log.info("Total monthly records returned for: " + clientId + " or " + clientDisplayName + " - " + jobListMonthlyTracking.size());
						System.out.println("Total monthly records returned for: " + clientId + " or " + clientDisplayName + " - " + jobListMonthlyTracking.size());
						filePath = createExcel(f, jobListMonthlyTracking, clientId,"monthly");
						List<Recipients> recipients = recipientMap.get(clientId);

						if(recipients!=null){
						for (int j = 0; j < recipients.size(); j++)
						{
							contact = recipients.get(j).getEmail();

							name = recipients.get(j).getContactName();

							String[] cc = recipients.get(j).getEmaillCCList();
							String finalText = text;
							finalText = text.replaceFirst("Dear ##contactname##", "Dear " + name);
							System.out.println("Sending: " + finalText + " to : " + name + " at " + contact);
							log.info("Sending: " + finalText + " to : " + name + " at " + contact);
							subject= "EASYFix - Monthly Report";
							MailSender.email(contact, finalText, subject, props, filePath,cc);
							// SmsSender.sendSms(finalText, contact);
						}
						}

					}
					else
					{
						log.info("No monthly records returned for: " + clientId + " or " + clientDisplayName);
						System.out.println("No monthly records returned for: " + clientId + " or " + clientDisplayName);
						
					}
				}
					
						
					String  start= DateUtils.getYesterdayDate() ;
					String startDate = start+ " 00:00:01";
					String end = DateUtils.getTodaysDate(); 
					String endDate = end+" 00:00:01";
					String firstDayOfMon = DateUtils.getFirstDayOfCurrentMonth();
					String firstDayTimeOfMonth = firstDayOfMon+" 00:00:01";
					Tracking MonthlyTrackingData = DBUtils.getTrackingData(conn, clientId, firstDayTimeOfMonth, endDate);
					//Tracking tracking = DBUtils.getTrackingData(conn, clientId, startDate, endDate);
					text = new String(emailText.getBytes(1l, (int) emailText.length()));
					text = text.replaceFirst(" summary of ##client_display_name##", " summary of " + clientDisplayName);
					text = text.replaceFirst("between ##yesterday## and ##today##", "between " + firstDayOfMon+ " and "+start);
					text = text.replaceFirst("New Jobs : ##created##", "New Jobs : " + String.valueOf(MonthlyTrackingData.getCountJobCreated()));
					text = text.replaceFirst("Completed Jobs : ##created##",
							"Completed Jobs : " + String.valueOf(MonthlyTrackingData.getCountJobCheckedout()));
					text = text.replaceFirst("Scheduled Jobs : ##created##",
							"Scheduled Jobs : " + String.valueOf(MonthlyTrackingData.getCountJobScheduled()));
					text = text.replaceFirst("Requested Jobs : ##created##",
							"Requested Jobs : " + String.valueOf(MonthlyTrackingData.getCountJobRequested()));
					List<Jobs> jobList = getJobList(clientId, firstDayTimeOfMonth, endDate);
					if (jobList.size() > 0)
					{
						log.info("Total records returned for: " + clientId + " or " + clientDisplayName + " - " + jobList.size());
						System.out.println("Total records returned for: " + clientId + " or " + clientDisplayName + " - " + jobList.size());
						
						filePath = createExcel(f, jobList, clientId,"MTD");
						List<Recipients> recipients = recipientMap.get(clientId);

						if(recipients!=null){
						for (int j = 0; j < recipients.size(); j++)
						{
							contact = recipients.get(j).getEmail();

							name = recipients.get(j).getContactName();

							String[] cc = recipients.get(j).getEmaillCCList();
							String finalText = text;
							finalText = text.replaceFirst("Dear ##contactname##", "Dear " + name);
							System.out.println("Sending: " + finalText + " to : " + name + " at " + contact);
							log.info("Sending: " + finalText + " to : " + name + " at " + contact);
							subject= emailList.get(i).getSubject();;
							MailSender.email(contact, finalText, subject, props, filePath,cc);
							// SmsSender.sendSms(finalText, contact);
						}
						}

					}
					else
					{
						log.info("No records returned for: " + clientId + " or " + clientDisplayName);
						System.out.println("No records returned for: " + clientId + " or " + clientDisplayName);
						continue;
					}
					
		
					
					
				}
				catch (Exception e)
				{
					StringWriter stack = new StringWriter();
					e.printStackTrace(new PrintWriter(stack));
					log.error("Exception occurred while processing reporting email for :" + clientId + " message : " + stack.toString());
					log.error(e.getMessage());
					continue;
				}
				
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.info("Error occurred :" + e.getMessage());
			
		}
		finally
		{
			if (conn != null)
			{
				try
				{
					conn.close();
				}
				catch (SQLException e)
				{ /* ignored */
				}
			}

		}
	}

	public static List<Recipients> getRecipientsListFromDB()
	{
		List<Recipients> recipientsListFromDB = new ArrayList<Recipients>();
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = "select * from tbl_client_contacts where status =1 and client_type='reporting'";
		try
		{
			
			psmt = conn.prepareStatement(sql);
			//psmt.setString(1, "reporting");
			rs = psmt.executeQuery();
			while (rs.next())
			{
				Recipients r = new Recipients();
				r.setClientId(rs.getInt("client_id"));
				r.setContactName(rs.getString("contact_name"));
				r.setContactNo(rs.getString("contact_no"));
				r.setEmail(rs.getString("contact_email"));
				r.setFrequency(rs.getString("frequency"));
				r.setStatus(rs.getInt("status"));
				r.setEmailCC(rs.getString("email_cc"));
				System.out.println("recipent list loaded from db");
				recipientsListFromDB.add(r);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
			log.error(e.getMessage());
		}
		finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				}
				catch (SQLException e)
				{ /* ignored */
				}
			}
			if (psmt != null)
			{
				try
				{
					psmt.close();
				}
				catch (SQLException e)
				{ /* ignored */
				}
			}
		}

		return recipientsListFromDB;

	}

	public static List<Email> getEmailListFromDB()
	{
		List<Email> emailListFromDB = new ArrayList<Email>();
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = "select * from tbl_email_reporting_meta where status =1";
		try
		{
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next())
			{
				Email email = new Email();
				email.setClientId(rs.getInt("client_id"));
				email.setEmailId(rs.getInt("email_id"));
				email.setEmail_text(rs.getBlob("email_text"));
				email.setStatus(rs.getInt("status"));
				email.setDisplayName(rs.getString("client_display_name"));
				email.setSubject(rs.getString("subject"));
				
				emailListFromDB.add(email);
			}
		}
		catch (SQLException e)
		{
			log.error(e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				}
				catch (SQLException e)
				{ /* ignored */
				}
			}
			if (psmt != null)
			{
				try
				{
					psmt.close();
				}
				catch (SQLException e)
				{ /* ignored */
				}
			}
		}

		return emailListFromDB;

	}

	public static List<Email> getEmailDetails()
	{
		long currentTimeinMilli = System.currentTimeMillis();
		if (emailTime.get() == 0 || (currentTimeinMilli - emailTime.get()) > 5 * 60 * 1000)
		{
			emailcache.set(getEmailListFromDB());
			emailTime.set(currentTimeinMilli);
		}
		return emailcache.get();
	}

	public static void sortRecipients()
	{
		recipientsMap = new HashMap<Integer, List<Recipients>>();
		for (Recipients r : recipientscache.get())
		{
			List<Recipients> localRecipients = null;

			if (recipientsMap.containsKey(r.getClientId()))
				localRecipients = recipientsMap.get(r.getClientId());

			else
				localRecipients = new ArrayList<Recipients>();

			localRecipients.add(r);
			recipientsMap.put(r.getClientId(), localRecipients);
		}

	}

	public static Map<Integer, List<Recipients>> getRecipientsDetails()
	{
		long currentTimeinMilli = System.currentTimeMillis();

		if (receipientTime.get() == 0 || (currentTimeinMilli - receipientTime.get()) > 5 * 60 * 1000)
		{
			recipientscache.set(getRecipientsListFromDB());
			receipientTime.set(currentTimeinMilli);
		}
		sortRecipients();
		return recipientsMap;
	}

	public static List<Jobs> getJobList(int clientId, String startDate, String endDate) throws Exception
	{
		log.info("Finding jobs for : " + clientId + " between " + startDate + " and " + endDate);
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = "SELECT c.`customer_id`,c.`customer_mob_no`,c.`customer_name`,a.`address_id`,a.`address`,a.`pin_code`,a.`city_id`,t.total_charge, "
				+"city.`city_name`,j.* FROM `tbl_job` j LEFT JOIN `tbl_customer` c ON j.`fk_customer_id` = c.`customer_id`" 
				+" LEFT JOIN `tbl_address` a ON j.`fk_address_id`=a.`address_id`"
				+" LEFT JOIN `tbl_city` city ON a.`city_id`=city.`city_id` LEFT JOIN `tbl_job_transaction` t ON t.`fk_job_id`=j.job_id" 
				+" WHERE (j.job_status IN (0,1,2) OR j.created_date_time BETWEEN ? AND ? OR" 
				+" j.requested_date_time BETWEEN ? AND ? OR" 
				+" j.scheduled_date_time BETWEEN ? AND ? OR" 
				+" j.checkin_date_time BETWEEN ? AND ? OR" 
				+" j.checkout_date_time BETWEEN ? AND ? OR" 
				+" j.cancel_date_time BETWEEN ? AND ?) AND j.fk_client_id=?";
		List<Jobs> listOfJobs = new ArrayList<Jobs>();
		try
		{
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, startDate);
			psmt.setString(2, endDate);
			psmt.setString(3, startDate);
			psmt.setString(4, endDate);
			psmt.setString(5, startDate);
			psmt.setString(6, endDate);
			psmt.setString(7, startDate);
			psmt.setString(8, endDate);
			psmt.setString(9, startDate);
			psmt.setString(10, endDate);
			psmt.setString(11, startDate);
			psmt.setString(12, endDate);
			psmt.setInt(13, clientId);
			rs = psmt.executeQuery();
			while (rs.next())
			{
				Jobs job = new Jobs();
				job.setId(rs.getString("job_id"));
				job.setScheduledTimestamp(rs.getTimestamp("scheduled_date_time"));
				job.setCreatedTimestamp(rs.getTimestamp("created_date_time"));
				job.setRequestedTimestamp(rs.getTimestamp("requested_date_time"));
				job.setCompletedTimestamp(rs.getTimestamp("checkout_date_time"));
				job.setCancelledTimestamp(rs.getTimestamp("cancel_date_time"));
				job.setClientReferenceId(rs.getString("client_ref_id"));
				job.setCustomerName(rs.getString("customer_name"));
				job.setCity(rs.getString("city_name"));
				job.setAddress(rs.getString("address"));
				job.setPinCode(rs.getString("pin_code"));
				int jobStatus = rs.getInt("job_status");
				String status = getCurrentStatus(jobStatus);
				job.setCurrentStatus(status);
				job.setTotalCharge(rs.getString("total_charge"));
				job.setEnumDesc(getJobRemark(Integer.valueOf(job.getId()),jobStatus));
				
				listOfJobs.add(job);
			}
		}
		catch (Exception e)
		{
			log.info("Exception occurred in getJobList :" + e.getMessage());
			throw new Exception(e);
		}
		finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				}
				catch (SQLException e)
				{ /* ignored */
				}
			}
			if (psmt != null)
			{
				try
				{
					psmt.close();
				}
				catch (SQLException e)
				{ /* ignored */
				}
			}
		}
		return listOfJobs;

	}

	private static String createExcel(File f, List<Jobs> jobList, int clientId,String reportType) throws Exception
	{
		final String[] titles = { "Job_ID", "ClientReference ID", 
				"Created Date", "Requested Date", 
				"Scheduled Date", "Completion Date",
				"Cancelled Date", "Current Status", 
				"Remark", "Customer Name",
				"City", "Address", 
				"Cost"};
		Workbook wb = new XSSFWorkbook(); // or new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");
		sheet.setColumnWidth(1, 256 * 30);
		sheet.setColumnWidth(2, 256 * 30);
		sheet.setColumnWidth(3, 256 * 30);
		sheet.setColumnWidth(4, 256 * 30);
		sheet.setColumnWidth(5, 256 * 30);
		sheet.setColumnWidth(6, 256 * 30);
		sheet.setColumnWidth(7, 256 * 30);
		sheet.setColumnWidth(8, 256 * 20);
		sheet.setColumnWidth(9, 256 * 20);
		sheet.setColumnWidth(10, 256 * 20);
		sheet.setColumnWidth(11, 356 * 20);
	
	//	sheet.setZoom(90); // 75% scale

		// Create a row and put some cells in it. Rows are 0 based.
		Row headerRow = sheet.createRow(0);
		CellStyle headerStyle = getCellStyle(wb, "header");
		for (int i = 0; i < titles.length; i++)
		{
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(titles[i]);
			cell.setCellStyle(headerStyle);
		}
		for (int i = 0; i < jobList.size(); i++)
		{
			Row dataRow = sheet.createRow(i + 1);
			Cell c0 = dataRow.createCell(0);
			c0.setCellValue(jobList.get(i).getId());
			c0.setCellStyle(getCellStyle(wb, ""));

			Cell c1 = dataRow.createCell(1);
			c1.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getClientReferenceId() != null)
			{
				c1.setCellValue(jobList.get(i).getClientReferenceId());
			}

			Cell c2 = dataRow.createCell(2);
			c2.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getCreatedTimestamp() != null)
			{
				c2.setCellValue(jobList.get(i).getCreatedTimestamp().toString());
			}

			Cell c3 = dataRow.createCell(3);
			c3.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getRequestedTimestamp() != null)
			{
				c3.setCellValue(jobList.get(i).getRequestedTimestamp().toString());
			}

			Cell c4 = dataRow.createCell(4);
			c4.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getScheduledTimestamp() != null)
			{
				c4.setCellValue(jobList.get(i).getScheduledTimestamp().toString());

			}

			Cell c5 = dataRow.createCell(5);
			c5.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getCompletedTimestamp() != null)
			{

				c5.setCellValue(jobList.get(i).getCompletedTimestamp().toString());

			}

			Cell c6 = dataRow.createCell(6);
			c6.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getCancelledTimestamp() != null)
			{
				c6.setCellValue(jobList.get(i).getCancelledTimestamp().toString());
			}

			Cell c7 = dataRow.createCell(7);
			c7.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getCurrentStatus() != null)
			{
				c7.setCellValue(jobList.get(i).getCurrentStatus());
			}
			
			Cell c8 = dataRow.createCell(8);
			c8.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getEnumDesc() != null)
			{
				c8.setCellValue(jobList.get(i).getEnumDesc());
			}
			
			Cell c9 = dataRow.createCell(9);
			c9.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getCustomerName() != null)
			{
				c9.setCellValue(jobList.get(i).getCustomerName());
			}
			
			Cell c10 = dataRow.createCell(10);
			c10.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getCity() != null)
			{
				c10.setCellValue(jobList.get(i).getCity());
			}
			
			Cell c11 = dataRow.createCell(11);
			c11.setCellStyle(getCellStyle(wb, ""));
			
			if (jobList.get(i).getAddress() != null)
			{
				c11.setCellValue(jobList.get(i).getAddress());
			}
			
			Cell c12 = dataRow.createCell(12);
			c12.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getTotalCharge() != null)
			{
				c12.setCellValue(jobList.get(i).getTotalCharge());
			}
		}

		String fullPath = f + System.getProperty("file.separator") + "EmailReport_" +reportType+ "_" + clientId + ".xlsx";
		FileOutputStream fileOut = new FileOutputStream(fullPath);
		wb.write(fileOut);
		fileOut.close();
		return fullPath;

	}
	
	
	private static String createCustomExcelForTheWarrantyGroup(File f, List<Jobs> jobList, int clientId,String reportType) throws Exception
	{ //client ID: 121
		
		final String[] titles = { "Job_ID", "ClientReference ID", 
				"Created Date", "Requested Date", 
				"Scheduled Date", "Completion Date",
				"Cancelled Date", "Current Status", 
				"Remark", "Customer Name",
				"City", "Address", 
				"Cost"};
		Workbook wb = new XSSFWorkbook(); // or new HSSFWorkbook();
		Sheet sheet = wb.createSheet("new sheet");
		sheet.setColumnWidth(1, 256 * 30);
		sheet.setColumnWidth(2, 256 * 30);
		sheet.setColumnWidth(3, 256 * 30);
		sheet.setColumnWidth(4, 256 * 30);
		sheet.setColumnWidth(5, 256 * 30);
		sheet.setColumnWidth(6, 256 * 30);
		sheet.setColumnWidth(7, 256 * 30);
		sheet.setColumnWidth(8, 256 * 20);
		sheet.setColumnWidth(9, 256 * 20);
		sheet.setColumnWidth(10, 256 * 20);
		sheet.setColumnWidth(11, 356 * 20);
	
		//sheet.setZoom(90); // 75% scale

		// Create a row and put some cells in it. Rows are 0 based.
		Row headerRow = sheet.createRow(0);
		CellStyle headerStyle = getCellStyle(wb, "header");
		for (int i = 0; i < titles.length; i++)
		{
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(titles[i]);
			cell.setCellStyle(headerStyle);
		}
		for (int i = 0; i < jobList.size(); i++)
		{
			Row dataRow = sheet.createRow(i + 1);
			Cell c0 = dataRow.createCell(0);
			c0.setCellValue(jobList.get(i).getId());
			c0.setCellStyle(getCellStyle(wb, ""));

			Cell c1 = dataRow.createCell(1);
			c1.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getClientReferenceId() != null)
			{
				c1.setCellValue(jobList.get(i).getClientReferenceId());
			}

			Cell c2 = dataRow.createCell(2);
			c2.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getCreatedTimestamp() != null)
			{
				c2.setCellValue(jobList.get(i).getCreatedTimestamp().toString());
			}

			Cell c3 = dataRow.createCell(3);
			c3.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getRequestedTimestamp() != null)
			{
				c3.setCellValue(jobList.get(i).getRequestedTimestamp().toString());
			}

			Cell c4 = dataRow.createCell(4);
			c4.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getScheduledTimestamp() != null)
			{
				c4.setCellValue(jobList.get(i).getScheduledTimestamp().toString());

			}

			Cell c5 = dataRow.createCell(5);
			c5.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getCompletedTimestamp() != null)
			{

				c5.setCellValue(jobList.get(i).getCompletedTimestamp().toString());

			}

			Cell c6 = dataRow.createCell(6);
			c6.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getCancelledTimestamp() != null)
			{
				c6.setCellValue(jobList.get(i).getCancelledTimestamp().toString());
			}

			Cell c7 = dataRow.createCell(7);
			c7.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getCurrentStatus() != null)
			{
				c7.setCellValue(jobList.get(i).getCurrentStatus());
			}
			
			Cell c8 = dataRow.createCell(8);
			c8.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getEnumDesc() != null)
			{
				c8.setCellValue(jobList.get(i).getEnumDesc());
			}
			
			Cell c9 = dataRow.createCell(9);
			c9.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getCustomerName() != null)
			{
				c9.setCellValue(jobList.get(i).getCustomerName());
			}
			
			Cell c10 = dataRow.createCell(10);
			c10.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getCity() != null)
			{
				c10.setCellValue(jobList.get(i).getCity());
			}
			
			Cell c11 = dataRow.createCell(11);
			c11.setCellStyle(getCellStyle(wb, ""));
			
			if (jobList.get(i).getAddress() != null)
			{
				c11.setCellValue(jobList.get(i).getAddress());
			}
			
			Cell c12 = dataRow.createCell(12);
			c12.setCellStyle(getCellStyle(wb, ""));
			if (jobList.get(i).getTotalCharge() != null)
			{
				c12.setCellValue(jobList.get(i).getTotalCharge());
			}
		}

		String fullPath = f + System.getProperty("file.separator") + "EmailReport_" +reportType+ "_" + clientId + ".xlsx";
		FileOutputStream fileOut = new FileOutputStream(fullPath);
		wb.write(fileOut);
		fileOut.close();
		return fullPath;

	}

	private static CellStyle getCellStyle(Workbook wb, String type)
	{
		CellStyle style = wb.createCellStyle();
		style.setWrapText(true);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		style.setAlignment(CellStyle.ALIGN_LEFT);
		if (type == "header")
		{
			style.setFillBackgroundColor(IndexedColors.RED.index);
			style.setAlignment(CellStyle.ALIGN_CENTER);
			Font headerFont = wb.createFont();
			headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFont(headerFont);
		}
		return style;
	}

	private static String getCurrentStatus(int currentStatus)
	{
		{
			String status = "";
			if (currentStatus == 0 || currentStatus == 9)
				status = "Booked";
			else if (currentStatus == 1)
				status = "Scheduled";
			else if (currentStatus == 2)
				status = "Started";
			else if (currentStatus == 3 || currentStatus == 5)
				status = "Completed";
			else if (currentStatus == 4)
				status = "Completed - Feedback";
			else if (currentStatus == 6)
				status = "Cancelled";
			else if (currentStatus == 7)
				status = "Completed - Enquiry";
			else
				status = "Completed";
			return status;

		}
	}
	private static String getJobRemark(int jobId,int jobStatus) throws Exception{
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql =" SELECT r.`enum_desc`,r.`enum_type` FROM `tbl_job_comment` c LEFT JOIN `tbl_enum_reason` r ON r.`enum_id` = c.`enum_reason_id`"
				+ " WHERE `job_id` = ?" 
+" UNION SELECT r.enum_desc,r.enum_type FROM tbl_job j LEFT JOIN `tbl_enum_reason` r ON r.`enum_id` = j.cancel_reason_id WHERE job_status = 6 AND `job_id` = ? "
+" UNION SELECT r.enum_desc,r.enum_type FROM tbl_job j LEFT JOIN `tbl_enum_reason` r ON r.`enum_id` = j.`enquiry_reason_id` WHERE job_status = 7 AND `job_id` = ?";
		String jobEnumDesc = null;
		try
		{
			psmt = conn.prepareStatement(sql);
			psmt.setInt(1, jobId);
			psmt.setInt(2, jobId);
			psmt.setInt(3, jobId);
			
			rs = psmt.executeQuery();
			while (rs.next())
			{
				
				String enumDesc = (rs.getString("enum_desc"));
				int enumType = rs.getInt("enum_type");
				if(enumType!=0 && enumType== 1 && jobStatus==6){
					jobEnumDesc = enumDesc;
				}
				else if(enumType!=0 && enumType== 2 && jobStatus==7){
					jobEnumDesc = enumDesc;
				}
				else if(enumType!=0 && enumType== 5 && jobStatus==0){
					jobEnumDesc = enumDesc;
				}
				else if(enumType!=0 && enumType== 8 && jobStatus==1){
					jobEnumDesc = enumDesc;
				}
				else if(enumType!=0 && enumType== 6 && jobStatus==1){
					jobEnumDesc = enumDesc;
				}
				else if(enumType!=0 && enumType== 7 && jobStatus==2){
					jobEnumDesc = enumDesc;
				}
				
				
				
			}
		}
		catch (Exception e)
		{
			log.info("Exception occurred in getJobList :" + e.getMessage());
			throw new Exception(e);
		}
		finally
		{
			if (rs != null)
			{
				try
				{
					rs.close();
				}
				catch (SQLException e)
				{ /* ignored */
				}
			}
			if (psmt != null)
			{
				try
				{
					psmt.close();
				}
				catch (SQLException e)
				{ /* ignored */
				}
			}
		}
	//	System.out.println("jobEnumDescfor job:"+jobId +"is: "+jobEnumDesc);
		log.info("jobEnumDescfor job:"+jobId +"is: "+jobEnumDesc);
		return jobEnumDesc;

	}
	
	private static void main (String[] args){
		try {
			EmailReportingService e = new EmailReportingService();
			e.getJobRemark(20108,6);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
