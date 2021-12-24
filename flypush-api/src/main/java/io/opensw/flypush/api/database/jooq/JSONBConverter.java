package io.opensw.flypush.api.database.jooq;

/**
 * The Class JSONBConverter.
 */
public class JSONBConverter extends AbstractJsonbConverter< Jsonb > {

	
	private static final long serialVersionUID = 3116829767745985252L;

	/**
	 * Instantiates a new JSONB converter.
	 */
	public JSONBConverter() {
		super( Jsonb.class );
	}
}
