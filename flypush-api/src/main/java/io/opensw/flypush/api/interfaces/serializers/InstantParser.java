package io.opensw.flypush.api.interfaces.serializers;

import static io.opensw.flypush.api.interfaces.serializers.FormatHolder.build;

import java.time.Instant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InstantParser {

	static final FormatHolder[] DATE_FORMATS = new FormatHolder[] {
			new FormatHolder( build( "yyyy-MM-dd'T'HH:mm:ss" ) ),
			new FormatHolder( build( "yyyy-MM-dd", true ) )
	};

	static Instant parser( String source ) {
		for ( FormatHolder holder : DATE_FORMATS ) {
			try {
				return holder.parse( source );
			}
			catch ( Exception e ) {
				log.trace( "Error parsing date {} using format {} : Error: {}", source, holder.formatter.toString(), e.getMessage() );
			}
		}
		return null;
	}
}
