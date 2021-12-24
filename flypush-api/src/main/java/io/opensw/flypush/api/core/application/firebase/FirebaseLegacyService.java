package io.opensw.flypush.api.core.application.firebase;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import io.opensw.flypush.api.core.command.MessageCommand;
import io.opensw.flypush.api.core.domain.installation.InstallationMinimal;
import io.opensw.flypush.api.core.domain.variant.VariantMinimal;
import io.opensw.flypush.api.core.events.obj.ExceptionEvent;
import io.opensw.flypush.api.core.events.obj.FirebaseV1BatchResponseEvent;
import io.opensw.flypush.api.infrastructure.firebase.legacy.FirebaseLegacyHandler;
import io.opensw.flypush.api.infrastructure.firebase.legacy.response.FBResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirebaseLegacyService {

	private final FirebaseLegacyHandler firebaseLegacyHandler;

	private final ApplicationEventPublisher eventPublisher;

	public boolean sendVariantMessage( final VariantMinimal variantMinimal, final InstallationMinimal installationMinimal,
			final MessageCommand command ) {
		log.debug( "FirebaseLegacyService send variant message {}", command );
		try {
			FBResponse response = firebaseLegacyHandler.sendMessage(
					variantMinimal.getConfig().get( "sender_key" ).toString(),
					FirebaseUtils.buildLegacyMessage( command, installationMinimal.getDeviceToken() )
			);

			// trigger event to add logs of message sent
			eventPublisher
					.publishEvent( FirebaseV1BatchResponseEvent.create( response, Collections.singletonList( installationMinimal ) ) );

			return true;
		}
		catch ( Exception e ) {
			eventPublisher.publishEvent( ExceptionEvent.create( this.getClass().getCanonicalName(), e ) );
		}

		return false;
	}

	public boolean sendVariantMulticastMessage( final VariantMinimal variantMinimal, final List< InstallationMinimal > installations,
			final MessageCommand command ) {
		log.debug( "FirebaseLegacyService send variant multicast message {}", command );
		try {
			FBResponse response = firebaseLegacyHandler.sendMulticastMessage(
					variantMinimal.getConfig().get( "sender_key" ).toString(),
					FirebaseUtils.buildLegacyMulticastMessage(
							command, installations.stream()
									.map( InstallationMinimal::getDeviceToken )
									.collect( Collectors.toList() )
					)
			);

			// trigger event to add logs of message batch sent
			eventPublisher.publishEvent( FirebaseV1BatchResponseEvent.create( response, installations ) );

			return true;
		}
		catch ( Exception e ) {
			eventPublisher.publishEvent( ExceptionEvent.create( this.getClass().getCanonicalName(), e ) );
		}

		return false;
	}

}
