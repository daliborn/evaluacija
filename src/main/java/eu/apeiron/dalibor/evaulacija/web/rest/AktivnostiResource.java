package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.domain.Aktivnosti;
import eu.apeiron.dalibor.evaulacija.repository.AktivnostiRepository;
import eu.apeiron.dalibor.evaulacija.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link eu.apeiron.dalibor.evaulacija.domain.Aktivnosti}.
 */
@RestController
@RequestMapping("/api")
public class AktivnostiResource {

    private final Logger log = LoggerFactory.getLogger(AktivnostiResource.class);

    private static final String ENTITY_NAME = "aktivnosti";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AktivnostiRepository aktivnostiRepository;

    public AktivnostiResource(AktivnostiRepository aktivnostiRepository) {
        this.aktivnostiRepository = aktivnostiRepository;
    }

    /**
     * {@code POST  /aktivnostis} : Create a new aktivnosti.
     *
     * @param aktivnosti the aktivnosti to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new aktivnosti, or with status {@code 400 (Bad Request)} if the aktivnosti has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/aktivnostis")
    public ResponseEntity<Aktivnosti> createAktivnosti(@RequestBody Aktivnosti aktivnosti) throws URISyntaxException {
        log.debug("REST request to save Aktivnosti : {}", aktivnosti);
        if (aktivnosti.getId() != null) {
            throw new BadRequestAlertException("A new aktivnosti cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Aktivnosti result = aktivnostiRepository.save(aktivnosti);
        return ResponseEntity.created(new URI("/api/aktivnostis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /aktivnostis} : Updates an existing aktivnosti.
     *
     * @param aktivnosti the aktivnosti to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated aktivnosti,
     * or with status {@code 400 (Bad Request)} if the aktivnosti is not valid,
     * or with status {@code 500 (Internal Server Error)} if the aktivnosti couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/aktivnostis")
    public ResponseEntity<Aktivnosti> updateAktivnosti(@RequestBody Aktivnosti aktivnosti) throws URISyntaxException {
        log.debug("REST request to update Aktivnosti : {}", aktivnosti);
        if (aktivnosti.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Aktivnosti result = aktivnostiRepository.save(aktivnosti);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, aktivnosti.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /aktivnostis} : get all the aktivnostis.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of aktivnostis in body.
     */
    @GetMapping("/aktivnostis")
    public List<Aktivnosti> getAllAktivnostis() {
        log.debug("REST request to get all Aktivnostis");
        return aktivnostiRepository.findAll();
    }

    /**
     * {@code GET  /aktivnostis/:id} : get the "id" aktivnosti.
     *
     * @param id the id of the aktivnosti to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the aktivnosti, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/aktivnostis/{id}")
    public ResponseEntity<Aktivnosti> getAktivnosti(@PathVariable Long id) {
        log.debug("REST request to get Aktivnosti : {}", id);
        Optional<Aktivnosti> aktivnosti = aktivnostiRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(aktivnosti);
    }

    /**
     * {@code DELETE  /aktivnostis/:id} : delete the "id" aktivnosti.
     *
     * @param id the id of the aktivnosti to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/aktivnostis/{id}")
    public ResponseEntity<Void> deleteAktivnosti(@PathVariable Long id) {
        log.debug("REST request to delete Aktivnosti : {}", id);
        aktivnostiRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
