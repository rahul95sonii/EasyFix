package com.easyfix.reports;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.exception.DRException;




public class JobReport {
	
public static void main(String[] args){
	Connection con = null;
	try{
		Class.forName("com.mysql.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/easyfix?zeroDateTimeBehavior=convertToNull","root", "kundan");
	}
	catch(SQLException e){
		System.out.println(e);
		return;
	}
	catch(ClassNotFoundException e){
		System.out.println(e);
		return;
	}
	JasperReportBuilder report = DynamicReports.report();
	StyleBuilder boldStyle         = DynamicReports.stl.style().bold(); 
	StyleBuilder centertextalignStyle         = DynamicReports.stl.style().setHorizontalTextAlignment(HorizontalTextAlignment.CENTER);
	StyleBuilder boldCenteredStyle = DynamicReports.stl.style(boldStyle).setHorizontalTextAlignment(HorizontalTextAlignment.CENTER); 
	StyleBuilder columnTitleStyle  = DynamicReports.stl.style(boldCenteredStyle);
	report.columns(
			Columns.column("City", "city_name", DataTypes.stringType()).setStyle(centertextalignStyle),
			Columns.column("Job Code", "job_id", DataTypes.integerType()).setStyle(centertextalignStyle),
			Columns.column("Customer Name", "customer_name", DataTypes.stringType()).setStyle(centertextalignStyle),
			Columns.column("completed jobs ", "job_completed", DataTypes.integerType()).setStyle(centertextalignStyle),
			Columns.column("Customer Rating", "customer_rating", DataTypes.integerType()).setStyle(centertextalignStyle),
			Columns.column("EFR Code", "fk_easyfixter_id", DataTypes.integerType()).setStyle(centertextalignStyle),
			Columns.column("EFR Name", "efr_name", DataTypes.stringType()).setStyle(centertextalignStyle),
			Columns.column("Dis Travelled", "Efr_dis_travelled", DataTypes.stringType()).setStyle(centertextalignStyle),
			Columns.column("Client", "client_name", DataTypes.stringType()).setStyle(centertextalignStyle),
			Columns.column("Service Type", "service_type_name", DataTypes.stringType()).setStyle(centertextalignStyle),
			Columns.column("Created On", "created_date_time", DataTypes.stringType()).setStyle(centertextalignStyle),
			Columns.column("Requested On", "created_date_time", DataTypes.stringType()).setStyle(centertextalignStyle),
			Columns.column("Feedback On", "feedback_date_time", DataTypes.stringType()).setStyle(centertextalignStyle),
			Columns.column("Completion Time", "checkout_date_time", DataTypes.stringType()).setStyle(centertextalignStyle),
			Columns.column("EF Share", "easyfix_charge", DataTypes.floatType()).setStyle(centertextalignStyle),
			Columns.column("EFR Share", "easyfixer_charge", DataTypes.floatType()).setStyle(centertextalignStyle),
			Columns.column("Client Share", "client_charge", DataTypes.floatType()).setStyle(centertextalignStyle),
			Columns.column("Matrial Charge", "material_charge", DataTypes.integerType()).setStyle(centertextalignStyle),
			Columns.column("Total Charge", "total_charge", DataTypes.integerType()).setStyle(centertextalignStyle),
			Columns.column("Collected By", "collected_by", DataTypes.integerType()).setStyle(centertextalignStyle)
			
			)
			 .title(//title of the report
				      Components.text("job report")
				      .setHorizontalTextAlignment(HorizontalTextAlignment.CENTER))
			.setColumnTitleStyle(columnTitleStyle)
			.highlightDetailEvenRows() 
			.addProperty("net.sf.jasperreports.export.xls.wrap.text", "false")
			.pageFooter(Components.pageXofY())//show page number on the page footer
			.setDataSource("call try()", 
			                                  con);
	try {
        //show the report
		report.ignorePageWidth();
		
			report.show();

        //export the report to a pdf file
			report.toXlsx(new FileOutputStream("D:/report.xlsx"));
			//report.toPdf(new FileOutputStream("c:/report.pdf"));
		} 
	catch (DRException e) {
		e.printStackTrace();
		} 
	catch (FileNotFoundException e) {
		e.printStackTrace();
		}
	
}


}
