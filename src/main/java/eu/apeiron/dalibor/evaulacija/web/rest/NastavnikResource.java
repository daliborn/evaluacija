package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.domain.Nastavnik;
import eu.apeiron.dalibor.evaulacija.repository.NastavnikRepository;
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
 * REST controller for managing {@link eu.apeiron.dalibor.evaulacija.domain.Nastavnik}.
 */
@RestController
@RequestMapping("/api")
public class NastavnikResource {

    private final Logger log = LoggerFactory.getLogger(NastavnikResource.class);

    private static final String ENTITY_NAME = "nastavnik";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NastavnikRepository nastavnikRepository;

    public NastavnikResource(NastavnikRepository nastavnikRepository) {
        this.nastavnikRepository = nastavnikRepository;
    }

    /**
     * {@code POST  /nastavniks} : Create a new nastavnik.
     *
     * @param nastavnik the nastavnik to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nastavnik, or with status {@code 400 (Bad Request)} if the nastavnik has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nastavniks")
    public ResponseEntity<Nastavnik> createNastavnik(@Valid @RequestBody Nastavnik nastavnik) throws URISyntaxException {
        log.debug("REST request to save Nastavnik : {}", nastavnik);
        if (nastavnik.getId() != null) {
            throw new BadRequestAlertException("A new nastavnik cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Nastavnik result = nastavnikRepository.save(nastavnik);
        return ResponseEntity.created(new URI("/api/nastavniks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nastavniks} : Updates an existing nastavnik.
     *
     * @param nastavnik the nastavnik to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nastavnik,
     * or with status {@code 400 (Bad Request)} if the nastavnik is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nastavnik couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nastavniks")
    public ResponseEntity<Nastavnik> updateNastavnik(@Valid @RequestBody Nastavnik nastavnik) throws URISyntaxException {
        log.debug("REST request to update Nastavnik : {}", nastavnik);
        if (nastavnik.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Nastavnik result = nastavnikRepository.save(nastavnik);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nastavnik.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nastavniks} : get all the nastavniks.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nastavniks in body.
     */
    @GetMapping("/nastavniks")
    public List<Nastavnik> getAllNastavniks(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all Nastavniks");
        return nastavnikRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /nastavniks/:id} : get the "id" nastavnik.
     *
     * @param id the id of the nastavnik to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nastavnik, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nastavniks/{id}")
    public ResponseEntity<Nastavnik> getNastavnik(@PathVariable Long id) {
        log.debug("REST request to get Nastavnik : {}", id);
        Optional<Nastavnik> nastavnik = nastavnikRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(nastavnik);
    }

    /**
     * {@code DELETE  /nastavniks/:id} : delete the "id" nastavnik.
     *
     * @param id the id of the nastavnik to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nastavniks/{id}")
    public ResponseEntity<Void> deleteNastavnik(@PathVariable Long id) {
        log.debug("REST request to delete Nastavnik : {}", id);
        nastavnikRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
