package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.domain.VrstaMentorstva;
import eu.apeiron.dalibor.evaulacija.repository.VrstaMentorstvaRepository;
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
 * REST controller for managing {@link eu.apeiron.dalibor.evaulacija.domain.VrstaMentorstva}.
 */
@RestController
@RequestMapping("/api")
public class VrstaMentorstvaResource {

    private final Logger log = LoggerFactory.getLogger(VrstaMentorstvaResource.class);

    private static final String ENTITY_NAME = "vrstaMentorstva";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VrstaMentorstvaRepository vrstaMentorstvaRepository;

    public VrstaMentorstvaResource(VrstaMentorstvaRepository vrstaMentorstvaRepository) {
        this.vrstaMentorstvaRepository = vrstaMentorstvaRepository;
    }

    /**
     * {@code POST  /vrsta-mentorstvas} : Create a new vrstaMentorstva.
     *
     * @param vrstaMentorstva the vrstaMentorstva to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vrstaMentorstva, or with status {@code 400 (Bad Request)} if the vrstaMentorstva has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vrsta-mentorstvas")
    public ResponseEntity<VrstaMentorstva> createVrstaMentorstva(@Valid @RequestBody VrstaMentorstva vrstaMentorstva) throws URISyntaxException {
        log.debug("REST request to save VrstaMentorstva : {}", vrstaMentorstva);
        if (vrstaMentorstva.getId() != null) {
            throw new BadRequestAlertException("A new vrstaMentorstva cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VrstaMentorstva result = vrstaMentorstvaRepository.save(vrstaMentorstva);
        return ResponseEntity.created(new URI("/api/vrsta-mentorstvas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vrsta-mentorstvas} : Updates an existing vrstaMentorstva.
     *
     * @param vrstaMentorstva the vrstaMentorstva to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vrstaMentorstva,
     * or with status {@code 400 (Bad Request)} if the vrstaMentorstva is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vrstaMentorstva couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vrsta-mentorstvas")
    public ResponseEntity<VrstaMentorstva> updateVrstaMentorstva(@Valid @RequestBody VrstaMentorstva vrstaMentorstva) throws URISyntaxException {
        log.debug("REST request to update VrstaMentorstva : {}", vrstaMentorstva);
        if (vrstaMentorstva.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VrstaMentorstva result = vrstaMentorstvaRepository.save(vrstaMentorstva);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vrstaMentorstva.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vrsta-mentorstvas} : get all the vrstaMentorstvas.
     *

     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vrstaMentorstvas in body.
     */
    @GetMapping("/vrsta-mentorstvas")
    public List<VrstaMentorstva> getAllVrstaMentorstvas(@RequestParam(required = false) String filter) {
        if ("diplome-is-null".equals(filter)) {
            log.debug("REST request to get all VrstaMentorstvas where diplome is null");
            return StreamSupport
                .stream(vrstaMentorstvaRepository.findAll().spliterator(), false)
                .filter(vrstaMentorstva -> vrstaMentorstva.getDiplome() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all VrstaMentorstvas");
        return vrstaMentorstvaRepository.findAll();
    }

    /**
     * {@code GET  /vrsta-mentorstvas/:id} : get the "id" vrstaMentorstva.
     *
     * @param id the id of the vrstaMentorstva to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vrstaMentorstva, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vrsta-mentorstvas/{id}")
    public ResponseEntity<VrstaMentorstva> getVrstaMentorstva(@PathVariable Long id) {
        log.debug("REST request to get VrstaMentorstva : {}", id);
        Optional<VrstaMentorstva> vrstaMentorstva = vrstaMentorstvaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vrstaMentorstva);
    }

    /**
     * {@code DELETE  /vrsta-mentorstvas/:id} : delete the "id" vrstaMentorstva.
     *
     * @param id the id of the vrstaMentorstva to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vrsta-mentorstvas/{id}")
    public ResponseEntity<Void> deleteVrstaMentorstva(@PathVariable Long id) {
        log.debug("REST request to delete VrstaMentorstva : {}", id);
        vrstaMentorstvaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
