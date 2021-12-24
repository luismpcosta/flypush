package io.opensw.flypush.api.core.command;

import java.io.Serializable;
import java.util.Map;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageCommand implements Serializable{

	private static final long serialVersionUID = -7219525390674074117L;

	private String title;

	private String message;

	private String image;

	private Map< String, String > data;

	private String topic;

	private String alias;
	
}
