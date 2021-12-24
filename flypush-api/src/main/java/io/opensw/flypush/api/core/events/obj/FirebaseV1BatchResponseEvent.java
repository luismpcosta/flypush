package io.opensw.flypush.api.core.events.obj;

import java.util.List;

import io.opensw.flypush.api.core.domain.installation.InstallationMinimal;
import io.opensw.flypush.api.infrastructure.firebase.legacy.response.FBResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FirebaseV1BatchResponseEvent  implements AppEvent {
	
	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	@Getter
	private final FBResponse response;
	
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
	public static FirebaseV1BatchResponseEvent create( final FBResponse response, final List< InstallationMinimal > installations ) {
		return new FirebaseV1BatchResponseEvent( response, installations );
	}
}
