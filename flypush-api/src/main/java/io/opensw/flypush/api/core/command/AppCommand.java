package io.opensw.flypush.api.core.command;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppCommand implements Serializable{

	private static final long serialVersionUID = 6701322392577940323L;

	private String name;

	private String description;

	private String logo;
	
}
