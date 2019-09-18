package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.domain.OstaliPodaci;
import eu.apeiron.dalibor.evaulacija.repository.OstaliPodaciRepository;
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
 * REST controller for managing {@link eu.apeiron.dalibor.evaulacija.domain.OstaliPodaci}.
 */
@RestController
@RequestMapping("/api")
public class OstaliPodaciResource {

    private final Logger log = LoggerFactory.getLogger(OstaliPodaciResource.class);

    private static final String ENTITY_NAME = "ostaliPodaci";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OstaliPodaciRepository ostaliPodaciRepository;

    public OstaliPodaciResource(OstaliPodaciRepository ostaliPodaciRepository) {
        this.ostaliPodaciRepository = ostaliPodaciRepository;
    }

    /**
     * {@code POST  /ostali-podacis} : Create a new ostaliPodaci.
     *
     * @param ostaliPodaci the ostaliPodaci to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ostaliPodaci, or with status {@code 400 (Bad Request)} if the ostaliPodaci has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ostali-podacis")
    public ResponseEntity<OstaliPodaci> createOstaliPodaci(@RequestBody OstaliPodaci ostaliPodaci) throws URISyntaxException {
        log.debug("REST request to save OstaliPodaci : {}", ostaliPodaci);
        if (ostaliPodaci.getId() != null) {
            throw new BadRequestAlertException("A new ostaliPodaci cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OstaliPodaci result = ostaliPodaciRepository.save(ostaliPodaci);
        return ResponseEntity.created(new URI("/api/ostali-podacis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ostali-podacis} : Updates an existing ostaliPodaci.
     *
     * @param ostaliPodaci the ostaliPodaci to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ostaliPodaci,
     * or with status {@code 400 (Bad Request)} if the ostaliPodaci is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ostaliPodaci couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ostali-podacis")
    public ResponseEntity<OstaliPodaci> updateOstaliPodaci(@RequestBody OstaliPodaci ostaliPodaci) throws URISyntaxException {
        log.debug("REST request to update OstaliPodaci : {}", ostaliPodaci);
        if (ostaliPodaci.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OstaliPodaci result = ostaliPodaciRepository.save(ostaliPodaci);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ostaliPodaci.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ostali-podacis} : get all the ostaliPodacis.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ostaliPodacis in body.
     */
    @GetMapping("/ostali-podacis")
    public List<OstaliPodaci> getAllOstaliPodacis() {
        log.debug("REST request to get all OstaliPodacis");
        return ostaliPodaciRepository.findAll();
    }

    /**
     * {@code GET  /ostali-podacis/:id} : get the "id" ostaliPodaci.
     *
     * @param id the id of the ostaliPodaci to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ostaliPodaci, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ostali-podacis/{id}")
    public ResponseEntity<OstaliPodaci> getOstaliPodaci(@PathVariable Long id) {
        log.debug("REST request to get OstaliPodaci : {}", id);
        Optional<OstaliPodaci> ostaliPodaci = ostaliPodaciRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ostaliPodaci);
    }

    /**
     * {@code DELETE  /ostali-podacis/:id} : delete the "id" ostaliPodaci.
     *
     * @param id the id of the ostaliPodaci to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ostali-podacis/{id}")
    public ResponseEntity<Void> deleteOstaliPodaci(@PathVariable Long id) {
        log.debug("REST request to delete OstaliPodaci : {}", id);
        ostaliPodaciRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
