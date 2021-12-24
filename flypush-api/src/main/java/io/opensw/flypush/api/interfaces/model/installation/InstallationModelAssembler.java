package io.opensw.flypush.api.interfaces.model.installation;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import io.opensw.flypush.api.core.domain.installation.Installation;
import io.opensw.flypush.api.interfaces.exceptions.ObjectNotFoundException;
import io.opensw.flypush.api.interfaces.mappers.InstallationMapper;
import io.opensw.flypush.api.interfaces.model.LinkConstants;
import io.opensw.flypush.api.interfaces.rest.DeviceEndpoints;

@Component
public class InstallationModelAssembler extends RepresentationModelAssemblerSupport< Installation, InstallationModel > {

	public InstallationModelAssembler() {
		super( DeviceEndpoints.class, InstallationModel.class );
	}

	@Override
	public InstallationModel toModel( Installation installation ) {
		if ( installation == null ) {
			throw new ObjectNotFoundException();
		}

		InstallationModel model = InstallationMapper.INSTANCE.toModel( installation );

		Link self = linkTo( methodOn( DeviceEndpoints.class ).load( installation.getDeviceToken() ) )
				.withRel( LinkConstants.SELF )
				.withType( HttpMethod.GET.name() );

		Link delete = linkTo( methodOn( DeviceEndpoints.class ).delete( installation.getDeviceToken() ) )
				.withRel( LinkConstants.DELETE )
				.withType( HttpMethod.DELETE.name() );

		
		model.add( self );
		model.add( delete );

		return model;
	}

}
