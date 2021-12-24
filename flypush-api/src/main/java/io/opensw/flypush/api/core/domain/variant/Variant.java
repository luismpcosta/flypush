package io.opensw.flypush.api.core.domain.variant;

import static io.opensw.flypush.api.database.generated.tables.EngineVariant.ENGINE_VARIANT;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;

import org.jooq.Record;

import io.opensw.flypush.api.core.enums.VariantType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@AllArgsConstructor
@Slf4j
public class Variant implements Serializable {

	private static final long serialVersionUID = 4278163191939916638L;

	private Integer id;

	private Integer applicationId;

	private String name;

	private String description;

	private String secret;

	private String apiKey;

	private VariantType type;

	private Map< String, Object > config;

	private byte [] configFile;

	private boolean active;

	private Instant created_at;

	private String createdBy;

	private Instant updatedAt;

	private String updatedBy;

	public static Variant recordMapper( Record record ) {
		log.debug( "Variant Asterisk Mapper {}", record );

		return Variant.builder().id( record.getValue( ENGINE_VARIANT.ID ) )
				.applicationId( record.getValue( ENGINE_VARIANT.APPLICATION_ID ) )
				.name( record.getValue( ENGINE_VARIANT.NAME ) )
				.description( record.getValue( ENGINE_VARIANT.DESCRIPTION ) )
				.secret( record.getValue( ENGINE_VARIANT.SECRET ) )
				.apiKey( record.getValue( ENGINE_VARIANT.API_KEY ) )
				.type( VariantType.valueOf( record.getValue( ENGINE_VARIANT.TYPE ) ) )
				.config( record.getValue( ENGINE_VARIANT.CONFIG ) )
				.active( record.getValue( ENGINE_VARIANT.ACTIVE ) )
				.created_at( record.getValue( ENGINE_VARIANT.CREATED_AT ) )
				.createdBy( record.getValue( ENGINE_VARIANT.CREATED_BY ) )
				.updatedAt( record.getValue( ENGINE_VARIANT.UPDATED_AT ) )
				.updatedBy( record.getValue( ENGINE_VARIANT.UPDATED_BY ) ).build();
	}
}
