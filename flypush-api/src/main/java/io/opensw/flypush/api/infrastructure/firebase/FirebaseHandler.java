package io.opensw.flypush.api.infrastructure.firebase;

import java.io.ByteArrayInputStream;

import org.springframework.stereotype.Component;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FirebaseHandler {

	public String sendMessage( final String identifier, final String config, final Message message ) {
		try {
			FirebaseApp firebaseApp = this.loadFirebaseApp( identifier, config );
			return FirebaseMessaging.getInstance( firebaseApp ).send( message );
		}
		catch ( Exception e ) {
			log.error( "Error in send firebase notification" );
			throw new FirebaseExceptionException( e.getMessage() );
		}
	}

	public BatchResponse sendMulticastMessage( final String identifier, final String config, final MulticastMessage message ) {
		try {
			FirebaseApp firebaseApp = this.loadFirebaseApp( identifier, config );

			return FirebaseMessaging.getInstance( firebaseApp ).sendMulticast( message );
		}
		catch ( Exception e ) {
			log.error( "Error in send firebase notification" );
			throw new FirebaseExceptionException( e.getMessage() );
		}
	}

	private FirebaseApp loadFirebaseApp( final String identifier, final String config ) {
		try {
			// get firabase app by identifier
			FirebaseApp firebaseApp = this.loadFirebaseAppInstance( identifier );

			// if firebase app pis null create entry
			if ( firebaseApp == null ) {
				FirebaseOptions options = FirebaseOptions.builder()
						.setCredentials( GoogleCredentials.fromStream( new ByteArrayInputStream( config.getBytes() ) ) ).build();
				firebaseApp = FirebaseApp.initializeApp( options, identifier );
				log.debug( "Firebase application has been initialized" );
			}

			return firebaseApp;
		}
		catch ( Exception e ) {
			log.error( "Error in load firebase app" );
			throw new FirebaseExceptionException( e.getMessage() );
		}
	}
	
	private FirebaseApp loadFirebaseAppInstance( final String identifier ) {
		try {
			// get firabase app by identifier
			return FirebaseApp.getInstance( identifier );
		}
		catch ( Exception e ) {
			log.debug( e.getMessage() );
		}
		return null;
	}
}
