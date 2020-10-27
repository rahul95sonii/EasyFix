package com.easyfix.Jobs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
@Entity
@Table(name= "tbl_job_image")
public class JobImage {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="image_id")
	private int imageId;
	
	@Column(name="job_id")
	private int jobId;
	
	@Column(name="job_stage")
	private int jobStage;
	
	@Column(name="created_date")
	private Timestamp createdDate;
	
	@Column(name="updated_date")
	private Timestamp updatedDate;
	@Column(name = "created_by")
	private int ceatedBy;
	@Column(name = "updated_by")
	private int updatedBy;
	
	public int getCeatedBy() {
		return ceatedBy;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setCeatedBy(int ceatedBy) {
		this.ceatedBy = ceatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public int getImageId() {
		return imageId;
	}

	public int getJobId() {
		return jobId;
	}

	public int getJobStage() {
		return jobStage;
	}

	public String getImage() {
		return image;
	}

	public int getStatus() {
		return status;
	}

	public void setImageId(int imageId) {
		this.imageId = imageId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public void setJobStage(int jobStage) {
		this.jobStage = jobStage;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name="image")
	private String image;
	
	@Column(name="status")
	private int status;
	
}
