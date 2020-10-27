package com.easyfix.util.utilityFunction.delegate;

import java.util.List;

import com.easyfix.Jobs.model.Jobs;
import com.easyfix.user.model.User;

public interface ActiveUserJobListService {
	public List<Jobs> getActiveJobList(int...flag) throws Exception;
	public List<User> getACtiveUserList(int status) throws Exception;
	public void assignJobsInloginOrder(List<Jobs> jobs,List<User> users) throws Exception;
}
