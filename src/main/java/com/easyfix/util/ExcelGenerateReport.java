package com.easyfix.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HeaderFooter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Footer;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.easyfix.tracking.model.Tracking;

public class ExcelGenerateReport {


	 private CellStyle cs = null;
	 private CellStyle csBold = null;
	 private CellStyle csTop = null;
	 private CellStyle csRight = null;
	 private CellStyle csBottom = null;
	 private CellStyle csLeft = null;
	 private CellStyle csTopLeft = null;
	 private CellStyle csTopRight = null;
	 private CellStyle csBottomLeft = null;
	 private CellStyle csBottomRight = null;
	 private CellStyle csHeading = null; 
	 
	 private String uploadPath = "/var/www/html/easydoc/upload_jobs/";
	 //private String uploadPath = "O:\\ef_documents\\";
	 
	 private void setCellStyles(Workbook wb) {

		  //font size 10
		  Font f = wb.createFont();
		  f.setFontHeightInPoints((short) 10);

		  //Simple style 
		  cs = wb.createCellStyle();
		  cs.setFont(f);

		  //Bold Fond
		  Font bold = wb.createFont();
		  bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		  bold.setFontHeightInPoints((short) 10);

		  //Heading style 
		  csHeading = wb.createCellStyle();
		  csHeading.setBorderTop(CellStyle.BORDER_THIN);
		  csHeading.setBorderBottom(CellStyle.BORDER_THIN);
		  csHeading.setTopBorderColor(IndexedColors.BLACK.getIndex());
		  csHeading.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		  csHeading.setFillBackgroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		  csHeading.setFont(bold);
		  
		//Bold style 
		  csBold = wb.createCellStyle();
		  csBold.setBorderBottom(CellStyle.BORDER_THIN);
		  csBold.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		  csBold.setFont(bold);
		  

		  //Setup style for Top Border Line
		  csTop = wb.createCellStyle();
		  csTop.setBorderTop(CellStyle.BORDER_THIN);
		  csTop.setTopBorderColor(IndexedColors.BLACK.getIndex());
		  csTop.setFont(f);

		  //Setup style for Right Border Line
		  csRight = wb.createCellStyle();
		  csRight.setBorderRight(CellStyle.BORDER_THIN);
		  csRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		  csRight.setFont(f);

		  //Setup style for Bottom Border Line
		  csBottom = wb.createCellStyle();
		  csBottom.setBorderBottom(CellStyle.BORDER_THIN);
		  csBottom.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		  csBottom.setFont(f);

		  //Setup style for Left Border Line
		  csLeft = wb.createCellStyle();
		  csLeft.setBorderLeft(CellStyle.BORDER_THIN);
		  csLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		  csLeft.setFont(f);

		  //Setup style for Top/Left corner cell Border Lines
		  csTopLeft = wb.createCellStyle();
		  csTopLeft.setBorderTop(CellStyle.BORDER_THIN);
		  csTopLeft.setTopBorderColor(IndexedColors.BLACK.getIndex());
		  csTopLeft.setBorderLeft(CellStyle.BORDER_THIN);
		  csTopLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		  csTopLeft.setFont(f);

		  //Setup style for Top/Right corner cell Border Lines
		  csTopRight = wb.createCellStyle();
		  csTopRight.setBorderTop(CellStyle.BORDER_THIN);
		  csTopRight.setTopBorderColor(IndexedColors.BLACK.getIndex());
		  csTopRight.setBorderRight(CellStyle.BORDER_THIN);
		  csTopRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		  csTopRight.setFont(f);

		  //Setup style for Bottom/Left corner cell Border Lines
		  csBottomLeft = wb.createCellStyle();
		  csBottomLeft.setBorderBottom(CellStyle.BORDER_THIN);
		  csBottomLeft.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		  csBottomLeft.setBorderLeft(CellStyle.BORDER_THIN);
		  csBottomLeft.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		  csBottomLeft.setFont(f);

		  //Setup style for Bottom/Right corner cell Border Lines
		  csBottomRight = wb.createCellStyle();
		  csBottomRight.setBorderBottom(CellStyle.BORDER_THIN);
		  csBottomRight.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		  csBottomRight.setBorderRight(CellStyle.BORDER_THIN);
		  csBottomRight.setRightBorderColor(IndexedColors.BLACK.getIndex());
		  csBottomRight.setFont(f);

		 }
	
	 /*public static void main(String[] args) {

		  ExcelGenerateReport myReport = new ExcelGenerateReport();
		  myReport.createExcel();

		 }*/
	 
	 public File createExcel(String fileName,List<String> headerList, List<Tracking> dataInfoList, String flag) throws Exception {
		 
		 File reportFile=null;

		  try{

		   Workbook wb = new XSSFWorkbook();
		  // Workbook wb = new HSSFWorkbook();
		   
		   Sheet sheet = wb.createSheet(flag);
		   
		 //Set Column Widths
		 /*  sheet.setColumnWidth(0, 3000); 
		   sheet.setColumnWidth(1, 2500);
		   sheet.setColumnWidth(2, 2500);
		   sheet.setColumnWidth(3, 2500);
		   sheet.setColumnWidth(4, 2500);*/

		   //Setup some styles that we need for the Cells
		   setCellStyles(wb);

		   //Get current Date and Time
		   Date date = new Date(System.currentTimeMillis());
		   DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");

		   //Setup the Page margins - Left, Right, Top and Bottom
		   sheet.setMargin(Sheet.LeftMargin, 0.25);
		   sheet.setMargin(Sheet.RightMargin, 0.25);
		   sheet.setMargin(Sheet.TopMargin, 0.75);
		   sheet.setMargin(Sheet.BottomMargin, 0.75);

		   //Setup the Header and Footer Margins
		   sheet.setMargin(Sheet.HeaderMargin, 0.25);
		   sheet.setMargin(Sheet.FooterMargin, 0.25);

		   //Set Header Information 
		   Header header = sheet.getHeader();
		   header.setLeft("*** ORIGINAL COPY ***");
		   header.setCenter(HSSFHeader.font("Arial", "Bold") +
		     HSSFHeader.fontSize((short) 14) + "SAMPLE ORDER");
		   header.setRight(df.format(date));

		   //Set Footer Information with Page Numbers
		   Footer footer = sheet.getFooter();
		   footer.setRight( "Page " + HeaderFooter.page() + " of " + 
		     HeaderFooter.numPages() );


		   int rowIndex = 0;
		   
		   if(flag.equalsIgnoreCase("userLogging") ) {
			   rowIndex = insertLoggingHeaderInfo(sheet, rowIndex, headerList);
			   rowIndex = insertLoggingDetailInfo(sheet, rowIndex, headerList, dataInfoList);		   
		   }
		   else if(flag.equalsIgnoreCase("clientTracking")) {
			   rowIndex = insertTrackingHeader(sheet, rowIndex, flag);
			   rowIndex = insertTrackingDetail(sheet, rowIndex, dataInfoList, flag);
		   }
		   else if(flag.equalsIgnoreCase("cityTracking")) {
			   rowIndex = insertTrackingHeader(sheet, rowIndex, flag);
			   rowIndex = insertTrackingDetail(sheet, rowIndex, dataInfoList, flag);
		   }
		   else if(flag.equalsIgnoreCase("jobTracking")) {
			   rowIndex = insertTrackingHeader(sheet, rowIndex, flag);
			   rowIndex = insertTrackingDetail(sheet, rowIndex, dataInfoList, flag);
		   }
		   
		   else {
			  /* rowIndex = insertHeaderInfo(sheet, rowIndex);
			   rowIndex = insertDetailInfo(sheet, rowIndex);*/
		   }

		  		   
		   reportFile = new File(uploadPath+fileName);
		   reportFile.createNewFile();
		   
		 //Write the Excel file
		  
		   FileOutputStream fileOut = new FileOutputStream(reportFile);
		   wb.write(fileOut);
		   fileOut.close();
		  
		   
		  }
		  catch (Exception e) {
		   e.printStackTrace();
		  }
		  
		  return reportFile;
	 }
	 
private int insertTrackingHeader(Sheet sheet, int index, String flag){

	  int rowIndex = index;
	  Row row = null;
	  Cell c = null;
	  
	//Set Column Widths
	   sheet.setColumnWidth(0, 4000); 
	   sheet.setColumnWidth(1, 3000);
	   sheet.setColumnWidth(2, 3000);
	   sheet.setColumnWidth(3, 3000);
	   sheet.setColumnWidth(4, 3000);
	   sheet.setColumnWidth(5, 3000); 
	   sheet.setColumnWidth(6, 3000);
	   sheet.setColumnWidth(7, 3000);
	   sheet.setColumnWidth(8, 3000);
	   sheet.setColumnWidth(9, 3000);
	   sheet.setColumnWidth(10, 3000);

	  row = sheet.createRow(rowIndex);	 
	  
	  if(flag.equalsIgnoreCase("jobTracking"))
	  {
		  c = row.createCell(0);
		  c.setCellValue("Employee");
		  c.setCellStyle(csHeading);		  
		  
		  c = row.createCell(1);	  
		  c.setCellValue("Booked");
		  c.setCellStyle(csHeading);
		  
		  c = row.createCell(2);
		  c.setCellValue("Scheduled");
		  c.setCellStyle(csHeading);
		  
		  c = row.createCell(3);
		  c.setCellValue("CheckIn");
		  c.setCellStyle(csHeading);
		  
		  c = row.createCell(4);
		  c.setCellValue("CheckOut");
		  c.setCellStyle(csHeading);
		  
		  c = row.createCell(5);
		  c.setCellValue("FeedBack");
		  c.setCellStyle(csHeading);
		  
		  c = row.createCell(6);
		  c.setCellValue("Cancelled");
		  c.setCellStyle(csHeading);
		  
		  c = row.createCell(7);
		  c.setCellValue("Inquiry");
		  c.setCellStyle(csHeading);
		  
		  c = row.createCell(8);
		  c.setCellValue("Missed calls");
		  c.setCellStyle(csHeading);
		  
	  }
	  
	  else
	  {
		  c = row.createCell(0);	  
		  if(flag.equalsIgnoreCase("clientTracking"))
			  c.setCellValue("Client");
		  if(flag.equalsIgnoreCase("cityTracking"))
			  c.setCellValue("City");
		  
		  c.setCellStyle(csHeading);	 
		
		  c = row.createCell(1);	  
		  c.setCellValue("Jobs");
		  c.setCellStyle(csHeading);
		  
		  c = row.createCell(2);
		  c.setCellValue("Finished");
		  c.setCellStyle(csHeading);
		  
		  c = row.createCell(3);
		  c.setCellValue("Canceled");
		  c.setCellStyle(csHeading);
		  
		  c = row.createCell(4);
		  c.setCellValue("Billing");
		  c.setCellStyle(csHeading);
		  
		  c = row.createCell(5);
		  c.setCellValue("EF Share");
		  c.setCellStyle(csHeading);
		  
		  c = row.createCell(6);
		  c.setCellValue("Client Share");
		  c.setCellStyle(csHeading);
		  
		  c = row.createCell(7);
		  c.setCellValue("Material");
		  c.setCellStyle(csHeading);
		  
		  c = row.createCell(8);
		  c.setCellValue("Conversion %");
		  c.setCellStyle(csHeading);
		  
		  c = row.createCell(9);
		  c.setCellValue("Rating");
		  c.setCellStyle(csHeading);
		  
		  c = row.createCell(10);
		  c.setCellValue("% Margin");
		  c.setCellStyle(csHeading);
		  
		  c = row.createCell(11);
		  c.setCellValue("Avg. Ticket");
		  c.setCellStyle(csHeading);
	  }
	  
	  rowIndex++;

  return rowIndex;

 }

private int insertTrackingDetail(Sheet sheet, int index, List<Tracking> dataInfoList, String flag){

	 int rowIndex = 0;
	  Row row = null;
	  Cell c = null;
	  
	  int i = 0;  
	  float t1 = 0;   float t2 = 0;   float t3 = 0;  float t4 = 0;  float t5 = 0;   float t6 = 0;  float t7 = 0;	
	  for(Tracking t : dataInfoList) {
			 rowIndex = index + i;
			 row = sheet.createRow(rowIndex);			 
			 
			 if(flag.equalsIgnoreCase("jobTracking"))
			 {
				 c = row.createCell(0);
				 c.setCellValue(t.getUserName());				 
				 c.setCellStyle(cs);
				 
				 c = row.createCell(1);
				 c.setCellValue(t.getJobCreatedBy());
				 c.setCellStyle(cs);
				 
				 c = row.createCell(2);
				 c.setCellValue(t.getJobScheduledBy());
				 c.setCellStyle(cs);
				 
				 c = row.createCell(3);
				 c.setCellValue(t.getJobCheckInBy());
				 c.setCellStyle(cs);
				 
				 c = row.createCell(4);
				 c.setCellValue(t.getJobCheckOutBy());
				 c.setCellStyle(cs);
				 
				 c = row.createCell(5);
				 c.setCellValue(t.getJobFeedbackBy());
				 c.setCellStyle(cs);
				 
				 c = row.createCell(6);
				 c.setCellValue(t.getJobCanceledBy());
				 c.setCellStyle(cs);
				 
				 c = row.createCell(7);
				 c.setCellValue(t.getJobEnquiryCount());
				 c.setCellStyle(cs);
				 
				 c = row.createCell(8);
				 c.setCellValue("--");
				 c.setCellStyle(cs);
			 }
			 else
			 {
				 t1 = t1 + t.getJobGiven();
				 t2 = t2 + t.getJobCompleted();
				 t3 = t3 + t.getJobCanceled();
				 t4 = (float) (t4 + t.getBilling());
				 t5 = t5 + t.getEfShare();
				 t6 = t6 + t.getClientShare();
				 t7 = t7 + t.getMaterial();
			 
				 c = row.createCell(0);
				 if(flag.equalsIgnoreCase("clientTracking"))
					 c.setCellValue(t.getClientName());
				 if(flag.equalsIgnoreCase("cityTracking"))
					 c.setCellValue(t.getCityName());
				 
				 c.setCellStyle(cs);
				 
				 c = row.createCell(1);
				 c.setCellValue(t.getJobGiven());
				 c.setCellStyle(cs);
				 
				 c = row.createCell(2);
				 c.setCellValue(t.getJobCompleted());
				 c.setCellStyle(cs);
				 
				 c = row.createCell(3);
				 c.setCellValue(t.getJobCanceled());
				 c.setCellStyle(cs);
				 
				 c = row.createCell(4);
				 c.setCellValue(t.getBilling());
				 c.setCellStyle(cs);
				 
				 c = row.createCell(5);
				 c.setCellValue(t.getEfShare());
				 c.setCellStyle(cs);
				 
				 c = row.createCell(6);
				 c.setCellValue(t.getClientShare());
				 c.setCellStyle(cs);
				 
				 c = row.createCell(7);
				 c.setCellValue(t.getMaterial());
				 c.setCellStyle(cs);
				 
				 c = row.createCell(8);
				 c.setCellValue(t.getConversion());
				 c.setCellStyle(cs);
				 
				 c = row.createCell(9);
				 c.setCellValue(t.getCustomerRating());
				 c.setCellStyle(cs);
				 
				 c = row.createCell(10);
				 c.setCellValue(t.getMargin());
				 c.setCellStyle(cs);
				 
				 c = row.createCell(11);
				 c.setCellValue(t.getAvgTicket());
				 c.setCellStyle(cs);
			 }
			 
		i++;
	  }
	  
	  //============ Total ====================
	  if(!flag.equalsIgnoreCase("jobTracking"))
		 {
		  	rowIndex = index + i;
			row = sheet.createRow(rowIndex);
			c = row.createCell(0);	  
			c.setCellValue("Total");
			c.setCellStyle(csHeading);
			
			c = row.createCell(1);	  
			c.setCellValue(t1);
			c.setCellStyle(csHeading);
			
			c = row.createCell(2);	  
			c.setCellValue(t2);
			c.setCellStyle(csHeading);
			
			c = row.createCell(3);
			c.setCellValue(t3);
			c.setCellStyle(csHeading);
			
			c = row.createCell(4);	  
			c.setCellValue(t4);
			c.setCellStyle(csHeading);
			
			c = row.createCell(5);	  
			c.setCellValue(t5);
			c.setCellStyle(csHeading);
			
			c = row.createCell(6);	  
			c.setCellValue(t6);
			c.setCellStyle(csHeading);
			
			c = row.createCell(7);	  
			c.setCellValue(t7);
			c.setCellStyle(csHeading);
			
			c = row.createCell(8);	  
			c.setCellValue("");
			c.setCellStyle(csHeading);
			
			c = row.createCell(9);	  
			c.setCellValue("");
			c.setCellStyle(csHeading);
			
			c = row.createCell(10);	  
			c.setCellValue("");
			c.setCellStyle(csHeading);
			
			c = row.createCell(11);	  
			c.setCellValue("");
			c.setCellStyle(csHeading);
		 }
		

  return rowIndex;

 }


	 
	 private int insertLoggingHeaderInfo(Sheet sheet, int index, List<String> columnDayList) {
		 
		 int rowIndex = index;
		  Row row = null;
		  Cell c = null;

		  row = sheet.createRow(rowIndex);
		  sheet.setColumnWidth(0, 4000);
		  
		  int colNo = 0;
		  c = row.createCell(colNo);
		  c.setCellValue("Employee");
		  c.setCellStyle(csBold);
		  
		  colNo = 1;
		  for(String s : columnDayList) {
			  
			  c = row.createCell(colNo);
			  c.setCellValue(s);
			  c.setCellStyle(csBold);
			  
			  colNo++;
		  }
		  
		  rowIndex++;
		  
		 return rowIndex;
	 }
	 
	 private int insertLoggingDetailInfo(Sheet sheet, int index, List<String> columnDayList, List<Tracking> userLoggingList) {
		 int rowIndex = 0;
		  Row row = null;
		  Cell c = null;
		  
		  int i = 0;
		  int uid = 0;
		  for(Tracking t : userLoggingList) {
			 if(uid != t.getUserId()) {
				 rowIndex = index + i;
				 row = sheet.createRow(rowIndex);
				 c = row.createCell(0);
				 c.setCellValue(t.getUserName());
				 c.setCellStyle(cs);
				 int colNo = 1;
				  for(String s : columnDayList) {	
					  c = row.createCell(colNo);
					  for(Tracking tl : userLoggingList) {
							 if(t.getUserId() == tl.getUserId() && s.equalsIgnoreCase(tl.getLoginDateTime()) ) {
								 c.setCellValue(tl.getActiveTime());
							 }
					  }
					  c.setCellStyle(cs);
					  
					  colNo++;
				  }
				  uid = t.getUserId();
				  i++;
			 }					 
			 
		  }

		  return rowIndex;
		} 
	 
	 
}
