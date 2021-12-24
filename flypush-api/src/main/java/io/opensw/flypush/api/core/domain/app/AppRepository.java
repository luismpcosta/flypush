package io.opensw.flypush.api.core.domain.app;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppRepository {

	App create( App app );

	App update( App app );

	int delete( Integer applicationId );

	App load( Integer applicationId );

	int updateLogo( Integer applicationId, String logo );

	Page< App > loadAll( Pageable page );

	boolean validateKeyAndSecret( String key, String secret );

	Integer loadAppId( String key, String secret );

}
