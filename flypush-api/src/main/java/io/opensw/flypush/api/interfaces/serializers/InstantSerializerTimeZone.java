package io.opensw.flypush.api.interfaces.serializers;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class InstantSerializerTimeZone extends StdSerializer<Instant> {

	private static final long serialVersionUID = -5891884238732612321L;

	private static final String FORMATTER = "yyyy-MM-dd'T'HH:mm:ss";

	public InstantSerializerTimeZone() {
		super( Instant.class );
	}

	@Override
	public void serialize( Instant value, JsonGenerator gen, SerializerProvider provider ) throws IOException {
		gen.writeString( value.atZone( ZoneOffset.UTC ).format( DateTimeFormatter.ofPattern( FORMATTER ) ) );
	}

}
