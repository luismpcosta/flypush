package io.opensw.flypush.api.interfaces.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import io.opensw.flypush.api.core.domain.variant.Variant;
import io.opensw.flypush.api.interfaces.model.variant.VariantModel;

/**
 * The Interface VariantMapper
 */
@Named( "VariantMapper" )
@Mapper
public interface VariantMapper {

	/** The instance */
	VariantMapper INSTANCE = Mappers.getMapper( VariantMapper.class );

	/**
	 * Entity to Model (interface return)
	 * 
	 * @param variant the object to convert
	 * @return VariantModel 
	 */
	VariantModel toModel( Variant variant );

}
