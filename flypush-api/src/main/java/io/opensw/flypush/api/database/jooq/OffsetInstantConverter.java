package io.opensw.flypush.api.database.jooq;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

import org.jooq.Converter;

public class OffsetInstantConverter implements Converter<OffsetDateTime, Instant> {

	private static final long serialVersionUID = 9142903873486979654L;

	@Override
	public Instant from( final OffsetDateTime timestamp ) {
		return timestamp == null ? null : timestamp.toInstant();
	}

	@Override
	public OffsetDateTime to( final Instant instant ) {
		return instant == null ? null : OffsetDateTime.ofInstant( instant, ZoneId.of( "UTC" ) );
	}

	@Override
	public Class<OffsetDateTime> fromType() {
		return OffsetDateTime.class;
	}

	@Override
	public Class<Instant> toType() {
		return Instant.class;
	}

}
