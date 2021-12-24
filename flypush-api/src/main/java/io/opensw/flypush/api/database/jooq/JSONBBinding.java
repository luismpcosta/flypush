package io.opensw.flypush.api.database.jooq;


/**
 * The Class JSONBBinding.
 */
public class JSONBBinding extends GenericJsonbBinding<Jsonb> {

	
	private static final long serialVersionUID = -4894270000157207382L;

	/**
	 * Instantiates a new JSONB binding.
	 */
	public JSONBBinding() {
		super( new JSONBConverter() );
	}
}
