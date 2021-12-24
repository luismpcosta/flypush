package io.opensw.flypush.api.core.application.firebase;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.BatchResponse;

import io.opensw.flypush.api.Constants;
import io.opensw.flypush.api.core.command.MessageCommand;
import io.opensw.flypush.api.core.domain.installation.InstallationMinimal;
import io.opensw.flypush.api.core.domain.variant.VariantMinimal;
import io.opensw.flypush.api.core.events.obj.ExceptionEvent;
import io.opensw.flypush.api.core.events.obj.FirebaseBatchResponseEvent;
import io.opensw.flypush.api.core.events.obj.FirebaseResponseEvent;
import io.opensw.flypush.api.infrastructure.firebase.FirebaseHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class FirebaseService {

	private final FirebaseHandler firebaseHandler;

	private final ApplicationEventPublisher eventPublisher;

	public boolean sendVariantMessage( final VariantMinimal variantMinimal, final InstallationMinimal installationMinimal,
			final MessageCommand command ) {
		log.debug( "FirebaseService send variant message {}", command );
		try {
			String response = firebaseHandler.sendMessage(
					variantMinimal.getUuid(),
					variantMinimal.getConfig().get( Constants.CONFIG_FILE_CONTENT ).toString(),
					FirebaseUtils.buildMessage( command, installationMinimal.getDeviceToken() )
			);

			// trigger event to add logs of message sent
			eventPublisher.publishEvent( FirebaseResponseEvent.create( response, installationMinimal ) );

			return true;
		}
		catch ( Exception e ) {
			eventPublisher.publishEvent( ExceptionEvent.create( this.getClass().getCanonicalName(), e ) );
		}

		return false;
	}

	public boolean sendVariantMulticastMessage( final VariantMinimal variantMinimal, final List< InstallationMinimal > installations,
			final MessageCommand command ) {
		log.debug( "FirebaseService send variant multicast message {}", command );
		try {
			BatchResponse response = firebaseHandler.sendMulticastMessage(
					variantMinimal.getUuid(),
					variantMinimal.getConfig().get( Constants.CONFIG_FILE_CONTENT ).toString(),
					FirebaseUtils.buildMulticastMessage(
							command, installations.stream()
									.map( InstallationMinimal::getDeviceToken )
									.collect( Collectors.toList() )
					)
			);

			// trigger event to add logs of message batch sent
			eventPublisher.publishEvent( FirebaseBatchResponseEvent.create( response, installations ) );

			return true;
		}
		catch ( Exception e ) {
			eventPublisher.publishEvent( ExceptionEvent.create( this.getClass().getCanonicalName(), e ) );
		}

		return false;
	}

}
