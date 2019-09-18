package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.domain.ZavrsniRad;
import eu.apeiron.dalibor.evaulacija.repository.ZavrsniRadRepository;
import eu.apeiron.dalibor.evaulacija.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link eu.apeiron.dalibor.evaulacija.domain.ZavrsniRad}.
 */
@RestController
@RequestMapping("/api")
public class ZavrsniRadResource {

    private final Logger log = LoggerFactory.getLogger(ZavrsniRadResource.class);

    private static final String ENTITY_NAME = "zavrsniRad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZavrsniRadRepository zavrsniRadRepository;

    public ZavrsniRadResource(ZavrsniRadRepository zavrsniRadRepository) {
        this.zavrsniRadRepository = zavrsniRadRepository;
    }

    /**
     * {@code POST  /zavrsni-rads} : Create a new zavrsniRad.
     *
     * @param zavrsniRad the zavrsniRad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new zavrsniRad, or with status {@code 400 (Bad Request)} if the zavrsniRad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/zavrsni-rads")
    public ResponseEntity<ZavrsniRad> createZavrsniRad(@Valid @RequestBody ZavrsniRad zavrsniRad) throws URISyntaxException {
        log.debug("REST request to save ZavrsniRad : {}", zavrsniRad);
        if (zavrsniRad.getId() != null) {
            throw new BadRequestAlertException("A new zavrsniRad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZavrsniRad result = zavrsniRadRepository.save(zavrsniRad);
        return ResponseEntity.created(new URI("/api/zavrsni-rads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /zavrsni-rads} : Updates an existing zavrsniRad.
     *
     * @param zavrsniRad the zavrsniRad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zavrsniRad,
     * or with status {@code 400 (Bad Request)} if the zavrsniRad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the zavrsniRad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/zavrsni-rads")
    public ResponseEntity<ZavrsniRad> updateZavrsniRad(@Valid @RequestBody ZavrsniRad zavrsniRad) throws URISyntaxException {
        log.debug("REST request to update ZavrsniRad : {}", zavrsniRad);
        if (zavrsniRad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ZavrsniRad result = zavrsniRadRepository.save(zavrsniRad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, zavrsniRad.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /zavrsni-rads} : get all the zavrsniRads.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of zavrsniRads in body.
     */
    @GetMapping("/zavrsni-rads")
    public List<ZavrsniRad> getAllZavrsniRads() {
        log.debug("REST request to get all ZavrsniRads");
        return zavrsniRadRepository.findAll();
    }

    /**
     * {@code GET  /zavrsni-rads/:id} : get the "id" zavrsniRad.
     *
     * @param id the id of the zavrsniRad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the zavrsniRad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/zavrsni-rads/{id}")
    public ResponseEntity<ZavrsniRad> getZavrsniRad(@PathVariable Long id) {
        log.debug("REST request to get ZavrsniRad : {}", id);
        Optional<ZavrsniRad> zavrsniRad = zavrsniRadRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(zavrsniRad);
    }

    /**
     * {@code DELETE  /zavrsni-rads/:id} : delete the "id" zavrsniRad.
     *
     * @param id the id of the zavrsniRad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/zavrsni-rads/{id}")
    public ResponseEntity<Void> deleteZavrsniRad(@PathVariable Long id) {
        log.debug("REST request to delete ZavrsniRad : {}", id);
        zavrsniRadRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
