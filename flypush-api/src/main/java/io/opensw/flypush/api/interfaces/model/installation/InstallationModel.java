package io.opensw.flypush.api.interfaces.model.installation;

import java.time.Instant;
import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode( callSuper = false )
@Relation( value = "installation", collectionRelation = "installations" )
public class InstallationModel extends RepresentationModel< InstallationModel > {

	private Integer id;

	private String alias;

	private String deviceToken;

	private String deviceType;

	private String operatingSystem;

	private String osVersion;

	private String platform;

	private List< String > topics;

	private boolean active;

	private Instant created_at;

	private String createdBy;

	private Instant updatedAt;

	private String updatedBy;
	
}
