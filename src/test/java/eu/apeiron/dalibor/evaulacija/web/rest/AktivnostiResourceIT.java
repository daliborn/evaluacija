package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.EvaluacijaApp;
import eu.apeiron.dalibor.evaulacija.domain.Aktivnosti;
import eu.apeiron.dalibor.evaulacija.repository.AktivnostiRepository;
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
import java.util.List;

import static eu.apeiron.dalibor.evaulacija.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AktivnostiResource} REST controller.
 */
@SpringBootTest(classes = EvaluacijaApp.class)
public class AktivnostiResourceIT {

    private static final Integer DEFAULT_BROJ_CITATA = 1;
    private static final Integer UPDATED_BROJ_CITATA = 2;
    private static final Integer SMALLER_BROJ_CITATA = 1 - 1;

    private static final Integer DEFAULT_BROJ_RADOVA = 1;
    private static final Integer UPDATED_BROJ_RADOVA = 2;
    private static final Integer SMALLER_BROJ_RADOVA = 1 - 1;

    private static final Integer DEFAULT_BROJ_DOMACI_PROJEKATA = 1;
    private static final Integer UPDATED_BROJ_DOMACI_PROJEKATA = 2;
    private static final Integer SMALLER_BROJ_DOMACI_PROJEKATA = 1 - 1;

    private static final Integer DEFAULT_BROJ_MEDJUNARODNIH_PROJEKATA = 1;
    private static final Integer UPDATED_BROJ_MEDJUNARODNIH_PROJEKATA = 2;
    private static final Integer SMALLER_BROJ_MEDJUNARODNIH_PROJEKATA = 1 - 1;

    @Autowired
    private AktivnostiRepository aktivnostiRepository;

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

    private MockMvc restAktivnostiMockMvc;

    private Aktivnosti aktivnosti;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AktivnostiResource aktivnostiResource = new AktivnostiResource(aktivnostiRepository);
        this.restAktivnostiMockMvc = MockMvcBuilders.standaloneSetup(aktivnostiResource)
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
    public static Aktivnosti createEntity(EntityManager em) {
        Aktivnosti aktivnosti = new Aktivnosti()
            .brojCitata(DEFAULT_BROJ_CITATA)
            .brojRadova(DEFAULT_BROJ_RADOVA)
            .brojDomaciProjekata(DEFAULT_BROJ_DOMACI_PROJEKATA)
            .brojMedjunarodnihProjekata(DEFAULT_BROJ_MEDJUNARODNIH_PROJEKATA);
        return aktivnosti;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Aktivnosti createUpdatedEntity(EntityManager em) {
        Aktivnosti aktivnosti = new Aktivnosti()
            .brojCitata(UPDATED_BROJ_CITATA)
            .brojRadova(UPDATED_BROJ_RADOVA)
            .brojDomaciProjekata(UPDATED_BROJ_DOMACI_PROJEKATA)
            .brojMedjunarodnihProjekata(UPDATED_BROJ_MEDJUNARODNIH_PROJEKATA);
        return aktivnosti;
    }

    @BeforeEach
    public void initTest() {
        aktivnosti = createEntity(em);
    }

    @Test
    @Transactional
    public void createAktivnosti() throws Exception {
        int databaseSizeBeforeCreate = aktivnostiRepository.findAll().size();

        // Create the Aktivnosti
        restAktivnostiMockMvc.perform(post("/api/aktivnostis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aktivnosti)))
            .andExpect(status().isCreated());

        // Validate the Aktivnosti in the database
        List<Aktivnosti> aktivnostiList = aktivnostiRepository.findAll();
        assertThat(aktivnostiList).hasSize(databaseSizeBeforeCreate + 1);
        Aktivnosti testAktivnosti = aktivnostiList.get(aktivnostiList.size() - 1);
        assertThat(testAktivnosti.getBrojCitata()).isEqualTo(DEFAULT_BROJ_CITATA);
        assertThat(testAktivnosti.getBrojRadova()).isEqualTo(DEFAULT_BROJ_RADOVA);
        assertThat(testAktivnosti.getBrojDomaciProjekata()).isEqualTo(DEFAULT_BROJ_DOMACI_PROJEKATA);
        assertThat(testAktivnosti.getBrojMedjunarodnihProjekata()).isEqualTo(DEFAULT_BROJ_MEDJUNARODNIH_PROJEKATA);
    }

    @Test
    @Transactional
    public void createAktivnostiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = aktivnostiRepository.findAll().size();

        // Create the Aktivnosti with an existing ID
        aktivnosti.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAktivnostiMockMvc.perform(post("/api/aktivnostis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aktivnosti)))
            .andExpect(status().isBadRequest());

        // Validate the Aktivnosti in the database
        List<Aktivnosti> aktivnostiList = aktivnostiRepository.findAll();
        assertThat(aktivnostiList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllAktivnostis() throws Exception {
        // Initialize the database
        aktivnostiRepository.saveAndFlush(aktivnosti);

        // Get all the aktivnostiList
        restAktivnostiMockMvc.perform(get("/api/aktivnostis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(aktivnosti.getId().intValue())))
            .andExpect(jsonPath("$.[*].brojCitata").value(hasItem(DEFAULT_BROJ_CITATA)))
            .andExpect(jsonPath("$.[*].brojRadova").value(hasItem(DEFAULT_BROJ_RADOVA)))
            .andExpect(jsonPath("$.[*].brojDomaciProjekata").value(hasItem(DEFAULT_BROJ_DOMACI_PROJEKATA)))
            .andExpect(jsonPath("$.[*].brojMedjunarodnihProjekata").value(hasItem(DEFAULT_BROJ_MEDJUNARODNIH_PROJEKATA)));
    }
    
    @Test
    @Transactional
    public void getAktivnosti() throws Exception {
        // Initialize the database
        aktivnostiRepository.saveAndFlush(aktivnosti);

        // Get the aktivnosti
        restAktivnostiMockMvc.perform(get("/api/aktivnostis/{id}", aktivnosti.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(aktivnosti.getId().intValue()))
            .andExpect(jsonPath("$.brojCitata").value(DEFAULT_BROJ_CITATA))
            .andExpect(jsonPath("$.brojRadova").value(DEFAULT_BROJ_RADOVA))
            .andExpect(jsonPath("$.brojDomaciProjekata").value(DEFAULT_BROJ_DOMACI_PROJEKATA))
            .andExpect(jsonPath("$.brojMedjunarodnihProjekata").value(DEFAULT_BROJ_MEDJUNARODNIH_PROJEKATA));
    }

    @Test
    @Transactional
    public void getNonExistingAktivnosti() throws Exception {
        // Get the aktivnosti
        restAktivnostiMockMvc.perform(get("/api/aktivnostis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAktivnosti() throws Exception {
        // Initialize the database
        aktivnostiRepository.saveAndFlush(aktivnosti);

        int databaseSizeBeforeUpdate = aktivnostiRepository.findAll().size();

        // Update the aktivnosti
        Aktivnosti updatedAktivnosti = aktivnostiRepository.findById(aktivnosti.getId()).get();
        // Disconnect from session so that the updates on updatedAktivnosti are not directly saved in db
        em.detach(updatedAktivnosti);
        updatedAktivnosti
            .brojCitata(UPDATED_BROJ_CITATA)
            .brojRadova(UPDATED_BROJ_RADOVA)
            .brojDomaciProjekata(UPDATED_BROJ_DOMACI_PROJEKATA)
            .brojMedjunarodnihProjekata(UPDATED_BROJ_MEDJUNARODNIH_PROJEKATA);

        restAktivnostiMockMvc.perform(put("/api/aktivnostis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAktivnosti)))
            .andExpect(status().isOk());

        // Validate the Aktivnosti in the database
        List<Aktivnosti> aktivnostiList = aktivnostiRepository.findAll();
        assertThat(aktivnostiList).hasSize(databaseSizeBeforeUpdate);
        Aktivnosti testAktivnosti = aktivnostiList.get(aktivnostiList.size() - 1);
        assertThat(testAktivnosti.getBrojCitata()).isEqualTo(UPDATED_BROJ_CITATA);
        assertThat(testAktivnosti.getBrojRadova()).isEqualTo(UPDATED_BROJ_RADOVA);
        assertThat(testAktivnosti.getBrojDomaciProjekata()).isEqualTo(UPDATED_BROJ_DOMACI_PROJEKATA);
        assertThat(testAktivnosti.getBrojMedjunarodnihProjekata()).isEqualTo(UPDATED_BROJ_MEDJUNARODNIH_PROJEKATA);
    }

    @Test
    @Transactional
    public void updateNonExistingAktivnosti() throws Exception {
        int databaseSizeBeforeUpdate = aktivnostiRepository.findAll().size();

        // Create the Aktivnosti

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAktivnostiMockMvc.perform(put("/api/aktivnostis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(aktivnosti)))
            .andExpect(status().isBadRequest());

        // Validate the Aktivnosti in the database
        List<Aktivnosti> aktivnostiList = aktivnostiRepository.findAll();
        assertThat(aktivnostiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAktivnosti() throws Exception {
        // Initialize the database
        aktivnostiRepository.saveAndFlush(aktivnosti);

        int databaseSizeBeforeDelete = aktivnostiRepository.findAll().size();

        // Delete the aktivnosti
        restAktivnostiMockMvc.perform(delete("/api/aktivnostis/{id}", aktivnosti.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Aktivnosti> aktivnostiList = aktivnostiRepository.findAll();
        assertThat(aktivnostiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aktivnosti.class);
        Aktivnosti aktivnosti1 = new Aktivnosti();
        aktivnosti1.setId(1L);
        Aktivnosti aktivnosti2 = new Aktivnosti();
        aktivnosti2.setId(aktivnosti1.getId());
        assertThat(aktivnosti1).isEqualTo(aktivnosti2);
        aktivnosti2.setId(2L);
        assertThat(aktivnosti1).isNotEqualTo(aktivnosti2);
        aktivnosti1.setId(null);
        assertThat(aktivnosti1).isNotEqualTo(aktivnosti2);
    }
}
