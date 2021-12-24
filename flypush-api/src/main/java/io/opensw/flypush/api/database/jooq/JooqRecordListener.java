package io.opensw.flypush.api.database.jooq;

import org.jooq.RecordContext;
import org.jooq.impl.DefaultRecordListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JooqRecordListener extends DefaultRecordListener {

	@Override
	public void insertStart( RecordContext ctx ) {
		log.debug( "Jooq Listener Insert" );
		super.insertStart( ctx );
	}

	@Override
	public void updateStart( RecordContext ctx ) {
		log.debug( "Jooq Listener Update" );
		super.updateStart( ctx );
	}
	
	@Override
	public void storeStart( RecordContext ctx ) {
		log.debug( "Jooq Listener Store" );
		super.storeStart( ctx );
	}
}
