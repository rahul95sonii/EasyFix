package com.easyfix.clients.delegate;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

import com.easyfix.clients.dao.ClientDao;
import com.easyfix.clients.model.ClientWebsite;
import com.easyfix.clients.model.Clients;
import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.util.CommonAbstractAction;


public class ClientServiceImpl extends CommonAbstractAction implements ClientService {

	private static final long serialVersionUID = 1L;
	private  ClientDao clientDao;
	
	public ClientDao getClientDao() {
		return clientDao;
	}

	public void setClientDao(ClientDao clientDao) {
		this.clientDao = clientDao;
	}
	

	public Clients getClientDetails(int clientId) throws Exception {
		Clients cl =  clientDao.getClientDetails(clientId);
		cl.setClientWebsite(clientDao.getClientWebSiteDetails(clientId));
		return cl;
	}

	@Override
	public int addUpdateClient(Clients client) throws Exception {		
		return clientDao.addUpdateClient(client);
	}

	@Override
	public List<Clients> getClientList(int flag) throws Exception {
		return clientDao.getClientList(flag);
	}
	
	
	@Override
	public List<Clients> getServiceList(int clientId) throws Exception {
		return clientDao.getServiceList(clientId);
	}


	@Override
	public int addUpdateClientServices(Clients client) throws Exception {
		return clientDao.addUpdateClientServices(client);
	}
	@Override
	public int addUpdateClientServicesFromExcel(Clients client) throws Exception{
		return clientDao.addUpdateClientServicesFromExcel(client);
	}

	@Override
	public Clients getClientServicesDetails(int clientServiceId) throws Exception {
		return clientDao.getClientServicesDetails(clientServiceId);
	}


	@Override
	public List<Clients> getClientServiceListByClientIdAndServiceTypeId(
			int fkClientId, int fkServiceTypeId) throws Exception {
		return clientDao.getClientServiceListByClientIdAndServiceTypeId(fkClientId,fkServiceTypeId);
	}

	@Override
	public int addDeleteClientEasyfixerMapping(Clients client) throws Exception {
		
		return clientDao.addDeleteClientEasyfixerMapping(client);
	}

	@Override
	public List<Clients> getEasyfixerListForClient(int clientId, int mappingStatus) throws Exception {
		
		return  clientDao.getEasyfixerListForClient(clientId,mappingStatus);
	}

	@Override
	public List<Easyfixers> getEasyfixerForMapping(int clientId, int serviceTypeId) throws Exception {
		return  clientDao.getEasyfixerForMapping(clientId,serviceTypeId);
	}

	@Override
	public int saveMappedEasyfixer(int clientId, int serviceTypeId, List<String> list) throws Exception {
		return  clientDao.saveMappedEasyfixer(clientId,serviceTypeId,list);
	}

	@Override
	public int updateMappedEasyfixer(int clientId, int efrId, String flag, List<String> list) throws Exception {
		return  clientDao.updateMappedEasyfixer(clientId, efrId, flag, list);
	}

	@Override
	public FileInputStream downloadClientRateCard(int clientId) throws Exception {
		List<Clients> serviceList = clientDao.getServiceList(clientId);
		DRDataSource dataSource = new DRDataSource("clientId", "ServiceTypeID","rateCardId",
				"chargeType","totalAmount","EFDF","EFDV","OF","OV","CF","CV","status",
				"clientName","ServiceTypeName","rateCardName"
				 );
		for(Clients c:serviceList )
			dataSource.add(c.getClientId(),c.getServiceTypeId(),c.getRateCardId(),
					c.getChargeType(),Integer.valueOf(c.getTotalCharge()),Integer.valueOf(c.getEasyfixDirectFixed()),
					Float.valueOf(c.getEasyfixDirectVariable()),Integer.valueOf(c.getOverheadFixed()),
					Float.valueOf(c.getOverheadVariable()),
					Integer.valueOf(c.getClientFixed()),Float.valueOf(c.getClientVariable()),
							c.getServiceStatus(),c.getClientName(),c.getServiceTypeObj().getServiceTypeName(),
							c.getClientRateCardObj().getClientRateCardName()
					);
		return buildReport(dataSource);
	}

	public FileInputStream buildReport(DRDataSource dataSource) {
		FileInputStream reportFile=null;
		 try{
			 File efrReport =new File("RateCard.xlsx");
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
	 		//create column
	 		TextColumnBuilder<Integer>    rowNumberColumn = DynamicReports.col.reportRowNumberColumn("No.").setFixedColumns(2).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Integer>   clientId      = DynamicReports.col.column("Client ID", "clientId", DataTypes.integerType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Integer>   ServiceTypeID      = DynamicReports.col.column("Service Type ID", "ServiceTypeID", DataTypes.integerType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Integer>   rateCardId      = DynamicReports.col.column("rate Card Id", "rateCardId", DataTypes.integerType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Integer>   chargeType      = DynamicReports.col.column("charge Type", "chargeType", DataTypes.integerType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Integer>   totalAmount      = DynamicReports.col.column("Total Amount", "totalAmount", DataTypes.integerType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Integer>   EFDF      = DynamicReports.col.column("EF Direct Fixed", "EFDF", DataTypes.integerType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Float>   EFDV      = DynamicReports.col.column("EF Direct variable", "EFDV", DataTypes.floatType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Integer>   OF      = DynamicReports.col.column("overhead fixed", "OF", DataTypes.integerType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Float>   OV      = DynamicReports.col.column("overhead variable", "OV", DataTypes.floatType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Integer>   CF      = DynamicReports.col.column("Client fixed", "CF", DataTypes.integerType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Float>   CV      = DynamicReports.col.column("Client variable", "CV", DataTypes.floatType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<Integer>   status      = DynamicReports.col.column("status", "status", DataTypes.integerType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   clientName      = DynamicReports.col.column("Client Name", "clientName", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   ServiceTypeName      = DynamicReports.col.column("Service Type Name", "ServiceTypeName", DataTypes.stringType()).setStyle(centertextalignStyle);
	 		TextColumnBuilder<String>   rateCardName      = DynamicReports.col.column("rateCard Name", "rateCardName", DataTypes.stringType()).setStyle(centertextalignStyle);
		
	 	// build report
	 		JasperReportBuilder report = DynamicReports.report();
	 		report.setColumnTitleStyle(columnTitleStyle)
	 		.addProperty("net.sf.jasperreports.export.xlsx.wrap.text", "false")
	 		.ignorePagination()
			.ignorePageWidth()
			.addProperty(JasperProperty.PRINT_KEEP_FULL_TEXT, "true")
			.columns(rowNumberColumn,clientId,ServiceTypeID,rateCardId,chargeType,totalAmount,
					EFDF,EFDV,OF,OV,CF,CV,status,clientName,ServiceTypeName,rateCardName)
					.setDataSource(dataSource)
					.toXlsx(xlsExporter);
	 		reportFile=new FileInputStream(efrReport);
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
		 return reportFile;
	}
	private Object getCellValue(Cell cell) {
	    switch (cell.getCellType()) {
	    case Cell.CELL_TYPE_STRING:
	        return cell.getStringCellValue();
	 
	    case Cell.CELL_TYPE_BOOLEAN:
	        return cell.getBooleanCellValue();
	 
	    case Cell.CELL_TYPE_NUMERIC:
	        return cell.getNumericCellValue();
	    }
	 
	    return null;
	}
	@Override
	public List<Clients> readClientRateCardFromExcelFile(String excelFilePath) throws IOException {
		   
		List<Clients> rcList = new ArrayList<Clients>();
		 try{
	    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	 
	    Workbook workbook = new XSSFWorkbook(inputStream);
	    Sheet firstSheet = workbook.getSheetAt(0);
	    Iterator<Row> iterator = firstSheet.iterator();
	    //System.out.println("no of rows: "+firstSheet.getPhysicalNumberOfRows()); // print no of rows
	    
	    while (iterator.hasNext()) {
	    	Row  nextRow =  iterator.next();
	    	int noOfColumn = nextRow.getPhysicalNumberOfCells();
	    	// if no of columns in any row is less than 13 dont save it
	    	if(noOfColumn>16 || noOfColumn<13){
	    		System.out.println("no of columns are not 13");
	    	
	    		continue;
	    	}
	    		
	    	//System.out.println(nextRow.getPhysicalNumberOfCells());
	        if(nextRow.getRowNum()==0) // skip 1st row 
	        	continue;
	        Iterator<Cell> cellIterator = nextRow.cellIterator();
	        Clients clientObj = new Clients();
	     
	 
	        while (cellIterator.hasNext()) {
	            Cell nextCell = cellIterator.next();
	            int columnIndex = nextCell.getColumnIndex();
	 
	            switch (columnIndex) {
	            // skip 1st row
	   /*         case 0:
	            	nextCell.setCellType(Cell.CELL_TYPE_STRING);
	            	try{
	            		String s = (String) getCellValue(nextCell);
	            		if(s==null)
	            			continue;
	            	cusObj.setCustomerMobNo((String) getCellValue(nextCell));
	            	}
	            	catch(Exception e){
	            		e.printStackTrace();
	            	}
	            	//System.out.println(cusObj.getCustomerMobNo());
	                break;
	                */
	            case 1:
	            	nextCell.setCellType(Cell.CELL_TYPE_NUMERIC);
	            	try{
	            		Double d= (Double)getCellValue(nextCell);
	            		clientObj.setClientId(d.intValue());
	            	}
	            	catch(Exception e){
	            		e.printStackTrace();
	            	}
	                break;
	            case 2:
	            	nextCell.setCellType(Cell.CELL_TYPE_NUMERIC);
	            	try{
	            		Double d= (Double)getCellValue(nextCell);
	            		clientObj.setServiceTypeId(d.intValue());
	            	}
	            	catch(Exception e){
	            		e.printStackTrace();
	            	}
	                break;
	            case 3:
	            	nextCell.setCellType(Cell.CELL_TYPE_NUMERIC);
	            	try{
	            		Double d= (Double)getCellValue(nextCell);
	            		clientObj.setRateCardId(d.intValue());
	            	}
	            	catch(Exception e){
	            		e.printStackTrace();
	            	}
	                break;
	            case 4:
	            	nextCell.setCellType(Cell.CELL_TYPE_NUMERIC);
	            	try{
	            		Double d= (Double)getCellValue(nextCell);
	            		clientObj.setChargeType(d.intValue());
	            	}
	            	catch(Exception e){
	            		e.printStackTrace();
	            	}
	            	break;
	            case 5:
	            	nextCell.setCellType(Cell.CELL_TYPE_STRING);
	            	try{
	            		clientObj.setTotalCharge((String)getCellValue(nextCell));
	            	}
	            	catch(Exception e){
	            		e.printStackTrace();
	            	}
	            	break;
	            case 6:
	            	nextCell.setCellType(Cell.CELL_TYPE_STRING);
	            	try{
	            		clientObj.setEasyfixDirectFixed((String)getCellValue(nextCell));
	            	}
	            	catch(Exception e){
	            		e.printStackTrace();
	            	}
	            	break;
	            case 7:
	            	nextCell.setCellType(Cell.CELL_TYPE_STRING);
	            	try{
	            		clientObj.setEasyfixDirectVariable((String)getCellValue(nextCell));
	            	}
	            	catch(Exception e){
	            		e.printStackTrace();
	            	}
	            	break;
	            case 8:
	            	nextCell.setCellType(Cell.CELL_TYPE_STRING);
	            	try{
	            		clientObj.setOverheadFixed((String)getCellValue(nextCell));
	            	}
	            	catch(Exception e){
	            		e.printStackTrace();
	            	}
	                break;
	            case 9:
	            	nextCell.setCellType(Cell.CELL_TYPE_STRING);
	            	try{
	            		clientObj.setOverheadVariable((String)getCellValue(nextCell));
	            	}
	            	catch(Exception e){
	            		e.printStackTrace();
	            	}
	                break;
	            case 10:
	            	nextCell.setCellType(Cell.CELL_TYPE_STRING);
	            	try{
	            		clientObj.setClientFixed((String)getCellValue(nextCell));
	            	}catch(Exception e){
	            		e.printStackTrace();
	            	}
	                break;
	            case 11:
	            	nextCell.setCellType(Cell.CELL_TYPE_STRING);
	            	try{
	            		clientObj.setClientVariable((String)getCellValue(nextCell));
	            	}catch(Exception e){
	            		e.printStackTrace();
	            	}
	                break;
	            case 12:
	            	nextCell.setCellType(Cell.CELL_TYPE_NUMERIC);
	            	try{
	            		Double d= (Double)getCellValue(nextCell);
	            		clientObj.setServiceStatus(d.intValue());
	            	}catch(Exception e){
	            		e.printStackTrace();
	            	}
	                break;
	           
	            }
	            
	        }
	        rcList.add(clientObj);
	    }
	 
	     workbook.close();
	    inputStream.close();
		 }
		 catch(Exception e){
			 e.printStackTrace();
		 }
	    return rcList;
	}

	@Override
	public ClientWebsite getClientWebSiteDetails(int clientId) throws Exception {
		return clientDao.getClientWebSiteDetails(clientId);
	}
	
	

}
