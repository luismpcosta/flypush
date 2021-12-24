package io.opensw.flypush.api.core.domain.installation;

import static io.opensw.flypush.api.database.generated.tables.EngineInstallation.ENGINE_INSTALLATION;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordHandler;
import org.jooq.TableField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import io.opensw.flypush.api.database.generated.tables.records.InstallationRecord;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class InstallationRepositoryImpl implements InstallationRepository {

	private final DSLContext context;

	@Override
	public Installation createOrUpdate( final Integer variantId, final Installation installation ) {
		long count = this.context.selectCount().from( ENGINE_INSTALLATION )
				.where(
						ENGINE_INSTALLATION.VARIANT_ID.eq( variantId )
								.and( ENGINE_INSTALLATION.DEVICE_TOKEN.eq( installation.getDeviceToken() ) )
				).fetchOne( 0, Long.class );

		if ( count == 0 ) {
			return this.create( variantId, installation );
		}

		return this.update( variantId, installation );
	}

	private Installation create( final Integer variantId, final Installation installation ) {
		return this.context.insertInto(
				ENGINE_INSTALLATION, ENGINE_INSTALLATION.UUID, ENGINE_INSTALLATION.ALIAS, ENGINE_INSTALLATION.DEVICE_TOKEN,
				ENGINE_INSTALLATION.DEVICE_TYPE, ENGINE_INSTALLATION.OPERATING_SYSTEM, ENGINE_INSTALLATION.OS_VERSION,
				ENGINE_INSTALLATION.PLATFORM, ENGINE_INSTALLATION.VARIANT_ID
		).values(
				UUID.randomUUID().toString(), installation.getAlias(), installation.getDeviceToken(), installation.getDeviceType(),
				installation.getOperatingSystem(), installation.getOsVersion(), installation.getPlatform(), variantId
		).returningResult( ENGINE_INSTALLATION.asterisk() ).fetchOne().map( record -> Installation.recordMapper( record ) );
	}

	private Installation update( final Integer variantId, final Installation installation ) {
		Map< TableField< InstallationRecord, ? >, Object > map = new HashMap<>();
		map.put( ENGINE_INSTALLATION.ALIAS, installation.getAlias() );
		map.put( ENGINE_INSTALLATION.DEVICE_TOKEN, installation.getDeviceToken() );
		map.put( ENGINE_INSTALLATION.DEVICE_TYPE, installation.getDeviceType() );
		map.put( ENGINE_INSTALLATION.OPERATING_SYSTEM, installation.getOperatingSystem() );
		map.put( ENGINE_INSTALLATION.OS_VERSION, installation.getOsVersion() );
		map.put( ENGINE_INSTALLATION.PLATFORM, installation.getPlatform() );
		map.put( ENGINE_INSTALLATION.UPDATED_AT, Instant.now() );

		return this.context.update( ENGINE_INSTALLATION ).set( map )
				.where(
						ENGINE_INSTALLATION.VARIANT_ID.eq( variantId )
								.and( ENGINE_INSTALLATION.DEVICE_TOKEN.eq( installation.getDeviceToken() ) )
				).returningResult( ENGINE_INSTALLATION.asterisk() ).fetchOne().map( record -> Installation.recordMapper( record ) );
	}

	@Override
	public List< InstallationMinimal > loadInstallationMinimalByVariant( final Integer variantId, final String alias ) {
		List< InstallationMinimal > results = new ArrayList<>();

		Condition condition = ENGINE_INSTALLATION.VARIANT_ID.eq( variantId ).and( ENGINE_INSTALLATION.ACTIVE.isTrue() );

		if ( alias != null && !alias.isEmpty() ) {
			condition = condition.and( ENGINE_INSTALLATION.ALIAS.eq( alias ) );
		}

		this.context.select( ENGINE_INSTALLATION.ID, ENGINE_INSTALLATION.DEVICE_TOKEN, ENGINE_INSTALLATION.ALIAS )
				.from( ENGINE_INSTALLATION )
				.where( condition )
				.fetch().into( new RecordHandler< Record >() {

					@Override
					public void next( Record record ) {
						results.add( InstallationMinimal.recordMapper( record ) );
					}
				} );

		return results;
	}

	@Override
	public Long countInstallationByVariant( final Integer variantId, final String alias ) {
		Condition condition = ENGINE_INSTALLATION.VARIANT_ID.eq( variantId ).and( ENGINE_INSTALLATION.ACTIVE.isTrue() );

		if ( alias != null && !alias.isEmpty() ) {
			condition = condition.and( ENGINE_INSTALLATION.ALIAS.eq( alias ) );
		}

		return this.context.selectCount().from( ENGINE_INSTALLATION )
				.where( condition )
				.fetchOne( 0, Long.class );
	}
	
	@Override
	public Page< InstallationMinimal > loadInstallationMinimalByVariant( final Integer variantId, final String alias, final Pageable page ) {
		List< InstallationMinimal > results = new ArrayList<>();

		Condition condition = ENGINE_INSTALLATION.VARIANT_ID.eq( variantId ).and( ENGINE_INSTALLATION.ACTIVE.isTrue() );

		if ( alias != null && !alias.isEmpty() ) {
			condition = condition.and( ENGINE_INSTALLATION.ALIAS.eq( alias ) );
		}

		this.context.select( ENGINE_INSTALLATION.ID, ENGINE_INSTALLATION.DEVICE_TOKEN, ENGINE_INSTALLATION.ALIAS )
				.from( ENGINE_INSTALLATION )
				.where( condition )
				.orderBy( ENGINE_INSTALLATION.ID.asc() )
				.offset( (int) page.getOffset() )
				.limit( page.getPageSize() )
				.fetch().into( new RecordHandler< Record >() {

					@Override
					public void next( Record record ) {
						results.add( InstallationMinimal.recordMapper( record ) );
					}
				} );

		// query total of elements
		long total = this.countInstallationByVariant( variantId, alias );

		return new PageImpl<>( results, page, total );
	}
}
