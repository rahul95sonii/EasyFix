package com.easyfix.reports.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.easyfix.reports.model.EasyfixerReport;

public class EasyfixerReportDaoImpl extends JdbcDaoSupport implements EasyfixerReportDao {

	@Override
	public List<EasyfixerReport> downloadEfrReport(String start, String end, int flag, int client)
			throws Exception {
		Connection conn = null;
		CallableStatement cstmt=null;
		ResultSet rs = null;
		List<EasyfixerReport> efrList = new ArrayList<EasyfixerReport>();
		try{
			conn = getJdbcTemplate().getDataSource().getConnection();
			cstmt=conn.prepareCall("call sp_ef_report_easyfixer_report(?,?,?,?)");
			cstmt.setString(1, start);
			cstmt.setString(2, end);
			cstmt.setInt(3, flag);
			cstmt.setInt(4, client);
			rs = cstmt.executeQuery();
			if(rs!=null){
				while(rs.next()){
					EasyfixerReport efrReportObj = new EasyfixerReport();
					efrReportObj.setEfrId(rs.getInt("efr_id"));
					efrReportObj.setEfrName(rs.getString("efr_name"));
					efrReportObj.setActiveDays(rs.getLong("active_days"));
					efrReportObj.setAvgTAT(rs.getLong("avgTAT"));
					efrReportObj.setCancledJobs(rs.getInt("cancled_jobs"));
					efrReportObj.setCusRat(rs.getFloat("cus_rat"));
					efrReportObj.setDateOfJoining(rs.getDate("insert_date"));
					efrReportObj.setDis(rs.getLong("dis"));
					efrReportObj.setEfrDoc(rs.getString("efr_doc"));
					efrReportObj.setEfrIncome(rs.getFloat("efr_income"));
					efrReportObj.setEfrServiceType(rs.getString("efr_service_type"));
					efrReportObj.setLessThenOrEqual250(rs.getInt("lessthenorequal250"));
					efrReportObj.setMoreThen250(rs.getInt("morethen250"));
					efrReportObj.setNdmName(rs.getString("ndm_name"));
					efrReportObj.setStatus(rs.getInt("efr_status"));
					efrReportObj.setBalance(rs.getFloat("balance"));
					efrReportObj.setCity(rs.getString("city_name"));
					efrReportObj.setMobNo(rs.getString("efr_no"));
					efrReportObj.setAddress(rs.getString("efr_address"));
					efrReportObj.setAppUser(rs.getString("app_user"));
					efrReportObj.setAppVersionName(rs.getString("app_version_name"));
					efrReportObj.setZone(rs.getString("zone_name"));
					efrList.add(efrReportObj);
				}
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally {
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
		return efrList;
	}

}
