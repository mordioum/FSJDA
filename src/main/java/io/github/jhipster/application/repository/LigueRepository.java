package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.DojoClub;
import io.github.jhipster.application.domain.Ligue;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the Ligue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LigueRepository extends JpaRepository<Ligue, Long> {

    @Query("select ligue from Ligue ligue where ligue.user.login = ?#{principal.username}")
    Page<Ligue> findByUserIsCurrentUser(Pageable pageable);
    
    @Query("select ligue from Ligue ligue where ligue.user.login = ?#{principal.username}")
    Ligue findByUserIsCurrentUserService();

}
