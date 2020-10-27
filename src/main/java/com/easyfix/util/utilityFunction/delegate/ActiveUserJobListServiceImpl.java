package com.easyfix.util.utilityFunction.delegate;

import java.util.ArrayList;
import java.util.List;

import com.easyfix.Jobs.model.Jobs;
import com.easyfix.user.model.User;
import com.easyfix.util.utilityFunction.dao.ActiveUserJobListDaoImpl;

public class ActiveUserJobListServiceImpl implements ActiveUserJobListService {

	private ActiveUserJobListDaoImpl activeUserJobListDao;
	@Override
	public List<Jobs> getActiveJobList(int... flag) throws Exception {
		return activeUserJobListDao.getActiveJobList(flag);
	}

	@Override
	public List<User> getACtiveUserList(int status) throws Exception {
		return activeUserJobListDao.getACtiveUserList(status);
	}

	public ActiveUserJobListDaoImpl getActiveUserJobListDao() {
		return activeUserJobListDao;
	}

	public void setActiveUserJobListDao(ActiveUserJobListDaoImpl activeUserJobListDao) {
		this.activeUserJobListDao = activeUserJobListDao;
	}
	public void assignJobsInloginOrder(List<Jobs> jobs,List<User> users){
		//assign jobs to those first who have logged in last
		//user list is already sorted by login time at stored procedure level
		List<Integer> userIdList = new ArrayList<Integer>();
		for(User u: users)
			userIdList.add(u.getUserId());
		int i =0;
		int j=0;
		while(j<users.size() && i<jobs.size() ){
				if(userIdList.contains(jobs.get(i).getJobOwner())){
					++i;
					continue;
				}
					
				activeUserJobListDao.assignJobToUser(users.get(j), jobs.get(i));
				++i;
				++j;
				j%=users.size();
			}	
		}
}
