package com.easyfix.Jobs.action;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.easyfix.Jobs.model.JobSelectedServices;
import com.easyfix.Jobs.model.Jobs;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class InvoiceApprovalPDF {

	
	public void test(List<JobSelectedServices> jobServiceList,Jobs job){		
		
			String fileName = null;
			/*Settings1OfficeModel s = insurancePolicyServiceObj.fetchAllGenericDocuments(0).get(0);
			
			UserRetaility ur = new UserRetaility();
			ur.setUserID(43778);
			UserRetaility newUserSessionObject = null;
	  		
			try {
				newUserSessionObject = userRetailityServiceObj.getFrancisUserDetail(ur);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			final String uploadPathForEstimateApproval = "/var/www/html/easydoc/estimateapproval/";
			
		//	final String uploadPathForEstimateApproval = "C://Users//Anurag//Desktop";
			
			String jobId=""+job.getJobId();
			FileOutputStream out = null;
			fileName = "Estimate_Approval_"+jobId+".pdf";
			try {
				out = new FileOutputStream(uploadPathForEstimateApproval+fileName);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 Document pdf = null;
			 Paragraph para = null;
			try {
				pdf = new Document(PageSize.A4, 40.0F, 20.0F, 5.0F, 0.0F);
				    PdfWriter writer = PdfWriter.getInstance(pdf, out);
				   // writer.setEncryption(USER, OWNER, PdfWriter.ALLOW_PRINTING, PdfWriter.STANDARD_ENCRYPTION_128);
			        //writer.createXmpMetadata();
				    pdf.open();				
				    pdf.addTitle("My first PDF");
			        pdf.addSubject("Using iText");
			        pdf.addKeywords("Java, PDF, iText");
			        pdf.addAuthor("Lars Vogel");
			        pdf.addCreator("Lars Vogel");
			        
			        
			        PdfPTable table = new PdfPTable(3);
			        table.setWidths(new float[] {2,1,1});
			        table.setWidthPercentage(100);
			        
			        DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			        Date d = f.parse(job.getCreatedDateTime());
			        Calendar c = Calendar.getInstance();
			        c.setTime(d); 
			        c.add(Calendar.DATE, 30); 
			        DateFormat date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			        String expiryDate = date.format(c.getTime());
			        Date currentDate = new Date();
			        String currentDate1 = date.format(currentDate);
			        para = new Paragraph();
			        para.add(new Chunk("Estimate Approval", FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD)));
			        para.add(new Chunk("\nDate: "+ currentDate1
			        				 + "\nEstimate Expiration Date:"+expiryDate, FontFactory.getFont(FontFactory.TIMES, 10, Font.BOLD)));
			        PdfPCell cell1 = new PdfPCell(para);
			        cell1.setBorder(Rectangle.NO_BORDER); 
			        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        table.addCell(cell1);
			        
			        cell1 = new PdfPCell();
			        cell1.setBorder(Rectangle.NO_BORDER);
			        table.addCell(cell1);
			        
			        float height=30;
			        Image img1 = Image.getInstance("/var/www/html/easydoc/Easy fix Logo-01.png");
		//	        Image img1 = Image.getInstance("C://Users//Anurag//Desktop//Easy fix Logo-01.png");
			        cell1 = new PdfPCell(img1, true);
			        cell1.setFixedHeight(height);;
			        cell1.setBorder(Rectangle.NO_BORDER);
			        table.addCell(cell1);
			     
			        pdf.add(table);

			       
			        //Main table
			        PdfPTable table1 = new PdfPTable(2);
			        table1.setWidths(new float[] {1,1});
			        table1.setWidthPercentage(100);
			        
			        para = new Paragraph();
			        para.add(new Chunk("Service Provider", FontFactory.getFont(FontFactory.TIMES, 10, Font.BOLD)));
			        cell1 = new PdfPCell(para);
			        cell1.setBorder(Rectangle.BOX); 
			        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			        table1.addCell(cell1);
			        
			        para = new Paragraph();
			        para.add(new Chunk("Customer", FontFactory.getFont(FontFactory.TIMES, 10, Font.BOLD)));
			        cell1 = new PdfPCell(para);
			        cell1.setBorder(Rectangle.BOX); 
			        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			        table1.addCell(cell1);
			        
			   
			        //Emp info part1
			        PdfPTable table2 = new PdfPTable(2);
			        table2.setWidths(new float[] {2,3});
			        table2.setWidthPercentage(100);

			        Paragraph para1 = null;
			        para1 = new Paragraph();
			        para1.add(new Chunk("Vendor: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\nSupervisor: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\nDate of Visit: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\nEMAIL: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\nMobile No.: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\nGST NO./SAC Number: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        
			        cell1 = new PdfPCell(para1);
			        cell1.setBorder(Rectangle.BOX);
			        table2.addCell(cell1);
			        
			        String vendorEmail=job.getOwner().getOfficialEmail();
			        if(vendorEmail==null){
			        	vendorEmail="";
			        }
			        String vendorMobile=job.getOwner().getMobileNo();
			        if(vendorMobile==null){
			        	vendorMobile="";
			        }
			        
			        para1 = new Paragraph();
			        para1.add(new Chunk("Easyfix ", FontFactory.getFont(FontFactory.HELVETICA, 8)));
			        para1.add(new Chunk("\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\n"+ job.getOwner().getUserName(), FontFactory.getFont(FontFactory.HELVETICA,8)));
			        para1.add(new Chunk("\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\n"+job.getScheduleDateTime(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
			        para1.add(new Chunk("\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\n"+ vendorEmail, FontFactory.getFont(FontFactory.HELVETICA, 8)));
			        para1.add(new Chunk("\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\n"+vendorMobile , FontFactory.getFont(FontFactory.HELVETICA, 8)));
			        para1.add(new Chunk("\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\n07AADCE4878L1Z4", FontFactory.getFont(FontFactory.HELVETICA, 8)));
			       
			        cell1 = new PdfPCell(para1);
			        cell1.setBorder(Rectangle.BOX);
			        table2.addCell(cell1);
			        
			        table1.addCell(new PdfPCell(table2));
			        
			        
			        String str=job.getAddressObj().getAddress();
			        int n= (str.length())/45;
			        
			        PdfPTable table3 = new PdfPTable(2);
			        table3.setWidths(new float[] {2,3});
			        table3.setWidthPercentage(100);
			        para1 = new Paragraph();
			        para1.add(new Chunk("Client: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\nName: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\nAddress: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        while(n!=0){
			        para1.add(new Chunk("\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        n=n-1;
			        }
			        para1.add(new Chunk("\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\nEMAIL: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\nMobile  No.: ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        
			        cell1 = new PdfPCell(para1);
			        cell1.setBorder(Rectangle.BOX);
			        table3.addCell(cell1);
			        
			        
			        String customerEmail=job.getCustomerObj().getCustomerEmail();
			        if(customerEmail==null){
			        	customerEmail="";
			        }
			        String customerMobile=job.getCustomerObj().getCustomerMobNo();
			        if(customerMobile==null){
			        	customerMobile="";
			        }
			        para1 = new Paragraph();
			        para1.add(new Chunk(""+job.getClientObj().getClientName(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
			        para1.add(new Chunk("\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\n"+job.getCustomerObj().getCustomerName(), FontFactory.getFont(FontFactory.HELVETICA, 8)));
			        para1.add(new Chunk("\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\n"+str, FontFactory.getFont(FontFactory.HELVETICA, 8)));
			        para1.add(new Chunk("\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\n"+customerEmail, FontFactory.getFont(FontFactory.HELVETICA, 8)));
			        para1.add(new Chunk("\n", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        para1.add(new Chunk("\n"+customerMobile, FontFactory.getFont(FontFactory.HELVETICA, 8)));
			        cell1 = new PdfPCell(para1);
			        cell1.setBorder(Rectangle.BOX);
			        table3.addCell(cell1);
			        
			        table1.addCell(new PdfPCell(table3));
			        pdf.add(table1);
			        
			        
			        PdfPTable table4 = new PdfPTable(6);
			        table4.setWidths(new float[] {1,3,1,1,1,1});
			        table4.setWidthPercentage(100);
			        para = new Paragraph();
			        para.add(new Chunk("Scope of work", FontFactory.getFont(FontFactory.TIMES, 10, Font.BOLD)));
			        cell1 = new PdfPCell(para);
			        cell1.setColspan(6);
			        cell1.setBorder(Rectangle.NO_BORDER); 
			        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        table4.addCell(cell1);
			       
			        para = new Paragraph();
			        para.add(new Chunk("APPENDIX", FontFactory.getFont(FontFactory.TIMES, 10, Font.BOLD)));
			        cell1 = new PdfPCell(para);
			        cell1.setColspan(6);
			        cell1.setBorder(Rectangle.BOX); 
			        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			        table4.addCell(cell1);
			     
			        table4.addCell(new Paragraph("Sr. No.", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        table4.addCell(new Paragraph("Item Description", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        table4.addCell(new Paragraph("Qty", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        table4.addCell(new Paragraph("Unit  Price", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        table4.addCell(new Paragraph("Material Cost", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        table4.addCell(new Paragraph("Total", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        
			        double finalTotal=0;
			        for(int i=0;i<jobServiceList.size();i++){
			        table4.addCell(new Paragraph(""+(i+1), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        table4.addCell(new Paragraph(""+jobServiceList.get(i).getServiceName(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        table4.addCell(new Paragraph("1", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        table4.addCell(new Paragraph(""+Integer.parseInt(jobServiceList.get(i).getTotalCharge()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        table4.addCell(new Paragraph(""+jobServiceList.get(i).getServiceMaterialCharge(), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        table4.addCell(new Paragraph(""+(Double.parseDouble(jobServiceList.get(i).getTotalCharge())+jobServiceList.get(i).getServiceMaterialCharge()), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        finalTotal+=Double.parseDouble(jobServiceList.get(i).getTotalCharge())+jobServiceList.get(i).getServiceMaterialCharge();
			        }
			        
			        for(int i=0;i<3;i++){
			        table4.addCell(new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        table4.addCell(new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        table4.addCell(new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        table4.addCell(new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        table4.addCell(new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        table4.addCell(new Paragraph(" ", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        i=i+1;
			        }
			        
			        table4.addCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        table4.addCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        table4.addCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        table4.addCell(new Paragraph("Gross Total", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        table4.addCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD)));
			        table4.addCell(new Paragraph(""+finalTotal, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        
			        DecimalFormat numberFormat = new DecimalFormat("#.00");
			        double tax=(.18)*finalTotal;
			        table4.addCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        table4.addCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        table4.addCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        table4.addCell(new Paragraph("IGST", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        table4.addCell(new Paragraph("18%", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        table4.addCell(new Paragraph(""+numberFormat.format(tax), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));

			        double netTotal=finalTotal+tax;
			        table4.addCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        table4.addCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        para = new Paragraph();
			        para.add(new Chunk("Net Total Amount", FontFactory.getFont(FontFactory.TIMES, 8, Font.BOLD)));
			        cell1 = new PdfPCell(para);
			        cell1.setColspan(3);
			        cell1.setBorder(Rectangle.BOX); 
			        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			        table4.addCell(cell1);
			        table4.addCell(new Paragraph(""+netTotal, FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        
			        int firstDecimal=(int)(10*netTotal)-(10*(int)netTotal);
			        if(firstDecimal<5){
			        	netTotal=(int)netTotal;
			        }
			        else{
			        	netTotal=1+(int)netTotal;
			        }
			        table4.addCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        table4.addCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        para = new Paragraph();
			        para.add(new Chunk("Round Off", FontFactory.getFont(FontFactory.TIMES, 8, Font.BOLD)));
			        cell1 = new PdfPCell(para);
			        cell1.setColspan(3);
			        cell1.setBorder(Rectangle.BOX); 
			        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			        table4.addCell(cell1);
			        table4.addCell(new Paragraph(""+(int)(netTotal), FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        
			        table4.addCell(new Paragraph("", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        para = new Paragraph();
			        para.add(new Chunk("Rupees in words:-  "+EnglishNumberToWords.convert((int)netTotal), FontFactory.getFont(FontFactory.TIMES, 8, Font.BOLD)));
			        cell1 = new PdfPCell(para);
			        cell1.setColspan(5);
			        cell1.setBorder(Rectangle.BOX); 
			        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			        table4.addCell(cell1);
			        
			        pdf.add(table4);
			        
			        
			        PdfPTable table5= new PdfPTable(2);
			        table5.setWidths(new float[] {1,7});
			        table5.setWidthPercentage(100);
			        para = new Paragraph();
			        
			        para = new Paragraph();
			        para.add(new Chunk("Timelines and General Terms", FontFactory.getFont(FontFactory.TIMES, 10, Font.BOLD)));
			        cell1 = new PdfPCell(para);
			        cell1.setColspan(2);
			        cell1.setBorder(Rectangle.NO_BORDER); 
			        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			        table5.addCell(cell1);
			        
			        table5.addCell(new Paragraph("1", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        para = new Paragraph();
			        para.add(new Chunk("Work will start with 48 hours post receiving advance from client.", FontFactory.getFont(FontFactory.TIMES, 10)));
			        cell1 = new PdfPCell(para);
			        cell1.setBorder(Rectangle.BOX); 
			        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        table5.addCell(cell1);
			        
			        table5.addCell(new Paragraph("2", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        para = new Paragraph();
			        para.add(new Chunk("The work will be completed as per the timelines committed and shared by project supervisor over email.", FontFactory.getFont(FontFactory.TIMES, 10)));
			        cell1 = new PdfPCell(para);
			        cell1.setBorder(Rectangle.BOX); 
			        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        table5.addCell(cell1);
			        
			        table5.addCell(new Paragraph("3", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        para = new Paragraph();
			        para.add(new Chunk("Tax will be charged extra  as per Govt.Rules.", FontFactory.getFont(FontFactory.TIMES, 10, Font.BOLD)));
			        cell1 = new PdfPCell(para);
			        cell1.setBorder(Rectangle.BOX); 
			        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        table5.addCell(cell1);
			        
			        table5.addCell(new Paragraph("4", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        para = new Paragraph();
			        para.add(new Chunk("Any work conducted outside of Scope of work, will be charged on actuals.", FontFactory.getFont(FontFactory.TIMES, 10)));
			        cell1 = new PdfPCell(para);
			        cell1.setBorder(Rectangle.BOX); 
			        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        table5.addCell(cell1);
			        
			        table5.addCell(new Paragraph("5", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        para = new Paragraph();
			        para.add(new Chunk("The above quote is valid for a period of 15 days.", FontFactory.getFont(FontFactory.TIMES, 10)));
			        cell1 = new PdfPCell(para);
			        cell1.setBorder(Rectangle.BOX); 
			        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        table5.addCell(cell1);
			        
			        table5.addCell(new Paragraph("6", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        para = new Paragraph();
			        para.add(new Chunk("50% advance payment to initiate material purchase will be provided by Client in EasyFix's account.", FontFactory.getFont(FontFactory.TIMES, 10)));
			        cell1 = new PdfPCell(para);
			        cell1.setBorder(Rectangle.BOX); 
			        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        table5.addCell(cell1);
			        
			        table5.addCell(new Paragraph("7", FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL)));
			        para = new Paragraph();
			        para.add(new Chunk(" The above quotation is an estimate the final bill shall be prepared upon inception of project.", FontFactory.getFont(FontFactory.TIMES, 10)));
			        cell1 = new PdfPCell(para);
			        cell1.setBorder(Rectangle.BOX); 
			        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        table5.addCell(cell1);
			        
			        para = new Paragraph();
			        para.add(new Chunk("Quotation prepared by : "+job.getOwner().getUserName(), FontFactory.getFont(FontFactory.TIMES, 10)));
			        cell1 = new PdfPCell(para);
			        cell1.setColspan(2);
			        cell1.setMinimumHeight(20);
			        cell1.setBorder(Rectangle.BOX); 
			        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
			        table5.addCell(cell1);
			        
			        para = new Paragraph();
			        para.add(new Chunk("This is a digitally generated estimate and does not require signature.", FontFactory.getFont(FontFactory.TIMES, 10)));
			        cell1 = new PdfPCell(para);
			        cell1.setColspan(2);
			        cell1.setBorder(Rectangle.BOX); 
			        cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
			        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			        table5.addCell(cell1);
			        pdf.add(table5);
				 pdf.close();
			        out.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
