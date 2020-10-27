package com.easyfix.user.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.easyfix.settings.model.City;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class User implements Serializable{
	
	private int userId;
	private String userCode;
	private String officialEmail;
	private String userName;
	private int cityId;	
	private int userStatus;
	
	@JsonProperty("role")
	private Role roleObj;
	@JsonProperty("clientId")
	private int loginClient;
	
	private String insertDate;
	private String updateDate;
	private int updatedBy;	
	private int loginStatus;
	private String sessionId;
	
	@JsonIgnore
	private int roleId;
	@JsonIgnore
	private String picture;
	private String manageCities;
	@JsonIgnore
	private String menues;
	@JsonIgnore
	private String mobileNo;
	@JsonIgnore
	private String alternateNo;
	@JsonIgnore
	private City cityObj;
	@JsonIgnore
	private String password;
	@JsonIgnore
	private List<Role> menuList;
	@JsonIgnore
	private String address;
	@JsonIgnore
	private String city;
	@JsonIgnore
	private String contact;
	@JsonIgnore
	private int displayJobDashboard;
	@JsonIgnore
	private Timestamp loginLogoutDateTime;// for CRM efficiency
	@JsonIgnore
	private String loginLogoutAction;     // for CRM efficiency
	@JsonIgnore
	private long crmActiveTime;				 // for CRM efficiency
	
		
	public User() {
		// TODO Auto-generated constructor stub
	}
	public int getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(int loginStatus) {
		this.loginStatus = loginStatus;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public int getUserId() {
		return userId;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + "]";
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	public String getOfficialEmail() {
		return officialEmail;
	}
	public void setOfficialEmail(String officialEmail) {
		this.officialEmail = officialEmail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public City getCityObj() {
		return cityObj;
	}
	public void setCityObj(City cityObj) {
		this.cityObj = cityObj;
	}
	public int getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(int userStatus) {
		this.userStatus = userStatus;
	}
	
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public Role getRoleObj() {
		return roleObj;
	}
	public void setRoleObj(Role roleObj) {
		this.roleObj = roleObj;
	}
	public String getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(String insertDate) {
		this.insertDate = insertDate;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public int getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getManageCities() {
		return manageCities;
	}
	public void setManageCities(String manageCities) {
		this.manageCities = manageCities;
	}
	public String getMenues() {
		return menues;
	}
	public void setMenues(String menues) {
		this.menues = menues;
	}
	public List<Role> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<Role> menuList) {
		this.menuList = menuList;
	}
	public int getLoginClient() {
		return loginClient;
	}
	public void setLoginClient(int loginClient) {
		this.loginClient = loginClient;
	}
	public String getAddress() {
		return address;
	}
	public String getCity() {
		return city;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public int getDisplayJobDashboard() {
		return displayJobDashboard;
	}
	public void setDisplayJobDashboard(int displayJobDashboard) {
		this.displayJobDashboard = displayJobDashboard;
	}
	public Timestamp getLoginLogoutDateTime() {
		return loginLogoutDateTime;
	}
	public String getLoginLogoutAction() {
		return loginLogoutAction;
	}
	public void setLoginLogoutDateTime(Timestamp loginLogoutDateTime) {
		this.loginLogoutDateTime = loginLogoutDateTime;
	}
	public void setLoginLogoutAction(String loginLogoutAction) {
		this.loginLogoutAction = loginLogoutAction;
	}
	public long getCrmActiveTime() {
		return crmActiveTime;
	}
	public void setCrmActiveTime(long crmActiveTime) {
		this.crmActiveTime = crmActiveTime;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getAlternateNo() {
		return alternateNo;
	}
	public void setAlternateNo(String alternateNo) {
		this.alternateNo = alternateNo;
	}
	

}
