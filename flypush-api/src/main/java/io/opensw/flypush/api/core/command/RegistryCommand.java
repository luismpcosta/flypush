package io.opensw.flypush.api.core.command;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistryCommand implements Serializable{

	private static final long serialVersionUID = -5159060431305347941L;

	private String alias;

	private String deviceToken;

	private String deviceType;
	
	private String operatingSystem;

	private String osVersion;
	
	private String platform;
	
	private List< String > topics;
	
}
