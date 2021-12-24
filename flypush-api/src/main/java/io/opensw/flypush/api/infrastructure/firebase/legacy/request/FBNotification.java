package io.opensw.flypush.api.infrastructure.firebase.legacy.request;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FBNotification implements Serializable {

	private static final long serialVersionUID = 7119239373922380472L;

	private String title;

	private String body;

	private String image;

}
