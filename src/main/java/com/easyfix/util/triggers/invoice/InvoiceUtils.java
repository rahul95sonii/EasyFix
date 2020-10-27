package com.easyfix.util.triggers.invoice;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.easyfix.util.PropertyReader;
import com.easyfix.util.triggers.model.ClientInvoiceBean;
import com.easyfix.util.triggers.utils.ConnectionProvider;



public class InvoiceUtils
{
	
	private static String seperator=System.getProperty("file.separator");
	private static final Logger logger = LogManager.getLogger(GenerateInvoiceDetails.class);
	
	public static void createRootDirectory(PropertyReader props){
		String filePath = props.getValue("UPLOAD.PATH");
		Date curdate = new Date();
		String curMonth = new SimpleDateFormat("MMM_yyyy").format(curdate);
		File rootDir = new File(filePath);
		if (!rootDir.exists())
		{
			rootDir.mkdir();
			rootDir.setReadable(true);
			rootDir.setWritable(true);
		}
		File monthDir = new File(filePath + seperator + curMonth);
		if (!monthDir.exists())
		{
			monthDir.mkdir();
			monthDir.setReadable(true);
			monthDir.setWritable(true);
		}

	}
	
	public static List<ClientInvoiceBean> fetchAllClientDetails(PropertyReader props, String password) throws Exception
	{
		List<ClientInvoiceBean> clientList = new ArrayList<ClientInvoiceBean>();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		ClientInvoiceBean cib = null;
		// String getClientsql=
		// "select * from tbl_client where client_status=? and billing_raised=1";
		String getClientsql = "SELECT * FROM `tbl_client` CL" +
				 " WHERE client_status = 1 AND billing_raised = 1";
		try
		{
			conn = ConnectionProvider.getConnection(props, password);

			if (conn != null)
			{
				pstmt = conn.prepareStatement(getClientsql);
				// pstmt.setInt(1, 1);
				rs = pstmt.executeQuery();
				if (rs != null)
				{
					while (rs.next())
					{
						cib = new ClientInvoiceBean();
						cib.setClientId(rs.getInt("client_id"));
						cib.setClientName(rs.getString("client_name"));
						cib.setBillingCycle(rs.getString("billing_cycle"));
						cib.setBillingFromDate(rs.getString("billing_start_date"));
						cib.setBillingRaised(rs.getInt("billing_raised"));
//						if(rs.getInt("client_id")==14){
//						System.out.println(rs.getString("client_address")+ "------------------" +rs.getString("billing_name"));
//						}
						cib.setBilingAddress(rs.getString("client_address"));
						cib.setBillingName(rs.getString("billing_name"));
						clientList.add(cib);
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.catching(e);
		}
		finally
		{
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}

		return clientList;
	}
	
	
	public static ClientInvoiceBean fetchClientDetails(int clinetId,PropertyReader props, String password) throws Exception
	{
		ClientInvoiceBean clientInvoiceBean= new ClientInvoiceBean();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		// String getClientsql=
		// "select * from tbl_client where client_status=? and billing_raised=1";
		String getClientsql = "SELECT * from `tbl_client` " + 
		" WHERE client_status = 1 AND billing_raised = 1 AND client_id=?";
		try
		{
			conn = ConnectionProvider.getConnection(props, password);

			if (conn != null)
			{
				pstmt = conn.prepareStatement(getClientsql);
				pstmt.setInt(1, clinetId);
				rs = pstmt.executeQuery();
				if (rs != null)
				{
					while (rs.next())
					{
						clientInvoiceBean = new ClientInvoiceBean();
						clientInvoiceBean.setClientId(rs.getInt("client_id"));
						clientInvoiceBean.setClientName(rs.getString("client_name"));
						clientInvoiceBean.setBillingCycle(rs.getString("billing_cycle"));
						clientInvoiceBean.setBillingFromDate(rs.getString("billing_start_date"));
						clientInvoiceBean.setBillingRaised(rs.getInt("billing_raised"));
						if(rs.getInt("client_id")==14){
						System.out.println(rs.getString("client_address")+ "------------------" +rs.getString("billing_name"));
						}
						clientInvoiceBean.setBilingAddress(rs.getString("client_address"));
						clientInvoiceBean.setBillingName(rs.getString("billing_name"));
					}
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.catching(e);
		}
		finally
		{
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}

		return clientInvoiceBean;
	}
	
	
	public static void updateInvoiceFiles(int tempClientId, PropertyReader props, String password, String excelFile, String pdfFile) throws Exception
	{
		Connection conn = null;
		CallableStatement cstmt = null;
		//String fileName = null;
		//ClientInvoiceBean cib= new ClientInvoiceBean();
		//getBillingStartDate(tempClientId);
		//System.out.println("billing cycle: " +billingCycle +" flag: " +flag);
		try
		{
			conn = ConnectionProvider.getConnection(props, password);
			if (conn != null)
			{
				cstmt = conn.prepareCall("call sp_ef_update_client_invoice_file_name(?,?,?)");
				cstmt.setInt(1, tempClientId);
				cstmt.setString(2, excelFile);
				cstmt.setString(3, pdfFile);
				
				cstmt.executeUpdate();

			}
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.catching(e);
		}
		finally
		{
			if (cstmt != null)
				cstmt.close();
			if (conn != null)
				conn.close();
		}
		
	}

	protected static ClientInvoiceBean generateInvoiceData(int tempClientId, PropertyReader props, String password, int billingCycle, int flag, String billingFromDate) throws Exception
	{
		Connection conn = null;
		CallableStatement cstmt = null;
		//String fileName = null;
		ClientInvoiceBean cib= new ClientInvoiceBean();
		//getBillingStartDate(tempClientId);
		System.out.println("billing cycle: " +billingCycle +" flag: " +flag);
		try
		{
			conn = ConnectionProvider.getConnection(props, password);
			if (conn != null)
			{
				cstmt = conn.prepareCall("call sp_ef_generate_client_invoice(?,?,?,?,?,?)");
				cstmt.setInt(1, tempClientId);
				cstmt.setInt(2, billingCycle);
				cstmt.setInt(3, flag);
				cstmt.setString(4, billingFromDate);
				cstmt.registerOutParameter(5, Types.VARCHAR);
				cstmt.registerOutParameter(6, Types.VARCHAR);
				cstmt.executeUpdate();
				cib.setBillingFromDate(cstmt.getString(5));
				cib.setBillingToDate(cstmt.getString(6));
				//System.out.println("file name from db: " +fileName);
				System.out.println("start date: " +cstmt.getString(5));
				System.out.println("end date: " +cstmt.getString(6));
			}
			
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.catching(e);
		}
		finally
		{
			if (cstmt != null)
				cstmt.close();
			if (conn != null)
				conn.close();
		}
		return cib;
	}

	protected static List<ClientInvoiceBean> generateExcelData(int tempClientId, PropertyReader props, String password, String billingStartDate, String billingToDate) throws Exception
	{
		List<ClientInvoiceBean> jobListExcel = new ArrayList<ClientInvoiceBean>();
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		ClientInvoiceBean jobObj = null;
		System.out.println("bst: " +billingStartDate + ", " +billingToDate + ", " +tempClientId);
		try
		{
			conn = ConnectionProvider.getConnection(props, password);
			if (conn != null)
			{
				cstmt = conn.prepareCall("call sp_ef_generate_client_invoice_excel(?,?,?)");
				cstmt.setInt(1, tempClientId);
				cstmt.setString(2, billingStartDate);
				cstmt.setString(3, billingToDate);
				rs = cstmt.executeQuery();
				if (rs != null)
				{
					while (rs.next())
					{
						jobObj = new ClientInvoiceBean();
						jobObj.setJobId(rs.getInt("job_id"));
						jobObj.setJobDesc(rs.getString("job_desc"));
						jobObj.setClientId(rs.getInt("fk_client_id"));
						jobObj.setClientRefId(rs.getString("client_ref_id"));
						jobObj.setCustomerName(rs.getString("customer_name"));
						jobObj.setCustomerNo(rs.getString("customer_mob_no"));
						jobObj.setServiceTypeId(rs.getInt("fk_service_type_id"));
						jobObj.setServiceTypeName(rs.getString("service_type_name"));
						jobObj.setCityName(rs.getString("city_name"));
						jobObj.setCheckOutDate(rs.getString("checkOutDate"));
						jobObj.setJobStatus(rs.getString("job_status"));
						jobObj.setJobServices(rs.getString("services"));
						jobObj.setTotalAmount(rs.getFloat("total_amount"));
						jobObj.setRepairCharge(rs.getFloat("repair_amount"));
						jobObj.setInstallationCharge(rs.getFloat("install_amount"));
						jobListExcel.add(jobObj);
					}
				}
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.catching(e);
		}
		finally
		{
			if (rs != null)
				rs.close();
			if (cstmt != null)
				cstmt.close();
			if (conn != null)
				conn.close();

		}

		return jobListExcel;
	}
	
	
}
