package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.domain.Kandidat;
import eu.apeiron.dalibor.evaulacija.repository.KandidatRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link eu.apeiron.dalibor.evaulacija.domain.Kandidat}.
 */
@RestController
@RequestMapping("/api")
public class KandidatResource {

    private final Logger log = LoggerFactory.getLogger(KandidatResource.class);

    private static final String ENTITY_NAME = "kandidat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KandidatRepository kandidatRepository;

    public KandidatResource(KandidatRepository kandidatRepository) {
        this.kandidatRepository = kandidatRepository;
    }

    /**
     * {@code POST  /kandidats} : Create a new kandidat.
     *
     * @param kandidat the kandidat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kandidat, or with status {@code 400 (Bad Request)} if the kandidat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kandidats")
    public ResponseEntity<Kandidat> createKandidat(@Valid @RequestBody Kandidat kandidat) throws URISyntaxException {
        log.debug("REST request to save Kandidat : {}", kandidat);
        if (kandidat.getId() != null) {
            throw new BadRequestAlertException("A new kandidat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Kandidat result = kandidatRepository.save(kandidat);
        return ResponseEntity.created(new URI("/api/kandidats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kandidats} : Updates an existing kandidat.
     *
     * @param kandidat the kandidat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kandidat,
     * or with status {@code 400 (Bad Request)} if the kandidat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kandidat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kandidats")
    public ResponseEntity<Kandidat> updateKandidat(@Valid @RequestBody Kandidat kandidat) throws URISyntaxException {
        log.debug("REST request to update Kandidat : {}", kandidat);
        if (kandidat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Kandidat result = kandidatRepository.save(kandidat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, kandidat.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /kandidats} : get all the kandidats.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kandidats in body.
     */
    @GetMapping("/kandidats")
    public List<Kandidat> getAllKandidats(@RequestParam(required = false) String filter) {
        if ("diplome-is-null".equals(filter)) {
            log.debug("REST request to get all Kandidats where diplome is null");
            return StreamSupport
                .stream(kandidatRepository.findAll().spliterator(), false)
                .filter(kandidat -> kandidat.getDiplome() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Kandidats");
        return kandidatRepository.findAll();
    }

    /**
     * {@code GET  /kandidats/:id} : get the "id" kandidat.
     *
     * @param id the id of the kandidat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kandidat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kandidats/{id}")
    public ResponseEntity<Kandidat> getKandidat(@PathVariable Long id) {
        log.debug("REST request to get Kandidat : {}", id);
        Optional<Kandidat> kandidat = kandidatRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(kandidat);
    }

    /**
     * {@code DELETE  /kandidats/:id} : delete the "id" kandidat.
     *
     * @param id the id of the kandidat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kandidats/{id}")
    public ResponseEntity<Void> deleteKandidat(@PathVariable Long id) {
        log.debug("REST request to delete Kandidat : {}", id);
        kandidatRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
