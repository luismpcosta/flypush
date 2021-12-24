package io.opensw.flypush.api.core.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.opensw.flypush.api.core.application.firebase.FirebaseDispatcher;
import io.opensw.flypush.api.core.command.MessageCommand;
import io.opensw.flypush.api.core.domain.app.AppRepository;
import io.opensw.flypush.api.core.domain.variant.VariantMinimal;
import io.opensw.flypush.api.core.domain.variant.VariantRepository;
import io.opensw.flypush.api.core.exceptions.ErrorCodesProblems;
import io.opensw.flypush.api.core.exceptions.PreconditionException;
import io.opensw.flypush.api.core.obj.BasicAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class SenderService {

	private final AppRepository appRepository;

	private final VariantRepository variantRepository;

	private final FirebaseDispatcher firebaseDispatcher;

	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public void send( final BasicAuth auth, final MessageCommand command ) {
		log.debug( "Service to Send Messages to Specific App {} with command {}", auth, command );

		Integer appId = appRepository.loadAppId( auth.getUsername(), auth.getPassword() );
		// if variant not found return exception
		if ( appId == null || appId == 0 ) {
			throw new PreconditionException( "Application not found!", ErrorCodesProblems.APP_NOT_FOUND_EXCEPTION );
		}

		List< VariantMinimal > variants = variantRepository.loadVariantMinimalByApp( appId );
		for ( VariantMinimal variant : variants ) {
			switch ( variant.getType() ) {
			case GOOGLE_FIREBASE:
				firebaseDispatcher.sendFirebaseVariant( variant, command );
				break;

			case GOOGLE_FIREBASE_LEGACY:
				firebaseDispatcher.sendFirebaseVariant(  variant, command );
				break;

			default:
				log.debug( "Not implemented type!" );
				break;
			}
		}
	}

}
