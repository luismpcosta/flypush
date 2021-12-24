package io.opensw.flypush.api.core.annotations;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.opensw.flypush.api.core.domain.variant.VariantRepository;
import io.opensw.flypush.api.core.obj.BasicAuth;
import io.opensw.flypush.api.utils.HeaderUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class AuditAspect.
 */
@Aspect
@Slf4j
@RequiredArgsConstructor
@Component
public class PreAuthorizeVariantAspect {

	private final VariantRepository variantRepository;

	/**
	 * Intercepts all methods annotated with @{@link PreAuthorizeVariant}.
	 */
	@Pointcut( "execution(@io.opensw.flypush.api.core.annotations.PreAuthorizeVariant * *(..))" )
	public void preAuthorizeVariant() {
		// method without body
	}

	/**
	 * PreAuthorizeVariant Resolver.
	 *
	 * @param point  to validate variant autentication
	 * @param method to alidate variant autentication
	 * @throws Throwable exception
	 * @return method execution
	 */
	@Around( "preAuthorizeVariant() && @annotation(method)" )
	public Object preAuthorizeVariantResolver( ProceedingJoinPoint point, PreAuthorizeVariant method ) throws Throwable {
		log.trace( "Intercepting invocation of method {}", point.getSignature().getName() );
		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if ( requestAttributes == null ) {
			throw new AccessDeniedException( "Variant authentication failed" );
		}
		HttpServletRequest request = ( (ServletRequestAttributes) requestAttributes ).getRequest();
		//load basic auth from request headers
		BasicAuth basicAuth = HeaderUtils.loadBasicAuth( request );

		if ( basicAuth == null ||
				!variantRepository.validateKeyAndSecret( basicAuth.getUsername(), basicAuth.getPassword() ) ) {
			throw new AccessDeniedException( "Variant authentication failed" );
		}

		return point.proceed();
	}

}
