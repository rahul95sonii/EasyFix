package com.easyfix.home.dao;

import java.util.List;

import com.easyfix.Jobs.model.Jobs;

public interface HomeDao {

	Jobs getUserJobsList(int userId, int roleId) throws Exception;
	Jobs getUserJobsListFromExcel(int userId, int roleId) throws Exception;

	List<Jobs> getClientJobsList(Jobs jobObj, int loginClient) throws Exception;
	void deactiveLeadJob(int jobId, String comments, int userId) throws Exception;
	Jobs getAppjobList(int userId,int roleId) throws Exception;

}
