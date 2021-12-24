package io.opensw.flypush.api.core.application;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.opensw.flypush.api.core.command.VariantCommand;
import io.opensw.flypush.api.core.domain.variant.Variant;
import io.opensw.flypush.api.core.domain.variant.VariantRepository;
import io.opensw.flypush.api.core.enums.VariantType;
import io.opensw.flypush.api.core.exceptions.ErrorCodesProblems;
import io.opensw.flypush.api.core.exceptions.PreconditionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class VariantService {

	private final VariantRepository variantRepository;

	@Transactional( propagation = Propagation.REQUIRED )
	public Variant createVariant( final Integer applicationId, final VariantCommand command ) {
		log.debug( "Service to Create Application Variant with Command {}", command );

		if(command.getConfigFile() != null && !command.getConfigFile().isEmpty()) {
			command.addFileContent( new String( Base64.getDecoder().decode( command.getConfigFile().getBytes() ), StandardCharsets.UTF_8 ) );
		}else if(VariantType.GOOGLE_FIREBASE.equals( command.getType() ) ){
			throw new PreconditionException( "Firebase config file was required.", ErrorCodesProblems.CONFIG_FILE_REQUIRED );
		}
		
		return variantRepository.create(
				Variant.builder().applicationId( applicationId ).name( command.getName() ).description( command.getDescription() )
						.config( command.getConfig() ).type( command.getType() )
						.configFile( command.getConfigFile() != null ? command.getConfigFile().getBytes() : null ).build()
		);
	}

//	@Transactional
//	public App updateApplication( final Integer applicationId, final AppCommand command ) {
//		log.debug( "Service to Update Application {} with Command {}", applicationId, command );
//
//		return variantRepository.update(
//				App.builder().id( applicationId ).name( command.getName() ).description( command.getDescription() )
//						.logo( command.getLogo() ).build()
//		);
//	}

	@Transactional( propagation = Propagation.REQUIRED )
	public void deleteVariant( final Integer applicationId, final Integer variantId ) {
		log.debug( "Service to Delete Application {} , final Integer variantId {}", applicationId, variantId );

		variantRepository.delete( applicationId, variantId );
	}

	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public Variant loadVariant( final Integer applicationId, final Integer variantId ) {
		log.debug( "Service to Load Application {} Variant {}", applicationId, variantId );

		return variantRepository.load( applicationId, variantId );
	}

	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public Page< Variant > loadAllApplicationVariants( final Integer applicationId, final Pageable page ) {
		log.debug( "Service to Load All Application {} Variant Page {}", applicationId, page );

		return variantRepository.loadAll( applicationId, page );
	}
}
