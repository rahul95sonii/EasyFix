package com.easyfix.reports.dao;

import java.util.List;

import com.easyfix.reports.model.EasyfixerReport;

public interface EasyfixerReportDao {
	public List<EasyfixerReport>  downloadEfrReport(String start, String end, int flag, int client) throws Exception;
}
