package io.opensw.flypush.api.interfaces.model.app;

import java.time.Instant;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode( callSuper = false )
@Relation( value = "app", collectionRelation = "apps" )
public class AppModel extends RepresentationModel< AppModel > {

	private Integer id;

	private String name;

	private String description;

	private String masterSecret;

	private String apiKey;

	private String logo;

	private boolean active;

	private Instant created_at;

	private String createdBy;

	private Instant updatedAt;

	private String updatedBy;
	
}
