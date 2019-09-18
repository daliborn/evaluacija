package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.EvaluacijaApp;
import eu.apeiron.dalibor.evaulacija.domain.StrucnoUsavrsavanje;
import eu.apeiron.dalibor.evaulacija.repository.StrucnoUsavrsavanjeRepository;
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
 * Integration tests for the {@link StrucnoUsavrsavanjeResource} REST controller.
 */
@SpringBootTest(classes = EvaluacijaApp.class)
public class StrucnoUsavrsavanjeResourceIT {

    private static final String DEFAULT_NAZIV = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_POCETAK_USAVRSAVANJA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_POCETAK_USAVRSAVANJA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_POCETAK_USAVRSAVANJA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_KRAJ_USAVRSAVANJA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_KRAJ_USAVRSAVANJA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_KRAJ_USAVRSAVANJA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private StrucnoUsavrsavanjeRepository strucnoUsavrsavanjeRepository;

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

    private MockMvc restStrucnoUsavrsavanjeMockMvc;

    private StrucnoUsavrsavanje strucnoUsavrsavanje;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StrucnoUsavrsavanjeResource strucnoUsavrsavanjeResource = new StrucnoUsavrsavanjeResource(strucnoUsavrsavanjeRepository);
        this.restStrucnoUsavrsavanjeMockMvc = MockMvcBuilders.standaloneSetup(strucnoUsavrsavanjeResource)
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
    public static StrucnoUsavrsavanje createEntity(EntityManager em) {
        StrucnoUsavrsavanje strucnoUsavrsavanje = new StrucnoUsavrsavanje()
            .naziv(DEFAULT_NAZIV)
            .pocetakUsavrsavanja(DEFAULT_POCETAK_USAVRSAVANJA)
            .krajUsavrsavanja(DEFAULT_KRAJ_USAVRSAVANJA);
        return strucnoUsavrsavanje;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StrucnoUsavrsavanje createUpdatedEntity(EntityManager em) {
        StrucnoUsavrsavanje strucnoUsavrsavanje = new StrucnoUsavrsavanje()
            .naziv(UPDATED_NAZIV)
            .pocetakUsavrsavanja(UPDATED_POCETAK_USAVRSAVANJA)
            .krajUsavrsavanja(UPDATED_KRAJ_USAVRSAVANJA);
        return strucnoUsavrsavanje;
    }

    @BeforeEach
    public void initTest() {
        strucnoUsavrsavanje = createEntity(em);
    }

    @Test
    @Transactional
    public void createStrucnoUsavrsavanje() throws Exception {
        int databaseSizeBeforeCreate = strucnoUsavrsavanjeRepository.findAll().size();

        // Create the StrucnoUsavrsavanje
        restStrucnoUsavrsavanjeMockMvc.perform(post("/api/strucno-usavrsavanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(strucnoUsavrsavanje)))
            .andExpect(status().isCreated());

        // Validate the StrucnoUsavrsavanje in the database
        List<StrucnoUsavrsavanje> strucnoUsavrsavanjeList = strucnoUsavrsavanjeRepository.findAll();
        assertThat(strucnoUsavrsavanjeList).hasSize(databaseSizeBeforeCreate + 1);
        StrucnoUsavrsavanje testStrucnoUsavrsavanje = strucnoUsavrsavanjeList.get(strucnoUsavrsavanjeList.size() - 1);
        assertThat(testStrucnoUsavrsavanje.getNaziv()).isEqualTo(DEFAULT_NAZIV);
        assertThat(testStrucnoUsavrsavanje.getPocetakUsavrsavanja()).isEqualTo(DEFAULT_POCETAK_USAVRSAVANJA);
        assertThat(testStrucnoUsavrsavanje.getKrajUsavrsavanja()).isEqualTo(DEFAULT_KRAJ_USAVRSAVANJA);
    }

    @Test
    @Transactional
    public void createStrucnoUsavrsavanjeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = strucnoUsavrsavanjeRepository.findAll().size();

        // Create the StrucnoUsavrsavanje with an existing ID
        strucnoUsavrsavanje.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStrucnoUsavrsavanjeMockMvc.perform(post("/api/strucno-usavrsavanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(strucnoUsavrsavanje)))
            .andExpect(status().isBadRequest());

        // Validate the StrucnoUsavrsavanje in the database
        List<StrucnoUsavrsavanje> strucnoUsavrsavanjeList = strucnoUsavrsavanjeRepository.findAll();
        assertThat(strucnoUsavrsavanjeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNazivIsRequired() throws Exception {
        int databaseSizeBeforeTest = strucnoUsavrsavanjeRepository.findAll().size();
        // set the field null
        strucnoUsavrsavanje.setNaziv(null);

        // Create the StrucnoUsavrsavanje, which fails.

        restStrucnoUsavrsavanjeMockMvc.perform(post("/api/strucno-usavrsavanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(strucnoUsavrsavanje)))
            .andExpect(status().isBadRequest());

        List<StrucnoUsavrsavanje> strucnoUsavrsavanjeList = strucnoUsavrsavanjeRepository.findAll();
        assertThat(strucnoUsavrsavanjeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStrucnoUsavrsavanjes() throws Exception {
        // Initialize the database
        strucnoUsavrsavanjeRepository.saveAndFlush(strucnoUsavrsavanje);

        // Get all the strucnoUsavrsavanjeList
        restStrucnoUsavrsavanjeMockMvc.perform(get("/api/strucno-usavrsavanjes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(strucnoUsavrsavanje.getId().intValue())))
            .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV.toString())))
            .andExpect(jsonPath("$.[*].pocetakUsavrsavanja").value(hasItem(sameInstant(DEFAULT_POCETAK_USAVRSAVANJA))))
            .andExpect(jsonPath("$.[*].krajUsavrsavanja").value(hasItem(sameInstant(DEFAULT_KRAJ_USAVRSAVANJA))));
    }
    
    @Test
    @Transactional
    public void getStrucnoUsavrsavanje() throws Exception {
        // Initialize the database
        strucnoUsavrsavanjeRepository.saveAndFlush(strucnoUsavrsavanje);

        // Get the strucnoUsavrsavanje
        restStrucnoUsavrsavanjeMockMvc.perform(get("/api/strucno-usavrsavanjes/{id}", strucnoUsavrsavanje.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(strucnoUsavrsavanje.getId().intValue()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV.toString()))
            .andExpect(jsonPath("$.pocetakUsavrsavanja").value(sameInstant(DEFAULT_POCETAK_USAVRSAVANJA)))
            .andExpect(jsonPath("$.krajUsavrsavanja").value(sameInstant(DEFAULT_KRAJ_USAVRSAVANJA)));
    }

    @Test
    @Transactional
    public void getNonExistingStrucnoUsavrsavanje() throws Exception {
        // Get the strucnoUsavrsavanje
        restStrucnoUsavrsavanjeMockMvc.perform(get("/api/strucno-usavrsavanjes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStrucnoUsavrsavanje() throws Exception {
        // Initialize the database
        strucnoUsavrsavanjeRepository.saveAndFlush(strucnoUsavrsavanje);

        int databaseSizeBeforeUpdate = strucnoUsavrsavanjeRepository.findAll().size();

        // Update the strucnoUsavrsavanje
        StrucnoUsavrsavanje updatedStrucnoUsavrsavanje = strucnoUsavrsavanjeRepository.findById(strucnoUsavrsavanje.getId()).get();
        // Disconnect from session so that the updates on updatedStrucnoUsavrsavanje are not directly saved in db
        em.detach(updatedStrucnoUsavrsavanje);
        updatedStrucnoUsavrsavanje
            .naziv(UPDATED_NAZIV)
            .pocetakUsavrsavanja(UPDATED_POCETAK_USAVRSAVANJA)
            .krajUsavrsavanja(UPDATED_KRAJ_USAVRSAVANJA);

        restStrucnoUsavrsavanjeMockMvc.perform(put("/api/strucno-usavrsavanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStrucnoUsavrsavanje)))
            .andExpect(status().isOk());

        // Validate the StrucnoUsavrsavanje in the database
        List<StrucnoUsavrsavanje> strucnoUsavrsavanjeList = strucnoUsavrsavanjeRepository.findAll();
        assertThat(strucnoUsavrsavanjeList).hasSize(databaseSizeBeforeUpdate);
        StrucnoUsavrsavanje testStrucnoUsavrsavanje = strucnoUsavrsavanjeList.get(strucnoUsavrsavanjeList.size() - 1);
        assertThat(testStrucnoUsavrsavanje.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testStrucnoUsavrsavanje.getPocetakUsavrsavanja()).isEqualTo(UPDATED_POCETAK_USAVRSAVANJA);
        assertThat(testStrucnoUsavrsavanje.getKrajUsavrsavanja()).isEqualTo(UPDATED_KRAJ_USAVRSAVANJA);
    }

    @Test
    @Transactional
    public void updateNonExistingStrucnoUsavrsavanje() throws Exception {
        int databaseSizeBeforeUpdate = strucnoUsavrsavanjeRepository.findAll().size();

        // Create the StrucnoUsavrsavanje

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStrucnoUsavrsavanjeMockMvc.perform(put("/api/strucno-usavrsavanjes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(strucnoUsavrsavanje)))
            .andExpect(status().isBadRequest());

        // Validate the StrucnoUsavrsavanje in the database
        List<StrucnoUsavrsavanje> strucnoUsavrsavanjeList = strucnoUsavrsavanjeRepository.findAll();
        assertThat(strucnoUsavrsavanjeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStrucnoUsavrsavanje() throws Exception {
        // Initialize the database
        strucnoUsavrsavanjeRepository.saveAndFlush(strucnoUsavrsavanje);

        int databaseSizeBeforeDelete = strucnoUsavrsavanjeRepository.findAll().size();

        // Delete the strucnoUsavrsavanje
        restStrucnoUsavrsavanjeMockMvc.perform(delete("/api/strucno-usavrsavanjes/{id}", strucnoUsavrsavanje.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StrucnoUsavrsavanje> strucnoUsavrsavanjeList = strucnoUsavrsavanjeRepository.findAll();
        assertThat(strucnoUsavrsavanjeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StrucnoUsavrsavanje.class);
        StrucnoUsavrsavanje strucnoUsavrsavanje1 = new StrucnoUsavrsavanje();
        strucnoUsavrsavanje1.setId(1L);
        StrucnoUsavrsavanje strucnoUsavrsavanje2 = new StrucnoUsavrsavanje();
        strucnoUsavrsavanje2.setId(strucnoUsavrsavanje1.getId());
        assertThat(strucnoUsavrsavanje1).isEqualTo(strucnoUsavrsavanje2);
        strucnoUsavrsavanje2.setId(2L);
        assertThat(strucnoUsavrsavanje1).isNotEqualTo(strucnoUsavrsavanje2);
        strucnoUsavrsavanje1.setId(null);
        assertThat(strucnoUsavrsavanje1).isNotEqualTo(strucnoUsavrsavanje2);
    }
}
