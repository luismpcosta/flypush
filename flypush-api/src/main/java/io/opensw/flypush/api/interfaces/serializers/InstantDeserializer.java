package io.opensw.flypush.api.interfaces.serializers;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class InstantDeserializer extends StdDeserializer< Instant > {

	private static final long serialVersionUID = 8979927185125408950L;

	public InstantDeserializer() {
		this( null );
	}

	public InstantDeserializer( Class< ? > vc ) {
		super( vc );
	}

	@Override
	public Instant deserialize( JsonParser jp, DeserializationContext ctxt ) throws IOException {

		final JsonNode node = jp.getCodec().readTree( jp );
		final String source = node.textValue();

		final Instant instant = InstantParser.parser( source );
		if ( instant == null ) {
			throw new JsonParseException(
					jp,
					"Unparseable source: \"" + source + "\". Supported formats: "
							+ Arrays.toString( InstantParser.DATE_FORMATS )
			);
		}
		return instant;
	}
}
