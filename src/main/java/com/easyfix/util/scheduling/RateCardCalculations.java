package com.easyfix.util.scheduling;

import java.math.BigDecimal;

public interface RateCardCalculations {
	public BigDecimal getOverheadShare();
	public BigDecimal getClientShare();
	public BigDecimal getEasyfixShare();
	public BigDecimal getEasyfixerShare();
}
