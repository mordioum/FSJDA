package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.Saison;

import io.github.jhipster.application.repository.SaisonRepository;
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

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Saison.
 */
@RestController
@RequestMapping("/api")
public class SaisonResource {

    private final Logger log = LoggerFactory.getLogger(SaisonResource.class);

    private static final String ENTITY_NAME = "saison";

    private final SaisonRepository saisonRepository;

    public SaisonResource(SaisonRepository saisonRepository) {
        this.saisonRepository = saisonRepository;
    }

    /**
     * POST  /saisons : Create a new saison.
     *
     * @param saison the saison to create
     * @return the ResponseEntity with status 201 (Created) and with body the new saison, or with status 400 (Bad Request) if the saison has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/saisons")
    @Timed
    public ResponseEntity<Saison> createSaison(@Valid @RequestBody Saison saison) throws URISyntaxException {
        log.debug("REST request to save Saison : {}", saison);
        if (saison.getId() != null) {
            throw new BadRequestAlertException("A new saison cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Saison result = saisonRepository.save(saison);
        return ResponseEntity.created(new URI("/api/saisons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /saisons : Updates an existing saison.
     *
     * @param saison the saison to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated saison,
     * or with status 400 (Bad Request) if the saison is not valid,
     * or with status 500 (Internal Server Error) if the saison couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/saisons")
    @Timed
    public ResponseEntity<Saison> updateSaison(@Valid @RequestBody Saison saison) throws URISyntaxException {
        log.debug("REST request to update Saison : {}", saison);
        if (saison.getId() == null) {
            return createSaison(saison);
        }
        Saison result = saisonRepository.save(saison);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, saison.getId().toString()))
            .body(result);
    }

    /**
     * GET  /saisons : get all the saisons.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of saisons in body
     */
    @GetMapping("/saisons")
    @Timed
    public ResponseEntity<List<Saison>> getAllSaisons(Pageable pageable) {
        log.debug("REST request to get a page of Saisons");
        Page<Saison> page = saisonRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/saisons");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /saisons/:id : get the "id" saison.
     *
     * @param id the id of the saison to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the saison, or with status 404 (Not Found)
     */
    @GetMapping("/saisons/{id}")
    @Timed
    public ResponseEntity<Saison> getSaison(@PathVariable Long id) {
        log.debug("REST request to get Saison : {}", id);
        Saison saison = saisonRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(saison));
    }

    /**
     * DELETE  /saisons/:id : delete the "id" saison.
     *
     * @param id the id of the saison to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/saisons/{id}")
    @Timed
    public ResponseEntity<Void> deleteSaison(@PathVariable Long id) {
        log.debug("REST request to delete Saison : {}", id);
        saisonRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
