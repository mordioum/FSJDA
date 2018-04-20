package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.DojoClub;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the DojoClub entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DojoClubRepository extends JpaRepository<DojoClub, Long> {

    @Query("select dojo_club from DojoClub dojo_club where dojo_club.user.login = ?#{principal.username}")
    List<DojoClub> findByUserIsCurrentUser();

}
