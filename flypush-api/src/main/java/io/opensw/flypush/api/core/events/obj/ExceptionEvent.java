package io.opensw.flypush.api.core.events.obj;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExceptionEvent  implements AppEvent {
	
	/**
	 * Gets the clazz.
	 *
	 * @return the clazz
	 */
	@Getter
	private final String clazz;
	
	/**
	 * Gets the exception.
	 *
	 * @return the exception
	 */
	@Getter
	private final Exception exception;
	
	
	/**
	 * Creates event
	 *
	 * @param clazz to method/class when occurs exception
	 * @param exception that was throw
	 * @return the exception event
	 */
	public static ExceptionEvent create( final String clazz, final Exception exception ) {
		return new ExceptionEvent( clazz, exception );
	}
}
