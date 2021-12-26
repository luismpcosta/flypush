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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.opensw.flypush.api.core.application.VariantService;
import io.opensw.flypush.api.core.command.VariantCommand;
import io.opensw.flypush.api.core.domain.variant.Variant;
import io.opensw.flypush.api.interfaces.model.variant.VariantModel;
import io.opensw.flypush.api.interfaces.model.variant.VariantModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class VariantEndpoints extends ApiAbstractEndpoint {

	private final VariantService variantService;

	private final PagedResourcesAssembler< Variant > pagedResourcesAssembler;

	private final VariantModelAssembler variantModelAssembler;

	@PostMapping( "/app/{app-id}/variant" )
	@PreAuthorize( "hasAnyAuthority( 'FLYPUSH_ADMIN' )" )
	@Operation( summary = "Create Application Variant Entry", description = "Endpoint to create a new application variant." )
	public ResponseEntity< VariantModel > create( @PathVariable( value = "app-id" ) final Integer applicationId,
			@RequestBody final VariantCommand command ) {
		log.debug( "Create Application Variant with command {}", command );

		return ResponseEntity.ok( variantModelAssembler.toModel( variantService.createVariant( applicationId, command ) ) );
	}

//	@Operation( summary = "Update Application Entry", description = "Endpoint to update existing application." )
//	@PutMapping( "/app/{app-id}/variant/{variant-id}" )
//	public ResponseEntity< VariantModel > update( @PathVariable( value = "app-id" ) Integer applicationId,
//			@RequestBody final VariantCommand command ) {
//		log.debug( "Update Application {} with command {}", applicationId, command );
//
//		return ResponseEntity.ok( variantModelAssembler.toModel( variantService.updateVariant( applicationId, command ) ) );
//	}

	@DeleteMapping( "/app/{app-id}/variant/{variant-id}" )
	@PreAuthorize( "hasAnyAuthority( 'FLYPUSH_ADMIN' )" )
	@Operation( summary = "Delete Application Variant Entry", description = "Endpoint to delete existing application variant." )
	public ResponseEntity< Void > delete( @PathVariable( value = "app-id" ) final Integer applicationId,
			@PathVariable( value = "variant-id" ) final Integer variantId ) {
		log.debug( "Delete Application {} Variant {}", applicationId, variantId );

		variantService.deleteVariant( applicationId, variantId );

		return ResponseEntity.noContent().build();
	}

	@GetMapping( "/app/{app-id}/variant/{variant-id}" )
	@PreAuthorize( "hasAnyAuthority( 'FLYPUSH_ADMIN' )" )
	@Operation( summary = "Load Application Variant Entry", description = "Endpoint to load existing application variant." )
	public ResponseEntity< VariantModel > load( @PathVariable( value = "app-id" ) final Integer applicationId,
			@PathVariable( value = "variant-id" ) final Integer variantId ) {
		log.debug( "Load Application {} Variant {}", applicationId, variantId );

		return ResponseEntity.ok( variantModelAssembler.toModel( variantService.loadVariant( applicationId, variantId ) ) );
	}

	@GetMapping( "/app/{app-id}/variant" )
	@PreAuthorize( "hasAnyAuthority( 'FLYPUSH_ADMIN' )" )
	@Operation( summary = "Load All Application Variant Entries",
			description = "Endpoint to load all application variant entries paginated." )
	public ResponseEntity< PagedModel< VariantModel > > loadAll( @PathVariable( value = "app-id" ) final Integer applicationId,
			final Pageable page ) {
		log.debug( "Load all application {} variant entries paginated {}", applicationId, page );

		return ResponseEntity
				.ok(
						pagedResourcesAssembler
								.toModel( variantService.loadAllApplicationVariants( applicationId, page ), variantModelAssembler )
				);
	}
	
}
