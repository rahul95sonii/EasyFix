package com.easyfix.reports.delegate;

import java.awt.Color;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.Exporters;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsxExporterBuilder;
import net.sf.dynamicreports.jasper.constant.JasperProperty;
import net.sf.dynamicreports.report.base.expression.AbstractSimpleExpression;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.definition.ReportParameters;
import net.sf.jasperreports.engine.JRDataSource;
import com.easyfix.reports.dao.EasyfixerReportDao;
import com.easyfix.reports.model.EasyfixerReport;

public class EasyfixerReportServiceImpl implements EasyfixerReportService {
	EasyfixerReportDao easyfixerReporDao;
	List<EasyfixerReport> efrList;
	Map<Integer, String> serType = new HashMap<Integer, String>();
	
	@Override
	public FileInputStream downloadEfrReport(String start, String end,int flag, int client) throws Exception {
		 efrList =easyfixerReporDao.downloadEfrReport(start, end, flag,client);
//		 for(EasyfixerReport e:efrList){
//			String s= financeService.getCurrentBalance(e.getEfrId());
//			System.out.println(s);
//			try{
//			float f =Float.valueOf(s);
//			e.setBalance(f);
//			}
//			catch(Exception ex){
//				ex.printStackTrace();
//			}
//		 }
		 serType.put(1, "kk");serType.put(2,"ak");
		 return buildReport();
		}
	
	 private JRDataSource createDataSource() throws Exception {
		 DRDataSource dataSource = new DRDataSource("efrId", "efrName", "registrationDate","activeDays","morethan250",
				 "equalorlessthan250","efrEarning","jobCancled","cusRat","dis","completionTAT","efrServiceType","status",
				 "ndmName","docs","balance","city","phoneNo","address","map","appUser","appVersionName","zone"
				 );
		 for(EasyfixerReport e:efrList)
			 dataSource.add(e.getEfrId(),e.getEfrName(),e.getDateOfJoining(),e.getActiveDays(),e.getMoreThen250(),
					 e.getLessThenOrEqual250(),e.getEfrIncome(),e.getCancledJobs(),e.getCusRat(),e.getDis(),e.getAvgTAT(),
					 e.getEfrServiceType(),e.getStatus(),e.getNdmName(),e.getEfrDoc(),e.getBalance(),e.getCity(),
					 e.getMobNo(),e.getAddress(),serType,e.getAppUser(),e.getAppVersionName(),e.getZone());
		 return dataSource;
			 
	 }

	 private FileInputStream buildReport(){
		 FileInputStream reportFile=null;
		 try{
			 File efrReport =new File("Myreport.xlsx");
			 efrReport.createNewFile();
	         JasperXlsxExporterBuilder xlsExporter = Exporters.xlsxExporter(efrReport)
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
	 		
	 		//create columns
//	 		List<String> l =new ArrayList();l.add("kk");l.add("ak");
//	 		
//	 		for(String s:l){
//	 			
//	 			TextColumnBuilder<String> col   = DynamicReports.col.column(s, s, DataTypes.stringType()).setStyle(centertextalignStyle);
//	 				 		}

	 		TextColumnBuilder<Integer>    rowNumberColumn = DynamicReports.col.reportRowNumberColumn("No.").setFixedColumns(2).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Integer>   EfrIdColumn      = DynamicReports.col.column("ID", "efrId", DataTypes.integerType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   EfrNameColumn     = DynamicReports.col.column("Name", "efrName", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Integer>   morethan250 = DynamicReports.col.column(">RS.250", "morethan250", DataTypes.integerType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Integer>   equalorlessthan250 = DynamicReports.col.column("<=RS.250", "equalorlessthan250", DataTypes.integerType()).setStyle(centertextalignStyle);
	 		 TextColumnBuilder<Long>    activeDays = DynamicReports.col.column("Active Days",new secondTodaysactiveDays()).setStyle(centertextalignStyle);
	 		 TextColumnBuilder<Date> regisDateColumn = DynamicReports.col.column("Date Of Registration", "registrationDate", DataTypes.dateType()).setStyle(centertextalignStyle).setPattern("DD-MMM-yy");
	 		TextColumnBuilder<Float>   efrIncome = DynamicReports.col.column("Efr Income", "efrEarning", DataTypes.floatType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Integer>   jobCancled = DynamicReports.col.column("job Cancled", "jobCancled", DataTypes.integerType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Float>   cusrRat = DynamicReports.col.column("Customer Rating", "cusRat", DataTypes.floatType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Float>   dis = DynamicReports.col.column("Avg Distance(KM)", new disMeterToKM() ).setStyle(centertextalignStyle);
	 		 TextColumnBuilder<Long>    completionTAT = DynamicReports.col.column("completionTAT",new secondTohrcompletionTATEFR()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   plumber     = DynamicReports.col.column("Plumber", new EfrPlumberervice()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   electrician     = DynamicReports.col.column("Electrician", new EfrElectricianService()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   carpenter     = DynamicReports.col.column("Carpenter", new EfrCarpenterService()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   painting     = DynamicReports.col.column("Painting", new EfrPaintService()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   ACandRef     = DynamicReports.col.column("AC And Refrigerator", new AcRefService()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   Appliacnces     = DynamicReports.col.column("Appliances", new ApplianceService()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   Masonry     = DynamicReports.col.column("Masonry", new MasonryService()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   RO     = DynamicReports.col.column("RO", new ROService()).setStyle(centertextalignStyle);
	 		
	 		TextColumnBuilder<String>   status     = DynamicReports.col.column("Status", new EfrstatusToString()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   ndmName     = DynamicReports.col.column("NDM Name", "ndmName", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   docs     = DynamicReports.col.column("Docs","docs",DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Float>   balance = DynamicReports.col.column("Balance", "balance", DataTypes.floatType() ).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   city     = DynamicReports.col.column("City", "city", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   zone     = DynamicReports.col.column("Zone", "zone", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		
	 		TextColumnBuilder<String>   phoneNo     = DynamicReports.col.column("Phone No", "phoneNo", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   address     = DynamicReports.col.column("Address", "address", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   appUser     = DynamicReports.col.column("Has App", "appUser", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   appVersionName     = DynamicReports.col.column("App Version", "appVersionName", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		
	 		// build report
	 		JasperReportBuilder report = DynamicReports.report();
	 		
			report.setColumnTitleStyle(columnTitleStyle)
	 		.addProperty("net.sf.jasperreports.export.xlsx.wrap.text", "false")
	 		.fields(
					DynamicReports.field("activeDays", Long.class),
					DynamicReports.field("dis", Long.class),
					DynamicReports.field("completionTAT", Long.class),
					DynamicReports.field("efrServiceType", String.class),
					DynamicReports.field("status", Integer.class)
					//DynamicReports.field("docs", String.class)
					)
	 		.ignorePagination()
			.ignorePageWidth()
			//.columns(
				//	Columns.column("Registration Date", "registrationDate", DataTypes.dateYearToSecondType()).setStyle(centertextalignStyle).setPattern("DD-MMM-yy")
			//		)
			.addProperty(JasperProperty.PRINT_KEEP_FULL_TEXT, "true")
			.columns(rowNumberColumn,EfrIdColumn,EfrNameColumn,phoneNo,address,city,zone,morethan250,equalorlessthan250,activeDays,regisDateColumn,efrIncome,
					jobCancled,cusrRat,dis,completionTAT,plumber,electrician,carpenter,painting,
					ACandRef,Appliacnces,Masonry,RO,status,ndmName,docs,balance,appUser,appVersionName)
					
			.subtotalsAtSummary(DynamicReports.sbt.sum(morethan250),
					DynamicReports.sbt.sum(equalorlessthan250),
					DynamicReports.sbt.sum(efrIncome),
					DynamicReports.sbt.sum(jobCancled),
					DynamicReports.sbt.sum(balance))
					.setSubtotalStyle(titleStyle)
			.setDataSource(createDataSource())
			.toXlsx(xlsExporter);
	 	//	report.show();
	 		reportFile=new FileInputStream(efrReport);
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 return reportFile;
	 }
	 @Override
	 public String convertStringToDate(String dateString) throws Exception {
			String formatteddate = null;
		    DateFormat rdf = new SimpleDateFormat( "dd MMM, yyyy");
		    DateFormat wdf = new SimpleDateFormat("yyyy-MM-dd");
		    Date date = null;
		    try {
		    	date =  rdf.parse(dateString);
		     } catch ( ParseException e ) {
		         e.printStackTrace();
		     }
		    
		    if( date != null ) {
		    	formatteddate = wdf.format( date );
		        }
		    
		    return formatteddate;
		}
		
	
	public EasyfixerReportDao geteasyfixerReporDao() {
		return easyfixerReporDao;
	}
	public void seteasyfixerReporDao(EasyfixerReportDao easyfixerReporDao) {
		this.easyfixerReporDao = easyfixerReporDao;
	}

}
class secondTodaysactiveDays extends AbstractSimpleExpression<Long> {
	private static final long serialVersionUID = 1L;

public Long evaluate(ReportParameters reportParameters) {
	long time =0;
	Long localTime=null;
	try{
		localTime = reportParameters.getValue("activeDays");
		  if(localTime!=null)
		    	 time=localTime.longValue();
	}
    catch(Exception e){
    	System.out.println(e);
    }
     return (long)(Math.abs((time/3600)/24));
  }

}
class disMeterToKM extends AbstractSimpleExpression<Float> {
	private static final long serialVersionUID = 1L;

public Float evaluate(ReportParameters reportParameters) {
	long dis =0;
	Long localDis=null;
	try{
		localDis = reportParameters.getValue("dis");
		  if(localDis!=null)
			  dis=localDis.longValue();
	}
    catch(Exception e){
    	System.out.println(e);
    }
     return (float)(Math.abs(dis/1000));
  }

}
class secondTohrcompletionTATEFR extends AbstractSimpleExpression<Long> {
	private static final long serialVersionUID = 1L;

public Long evaluate(ReportParameters reportParameters) {
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
     return (long)(Math.abs((time/3600)));
  }
}
class EfrPlumberervice extends AbstractSimpleExpression<String> {
	private static final long serialVersionUID = 1L;

public String evaluate(ReportParameters reportParameters) {
	String value ="";
	String localValue="";
	try{
		localValue = reportParameters.getValue("efrServiceType");
		String[] services = localValue.trim().split(",");
		List<String> list = Arrays.asList(services);
		if(list.contains("5"))
			value = "yes";
		else
			value = "no";
		
		  
	}
    catch(Exception e){
    	System.out.println(e);
    }
	return value;
  }

}

class EfrElectricianService extends AbstractSimpleExpression<String> {
	private static final long serialVersionUID = 1L;

public String evaluate(ReportParameters reportParameters) {
	String value ="";
	String localValue="";
	try{
		localValue = reportParameters.getValue("efrServiceType");
		String[] services = localValue.trim().split(",");
		List<String> list = Arrays.asList(services);
		if(list.contains("1"))
			value = "yes";
		else
			value = "no";
		
		  
	}
    catch(Exception e){
    	System.out.println(e);
    }
	return value;
  }

}
class EfrCarpenterService extends AbstractSimpleExpression<String> {
	private static final long serialVersionUID = 1L;

public String evaluate(ReportParameters reportParameters) {
	String value ="";
	String localValue="";
	try{
		localValue = reportParameters.getValue("efrServiceType");
		String[] services = localValue.trim().split(",");
		List<String> list = Arrays.asList(services);
		if(list.contains("4"))
			value = "yes";
		else
			value = "no";
		
		  
	}
    catch(Exception e){
    	System.out.println(e);
    }
	return value;
  }

}
class EfrPaintService extends AbstractSimpleExpression<String> {
	private static final long serialVersionUID = 1L;

public String evaluate(ReportParameters reportParameters) {
	String value ="";
	String localValue="";
	try{
		localValue = reportParameters.getValue("efrServiceType");
		String[] services = localValue.trim().split(",");
		List<String> list = Arrays.asList(services);
		if(list.contains("7"))
			value = "yes";
		else
			value = "no";
		
		  
	}
    catch(Exception e){
    	System.out.println(e);
    }
	return value;
  }

}
class AcRefService extends AbstractSimpleExpression<String> {
	private static final long serialVersionUID = 1L;

public String evaluate(ReportParameters reportParameters) {
	String value ="";
	String localValue="";
	try{
		localValue = reportParameters.getValue("efrServiceType");
		String[] services = localValue.trim().split(",");
		List<String> list = Arrays.asList(services);
		if(list.contains("2"))
			value = "yes";
		else
			value = "no";
		
		  
	}
    catch(Exception e){
    	System.out.println(e);
    }
	return value;
  }

}
class ApplianceService extends AbstractSimpleExpression<String> {
	private static final long serialVersionUID = 1L;

public String evaluate(ReportParameters reportParameters) {
	String value ="";
	String localValue="";
	try{
		localValue = reportParameters.getValue("efrServiceType");
		String[] services = localValue.trim().split(",");
		List<String> list = Arrays.asList(services);
		if(list.contains("3"))
			value = "yes";
		else
			value = "no";
		
		  
	}
    catch(Exception e){
    	System.out.println(e);
    }
	return value;
  }

}
class MasonryService extends AbstractSimpleExpression<String> {
	private static final long serialVersionUID = 1L;

public String evaluate(ReportParameters reportParameters) {
	String value ="";
	String localValue="";
	try{
		localValue = reportParameters.getValue("efrServiceType");
		String[] services = localValue.trim().split(",");
		List<String> list = Arrays.asList(services);
		if(list.contains("8"))
			value = "yes";
		else
			value = "no";
		
		  
	}
    catch(Exception e){
    	System.out.println(e);
    }
	return value;
  }

}
class ROService extends AbstractSimpleExpression<String> {
	private static final long serialVersionUID = 1L;

public String evaluate(ReportParameters reportParameters) {
	String value ="";
	String localValue="";
	try{
		localValue = reportParameters.getValue("efrServiceType");
		String[] services = localValue.trim().split(",");
		List<String> list = Arrays.asList(services);
		if(list.contains("11"))
			value = "yes";
		else
			value = "no";
		
		  
	}
    catch(Exception e){
    	System.out.println(e);
    }
	return value;
  }

}
class EfrstatusToString extends AbstractSimpleExpression<String> {
	private static final long serialVersionUID = 1L;

public String evaluate(ReportParameters reportParameters) {
	String value ="";
	int localValue=0;
	try{
		localValue = reportParameters.getValue("status");
		
		if(localValue==1)
			value = "Active";
		else if(localValue==0)
			value = "Inactive";
		else
			value="Unknown"; 
	}
    catch(Exception e){
    	System.out.println(e);
    }
	return value;
  }

}

/*
class EfrdocsToString extends AbstractSimpleExpression<String> {
	private static final long serialVersionUID = 1L;

public String evaluate(ReportParameters reportParameters) {
	String value ="";
	String localValue="";
	try{
		localValue = reportParameters.getValue("docs");
		if(localValue!=null){
		String[] services = localValue.trim().split(",");
		List<String> list = Arrays.asList(services);
		for(String s: list){
			//System.out.println(s);
		if(s.trim().equals("1")){
			value = value+" Aadhaar Card, ";
			continue;
		}
		 if(s.trim().equals("2")){
			value= value+" Ration Card, ";
			continue;
		 }
//		if(!s.equals("1") && !s.equals("2"))
//			value = value+"add more data in report";
		}
	}
		else
			value = "NA";
	}
    catch(Exception e){
    	System.out.println(e);
    }
	return value;
  }

}
*/

