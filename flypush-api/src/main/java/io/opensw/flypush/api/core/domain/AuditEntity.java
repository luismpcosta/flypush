package io.opensw.flypush.api.core.domain;

import java.time.Instant;

import lombok.Data;

@Data
public abstract class AuditEntity {

	private boolean active;

	private Instant created_at;

	private String createdBy;

	private Instant updatedAt;

	private String updatedBy;
	
}
