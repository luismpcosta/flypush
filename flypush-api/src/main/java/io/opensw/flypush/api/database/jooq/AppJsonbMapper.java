package io.opensw.flypush.api.database.jooq;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;


/**
 * The Class AppJsonbMapper
 */
public class AppJsonbMapper {

	/** The Constant MAPPER. */
	public static final ObjectMapper MAPPER = new ObjectMapper();

	static {
		MAPPER.configure( DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false );
		MAPPER.configure( SerializationFeature.FAIL_ON_EMPTY_BEANS, false );
		MAPPER.disable( SerializationFeature.WRITE_DATES_AS_TIMESTAMPS );
		MAPPER.registerModules( new JavaTimeModule(), new ParameterNamesModule() );
	}

	/**
	 * Decode.
	 *
	 * @param source to decode
	 * @return jsonb decoded
	 */
	public static Jsonb decode( String source ){
		return decode( source, Jsonb.class );
	}

	/**
	 * Decode.
	 *
	 * @param <T> abstract class
	 * @param source to decode
	 * @param clazz to decode
	 * @return decoded object
	 */
	public static <T> T decode( String source, Class<T> clazz ){
		try{
			return MAPPER.readValue( source, clazz );
		}catch ( Exception e ){
			throw new JsonbEncodingException( e );
		}
	}

	/**
	 * Encode.
	 *
	 * @param object to encode
	 * @return encoded object
	 */
	public static String encode( Jsonb object ){
		try{
			return MAPPER.writeValueAsString( object );
		}catch ( Exception e ){
			throw new JsonbEncodingException( e );
		}
	}

	/**
	 * Encode.
	 *
	 * @param <T> generic class
	 * @param source to encode
	 * @return encoded object
	 */
	public static <T> String encode( T source ){
		try{
			return MAPPER.writeValueAsString( source );
		}catch ( Exception e ){
			throw new JsonbEncodingException( e );
		}
	}

	/**
	 * Convert.
	 *
	 * @param <T> generic class
	 * @param source object to convert
	 * @param target class
	 * @return coverted class
	 */
	public static <T> T convert( Object source, Class<T> target ){
		try{
			return MAPPER.convertValue( source, target );
		}catch ( Exception e ){
			throw new JsonbEncodingException( e );
		}
	}
}
