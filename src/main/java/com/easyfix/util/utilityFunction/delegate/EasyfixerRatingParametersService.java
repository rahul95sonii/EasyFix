package com.easyfix.util.utilityFunction.delegate;

import java.util.List;

import com.easyfix.util.utilityFunction.model.EasyfixerRatingParameters;

public interface EasyfixerRatingParametersService {
	EasyfixerRatingParameters getParamDetails(int paramId) throws Exception;
	public int addUpdateParam(EasyfixerRatingParameters param) throws Exception;
	public List<EasyfixerRatingParameters> getParamList() throws Exception;
}
