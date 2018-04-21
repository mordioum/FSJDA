package io.github.jhipster.application.repository;

import io.github.jhipster.application.domain.DojoClub;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import java.util.List;

/**
 * Spring Data JPA repository for the DojoClub entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DojoClubRepository extends JpaRepository<DojoClub, Long> {
	
	@Query("select dojoclub from DojoClub dojoclub where dojoclub.user.login = ?#{principal.username}")
	Page<DojoClub> findByUserIsCurrentUser(Pageable pageable);

}
