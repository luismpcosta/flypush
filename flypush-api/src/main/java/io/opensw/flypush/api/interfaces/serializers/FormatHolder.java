package io.opensw.flypush.api.interfaces.serializers;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

class FormatHolder {

	final DateTimeFormatter formatter;

	public FormatHolder( DateTimeFormatter formatter ) {
		this.formatter = formatter;
	}

	Instant parse( String source ) {
		return formatter.parse( source, Instant::from );
	}

	@Override
	public String toString() {
		return formatter.toString();
	}


	static DateTimeFormatter build( String pattern ) {
		return build( pattern, false );
	}

	static DateTimeFormatter build( String pattern, boolean resetHours ) {
		final DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder().appendPattern( pattern );
		if ( resetHours ) {
			builder.parseDefaulting( ChronoField.NANO_OF_DAY, 0 );
		}
		return builder.toFormatter().withZone( ZoneId.of( "UTC" ) );
	}
}
