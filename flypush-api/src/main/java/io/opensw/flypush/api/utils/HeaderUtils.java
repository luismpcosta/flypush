package io.opensw.flypush.api.utils;

import java.nio.charset.Charset;
import java.util.Base64;

import javax.servlet.http.HttpServletRequest;

import io.opensw.flypush.api.core.obj.BasicAuth;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class HeaderUtils.
 */
@Slf4j
public class HeaderUtils {

	/**
	 * Load username and password from Authorization header
	 *
	 * @param request http servlet request
	 * @return basic auth with username and password or null if not exists
	 */
	public static BasicAuth loadBasicAuth( HttpServletRequest request ) {
		try {
			final String header = request.getHeader( "Authorization" );
			if ( header == null || header.isEmpty() ) {
				return null;
			}
			final String [] auth = new String( Base64.getDecoder().decode( header.split( " " )[1] ), Charset.forName( "UTF8" ) ).split( ":" );

			return BasicAuth.builder().username( auth[ 0 ] ).password( auth[ 1 ] ).build();
		}
		catch ( Exception e ) {
			log.error("Error in HeaderUtils.loadBasicAuth( HttpServletRequest request ), message: {}", e.getMessage() );
		}
		return null;
	}

}
