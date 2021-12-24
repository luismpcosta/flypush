package io.opensw.flypush.api.database.jooq;

import org.springframework.data.domain.Sort;

import lombok.Getter;

/**
 * <p>
 * This class extends {@link Sort.Order} to add two more fields: column
 * (database column name) and propertyPath (path to field in object to sort).
 * The column is used in jooq and propertyPath is used in hibernate for sort in
 * joins between tables.
 * </p>
 *
 */
public class CustomOrder extends Sort.Order {

	
	private static final long serialVersionUID = -3640474213134916985L;

	/**
	 * Gets the column.
	 *
	 * @return the column
	 */
	@Getter
	private final String column;

	/**
	 * Gets the query type.
	 *
	 * @return the query type
	 */
	@Getter
	private final QueryType queryType;

	/**
	 * Gets the table.
	 *
	 * @return the table
	 */
	@Getter
	private final String table;

	/**
	 * Gets the property name.
	 *
	 * @return the property name
	 */
	@Getter
	private final String propertyName;

	/**
	 * Creates a new {@link Sort.Order} instance. if order is {@literal null}
	 * then order defaults to {@link Sort#DEFAULT_DIRECTION}
	 *
	 * @param direction    can be {@literal null}, will default to
	 *                     {@link Sort#DEFAULT_DIRECTION}
	 * @param table        table name in database
	 * @param column       column name in database
	 * @param queryType    {@link QueryType} related to this sort
	 * @param propertyName name of the property, typically the name of sort
	 *                     given in pageable
	 */
	public CustomOrder( Sort.Direction direction, String table, String column, QueryType queryType,
			String propertyName ) {
		super( direction, column );
		this.table = table;
		this.column = column;
		this.queryType = queryType;
		this.propertyName = propertyName;
	}

	/**
	 * Overrides the default method
	 *
	 * By default spring uses the getProperty method to create page link but in
	 * B2C, when a resource field does not match the database column we need to
	 * return the propertyName instead of the column name that is used in
	 * database sort.
	 *
	 * By overriding this method we can keep all logic and tweak the page link
	 * generation.
	 *
	 * @return Resource property name
	 */
	@Override
	public String getProperty() {
		return propertyName;
	}

}
