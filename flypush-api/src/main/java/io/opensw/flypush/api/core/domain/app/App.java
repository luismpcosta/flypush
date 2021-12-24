package io.opensw.flypush.api.core.domain.app;

import static io.opensw.flypush.api.database.generated.tables.EngineApplication.ENGINE_APPLICATION;

import java.io.Serializable;
import java.time.Instant;

import org.jooq.Record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@AllArgsConstructor
@Slf4j
public class App implements Serializable {

	private static final long serialVersionUID = -8842888459261104393L;

	private Integer id;

	private String name;

	private String description;

	private String masterSecret;

	private String apiKey;

	private String logo;

	private boolean active;

	private Instant created_at;

	private String createdBy;

	private Instant updatedAt;

	private String updatedBy;

	public static App recordMapper( Record record ) {
		log.debug( "App Asterisk Mapper {}", record );
		
		return App.builder().id( record.getValue( ENGINE_APPLICATION.ID ) )
				.name( record.getValue( ENGINE_APPLICATION.NAME ) )
				.description( record.getValue( ENGINE_APPLICATION.DESCRIPTION ) )
				.masterSecret( record.getValue( ENGINE_APPLICATION.MASTER_SECRET ) )
				.apiKey( record.getValue( ENGINE_APPLICATION.API_KEY ) )
				.logo( record.getValue( ENGINE_APPLICATION.LOGO ) )
				.active( record.getValue( ENGINE_APPLICATION.ACTIVE ) )
				.created_at( record.getValue( ENGINE_APPLICATION.CREATED_AT ) )
				.createdBy( record.getValue( ENGINE_APPLICATION.CREATED_BY ) )
				.updatedAt( record.getValue( ENGINE_APPLICATION.UPDATED_AT ) )
				.updatedBy( record.getValue( ENGINE_APPLICATION.UPDATED_BY ) ).build();
	}
}
