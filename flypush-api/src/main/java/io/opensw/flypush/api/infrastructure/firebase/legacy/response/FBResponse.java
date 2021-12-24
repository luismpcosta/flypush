package io.opensw.flypush.api.infrastructure.firebase.legacy.response;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FBResponse implements Serializable {

	private static final long serialVersionUID = 1979303458009978320L;

	@JsonProperty( "multicast_id" )
	private Long multicastId;

	private Integer success;

	private Integer failure;

	@JsonProperty( "canonical_ids" )
	private Integer canonicalIds;

	private List< FBResult > results;

}
