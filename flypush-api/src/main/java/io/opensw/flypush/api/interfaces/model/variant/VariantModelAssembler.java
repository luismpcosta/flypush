package io.opensw.flypush.api.interfaces.model.variant;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import io.opensw.flypush.api.core.domain.variant.Variant;
import io.opensw.flypush.api.interfaces.exceptions.ObjectNotFoundException;
import io.opensw.flypush.api.interfaces.mappers.VariantMapper;
import io.opensw.flypush.api.interfaces.model.LinkConstants;
import io.opensw.flypush.api.interfaces.rest.VariantEndpoints;

@Component
public class VariantModelAssembler extends RepresentationModelAssemblerSupport< Variant, VariantModel > {

	public VariantModelAssembler() {
		super( VariantEndpoints.class, VariantModel.class );
	}

	@Override
	public VariantModel toModel( Variant variant ) {
		if ( variant == null ) {
			throw new ObjectNotFoundException();
		}

		VariantModel model = VariantMapper.INSTANCE.toModel( variant );

		Link self = linkTo( methodOn( VariantEndpoints.class ).load( variant.getApplicationId(), variant.getId() ) )
				.withRel( LinkConstants.SELF )
				.withType( HttpMethod.GET.name() );

		Link delete = linkTo( methodOn( VariantEndpoints.class ).delete( variant.getApplicationId(), variant.getId() ) )
				.withRel( LinkConstants.DELETE )
				.withType( HttpMethod.DELETE.name() );

//		Link update = linkTo( methodOn( VariantEndpoints.class ).update( variant.getApplicationId(), variant.getId(), null ) )
//				.withRel( LinkConstants.UPDATE )
//				.withType( HttpMethod.PUT.name() );
		
		model.add( self );
//		model.add( update );
		model.add( delete );

		return model;
	}

}
