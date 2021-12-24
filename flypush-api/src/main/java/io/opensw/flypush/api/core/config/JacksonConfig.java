package io.opensw.flypush.api.core.config;

import java.time.Instant;
import java.util.TimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;

import io.opensw.flypush.api.interfaces.serializers.InstantDeserializer;
import io.opensw.flypush.api.interfaces.serializers.InstantSerializerTimeZone;


/**
 * Jackson Mapper configuration
 */
@Configuration
public class JacksonConfig {

	@Bean
	@Primary
	ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();

		mapper.configure( SerializationFeature.INDENT_OUTPUT, false );
		mapper.disable( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES );

		mapper.configure( DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS, false );
		mapper.configure( SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false );
		mapper.configure( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false );
		mapper.configure( SerializationFeature.FAIL_ON_EMPTY_BEANS, false );

		mapper.setTimeZone( TimeZone.getTimeZone( "UTC" ) );

		mapper.registerModule(new JavaTimeModule());

		mapper.setSerializationInclusion( JsonInclude.Include.NON_NULL );

		// Converts properties to snake_case
		mapper.setPropertyNamingStrategy( new PropertyNamingStrategy.SnakeCaseStrategy() );


		JavaTimeModule javaTimeModule = new JavaTimeModule();
		javaTimeModule.addSerializer( Instant.class, new InstantSerializerTimeZone() );
		javaTimeModule.addDeserializer( Instant.class, new InstantDeserializer() );

		mapper.registerModules(
				javaTimeModule,
				problemModule(),
				constraintViolationProblemModule(),
				new ParameterNamesModule( JsonCreator.Mode.PROPERTIES ) );

		return mapper;
	}
	
	/**
	 * Problem module.
	 *
	 * @return the problem module
	 */
	@Bean
	public ProblemModule problemModule() {
		return new ProblemModule();
	}

	/**
	 * Constraint violation problem module.
	 *
	 * @return the constraint violation problem module
	 */
	@Bean
	public ConstraintViolationProblemModule constraintViolationProblemModule() {
		return new ConstraintViolationProblemModule();
	}

	
}
