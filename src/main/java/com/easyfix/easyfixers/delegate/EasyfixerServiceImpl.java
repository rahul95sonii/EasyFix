package com.easyfix.easyfixers.delegate;


import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.Exporters;
import net.sf.dynamicreports.jasper.builder.export.JasperXlsxExporterBuilder;
import net.sf.dynamicreports.jasper.constant.JasperProperty;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalTextAlignment;
import net.sf.dynamicreports.report.constant.VerticalTextAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.jasperreports.engine.JRDataSource;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;

import com.easyfix.clients.model.Clients;
import com.easyfix.easyfixers.dao.EasyfixerDao;
import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.easyfixers.model.ServicePayout;
import com.easyfix.finance.model.EasyfixerTransaction;
import com.easyfix.finance.model.Finance;
import com.easyfix.reports.model.CompletedJobsReport;
import com.easyfix.settings.model.DocumentType;
import com.easyfix.util.RestClient;
import com.easyfix.util.UtilityFunctions;

public class EasyfixerServiceImpl implements EasyfixerService
{

	private EasyfixerDao easyfixerDao;
	private List<ServicePayout> efrPayoutList;
	
	public EasyfixerDao getEasyfixerDao()
	{
		return easyfixerDao;
	}

	public void setEasyfixerDao(EasyfixerDao easyfixerDao)
	{
		this.easyfixerDao = easyfixerDao;
	}

	@Override
	public List<Easyfixers> getEasyfixerList(int flag) throws Exception
	{
		return easyfixerDao.getEasyfixerList(flag);
	}

	@Override
	public int addUpdateEasyFixer(Easyfixers easyfixerObj) throws Exception
	{
		return easyfixerDao.addUpdateEasyFixer(easyfixerObj);
	}

	@Override
	public Easyfixers getEasyfixerDetailsById(int easyfixerId) throws Exception
	{
		return easyfixerDao.getEasyfixerDetailsById(easyfixerId);
	}

	@Override
	public List<DocumentType> getEasyfixerDocument(int easyfixerId) throws Exception
	{
		return easyfixerDao.getEasyfixerDocument(easyfixerId);
	}

	@Override
	public List<Clients> getClientListForEasyfixer(int easyfixerId, int mappedStatus) throws Exception
	{
		return easyfixerDao.getClientListForEasyfixer(easyfixerId, mappedStatus);
	}

	@Override
	public List<Easyfixers> easyfixerBalance(Easyfixers easyfixerObj) throws Exception
	{
		List<Easyfixers> efrBalanceList = new ArrayList<Easyfixers>();
		try
		{
			WebTarget target = new RestClient().apiResponse();
			WebTarget efrTarget = target.path("easyfixers");

			/*
			 * if(easyfixerObj.getEasyfixerId() > 0){ efrTarget =
			 * efrTarget.queryParam("id", easyfixerObj.getEasyfixerId()); }
			 */

			if (!easyfixerObj.getEasyfixerName().equalsIgnoreCase(""))
			{
				efrTarget = efrTarget.queryParam("name", easyfixerObj.getEasyfixerName());
			}

			if (!easyfixerObj.getEasyfixerNo().equalsIgnoreCase(""))
			{
				efrTarget = efrTarget.queryParam("mobile", easyfixerObj.getEasyfixerNo());
			}

			if (easyfixerObj.getNdmId() > 0)
			{
				efrTarget = efrTarget.queryParam("ndm", easyfixerObj.getNdmId());
			}
			if(easyfixerObj.getEasyfixerServiceTypeId()>0){
				efrTarget = efrTarget.queryParam("serviceTypeId", easyfixerObj.getEasyfixerServiceTypeId());
			}
				

			String jsonResult = efrTarget.request().accept(MediaType.APPLICATION_JSON).get(String.class);

			JSONArray jArray = new JSONArray(jsonResult);
			ObjectMapper mapper = new ObjectMapper();
			for (int i = 0; i < jArray.length(); i++)
			{
				Easyfixers efrObj = mapper.readValue(jArray.getString(i), Easyfixers.class);
				if (efrObj.getBalanceUpdateDate() != null)
				{
					String bdate = UtilityFunctions.changeDateFormatToFormat(efrObj.getBalanceUpdateDate(), "dd-MM-yyyy HH:mm",
							"dd-MM-yyyy");
					efrObj.setBalanceUpdateDate(bdate);
				}
				efrBalanceList.add(efrObj);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return efrBalanceList;
	}

	@Override
	public List<EasyfixerTransaction> efrTransactionList(String efrId, String startDate, String endDate) throws Exception
	{
		List<EasyfixerTransaction> efrTransList = new ArrayList<EasyfixerTransaction>();
		try
		{
			WebTarget target = new RestClient().apiResponse();
			WebTarget transTarget = target.path("easyfixers/transactions");

			if (!efrId.equalsIgnoreCase(""))
				transTarget = transTarget.queryParam("id", efrId);
			if (!startDate.equalsIgnoreCase(""))
				transTarget = transTarget.queryParam("startDate", startDate);
			if (!endDate.equalsIgnoreCase(""))
				transTarget = transTarget.queryParam("endDate", endDate);

			String jsonResult = transTarget.request().accept(MediaType.APPLICATION_JSON).get(String.class);
			
			JSONArray jArray = new JSONArray(jsonResult);
			ObjectMapper mapper = new ObjectMapper();
			for (int i = 0; i < jArray.length(); i++)
			{
				EasyfixerTransaction efrTrans = mapper.readValue(jArray.getString(i), EasyfixerTransaction.class);
				if (efrTrans.getEfrTransDate() != null)
				{
					String tdate = UtilityFunctions.changeDateFormatToFormat(efrTrans.getEfrTransDate(), "dd-MM-yyyy HH:mm", "dd-MM-yyyy");
					efrTrans.setEfrTransDate(tdate);
				}
				efrTransList.add(efrTrans);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return efrTransList;
	}

	@Override
	public List<Easyfixers> getEnumReasonList(int enumType) throws Exception
	{
		return easyfixerDao.getEnumReasonList(enumType);
	}

	@Override
	public void updateRechargeAmount(EasyfixerTransaction efrTransObj) throws Exception
	{
		try
		{
			WebTarget target = new RestClient().apiResponse();
			WebTarget efrTarget = target.path("easyfixers/transactions");
			String json = Entity.json(efrTransObj).toString();
			System.out.println("json: " + json);

			Response response = efrTarget.request().post(Entity.entity(efrTransObj, MediaType.APPLICATION_JSON));
			int status = response.getStatus();
			System.out.println("statud is " + status);

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public List<Easyfixers> getEasyfixerListForPayout(int cityId) throws Exception
	{
		/*WebTarget target = new RestClient().apiResponse();
		WebTarget efrTarget = target.path("easyfixers/city");
		
		efrTarget=efrTarget.queryParam("id", cityId);
		
		Invocation invocation= efrTarget.request().buildGet();
		Response response = invocation.invoke();
		int status = response.getStatus();
		if(status!=200){
			throw new Exception("Error from api: "+status);
		}
		List<Easyfixers> easyfixers = response.readEntity(new GenericType<List<Easyfixers>>(){});
		return easyfixers;*/
		
		return easyfixerDao.easyfixerListForPayOut(cityId);
		
	}

	@Override
	public int saveServicePayOut(ServicePayout payoutObj) throws Exception {
		return easyfixerDao.saveServicePayOut(payoutObj);
	}
	@Override
	public Map<String, Easyfixers> getEfrAppActions(int jobId, int efrId)
			throws Exception {
		List<Easyfixers> list = easyfixerDao.getEfrAppActions(jobId, efrId);
		Map<String,Easyfixers> mapByJobId= new HashMap<String,Easyfixers>();
		for(Easyfixers e:list){
			if(!mapByJobId.containsKey(e.getAction())){
				e.setEfrNameCSV(e.getEasyfixerName());
							}
			else{
				e.setEfrNameCSV(mapByJobId.get(e.getAction()).getEfrNameCSV()+","+e.getEasyfixerName());
			}
			mapByJobId.put(e.getAction(), e);
			
			System.out.println(mapByJobId);
		}
		return mapByJobId;
	}
	@Override
	public int approvePayOutByFinance(int payoutId, int efrId, int userId, float finAprvAmnt, int status) throws Exception {
		return easyfixerDao.approvePayOutByFinance(payoutId,efrId,userId,finAprvAmnt,status);
	}

	@Override
	public FileInputStream downloadEfrPayoutSheet(String start, String end,
			int flag) throws Exception {
		efrPayoutList = easyfixerDao.downloadEfrPayoutSheet(start, end, flag);
		//System.out.println(efrPayoutList);
		return buildEfrPayoutSheet();
	}
	private JRDataSource createDataSource() throws Exception {
		 DRDataSource dataSource = new DRDataSource(
					"efrId","efrBalance","opsAmount","opsApprovedAmount",
					"opsApprovedDate","finApproveDate","finApprovedAmount",
					"createdDate","approval","cityName","EfrName","efrNo"
					
				 );
		 for(ServicePayout s:efrPayoutList)
			 dataSource.add(
					 s.getEfrId(),s.getEfrBalance(),s.getOpsAmount(),s.getOpsApprovedAmount(),
					 s.getOpsApprovedDate(),s.getFinApproveDate(),s.getFinApprovedAmount(),
					 s.getCreatedDate(),s.getApproval(),s.getCityName(),s.getEfrName(),s.getEfrNo()
					 );
		 return dataSource;
			 
	 }
	private FileInputStream buildEfrPayoutSheet(){
		 FileInputStream reportFile=null;
		 try{
			 File jobReport =new File("efrPayout.xlsx");
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
	 		
	 		
	 		TextColumnBuilder<Integer>    rowNumberColumn = DynamicReports.col.reportRowNumberColumn("No.").setFixedColumns(2).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   cityName      = DynamicReports.col.column("City", "cityName", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Float> efrBalance = DynamicReports.col.column("Efr Balance", "efrBalance", DataTypes.floatType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Float> opsApprovedAmount = DynamicReports.col.column("Ops Approved Amount", "opsApprovedAmount", DataTypes.floatType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Float> opsAmount = DynamicReports.col.column("Ops Amount", "opsAmount", DataTypes.floatType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Float> finApprovedAmount = DynamicReports.col.column("Fin Approved Amount", "finApprovedAmount", DataTypes.floatType()).setStyle(centertextalignStyle);
	 		
	 		
	 		
	 		TextColumnBuilder<Integer>   easyfixerId     = DynamicReports.col.column("Easyfixer Id", "efrId", DataTypes.integerType()).setStyle(centertextalignStyle);
	 		
	 		TextColumnBuilder<String>   easyfixerName = DynamicReports.col.column("Easyfixer", "EfrName", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   creationDtae = DynamicReports.col.column("Created On","createdDate", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   opsApprovedDate = DynamicReports.col.column("Ops Approval Date","opsApprovedDate", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   finApproveDate = DynamicReports.col.column("Fin Approval Date","finApproveDate", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   approval      = DynamicReports.col.column("Approval", "approval", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		
	 		TextColumnBuilder<String>   EFRMobileNo      = DynamicReports.col.column("EFR Phone", "efrNo", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		
	 		
	 		// build report
	 		JasperReportBuilder report = DynamicReports.report();
	 		report.setColumnTitleStyle(columnTitleStyle)
	 		.addProperty("net.sf.jasperreports.export.xlsx.wrap.text", "false")
	 		
	 		.ignorePagination()
			.ignorePageWidth()
			//.columns(
				//	Columns.column("Registration Date", "registrationDate", DataTypes.dateYearToSecondType()).setStyle(centertextalignStyle).setPattern("DD-MMM-yy")
			//		)
			// "rescheduleReason","commentOnReschedule","rescheduleTime","rescheduleBy"
			.addProperty(JasperProperty.PRINT_KEEP_FULL_TEXT, "true")
			.columns(rowNumberColumn,cityName,easyfixerId,easyfixerName,
					EFRMobileNo,efrBalance,opsAmount,opsApprovedAmount,opsApprovedDate,
					finApprovedAmount,finApproveDate,approval,creationDtae
					)
			
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

	public List<ServicePayout> getEfrPayoutList() {
		return efrPayoutList;
	}

	public void setEfrPayoutList(List<ServicePayout> efrPayoutList) {
		this.efrPayoutList = efrPayoutList;
	}

	@Override
	public Easyfixers getEfrMetaData(int efrId) throws Exception {
		// TODO Auto-generated method stub
		return easyfixerDao.getEfrMetaData(efrId);
	}

	

	

	/*
	 * public static void main(String[] args)throws Exception{ Easyfixers
	 * easyfixerObj = new Easyfixers(); //job.setJobId(0);
	 * easyfixerObj.setEasyfixerId(0);
	 * easyfixerObj.setEasyfixerName("Sarfaraz");
	 * easyfixerObj.setEasyfixerNo(""); easyfixerObj.setNDMName("");
	 * EasyfixerServiceImpl s =new EasyfixerServiceImpl(); List<Easyfixers> l
	 * =s.easyfixerBalance(easyfixerObj);
	 * 
	 * }
	 */

}
