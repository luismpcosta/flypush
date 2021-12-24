package io.opensw.flypush.api.core.domain.app;

import static io.opensw.flypush.api.database.generated.tables.EngineApplication.ENGINE_APPLICATION;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordHandler;
import org.jooq.TableField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import io.opensw.flypush.api.database.generated.tables.records.ApplicationRecord;
import io.opensw.flypush.api.database.jooq.NativeQueryUtils;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AppRepositoryImpl implements AppRepository {

	private final DSLContext context;

	@Override
	public App create( final App app ) {
		return this.context.insertInto(
				ENGINE_APPLICATION, ENGINE_APPLICATION.UUID, ENGINE_APPLICATION.NAME, ENGINE_APPLICATION.DESCRIPTION,
				ENGINE_APPLICATION.LOGO, ENGINE_APPLICATION.MASTER_SECRET, ENGINE_APPLICATION.API_KEY
		).values(
				UUID.randomUUID().toString(), app.getName(), app.getDescription(), app.getLogo(),
				UUID.randomUUID().toString(), UUID.randomUUID().toString()
		).returningResult( ENGINE_APPLICATION.asterisk() ).fetchOne().map( record -> App.recordMapper( record ) );
	}

	@Override
	public App update( final App app ) {
		Map< TableField< ApplicationRecord, ? >, Object > map = new HashMap<>();
		map.put( ENGINE_APPLICATION.NAME, app.getName() );
		map.put( ENGINE_APPLICATION.DESCRIPTION, app.getDescription() );
		map.put( ENGINE_APPLICATION.UPDATED_AT, Instant.now() );

		final Record entity = this.context.update( ENGINE_APPLICATION ).set( map )
				.where( ENGINE_APPLICATION.ID.eq( app.getId() ) )
				.returningResult( ENGINE_APPLICATION.asterisk() ).fetchOne();

		// validate if entity was null
		if ( entity == null ) {
			return null;
		}
		return entity.map( record -> App.recordMapper( record ) );
	}

	@Override
	public int updateLogo( final Integer applicationId, final String logo ) {
		return this.context.update( ENGINE_APPLICATION ).set( ENGINE_APPLICATION.LOGO, logo )
				.where( ENGINE_APPLICATION.ID.eq( applicationId ) ).execute();
	}

	@Override
	public int delete( final Integer applicationId ) {
		return this.context.delete( ENGINE_APPLICATION ).where( ENGINE_APPLICATION.ID.eq( applicationId ) ).execute();
	}

	@Override
	public App load( final Integer applicationId ) {
		final Record entity = this.context.select( ENGINE_APPLICATION.asterisk() ).from( ENGINE_APPLICATION )
				.where( ENGINE_APPLICATION.ID.eq( applicationId ) ).fetchOne();

		// validate if entity was null
		if ( entity == null ) {
			return null;
		}
		return entity.map( record -> record != null ? App.recordMapper( record ) : null );
	}

	@Override
	public Page< App > loadAll( final Pageable page ) {
		List< App > results = new ArrayList<>();

		// query select all
		this.context.select( ENGINE_APPLICATION.asterisk() ).from( ENGINE_APPLICATION )
				.orderBy( NativeQueryUtils.sort( page, false ) )
				.offset( (int) page.getOffset() )
				.limit( page.getPageSize() )
				.fetch().into( new RecordHandler< Record >() {

					@Override
					public void next( Record record ) {
						results.add( App.recordMapper( record ) );
					}
				} );

		// query total of elements
		long total = this.context.selectCount().from( ENGINE_APPLICATION ).fetchOne( 0, Long.class );

		return new PageImpl<>( results, page, total );
	}

	@Override
	public boolean validateKeyAndSecret( final String key, final String secret ) {
		long count = this.context.selectCount().from( ENGINE_APPLICATION )
				.where( ENGINE_APPLICATION.API_KEY.eq( key ).and( ENGINE_APPLICATION.MASTER_SECRET.eq( secret ) ) )
				.fetchOne( 0, Long.class );

		return count > 0 ? true : false;
	}

	@Override
	public Integer loadAppId( final String key, final String secret ) {
		return this.context.select( ENGINE_APPLICATION.ID ).from( ENGINE_APPLICATION )
				.where( ENGINE_APPLICATION.API_KEY.eq( key ).and( ENGINE_APPLICATION.MASTER_SECRET.eq( secret ) ) )
				.fetchOneInto( Integer.class );
	}

}
