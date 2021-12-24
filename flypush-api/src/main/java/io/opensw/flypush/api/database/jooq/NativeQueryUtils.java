package io.opensw.flypush.api.database.jooq;

import java.util.ArrayList;
import java.util.List;

import org.jooq.Field;
import org.jooq.SortField;
import org.jooq.impl.DSL;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import io.opensw.flypush.api.utils.StrUtils;

/**
 * Utility class to help build JPA native queries with Jooq support.
 */
public class NativeQueryUtils {



	/**
	 * Adds sort with the given {@link Pageable}.
	 *
	 * @param pageable      the {@link Pageable} object with sort
	 * @param useFieldAlias to use field alias instead of table alias
	 * @return List of {@link SortField}
	 */
	public static List< SortField< ? > > sort( Pageable pageable, boolean useFieldAlias ) {

		final List< SortField< ? > > sorts = new ArrayList<>();

		if ( pageable.getSort().isEmpty() ) {
			return sorts;
		}

		pageable.getSort().forEach( order -> {

			Field< Object > sortField;

			if ( order instanceof CustomOrder ) {

				CustomOrder customOrder = (CustomOrder) order;
				sortField = StringUtils.hasText( customOrder.getColumn() )
						? DSL.field( useFieldAlias ? DSL.name( customOrder.getColumn() ) : DSL.name( customOrder.getTable(), customOrder.getColumn() )
						)
						: DSL.field( order.getProperty() );
			}
			else {
				sortField = DSL.field( StrUtils.toSnakeCase( order.getProperty() ) );
			}

			sorts.add(
					order.getDirection().isAscending() ? sortField.asc().nullsLast() : sortField.desc().nullsLast()
			);
		} );

		return sorts;
	}


}
