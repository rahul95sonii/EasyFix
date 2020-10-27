package com.easyfix.home.delegate;

import java.util.List;

import com.easyfix.Jobs.model.Jobs;
import com.easyfix.home.dao.HomeDao;

public class HomeServiceImpl implements HomeService {
	
	private HomeDao homeDao;

	public HomeServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Jobs getUserJobsList(int userId, int roleId) throws Exception {
		return homeDao.getUserJobsList(userId,roleId);
	}
	
	@Override
	public Jobs getUserJobsListFromExcel(int userId, int roleId) throws Exception {
		// TODO Auto-generated method stub
		return homeDao.getUserJobsListFromExcel(userId, roleId);
	}

	
	@Override
	public List<Jobs>  getClientJobsList(Jobs jobObj, int loginClient) throws Exception {
		return homeDao.getClientJobsList(jobObj,loginClient);
	}
	@Override
	public void deactiveLeadJob(int jobId, String comments, int userId)	throws Exception {
		homeDao.deactiveLeadJob(jobId, comments, userId);
		
	}
	
	
	
	
	
	
	
	

	public HomeDao getHomeDao() {
		return homeDao;
	}

	public void setHomeDao(HomeDao homeDao) {
		this.homeDao = homeDao;
	}

	@Override
	public Jobs getAppjobList(int userId, int roleId) throws Exception {
		return homeDao.getAppjobList(userId, roleId);
	}

	

}
