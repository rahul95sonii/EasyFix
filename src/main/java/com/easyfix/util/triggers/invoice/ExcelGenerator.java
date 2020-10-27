package com.easyfix.util.triggers.invoice;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.easyfix.util.PropertyReader;
import com.easyfix.util.triggers.model.ClientInvoiceBean;




public class ExcelGenerator
{

	public static ExcelGenerator generateInvoiceExcel = null;

	public static ExcelGenerator getInstance()
	{
		generateInvoiceExcel = new ExcelGenerator();
		return generateInvoiceExcel;
	}

	private CellStyle cs = null;
	private CellStyle csBold = null;
	private CellStyle csHeading = null;
	private CellStyle csTitle = null;
	private CellStyle csSubTotal = null;
	private CellStyle csDetails = null;
	private CellStyle csNoRecord = null;
	private HashMap<String, HashMap<String, String>> summaryMap = new HashMap<String, HashMap<String, String>>();

	public void createInvoiceFiles(String masterDirName, String invoiceDirName, Map<String, List<ClientInvoiceBean>> serviceTypeJobMap,
			String billingName, String billingAddress, String startDate, String endDate, PropertyReader props) throws Exception
	{

		createExcel(masterDirName, serviceTypeJobMap);
		try{
			System.out.println(summaryMap);
			System.out.println(invoiceDirName);
			System.out.println(billingName);
			
			System.out.println(billingAddress);
			System.out.println(startDate);
			System.out.println(endDate);
			System.out.println(props);
			System.out.println(props.toString());
		PDFGenerator.createPdf(summaryMap, invoiceDirName, billingName, billingAddress, startDate, endDate, props);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		}

	public File createExcel(String fileName, Map<String, List<ClientInvoiceBean>> serviceTypeJobMap) throws Exception
	{
		System.out.println("in createExcel :"+fileName);
		File reportFile = null;
		try
		{
			Workbook wb = new XSSFWorkbook();
			Sheet sheet = wb.createSheet("Client Invoice");
			setCellStyles(wb);
			Date date = new Date(System.currentTimeMillis());
			DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");

			sheet.setMargin(Sheet.LeftMargin, 0.25);
			sheet.setMargin(Sheet.RightMargin, 0.25);
			sheet.setMargin(Sheet.TopMargin, 0.75);
			sheet.setMargin(Sheet.BottomMargin, 0.75);

			// Setup the Header and Footer Margins
			sheet.setMargin(Sheet.HeaderMargin, 0.25);
			sheet.setMargin(Sheet.FooterMargin, 0.25);

			// Set Header Information
			Header header = sheet.getHeader();
			header.setLeft("*** ORIGINAL COPY ***");
			header.setCenter(HSSFHeader.font("Arial", "Bold") + HSSFHeader.fontSize((short) 14) + "SAMPLE ORDER");
			header.setRight(df.format(date));

			// Set Footer Information with Page Numbers
			Footer footer = sheet.getFooter();
			footer.setRight("Page " + HeaderFooter.page() + " of " + HeaderFooter.numPages());

			int rowIndex = 0;

			rowIndex = insertInvoiceHeaderInfo(sheet, rowIndex, "Client Invoice");
			rowIndex = insertInvoiceDetailInfo(sheet, rowIndex, serviceTypeJobMap);

			System.out.println("trying to create Report file:"+fileName+".xlsx");
			try{
			reportFile = new File(fileName + ".xlsx");
			reportFile.createNewFile();
			System.out.println(reportFile.toString()+": file created succssfully");
			// Write the Excel file
			System.out.println("Report file: " + reportFile.toString());
			FileOutputStream fileOut = new FileOutputStream(reportFile);
			wb.write(fileOut);
			fileOut.close();
			}
			catch(Exception e){
				System.out.println(e.toString());
				e.printStackTrace();
				
			}

		}
		catch (Exception e)
		{
			System.out.println("e:::::::");
			e.printStackTrace();
		}

		return reportFile;
	}

	private int insertInvoiceHeaderInfo(Sheet sheet, int index, String flag)
	{

		int rowIndex = index;
		Row row = null;
		Cell c = null;

		row = sheet.createRow(rowIndex);
		row.setHeight((short) 700);

		sheet.setColumnWidth(0, 3000);
		c = row.createCell(0);
		c.setCellValue("Sl No.");
		c.setCellStyle(csHeading);

		sheet.setColumnWidth(1, 4000);
		c = row.createCell(1);
		c.setCellValue("Job Code");
		c.setCellStyle(csHeading);

		sheet.setColumnWidth(2, 5000);
		c = row.createCell(2);
		c.setCellValue("Job Description");
		c.setCellStyle(csHeading);

		sheet.setColumnWidth(3, 5000);
		c = row.createCell(3);
		c.setCellValue("Client Ref ID");
		c.setCellStyle(csHeading);

		sheet.setColumnWidth(4, 7000);
		c = row.createCell(4);
		c.setCellValue("Customer Name");
		c.setCellStyle(csHeading);

		sheet.setColumnWidth(5, 4000);
		c = row.createCell(5);
		c.setCellValue("City name");
		c.setCellStyle(csHeading);

		sheet.setColumnWidth(6, 4000);
		c = row.createCell(6);
		c.setCellValue("State");
		c.setCellStyle(csHeading);

		sheet.setColumnWidth(7, 6000);
		c = row.createCell(7);
		c.setCellValue("Checkout on (DD-MM-YY)");
		c.setCellStyle(csHeading);

		sheet.setColumnWidth(8, 10000);
		c = row.createCell(8);
		c.setCellValue("Product Name/ Description");
		c.setCellStyle(csHeading);

		sheet.setColumnWidth(9, 5000);
		c = row.createCell(9);
		c.setCellValue("Service Type");
		c.setCellStyle(csHeading);

		sheet.setColumnWidth(10, 4000);
		c = row.createCell(10);
		c.setCellValue("Status");
		c.setCellStyle(csHeading);

		sheet.setColumnWidth(11, 4000);
		c = row.createCell(11);
		c.setCellValue("Installation Charges");
		c.setCellStyle(csHeading);

		sheet.setColumnWidth(12, 4000);
		c = row.createCell(12);
		c.setCellValue("Repair Charges");
		c.setCellStyle(csHeading);

		sheet.setColumnWidth(13, 4000);
		c = row.createCell(13);
		c.setCellValue("Total Amount");
		c.setCellStyle(csHeading);

		rowIndex++;

		return rowIndex;
	}

	private int insertInvoiceDetailInfo(Sheet sheet, int index, Map<String, List<ClientInvoiceBean>> serviceTypeJobMap)
	{
		int rowIndex = 0;
		Row row = null;
		Cell c = null;

		Set<String> serviceTypeNameSet = serviceTypeJobMap.keySet();

		try
		{
			for (String key : serviceTypeNameSet)
			{

				List<ClientInvoiceBean> jobList = serviceTypeJobMap.get(key);

				// ===== JOB INSTALLATION LIST ===============
				rowIndex++;
				row = sheet.createRow(rowIndex);
				row.setHeight((short) 500);
				c = row.createCell(0);
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 4));
				c.setCellValue("Charges for Installation - " + key);
				c.setCellStyle(csTitle);
				for (int x = 1; x <= 4; x++)
				{
					c = row.createCell(x);
					c.setCellStyle(csTitle);
				}

				int i = 1;
				float iTotalAmount = 0;
				float iInstallation = 0;
				float iRepaid = 0;
				int iTotalJobs=0;
				HashMap<String, String> installationSummary=new HashMap<String,String>();
				for (ClientInvoiceBean job : jobList)
				{
					
					if (job.getInstallationCharge() > 0)
					{
						
						iTotalJobs=iTotalJobs+1;
						iTotalAmount = iTotalAmount + job.getTotalAmount();
						iInstallation = iInstallation + job.getInstallationCharge();
						iRepaid = iRepaid + job.getRepairCharge();

						rowIndex++;
						row = sheet.createRow(rowIndex);

						c = row.createCell(0);
						c.setCellValue(i);
						c.setCellStyle(csDetails);

						c = row.createCell(1);
						c.setCellValue(job.getJobId());
						c.setCellStyle(csDetails);

						c = row.createCell(2);
						c.setCellValue(job.getJobDesc());
						c.setCellStyle(csDetails);

						c = row.createCell(3);
						c.setCellValue(job.getClientRefId());
						c.setCellStyle(csDetails);

						c = row.createCell(4);
						c.setCellValue(job.getCustomerName());
						c.setCellStyle(csDetails);

						c = row.createCell(5);
						c.setCellValue(job.getCityName());
						c.setCellStyle(csDetails);

						c = row.createCell(6);
						c.setCellValue("--");
						c.setCellStyle(csDetails);

						c = row.createCell(7);
						c.setCellValue(job.getCheckOutDate());
						c.setCellStyle(csDetails);

						c = row.createCell(8);
						c.setCellValue(job.getJobServices());
						c.setCellStyle(csDetails);

						c = row.createCell(9);
						c.setCellValue(job.getServiceTypeName());
						c.setCellStyle(csDetails);

						c = row.createCell(10);
						c.setCellValue(job.getJobStatus());
						c.setCellStyle(csDetails);

						c = row.createCell(11);
						c.setCellValue(job.getInstallationCharge());
						c.setCellStyle(csDetails);

						c = row.createCell(12);
						c.setCellValue(job.getRepairCharge());
						c.setCellStyle(csDetails);

						c = row.createCell(13);
						c.setCellValue(job.getTotalAmount());
						c.setCellStyle(csDetails);

						i++;
					}
				}

				// Sub Total Amount
				rowIndex++;
				row = sheet.createRow(rowIndex);
				row.setHeight((short) 400);

				if (iTotalAmount > 0)
				{

					c = row.createCell(8);
					sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 7, 9));
					c.setCellValue("Sub-Total of Installation Charges - " + key);
					c.setCellStyle(csSubTotal);

					c = row.createCell(9);
					c.setCellStyle(csSubTotal);

					c = row.createCell(10);
					c.setCellStyle(csSubTotal);

					c = row.createCell(11);
					c.setCellValue(iInstallation);
					c.setCellStyle(csSubTotal);

					c = row.createCell(12);
					c.setCellValue(iRepaid);
					c.setCellStyle(csSubTotal);

					c = row.createCell(13);
					c.setCellValue(iTotalAmount);
					c.setCellStyle(csSubTotal);
					installationSummary.put("count", iTotalJobs+"");
					installationSummary.put("amount", iTotalAmount+"");
					summaryMap.put("Charges for Installation - " + key, installationSummary);
				}
				else
				{
					c = row.createCell(0);
					row.setHeight((short) 500);
					sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 4));
					c.setCellValue("--- No Record Found --- ");
					c.setCellStyle(csNoRecord);
					for (int x = 1; x <= 4; x++)
					{
						c = row.createCell(x);
						c.setCellStyle(csNoRecord);
					}
				}
				rowIndex++;

				// ===== JOB REPAIR LIST ===============
				rowIndex++;
				row = sheet.createRow(rowIndex);
				row.setHeight((short) 500);
				c = row.createCell(0);
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 4));
				c.setCellValue("Charges for Repairs - " + key);
				c.setCellStyle(csTitle);
				for (int x = 1; x <= 4; x++)
				{
					c = row.createCell(x);
					c.setCellStyle(csTitle);
				}

				int r = 1;
				float rTotalAmount = 0;
				float rInstallation = 0;
				float rRepaid = 0;
				int rRepaiarJobs=0;
				HashMap<String, String> repairSummary= new HashMap<String, String>();
				for (ClientInvoiceBean job : jobList)
				{

					if (job.getRepairCharge() > 0 && (job.getInstallationCharge() == 0 || job.getInstallationCharge() == 0.0))
					{
						rRepaiarJobs=rRepaiarJobs+1;
						rTotalAmount = rTotalAmount + job.getTotalAmount();
						rInstallation = rInstallation + job.getInstallationCharge();
						rRepaid = rRepaid + job.getRepairCharge();

						rowIndex++;
						row = sheet.createRow(rowIndex);

						c = row.createCell(0);
						c.setCellValue(r);
						c.setCellStyle(csDetails);

						c = row.createCell(1);
						c.setCellValue(job.getJobId());
						c.setCellStyle(csDetails);

						c = row.createCell(2);
						c.setCellValue(job.getJobDesc());
						c.setCellStyle(csDetails);

						c = row.createCell(3);
						c.setCellValue(job.getClientRefId());
						c.setCellStyle(csDetails);

						c = row.createCell(4);
						c.setCellValue(job.getCustomerName());
						c.setCellStyle(csDetails);

						c = row.createCell(5);
						c.setCellValue(job.getCityName());
						c.setCellStyle(csDetails);

						c = row.createCell(6);
						c.setCellValue("--");
						c.setCellStyle(csDetails);

						c = row.createCell(7);
						c.setCellValue(job.getCheckOutDate());
						c.setCellStyle(csDetails);

						c = row.createCell(8);
						c.setCellValue(job.getJobServices());
						c.setCellStyle(csDetails);

						c = row.createCell(9);
						c.setCellValue(job.getServiceTypeName());
						c.setCellStyle(csDetails);

						c = row.createCell(10);
						c.setCellValue(job.getJobStatus());
						c.setCellStyle(csDetails);

						c = row.createCell(11);
						c.setCellValue(job.getInstallationCharge());
						c.setCellStyle(csDetails);

						c = row.createCell(12);
						c.setCellValue(job.getRepairCharge());
						c.setCellStyle(csDetails);

						c = row.createCell(13);
						c.setCellValue(job.getTotalAmount());
						c.setCellStyle(csDetails);

						r++;
					}
				}

				// Sub Total Amount
				rowIndex++;
				row = sheet.createRow(rowIndex);
				row.setHeight((short) 400);

				if (rTotalAmount > 0)
				{

					c = row.createCell(8);
					sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 7, 9));
					c.setCellValue("Sub-Total of Repair Charges - " + key);
					c.setCellStyle(csSubTotal);

					c = row.createCell(9);
					c.setCellStyle(csSubTotal);

					c = row.createCell(10);
					c.setCellStyle(csSubTotal);

					c = row.createCell(11);
					c.setCellValue(rInstallation);
					c.setCellStyle(csSubTotal);

					c = row.createCell(12);
					c.setCellValue(rRepaid);
					c.setCellStyle(csSubTotal);

					c = row.createCell(13);
					c.setCellValue(rTotalAmount);
					c.setCellStyle(csSubTotal);
					repairSummary.put("count", rRepaiarJobs+"");
					repairSummary.put("amount", rTotalAmount+"");
					summaryMap.put("Charges for Repair - " + key, repairSummary);
				}
				else
				{
					c = row.createCell(0);
					row.setHeight((short) 500);
					sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, 4));
					c.setCellValue("--- No Record Found --- ");
					c.setCellStyle(csNoRecord);
					for (int x = 1; x <= 4; x++)
					{
						c = row.createCell(x);
						c.setCellStyle(csNoRecord);
					}
				}

				rowIndex++;
			}
		}
		catch (Exception e)
		{
			System.out.println("e::::::" + e.getMessage());
			e.printStackTrace();
		}

		return rowIndex;
	}

	private void setCellStyles(Workbook wb)
	{

		// font size 10
		Font f = wb.createFont();
		f.setFontHeightInPoints((short) 10);

		// Simple style
		cs = wb.createCellStyle();
		cs.setFont(f);

		// Bold Font
		Font bold = wb.createFont();
		bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		bold.setFontHeightInPoints((short) 10);

		// Italic Font
		Font Italic = wb.createFont();
		Italic.setBoldweight(Font.BOLDWEIGHT_BOLD);
		Italic.setFontHeightInPoints((short) 10);
		Italic.setItalic(true);

		// RedColor Font
		Font redF = wb.createFont();
		redF.setBoldweight(Font.BOLDWEIGHT_BOLD);
		redF.setFontHeightInPoints((short) 10);
		redF.setColor(IndexedColors.RED.getIndex());

		// Heading style
		csHeading = wb.createCellStyle();
		csHeading.setBorderTop(CellStyle.BORDER_THIN);
		csHeading.setTopBorderColor(IndexedColors.BLACK.getIndex());
		csHeading.setBorderBottom(CellStyle.BORDER_THIN);
		csHeading.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		csHeading.setBorderLeft(CellStyle.BORDER_THIN);
		csHeading.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		csHeading.setBorderRight(CellStyle.BORDER_THIN);
		csHeading.setRightBorderColor(IndexedColors.BLACK.getIndex());
		csHeading.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
		csHeading.setFillPattern(CellStyle.SOLID_FOREGROUND);
		csHeading.setFont(bold);
		csHeading.setAlignment(CellStyle.ALIGN_CENTER);
		csHeading.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		// Title style
		csTitle = wb.createCellStyle();
		csTitle.setBorderTop(CellStyle.BORDER_THIN);
		csTitle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		csTitle.setBorderBottom(CellStyle.BORDER_THIN);
		csTitle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		csTitle.setBorderLeft(CellStyle.BORDER_THIN);
		csTitle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		csTitle.setBorderRight(CellStyle.BORDER_THIN);
		csTitle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		csTitle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		csTitle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		csTitle.setFont(Italic);

		// Bold style
		csBold = wb.createCellStyle();
		csBold.setBorderBottom(CellStyle.BORDER_THIN);
		csBold.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		csBold.setFont(bold);

		// SubTotal style
		csSubTotal = wb.createCellStyle();
		csSubTotal.setBorderTop(CellStyle.BORDER_THIN);
		csSubTotal.setTopBorderColor(IndexedColors.BLACK.getIndex());
		csSubTotal.setBorderLeft(CellStyle.BORDER_THIN);
		csSubTotal.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		csSubTotal.setBorderBottom(CellStyle.BORDER_THIN);
		csSubTotal.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		csSubTotal.setBorderRight(CellStyle.BORDER_THIN);
		csSubTotal.setRightBorderColor(IndexedColors.BLACK.getIndex());
		csSubTotal.setFont(bold);

		// Setup style for Bottom/Right/Top/Left corner cell Border Lines
		csDetails = wb.createCellStyle();
		csDetails.setBorderTop(CellStyle.BORDER_THIN);
		csDetails.setTopBorderColor(IndexedColors.BLACK.getIndex());
		csDetails.setBorderLeft(CellStyle.BORDER_THIN);
		csDetails.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		csDetails.setBorderBottom(CellStyle.BORDER_THIN);
		csDetails.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		csDetails.setBorderRight(CellStyle.BORDER_THIN);
		csDetails.setRightBorderColor(IndexedColors.BLACK.getIndex());
		csDetails.setFont(f);

		csNoRecord = wb.createCellStyle();
		csNoRecord.setFont(redF);
		csNoRecord.setAlignment(CellStyle.ALIGN_CENTER);
		csNoRecord.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		csNoRecord.setBorderTop(CellStyle.BORDER_THIN);
		csNoRecord.setTopBorderColor(IndexedColors.BLACK.getIndex());
		csNoRecord.setBorderLeft(CellStyle.BORDER_THIN);
		csNoRecord.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		csNoRecord.setBorderBottom(CellStyle.BORDER_THIN);
		csNoRecord.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		csNoRecord.setBorderRight(CellStyle.BORDER_THIN);
		csNoRecord.setRightBorderColor(IndexedColors.BLACK.getIndex());
	}

}
