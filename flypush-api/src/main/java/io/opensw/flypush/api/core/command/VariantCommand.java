package io.opensw.flypush.api.core.command;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import io.opensw.flypush.api.Constants;
import io.opensw.flypush.api.core.enums.VariantType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VariantCommand implements Serializable {

	private static final long serialVersionUID = -6630566859214926139L;

	private String name;

	private String description;

	private Map< String, Object > config;

	private String configFile;

	private VariantType type;

	public void addFileContent( String content ) {
		if ( this.config == null ) {
			this.config = new HashMap<>();
		}

		this.config.put( Constants.CONFIG_FILE_CONTENT, content );
	}
}
