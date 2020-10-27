package com.easyfix.invoice.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.easyfix.clients.model.Clients;
import com.easyfix.invoice.model.Invoice;
import com.easyfix.util.UtilityFunctions;
import com.easyfix.invoice.dao.InvoiceDao;

public class InvoiceDaoImpl extends JdbcDaoSupport implements InvoiceDao {

	@Override
	public List<Invoice> clientInvoiceList() throws Exception {
		List<Invoice> clientInvoiceList = new ArrayList<Invoice>();
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Invoice invObj = null;
		Clients clientObj = null;
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
			if(conn!=null){
				cstmt = conn.prepareCall("call sp_ef_client_invoice_list()");
				if(cstmt!=null){
					rs = cstmt.executeQuery();
					if(rs!=null){
						while(rs.next()){
							invObj = new Invoice();
							clientObj = new Clients();
							String bsd = "";
							String btd = "";
							String aud = "";							
							String fPath = "";
							
							
							invObj.setInvoiceId(rs.getInt("id"));
							invObj.setBillingStartDate(rs.getString("billing_from_date"));
							invObj.setBillingEndDate(rs.getString("billing_to_date"));
							invObj.setCurentDues(rs.getFloat("current_due_amount"));
							invObj.setPreviousDues(rs.getFloat("previous_due_amount"));
							invObj.setTotalBillingAmount(rs.getFloat("total_invoice_amount"));
							invObj.setAmountDueDate(rs.getString("amount_due_date"));
							invObj.setIsRaised(rs.getInt("is_raised"));
							invObj.setIsPaid(rs.getInt("is_paid"));
							invObj.setTotalPaidAmount(rs.getFloat("total_paid_amount"));
							invObj.setInvClientId(rs.getInt("fk_client_id"));
							invObj.setInvoiceFileName(rs.getString("file_path_pdf"));					
							invObj.setMasterSheetFileName(rs.getString("file_path_excel"));
							bsd = UtilityFunctions.changeDateFormatToFormat(invObj.getBillingStartDate(), "yyyy-MM-dd", "dd MMM");
							btd = UtilityFunctions.changeDateFormatToFormat(invObj.getBillingEndDate(), "yyyy-MM-dd", "dd MMM, yyyy");
							aud = UtilityFunctions.changeDateFormatToFormat(invObj.getAmountDueDate(), "yyyy-MM-dd", "dd MMM, yyyy");
							
							invObj.setBillingStartDate(bsd);
							invObj.setBillingEndDate(btd);
							invObj.setAmountDueDate(aud);
							
							invObj.setBillingCycle(bsd+" -- "+btd);
							
							clientObj.setClientName(rs.getString("client_name"));
							invObj.setClient(clientObj);
							
							fPath = clientObj.getClientName().replaceAll(" ", "_").toLowerCase()+invObj.getInvClientId();

							invObj.setFilePath(fPath);
							
							clientInvoiceList.add(invObj);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null)
				rs.close();
			if(cstmt!=null)
				cstmt.close();
			if(conn!=null)
				conn.close();
			
		}

		
		return clientInvoiceList;
	}

	@Override
	public List<Invoice> clientInvoiceListByclientId(int clientId) throws Exception {
		List<Invoice> clientInvoiceList = new ArrayList<Invoice>();
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Invoice invObj = null;
		Clients clientObj = null;
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
			if(conn!=null){
				cstmt = conn.prepareCall("call sp_ef_client_invoice_list_by_clientId(?)");				
				if(cstmt!=null){
					cstmt.setInt(1, clientId);
					rs = cstmt.executeQuery();
					if(rs!=null){
						while(rs.next()){
							invObj = new Invoice();
							clientObj = new Clients();
							String bsd = "";
							String btd = "";
							String aud = "";
							invObj.setInvoiceId(rs.getInt("id"));
							invObj.setBillingStartDate(rs.getString("billing_from_date"));
							invObj.setBillingEndDate(rs.getString("billing_to_date"));
							invObj.setCurentDues(rs.getFloat("current_due_amount"));
							invObj.setPreviousDues(rs.getFloat("previous_due_amount"));
							invObj.setTotalBillingAmount(rs.getFloat("total_invoice_amount"));
							invObj.setAmountDueDate(rs.getString("amount_due_date"));
							invObj.setIsRaised(rs.getInt("is_raised"));
							invObj.setIsPaid(rs.getInt("is_paid"));
							invObj.setTotalPaidAmount(rs.getFloat("total_paid_amount"));
							invObj.setInvClientId(rs.getInt("fk_client_id"));
							invObj.setInvoiceFileName(rs.getString("file_path_pdf"));
							
							bsd = UtilityFunctions.changeDateFormatToFormat(invObj.getBillingStartDate(), "yyyy-MM-dd", "dd MMM, yyyy");
							btd = UtilityFunctions.changeDateFormatToFormat(invObj.getBillingEndDate(), "yyyy-MM-dd", "dd MMM, yyyy");
							aud = UtilityFunctions.changeDateFormatToFormat(invObj.getAmountDueDate(), "yyyy-MM-dd", "dd MMM, yyyy");
							
							invObj.setBillingStartDate(bsd);
							invObj.setBillingEndDate(btd);
							invObj.setAmountDueDate(aud);
							
							clientObj.setClientName(rs.getString("client_name"));
							invObj.setClient(clientObj);
							
							clientInvoiceList.add(invObj);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs!=null)
				rs.close();
			if(cstmt!=null)
				cstmt.close();
			if(conn!=null)
				conn.close();
			
		}

		
		return clientInvoiceList;
	}

	@Override
	public void changeInvoiceStatus(int invId) throws Exception {
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
			if(conn!=null){
				cstmt = conn.prepareCall("call sp_ef_change_invoice_status(?)");
				cstmt.setInt(1, invId);
				cstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(cstmt!=null)
				cstmt.close();
			if(conn!=null)
				conn.close();
		}
		
	}

	@Override
	public void saveInvoicePaidAmount(int invId, float paidAmount, int paidBy) throws Exception {
		Connection conn = null;
		CallableStatement cstmt = null;
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
			if(conn!=null){
				cstmt = conn.prepareCall("call sp_ef_save_invoice_paid_amount(?,?,?)");
				cstmt.setInt(1, invId);
				cstmt.setFloat(2, paidAmount);
				cstmt.setInt(3, paidBy);
				cstmt.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(cstmt!=null)
				cstmt.close();
			if(conn!=null)
				conn.close();
		}
		
	}

	@Override
	public Invoice getInvoiceDetailById(Invoice inv) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	

}
