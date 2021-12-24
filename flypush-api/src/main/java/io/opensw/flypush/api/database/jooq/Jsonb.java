package io.opensw.flypush.api.database.jooq;

import java.util.HashMap;
import java.util.Map;


/**
 * The Class Jsonb.
 */
public class Jsonb extends HashMap<String, Object> {

	
	private static final long serialVersionUID = -6444059364952737142L;

	/**
	 * Instantiates a new jsonb.
	 */
	public Jsonb() {
		super();
	}

	/**
	 * Instantiates a new jsonb.
	 *
	 * @param map the map
	 */
	public Jsonb( Map<String, Object> map ) {
		super( map );
	}
}
