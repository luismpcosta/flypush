package io.opensw.flypush.api.core.exceptions;

import static org.zalando.problem.Status.CONFLICT;

/**
 * The Class EntityConfigFileExistsException.
 */
public class EntityConfigFileExistsException extends AppProblems {

	private static final long serialVersionUID = -1645981752013835282L;

	/** The Constant TITLE. */
	private static final String TITLE = "Entity";

	/** The Constant DETAIL. */
	private static final String DETAIL = "Entity with same config file exists!";

	/**
	 * Instantiates a new not found exception.
	 *
	 */
	public EntityConfigFileExistsException() {
		super(
				TITLE,
				CONFLICT,
				DETAIL
		);
		this.addAdditionalData( ERROR_CODE_KEY, ErrorCodesProblems.ENTITY_CONFIG_FILE_EXISTS_EXCEPTION );
	}

}
