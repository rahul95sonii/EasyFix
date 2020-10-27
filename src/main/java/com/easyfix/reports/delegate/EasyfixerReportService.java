package com.easyfix.reports.delegate;

import java.io.FileInputStream;

public interface EasyfixerReportService {
	public FileInputStream downloadEfrReport(String start, String end, int flag, int client) throws Exception;
	 public String convertStringToDate(String dateString) throws Exception;
}
