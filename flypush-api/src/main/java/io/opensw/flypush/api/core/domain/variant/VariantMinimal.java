package io.opensw.flypush.api.core.domain.variant;

import static io.opensw.flypush.api.database.generated.tables.EngineVariant.ENGINE_VARIANT;

import java.io.Serializable;
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
public class VariantMinimal implements Serializable {

	private static final long serialVersionUID = 7147777057419611408L;

	private Integer id;

	private String uuid;

	private VariantType type;

	private Map< String, Object > config;

	public static VariantMinimal recordMapper( Record record ) {
		log.debug( "Variant Asterisk Mapper {}", record );

		return VariantMinimal.builder().id( record.getValue( ENGINE_VARIANT.ID ) )
				.uuid( record.getValue( ENGINE_VARIANT.UUID ) )
				.config( record.getValue( ENGINE_VARIANT.CONFIG ) )
				.type( VariantType.valueOf( record.getValue( ENGINE_VARIANT.TYPE ) ) )
				.build();
	}
}
