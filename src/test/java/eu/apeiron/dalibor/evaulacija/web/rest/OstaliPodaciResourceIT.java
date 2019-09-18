package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.EvaluacijaApp;
import eu.apeiron.dalibor.evaulacija.domain.OstaliPodaci;
import eu.apeiron.dalibor.evaulacija.repository.OstaliPodaciRepository;
import eu.apeiron.dalibor.evaulacija.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static eu.apeiron.dalibor.evaulacija.web.rest.TestUtil.sameInstant;
import static eu.apeiron.dalibor.evaulacija.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OstaliPodaciResource} REST controller.
 */
@SpringBootTest(classes = EvaluacijaApp.class)
public class OstaliPodaciResourceIT {

    private static final String DEFAULT_OSTALO = "AAAAAAAAAA";
    private static final String UPDATED_OSTALO = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATUM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATUM = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATUM = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private OstaliPodaciRepository ostaliPodaciRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restOstaliPodaciMockMvc;

    private OstaliPodaci ostaliPodaci;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OstaliPodaciResource ostaliPodaciResource = new OstaliPodaciResource(ostaliPodaciRepository);
        this.restOstaliPodaciMockMvc = MockMvcBuilders.standaloneSetup(ostaliPodaciResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OstaliPodaci createEntity(EntityManager em) {
        OstaliPodaci ostaliPodaci = new OstaliPodaci()
            .ostalo(DEFAULT_OSTALO)
            .datum(DEFAULT_DATUM);
        return ostaliPodaci;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OstaliPodaci createUpdatedEntity(EntityManager em) {
        OstaliPodaci ostaliPodaci = new OstaliPodaci()
            .ostalo(UPDATED_OSTALO)
            .datum(UPDATED_DATUM);
        return ostaliPodaci;
    }

    @BeforeEach
    public void initTest() {
        ostaliPodaci = createEntity(em);
    }

    @Test
    @Transactional
    public void createOstaliPodaci() throws Exception {
        int databaseSizeBeforeCreate = ostaliPodaciRepository.findAll().size();

        // Create the OstaliPodaci
        restOstaliPodaciMockMvc.perform(post("/api/ostali-podacis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ostaliPodaci)))
            .andExpect(status().isCreated());

        // Validate the OstaliPodaci in the database
        List<OstaliPodaci> ostaliPodaciList = ostaliPodaciRepository.findAll();
        assertThat(ostaliPodaciList).hasSize(databaseSizeBeforeCreate + 1);
        OstaliPodaci testOstaliPodaci = ostaliPodaciList.get(ostaliPodaciList.size() - 1);
        assertThat(testOstaliPodaci.getOstalo()).isEqualTo(DEFAULT_OSTALO);
        assertThat(testOstaliPodaci.getDatum()).isEqualTo(DEFAULT_DATUM);
    }

    @Test
    @Transactional
    public void createOstaliPodaciWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ostaliPodaciRepository.findAll().size();

        // Create the OstaliPodaci with an existing ID
        ostaliPodaci.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOstaliPodaciMockMvc.perform(post("/api/ostali-podacis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ostaliPodaci)))
            .andExpect(status().isBadRequest());

        // Validate the OstaliPodaci in the database
        List<OstaliPodaci> ostaliPodaciList = ostaliPodaciRepository.findAll();
        assertThat(ostaliPodaciList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOstaliPodacis() throws Exception {
        // Initialize the database
        ostaliPodaciRepository.saveAndFlush(ostaliPodaci);

        // Get all the ostaliPodaciList
        restOstaliPodaciMockMvc.perform(get("/api/ostali-podacis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ostaliPodaci.getId().intValue())))
            .andExpect(jsonPath("$.[*].ostalo").value(hasItem(DEFAULT_OSTALO.toString())))
            .andExpect(jsonPath("$.[*].datum").value(hasItem(sameInstant(DEFAULT_DATUM))));
    }
    
    @Test
    @Transactional
    public void getOstaliPodaci() throws Exception {
        // Initialize the database
        ostaliPodaciRepository.saveAndFlush(ostaliPodaci);

        // Get the ostaliPodaci
        restOstaliPodaciMockMvc.perform(get("/api/ostali-podacis/{id}", ostaliPodaci.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ostaliPodaci.getId().intValue()))
            .andExpect(jsonPath("$.ostalo").value(DEFAULT_OSTALO.toString()))
            .andExpect(jsonPath("$.datum").value(sameInstant(DEFAULT_DATUM)));
    }

    @Test
    @Transactional
    public void getNonExistingOstaliPodaci() throws Exception {
        // Get the ostaliPodaci
        restOstaliPodaciMockMvc.perform(get("/api/ostali-podacis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOstaliPodaci() throws Exception {
        // Initialize the database
        ostaliPodaciRepository.saveAndFlush(ostaliPodaci);

        int databaseSizeBeforeUpdate = ostaliPodaciRepository.findAll().size();

        // Update the ostaliPodaci
        OstaliPodaci updatedOstaliPodaci = ostaliPodaciRepository.findById(ostaliPodaci.getId()).get();
        // Disconnect from session so that the updates on updatedOstaliPodaci are not directly saved in db
        em.detach(updatedOstaliPodaci);
        updatedOstaliPodaci
            .ostalo(UPDATED_OSTALO)
            .datum(UPDATED_DATUM);

        restOstaliPodaciMockMvc.perform(put("/api/ostali-podacis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOstaliPodaci)))
            .andExpect(status().isOk());

        // Validate the OstaliPodaci in the database
        List<OstaliPodaci> ostaliPodaciList = ostaliPodaciRepository.findAll();
        assertThat(ostaliPodaciList).hasSize(databaseSizeBeforeUpdate);
        OstaliPodaci testOstaliPodaci = ostaliPodaciList.get(ostaliPodaciList.size() - 1);
        assertThat(testOstaliPodaci.getOstalo()).isEqualTo(UPDATED_OSTALO);
        assertThat(testOstaliPodaci.getDatum()).isEqualTo(UPDATED_DATUM);
    }

    @Test
    @Transactional
    public void updateNonExistingOstaliPodaci() throws Exception {
        int databaseSizeBeforeUpdate = ostaliPodaciRepository.findAll().size();

        // Create the OstaliPodaci

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOstaliPodaciMockMvc.perform(put("/api/ostali-podacis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ostaliPodaci)))
            .andExpect(status().isBadRequest());

        // Validate the OstaliPodaci in the database
        List<OstaliPodaci> ostaliPodaciList = ostaliPodaciRepository.findAll();
        assertThat(ostaliPodaciList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOstaliPodaci() throws Exception {
        // Initialize the database
        ostaliPodaciRepository.saveAndFlush(ostaliPodaci);

        int databaseSizeBeforeDelete = ostaliPodaciRepository.findAll().size();

        // Delete the ostaliPodaci
        restOstaliPodaciMockMvc.perform(delete("/api/ostali-podacis/{id}", ostaliPodaci.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OstaliPodaci> ostaliPodaciList = ostaliPodaciRepository.findAll();
        assertThat(ostaliPodaciList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OstaliPodaci.class);
        OstaliPodaci ostaliPodaci1 = new OstaliPodaci();
        ostaliPodaci1.setId(1L);
        OstaliPodaci ostaliPodaci2 = new OstaliPodaci();
        ostaliPodaci2.setId(ostaliPodaci1.getId());
        assertThat(ostaliPodaci1).isEqualTo(ostaliPodaci2);
        ostaliPodaci2.setId(2L);
        assertThat(ostaliPodaci1).isNotEqualTo(ostaliPodaci2);
        ostaliPodaci1.setId(null);
        assertThat(ostaliPodaci1).isNotEqualTo(ostaliPodaci2);
    }
}
