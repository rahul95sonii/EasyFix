package com.easyfix.settings.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="tbl_zone_city_mapping")
public class ZoneCityMapping {

	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="city_zone_id")
	private int cityZoneId;
	
	@ManyToOne
	@JoinColumn(name="zone_id")
	private ZoneMaster zone;
	
	@ManyToOne
	@JoinColumn(name="city_id")
	private City city;

	
	public int getCityZoneId() {
		return cityZoneId;
	}

	public ZoneMaster getZone() {
		return zone;
	}

	public City getCity() {
		return city;
	}

	public void setCityZoneId(int cityZoneId) {
		this.cityZoneId = cityZoneId;
	}

	public void setZone(ZoneMaster zone) {
		this.zone = zone;
	}

	public void setCity(City city) {
		this.city = city;
	}
	
}
