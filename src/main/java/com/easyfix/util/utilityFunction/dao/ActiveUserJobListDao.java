package com.easyfix.util.utilityFunction.dao;

import java.util.List;

import com.easyfix.Jobs.model.Jobs;
import com.easyfix.user.model.User;

public interface ActiveUserJobListDao {
public List<Jobs> getActiveJobList(int...flag) throws Exception;
public List<User> getACtiveUserList(int status) throws Exception;
public void assignJobToUser(User user,Jobs job)throws Exception;
}
