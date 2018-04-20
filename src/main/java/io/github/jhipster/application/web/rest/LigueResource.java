package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Ligue;

import io.github.jhipster.application.repository.LigueRepository;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Ligue.
 */
@RestController
@RequestMapping("/api")
public class LigueResource {

    private final Logger log = LoggerFactory.getLogger(LigueResource.class);

    private static final String ENTITY_NAME = "ligue";

    private final LigueRepository ligueRepository;

    public LigueResource(LigueRepository ligueRepository) {
        this.ligueRepository = ligueRepository;
    }

    /**
     * POST  /ligues : Create a new ligue.
     *
     * @param ligue the ligue to create
     * @return the ResponseEntity with status 201 (Created) and with body the new ligue, or with status 400 (Bad Request) if the ligue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ligues")
    @Timed
    public ResponseEntity<Ligue> createLigue(@Valid @RequestBody Ligue ligue) throws URISyntaxException {
        log.debug("REST request to save Ligue : {}", ligue);
        if (ligue.getId() != null) {
            throw new BadRequestAlertException("A new ligue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LocalDate today = LocalDate.now();
        ligue.setDateCreation(today);
        Ligue result = ligueRepository.save(ligue);
        return ResponseEntity.created(new URI("/api/ligues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ligues : Updates an existing ligue.
     *
     * @param ligue the ligue to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated ligue,
     * or with status 400 (Bad Request) if the ligue is not valid,
     * or with status 500 (Internal Server Error) if the ligue couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ligues")
    @Timed
    public ResponseEntity<Ligue> updateLigue(@Valid @RequestBody Ligue ligue) throws URISyntaxException {
        log.debug("REST request to update Ligue : {}", ligue);
        if (ligue.getId() == null) {
            return createLigue(ligue);
        }
        Ligue result = ligueRepository.save(ligue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, ligue.getId().toString()))
            .body(result);
    }

    /**
     * GET  /ligues : get all the ligues.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of ligues in body
     */
    @GetMapping("/ligues")
    @Timed
    public ResponseEntity<List<Ligue>> getAllLigues(Pageable pageable) {
        log.debug("REST request to get a page of Ligues");
        Page<Ligue> page = ligueRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ligues");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ligues/:id : get the "id" ligue.
     *
     * @param id the id of the ligue to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the ligue, or with status 404 (Not Found)
     */
    @GetMapping("/ligues/{id}")
    @Timed
    public ResponseEntity<Ligue> getLigue(@PathVariable Long id) {
        log.debug("REST request to get Ligue : {}", id);
        Ligue ligue = ligueRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(ligue));
    }

    /**
     * DELETE  /ligues/:id : delete the "id" ligue.
     *
     * @param id the id of the ligue to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ligues/{id}")
    @Timed
    public ResponseEntity<Void> deleteLigue(@PathVariable Long id) {
        log.debug("REST request to delete Ligue : {}", id);
        ligueRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
