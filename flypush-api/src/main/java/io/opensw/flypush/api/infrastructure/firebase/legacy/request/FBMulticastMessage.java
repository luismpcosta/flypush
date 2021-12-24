package io.opensw.flypush.api.infrastructure.firebase.legacy.request;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FBMulticastMessage implements Serializable {

	private static final long serialVersionUID = -1101787564319849895L;

	private List< String > registrationIds;

	private FBNotification notification;

	private Map< String, String > data;
}
