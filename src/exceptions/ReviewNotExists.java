package exceptions;

/**
 * Exception class for reviews.
 * 
 * @author Pucariello Giovanni
 * @author Plantone Vincenzo
 *
 */
public class ReviewNotExists extends Exception {
	
	/**
	 * Default private serial_ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Class constructor with no parameters
	 */
	public ReviewNotExists() {
		super();
	}

	/**
	 * Class constructor with parameter
	 * 
	 * @param message
	 */
	public ReviewNotExists(String message) {
		super(message);
	}

}
