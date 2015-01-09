package de.hackerspacebremen.domain.api;

public interface AuthAttemptService {

	/**
	 * Checks if the maximum authentication attempt is reached.
	 * @param userName
	 * given user name
	 * @return true-> attempt max is reached
	 */
	boolean checkAttemptMax(String userName);
	
	/**
	 * Clears the attempt amounts.
	 */
	void clearAttemptAmounts();
}