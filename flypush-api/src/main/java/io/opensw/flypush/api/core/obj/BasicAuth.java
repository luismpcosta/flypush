package io.opensw.flypush.api.core.obj;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BasicAuth {

	private String username;

	private String password;
	
}
