package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.DojoClub;

import io.github.jhipster.application.repository.DojoClubRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DojoClub.
 */
@RestController
@RequestMapping("/api")
public class DojoClubResource {

    private final Logger log = LoggerFactory.getLogger(DojoClubResource.class);

    private static final String ENTITY_NAME = "dojoClub";

    private final DojoClubRepository dojoClubRepository;
    

    public DojoClubResource(DojoClubRepository dojoClubRepository) {
        this.dojoClubRepository = dojoClubRepository;
    }

    /**
     * POST  /dojo-clubs : Create a new dojoClub.
     *
     * @param dojoClub the dojoClub to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dojoClub, or with status 400 (Bad Request) if the dojoClub has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/dojo-clubs")
    @Timed
    public ResponseEntity<DojoClub> createDojoClub(@Valid @RequestBody DojoClub dojoClub) throws URISyntaxException {
        log.debug("REST request to save DojoClub : {}", dojoClub);
        if (dojoClub.getId() != null) {
            throw new BadRequestAlertException("A new dojoClub cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocalDate today = LocalDate.now();
        dojoClub.setDateCreation(today);
        DojoClub result = dojoClubRepository.save(dojoClub);
        return ResponseEntity.created(new URI("/api/dojo-clubs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dojo-clubs : Updates an existing dojoClub.
     *
     * @param dojoClub the dojoClub to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dojoClub,
     * or with status 400 (Bad Request) if the dojoClub is not valid,
     * or with status 500 (Internal Server Error) if the dojoClub couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/dojo-clubs")
    @Timed
    public ResponseEntity<DojoClub> updateDojoClub(@Valid @RequestBody DojoClub dojoClub) throws URISyntaxException {
        log.debug("REST request to update DojoClub : {}", dojoClub);
        if (dojoClub.getId() == null) {
            return createDojoClub(dojoClub);
        }
        DojoClub result = dojoClubRepository.save(dojoClub);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dojoClub.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dojo-clubs : get all the dojoClubs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of dojoClubs in body
     */
    @GetMapping("/dojo-clubs")
    @Timed
    public ResponseEntity<List<DojoClub>> getAllDojoClubs(Pageable pageable) {
        log.debug("REST request to get a page of DojoClubs");
        Page<DojoClub> page = null;
        if(hasRole("ROLE_USER")) {
        	page = dojoClubRepository.findByUserIsCurrentUser(pageable);
        }
        if(hasRole("ROLE_ADMIN")) {
        	page = dojoClubRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dojo-clubs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    
    
    protected boolean hasRole(String role) {
        // get security context from thread local
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null)
            return false;

        Authentication authentication = context.getAuthentication();
        if (authentication == null)
            return false;

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (role.equals(auth.getAuthority()))
                return true;
        }

        return false;
    }
    

    /**
     * GET  /dojo-clubs/:id : get the "id" dojoClub.
     *
     * @param id the id of the dojoClub to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dojoClub, or with status 404 (Not Found)
     */
    @GetMapping("/dojo-clubs/{id}")
    @Timed
    public ResponseEntity<DojoClub> getDojoClub(@PathVariable Long id) {
        log.debug("REST request to get DojoClub : {}", id);
        DojoClub dojoClub = dojoClubRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dojoClub));
    }

    /**
     * DELETE  /dojo-clubs/:id : delete the "id" dojoClub.
     *
     * @param id the id of the dojoClub to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/dojo-clubs/{id}")
    @Timed
    public ResponseEntity<Void> deleteDojoClub(@PathVariable Long id) {
        log.debug("REST request to delete DojoClub : {}", id);
        dojoClubRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
