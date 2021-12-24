package io.opensw.flypush.api.core.domain.installation;

import static io.opensw.flypush.api.database.generated.tables.EngineInstallation.ENGINE_INSTALLATION;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.jooq.Record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@AllArgsConstructor
@Slf4j
public class Installation implements Serializable {

	private static final long serialVersionUID = -6828356806041236045L;

	private Integer id;

	private String alias;

	private String deviceToken;

	private String deviceType;

	private String operatingSystem;

	private String osVersion;

	private String platform;

	private List< String > topics;

	private boolean active;

	private Instant created_at;

	private String createdBy;

	private Instant updatedAt;

	private String updatedBy;

	public static Installation recordMapper( Record record ) {
		log.debug( "Installation Asterisk Mapper {}", record );

		return Installation.builder().id( record.getValue( ENGINE_INSTALLATION.ID ) )
				.alias( record.getValue( ENGINE_INSTALLATION.ALIAS ) )
				.deviceToken( record.getValue( ENGINE_INSTALLATION.DEVICE_TOKEN ) )
				.deviceType( record.getValue( ENGINE_INSTALLATION.DEVICE_TYPE ) )
				.operatingSystem( record.getValue( ENGINE_INSTALLATION.OPERATING_SYSTEM ) )
				.osVersion( record.getValue( ENGINE_INSTALLATION.OS_VERSION ) )
				.platform( record.getValue( ENGINE_INSTALLATION.PLATFORM ) )
				.active( record.getValue( ENGINE_INSTALLATION.ACTIVE ) )
				.created_at( record.getValue( ENGINE_INSTALLATION.CREATED_AT ) )
				.createdBy( record.getValue( ENGINE_INSTALLATION.CREATED_BY ) )
				.updatedAt( record.getValue( ENGINE_INSTALLATION.UPDATED_AT ) )
				.updatedBy( record.getValue( ENGINE_INSTALLATION.UPDATED_BY ) ).build();
	}

	public void addTopic( String topic ) {
		if ( this.topics == null ) {
			this.topics = new ArrayList<>();
		}

		this.topics.add( topic );
	}

	public void addTopics( List< String > topics ) {
		if ( this.topics == null ) {
			this.topics = new ArrayList<>();
		}

		this.topics.addAll( topics );
	}
}
