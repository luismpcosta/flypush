package io.opensw.flypush.api.interfaces.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.opensw.flypush.api.core.annotations.PreAuthorizeApp;
import io.opensw.flypush.api.core.application.SenderService;
import io.opensw.flypush.api.core.command.MessageCommand;
import io.opensw.flypush.api.utils.HeaderUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SenderEndpoints extends ApiAbstractEndpoint {

	private final SenderService senderService;

	@PostMapping( "/sender" )
	@PreAuthorizeApp
	@Operation( summary = "Sent Message to Specific Application",
			description = "Endpoint to sent messages to a specific application, to authenticate application use basic auth with key:master_secret." )
	public ResponseEntity< Void > send( @RequestBody final MessageCommand command, final HttpServletRequest request ) {
		log.debug( "Registry Device with command {}", command );
		
		senderService.send( HeaderUtils.loadBasicAuth( request ), command );
		
		return ResponseEntity.ok().build();
	}

}
