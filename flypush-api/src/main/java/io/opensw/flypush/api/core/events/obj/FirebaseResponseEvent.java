package io.opensw.flypush.api.core.events.obj;

import io.opensw.flypush.api.core.domain.installation.InstallationMinimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FirebaseResponseEvent  implements AppEvent {
	
	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	@Getter
	private final String response;
	
	/**
	 * Gets the installation.
	 *
	 * @return the installation
	 */
	@Getter
	private final InstallationMinimal installation;
	
	/**
	 * Creates event
	 *
	 * @param response with result of firebase sent
	 * @param installation with data of the installation
	 * @return the firebase response event
	 */
	public static FirebaseResponseEvent create( final String response, final InstallationMinimal installation ) {
		return new FirebaseResponseEvent( response, installation );
	}
}
