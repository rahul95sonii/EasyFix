package com.easyfix.finance.delegate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;

import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.finance.dao.FinanceDao;
import com.easyfix.finance.model.EasyfixerFinance;
import com.easyfix.finance.model.Finance;
import com.easyfix.util.RestClient;
import com.easyfix.util.UtilityFunctions;

public class FinanceServiceImpl implements FinanceService {
	
	private FinanceDao financeDao;

	@Override
	public List<Finance> getTransactionList(Finance financeObj)	throws Exception {
		return financeDao.getTransactionList(financeObj);
	}
	
	
	

	public FinanceDao getFinanceDao() {
		return financeDao;
	}

	public void setFinanceDao(FinanceDao financeDao) {
		this.financeDao = financeDao;
	}




	@Override
	public int addUpdateEasyfixerAmount(Finance financeObj) throws Exception {
		return financeDao.addUpdateEasyfixerAmount(financeObj);
	}




	@Override
	public String convertDateToString(String dateString) throws Exception {
		
			String formatteddate = null;
		    DateFormat rdf = new SimpleDateFormat( "dd/MM/yyyy");
		    DateFormat wdf = new SimpleDateFormat("yyyy-MM-dd");
		    Date date = null;
		    try {
		    	date = rdf.parse(dateString);
		     } catch ( ParseException e ) {
		         e.printStackTrace();
		     }
		    
		    if( date != null ) {
		    	formatteddate = wdf.format( date );
		        }
		    
		    return formatteddate;
		
	}




	@Override
	public String getCurrentBalance(int easyfixerId) throws Exception {
		return financeDao.getCurrentBalance(easyfixerId);
	}




	@Override
	public List<Easyfixers> ndmEasyfixerList(EasyfixerFinance efrFinObj) throws Exception {
		List<Easyfixers> ndmEfrList = new ArrayList<Easyfixers>();
		try {
			WebTarget target = new RestClient().apiResponse();
			WebTarget efrTarget = target.path("easyfixers");
			
			/*if(easyfixerObj.getEasyfixerId() > 0){
				efrTarget = efrTarget.queryParam("id", easyfixerObj.getEasyfixerId());
			}*/
			
			if(!efrFinObj.getEfr().getEasyfixerName().equalsIgnoreCase("")){
				efrTarget = efrTarget.queryParam("name", efrFinObj.getEfr().getEasyfixerName());
			}
			
			if(!efrFinObj.getEfr().getEasyfixerNo().equalsIgnoreCase("")){
				efrTarget = efrTarget.queryParam("mobile", efrFinObj.getEfr().getEasyfixerNo());
			}
			
			if(efrFinObj.getEfr().getNdmId() > 0){
				efrTarget = efrTarget.queryParam("ndm", efrFinObj.getEfr().getNdmId());
			}
			
			String jsonResult = efrTarget.request().accept(MediaType.APPLICATION_JSON).get(String.class);
			
			JSONArray jArray = new JSONArray(jsonResult);
			ObjectMapper mapper = new ObjectMapper();
			for(int i=0; i<jArray.length(); i++)
			{
				Easyfixers efrObj = mapper.readValue(jArray.getString(i), Easyfixers.class);
				if(efrObj.getBalanceUpdateDate() != null){
					String bdate = UtilityFunctions.changeDateFormatToFormat(efrObj.getBalanceUpdateDate(), "dd-MM-yyyy HH:mm", "dd-MM-yyyy");
					efrObj.setBalanceUpdateDate(bdate);
				}
				ndmEfrList.add(efrObj);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return ndmEfrList;
	}


	@Override
	public List<EasyfixerFinance> ndmRechargeList(int efrId, int ndmId, int flag) throws Exception {
		return financeDao.ndmRechargeList(efrId,ndmId,flag);
	}


	@Override
	public int addUpdateNdmRechargeAmount(EasyfixerFinance efrFinObj) throws Exception {
		return financeDao.addUpdateNdmRechargeAmount(efrFinObj);
	}


	@Override
	public List<EasyfixerFinance> submitToFinanceList(int is_submitted) throws Exception {
		List<EasyfixerFinance> ndmEfrList = new ArrayList<EasyfixerFinance>();
		try {
			WebTarget target = new RestClient().apiResponse();
			WebTarget efrTarget = target.path("easyfixers/recharges");
			
			efrTarget = efrTarget.queryParam("isApproved", is_submitted);
			
			
			String jsonResult = efrTarget.request().accept(MediaType.APPLICATION_JSON).get(String.class);
			
			JSONArray jArray = new JSONArray(jsonResult);
			ObjectMapper mapper = new ObjectMapper();
			for(int i=0; i<jArray.length(); i++)
			{
				EasyfixerFinance efrObj = mapper.readValue(jArray.getString(i), EasyfixerFinance.class);
				if(efrObj.getRecharge_date() != null){
					String rdate = UtilityFunctions.changeDateFormatToFormat(efrObj.getRecharge_date(), "dd-MM-yyyy HH:mm", "dd-MM-yyyy");
					efrObj.setRecharge_date(rdate);
				}
				ndmEfrList.add(efrObj);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return ndmEfrList;
	}




	
}
