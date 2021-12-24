package io.opensw.flypush.api.core.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.opensw.flypush.api.core.command.RegistryCommand;
import io.opensw.flypush.api.core.domain.installation.Installation;
import io.opensw.flypush.api.core.domain.installation.InstallationRepository;
import io.opensw.flypush.api.core.domain.variant.VariantRepository;
import io.opensw.flypush.api.core.exceptions.ErrorCodesProblems;
import io.opensw.flypush.api.core.exceptions.PreconditionException;
import io.opensw.flypush.api.core.obj.BasicAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class InstallationService {

	private final InstallationRepository installationRepository;

	private final VariantRepository variantRepository;

	@Transactional( propagation = Propagation.REQUIRED )
	public Installation registryDevice( final BasicAuth auth, final RegistryCommand command ) {
		log.debug( "Service to Registry Device with Command {}", command );

		Integer variantId = variantRepository.loadVariantId( auth.getUsername(), auth.getPassword() );
		
		//if variant not found return exception
		if(variantId == null || variantId == 0) {
			throw new PreconditionException( "Variant not found!", ErrorCodesProblems.VARIANT_NOT_FOUND_EXCEPTION );
		}
		
		Installation installation = Installation.builder().alias( command.getAlias() ).deviceToken( command.getDeviceToken() )
				.deviceType( command.getDeviceType() ).operatingSystem( command.getOperatingSystem() ).osVersion( command.getOsVersion() )
				.platform( command.getPlatform() ).build();
		
		return installationRepository.createOrUpdate( variantId, installation );
	}

	@Transactional( propagation = Propagation.REQUIRED )
	public void unregistryDevice( final String deviceToken ) {
		log.debug( "Service to Unregistry Device {}", deviceToken );

	}

	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public Installation loadByToken( final String deviceToken ) {
		log.debug( "Service to Unregistry Device {}", deviceToken );

		return null;
	}

}
