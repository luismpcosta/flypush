package io.opensw.flypush.api.core.obj;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KeyValueObj {

	private String key;

	private String value;
	
}
