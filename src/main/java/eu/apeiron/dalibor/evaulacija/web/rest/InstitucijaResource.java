package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.domain.Institucija;
import eu.apeiron.dalibor.evaulacija.repository.InstitucijaRepository;
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
 * REST controller for managing {@link eu.apeiron.dalibor.evaulacija.domain.Institucija}.
 */
@RestController
@RequestMapping("/api")
public class InstitucijaResource {

    private final Logger log = LoggerFactory.getLogger(InstitucijaResource.class);

    private static final String ENTITY_NAME = "institucija";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InstitucijaRepository institucijaRepository;

    public InstitucijaResource(InstitucijaRepository institucijaRepository) {
        this.institucijaRepository = institucijaRepository;
    }

    /**
     * {@code POST  /institucijas} : Create a new institucija.
     *
     * @param institucija the institucija to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new institucija, or with status {@code 400 (Bad Request)} if the institucija has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/institucijas")
    public ResponseEntity<Institucija> createInstitucija(@RequestBody Institucija institucija) throws URISyntaxException {
        log.debug("REST request to save Institucija : {}", institucija);
        if (institucija.getId() != null) {
            throw new BadRequestAlertException("A new institucija cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Institucija result = institucijaRepository.save(institucija);
        return ResponseEntity.created(new URI("/api/institucijas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /institucijas} : Updates an existing institucija.
     *
     * @param institucija the institucija to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated institucija,
     * or with status {@code 400 (Bad Request)} if the institucija is not valid,
     * or with status {@code 500 (Internal Server Error)} if the institucija couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/institucijas")
    public ResponseEntity<Institucija> updateInstitucija(@RequestBody Institucija institucija) throws URISyntaxException {
        log.debug("REST request to update Institucija : {}", institucija);
        if (institucija.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Institucija result = institucijaRepository.save(institucija);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, institucija.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /institucijas} : get all the institucijas.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of institucijas in body.
     */
    @GetMapping("/institucijas")
    public List<Institucija> getAllInstitucijas() {
        log.debug("REST request to get all Institucijas");
        return institucijaRepository.findAll();
    }

    /**
     * {@code GET  /institucijas/:id} : get the "id" institucija.
     *
     * @param id the id of the institucija to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the institucija, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/institucijas/{id}")
    public ResponseEntity<Institucija> getInstitucija(@PathVariable Long id) {
        log.debug("REST request to get Institucija : {}", id);
        Optional<Institucija> institucija = institucijaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(institucija);
    }

    /**
     * {@code DELETE  /institucijas/:id} : delete the "id" institucija.
     *
     * @param id the id of the institucija to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/institucijas/{id}")
    public ResponseEntity<Void> deleteInstitucija(@PathVariable Long id) {
        log.debug("REST request to delete Institucija : {}", id);
        institucijaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
