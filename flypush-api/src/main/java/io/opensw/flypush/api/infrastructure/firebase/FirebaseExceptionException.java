package io.opensw.flypush.api.infrastructure.firebase;

/**
 * The Class FirebaseExceptionException.
 */
public class FirebaseExceptionException extends RuntimeException {

	private static final long serialVersionUID = -1583231798134624482L;

	/**
	 * Instantiates a new firebase exception.
	 *
	 */
	public FirebaseExceptionException() {
		super();
	}
	
	/**
	 * Instantiates a new firebase exception.
	 * 
	 * @param message of exception
	 */
	public FirebaseExceptionException(String message) {
		super(message);
	}


}
