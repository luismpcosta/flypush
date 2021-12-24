package io.opensw.flypush.api.core.application;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.opensw.flypush.api.core.domain.variant.VariantRepository;
import io.opensw.flypush.api.core.obj.BasicAuth;
import io.opensw.flypush.api.utils.HeaderUtils;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityService {

	private final HttpServletRequest request;

	private final VariantRepository variantRepository;

	/**
	 * Validate variant authentication
	 *
	 * username = key in variant password = secret in variant
	 * 
	 * @return true, if successful
	 */
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public boolean validateVariantAuth() {
		BasicAuth basicAuth = HeaderUtils.loadBasicAuth( request );

		if ( basicAuth != null &&
				variantRepository.validateKeyAndSecret( basicAuth.getUsername(), basicAuth.getPassword() ) ) {
			return true;
		}

		throw new AccessDeniedException( "Variant authentication failed" );
	}

}
