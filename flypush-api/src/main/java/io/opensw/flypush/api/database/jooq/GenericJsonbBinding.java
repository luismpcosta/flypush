package io.opensw.flypush.api.database.jooq;

import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.Objects;

import org.jooq.Binding;
import org.jooq.BindingGetResultSetContext;
import org.jooq.BindingGetSQLInputContext;
import org.jooq.BindingGetStatementContext;
import org.jooq.BindingRegisterContext;
import org.jooq.BindingSQLContext;
import org.jooq.BindingSetSQLOutputContext;
import org.jooq.BindingSetStatementContext;
import org.jooq.Converter;
import org.jooq.JSONB;
import org.jooq.impl.DSL;


/**
 * The Class GenericJsonbBinding.
 *
 * @param <T> the generic type
 */
public abstract class GenericJsonbBinding<T> implements Binding<JSONB, T> {

	
	private static final long serialVersionUID = 3632609170077134827L;

	/** The converter. */
	private final Converter<JSONB, T> converter;

	/**
	 * Instantiates a new generic jsonb binding.
	 *
	 * @param converter the converter
	 */
	GenericJsonbBinding( Converter<JSONB, T> converter ) {
		this.converter = converter;
	}

	/**
	 * Converter.
	 *
	 * @return the converter
	 */
	@Override
	public Converter<JSONB, T> converter() {
		return converter;
	}

	/**
	 * Sql.
	 *
	 * @param ctx the ctx
	 */
	// Rending a bind variable for the binding context's value and casting it to the json type
	@Override
	public void sql( BindingSQLContext<T> ctx ) {
		// Depending on how you generate your SQL, you may need to explicitly distinguish
		// between jOOQ generating bind variables or inlined literals. If so, use this check:
		// ctx.render().paramType() == INLINED
		ctx.render().visit( DSL.val( ctx.convert( converter() ).value() ) ).sql( "::jsonb" );
	}

	/**
	 * Register.
	 *
	 * @param ctx the ctx
	 * @throws SQLException the SQL exception
	 */
	// Registering VARCHAR types for JDBC CallableStatement OUT parameters
	@Override
	public void register( BindingRegisterContext<T> ctx ) throws SQLException {
		ctx.statement().registerOutParameter( ctx.index(), Types.VARCHAR );
	}

	/**
	 * Sets the.
	 *
	 * @param ctx the ctx
	 * @throws SQLException the SQL exception
	 */
	// Converting the JsonElement to a String value and setting that on a JDBC PreparedStatement
	@Override
	public void set( BindingSetStatementContext<T> ctx ) throws SQLException {
		ctx.statement().setString( ctx.index(), Objects.toString( ctx.convert( converter() ).value(), null ) );
	}

	/**
	 * Gets the.
	 *
	 * @param ctx the ctx
	 * @throws SQLException the SQL exception
	 */
	// Getting a String value from a JDBC ResultSet and converting that to a JsonElement
	@Override
	public void get( BindingGetResultSetContext<T> ctx ) throws SQLException {
		ctx.convert( converter() ).value( JSONB.valueOf( ctx.resultSet().getString( ctx.index() ) ) );
	}

	/**
	 * Gets the.
	 *
	 * @param ctx the ctx
	 * @throws SQLException the SQL exception
	 */
	// Getting a String value from a JDBC CallableStatement and converting that to a JsonElement
	@Override
	public void get( BindingGetStatementContext<T> ctx ) throws SQLException {
		ctx.convert( converter() ).value( JSONB.valueOf( ctx.statement().getString( ctx.index() ) ) );
	}

	/**
	 * Sets the.
	 *
	 * @param ctx the ctx
	 * @throws SQLException the SQL exception
	 */
	// Setting a value on a JDBC SQLOutput (useful for Oracle OBJECT types)
	@Override
	public void set( BindingSetSQLOutputContext<T> ctx ) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	/**
	 * Gets the.
	 *
	 * @param ctx the ctx
	 * @throws SQLException the SQL exception
	 */
	// Getting a value from a JDBC SQLInput (useful for Oracle OBJECT types)
	@Override
	public void get( BindingGetSQLInputContext<T> ctx ) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}
}
