package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Athlete;

import io.github.jhipster.application.repository.AthleteRepository;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Athlete.
 */
@RestController
@RequestMapping("/api")
public class AthleteResource {

    private final Logger log = LoggerFactory.getLogger(AthleteResource.class);

    private static final String ENTITY_NAME = "athlete";

    private final AthleteRepository athleteRepository;

    public AthleteResource(AthleteRepository athleteRepository) {
        this.athleteRepository = athleteRepository;
    }

    /**
     * POST  /athletes : Create a new athlete.
     *
     * @param athlete the athlete to create
     * @return the ResponseEntity with status 201 (Created) and with body the new athlete, or with status 400 (Bad Request) if the athlete has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/athletes")
    @Timed
    public ResponseEntity<Athlete> createAthlete(@Valid @RequestBody Athlete athlete) throws URISyntaxException {
        log.debug("REST request to save Athlete : {}", athlete);
        if (athlete.getId() != null) {
            throw new BadRequestAlertException("A new athlete cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocalDate today = LocalDate.now();
        athlete.setDateCreation(today);
        Athlete result = athleteRepository.save(athlete);
        return ResponseEntity.created(new URI("/api/athletes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /athletes : Updates an existing athlete.
     *
     * @param athlete the athlete to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated athlete,
     * or with status 400 (Bad Request) if the athlete is not valid,
     * or with status 500 (Internal Server Error) if the athlete couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/athletes")
    @Timed
    public ResponseEntity<Athlete> updateAthlete(@Valid @RequestBody Athlete athlete) throws URISyntaxException {
        log.debug("REST request to update Athlete : {}", athlete);
        if (athlete.getId() == null) {
            return createAthlete(athlete);
        }
        Athlete result = athleteRepository.save(athlete);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, athlete.getId().toString()))
            .body(result);
    }

    /**
     * GET  /athletes : get all the athletes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of athletes in body
     */
    @GetMapping("/athletes")
    @Timed
    public ResponseEntity<List<Athlete>> getAllAthletes(Pageable pageable) {
        log.debug("REST request to get a page of Athletes");
        Page<Athlete> page = athleteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/athletes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
  
    /**
     * GET  /athletes/:id : get the "id" athlete.
     *
     * @param id the id of the athlete to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the athlete, or with status 404 (Not Found)
     */
    @GetMapping("/athletes/{id}")
    @Timed
    public ResponseEntity<Athlete> getAthlete(@PathVariable Long id) {
        log.debug("REST request to get Athlete : {}", id);
        Athlete athlete = athleteRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(athlete));
    }

    /**
     * DELETE  /athletes/:id : delete the "id" athlete.
     *
     * @param id the id of the athlete to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/athletes/{id}")
    @Timed
    public ResponseEntity<Void> deleteAthlete(@PathVariable Long id) {
        log.debug("REST request to delete Athlete : {}", id);
        athleteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
