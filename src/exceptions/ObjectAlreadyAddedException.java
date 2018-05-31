package exceptions;

/**
 * Exception class for objects that already exist.
 * 
 * @author Pucariello Giovanni
 * @author Plantone Vincenzo
 *
 */
public class ObjectAlreadyAddedException extends Exception {
	
	/**
	 * Default private serial_ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Class constructor with no parameters
	 */
	public ObjectAlreadyAddedException() {
		super();
	}

	/**
	 * Class constructor with parameter
	 * 
	 * @param message
	 */
	public ObjectAlreadyAddedException(String message) {
		super(message);
	}

}
