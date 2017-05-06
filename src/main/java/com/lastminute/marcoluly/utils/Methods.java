package com.lastminute.marcoluly.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 
 * @author m.luly Static method utilities
 */
public final class Methods {

	private Methods() {
	}

	public static BigDecimal createBigDecimalWithRoundingModeAndScale(double val) {
		return createBigDecimal(val).setScale(2, RoundingMode.HALF_UP);
	}

	public static BigDecimal createBigDecimalWithRoundingModeAndScale(BigDecimal val) {
		return val.setScale(2, RoundingMode.HALF_UP);
	}

	public static BigDecimal createBigDecimal(double val) {
		return new BigDecimal(val);
	}
}
