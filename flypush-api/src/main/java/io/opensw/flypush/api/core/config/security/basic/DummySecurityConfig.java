package io.opensw.flypush.api.core.config.security.basic;

import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * The Class DummySecurityConfig.
 */
@Configuration
@EnableWebSecurity
@Profile( value = { "insecure" } )
public class DummySecurityConfig extends WebSecurityConfigurerAdapter {

	/** The properties. */
	private final SecurityProperties properties;

	/**
	 * Instantiates a new dummy security config.
	 *
	 * @param properties the properties
	 */
	public DummySecurityConfig( final SecurityProperties properties ) {
		this.properties = properties;
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
	 * Configure.
	 *
	 * @param auth the auth
	 * @throws Exception the exception
	 */
	@Override
	protected void configure( final AuthenticationManagerBuilder auth ) throws Exception {
		auth.userDetailsService(
				username -> new User(
						properties.getUser().getName(),
						properties.getUser().getPassword(),
						properties.getUser().getRoles().stream().map( SimpleGrantedAuthority::new )
								.collect( Collectors.toList() )
				)
		);
	}

	/**
	 * Configure.
	 *
	 * @param http the http
	 * @throws Exception the exception
	 */
	@Override
	protected void configure( final org.springframework.security.config.annotation.web.builders.HttpSecurity http )
			throws Exception {
		http.csrf().disable();
		http.sessionManagement()
				.sessionCreationPolicy( SessionCreationPolicy.STATELESS )
				.and().httpBasic()
				.and().authorizeRequests()
				.anyRequest().authenticated();
	}
}
