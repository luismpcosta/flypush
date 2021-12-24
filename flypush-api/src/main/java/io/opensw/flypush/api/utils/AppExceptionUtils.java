package io.opensw.flypush.api.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * The Class AppExceptionUtils.
 */
public class AppExceptionUtils {
	
	private static final String MESSAGE_FIELD = "message";

	private static final String STACKTRACE_FIELD = "stacktrace";

	private static final String DETAIL_FIELD = "detail";

	private static final String TITLE_FIELD = "title";

	private static final String STATUS_FIELD = "status";

	/**
	 * Convert exception to map.
	 *
	 * @param e - exception to process
	 * @return map with exception stack trace
	 */
	public static Map< String, String > toMap( Exception e ) {
		Map< String, String > map = new HashMap<>();
		map.put( MESSAGE_FIELD, e.getMessage() );
		map.put( STACKTRACE_FIELD, ExceptionUtils.getStackTrace( e ) );

		return map;
	}
	
	public static Map< String, Object > toResponseEntity( String title, String message, int status ) {
		Map< String, Object > map = new HashMap<>();
		map.put( TITLE_FIELD, title );
		map.put( DETAIL_FIELD, message );
		map.put( STATUS_FIELD, status );

		return map;
	}

}
