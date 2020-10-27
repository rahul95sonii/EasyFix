package com.easyfix.finance.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.finance.model.EasyfixerFinance;
import com.easyfix.finance.model.Finance;
import com.easyfix.user.model.User;
import com.easyfix.util.DBConfig;
import com.easyfix.util.UtilityFunctions;

public class FinanceDaoImpl extends JdbcDaoSupport implements FinanceDao {

	@Override
	public List<Finance> getTransactionList(Finance financeObj)	throws Exception {
		List<Finance> transctionList = new ArrayList<Finance>();
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		Finance finObj = null;
		try {
				//conn = getJdbcTemplate().getDataSource().getConnection();
			DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();
				if(conn!=null){
					cstmt = conn.prepareCall("{call sp_ef_finance_geteasyfixer_transaction(?,?,?,?,?)}");
					cstmt.setInt(1, financeObj.getEasyfixerId());
					cstmt.setString(2, financeObj.getFromDate());
					cstmt.setString(3, financeObj.getToDate());
					cstmt.setInt(4, financeObj.getTransactionType());
					cstmt.setInt(5, financeObj.getCreatedBy().getUserId());
					rs = cstmt.executeQuery();
					if(rs!=null){
						while(rs.next()){
							finObj = new Finance();
							finObj.setTransactionId(rs.getInt("transaction_id"));
							finObj.setEasyfixerId(rs.getInt("easyfixer_id"));
							finObj.setEasyfixerName(rs.getString("efr_name"));
							finObj.setEasyfixerNo(rs.getString("efr_no"));
							finObj.setTransactionDate(rs.getString("transaction_date"));
							finObj.setTransactionAmount(rs.getString("amount"));
							finObj.setBalaceAmount(rs.getString("balance"));
							finObj.setDescription(rs.getString("description"));
							finObj.setTransactionType(rs.getInt("transaction_type"));
							finObj.setSource(rs.getInt("source"));
							finObj.setEmpName(rs.getString("created_by"));
							finObj.setJobId(rs.getInt("job_id"));
							transctionList.add(finObj);	
						}
					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /*ignored*/ }
            }
            if (cstmt != null) {
                try {
                	cstmt.close();
                } catch (SQLException e) { /*ignored*/ }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /*ignored*/ }
            }
        }
		
		
		
		
		return transctionList;
	}

	@Override
	public int addUpdateEasyfixerAmount(Finance financeObj) throws Exception {
		Connection conn = null;
		CallableStatement cstmt = null;
		int status = 0;
		try {
				//conn = getJdbcTemplate().getDataSource().getConnection();
			DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();
				if(conn!=null){
					cstmt = conn.prepareCall("{call sp_ef_finance_add_update_easyfixer_transaction(?,?,?,?,?,?,?,?)}");
					cstmt.setInt(1, financeObj.getEasyfixerId());
					cstmt.setDouble(2, Double.parseDouble(financeObj.getTransactionAmount()));
					cstmt.setInt(3, financeObj.getTransactionType());
					cstmt.setInt(4, financeObj.getSource());
					cstmt.setString(5, financeObj.getDescription());
					cstmt.setInt(6, financeObj.getCreatedBy().getUserId());
					cstmt.setInt(7, financeObj.getJobId());
					cstmt.registerOutParameter(8, Types.INTEGER);
					cstmt.executeQuery();
					status = cstmt.getInt(8);
					
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally {
            if (cstmt != null) {
                try {
                	cstmt.close();
                } catch (SQLException e) { /*ignored*/ }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /*ignored*/ }
            }
        }
		
		return status;
	}

	@Override
	public String getCurrentBalance(int easyfixerId) throws Exception {
		
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		String curBalance = "0.00";
		try {
				//conn = getJdbcTemplate().getDataSource().getConnection();
			DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();
				if(conn!=null){
					cstmt = conn.prepareCall("{call sp_ef_finance_get_easyfixer_current_balance(?)}");
					cstmt.setInt(1, easyfixerId);
					rs = cstmt.executeQuery();
					if(rs!=null){
						while(rs.next()){
							curBalance = rs.getString("balance");
						}
					}
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) { /*ignored*/ }
            }
            if (cstmt != null) {
                try {
                	cstmt.close();
                } catch (SQLException e) { /*ignored*/ }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /*ignored*/ }
            }
        }
		
		
		return curBalance;		
		
	}

	
	
	@Override
	public List<EasyfixerFinance> ndmRechargeList(int efrId, int ndmId, int flag) throws Exception {
		List<EasyfixerFinance> efrRechargeList = new ArrayList<EasyfixerFinance>();
		
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		EasyfixerFinance efrFinObj = null;
		Easyfixers efrObj = null;
		User ndm = null;
		String recharge_date = "";
		String approve_date = "";
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
			if(conn!=null){
				//System.out.println("here");
				cstmt = conn.prepareCall("call sp_ef_finance_efr_recharge(?,?,?)");
				cstmt.setInt(1, efrId);
				cstmt.setInt(2, ndmId);
				cstmt.setInt(3, flag);
				rs = cstmt.executeQuery();
				if(rs!=null){
					while(rs.next()){
						efrFinObj = new EasyfixerFinance();
						efrObj = new Easyfixers();
						ndm = new User();
						efrFinObj.setRecharge_id(rs.getInt("recharge_id"));
						efrFinObj.setEfrId(rs.getInt("efr_id"));
						efrFinObj.setNdmId(rs.getInt("ndm_id"));
						efrFinObj.setRecharge_amount(rs.getFloat("recharge_amount"));
						efrFinObj.setRecharge_date(rs.getString("recharge_date"));
						efrFinObj.setApprovalDate(rs.getString("approval_date"));
						efrFinObj.setRecharge_type(rs.getInt("recharge_type"));
						efrFinObj.setComments(rs.getString("comments"));
						efrFinObj.setApproved_by_finance(rs.getInt("approved_by_finance"));
						efrObj.setEasyfixerName(rs.getString("efr_name"));
						efrObj.setEasyfixerNo(rs.getString("efr_no"));
						ndm.setUserId(rs.getInt("ndm_id"));
						ndm.setUserName(rs.getString("user_name"));
						efrFinObj.setPaymentMode(rs.getString("payment_mode"));
						efrFinObj.setReferenceId(rs.getString("reference_id"));
						
						efrFinObj.setEfr(efrObj);
						efrFinObj.setNdm(ndm);
						
						recharge_date = UtilityFunctions.changeDateFormatToFormat(efrFinObj.getRecharge_date(), "yyyy-MM-dd HH:mm:ss", "dd-MM-yyyy HH:mm");
						if(efrFinObj.getApprovalDate() != null)
							approve_date = UtilityFunctions.changeDateFormatToFormat(efrFinObj.getApprovalDate(), "yyyy-MM-dd HH:mm:ss", "dd-MM-yyyy HH:mm");
						
						efrFinObj.setApprovalDate(approve_date);
						efrFinObj.setRecharge_date(recharge_date);
						efrRechargeList.add(efrFinObj);
						
					}
				}			
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null)
				rs.close();
			if(cstmt!=null)
				cstmt.close();
			if(conn!=null)
				conn.close();
		}
		
		
		return efrRechargeList;
	}

	@Override
	public int addUpdateNdmRechargeAmount(EasyfixerFinance efrFinObj) throws Exception {
		Connection conn = null;
		CallableStatement cstmt = null;
		int status = 0;
		try {
				//conn = getJdbcTemplate().getDataSource().getConnection();
			DataSource ds=DBConfig.getContextDataSource();			
			conn = ds.getConnection();
				if(conn!=null){
					cstmt = conn.prepareCall("{call sp_ef_finance_add_update_efr_recharge_amount(?,?,?,?,?,?,?,?,?,?)}");
					cstmt.setInt(1, efrFinObj.getRecharge_id());
					cstmt.setInt(2, efrFinObj.getEfrId());
					cstmt.setFloat(3, efrFinObj.getRecharge_amount());
					cstmt.setInt(4, efrFinObj.getNdmId());
					cstmt.setString(5, efrFinObj.getComments());
					cstmt.setInt(6, efrFinObj.getCreatedBy());
					cstmt.setInt(7, efrFinObj.getRecharge_type());
					cstmt.setString(8, efrFinObj.getPaymentMode());
					cstmt.setString(9, efrFinObj.getReferenceId());
					cstmt.registerOutParameter(10, Types.INTEGER);
					cstmt.executeQuery();
					status = cstmt.getInt(10);
					
				}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		}finally {
            if (cstmt != null) {
                try {
                	cstmt.close();
                } catch (SQLException e) { /*ignored*/ }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) { /*ignored*/ }
            }
        }
		
		return status;
	}

	

	
}
