package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Licence;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Licence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LicenceRepository extends JpaRepository<Licence, Long> {

	Page<Licence> findByathleteDojoclubId(Pageable pageable, Long id);
	
	Page<Licence> findByathleteDojoclubLigueId(Pageable pageable, Long id);

}
