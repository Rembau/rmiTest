package client;

import compute.*;
import java.math.*;

public class Pi implements Task {
	private static final long serialVersionUID = 1L;
	private static final BigDecimal ZERO = BigDecimal.valueOf(0);
	private static final BigDecimal ONE = BigDecimal.valueOf(1);
	private static final BigDecimal FOUR = BigDecimal.valueOf(4);
	private static final int roundingMode = BigDecimal.ROUND_HALF_EVEN;
	private int digits;

	public Pi(int digits) {
		this.digits = digits;
	}

	public Object execute() {
		return computePi(digits);
	}

	/***************************************************
	 * pi/4 = 4*arctan(1/5) - arctan(1/239)
	 ****************************************************/
	public static BigDecimal computePi(int digits) {
		int scale = digits + 5;
		BigDecimal arctan1_5 = arctan(5, scale);
		BigDecimal arctan1_239 = arctan(239, scale);
		BigDecimal pi = arctan1_5.multiply(FOUR).subtract(arctan1_239)
				.multiply(FOUR);
		return pi.setScale(digits, BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal arctan(int inverseX, int scale) {
		BigDecimal result, numer, term;
		BigDecimal invX = BigDecimal.valueOf(inverseX);
		BigDecimal invX2 = BigDecimal.valueOf(inverseX * inverseX);
		numer = ONE.divide(invX, scale, roundingMode);
		result = numer;
		int i = 1;
		do {
			numer = numer.divide(invX2, scale, roundingMode);
			int denom = 2 * i + 1;
			term = numer.divide(BigDecimal.valueOf(denom), scale, roundingMode);
			if ((i % 2) != 0) {
				result = result.subtract(term);
			} else {
				result = result.add(term);
			}
			i++;
		} while (term.compareTo(ZERO) != 0);
		return result;
	}
}
