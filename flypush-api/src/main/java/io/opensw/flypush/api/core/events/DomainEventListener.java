package io.opensw.flypush.api.core.events;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

/**
 * Domain event listener
 * 
 * @author Luis
 *
 */

@Service
@RequiredArgsConstructor
public class DomainEventListener {


//	/**
//	 * Invoked when on person is created.
//	 *
//	 * @param event the event
//	 */
//	@Async
//	@TransactionalEventListener( phase = TransactionPhase.AFTER_COMPLETION, classes = PersonCreatedEvent.class )
//	public void onPersonCreated( final PersonCreatedEvent event ) {
//		log.info( "After transaction completed in create user, with event {}", event );
//		tokenHandler.generateEmailValidationToken( event );
//	}

	
}
