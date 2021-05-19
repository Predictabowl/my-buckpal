package my.buckpal.application.service.exceptions;

import my.buckpal.account.domain.Money;

public class ThresholdExceededException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ThresholdExceededException(Money threshold, Money actual) {
		super(String.format(
				"Maximum threshold for transferring money exceeded: tried to transfer %s but threshold is %s!", actual,
				threshold));
	}
}
