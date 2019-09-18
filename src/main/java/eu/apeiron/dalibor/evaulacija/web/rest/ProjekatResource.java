package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.domain.Projekat;
import eu.apeiron.dalibor.evaulacija.repository.ProjekatRepository;
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
 * REST controller for managing {@link eu.apeiron.dalibor.evaulacija.domain.Projekat}.
 */
@RestController
@RequestMapping("/api")
public class ProjekatResource {

    private final Logger log = LoggerFactory.getLogger(ProjekatResource.class);

    private static final String ENTITY_NAME = "projekat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjekatRepository projekatRepository;

    public ProjekatResource(ProjekatRepository projekatRepository) {
        this.projekatRepository = projekatRepository;
    }

    /**
     * {@code POST  /projekats} : Create a new projekat.
     *
     * @param projekat the projekat to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projekat, or with status {@code 400 (Bad Request)} if the projekat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/projekats")
    public ResponseEntity<Projekat> createProjekat(@Valid @RequestBody Projekat projekat) throws URISyntaxException {
        log.debug("REST request to save Projekat : {}", projekat);
        if (projekat.getId() != null) {
            throw new BadRequestAlertException("A new projekat cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Projekat result = projekatRepository.save(projekat);
        return ResponseEntity.created(new URI("/api/projekats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /projekats} : Updates an existing projekat.
     *
     * @param projekat the projekat to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projekat,
     * or with status {@code 400 (Bad Request)} if the projekat is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projekat couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/projekats")
    public ResponseEntity<Projekat> updateProjekat(@Valid @RequestBody Projekat projekat) throws URISyntaxException {
        log.debug("REST request to update Projekat : {}", projekat);
        if (projekat.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Projekat result = projekatRepository.save(projekat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, projekat.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /projekats} : get all the projekats.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projekats in body.
     */
    @GetMapping("/projekats")
    public List<Projekat> getAllProjekats() {
        log.debug("REST request to get all Projekats");
        return projekatRepository.findAll();
    }

    /**
     * {@code GET  /projekats/:id} : get the "id" projekat.
     *
     * @param id the id of the projekat to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projekat, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/projekats/{id}")
    public ResponseEntity<Projekat> getProjekat(@PathVariable Long id) {
        log.debug("REST request to get Projekat : {}", id);
        Optional<Projekat> projekat = projekatRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(projekat);
    }

    /**
     * {@code DELETE  /projekats/:id} : delete the "id" projekat.
     *
     * @param id the id of the projekat to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/projekats/{id}")
    public ResponseEntity<Void> deleteProjekat(@PathVariable Long id) {
        log.debug("REST request to delete Projekat : {}", id);
        projekatRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
