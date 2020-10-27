package com.easyfix.util;

import java.math.BigDecimal;

public class RateCardCalculations {
	private BigDecimal amount;// private long amount;
	private BigDecimal minEasyfixerFee; //private long minEasyfixerFee;
	private BigDecimal easyfixFeeFixed;//private long easyfixFeeFixed;
	private BigDecimal easyfixFeeVariable;//private double easyfixFeeVariable;
	private BigDecimal overheadFixed;//private long overheadFixed;
	private BigDecimal overheadVariable;//private double overheadVariable;
	private BigDecimal clientfeeFixed;//private long clientfeeFixed;
	private BigDecimal clientfeeVariable;//private double clientfeeVariable;
	RateCardCalculations(String amount,String minEasyfixerFee,String easyfixFeeFixed,String easyfixFeeVariable,
			String overheadFixed,String overheadVariable,String clientfeeFixed,String clientfeeVariable){
		this.amount = new BigDecimal(amount);
		this.minEasyfixerFee =  new BigDecimal(minEasyfixerFee);
		this.easyfixFeeFixed =new BigDecimal(easyfixFeeFixed);
		this.easyfixFeeVariable = new BigDecimal(easyfixFeeVariable);
		this.overheadFixed = new BigDecimal(overheadFixed);
		this.overheadVariable = new BigDecimal(overheadVariable);
		this.clientfeeFixed = new BigDecimal(clientfeeFixed);
		this.clientfeeVariable = new BigDecimal(clientfeeVariable);
	}

	public BigDecimal getOverheadShare(){
		BigDecimal ef_conv_fee = (easyfixFeeVariable.multiply(amount)).add(easyfixFeeFixed);//(long) ((easyfixFeeVariable*amount)+easyfixFeeFixed);
		BigDecimal overhead;
		BigDecimal factor1;
		BigDecimal intitiy1 = amount.subtract(minEasyfixerFee);//amount-minEasyfixerFee;
		if(ef_conv_fee.compareTo(intitiy1)!=1)
		//if(ef_conv_fee<=intitiy1)
			factor1 = ef_conv_fee;
		else 
			factor1 = intitiy1;
		BigDecimal factor2= (amount.subtract(factor1)).multiply(overheadVariable).add(overheadFixed);//(long)((amount-factor1)*overheadVariable + overheadFixed);
		BigDecimal factor3 = amount.subtract(factor1).subtract(minEasyfixerFee);//(amount-factor1-minEasyfixerFee);
		//if(factor2<=factor3)
		if(factor2.compareTo(factor3)!=1)	
		overhead = factor2;
		else 
			overhead = factor3;
		//long overhead = (long)((amount-ef_conv_fee)*overheadVariable+overheadFixed);
		//System.out.println(overhead);
		return overhead;
		
	}
	public BigDecimal getClientShare(){
		BigDecimal ef_conv_fee = easyfixFeeVariable.multiply(amount).add(easyfixFeeFixed);//(long) ((easyfixFeeVariable*amount)+easyfixFeeFixed);
		BigDecimal cl_share = (amount.subtract(ef_conv_fee)).multiply(clientfeeVariable).add(clientfeeFixed);//(long)((amount-ef_conv_fee)*clientfeeVariable + clientfeeFixed);
		//System.out.println(cl_share);
		return cl_share;
	}
	public BigDecimal getEasyfixShare(){
		BigDecimal factor1;
		BigDecimal intitiy1 = amount.subtract(minEasyfixerFee);//amount-minEasyfixerFee; 
		BigDecimal ef_conv_fee = easyfixFeeVariable.multiply(amount).add(easyfixFeeFixed);//(long) ((easyfixFeeVariable*amount)+easyfixFeeFixed);
		if(ef_conv_fee.compareTo(intitiy1)!=1)
			factor1 = ef_conv_fee;
		else 
			factor1 = intitiy1;
		
		BigDecimal ef_share;
		BigDecimal factor2 = getOverheadShare().subtract(getClientShare()).add(factor1);//getOverheadShare()-getClientShare()+factor1;
		//if(factor2<=0)
		if(factor2.compareTo(new BigDecimal("0"))!=1)
			ef_share = new BigDecimal("0");
		else 
			ef_share = factor2;
			
		
		 
		//long ef_share = getOverheadShare()-getClientShare()+ef_conv_fee;
		//System.out.println(ef_share);
		return ef_share;
	}
	public BigDecimal getEasyfixerShare(){
		BigDecimal efer_share =amount.subtract(getEasyfixShare()).subtract(getClientShare()); //amount-getEasyfixShare()-getClientShare();
//		System.out.println(efer_share);
		return efer_share;
	}
	public String toString(){
		BigDecimal total = getClientShare().add(getEasyfixShare()).add(getEasyfixerShare());
		return ("total= "+total+", overhead="+getOverheadShare()+", client = "+getClientShare()+" "
				+ ",easyfix= "+getEasyfixShare()+", easyfixer = "+getEasyfixerShare());
	}

	public static void main (String arg[]){
		RateCardCalculations r1  = new RateCardCalculations("1312.876","111.32","98.78",".13","67.98",".5","59",".19");
		System.out.println(r1);
	}
}
