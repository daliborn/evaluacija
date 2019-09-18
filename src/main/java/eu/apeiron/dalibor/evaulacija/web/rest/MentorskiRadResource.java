package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.domain.MentorskiRad;
import eu.apeiron.dalibor.evaulacija.repository.MentorskiRadRepository;
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
 * REST controller for managing {@link eu.apeiron.dalibor.evaulacija.domain.MentorskiRad}.
 */
@RestController
@RequestMapping("/api")
public class MentorskiRadResource {

    private final Logger log = LoggerFactory.getLogger(MentorskiRadResource.class);

    private static final String ENTITY_NAME = "mentorskiRad";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MentorskiRadRepository mentorskiRadRepository;

    public MentorskiRadResource(MentorskiRadRepository mentorskiRadRepository) {
        this.mentorskiRadRepository = mentorskiRadRepository;
    }

    /**
     * {@code POST  /mentorski-rads} : Create a new mentorskiRad.
     *
     * @param mentorskiRad the mentorskiRad to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new mentorskiRad, or with status {@code 400 (Bad Request)} if the mentorskiRad has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/mentorski-rads")
    public ResponseEntity<MentorskiRad> createMentorskiRad(@Valid @RequestBody MentorskiRad mentorskiRad) throws URISyntaxException {
        log.debug("REST request to save MentorskiRad : {}", mentorskiRad);
        if (mentorskiRad.getId() != null) {
            throw new BadRequestAlertException("A new mentorskiRad cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MentorskiRad result = mentorskiRadRepository.save(mentorskiRad);
        return ResponseEntity.created(new URI("/api/mentorski-rads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /mentorski-rads} : Updates an existing mentorskiRad.
     *
     * @param mentorskiRad the mentorskiRad to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated mentorskiRad,
     * or with status {@code 400 (Bad Request)} if the mentorskiRad is not valid,
     * or with status {@code 500 (Internal Server Error)} if the mentorskiRad couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/mentorski-rads")
    public ResponseEntity<MentorskiRad> updateMentorskiRad(@Valid @RequestBody MentorskiRad mentorskiRad) throws URISyntaxException {
        log.debug("REST request to update MentorskiRad : {}", mentorskiRad);
        if (mentorskiRad.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MentorskiRad result = mentorskiRadRepository.save(mentorskiRad);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, mentorskiRad.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /mentorski-rads} : get all the mentorskiRads.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of mentorskiRads in body.
     */
    @GetMapping("/mentorski-rads")
    public List<MentorskiRad> getAllMentorskiRads() {
        log.debug("REST request to get all MentorskiRads");
        return mentorskiRadRepository.findAll();
    }

    /**
     * {@code GET  /mentorski-rads/:id} : get the "id" mentorskiRad.
     *
     * @param id the id of the mentorskiRad to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the mentorskiRad, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/mentorski-rads/{id}")
    public ResponseEntity<MentorskiRad> getMentorskiRad(@PathVariable Long id) {
        log.debug("REST request to get MentorskiRad : {}", id);
        Optional<MentorskiRad> mentorskiRad = mentorskiRadRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(mentorskiRad);
    }

    /**
     * {@code DELETE  /mentorski-rads/:id} : delete the "id" mentorskiRad.
     *
     * @param id the id of the mentorskiRad to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/mentorski-rads/{id}")
    public ResponseEntity<Void> deleteMentorskiRad(@PathVariable Long id) {
        log.debug("REST request to delete MentorskiRad : {}", id);
        mentorskiRadRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
