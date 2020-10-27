package com.easyfix.util.triggers.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils
{
	private static final String format= "yyyy-MM-dd";
	
	public static String getYesterdayDate()throws Exception{
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return dateFormat.format(cal.getTime());
	}
	
	public static String getTodaysDate()throws Exception{
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}
	public static String getLastDayOfPreviousMonth() throws Exception{
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DATE,-1);
		return dateFormat.format(cal.getTime());
	}
	public static String getFirstDayOfPreviousMonth() throws Exception{
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DATE,-1);
		cal.set(Calendar.DATE,1);
		return dateFormat.format(cal.getTime());
	}
	public static String getFirstDayOfCurrentMonth() throws Exception{
		DateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		//cal.add(Calendar.DATE,-1);
		//cal.set(Calendar.DATE,1);
		return dateFormat.format(cal.getTime());
	}
	public static void main(String arg[]){
		try {
			System.out.println(getFirstDayOfCurrentMonth());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
