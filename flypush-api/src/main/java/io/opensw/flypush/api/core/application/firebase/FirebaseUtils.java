package io.opensw.flypush.api.core.application.firebase;

import java.util.List;

import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;

import io.opensw.flypush.api.core.command.MessageCommand;
import io.opensw.flypush.api.infrastructure.firebase.legacy.request.FBMessage;
import io.opensw.flypush.api.infrastructure.firebase.legacy.request.FBMulticastMessage;
import io.opensw.flypush.api.infrastructure.firebase.legacy.request.FBNotification;

public class FirebaseUtils {

	
	private FirebaseUtils() {
		// TODO Auto-generated constructor stub
	}
	
	public static Message buildMessage( final MessageCommand command, final String token ) {
		Notification notification = Notification.builder()
				.setTitle( command.getTitle() )
				.setBody( command.getMessage() )
				.setImage( command.getImage() )
				.build();

		return Message.builder()
				.setNotification( notification )
				.setToken( token )
				.putAllData( command.getData() )
				.build();
	}

	public static MulticastMessage buildMulticastMessage( final MessageCommand command, final List< String > tokens ) {
		Notification notification = Notification.builder()
				.setTitle( command.getTitle() )
				.setBody( command.getMessage() )
				.setImage( command.getImage() )
				.build();

		return MulticastMessage.builder()
				.setNotification( notification )
				.addAllTokens( tokens )
				.putAllData( command.getData() )
				.build();
	}
	
	public static FBMessage buildLegacyMessage( final MessageCommand command, final String token ) {
		FBNotification notification = FBNotification.builder()
				.title( command.getTitle() )
				.body( command.getMessage() )
				.image( command.getImage() )
				.build();

		return FBMessage.builder()
				.notification( notification )
				.to( token )
				.data( command.getData() )
				.build();
	}

	public static FBMulticastMessage buildLegacyMulticastMessage( final MessageCommand command, final List< String > tokens ) {
		FBNotification notification = FBNotification.builder()
				.title( command.getTitle() )
				.body( command.getMessage() )
				.image( command.getImage() )
				.build();

		return FBMulticastMessage.builder()
				.notification( notification )
				.registrationIds( tokens )
				.data( command.getData() )
				.build();
	}
}
