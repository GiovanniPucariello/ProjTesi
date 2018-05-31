package exceptions;

/**
 * Exception class for empty lists.
 * 
 * @author Pucariello Giovanni
 * @author Plantone Vincenzo
 *
 */
public class EmptyListException extends Exception {
	
	/**
	 * Default private serial_ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Class constructor with no parameters
	 */
	public EmptyListException() {
		super();
	}

	/**
	 * Class constructor with parameter
	 * 
	 * @param message
	 */
	public EmptyListException(String message) {
		super(message);
	}

}
