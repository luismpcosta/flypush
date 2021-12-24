package io.opensw.flypush.api.core.domain.installation;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InstallationRepository {

	Installation createOrUpdate( Integer variantId, Installation installation );

	List< InstallationMinimal > loadInstallationMinimalByVariant( Integer variantId, String alias );

	Long countInstallationByVariant( Integer variantId, String alias );

	Page< InstallationMinimal > loadInstallationMinimalByVariant( Integer variantId, String alias, Pageable page );

}
