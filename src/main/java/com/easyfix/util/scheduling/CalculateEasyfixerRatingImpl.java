package com.easyfix.util.scheduling;

import java.math.BigDecimal;
import java.util.List;

import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.util.utilityFunction.model.EasyfixerRatingParameters;
 class CalculateEasyfixerRatingImpl implements CalculateEasyfixerRating {
	 
public BigDecimal getRating(Easyfixers easyfixer,List<EasyfixerRatingParameters> paramList)
{
	try{
	BigDecimal cusRat = new BigDecimal(easyfixer.getCustomerRating());
	
	//BigDecimal cusRat= Double.valueOf(easyfixer.getCustomerRating());
	BigDecimal cusRatWeight=new BigDecimal("0");
	BigDecimal auditRat = new BigDecimal(easyfixer.getAuditRating());
	BigDecimal auditRatWeight=new BigDecimal("0");
	BigDecimal counter = new BigDecimal(easyfixer.getDailyCounter());
	BigDecimal counterWeight=new BigDecimal("0");
	BigDecimal disRat = new BigDecimal(easyfixer.getDistanceRating());
	BigDecimal disRatWeight=new BigDecimal("0");
	BigDecimal dailyJobCeiling= new BigDecimal("0");
	
	for(EasyfixerRatingParameters e: paramList){
		
		if(e.getParamName().equals("customer_rating"))
			cusRatWeight = new BigDecimal(e.getParamWeightage());
		
		else if(e.getParamName().equals("audit_rating"))
			auditRatWeight = new BigDecimal(e.getParamWeightage());
		
		else if(e.getParamName().equals("daily_counter"))
			counterWeight = new BigDecimal(e.getParamWeightage());
		
		else if(e.getParamName().equals("distance_rating"))
			disRatWeight = new BigDecimal(e.getParamWeightage());
		
		else if(e.getParamName().equals("daily_job_ceiling"))
			dailyJobCeiling = new BigDecimal(e.getParamWeightage());
//		else
//			System.out.println("unmatched rating found in CalculateEasyfixerRatingImpl :"+e.getParamName());
	}
	counter = ((dailyJobCeiling.subtract(counter)).divide(dailyJobCeiling)).multiply(new BigDecimal("10"));
	
	//System.out.println(""+cusRatWeight+","+auditRatWeight+","+counterWeight+","+disRatWeight);
	BigDecimal Finalparam = ((cusRat.multiply(new BigDecimal("2").multiply(cusRatWeight))).add( //(cusRat*2*cusRatWeight)
							auditRat.multiply(new BigDecimal("2").multiply(auditRatWeight))).add(//(auditRat*2*auditRatWeight)
							counter.multiply(counterWeight)).add(//+((dailyJobCeiling-counter)*10*counterWeight/dailyJobCeiling)
							disRat.multiply(disRatWeight))).divide(new BigDecimal(40));
							
	Finalparam = Finalparam.setScale(4,BigDecimal.ROUND_HALF_EVEN);
	easyfixer.setFinalWeightage(""+Finalparam);
	
	//System.out.println();
	
	return Finalparam;
	}
	catch(Exception e){
		e.printStackTrace();
		return new BigDecimal("0");
	}
	
}

}
