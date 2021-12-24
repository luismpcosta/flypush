package io.opensw.flypush.api.interfaces.exceptions;

import static org.zalando.problem.Status.NOT_FOUND;

import io.opensw.flypush.api.core.exceptions.AppProblems;
import io.opensw.flypush.api.core.exceptions.ErrorCodesProblems;

/**
 * The Class ObjectNotFoundException.
 */
public class ObjectNotFoundException extends AppProblems {

	private static final long serialVersionUID = -2921181579482076321L;

	/** The Constant TITLE. */
	private static final String TITLE = "Object";

	/** The Constant DETAIL. */
	private static final String DETAIL = "Object not found!";

	/**
	 * Instantiates a new not found exception.
	 *
	 */
	public ObjectNotFoundException() {
		super(
				TITLE,
				NOT_FOUND,
				DETAIL
		);
		this.addAdditionalData( ERROR_CODE_KEY, ErrorCodesProblems.NOT_FOUND_EXCEPTION );
	}

}
