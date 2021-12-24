package io.opensw.flypush.api.database.jooq;


/**
 * The Class JsonbEncodingException.
 */
public class JsonbEncodingException extends RuntimeException {

	
	private static final long serialVersionUID = 1355611884524739112L;

	/**
	 * Instantiates a new jsonb encoding exception.
	 *
	 * @param cause the cause
	 */
	public JsonbEncodingException( Throwable cause ) {
		super( cause );
	}

	/**
	 * Instantiates a new jsonb encoding exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public JsonbEncodingException( String message, Throwable cause ) {
		super( message, cause );
	}
}
