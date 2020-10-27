package com.easyfix.util.utilityFunction.delegate;

import java.util.List;

import com.easyfix.util.utilityFunction.dao.EasyfixerRatingParametersDao;
import com.easyfix.util.utilityFunction.model.EasyfixerRatingParameters;

public class EasyfixerRatingParametersServiceImpl implements EasyfixerRatingParametersService {
	
	private EasyfixerRatingParametersDao easyfixerRatingParametersDao;

	@Override
	public EasyfixerRatingParameters getParamDetails(int paramId)
			throws Exception {
		return easyfixerRatingParametersDao.getParamDetails(paramId);
	}

	@Override
	public int addUpdateParam(EasyfixerRatingParameters param) throws Exception {
		return easyfixerRatingParametersDao.addUpdateParam(param);
	}

	@Override
	public List<EasyfixerRatingParameters> getParamList() throws Exception {
		return easyfixerRatingParametersDao.getParamList();
	}
	
	
	

	public EasyfixerRatingParametersDao getEasyfixerRatingParametersDao() {
		return easyfixerRatingParametersDao;
	}

	public void setEasyfixerRatingParametersDao(
			EasyfixerRatingParametersDao easyfixerRatingParametersDao) {
		this.easyfixerRatingParametersDao = easyfixerRatingParametersDao;
	}

}
