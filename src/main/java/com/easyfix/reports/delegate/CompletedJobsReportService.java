package com.easyfix.reports.delegate;

import java.io.FileInputStream;
import java.util.List;

import com.easyfix.reports.model.CompletedJobsReport;

public interface CompletedJobsReportService {
	//public List<CompletedJobsReport> getCompletedJobList(String start, String end, int flag) throws Exception; 
	public FileInputStream downloadCompletedJobReport(String start, String end, int flag, int client) throws Exception;
	public String convertStringToDate(String dateString) throws Exception;
	public FileInputStream downloadRescheduleJobReport(String start, String end, int flag, int client) throws Exception;
}
