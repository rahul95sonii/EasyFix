package com.easyfix.reports.delegate;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.Exporters;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsxExporterBuilder;
import net.sf.dynamicreports.jasper.constant.JasperProperty;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.chart.Bar3DChartBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.jasperreports.engine.JRDataSource;

import com.easyfix.reports.dao.CompletedJobsReportDao;
import com.easyfix.reports.model.CompletedJobsReport;
import com.easyfix.util.UtilityFunctions;


public class CompletedJobsReportServiceImpl implements CompletedJobsReportService{
	CompletedJobsReportDao completedJobsReportDao;
	List<CompletedJobsReport> jobList;

/*//	@Override
	public List<CompletedJobsReport> getCompletedJobList(String start, String end, int flag)
			throws Exception {
		return null;
	}*/
	@Override
	public FileInputStream downloadCompletedJobReport(String start, String end,
			int flag, int client) throws Exception {
		
		
		jobList =completedJobsReportDao.downloadCompletedJobReport(start, end, flag, client);
		return buildReport();
	}
	
	 private JRDataSource createDataSource() throws Exception {
		 DRDataSource dataSource = new DRDataSource(
					"cityName", "jobId","jobDesc","totalCharge", 
					 "EF_share","EFR_share","client_share","material_charge",
					  "completionTAT","scheduledTAT","checkinTAT","collectedBy",
					  "EfrDisTravelled","custName","customerSatisfactionScore","easyfixerId",
					  "easyfixerName","clientName","servicetype","jobCreationDtae",
				      "requestedOn","feedbackDate","chcekoutTime","noofrejection",
				      "jobStatus","clientRefId","scheduleComment","checkinComment",
				      "chekoutComment","feedbackComment","cancelComment","EFRNo",
				      "cancleReason","enquiryReason","commentOnEnquiry","address",
				      "pincode","cancelDate","scheduledBy","checkinBy","checkoutBy",
				      "rescheduleReason","commentOnReschedule","rescheduleTime","rescheduleBy",
				      "custMobileNo","jobDocs","appRejected","appCancelled"
					
				 );
		 for(CompletedJobsReport e:jobList)
			 dataSource.add(e.getCityName(),e.getJobId(),e.getJobDesc(),e.getTotalCharge(),
					 e.getEF_share(),e.getEFR_share(),e.getClient_share(),e.getMaterial_charge(),
					 e.getCompletionTAT(),e.getScheduledTAT(),e.getCheckinTAT(),e.getCollectedBy(),
					 e.getEfrDisTravelled(),e.getCustName(),e.getCustomerSatisfactionScore(),e.getEasyfixerId(),
					 e.getEasyfixerName(),e.getClientName(),e.getServicetype(),e.getJobCreationDtae(),
					 e.getRequestedOn(),e.getFeedbackDate(),e.getChcekoutTime(),e.getNoofrejection(),
					 e.getJobStatus(),e.getJobRefId(),e.getCommentOnSchedule(),e.getCommentOnCheckIn(),
					 e.getCommentOnCheckout(),e.getCommentOnFeedback(),e.getCommentOnCancel(),e.getEfPhoneNo(),
					 e.getCancleReason(),e.getEnquiryReason(),e.getCommentOnEnquiry(),e.getCustomerAddress(),
					 e.getCustomerPinCode(),e.getCancelTime(),e.getScheduledBy(),e.getCheckinBy(),e.getCheckoutBy(),
					 e.getRescheduleReason(),e.getCommentOnReschedule(),e.getRescheduleTime(),e.getRescheduleBy(),
					 e.getCustMobileNo(),e.getJobDocs(),e.getEfrAppRejected(),e.getEfrAppCancelled()
					 );
		 return dataSource;
			 
	 }
		@Override
		public FileInputStream downloadRescheduleJobReport(String start,
				String end, int flag, int client) throws Exception {
			jobList = completedJobsReportDao.downloadRescheduleJobReport(start, end, flag, client);
		//	System.out.println(jobList);
			return buildJobRescheduleReport();
		}
	 private FileInputStream buildJobRescheduleReport(){
		 FileInputStream reportFile=null;
		 try{
			 File jobReport =new File("jobReport.xlsx");
			 jobReport.createNewFile();
	         JasperXlsxExporterBuilder xlsExporter = Exporters.xlsxExporter(jobReport)
	              .setDetectCellType(true)
	              .setIgnorePageMargins(true)
	              .setWhitePageBackground(false)
	              .setRemoveEmptySpaceBetweenColumns(true);
	       
	         //styles
	 		StyleBuilder boldStyle         = DynamicReports.stl.style().bold(); 
	 		StyleBuilder centertextalignStyle         = DynamicReports.stl.style().setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
	 																	.setBorder(DynamicReports.stl.pen1Point());
	 		StyleBuilder boldCenteredStyle = DynamicReports.stl.style(boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER); 
	 		StyleBuilder columnTitleStyle  = DynamicReports.stl.style(boldCenteredStyle).setBorder(DynamicReports.stl.pen1Point()).setBackgroundColor(Color.LIGHT_GRAY).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
	 		StyleBuilder titleStyle = DynamicReports.stl.style(boldCenteredStyle).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
	 				 .setFontSize(15);
	 		
	 		//create columns  "EfrDisTravelled","custName","customerSatisfactionScore","easyfixerId",
	 		TextColumnBuilder<Integer>    rowNumberColumn = DynamicReports.col.reportRowNumberColumn("No.").setFixedColumns(2).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   cityName      = DynamicReports.col.column("City", "cityName", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Integer>   jobId     = DynamicReports.col.column("Job Code", "jobId", DataTypes.integerType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   jobDesc = DynamicReports.col.column("Desc", "jobDesc", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		
	 		TextColumnBuilder<String>    collectedBy = DynamicReports.col.column("Collected By",new changeCollectedBy()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   custMobileNo = DynamicReports.col.column("Customer No", "custMobileNo", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		
	 		TextColumnBuilder<String>   custName = DynamicReports.col.column("Customer", "custName", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Integer>   easyfixerId     = DynamicReports.col.column("Easyfixer Id", "easyfixerId", DataTypes.integerType()).setStyle(centertextalignStyle);
	 		//"easyfixerName","clientName","servicetype","jobCreationDtae",
	 		TextColumnBuilder<String>   easyfixerName = DynamicReports.col.column("Easyfixer", "easyfixerName", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   clientName = DynamicReports.col.column("Client", "clientName", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   servicetype = DynamicReports.col.column("Service Type", "servicetype", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Date>   jobCreationDtae = DynamicReports.col.column("Created On","jobCreationDtae", DataTypes.dateYearToSecondType()).setStyle(centertextalignStyle).setPattern("DD-MMM-yy");
	 		//Columns.column("Completion Time", "checkout_date_time", DataTypes.dateYearToSecondType()).setStyle(centertextalignStyle).setPattern("DD-MMM-yy"),
	 		//"requestedOn","feedbackDate","chcekoutTime","noofrejection"
	 		TextColumnBuilder<Date>   requestedOn = DynamicReports.col.column("Requested On","requestedOn", DataTypes.dateYearToSecondType()).setStyle(centertextalignStyle).setPattern("DD-MMM-yy HH:mm:ss");
	 		TextColumnBuilder<Date>   feedbackDate = DynamicReports.col.column("Feedback On","feedbackDate", DataTypes.dateYearToSecondType()).setStyle(centertextalignStyle).setPattern("DD-MMM-yy HH:mm:ss");
	 		TextColumnBuilder<Date>   chcekoutTime = DynamicReports.col.column("Chcekout On","chcekoutTime", DataTypes.dateYearToSecondType()).setStyle(centertextalignStyle).setPattern("DD-MMM-yy HH:mm:ss");
	 	
	 		TextColumnBuilder<String>    Status = DynamicReports.col.column("Status",new chageJobStatustoString()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   JobRefId      = DynamicReports.col.column("Client Ref ID", "clientRefId", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   EFRMobileNo      = DynamicReports.col.column("EFR Phone", "EFRNo", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   rescheduleReason      = DynamicReports.col.column("Reschedule Reason", "rescheduleReason", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		
	 		TextColumnBuilder<String>   commentOnReschedule      = DynamicReports.col.column("Reschedule Comment", "commentOnReschedule", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		
	 		TextColumnBuilder<Date>   rescheduleTime      = DynamicReports.col.column("Reschedule Time", "rescheduleTime", DataTypes.dateYearToSecondType()).setStyle(centertextalignStyle).setPattern("DD-MMM-yy HH:mm:ss");
	 		
	 		TextColumnBuilder<String>   rescheduleBy      = DynamicReports.col.column("Reschedule by", "rescheduleBy", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		
	 		
	 		// build report
	 		JasperReportBuilder report = DynamicReports.report();
	 		report.setColumnTitleStyle(columnTitleStyle)
	 		.addProperty("net.sf.jasperreports.export.xlsx.wrap.text", "false")
	 		.fields(
					DynamicReports.field("collectedBy", Integer.class),
					DynamicReports.field("EfrDisTravelled", String.class),
					DynamicReports.field("jobStatus", Integer.class),
					DynamicReports.field("clientName", String.class)
					
					)
	 		.ignorePagination()
			.ignorePageWidth()
			//.columns(
				//	Columns.column("Registration Date", "registrationDate", DataTypes.dateYearToSecondType()).setStyle(centertextalignStyle).setPattern("DD-MMM-yy")
			//		)
			// "rescheduleReason","commentOnReschedule","rescheduleTime","rescheduleBy"
			.addProperty(JasperProperty.PRINT_KEEP_FULL_TEXT, "true")
			.columns(rowNumberColumn,cityName,jobId,jobDesc,rescheduleReason,commentOnReschedule,rescheduleTime,rescheduleBy,
					JobRefId,Status,jobCreationDtae,requestedOn,feedbackDate,chcekoutTime,custName,custMobileNo,
					easyfixerId,easyfixerName,EFRMobileNo,servicetype,clientName,collectedBy)
			
			.setDataSource(createDataSource())
			.toXlsx(xlsExporter);
	 	//	report.show();
	 		reportFile=new FileInputStream(jobReport);
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 return reportFile;
	 }

	 private FileInputStream buildReport(){
		 FileInputStream reportFile=null;
		 try{
			 File jobReport =new File("jobReport.xlsx");
			 jobReport.createNewFile();
	         JasperXlsxExporterBuilder xlsExporter = Exporters.xlsxExporter(jobReport)
	              .setDetectCellType(true)
	              .setIgnorePageMargins(true)
	              .setWhitePageBackground(false)
	              .setRemoveEmptySpaceBetweenColumns(true);
	       
	         //styles
	 		StyleBuilder boldStyle         = DynamicReports.stl.style().bold(); 
	 		StyleBuilder centertextalignStyle         = DynamicReports.stl.style().setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
	 																	.setBorder(DynamicReports.stl.pen1Point());
	 		StyleBuilder boldCenteredStyle = DynamicReports.stl.style(boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER); 
	 		StyleBuilder columnTitleStyle  = DynamicReports.stl.style(boldCenteredStyle).setBorder(DynamicReports.stl.pen1Point()).setBackgroundColor(Color.LIGHT_GRAY).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
	 		StyleBuilder titleStyle = DynamicReports.stl.style(boldCenteredStyle).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
	 				 .setFontSize(15);
	 		
	 		//create columns  "EfrDisTravelled","custName","customerSatisfactionScore","easyfixerId",
	 		TextColumnBuilder<Integer>    rowNumberColumn = DynamicReports.col.reportRowNumberColumn("No.").setFixedColumns(2).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   cityName      = DynamicReports.col.column("City", "cityName", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Integer>   jobId     = DynamicReports.col.column("Job Code", "jobId", DataTypes.integerType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   jobDesc = DynamicReports.col.column("Desc", "jobDesc", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Integer>   totalCharge = DynamicReports.col.column("Total Charge", "totalCharge", DataTypes.integerType()).setStyle(centertextalignStyle);
	 		
	 		 TextColumnBuilder<Float>    EF_share = DynamicReports.col.column("EF Share","EF_share",DataTypes.floatType()).setStyle(centertextalignStyle);
	 		 TextColumnBuilder<Float> EFR_share = DynamicReports.col.column("EFR Share","EFR_share",DataTypes.floatType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Float> client_share = DynamicReports.col.column("Client Share","client_share",DataTypes.floatType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Integer>   material_charge = DynamicReports.col.column("Material Charge", "material_charge", DataTypes.integerType()).setStyle(centertextalignStyle);
	 		
	 		TextColumnBuilder<Float>   completionTAT = DynamicReports.col.column("completion TAT",new secondToHrcompletionTAT()).setStyle(centertextalignStyle);
	 		 TextColumnBuilder<Float>   scheduledTAT      = DynamicReports.col.column("scheduled TAT",new secondToHrscheduledTAT()).setStyle(centertextalignStyle);
	 		 TextColumnBuilder<Float>    checkinTAT     = DynamicReports.col.column("checkin TAT",new secondToHrcheckinTAT()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>    collectedBy = DynamicReports.col.column("Collected By",new changeCollectedBy()).setStyle(centertextalignStyle);
	 		
	 		TextColumnBuilder<Float>   EfrDisTravelled =  DynamicReports.col.column("Dis Travelled",new changediatance()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   custName = DynamicReports.col.column("Customer", "custName", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Integer>   customerSatisfactionScore     = DynamicReports.col.column("Customer Rating", "customerSatisfactionScore", DataTypes.integerType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Integer>   easyfixerId     = DynamicReports.col.column("Easyfixer Id", "easyfixerId", DataTypes.integerType()).setStyle(centertextalignStyle);
	 		//"easyfixerName","clientName","servicetype","jobCreationDtae",
	 		TextColumnBuilder<String>   easyfixerName = DynamicReports.col.column("Easyfixer", "easyfixerName", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   clientName = DynamicReports.col.column("Client", "clientName", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   servicetype = DynamicReports.col.column("Service Type", "servicetype", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Date>   jobCreationDtae = DynamicReports.col.column("Created On","jobCreationDtae", DataTypes.dateYearToSecondType()).setStyle(centertextalignStyle).setPattern("DD-MMM-yy");
	 		//Columns.column("Completion Time", "checkout_date_time", DataTypes.dateYearToSecondType()).setStyle(centertextalignStyle).setPattern("DD-MMM-yy"),
	 		//"requestedOn","feedbackDate","chcekoutTime","noofrejection"
	 		TextColumnBuilder<Date>   requestedOn = DynamicReports.col.column("Requested On","requestedOn", DataTypes.dateYearToSecondType()).setStyle(centertextalignStyle).setPattern("DD-MMM-yy HH:mm:ss");
	 		TextColumnBuilder<Date>   feedbackDate = DynamicReports.col.column("Feedback On","feedbackDate", DataTypes.dateYearToSecondType()).setStyle(centertextalignStyle).setPattern("DD-MMM-yy HH:mm:ss");
	 		TextColumnBuilder<Date>   chcekoutTime = DynamicReports.col.column("Chcekout On","chcekoutTime", DataTypes.dateYearToSecondType()).setStyle(centertextalignStyle).setPattern("DD-MMM-yy HH:mm:ss");
	 		TextColumnBuilder<Integer>   noofrejection     = DynamicReports.col.column("noofrejection","noofrejection" ,DataTypes.integerType()).setStyle(centertextalignStyle);
	 		
	 		TextColumnBuilder<String>    Status = DynamicReports.col.column("Status",new chageJobStatustoString()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   JobRefId      = DynamicReports.col.column("Client Ref ID", "clientRefId", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   scheduleComment      = DynamicReports.col.column("Comment on Scheduling", "scheduleComment", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   checkinComment      = DynamicReports.col.column("Comment on Checkin", "checkinComment", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   checkoutComment      = DynamicReports.col.column("Comment on Checkout", "chekoutComment", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   feedbackComment      = DynamicReports.col.column("Comment on Feedback", "feedbackComment", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   cancelComment      = DynamicReports.col.column("Comment on cancel", "cancelComment", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   cancleReason      = DynamicReports.col.column("Cancel Reason", "cancleReason", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   enquiryReason      = DynamicReports.col.column("Enquiry Reason", "enquiryReason", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   commentOnEnquiry      = DynamicReports.col.column("comment On Enquiry", "commentOnEnquiry", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   EFRMobileNo      = DynamicReports.col.column("EFR Phone", "EFRNo", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   address      = DynamicReports.col.column("Address", "address", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   pincode      = DynamicReports.col.column("Pin Code", "pincode", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Date>   cancelDate = DynamicReports.col.column("cancelled On","cancelDate", DataTypes.dateYearToSecondType()).setStyle(centertextalignStyle).setPattern("DD-MMM-yy HH:mm:ss");
	 		
	 		TextColumnBuilder<String>   scheduledBy      = DynamicReports.col.column("schedule By", "scheduledBy", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   checkinBy      = DynamicReports.col.column("checkIn By", "checkinBy", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   checkoutBy      = DynamicReports.col.column("checkOut By", "checkoutBy", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   custMobileNo = DynamicReports.col.column("Customer No", "custMobileNo", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   jobDocs = DynamicReports.col.column("Job Docs", new changeImageUrl()).setStyle(centertextalignStyle);
	 		
	 		TextColumnBuilder<String>   appRejected = DynamicReports.col.column("App Rejected","appRejected",  DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   appCancelled = DynamicReports.col.column("App cancelled","appCancelled",  DataTypes.stringType()).setStyle(centertextalignStyle);
	 		
	 		// add charts
			 Bar3DChartBuilder chargeChart = DynamicReports.cht.bar3DChart()
					 	    .setTitle("Total Charge By City")
					 	    .setCategory(cityName)
					 	    .addSerie(
							DynamicReports.cht.serie(totalCharge),
							DynamicReports.cht.serie(totalCharge),
							DynamicReports.cht.serie(EFR_share));
			 
			 Bar3DChartBuilder timeChart = DynamicReports.cht.bar3DChart()
				 	    .setTitle("Time information by City")
				 	    .setCategory(cityName)
				 	    .addSerie(
						DynamicReports.cht.serie(completionTAT),
						DynamicReports.cht.serie(scheduledTAT),
						DynamicReports.cht.serie(checkinTAT)
						);
	 		// build report
	 		JasperReportBuilder report = DynamicReports.report();
	 		report.setColumnTitleStyle(columnTitleStyle)
	 		.addProperty("net.sf.jasperreports.export.xlsx.wrap.text", "false")
	 		.fields(
					DynamicReports.field("completionTAT", Long.class),
					DynamicReports.field("scheduledTAT", Long.class),
					DynamicReports.field("checkinTAT", Long.class),
					DynamicReports.field("collectedBy", Integer.class),
					DynamicReports.field("EfrDisTravelled", String.class),
					DynamicReports.field("jobStatus", Integer.class),
					DynamicReports.field("clientName", String.class),
					DynamicReports.field("jobDocs", String.class)
					)
	 		.ignorePagination()
			.ignorePageWidth()
			//.columns(
				//	Columns.column("Registration Date", "registrationDate", DataTypes.dateYearToSecondType()).setStyle(centertextalignStyle).setPattern("DD-MMM-yy")
			//		)
			.addProperty(JasperProperty.PRINT_KEEP_FULL_TEXT, "true")
			.columns(rowNumberColumn,cityName,jobId,jobDesc,JobRefId,Status,jobCreationDtae,requestedOn,feedbackDate,chcekoutTime,cancelDate,custName,custMobileNo,address
					,pincode,customerSatisfactionScore,easyfixerId,easyfixerName,EFRMobileNo,servicetype,clientName,totalCharge,
					EF_share,EFR_share,client_share,material_charge,completionTAT,scheduledTAT,checkinTAT,collectedBy,EfrDisTravelled,noofrejection,
					scheduleComment,checkinComment,checkoutComment,feedbackComment,cancelComment,cancleReason,enquiryReason,commentOnEnquiry,
					scheduledBy,checkinBy,checkoutBy,jobDocs,appRejected,appCancelled)
			.subtotalsAtSummary(DynamicReports.sbt.sum(totalCharge),
					DynamicReports.sbt.sum(EF_share),
					DynamicReports.sbt.sum(EFR_share),
					DynamicReports.sbt.sum(client_share),
					DynamicReports.sbt.sum(material_charge),
					DynamicReports.sbt.sum(EfrDisTravelled),
					DynamicReports.sbt.sum(noofrejection))
					.setSubtotalStyle(titleStyle)
			.summary(chargeChart,timeChart)
			.setDataSource(createDataSource())
			.toXlsx(xlsExporter);
	 	//	report.show();
	 		reportFile=new FileInputStream(jobReport);
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 return reportFile;
	 }

/*
	@Override
	public FileInputStream downloadCompletedJobReport(String start, String end, int flag, int client)
			throws Exception {
		File jobReport=null;
		FileInputStream reportFile=null;
		ResultSet rs = completedJobsReportDao.downloadCompletedJobReport(start, end, flag, client);
		if(rs!=null){
		JasperReportBuilder report = DynamicReports.report();
		
		//styles
		StyleBuilder boldStyle         = DynamicReports.stl.style().bold(); 
		StyleBuilder centertextalignStyle         = DynamicReports.stl.style().setHorizontalTextAlignment(HorizontalTextAlignment.CENTER)
																	.setBorder(DynamicReports.stl.pen1Point());
		StyleBuilder boldCenteredStyle = DynamicReports.stl.style(boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER); 
		StyleBuilder columnTitleStyle  = DynamicReports.stl.style(boldCenteredStyle).setBorder(DynamicReports.stl.pen1Point()).setBackgroundColor(Color.LIGHT_GRAY);
		StyleBuilder titleStyle = DynamicReports.stl.style(boldCenteredStyle).setVerticalTextAlignment(VerticalTextAlignment.MIDDLE)
				 .setFontSize(15);
		
		//make columns
		 TextColumnBuilder<String>   cityColumn      = DynamicReports.col.column("City", "city_name", DataTypes.stringType()).setStyle(centertextalignStyle);
		 TextColumnBuilder<Integer>   totalChargeColumn      = DynamicReports.col.column("Total Charge", "total_charge", DataTypes.integerType()).setStyle(centertextalignStyle);
		 TextColumnBuilder<Float>   efChargeColumn      = DynamicReports.col.column("EF Share", "easyfix_charge", DataTypes.floatType()).setStyle(centertextalignStyle);
		 TextColumnBuilder<Float>   efrChargeColumn      = DynamicReports.col.column("EFR Share", "easyfixer_charge", DataTypes.floatType()).setStyle(centertextalignStyle);
		 TextColumnBuilder<Float>   completionTAT =       DynamicReports.col.column("completion TAT",new secondToHrcompletionTAT()).setStyle(centertextalignStyle);
		 TextColumnBuilder<Float>   scheduledTAT      = DynamicReports.col.column("scheduled TAT",new secondToHrscheduledTAT()).setStyle(centertextalignStyle);
		 TextColumnBuilder<Float>    checkinTAT     = DynamicReports.col.column("checkin TAT",new secondToHrcheckinTAT()).setStyle(centertextalignStyle);
		 // TextColumnBuilder<Long>   completionTAT      = DynamicReports.col.column("completion TAT", "completionTAT", DataTypes.longType()).setStyle(centertextalignStyle);
		// TextColumnBuilder<Long>   checkinTAT      = DynamicReports.col.column("checkin TAT", "checkinTAT", DataTypes.longType()).setStyle(centertextalignStyle);
		// TextColumnBuilder<Long>   scheduledTAT      = DynamicReports.col.column("scheduled TAT", "scheduledTAT", DataTypes.longType()).setStyle(centertextalignStyle);
		 TextColumnBuilder<Integer>    rowNumberColumn = DynamicReports.col.reportRowNumberColumn("No.").setFixedColumns(2).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
		 TextColumnBuilder<String>    collectedBy = DynamicReports.col.column("Collected By",new changeCollectedBy()).setStyle(centertextalignStyle);
		 TextColumnBuilder<Float>    dis = DynamicReports.col.column("Dis Travelled",new changediatance()).setStyle(centertextalignStyle);
		
		 
		 // add charts
		 Bar3DChartBuilder chargeChart = DynamicReports.cht.bar3DChart()
				 	    .setTitle("Total Charge By City")
				 	    .setCategory(cityColumn)
				 	    .addSerie(
						DynamicReports.cht.serie(totalChargeColumn),
						DynamicReports.cht.serie(efChargeColumn),
						DynamicReports.cht.serie(efrChargeColumn));
		 
		 Bar3DChartBuilder timeChart = DynamicReports.cht.bar3DChart()
			 	    .setTitle("Time information by City")
			 	    .setCategory(cityColumn)
			 	    .addSerie(
					DynamicReports.cht.serie(completionTAT),
					DynamicReports.cht.serie(scheduledTAT),
					DynamicReports.cht.serie(checkinTAT)
					);
		 //add columns to report
		report.columns(
				rowNumberColumn,
				cityColumn,
		//		checkinTAT,
		//		scheduledTAT,
		//		completionTAT,
				//Columns.column("City", "city_name", DataTypes.stringType()).setStyle(centertextalignStyle),
				Columns.column("Job Code", "job_id", DataTypes.integerType()).setStyle(centertextalignStyle),
				Columns.column("Customer Name", "customer_name", DataTypes.stringType()).setStyle(centertextalignStyle),

		//		Columns.column("completed jobs ", "job_completed", DataTypes.integerType()).setStyle(centertextalignStyle),

				Columns.column("Customer Rating", "customer_rating", DataTypes.integerType()).setStyle(centertextalignStyle),
				Columns.column("EFR Code", "fk_easyfixter_id", DataTypes.integerType()).setStyle(centertextalignStyle),
				Columns.column("EFR Name", "efr_name", DataTypes.stringType()).setStyle(centertextalignStyle),
				dis,
				//Columns.column("Dis Travelled", "Efr_dis_travelled", DataTypes.stringType()).setStyle(centertextalignStyle),
				Columns.column("Client", "client_name", DataTypes.stringType()).setStyle(centertextalignStyle),
				Columns.column("Service Type", "service_type_name", DataTypes.stringType()).setStyle(centertextalignStyle),
				Columns.column("Created On", "created_date_time", DataTypes.dateYearToSecondType()).setStyle(centertextalignStyle).setPattern("DD-MMM-yy"),
				Columns.column("Requested On", "requested_date_time", DataTypes.dateYearToSecondType()).setStyle(centertextalignStyle).setPattern("DD-MMM-yy"),
				Columns.column("Feedback On", "feedback_date_time", DataTypes.dateYearToSecondType()).setStyle(centertextalignStyle).setPattern("DD-MMM-yy"),
				Columns.column("Completion Time", "checkout_date_time", DataTypes.dateYearToSecondType()).setStyle(centertextalignStyle).setPattern("DD-MMM-yy"),			
				Columns.column("scheduled TAT",new secondToHrscheduledTAT()).setStyle(centertextalignStyle),
				
				completionTAT,
				scheduledTAT,
				checkinTAT,
				//Columns.column("checkin TAT",new secondToHrcheckinTAT()).setStyle(centertextalignStyle),
				//Columns.column("completion TAT",new secondToHrcompletionTAT()).setStyle(centertextalignStyle),
				//Columns.column("EF Share", "easyfix_charge", DataTypes.floatType()).setStyle(centertextalignStyle),
				efChargeColumn,
				efrChargeColumn,
				//Columns.column("EFR Share", "easyfixer_charge", DataTypes.floatType()).setStyle(centertextalignStyle),
				Columns.column("Client Share", "client_charge", DataTypes.floatType()).setStyle(centertextalignStyle),
				Columns.column("Matrial Charge", "material_charge", DataTypes.integerType()).setStyle(centertextalignStyle),
				totalChargeColumn,
				//Columns.column("Total Charge", "total_charge", DataTypes.integerType()).setStyle(centertextalignStyle),
				//Columns.column("Collected By", "collected_by", DataTypes.integerType()).setStyle(centertextalignStyle)
				collectedBy,
				Columns.column("No Of Rejections", "noofrejection", DataTypes.integerType()).setStyle(centertextalignStyle)
				)
				 .title(//title of the report
				      Components.text("job report").setStyle(titleStyle))
				     
			.setColumnTitleStyle(columnTitleStyle)
			.highlightDetailEvenRows() 
			.addProperty("net.sf.jasperreports.export.xlsx.wrap.text", "false")
			.subtotalsAtSummary(DynamicReports.sbt.sum(totalChargeColumn),
					DynamicReports.sbt.sum(efChargeColumn),
					DynamicReports.sbt.sum(efrChargeColumn))
					.setSubtotalStyle(titleStyle)
			.summary(chargeChart,timeChart)
			//.groupBy(cityColumn)
			.fields(
					DynamicReports.field("completionTAT", Long.class),
					DynamicReports.field("checkinTAT", Long.class),
					DynamicReports.field("scheduledTAT", Long.class),
					DynamicReports.field("collected_by",Integer.class),
					DynamicReports.field("Efr_dis_travelled",String.class)
					)
					
			.sortBy(cityColumn)
			.ignorePagination()
			.ignorePageWidth()
			.addProperty(JasperProperty.PRINT_KEEP_FULL_TEXT, "true") // show all content of row
			
			
			//.addProperty(JRParameter.IS_IGNORE_PAGINATION, true)
			
			.pageFooter(Components.pageXofY()) 
			.setDataSource(rs);
				
		
		try{
			jobReport =new File("Myreport");
			jobReport.createNewFile();
	         JasperXlsxExporterBuilder xlsxExporter = DynamicReports.export.xlsxExporter(jobReport)
	                                                      .setDetectCellType(true)
	                                                      .setIgnorePageMargins(true)
	                                                      .setWhitePageBackground(false)
	                                                      .setRemoveEmptySpaceBetweenColumns(true);
//				 jobReport =new File("Myreport");
//				jobReport.createNewFile();
//			FileOutputStream out = new FileOutputStream(jobReport);
			report.toXlsx(xlsxExporter);
			}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			
			Statement cstmt = rs.getStatement();
			Connection conn = cstmt.getConnection();
			if(rs!=null)
				try{
				rs.close();
				}
				catch(SQLException e){
						}
			  if (cstmt != null) {
	                try {
	                	cstmt.close();
	                } catch (SQLException e) { /*ignored }
	            }
	            
	            if (conn != null) {
	                try {
	                    conn.close();
	                } catch (SQLException e) { /*ignored }
	            }
			
		}
		 reportFile=new FileInputStream(jobReport);
		}
		else{
			System.out.println("no data");
		}
		
		return reportFile;
		
	}
	
	*/
	@Override
	public String convertStringToDate(String dateString) throws Exception {
		String formatteddate = null;
	    DateFormat rdf = new SimpleDateFormat( "dd MMM, yyyy");
	    DateFormat wdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date date = null;
	    try {
	    	date =  rdf.parse(dateString);
	   // 	System.out.println(date);
	     } catch ( ParseException e ) {
	         e.printStackTrace();
	     }
	    
	    if( date != null ) {
	    	formatteddate = wdf.format( date );
	    	
	    //	System.out.println(formatteddate);
	        }
	    
	    return formatteddate;
	}
	
	public static void main (String arg[]) throws Exception{
		CompletedJobsReportServiceImpl c = new CompletedJobsReportServiceImpl();
		c.convertStringToDate("20 Jan, 2016");
}
	
	

	public CompletedJobsReportDao getCompletedJobsReportDao() {
		return completedJobsReportDao;
	}

	public void setCompletedJobsReportDao(
			CompletedJobsReportDao completedJobsReportDao) {
		this.completedJobsReportDao = completedJobsReportDao;
	}
	/* private Style createHeaderStyle() {        
	        StyleBuilder sb=new StyleBuilder(true);
	        sb.setFont(Font.VERDANA_MEDIUM_BOLD);
	        sb.setBorder(Border.THIN());
	        sb.setBorderBottom(Border.PEN_2_POINT());
	        sb.setBorderColor(Color.BLACK);
	        sb.setBackgroundColor(Color.ORANGE);
	        sb.setTextColor(Color.BLACK);
	        sb.setHorizontalAlign(HorizontalAlign.CENTER);
	        sb.setVerticalAlign(VerticalAlign.MIDDLE);
	        sb.setTransparency(Transparency.OPAQUE);        
	        return sb.build();
	    }
*/
	public List<CompletedJobsReport> getJobList() {
		return jobList;
	}
	public void setJobList(List<CompletedJobsReport> jobList) {
		this.jobList = jobList;
	}

	
}
class secondToHrcompletionTAT extends AbstractSimpleExpression<Float> {
		private static final long serialVersionUID = 1L;

	public Float evaluate(ReportParameters reportParameters) {
		long time =0;
		Long localTime=null;
		try{
			localTime = reportParameters.getValue("completionTAT");
			  if(localTime!=null)
			    	 time=localTime.longValue();
		}
	    catch(Exception e){
	    	System.out.println(e);
	    }
	     return (float)(Math.abs(time/3600));
	  }

	}
class secondToHrcheckinTAT extends AbstractSimpleExpression<Float> {
	 
	private static final long serialVersionUID = 1L;

	public Float evaluate(ReportParameters reportParameters) {
		long time=0;
		Long localtime=null;
		try{
			localtime = reportParameters.getValue("checkinTAT");
	     if(localtime!=null)
	    	 time=localtime.longValue();
	    	 
		}
		catch(Exception e){
		System.out.println(e);	
		}
	     return (float)(Math.abs(time/3600));
	  }

	}
class secondToHrscheduledTAT extends AbstractSimpleExpression<Float> {
	
	private static final long serialVersionUID = 1L;
	
	public Float evaluate(ReportParameters reportParameters) {
		long time =0 ;
		Long localTime = null;
		try{
			localTime = reportParameters.getValue("scheduledTAT");
			 if(localTime!=null)
		    	 time=localTime.longValue();
		}
		catch(Exception e){
			System.out.println(e);
		}
	     return (float)(Math.abs(time/3600));
	  }

	}
class changeCollectedBy extends AbstractSimpleExpression<String> {
	
	private static final long serialVersionUID = 1L;
	
	public String evaluate(ReportParameters reportParameters) {
		String collectedBy="";
		try{
		int collector = reportParameters.getValue("collectedBy");
			 if(collector==1)
				 collectedBy="Easyfixer";
			 else if(collector==2)
				 collectedBy = "Easyfix";
			 else if(collector==3)
				 collectedBy =reportParameters.getValue("clientName");
			 else
			 collectedBy = "NotYetCollected";
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return collectedBy;
	  }

	}
class changediatance extends AbstractSimpleExpression<Float> {
	
	private static final long serialVersionUID = 1L;
	
	public Float evaluate(ReportParameters reportParameters) {
		Float dis=0f;
		try{
		String dist = reportParameters.getValue("EfrDisTravelled");
			if(dis!=null)
				dis = (float)(java.lang.Float.parseFloat(dist));
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return dis;
	  }

	}
	class chageJobStatustoString extends AbstractSimpleExpression<String> {
		
		private static final long serialVersionUID = 1L;
		
		public String evaluate(ReportParameters reportParameters) {
			int status=0;
			String localJobStatus ="";
			try{
			 status = reportParameters.getValue("jobStatus");
			switch(status){
			case 0: localJobStatus ="Created"; break;
			case 1: localJobStatus="Scheduled";break;
			case 2: localJobStatus="CheckedIn";break;
			case 3: localJobStatus="CheckedOut";break;
			
			case 4: localJobStatus="Feedback";break;
			case 5: localJobStatus="Completed";break;
			case 6: localJobStatus="Canceled";break;
			case 7: localJobStatus="Enquiry";break;
			case 8: localJobStatus="Paused";break;
			case 9: localJobStatus="callLater";break;
			case 10: localJobStatus="AppCheckoutVerification";break;
			default: localJobStatus="No Status"; break;
			}
			
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return localJobStatus;
		  }

		}
	
	class changeImageUrl extends AbstractSimpleExpression<String> {
		
		private static final long serialVersionUID = 1L;
		
		public String evaluate(ReportParameters reportParameters) {
			String url="NA";
			try{
			String urlFromDb = reportParameters.getValue("jobDocs");
			//System.out.println("urlFromDb:"+urlFromDb);
				 if(urlFromDb!=null && urlFromDb.contains("/var/www/html")){
					 url = urlFromDb.replace("/var/www/html", "https://core.easyfix.in");
				 }
			}
			catch(Exception e){
				e.printStackTrace();
			}
			//System.out.println("url"+url);
			return url;
		  }

		}
	

