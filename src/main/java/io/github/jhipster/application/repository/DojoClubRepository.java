package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.DojoClub;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DojoClub entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DojoClubRepository extends JpaRepository<DojoClub, Long> {

}
