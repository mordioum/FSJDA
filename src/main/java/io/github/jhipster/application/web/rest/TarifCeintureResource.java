package io.github.jhipster.application.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.application.domain.TarifCeinture;

import io.github.jhipster.application.repository.TarifCeintureRepository;
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
 * REST controller for managing TarifCeinture.
 */
@RestController
@RequestMapping("/api")
public class TarifCeintureResource {

    private final Logger log = LoggerFactory.getLogger(TarifCeintureResource.class);

    private static final String ENTITY_NAME = "tarifCeinture";

    private final TarifCeintureRepository tarifCeintureRepository;

    public TarifCeintureResource(TarifCeintureRepository tarifCeintureRepository) {
        this.tarifCeintureRepository = tarifCeintureRepository;
    }

    /**
     * POST  /tarif-ceintures : Create a new tarifCeinture.
     *
     * @param tarifCeinture the tarifCeinture to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tarifCeinture, or with status 400 (Bad Request) if the tarifCeinture has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tarif-ceintures")
    @Timed
    public ResponseEntity<TarifCeinture> createTarifCeinture(@Valid @RequestBody TarifCeinture tarifCeinture) throws URISyntaxException {
        log.debug("REST request to save TarifCeinture : {}", tarifCeinture);
        if (tarifCeinture.getId() != null) {
            throw new BadRequestAlertException("A new tarifCeinture cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TarifCeinture result = tarifCeintureRepository.save(tarifCeinture);
        return ResponseEntity.created(new URI("/api/tarif-ceintures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tarif-ceintures : Updates an existing tarifCeinture.
     *
     * @param tarifCeinture the tarifCeinture to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tarifCeinture,
     * or with status 400 (Bad Request) if the tarifCeinture is not valid,
     * or with status 500 (Internal Server Error) if the tarifCeinture couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tarif-ceintures")
    @Timed
    public ResponseEntity<TarifCeinture> updateTarifCeinture(@Valid @RequestBody TarifCeinture tarifCeinture) throws URISyntaxException {
        log.debug("REST request to update TarifCeinture : {}", tarifCeinture);
        if (tarifCeinture.getId() == null) {
            return createTarifCeinture(tarifCeinture);
        }
        TarifCeinture result = tarifCeintureRepository.save(tarifCeinture);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tarifCeinture.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tarif-ceintures : get all the tarifCeintures.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tarifCeintures in body
     */
    @GetMapping("/tarif-ceintures")
    @Timed
    public ResponseEntity<List<TarifCeinture>> getAllTarifCeintures(Pageable pageable) {
        log.debug("REST request to get a page of TarifCeintures");
        Page<TarifCeinture> page = tarifCeintureRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tarif-ceintures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tarif-ceintures/:id : get the "id" tarifCeinture.
     *
     * @param id the id of the tarifCeinture to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tarifCeinture, or with status 404 (Not Found)
     */
    @GetMapping("/tarif-ceintures/{id}")
    @Timed
    public ResponseEntity<TarifCeinture> getTarifCeinture(@PathVariable Long id) {
        log.debug("REST request to get TarifCeinture : {}", id);
        TarifCeinture tarifCeinture = tarifCeintureRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tarifCeinture));
    }

    /**
     * DELETE  /tarif-ceintures/:id : delete the "id" tarifCeinture.
     *
     * @param id the id of the tarifCeinture to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tarif-ceintures/{id}")
    @Timed
    public ResponseEntity<Void> deleteTarifCeinture(@PathVariable Long id) {
        log.debug("REST request to delete TarifCeinture : {}", id);
        tarifCeintureRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
