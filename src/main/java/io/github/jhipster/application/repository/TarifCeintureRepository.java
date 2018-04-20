package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.TarifCeinture;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TarifCeinture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TarifCeintureRepository extends JpaRepository<TarifCeinture, Long> {

}
