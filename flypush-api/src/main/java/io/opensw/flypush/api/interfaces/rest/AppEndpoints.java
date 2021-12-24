package io.opensw.flypush.api.interfaces.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.opensw.flypush.api.core.application.AppService;
import io.opensw.flypush.api.core.command.AppCommand;
import io.opensw.flypush.api.core.domain.app.App;
import io.opensw.flypush.api.interfaces.model.app.AppModel;
import io.opensw.flypush.api.interfaces.model.app.AppModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class AppEndpoints extends ApiAbstractEndpoint {

	private final AppService appService;

	private final PagedResourcesAssembler< App > pagedResourcesAssembler;

	private final AppModelAssembler appModelAssembler;

	@PostMapping( "/app" )
	@PreAuthorize( "hasAnyAuthority( 'ADMINISTRATOR' )" )
	@Operation( summary = "Create Application Entry", description = "Endpoint to create a new application." )
	public ResponseEntity< AppModel > create( @RequestBody final AppCommand command ) {
		log.debug( "Create Application with command {}", command );

		return ResponseEntity.ok( appModelAssembler.toModel( appService.createApplication( command ) ) );
	}

	@PutMapping( "/app/{app-id}" )
	@PreAuthorize( "hasAnyAuthority( 'ADMINISTRATOR' )" )
	@Operation( summary = "Update Application Entry", description = "Endpoint to update existing application." )
	public ResponseEntity< AppModel > update( @PathVariable( value = "app-id" ) Integer applicationId,
			@RequestBody final AppCommand command ) {
		log.debug( "Update Application {} with command {}", applicationId, command );

		return ResponseEntity.ok( appModelAssembler.toModel( appService.updateApplication( applicationId, command ) ) );
	}

	@PutMapping( "/app/{app-id}/logo" )
	@PreAuthorize( "hasAnyAuthority( 'ADMINISTRATOR' )" )
	@Operation( summary = "Update Application Logo Entry", description = "Endpoint to update logo of existing application." )
	public ResponseEntity< AppModel > updateLogo( @PathVariable( value = "app-id" ) Integer applicationId,
			@RequestBody final String logo ) {
		log.debug( "Update Application Logo {} logo", applicationId );

		appService.updateApplicationLogo( applicationId, logo );

		return ResponseEntity.ok().build();
	}

	@DeleteMapping( "/app/{app-id}" )
	@PreAuthorize( "hasAnyAuthority( 'ADMINISTRATOR' )" )
	@Operation( summary = "Delete Application Entry", description = "Endpoint to delete existing application." )
	public ResponseEntity< AppModel > delete( @PathVariable( value = "app-id" ) Integer applicationId ) {
		log.debug( "Delete Application {}", applicationId );

		appService.deleteApplication( applicationId );

		return ResponseEntity.noContent().build();
	}

	@GetMapping( "/app/{app-id}" )
	@PreAuthorize( "hasAnyAuthority( 'ADMINISTRATOR' )" )
	@Operation( summary = "Load Application Entry", description = "Endpoint to load existing application." )
	public ResponseEntity< AppModel > load( @PathVariable( value = "app-id" ) Integer applicationId ) {
		log.debug( "Load Application {}", applicationId );

		return ResponseEntity.ok( appModelAssembler.toModel( appService.loadApplication( applicationId ) ) );
	}

	@GetMapping( "/app" )
	@PreAuthorize( "hasAnyAuthority( 'ADMINISTRATOR' )" )
	@Operation( summary = "Load All Application Entries", description = "Endpoint to load all application entries paginated." )
	public ResponseEntity< PagedModel< AppModel > > loadAll( final Pageable page ) {
		log.debug( "Load all application entries paginated {}", page );

		return ResponseEntity.ok( pagedResourcesAssembler.toModel( appService.loadAllApplications( page ), appModelAssembler ) );
	}
	
}
