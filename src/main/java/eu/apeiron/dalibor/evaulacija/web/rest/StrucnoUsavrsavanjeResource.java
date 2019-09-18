package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.domain.StrucnoUsavrsavanje;
import eu.apeiron.dalibor.evaulacija.repository.StrucnoUsavrsavanjeRepository;
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
 * REST controller for managing {@link eu.apeiron.dalibor.evaulacija.domain.StrucnoUsavrsavanje}.
 */
@RestController
@RequestMapping("/api")
public class StrucnoUsavrsavanjeResource {

    private final Logger log = LoggerFactory.getLogger(StrucnoUsavrsavanjeResource.class);

    private static final String ENTITY_NAME = "strucnoUsavrsavanje";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StrucnoUsavrsavanjeRepository strucnoUsavrsavanjeRepository;

    public StrucnoUsavrsavanjeResource(StrucnoUsavrsavanjeRepository strucnoUsavrsavanjeRepository) {
        this.strucnoUsavrsavanjeRepository = strucnoUsavrsavanjeRepository;
    }

    /**
     * {@code POST  /strucno-usavrsavanjes} : Create a new strucnoUsavrsavanje.
     *
     * @param strucnoUsavrsavanje the strucnoUsavrsavanje to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new strucnoUsavrsavanje, or with status {@code 400 (Bad Request)} if the strucnoUsavrsavanje has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/strucno-usavrsavanjes")
    public ResponseEntity<StrucnoUsavrsavanje> createStrucnoUsavrsavanje(@Valid @RequestBody StrucnoUsavrsavanje strucnoUsavrsavanje) throws URISyntaxException {
        log.debug("REST request to save StrucnoUsavrsavanje : {}", strucnoUsavrsavanje);
        if (strucnoUsavrsavanje.getId() != null) {
            throw new BadRequestAlertException("A new strucnoUsavrsavanje cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StrucnoUsavrsavanje result = strucnoUsavrsavanjeRepository.save(strucnoUsavrsavanje);
        return ResponseEntity.created(new URI("/api/strucno-usavrsavanjes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /strucno-usavrsavanjes} : Updates an existing strucnoUsavrsavanje.
     *
     * @param strucnoUsavrsavanje the strucnoUsavrsavanje to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated strucnoUsavrsavanje,
     * or with status {@code 400 (Bad Request)} if the strucnoUsavrsavanje is not valid,
     * or with status {@code 500 (Internal Server Error)} if the strucnoUsavrsavanje couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/strucno-usavrsavanjes")
    public ResponseEntity<StrucnoUsavrsavanje> updateStrucnoUsavrsavanje(@Valid @RequestBody StrucnoUsavrsavanje strucnoUsavrsavanje) throws URISyntaxException {
        log.debug("REST request to update StrucnoUsavrsavanje : {}", strucnoUsavrsavanje);
        if (strucnoUsavrsavanje.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StrucnoUsavrsavanje result = strucnoUsavrsavanjeRepository.save(strucnoUsavrsavanje);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, strucnoUsavrsavanje.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /strucno-usavrsavanjes} : get all the strucnoUsavrsavanjes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of strucnoUsavrsavanjes in body.
     */
    @GetMapping("/strucno-usavrsavanjes")
    public List<StrucnoUsavrsavanje> getAllStrucnoUsavrsavanjes() {
        log.debug("REST request to get all StrucnoUsavrsavanjes");
        return strucnoUsavrsavanjeRepository.findAll();
    }

    /**
     * {@code GET  /strucno-usavrsavanjes/:id} : get the "id" strucnoUsavrsavanje.
     *
     * @param id the id of the strucnoUsavrsavanje to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the strucnoUsavrsavanje, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/strucno-usavrsavanjes/{id}")
    public ResponseEntity<StrucnoUsavrsavanje> getStrucnoUsavrsavanje(@PathVariable Long id) {
        log.debug("REST request to get StrucnoUsavrsavanje : {}", id);
        Optional<StrucnoUsavrsavanje> strucnoUsavrsavanje = strucnoUsavrsavanjeRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(strucnoUsavrsavanje);
    }

    /**
     * {@code DELETE  /strucno-usavrsavanjes/:id} : delete the "id" strucnoUsavrsavanje.
     *
     * @param id the id of the strucnoUsavrsavanje to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/strucno-usavrsavanjes/{id}")
    public ResponseEntity<Void> deleteStrucnoUsavrsavanje(@PathVariable Long id) {
        log.debug("REST request to delete StrucnoUsavrsavanje : {}", id);
        strucnoUsavrsavanjeRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
