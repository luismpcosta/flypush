package io.opensw.flypush.api.database.jooq;

import static io.opensw.flypush.api.database.jooq.AppJsonbMapper.decode;
import static io.opensw.flypush.api.database.jooq.AppJsonbMapper.encode;

import org.jooq.JSONB;
import org.jooq.impl.AbstractConverter;

/**
 * The Class AbstractJsonbConverter.
 *
 * @param <T> the generic type
 */
abstract class AbstractJsonbConverter<T> extends AbstractConverter< JSONB, T > {

	
	private static final long serialVersionUID = 6301649602634611389L;

	/** The target. */
	private final Class< T > target;

	/**
	 * Instantiates a new abstract jsonb converter.
	 *
	 * @param target the target
	 */
	AbstractJsonbConverter( Class< T > target ) {
		super( JSONB.class, target );
		this.target = target;
	}

	/**
	 * From.
	 *
	 * @param row the row
	 * @return the t
	 */
	@Override
	public T from( JSONB row ) {
		return row == null || row.data() == null ? null : decode( row.data(), target );
	}

	/**
	 * To.
	 *
	 * @param obj the obj
	 * @return the jsonb
	 */
	@Override
	public JSONB to( T obj ) {
		return obj == null ? null : JSONB.valueOf( encode( obj ) );
	}
}
