package com.easyfix.util.triggers.service;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.easyfix.util.*;
import com.easyfix.util.triggers.SMSReporting;
import com.easyfix.util.triggers.model.Recipients;
import com.easyfix.util.triggers.model.Sms;
import com.easyfix.util.triggers.model.Tracking;
import com.easyfix.util.triggers.utils.ConnectionProvider;
import com.easyfix.util.triggers.utils.DBUtils;
import com.easyfix.util.triggers.utils.DateUtils;

public class SMSReportingService
{
	private static Connection conn = null;
	public static AtomicReference<List<Sms>> smscache = new AtomicReference<List<Sms>>();
	public static AtomicReference<Long> smsTime = new AtomicReference<Long>(0l);
	public static AtomicReference<Long> receipientTime = new AtomicReference<Long>(0l);
	public static AtomicReference<List<Recipients>> recipientscache = new AtomicReference<List<Recipients>>();
	public static Map<Integer, List<Recipients>> recipientsMap;
	static Logger log = LogManager.getLogger(SMSReporting.class);

	public static void sendSms(PropertyReader props, String password)
	{
		conn = ConnectionProvider.getConnection(props, password);
		List<Sms> smsList = getsmsDetails();
		log.info("Total messages to be sent: " + smsList.size());

		if (smsList.size() == 0)
		{
			log.info("No messages being sent");
			return;
		}
		Map<Integer, List<Recipients>> recipientMap = getRecipientsDetails();
		String name = "";
		String contact = "";
		try
		{
			for (int i = 0; i < smsList.size(); i++)
			{
				int clientId = smsList.get(i).getClientId();
				log.info("Seding for client id:" + clientId);
				String text = smsList.get(i).getSmsText();
				String clientDisplayName = smsList.get(i).getClientNameForSms();
				try
				{
					String startDate = DateUtils.getYesterdayDate() + " 00:00:01";
					String endDate = DateUtils.getTodaysDate() + " 00:00:01";
					Tracking tracking = DBUtils.getTrackingData(conn, clientId, startDate, endDate);
					text = text.replaceFirst("summary of %s", "summary of " + clientDisplayName);
					text = text.replaceFirst("between %s and %s",
							"between " + DateUtils.getYesterdayDate() + " and " + DateUtils.getTodaysDate());
					text = text.replaceFirst("New Jobs : %d", "New Jobs : " + String.valueOf(tracking.getCountJobCreated()));
					text = text.replaceFirst("Completed Jobs : %d", "Completed Jobs : " + String.valueOf(tracking.getCountJobCheckedout()));
					text = text.replaceFirst("Scheduled Jobs : %d", "Scheduled Jobs : " + String.valueOf(tracking.getCountJobScheduled()));
					text = text.replaceFirst("Requested Jobs: %d", "Requested Jobs: " + String.valueOf(tracking.getCountJobRequested()));
				}
				catch (Exception e)
				{
					log.info("Exception occurred while processing reporting sms for :" + clientId + " message : " + e.getMessage());
					break;
				}
				List<Recipients> recipients = recipientMap.get(clientId);
				if (recipients!=null)
				{
					for (int j = 0; j < recipients.size(); j++)
					{
						contact = recipients.get(j).getContactNo();
						name = recipients.get(j).getContactName();
						String finalText = text;
						finalText = text.replaceFirst("Dear %s", "Dear " + name);
						log.info("Sending: " + finalText + " to : " + name + " at " + contact);
						SmsSender.sendSms(finalText, contact);
					}
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
		String sql = "select * from tbl_client_contacts where status =1";
		try
		{
			psmt = conn.prepareStatement(sql);
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

				recipientsListFromDB.add(r);
			}
		}
		catch (SQLException e)
		{
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

		return recipientsListFromDB;

	}

	public static List<Sms> getsmsListFromDB()
	{
		List<Sms> smsListFromDB = new ArrayList<Sms>();
		PreparedStatement psmt = null;
		ResultSet rs = null;
		String sql = "select * from tbl_sms_reporting_meta where status =1";
		try
		{
			psmt = conn.prepareStatement(sql);
			rs = psmt.executeQuery();
			while (rs.next())
			{
				Sms sms = new Sms();
				sms.setClientId(rs.getInt("client_id"));
				sms.setSmsId(rs.getInt("sms_id"));
				sms.setSmsText(rs.getString("sms_text"));
				sms.setStatus(rs.getInt("status"));
				sms.setClientNameForSms(rs.getString("client_display_name"));
				smsListFromDB.add(sms);
			}
		}
		catch (SQLException e)
		{
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

		return smsListFromDB;

	}

	public static List<Sms> getsmsDetails()
	{
		long currentTimeinMilli = System.currentTimeMillis();
		if (smsTime.get() == 0 || (currentTimeinMilli - smsTime.get()) > 5 * 60 * 1000)
		{
			smscache.set(getsmsListFromDB());
			smsTime.set(currentTimeinMilli);
		}
		return smscache.get();
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
}
