package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.domain.NaucneOblasti;
import eu.apeiron.dalibor.evaulacija.repository.NaucneOblastiRepository;
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
 * REST controller for managing {@link eu.apeiron.dalibor.evaulacija.domain.NaucneOblasti}.
 */
@RestController
@RequestMapping("/api")
public class NaucneOblastiResource {

    private final Logger log = LoggerFactory.getLogger(NaucneOblastiResource.class);

    private static final String ENTITY_NAME = "naucneOblasti";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NaucneOblastiRepository naucneOblastiRepository;

    public NaucneOblastiResource(NaucneOblastiRepository naucneOblastiRepository) {
        this.naucneOblastiRepository = naucneOblastiRepository;
    }

    /**
     * {@code POST  /naucne-oblastis} : Create a new naucneOblasti.
     *
     * @param naucneOblasti the naucneOblasti to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new naucneOblasti, or with status {@code 400 (Bad Request)} if the naucneOblasti has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/naucne-oblastis")
    public ResponseEntity<NaucneOblasti> createNaucneOblasti(@RequestBody NaucneOblasti naucneOblasti) throws URISyntaxException {
        log.debug("REST request to save NaucneOblasti : {}", naucneOblasti);
        if (naucneOblasti.getId() != null) {
            throw new BadRequestAlertException("A new naucneOblasti cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NaucneOblasti result = naucneOblastiRepository.save(naucneOblasti);
        return ResponseEntity.created(new URI("/api/naucne-oblastis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /naucne-oblastis} : Updates an existing naucneOblasti.
     *
     * @param naucneOblasti the naucneOblasti to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated naucneOblasti,
     * or with status {@code 400 (Bad Request)} if the naucneOblasti is not valid,
     * or with status {@code 500 (Internal Server Error)} if the naucneOblasti couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/naucne-oblastis")
    public ResponseEntity<NaucneOblasti> updateNaucneOblasti(@RequestBody NaucneOblasti naucneOblasti) throws URISyntaxException {
        log.debug("REST request to update NaucneOblasti : {}", naucneOblasti);
        if (naucneOblasti.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NaucneOblasti result = naucneOblastiRepository.save(naucneOblasti);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, naucneOblasti.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /naucne-oblastis} : get all the naucneOblastis.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of naucneOblastis in body.
     */
    @GetMapping("/naucne-oblastis")
    public List<NaucneOblasti> getAllNaucneOblastis() {
        log.debug("REST request to get all NaucneOblastis");
        return naucneOblastiRepository.findAll();
    }

    /**
     * {@code GET  /naucne-oblastis/:id} : get the "id" naucneOblasti.
     *
     * @param id the id of the naucneOblasti to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the naucneOblasti, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/naucne-oblastis/{id}")
    public ResponseEntity<NaucneOblasti> getNaucneOblasti(@PathVariable Long id) {
        log.debug("REST request to get NaucneOblasti : {}", id);
        Optional<NaucneOblasti> naucneOblasti = naucneOblastiRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(naucneOblasti);
    }

    /**
     * {@code DELETE  /naucne-oblastis/:id} : delete the "id" naucneOblasti.
     *
     * @param id the id of the naucneOblasti to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/naucne-oblastis/{id}")
    public ResponseEntity<Void> deleteNaucneOblasti(@PathVariable Long id) {
        log.debug("REST request to delete NaucneOblasti : {}", id);
        naucneOblastiRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
