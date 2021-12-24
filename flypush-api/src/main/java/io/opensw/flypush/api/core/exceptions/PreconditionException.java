package io.opensw.flypush.api.core.exceptions;

import static org.zalando.problem.Status.CONFLICT;

/**
 * The Class EntityExistsException.
 */
public class PreconditionException extends AppProblems {

	private static final long serialVersionUID = -1645981752013835282L;

	/** The Constant TITLE. */
	private static final String TITLE = "Precondition";

	/** The Constant DETAIL. */
	private static final String DETAIL = "Precondition failed!";

	/**
	 * Instantiates a new exception.
	 *
	 */
	public PreconditionException() {
		super(
				TITLE,
				CONFLICT,
				DETAIL
		);
		this.addAdditionalData( ERROR_CODE_KEY, ErrorCodesProblems.PRECONDITION_EXCEPTION );
	}
	
	/**
	 * Instantiates a new exception.
	 * 
	 * @param detail exception message
	 * @param code to identify exception
	 */
	public PreconditionException(String detail, String code) {
		super(
				TITLE,
				CONFLICT,
				detail
		);
		this.addAdditionalData( ERROR_CODE_KEY, code );
	}

}
