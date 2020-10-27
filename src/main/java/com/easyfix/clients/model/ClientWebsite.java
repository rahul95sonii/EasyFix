package com.easyfix.clients.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="tbl_client_website")
public class ClientWebsite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "client_login_id", unique = true, nullable = false)
	private Integer clientLoginId;
	
	@Column(name="client_id")
	private Integer clientId;
	
	@Column(name="login_name")
	private String loginName;
	
	@Column(name="login_password")
	private String loginPassword;

	@Column(name="status")
	private Integer status;
	
	

	public Integer getClientId() {
		return clientId;
	}

	public String getLoginName() {
		return loginName;
	}

	public String getLoginPassword() {
		return loginPassword;
	}

	

	public void setClientId(Integer clientId) {
		this.clientId = clientId;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getClientLoginId() {
		return clientLoginId;
	}

	public void setClientLoginId(Integer clientLoginId) {
		this.clientLoginId = clientLoginId;
	}
	
}
