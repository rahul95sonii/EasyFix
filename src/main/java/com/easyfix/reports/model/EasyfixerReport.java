package com.easyfix.reports.model;

import java.sql.Date;

public class EasyfixerReport {
	private int clientId;
	private String startDate;
	private String endDate;
	private int flag;
	private String dateRange;
	private int efrId;
	private String efrName;
	private Date dateOfJoining;
	private long activeDays;
	private int moreThen250;
	private int lessThenOrEqual250;
	private float efrIncome;
	private int cancledJobs;
	private float cusRat;
	private long dis;
	private long avgTAT;
	private String efrServiceType;
	private int status;
	private String ndmName;
	private String efrDoc;
	private float balance;
	private String city;
	private String mobNo;
	private String address;
	private String appUser;
	private String appVersionName;
	private String zone;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getDateRange() {
		return dateRange;
	}
	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}
	public int getEfrId() {
		return efrId;
	}
	public void setEfrId(int efrId) {
		this.efrId = efrId;
	}
	public String getEfrName() {
		return efrName;
	}
	public void setEfrName(String efrName) {
		this.efrName = efrName;
	}
	public Date getDateOfJoining() {
		return dateOfJoining;
	}
	public long getActiveDays() {
		return activeDays;
	}
	public int getMoreThen250() {
		return moreThen250;
	}
	public int getLessThenOrEqual250() {
		return lessThenOrEqual250;
	}
	public float getEfrIncome() {
		return efrIncome;
	}
	public int getCancledJobs() {
		return cancledJobs;
	}
	public float getCusRat() {
		return cusRat;
	}
	public long getDis() {
		return dis;
	}
	public long getAvgTAT() {
		return avgTAT;
	}
	public String getEfrServiceType() {
		return efrServiceType;
	}
	public String getAppUser() {
		return appUser;
	}
	public void setAppUser(String appUser) {
		this.appUser = appUser;
	}
	public int getStatus() {
		return status;
	}
	public String getNdmName() {
		return ndmName;
	}
	public String getEfrDoc() {
		return efrDoc;
	}
	public void setDateOfJoining(Date dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}
	public void setActiveDays(long activeDays) {
		this.activeDays = activeDays;
	}
	public void setMoreThen250(int moreThen250) {
		this.moreThen250 = moreThen250;
	}
	public void setLessThenOrEqual250(int lessThenOrEqual250) {
		this.lessThenOrEqual250 = lessThenOrEqual250;
	}
	public String getAppVersionName() {
		return appVersionName;
	}
	public void setAppVersionName(String appVersionName) {
		this.appVersionName = appVersionName;
	}
	public void setEfrIncome(float efrIncome) {
		this.efrIncome = efrIncome;
	}
	public void setCancledJobs(int cancledJobs) {
		this.cancledJobs = cancledJobs;
	}
	public void setCusRat(float cusRat) {
		this.cusRat = cusRat;
	}
	public void setDis(long dis) {
		this.dis = dis;
	}
	public void setAvgTAT(long avgTAT) {
		this.avgTAT = avgTAT;
	}
	public void setEfrServiceType(String efrServiceType) {
		this.efrServiceType = efrServiceType;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setNdmName(String ndmName) {
		this.ndmName = ndmName;
	}
	public void setEfrDoc(String efrDoc) {
		this.efrDoc = efrDoc;
	}
	public int getClientId() {
		return clientId;
	}
	public void setClientId(int clientId) {
		this.clientId = clientId;
	}
	public String getMobNo() {
		return mobNo;
	}
	public String getAddress() {
		return address;
	}
	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	
}
