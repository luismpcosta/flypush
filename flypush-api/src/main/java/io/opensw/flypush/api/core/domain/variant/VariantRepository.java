package io.opensw.flypush.api.core.domain.variant;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VariantRepository {

	Variant create( Variant variant );

	Variant update( Variant variant );

	int delete( Integer applicationId, Integer variantId );

	Variant load( Integer applicationId, Integer variantId );

	Page< Variant > loadAll( Integer applicationId, Pageable page );

	boolean validateKeyAndSecret( String key, String secret );

	Integer loadVariantId( String key, String secret );

	List< VariantMinimal > loadVariantMinimalByApp( Integer appId );

}
