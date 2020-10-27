package com.easyfix.settings.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonProperty;

@Entity
@Table(name="tbl_city")
public class City {
	
	@JsonProperty("city_id")
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="city_id")
	private int cityId;
	
	@JsonProperty("city_name")
	@Column(name="city_name")
	private String cityName;
	
	@JsonProperty("city_status")
	@Column(name="city_status")
	private String cityStatus;
	
	@JsonProperty("maxDistance")
	@Column(name="city_max_distance")
	private String maxDistance;
	
	@JsonProperty("display")
	@Column(name="display")
	private int display;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy="city")
	private List<ZoneCityMapping> zones = new ArrayList<>();
	

	public List<ZoneCityMapping> getZones() {
		return zones;
	}

	public void setZones(List<ZoneCityMapping> zones) {
		this.zones = zones;
	}

	public City() {
		
	}
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public int getCityId() {
		return cityId;
	}
	public void setCityId(int cityId) {
		this.cityId = cityId;
	}

	public String getCityStatus() {
		return cityStatus;
	}

	public void setCityStatus(String cityStatus) {
		this.cityStatus = cityStatus;
	}

	public String getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(String maxDistance) {
		this.maxDistance = maxDistance;
	}

	public int getDisplay() {
		return display;
	}

	public void setDisplay(int display) {
		this.display = display;
	}


}
