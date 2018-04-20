package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Ligue;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Ligue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LigueRepository extends JpaRepository<Ligue, Long> {

}
