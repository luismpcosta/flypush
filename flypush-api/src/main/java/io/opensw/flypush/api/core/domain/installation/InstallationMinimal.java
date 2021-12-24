package io.opensw.flypush.api.core.domain.installation;

import static io.opensw.flypush.api.database.generated.tables.EngineInstallation.ENGINE_INSTALLATION;

import java.io.Serializable;

import org.jooq.Record;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@AllArgsConstructor
@Slf4j
public class InstallationMinimal implements Serializable {

	private static final long serialVersionUID = 2699356958275843100L;

	private Integer id;

	private String alias;

	private String deviceToken;

	public static InstallationMinimal recordMapper( Record record ) {
		log.debug( "Installation Asterisk Mapper {}", record );

		return InstallationMinimal.builder().id( record.getValue( ENGINE_INSTALLATION.ID ) )
				.alias( record.getValue( ENGINE_INSTALLATION.ALIAS ) )
				.deviceToken( record.getValue( ENGINE_INSTALLATION.DEVICE_TOKEN ) )
				.build();
	}

}
