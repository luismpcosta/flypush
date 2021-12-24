package io.opensw.flypush.api.core.events.obj;

import java.util.List;

import com.google.firebase.messaging.BatchResponse;

import io.opensw.flypush.api.core.domain.installation.InstallationMinimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FirebaseBatchResponseEvent  implements AppEvent {
	
	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	@Getter
	private final BatchResponse response;
	
	/**
	 * Gets the installation.
	 *
	 * @return the installation
	 */
	@Getter
	private final List< InstallationMinimal > installations;
	
	/**
	 * Creates event
	 *
	 * @param response with result of firebase batch sent
	 * @param installations with list of the installations data
	 * @return the firebase batch response event
	 */
	public static FirebaseBatchResponseEvent create( final BatchResponse response, final List< InstallationMinimal > installations ) {
		return new FirebaseBatchResponseEvent( response, installations );
	}
}
