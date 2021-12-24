package io.opensw.flypush.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

/**
 * Application class
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@ComponentScan( "io.opensw.flypush.api" )
@Slf4j
public class FlyPushApplication {

	/**
	 * The main method.
	 *
	 * @param args to run application
	 */
	public static void main( String [] args ) {
		SpringApplication.run( FlyPushApplication.class, args );
		log.info( "run" );
	}
}
