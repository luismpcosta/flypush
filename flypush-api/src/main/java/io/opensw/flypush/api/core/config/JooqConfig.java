package io.opensw.flypush.api.core.config;

import javax.sql.DataSource;

import org.jooq.SQLDialect;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.jooq.impl.DefaultExecuteListenerProvider;
import org.jooq.impl.DefaultRecordListenerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import io.opensw.flypush.api.database.jooq.JooqExecuteListener;
import io.opensw.flypush.api.database.jooq.JooqRecordListener;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class JooqConfig {

	private final DataSource dataSource;

	@Bean
	public DefaultConfiguration configuration() {
		DefaultConfiguration configuration = new DefaultConfiguration();
		configuration.set( new DataSourceConnectionProvider( new TransactionAwareDataSourceProxy( this.dataSource ) ) );
		configuration.set( SQLDialect.POSTGRES );

		//add query listener
		configuration.set( new DefaultRecordListenerProvider( new JooqRecordListener() ) );
		configuration.set(new DefaultExecuteListenerProvider(new JooqExecuteListener()));

		return configuration;
	}

	@Bean
	public DefaultDSLContext dsl() {
		return new DefaultDSLContext( configuration() );
	}

}
