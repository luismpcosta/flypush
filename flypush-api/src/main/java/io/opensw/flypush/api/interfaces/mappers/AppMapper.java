package io.opensw.flypush.api.interfaces.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import io.opensw.flypush.api.core.domain.app.App;
import io.opensw.flypush.api.interfaces.model.app.AppModel;

/**
 * The Interface AppMapper
 */
@Named( "AppMapper" )
@Mapper
public interface AppMapper {

	/** The instance */
	AppMapper INSTANCE = Mappers.getMapper( AppMapper.class );

	/**
	 * Entity to Model (interface return)
	 * 
	 * @param app the object to convert
	 * @return AppModel 
	 */
	AppModel toModel( App app );

}
