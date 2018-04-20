package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Athlete;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Athlete entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {

}
