package io.opensw.flypush.api.core.application.firebase;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import io.opensw.flypush.api.core.command.MessageCommand;
import io.opensw.flypush.api.core.domain.installation.InstallationMinimal;
import io.opensw.flypush.api.core.domain.installation.InstallationRepository;
import io.opensw.flypush.api.core.domain.variant.VariantMinimal;
import io.opensw.flypush.api.core.events.obj.ExceptionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FirebaseDispatcher {

	private final InstallationRepository installationRepository;

	private final FirebaseService firebaseService;

	private final FirebaseLegacyService firebaseLegacyService;

	private final ApplicationEventPublisher eventPublisher;

	public boolean sendFirebaseVariant( final VariantMinimal variantMinimal, final MessageCommand command ) {
		log.debug( "Send Firebase Message to Variant {} command {}", variantMinimal, command );

		long count = installationRepository.countInstallationByVariant( variantMinimal.getId(), command.getAlias() );

		// if do not have installations return false
		if ( count == 0 ) {
			return false;
		}
		// when have more than 500 destinataries needs to a paginate method
		// and make it async
		else if ( count >= 500 ) {
			this.sendAsyncFirebaseVariant( variantMinimal, command );

			return true;
		}

		List< InstallationMinimal > installations = installationRepository
				.loadInstallationMinimalByVariant( variantMinimal.getId(), command.getAlias() );

		if ( installations.size() == 1 ) {
			return this.sendVariantMessageChooser( variantMinimal, installations.get( 0 ), command );
		}

		return this.sendVariantMulticastMessageChooser( variantMinimal, installations, command );
	}

	private boolean sendVariantMessageChooser( final VariantMinimal variantMinimal,
			final InstallationMinimal installationMinimal, final MessageCommand command ) {

		// choose message sender based on type.
		switch ( variantMinimal.getType() ) {
		case GOOGLE_FIREBASE_LEGACY:
			return firebaseLegacyService.sendVariantMessage( variantMinimal, installationMinimal, command );

		default:
			return firebaseService.sendVariantMessage( variantMinimal, installationMinimal, command );
		}

	}

	private boolean sendVariantMulticastMessageChooser( final VariantMinimal variantMinimal,
			final List< InstallationMinimal > installations, final MessageCommand command ) {

		// choose message sender based on type.
		switch ( variantMinimal.getType() ) {
		case GOOGLE_FIREBASE_LEGACY:
			return firebaseLegacyService.sendVariantMulticastMessage( variantMinimal, installations, command );

		default:
			return firebaseService.sendVariantMulticastMessage( variantMinimal, installations, command );
		}

	}

	@Async
	private void sendAsyncFirebaseVariant( final VariantMinimal variantMinimal, final MessageCommand command ) {
		log.debug( "Send Firebase Message in Async Task to Variant {} command {}", variantMinimal, command );

		// create pagination config
		PageRequest page = PageRequest.of( 0, 450 );
		// load first page
		Page< InstallationMinimal > result = installationRepository
				.loadInstallationMinimalByVariant( variantMinimal.getId(), command.getAlias(), page );
		try {
			do {
				// increment page
				page.next();

				this.sendVariantMulticastMessageChooser( variantMinimal, result.getContent(), command );

				result = installationRepository.loadInstallationMinimalByVariant( variantMinimal.getId(), command.getAlias(), page );

			} while ( !result.isEmpty() );
		}
		catch ( Exception e ) {
			eventPublisher.publishEvent( ExceptionEvent.create( this.getClass().getCanonicalName(), e ) );
		}
	}

}
