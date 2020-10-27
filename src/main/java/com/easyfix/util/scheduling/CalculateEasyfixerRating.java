package com.easyfix.util.scheduling;

import java.math.BigDecimal;
import java.util.List;

import com.easyfix.easyfixers.model.Easyfixers;
import com.easyfix.util.utilityFunction.model.EasyfixerRatingParameters;

public interface CalculateEasyfixerRating {
	public BigDecimal getRating(Easyfixers easyfixer,List<EasyfixerRatingParameters> paramList);
}
