package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Saison;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Saison entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SaisonRepository extends JpaRepository<Saison, Long> {

}
