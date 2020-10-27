package com.easyfix.settings.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="tbl_zone_master")
public class ZoneMaster {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="zone_id")
	private Integer zoneId;
	
	@Column(name="zone_name")
	private String zoneName;

	public Integer getZoneId() {
		return zoneId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	
	
}
