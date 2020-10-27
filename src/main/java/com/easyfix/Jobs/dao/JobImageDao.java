package com.easyfix.Jobs.dao;
import java.util.List;

import com.easyfix.Jobs.model.JobImage;
public interface JobImageDao {

	List<JobImage> getJobImage(JobImage jobImage);
	void addJobImage(JobImage jobImage);	
	
}
