package com.easyfix.reports.dao;

import java.io.FileInputStream;
import java.util.List;

import com.easyfix.reports.model.CompletedJobsReport;

public interface CompletedJobsReportDao {
//public List<CompletedJobsReport> getCompletedJobList(String start, String end, int flag) throws Exception; 
public List<CompletedJobsReport> downloadCompletedJobReport(String start, String end, int flag, int client) throws Exception;
public List<CompletedJobsReport> downloadRescheduleJobReport(String start, String end, int flag, int client) throws Exception;
}
