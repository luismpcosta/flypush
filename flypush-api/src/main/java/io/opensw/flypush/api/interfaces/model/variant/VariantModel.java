package io.opensw.flypush.api.interfaces.model.variant;

import java.time.Instant;
import java.util.Map;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder
@EqualsAndHashCode( callSuper = false )
@Relation( value = "variant", collectionRelation = "variants" )
public class VariantModel extends RepresentationModel< VariantModel > {

	private Integer id;

	private Integer applicationId;

	private String name;

	private String description;
	
	private Map< String, Object > config;

	private String secret;

	private String type;

	private String apiKey;

	private boolean active;

	private Instant created_at;

	private String createdBy;

	private Instant updatedAt;

	private String updatedBy;
	
}
