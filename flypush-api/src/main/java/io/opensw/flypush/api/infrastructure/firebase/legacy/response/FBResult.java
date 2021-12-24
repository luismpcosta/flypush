package io.opensw.flypush.api.infrastructure.firebase.legacy.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FBResult implements Serializable {

	private static final long serialVersionUID = -2084384072943066863L;

	@JsonProperty( "message_id" )
	private String messageId;

	private String error;

}
