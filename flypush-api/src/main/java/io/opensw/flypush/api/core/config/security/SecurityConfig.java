package io.opensw.flypush.api.core.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * The Class SecurityConfig.
 */
@Configuration
@EnableGlobalMethodSecurity( prePostEnabled = true )
public class SecurityConfig {

}
