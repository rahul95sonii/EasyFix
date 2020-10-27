package com.easyfix.util.triggers.invoice;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;




import com.easyfix.util.PropertyReader;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFGenerator
{

	public static String createPdf(Map<String, HashMap<String, String>> summaryMap, String name, String billingName, String billingAddress, String startDate,
			String endDate, PropertyReader props) throws Exception
	{

		File f = new File(name + ".pdf");
		System.out.println(name + ".pdf");
		boolean ifExists = f.exists();
		System.out.println("File already there" + ifExists);
		if (ifExists)
		{
			f.delete();
			System.out.println("deleteing");
		}
		System.out.println("creating pdf");
		Document document = new Document();
		PdfWriter.getInstance(document, new FileOutputStream(name + ".pdf"));
		document.open();

		// First Row
		PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100f);
		Image image = Image.getInstance(props.getValue("logo.path"));
		image.scalePercent(20f, 20f);
		PdfPCell cell1 = new PdfPCell(image, false);
		cell1.setColspan(5);
		cell1.setRowspan(6);
		table.addCell(cell1);

		PdfPCell cellHeading = new PdfPCell(new Phrase(getBoldStandardFont("INVOICE")));
		cellHeading.setColspan(5);
		cellHeading.setRowspan(2);
		// cellHeading.setBorder(Rectangle.R);
		// cellHeading.setBorder(Rectangle.LEFT);
		cellHeading.setBorder(Rectangle.BOX);
		cellHeading.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cellHeading);

		// Second Row
		Phrase p = new Phrase();
		p.add(getStandardFont("Easyfix Handy Solutions Private Limited"));
		p.add(Chunk.NEWLINE);
		p.add(getStandardFont("TIN   : 07976909200 "));
		p.add(Chunk.NEWLINE);
		p.add(getStandardFont("PAN : AADCE4878L"));
		p.add(Chunk.NEWLINE);
		p.add(getStandardFont("Service Tax No.: AADCE4878LSD001"));
		p.add(Chunk.NEWLINE);
		PdfPCell cell21 = new PdfPCell(p);
		cell21.setColspan(2);
		cell21.setRowspan(5);
		table.addCell(cell21);

		Phrase p1 = new Phrase();
		p1.add(getBoldStandardFont("Invoice No."));
		p1.add(Chunk.NEWLINE);
		int currentFinancialYear = Calendar.getInstance().get(Calendar.YEAR) % 100;
		int nextFinancialYear = Calendar.getInstance().get(Calendar.YEAR) % 100 + 1;
		Calendar cal =  Calendar.getInstance();
		cal.add(Calendar.MONTH ,-1);
		//format it to MMM-yyyy // January-2012
		String previousMonth= new SimpleDateFormat("MMM").format(cal.getTime());
		String invoiceNumber = "SI/" + currentFinancialYear + "-" + nextFinancialYear + "/" + previousMonth + "/" + getRandomInteger();
		p1.add(getStandardFont(invoiceNumber));
		PdfPCell cell22 = new PdfPCell(p1);
		cell22.setColspan(2);
		cell22.setRowspan(1);
		table.addCell(cell22);

		p1 = new Phrase();
		p1.add(getBoldStandardFont("Date:"));
		p1.add(Chunk.NEWLINE);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		Date date = new Date();
		String currentDate = sdf.format(date);
		p1.add(getStandardFont(currentDate));
		PdfPCell cell23 = new PdfPCell(p1);
		// cell23.setColspan(1);
		// cell22.setRowspan(1);
		table.addCell(cell23);

		p1 = new Phrase();
		p1.add(getBoldStandardFont(billingName));
		p1.add(Chunk.NEWLINE);
		p1.add(getStandardFont("Address:"));
		p1.add(Chunk.NEWLINE);
		p1.add(getStandardFont(billingAddress));
		p1.add(Chunk.NEWLINE);
		p1.add(Chunk.NEWLINE);
		p1.add(Chunk.NEWLINE);
		PdfPCell cell24 = new PdfPCell(p1);
		cell24.setColspan(3);
		cell24.setRowspan(3);
		table.addCell(cell24);

		p1 = new Phrase();
		p1.add(getBoldStandardFont("Terms of Payment:"));
		p1.add(Chunk.NEWLINE);
		p1.add(getStandardFont("Immediate"));
		PdfPCell cell25 = new PdfPCell(p1);
		cell25.setColspan(2);
		cell25.setRowspan(1);
		table.addCell(cell25);

		p1 = new Phrase();
		p1.add(getBoldStandardFont("Period:"));
		p1.add(Chunk.NEWLINE);
		p1.add(getSmallStandardFont(startDate + " to"));
		p1.add(Chunk.NEWLINE);
		p1.add(getSmallStandardFont(endDate));
		PdfPCell cell26 = new PdfPCell(p1);
		// cell26.setColspan(1);
		// cell26.setRowspan(1);
		table.addCell(cell26);

		// Third Row
		PdfPCell cell3 = new PdfPCell(new Phrase(getBoldStandardFont("S No.")));
		// cell3.setColspan(1);
		// cell3.setRowspan(1);
		cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell3);

		p1 = new Phrase();
		p1.add(getBoldStandardFont("Description of Service"));
		p1.add(Chunk.NEWLINE);
		p1.add(getBoldStandardFont("(Annexure Enclosed)"));
		PdfPCell cell32 = new PdfPCell(p1);
		// cell32.setColspan(1);
		// cell32.setRowspan(1);
		cell32.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell32);

		PdfPCell cell33 = new PdfPCell(new Phrase(getBoldStandardFont("Quantity")));
		// cell33.setColspan(1);
		// cell33.setRowspan(1);
		cell33.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell33);

		PdfPCell cell34 = new PdfPCell(new Phrase(getBoldStandardFont("Rate")));
		// cell34.setColspan(1);
		// cell34.setRowspan(1);
		cell34.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell34);

		PdfPCell cell35 = new PdfPCell(new Phrase(getBoldStandardFont("Total (Rs.)")));
		// cell35.setColspan(1);
		// cell35.setRowspan(1);
		cell35.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell35);

		Iterator<Entry<String, HashMap<String, String>>> entries = summaryMap.entrySet().iterator();

		int i = 1;
		int sum = 0;
		while (entries.hasNext())
		{
			PdfPCell dataCell1 = new PdfPCell(new Phrase(getStandardFont(i + "")));
			// dataCell1.setColspan(1);
			// dataCell1.setRowspan(1);
			dataCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(dataCell1);

			Map.Entry<String, HashMap<String, String>> entry = entries.next();
			System.out.println("key: " + entry.getKey());
			dataCell1 = new PdfPCell(new Phrase(getStandardFont(entry.getKey())));
			// dataCell1.setColspan(1);
			// dataCell1.setRowspan(1);
			table.addCell(dataCell1);
			
			String quantity= entry.getValue().get("count");
			dataCell1 = new PdfPCell(new Phrase(getStandardFont(quantity)));
			// dataCell1.setColspan(1);
			// dataCell1.setRowspan(1);
			dataCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(dataCell1);

			dataCell1 = new PdfPCell(new Phrase(getStandardFont("")));
			// dataCell1.setColspan(1);
			// dataCell1.setRowspan(1);
			table.addCell(dataCell1);
			String amount= entry.getValue().get("amount");
			int value = Math.round(Float.parseFloat(amount));
			System.out.println("value: " + entry.getValue());
			dataCell1 = new PdfPCell(new Phrase(getStandardFont(value + "")));
			// dataCell1.setColspan(1);
			// dataCell1.setRowspan(1);
			dataCell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(dataCell1);

			sum = sum + value;
			i = i + 1;
		}

		System.out.println("total amount for : " + name + " is:" + sum);

		for (int j = 0; j < 4; j++)
		{
			PdfPCell dataCell1 = new PdfPCell(new Phrase(" "));
			table.addCell(dataCell1);
			dataCell1 = new PdfPCell(new Phrase(" "));
			table.addCell(dataCell1);
			dataCell1 = new PdfPCell(new Phrase(""));
			table.addCell(dataCell1);
			dataCell1 = new PdfPCell(new Phrase(""));
			table.addCell(dataCell1);
			dataCell1 = new PdfPCell(new Phrase(""));
			table.addCell(dataCell1);
		}

		PdfPCell taxableInvoiceCell = new PdfPCell(new Phrase(""));
		table.addCell(taxableInvoiceCell);
		taxableInvoiceCell = new PdfPCell(new Phrase(getStandardFont("Taxable Invoice Value")));
		taxableInvoiceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(taxableInvoiceCell);
		taxableInvoiceCell = new PdfPCell(new Phrase(""));
		table.addCell(taxableInvoiceCell);
		taxableInvoiceCell = new PdfPCell(new Phrase(""));
		table.addCell(taxableInvoiceCell);
		taxableInvoiceCell = new PdfPCell(new Phrase(getStandardFont("Rs. " + sum)));
		taxableInvoiceCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(taxableInvoiceCell);

		PdfPCell serviceTaxCell = new PdfPCell(new Phrase(""));
		table.addCell(serviceTaxCell);
		serviceTaxCell = new PdfPCell(new Phrase(getStandardFont("Service Tax")));
		serviceTaxCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(serviceTaxCell);
		serviceTaxCell = new PdfPCell(new Phrase(""));
		table.addCell(serviceTaxCell);
		serviceTaxCell = new PdfPCell(new Phrase(getStandardFont("14.00%")));
		serviceTaxCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(serviceTaxCell);
		int serviceTaxAmount = (14 * sum) / 100;
		serviceTaxCell = new PdfPCell(new Phrase(getStandardFont("Rs. " + serviceTaxAmount)));
		serviceTaxCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(serviceTaxCell);

		PdfPCell sbCessCell = new PdfPCell(new Phrase(""));
		table.addCell(sbCessCell);
		sbCessCell = new PdfPCell(new Phrase(getStandardFont("SB Cess")));
		sbCessCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(sbCessCell);
		sbCessCell = new PdfPCell(new Phrase(""));
		table.addCell(sbCessCell);
		sbCessCell = new PdfPCell(new Phrase(getStandardFont("0.50%")));
		sbCessCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(sbCessCell);
		int sbCessAmount = (5 * sum) / 1000;
		sbCessCell = new PdfPCell(new Phrase(getStandardFont("Rs. " + sbCessAmount)));
		sbCessCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(sbCessCell);

		PdfPCell kkCessCell = new PdfPCell(new Phrase(""));
		table.addCell(kkCessCell);
		kkCessCell = new PdfPCell(new Phrase(getStandardFont("KKC*")));
		kkCessCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(kkCessCell);
		kkCessCell = new PdfPCell(new Phrase(""));
		table.addCell(kkCessCell);
		kkCessCell = new PdfPCell(new Phrase(getStandardFont("0.50%")));
		kkCessCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(kkCessCell);
		int kkCessAmount = (5 * sum) / 1000;
		kkCessCell = new PdfPCell(new Phrase(getStandardFont("Rs. " + kkCessAmount)));
		kkCessCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(kkCessCell);

		PdfPCell totalInvoiceValueCell = new PdfPCell(new Phrase(""));
		table.addCell(totalInvoiceValueCell);
		totalInvoiceValueCell = new PdfPCell(new Phrase(getBoldStandardFont("Total Invoice Value (Rs.)")));
		totalInvoiceValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(totalInvoiceValueCell);
		totalInvoiceValueCell = new PdfPCell(new Phrase(""));
		table.addCell(totalInvoiceValueCell);
		totalInvoiceValueCell = new PdfPCell(new Phrase(getStandardFont("")));
		table.addCell(totalInvoiceValueCell);
		int totalInvoiceAmount = sum + serviceTaxAmount + kkCessAmount + sbCessAmount;
		totalInvoiceValueCell = new PdfPCell(new Phrase(getBoldStandardFont("Rs. " + totalInvoiceAmount)));
		totalInvoiceValueCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(totalInvoiceValueCell);

		// Fourth Row
		p1 = new Phrase();
		p1.add(Chunk.NEWLINE);
		p1.add(getSmallStandardFont("For payments, kindly make Crossed Cheque/DD/Pay Order in favour of: "));
		p1.add(Chunk.NEWLINE);
		p1.add(getSmallBoldItalicStandardFont("EasyFix Handy Solutions India Pvt Ltd."));
		p1.add(Chunk.NEWLINE);
		p1.add(Chunk.NEWLINE);
		p1.add(getSmallBoldStandardFont("For NEFT Payments :"));
		p1.add(Chunk.NEWLINE);
		p1.add(getSmallStandardFont("Bank Name   :YES Bank"));
		p1.add(Chunk.NEWLINE);
		p1.add(getSmallStandardFont("A/c No.         :003084000003731 "));
		p1.add(Chunk.NEWLINE);
		p1.add(getSmallStandardFont("IFS Code       :YESB0000030"));
		PdfPCell cell41 = new PdfPCell(new Phrase(p1));
		cell41.setColspan(3);
		cell41.setRowspan(4);
		table.addCell(cell41);

		p1 = new Phrase();
		p1.add(Chunk.NEWLINE);
		p1.add(Chunk.NEWLINE);
		p1.add(Chunk.NEWLINE);
		p1.add(Chunk.NEWLINE);
		p1.add(getSmallBoldStandardFont("For EasyFix Handy Solutions India Pvt Ltd"));
		p1.add(Chunk.NEWLINE);
		p1.add(Chunk.NEWLINE);
		p1.add(Chunk.NEWLINE);
		p1.add(Chunk.NEWLINE);

		p1.add(getSmallStandardFont("Authorized Signatory"));
		PdfPCell cell42 = new PdfPCell(p1);
		cell42.setColspan(2);
		cell42.setRowspan(4);
		cell42.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(cell42);

		// Fifth Row
		p1 = new Phrase();
		p1.add(getSmallBoldStandardFont("Declaration: "));
		p1.add(Chunk.NEWLINE);
		p1.add(getSmallStandardFont("We declare that this invoice shows the actual price of the services described and that all particulars are true and correct. All dealings are subject to Delhi Jurisdiction.   *KKC has been levied @ 0.50% on taxable services from 01-06-2016."));
		PdfPCell cell5 = new PdfPCell(p1);
		cell5.setColspan(5);
		cell5.setRowspan(2);
		table.addCell(cell5);

		// Sixth Row
		// para= new Paragraph();
		// para.setAlignment(Element.ALIGN_CENTER);
		p1 = new Phrase();
		p1.add(getSmallStandardFont("CIN  : U93000DL2013PTC257571,  R.O. Address : H.NO. -F-3/36, 2ND FLOOR, SECTOR-11, ROHINI, New Delhi - 110085, "));
		p1.add(Chunk.NEWLINE);
		p1.add(getSmallStandardFont("Corporate Office:  H No-G-4/8, G Block DLF Phase-1 Gurgaon 122001, Haryana , Tel: 8882122666, Website:  www.easyfix.in "));
		// para.add(p1);
		PdfPCell cell6 = new PdfPCell(p1);
		cell6.setColspan(5);
		cell6.setRowspan(2);
		cell6.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(cell6);

		p1 = new Phrase();
		p1.add(getSmallStandardFont("This is computer generated invoice                                                                            E&OE   "));

		PdfPCell cell7 = new PdfPCell(p1);
		cell7.setColspan(5);
		cell7.setRowspan(4);
		cell7.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(cell7);
		float[] columnWidths = new float[] { 10f, 50f, 20f, 30f, 30f };

		table.setWidths(columnWidths);

		document.add(table);
		document.close();
		return invoiceNumber;
	}

	public static Chunk getBoldStandardFont(String text)
	{
		Chunk c = new Chunk(" ");
		if (text != null)
		{
			Font courier = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLD);
			c = new Chunk(text, courier);
		}
		return c;
	}

	public static Chunk getSmallBoldStandardFont(String text)
	{
		Chunk c = new Chunk(" ");
		if (text != null)
		{
			Font courier = new Font(FontFamily.TIMES_ROMAN, 9, Font.BOLD);
			c = new Chunk(text, courier);
		}
		return c;
	}

	public static Chunk getBoldItalicStandardFont(String text)
	{
		Chunk c = new Chunk(" ");
		if (text != null)
		{
			Font courier = new Font(FontFamily.TIMES_ROMAN, 10, Font.BOLDITALIC);
			c = new Chunk(text, courier);
		}
		return c;
	}

	public static Chunk getSmallBoldItalicStandardFont(String text)
	{
		Chunk c = new Chunk(" ");
		if (text != null)
		{
			Font courier = new Font(FontFamily.TIMES_ROMAN, 9, Font.BOLDITALIC);
			c = new Chunk(text, courier);
		}
		return c;
	}

	public static Chunk getStandardFont(String text)
	{
		Chunk c = new Chunk(" ");
		if (text != null)
		{
			Font courier = new Font(FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
			c = new Chunk(text, courier);
		}

		return c;
	}

	public static Chunk getSmallStandardFont(String text)
	{
		Chunk c = new Chunk(" ");
		if (text != null)
		{
			Font courier = new Font(FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
			c = new Chunk(text, courier);
		}
		return c;
	}

	private static String getRandomInteger()
	{
		Random generator = new Random();
		int i = generator.nextInt(100) + 1;
		return i+"";

	}
}
