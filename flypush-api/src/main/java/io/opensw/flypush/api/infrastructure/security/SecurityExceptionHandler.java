package io.opensw.flypush.api.infrastructure.security;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.opensw.flypush.api.utils.AppExceptionUtils;

@ControllerAdvice
public class SecurityExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String TITLE = "Access";
	
	@ExceptionHandler( { AccessDeniedException.class } )
	public ResponseEntity< Object > handleAccessDeniedException( Exception ex, WebRequest request ) {
		return new ResponseEntity< Object >(
				AppExceptionUtils.toResponseEntity( TITLE, ex.getMessage(), HttpStatus.UNAUTHORIZED.value() ), new HttpHeaders(),
				HttpStatus.UNAUTHORIZED
		);
	}

}
