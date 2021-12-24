package io.opensw.flypush.api.core.domain.variant;

import static io.opensw.flypush.api.database.generated.tables.EngineVariant.ENGINE_VARIANT;

import java.math.BigDecimal;
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

import io.opensw.flypush.api.core.exceptions.EntityConfigFileExistsException;
import io.opensw.flypush.api.database.generated.tables.records.VariantRecord;
import io.opensw.flypush.api.database.jooq.Jsonb;
import io.opensw.flypush.api.database.jooq.NativeQueryUtils;
import io.opensw.flypush.api.utils.CheckSumUtils;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VariantRepositoryImpl implements VariantRepository {

	private final DSLContext context;

	@Override
	public Variant create( final Variant variant ) {
		BigDecimal checksum = CheckSumUtils.checksumBigdecimal( variant.getConfigFile() );

		// if have checksum prevent duplicate configurations
		if ( checksum != null ) {
			long total = this.context.selectCount().from( ENGINE_VARIANT )
					.where(
							ENGINE_VARIANT.APPLICATION_ID.eq( variant.getApplicationId() )
									.and( ENGINE_VARIANT.CONFIG_FILE_CHECKSUM.eq( checksum ) )
					)
					.fetchOne( 0, Long.class );

			// if total greater than zero throw existing exception
			if ( total > 0 ) {
				throw new EntityConfigFileExistsException();
			}
		}

		return this.context.insertInto(
				ENGINE_VARIANT, ENGINE_VARIANT.UUID, ENGINE_VARIANT.APPLICATION_ID, ENGINE_VARIANT.NAME, ENGINE_VARIANT.DESCRIPTION,
				ENGINE_VARIANT.TYPE, ENGINE_VARIANT.SECRET, ENGINE_VARIANT.API_KEY, ENGINE_VARIANT.CONFIG,
				ENGINE_VARIANT.CONFIG_FILE, ENGINE_VARIANT.CONFIG_FILE_CHECKSUM
		).values(
				UUID.randomUUID().toString(), variant.getApplicationId(), variant.getName(), variant.getDescription(),
				variant.getType().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString(), new Jsonb( variant.getConfig() ),
				variant.getConfigFile(), checksum
		).returningResult( ENGINE_VARIANT.asterisk() ).fetchOne().map( record -> Variant.recordMapper( record ) );
	}

	@Override
	public Variant update( final Variant variant ) {
		Map< TableField< VariantRecord, ? >, Object > map = new HashMap<>();
		map.put( ENGINE_VARIANT.NAME, variant.getName() );
		map.put( ENGINE_VARIANT.DESCRIPTION, variant.getDescription() );
		map.put( ENGINE_VARIANT.UPDATED_AT, Instant.now() );
		map.put( ENGINE_VARIANT.UPDATED_BY, "NA" );

		final Record entity = this.context.update( ENGINE_VARIANT ).set( map )
				.where( ENGINE_VARIANT.ID.eq( variant.getId() ) )
				.returningResult( ENGINE_VARIANT.asterisk() ).fetchOne();

		// validate if entity was null
		if ( entity == null ) {
			return null;
		}
		return entity.map( record -> Variant.recordMapper( record ) );
	}

	@Override
	public int delete( final Integer applicationId, final Integer variantId ) {
		return this.context.delete( ENGINE_VARIANT )
				.where( ENGINE_VARIANT.APPLICATION_ID.eq( applicationId ).and( ENGINE_VARIANT.ID.eq( variantId ) ) )
				.execute();
	}

	@Override
	public Variant load( final Integer applicationId, final Integer variantId ) {
		final Record entity = this.context.select( ENGINE_VARIANT.asterisk() ).from( ENGINE_VARIANT )
				.where( ENGINE_VARIANT.APPLICATION_ID.eq( applicationId ).and( ENGINE_VARIANT.ID.eq( variantId ) ) )
				.fetchOne();

		// validate if entity was null
		if ( entity == null ) {
			return null;
		}
		return entity.map( record -> record != null ? Variant.recordMapper( record ) : null );
	}

	@Override
	public Page< Variant > loadAll( final Integer applicationId, final Pageable page ) {
		List< Variant > results = new ArrayList<>();

		// query select all
		this.context.select( ENGINE_VARIANT.asterisk() ).from( ENGINE_VARIANT )
				.where( ENGINE_VARIANT.APPLICATION_ID.eq( applicationId ) )
				.orderBy( NativeQueryUtils.sort( page, false ) )
				.offset( (int) page.getOffset() )
				.limit( page.getPageSize() )
				.fetch().into( new RecordHandler< Record >() {

					@Override
					public void next( Record record ) {
						results.add( Variant.recordMapper( record ) );
					}
				} );

		// query total of elements
		long total = this.context.selectCount().from( ENGINE_VARIANT ).fetchOne( 0, Long.class );

		return new PageImpl<>( results, page, total );
	}

	@Override
	public boolean validateKeyAndSecret( final String key, final String secret ) {
		long count = this.context.selectCount().from( ENGINE_VARIANT )
				.where( ENGINE_VARIANT.API_KEY.eq( key ).and( ENGINE_VARIANT.SECRET.eq( secret ) ) )
				.fetchOne( 0, Long.class );

		return count > 0 ? true : false;
	}

	@Override
	public Integer loadVariantId( final String key, final String secret ) {
		return this.context.selectCount().from( ENGINE_VARIANT )
				.where( ENGINE_VARIANT.API_KEY.eq( key ).and( ENGINE_VARIANT.SECRET.eq( secret ) ) )
				.fetchOneInto( Integer.class );
	}

	@Override
	public List< VariantMinimal > loadVariantMinimalByApp( final Integer appId ) {
		List< VariantMinimal > results = new ArrayList<>();

		this.context.select( ENGINE_VARIANT.ID, ENGINE_VARIANT.TYPE, ENGINE_VARIANT.CONFIG, ENGINE_VARIANT.UUID )
				.from( ENGINE_VARIANT )
				.where( ENGINE_VARIANT.APPLICATION_ID.eq( appId ).and( ENGINE_VARIANT.ACTIVE.isTrue() ) )
				.fetch().into( new RecordHandler< Record >() {

					@Override
					public void next( Record record ) {
						results.add( VariantMinimal.recordMapper( record ) );
					}
				} );

		return results;
	}

}
