package io.opensw.flypush.api.interfaces.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.opensw.flypush.api.core.annotations.PreAuthorizeVariant;
import io.opensw.flypush.api.core.application.InstallationService;
import io.opensw.flypush.api.core.command.RegistryCommand;
import io.opensw.flypush.api.interfaces.model.installation.InstallationModel;
import io.opensw.flypush.api.interfaces.model.installation.InstallationModelAssembler;
import io.opensw.flypush.api.utils.HeaderUtils;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class DeviceEndpoints extends ApiAbstractEndpoint {

	private final InstallationService installationService;

	private final InstallationModelAssembler installationModelAssembler;

	@PostMapping( "/device" )
	@PreAuthorizeVariant
	@Operation( summary = "Registry Device in Variant",
			description = "Endpoint to registry device in a specific variant, to authenticate variant use basic auth with key:secret." )
	public ResponseEntity< InstallationModel > create( @RequestBody final RegistryCommand command, final HttpServletRequest request ) {
		log.debug( "Registry Device with command {}", command );
		
		return ResponseEntity.ok(
				installationModelAssembler.toModel( installationService.registryDevice( HeaderUtils.loadBasicAuth( request ), command ) )
		);
	}

	@DeleteMapping( "/device/{token}" )
	@PreAuthorizeVariant
	@Operation( summary = "Delete Device Installation Entry", description = "Endpoint to delete existing device installation." )
	public ResponseEntity< Void > delete( @PathVariable( value = "token" ) final String token ) {
		log.debug( "Delete Device with Token {}", token );

		return ResponseEntity.noContent().build();
	}

	@GetMapping( "/device/{token}" )
	@PreAuthorizeVariant
	@Operation( summary = "Load Device Installation Entry", description = "Endpoint to load existing device installation." )
	public ResponseEntity< InstallationModel > load( @PathVariable( value = "token" ) final String token ) {
		log.debug( "Load Device with Token {}", token );

		return ResponseEntity.ok( installationModelAssembler.toModel( installationService.loadByToken( token ) ) );
	}

}
