package io.opensw.flypush.api.database.jooq;

import org.jooq.ExecuteContext;
import org.jooq.impl.DefaultExecuteListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JooqExecuteListener extends DefaultExecuteListener  {

	private static final long serialVersionUID = 7990461841410337994L;

	@Override
    public void start(ExecuteContext ctx) {
		log.debug( "Jooq Execution Listener" );
		super.start( ctx );
	}
	
}
