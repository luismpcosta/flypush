package io.opensw.flypush.api.core.exceptions;

import static java.util.Objects.nonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.StatusType;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * B2CProblems class that all Exceptions should extend to produce
 * application/problem+json responses
 */
public abstract class AppProblems extends AbstractThrowableProblem {

	private static final long serialVersionUID = -705568830594822044L;

	protected static final String ERROR_CODE_KEY = "error_code";
	
	@JsonProperty
	private final Map< String, Object > additionalData;

	/**
	 * @param title         Title used in error message
	 * @param status        HTTP status of the response message
	 * @param detail        Error detail
	 * @param propertyName  Additional property name
	 * @param propertyValue Additional property value
	 */
	public AppProblems( String title, StatusType status, String detail, String propertyName, Object propertyValue ) {
		this( title, status, detail, Collections.singletonMap( propertyName, propertyValue ) );
	}

	
	/**
	 * @param title   Title used in error message
	 * @param status  HTTP status of the response message
	 * @param detail  Error detail
	 * @param entries List of additional properties to log in error
	 */
	@SafeVarargs
	protected AppProblems( final String title, final StatusType status, final String detail,
			final Map.Entry< String, Object >... entries ) {
		super( null, title, status, detail );
		this.additionalData = new HashMap<>( entries == null ? 0 : entries.length );

		if ( entries != null ) {
			for ( final Map.Entry< String, Object > entry : entries ) {
				this.additionalData.put( entry.getKey(), entry.getValue() );
			}
		}
	}

	/**
	 * @param title          Title used in error message
	 * @param status         HTTP status of the response message
	 * @param detail         Error detail
	 * @param additionalData Map of additional properties to log in error
	 */
	protected AppProblems( final String title, final StatusType status, final String detail,
			final Map< String, Object > additionalData ) {
		super( null, title, status, detail );
		this.additionalData = nonNull( additionalData ) ? new HashMap<>( additionalData ) : null;
	}
	
	/**
	 * @param key          identifier to additional data
	 * @param value        value of additional data
	 */
	public void addAdditionalData(String key, String value){
		this.additionalData.put( key, value );
	}
}
