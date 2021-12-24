package io.opensw.flypush.api.core.events;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import io.opensw.flypush.api.core.events.obj.ExceptionEvent;
import io.opensw.flypush.api.core.events.obj.FirebaseBatchResponseEvent;
import io.opensw.flypush.api.core.events.obj.FirebaseResponseEvent;
import io.opensw.flypush.api.core.events.obj.FirebaseV1BatchResponseEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service event listener
 * 
 * @author Luis
 *
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceEventListener {


	/**
	 * On exception occurs
	 *
	 * @param event the event
	 */
	@Async
	@EventListener( classes = ExceptionEvent.class )
	public void onExceptionEventFired( final ExceptionEvent event ) {
		log.info( "Listener to ExceptionEvent {}", event );
		
		//TODO implement listener of event
	}

	/**
	 * On firebase response
	 *
	 * @param event the event
	 */
	@Async
	@EventListener( classes = FirebaseResponseEvent.class )
	public void onFirebaseResponseEventFired( final FirebaseResponseEvent event ) {
		log.info( "Listener to FirebaseResponseEvent {}", event );
		
		//TODO implement listener of event
	}
	
	/**
	 * On firebase batch response
	 *
	 * @param event the event
	 */
	@Async
	@EventListener( classes = FirebaseBatchResponseEvent.class )
	public void onFirebaseBatchResponseEventFired( final FirebaseBatchResponseEvent event ) {
		log.info( "Listener to FirebaseBatchResponseEvent {}", event );
		
		//TODO implement listener of event
	}
	
	/**
	 * On firebase v1 batch response
	 *
	 * @param event the event
	 */
	@Async
	@EventListener( classes = FirebaseV1BatchResponseEvent.class )
	public void onFirebaseV1BatchResponseEvent( final FirebaseV1BatchResponseEvent event ) {
		log.info( "Listener to FirebaseV1BatchResponseEvent {}", event );
		
		//TODO implement listener of event
	}

}
