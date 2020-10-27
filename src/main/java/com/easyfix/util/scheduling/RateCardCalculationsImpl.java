package com.easyfix.util.scheduling;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RateCardCalculationsImpl implements RateCardCalculations {
	//using builder pattern
	private BigDecimal  amount;// private long amount;
	private BigDecimal minEasyfixerFee; //private long minEasyfixerFee;
	private BigDecimal easyfixFeeFixed;//private long easyfixFeeFixed;
	private BigDecimal easyfixFeeVariable;//private double easyfixFeeVariable;
	private BigDecimal overheadFixed;//private long overheadFixed;
	private BigDecimal overheadVariable;//private double overheadVariable;
	private BigDecimal clientfeeFixed;//private long clientfeeFixed;
	private BigDecimal clientfeeVariable;//private double clientfeeVariable;
	
	/*RateCardCalculationsImpl(String amount,String minEasyfixerFee,String easyfixFeeFixed,String easyfixFeeVariable,
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
	*/
	RateCardCalculationsImpl(CalculatorBuilder builder){
		this.amount = builder.amount;
		this.minEasyfixerFee =  builder.minEasyfixerFee;
		this.easyfixFeeFixed =builder.easyfixFeeFixed;
		this.easyfixFeeVariable =builder.easyfixFeeVariable;
		this.overheadFixed =builder.overheadFixed;
		this.overheadVariable = builder.overheadVariable;
		this.clientfeeFixed =builder.clientfeeFixed;
		this.clientfeeVariable = builder.clientfeeVariable;
	}
	
	public static class CalculatorBuilder{
		//required parameter
		private final BigDecimal  amount;
		//optional
		private  BigDecimal minEasyfixerFee;
		private  BigDecimal easyfixFeeFixed;
		private  BigDecimal easyfixFeeVariable;
		private  BigDecimal overheadFixed;
		private  BigDecimal overheadVariable;
		private  BigDecimal clientfeeFixed;
		private  BigDecimal clientfeeVariable;
		
		public CalculatorBuilder(String  amount){
			this.amount = new BigDecimal(amount);
		}
		public CalculatorBuilder minEasyfixerFee(String val){
			this.minEasyfixerFee = new BigDecimal(val);
			return this;
		}
		public CalculatorBuilder easyfixFeeFixed(String val){
			this.easyfixFeeFixed = new BigDecimal(val);
			return this;
		}
		public CalculatorBuilder easyfixFeeVariable(String val){
			this.easyfixFeeVariable = new BigDecimal(val).divide(new BigDecimal("100"));
			return this;
		}
		public CalculatorBuilder overheadFixed(String val){
			this.overheadFixed = new BigDecimal(val);
			return this;
		}
		public CalculatorBuilder overheadVariable(String val){
			this.overheadVariable = new BigDecimal(val).divide(new BigDecimal("100"));
			return this;
		}
		public CalculatorBuilder clientfeeFixed(String val){
			this.clientfeeFixed = new BigDecimal(val);
			return this;
		}
		public CalculatorBuilder clientfeeVariable(String val){
			this.clientfeeVariable = new BigDecimal(val).divide(new BigDecimal("100"));
			return this;
		}
		
		public RateCardCalculationsImpl build(){
			return new RateCardCalculationsImpl(this);
		}
		
		
		
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
		return ("total= "+total.setScale(2,RoundingMode.HALF_EVEN)+", overhead="+getOverheadShare().setScale(2,RoundingMode.HALF_EVEN)+", client = "+getClientShare().setScale(2,RoundingMode.HALF_EVEN)+" "
				+ ",easyfix= "+getEasyfixShare()+", easyfixer = "+getEasyfixerShare());
	}

	public static void main (String arg[]){
		RateCardCalculationsImpl r1;  //new RateCardCalculationsImpl("1312.876","111.32","98.78",".13","67.98",".5","59",".19");
		r1 = new RateCardCalculationsImpl.CalculatorBuilder("1001").clientfeeFixed("100").clientfeeVariable
				(".19").easyfixFeeFixed("100").easyfixFeeVariable(".12").overheadFixed("100").overheadVariable(".79")
				.minEasyfixerFee("100").build();
		System.out.println(r1);

	}
}
