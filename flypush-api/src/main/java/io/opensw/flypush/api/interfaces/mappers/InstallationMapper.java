package io.opensw.flypush.api.interfaces.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import io.opensw.flypush.api.core.domain.installation.Installation;
import io.opensw.flypush.api.interfaces.model.installation.InstallationModel;

/**
 * The Interface InstallationMapper
 */
@Named( "InstallationMapper" )
@Mapper
public interface InstallationMapper {

	/** The instance */
	InstallationMapper INSTANCE = Mappers.getMapper( InstallationMapper.class );

	/**
	 * Entity to Model (interface return)
	 * 
	 * @param installation the object to convert
	 * @return InstallationModel 
	 */
	InstallationModel toModel( Installation installation );

}
