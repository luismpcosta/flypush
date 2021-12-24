package io.opensw.flypush.api.interfaces.model.app;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import io.opensw.flypush.api.core.domain.app.App;
import io.opensw.flypush.api.interfaces.exceptions.ObjectNotFoundException;
import io.opensw.flypush.api.interfaces.mappers.AppMapper;
import io.opensw.flypush.api.interfaces.model.LinkConstants;
import io.opensw.flypush.api.interfaces.rest.AppEndpoints;

@Component
public class AppModelAssembler extends RepresentationModelAssemblerSupport< App, AppModel > {

	public AppModelAssembler() {
		super( AppEndpoints.class, AppModel.class );
	}

	@Override
	public AppModel toModel( App app ) {
		if ( app == null ) {
			throw new ObjectNotFoundException();
		}

		AppModel model = AppMapper.INSTANCE.toModel( app );

		Link self = linkTo( methodOn( AppEndpoints.class ).load( app.getId() ) )
				.withRel( LinkConstants.SELF )
				.withType( HttpMethod.GET.name() );

		Link delete = linkTo( methodOn( AppEndpoints.class ).delete( app.getId() ) )
				.withRel( LinkConstants.DELETE )
				.withType( HttpMethod.DELETE.name() );

		Link update = linkTo( methodOn( AppEndpoints.class ).update( app.getId(), null ) )
				.withRel( LinkConstants.UPDATE )
				.withType( HttpMethod.PUT.name() );
		
		Link logo = linkTo( methodOn( AppEndpoints.class ).updateLogo( app.getId(), null ) )
				.withRel( "logo" )
				.withType( HttpMethod.PUT.name() );

		model.add( self );
		model.add( update );
		model.add( delete );
		model.add( logo );

		return model;
	}

}
