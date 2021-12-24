package io.opensw.flypush.api.core.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import io.opensw.flypush.api.core.command.AppCommand;
import io.opensw.flypush.api.core.domain.app.App;
import io.opensw.flypush.api.core.domain.app.AppRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppService {

	private final AppRepository appRepository;

	@Transactional( propagation = Propagation.REQUIRED )
	public App createApplication( final AppCommand command ) {
		log.debug( "Service to Create Application with Command {}", command );

		return appRepository.create(
				App.builder().name( command.getName() ).description( command.getDescription() )
						.logo( command.getLogo() ).build()
		);
	}
	
	@Transactional( propagation = Propagation.REQUIRED )
	public App updateApplication( final Integer applicationId, final AppCommand command ) {
		log.debug( "Service to Update Application {} with Command {}", applicationId, command );

		return appRepository.update(
				App.builder().id( applicationId ).name( command.getName() ).description( command.getDescription() )
						.logo( command.getLogo() ).build()
		);
	}
	
	@Transactional( propagation = Propagation.REQUIRED )
	public int updateApplicationLogo( final Integer applicationId, final String logo ) {
		log.debug( "Service to Update Applicatio {} logo", applicationId );

		return appRepository.updateLogo( applicationId, logo);
	}
	
	@Transactional( propagation = Propagation.REQUIRED )
	public void deleteApplication( final Integer applicationId ) {
		log.debug( "Service to Delete Application {}", applicationId );

		appRepository.delete( applicationId );
	}
	
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public App loadApplication( final Integer applicationId ) {
		log.debug( "Service to Load Application {}", applicationId );

		return appRepository.load( applicationId );
	}
	
	@Transactional( propagation = Propagation.SUPPORTS, readOnly = true )
	public Page< App > loadAllApplications( final Pageable page ) {
		log.debug( "Service to Load All Application Page {}", page );

		return appRepository.loadAll( page );
	}
}
