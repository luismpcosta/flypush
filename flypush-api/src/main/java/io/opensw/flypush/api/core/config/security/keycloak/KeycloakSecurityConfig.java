package io.opensw.flypush.api.core.config.security.keycloak;

import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticatedActionsFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakAuthenticationProcessingFilter;
import org.keycloak.adapters.springsecurity.filter.KeycloakSecurityContextRequestFilter;
import org.keycloak.adapters.springsecurity.management.HttpSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import lombok.RequiredArgsConstructor;

/**
 * The Class KeycloakSecurityConfig.
 */
@SuppressWarnings( { "rawtypes", "unchecked" } )
@Profile( { "!insecure" } )
@KeycloakConfiguration
@EnableWebSecurity
@RequiredArgsConstructor
public class KeycloakSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

	/**
	 * Registers the KeycloakAuthenticationProvider with the authentication
	 * manager.
	 *
	 * @param auth quthentication manager builder
	 * @throws Exception the exception
	 */
	@Autowired
	public void configureGlobal( final AuthenticationManagerBuilder auth ) throws Exception {

		final SimpleAuthorityMapper authorityMapper = new SimpleAuthorityMapper();
		authorityMapper.setPrefix( "" );
		authorityMapper.setConvertToUpperCase( true );

		final KeycloakAuthenticationProvider keycloakAuthenticationProvider = new KeycloakAuthenticationProvider();
		keycloakAuthenticationProvider.setGrantedAuthoritiesMapper( authorityMapper );

		auth.authenticationProvider( keycloakAuthenticationProvider );
	}

	/**
	 * Defines the session authentication strategy.
	 *
	 * @return the session authentication strategy
	 */
	@Bean
	@Override
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new NullAuthenticatedSessionStrategy();
	}

	/**
	 * Configure.
	 *
	 * @param http the http
	 * @throws Exception the exception
	 */
	@Override
	protected void configure( final HttpSecurity http ) throws Exception {
		super.configure( http );
		http
				.csrf().disable()
				.cors().disable()
				.sessionManagement().sessionCreationPolicy( SessionCreationPolicy.STATELESS )
				.and()
				.authorizeRequests()
				.anyRequest()
				.authenticated();
	}

	/**
	 * Configure.
	 *
	 * @param web the web
	 */
	@Override
	public void configure( final WebSecurity web ) {
		// add endpoints here to ignore authentication
		web.ignoring()
				.antMatchers( "/**/device*/**", "/**/sender*/**" );

	}

	/**
	 * Keycloak authentication processing filter registration bean.
	 *
	 * @param filter the filter
	 * @return the filter registration bean
	 */
	@Bean
	public FilterRegistrationBean keycloakAuthenticationProcessingFilterRegistrationBean(
			final KeycloakAuthenticationProcessingFilter filter ) {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean( filter );
		registrationBean.setEnabled( false );
		return registrationBean;
	}

	/**
	 * Keycloak pre auth actions filter registration bean.
	 *
	 * @return the filter registration bean
	 */
	@Bean
	public FilterRegistrationBean keycloakPreAuthActionsFilterRegistrationBean() {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setEnabled( false );
		return registrationBean;
	}

	/**
	 * Keycloak authenticated actions filter bean.
	 *
	 * @param filter the filter
	 * @return the filter registration bean
	 */
	@Bean
	public FilterRegistrationBean keycloakAuthenticatedActionsFilterBean(
			final KeycloakAuthenticatedActionsFilter filter ) {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean( filter );
		registrationBean.setEnabled( false );
		return registrationBean;
	}

	/**
	 * Keycloak security context request filter bean.
	 *
	 * @param filter the filter
	 * @return the filter registration bean
	 */
	@Bean
	public FilterRegistrationBean keycloakSecurityContextRequestFilterBean(
			final KeycloakSecurityContextRequestFilter filter ) {
		final FilterRegistrationBean registrationBean = new FilterRegistrationBean( filter );
		registrationBean.setEnabled( false );
		return registrationBean;
	}

	/**
	 * Http session manager.
	 *
	 * @return the http session manager
	 */
	@Bean
	@Override
	@ConditionalOnMissingBean( HttpSessionManager.class )
	protected HttpSessionManager httpSessionManager() {
		return new HttpSessionManager();
	}
}
