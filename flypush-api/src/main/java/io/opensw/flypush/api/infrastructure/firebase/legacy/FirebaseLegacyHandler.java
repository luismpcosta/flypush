package io.opensw.flypush.api.infrastructure.firebase.legacy;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import io.opensw.flypush.api.infrastructure.firebase.FirebaseExceptionException;
import io.opensw.flypush.api.infrastructure.firebase.legacy.request.FBMessage;
import io.opensw.flypush.api.infrastructure.firebase.legacy.request.FBMulticastMessage;
import io.opensw.flypush.api.infrastructure.firebase.legacy.response.FBResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class FirebaseLegacyHandler {

	private static final String FB_API_V1_URL = "https://fcm.googleapis.com/fcm/send";

	private static final String FB_KEY_HEADER = "key=";

	private final RestTemplate restTemplate;

	public FBResponse sendMessage( final String key, final FBMessage message ) {
		try {
			HttpHeaders headers = this.requestHeaders( key );

			HttpEntity< FBMessage > entity = new HttpEntity<>( message, headers );

			ResponseEntity< FBResponse > response = restTemplate.postForEntity( FB_API_V1_URL, entity, FBResponse.class );

			if ( response.hasBody() ) {
				return response.getBody();
			}
			throw new FirebaseExceptionException( "Firebase API V1 response without body." );
		}
		catch ( Exception e ) {
			log.error( "Error in legacy send firebase notification" );
			throw new FirebaseExceptionException( e.getMessage() );
		}
	}

	public FBResponse sendMulticastMessage( final String key, final FBMulticastMessage message ) {
		try {
			HttpHeaders headers = this.requestHeaders( key );

			HttpEntity< FBMulticastMessage > entity = new HttpEntity<>( message, headers );

			ResponseEntity< FBResponse > response = restTemplate.postForEntity( FB_API_V1_URL, entity, FBResponse.class );

			if ( response.hasBody() ) {
				return response.getBody();
			}
			throw new FirebaseExceptionException( "Firebase API V1 multicast response without body." );
		}
		catch ( Exception e ) {
			log.error( "Error in legacy send firebase notification" );
			throw new FirebaseExceptionException( e.getMessage() );
		}
	}

	private HttpHeaders requestHeaders( final String key ) {
		// create headers
		HttpHeaders headers = new HttpHeaders();
		// set `content-type` header
		headers.setContentType( MediaType.APPLICATION_JSON );
		// set `accept` header
		headers.setAccept( Collections.singletonList( MediaType.APPLICATION_JSON ) );
		// add authorization header
		headers.add( "Authorization", FB_KEY_HEADER.concat( key ) );

		return headers;
	}

}
