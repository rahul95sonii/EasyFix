package com.easyfix.util.utilityFunction.dao;

import java.util.List;
import com.easyfix.util.utilityFunction.model.EasyfixerRatingParameters;

public interface EasyfixerRatingParametersDao {

	EasyfixerRatingParameters getParamDetails(int paramId) throws Exception;
	public int addUpdateParam(EasyfixerRatingParameters param) throws Exception;
	public List<EasyfixerRatingParameters> getParamList() throws Exception;
}
