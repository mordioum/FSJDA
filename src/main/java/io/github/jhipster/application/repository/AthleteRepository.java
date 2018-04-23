package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.Athlete;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import io.github.jhipster.application.domain.DojoClub;
import io.github.jhipster.application.domain.User;


/**
 * Spring Data JPA repository for the Athlete entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {

	Page<Athlete> findBydojoclubId(Pageable pageable,long id);

}
