package io.opensw.flypush.api.infrastructure.web;

import java.net.URI;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;

/**
 * EndpointAdvice class that implements {@link ProblemHandling},
 * that handles all exceptions thrown by services.
 */
@RestControllerAdvice
public class EndpointAdvice implements ProblemHandling {

	/**
	 * Default constraint violation type.
	 *
	 * @return the uri
	 */
	@Override
	public URI defaultConstraintViolationType() {
		return null;
	}

}
