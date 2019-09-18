package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.EvaluacijaApp;
import eu.apeiron.dalibor.evaulacija.domain.ZavrsniRad;
import eu.apeiron.dalibor.evaulacija.repository.ZavrsniRadRepository;
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
 * Integration tests for the {@link ZavrsniRadResource} REST controller.
 */
@SpringBootTest(classes = EvaluacijaApp.class)
public class ZavrsniRadResourceIT {

    private static final String DEFAULT_TIP_STUDIJA = "AAAAAAAAAA";
    private static final String UPDATED_TIP_STUDIJA = "BBBBBBBBBB";

    private static final String DEFAULT_MENTOR = "AAAAAAAAAA";
    private static final String UPDATED_MENTOR = "BBBBBBBBBB";

    private static final String DEFAULT_NAZIV = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATUM_ZAVRSETKA_RADA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATUM_ZAVRSETKA_RADA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATUM_ZAVRSETKA_RADA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private ZavrsniRadRepository zavrsniRadRepository;

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

    private MockMvc restZavrsniRadMockMvc;

    private ZavrsniRad zavrsniRad;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ZavrsniRadResource zavrsniRadResource = new ZavrsniRadResource(zavrsniRadRepository);
        this.restZavrsniRadMockMvc = MockMvcBuilders.standaloneSetup(zavrsniRadResource)
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
    public static ZavrsniRad createEntity(EntityManager em) {
        ZavrsniRad zavrsniRad = new ZavrsniRad()
            .tipStudija(DEFAULT_TIP_STUDIJA)
            .mentor(DEFAULT_MENTOR)
            .naziv(DEFAULT_NAZIV)
            .datumZavrsetkaRada(DEFAULT_DATUM_ZAVRSETKA_RADA);
        return zavrsniRad;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ZavrsniRad createUpdatedEntity(EntityManager em) {
        ZavrsniRad zavrsniRad = new ZavrsniRad()
            .tipStudija(UPDATED_TIP_STUDIJA)
            .mentor(UPDATED_MENTOR)
            .naziv(UPDATED_NAZIV)
            .datumZavrsetkaRada(UPDATED_DATUM_ZAVRSETKA_RADA);
        return zavrsniRad;
    }

    @BeforeEach
    public void initTest() {
        zavrsniRad = createEntity(em);
    }

    @Test
    @Transactional
    public void createZavrsniRad() throws Exception {
        int databaseSizeBeforeCreate = zavrsniRadRepository.findAll().size();

        // Create the ZavrsniRad
        restZavrsniRadMockMvc.perform(post("/api/zavrsni-rads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zavrsniRad)))
            .andExpect(status().isCreated());

        // Validate the ZavrsniRad in the database
        List<ZavrsniRad> zavrsniRadList = zavrsniRadRepository.findAll();
        assertThat(zavrsniRadList).hasSize(databaseSizeBeforeCreate + 1);
        ZavrsniRad testZavrsniRad = zavrsniRadList.get(zavrsniRadList.size() - 1);
        assertThat(testZavrsniRad.getTipStudija()).isEqualTo(DEFAULT_TIP_STUDIJA);
        assertThat(testZavrsniRad.getMentor()).isEqualTo(DEFAULT_MENTOR);
        assertThat(testZavrsniRad.getNaziv()).isEqualTo(DEFAULT_NAZIV);
        assertThat(testZavrsniRad.getDatumZavrsetkaRada()).isEqualTo(DEFAULT_DATUM_ZAVRSETKA_RADA);
    }

    @Test
    @Transactional
    public void createZavrsniRadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zavrsniRadRepository.findAll().size();

        // Create the ZavrsniRad with an existing ID
        zavrsniRad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZavrsniRadMockMvc.perform(post("/api/zavrsni-rads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zavrsniRad)))
            .andExpect(status().isBadRequest());

        // Validate the ZavrsniRad in the database
        List<ZavrsniRad> zavrsniRadList = zavrsniRadRepository.findAll();
        assertThat(zavrsniRadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTipStudijaIsRequired() throws Exception {
        int databaseSizeBeforeTest = zavrsniRadRepository.findAll().size();
        // set the field null
        zavrsniRad.setTipStudija(null);

        // Create the ZavrsniRad, which fails.

        restZavrsniRadMockMvc.perform(post("/api/zavrsni-rads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zavrsniRad)))
            .andExpect(status().isBadRequest());

        List<ZavrsniRad> zavrsniRadList = zavrsniRadRepository.findAll();
        assertThat(zavrsniRadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNazivIsRequired() throws Exception {
        int databaseSizeBeforeTest = zavrsniRadRepository.findAll().size();
        // set the field null
        zavrsniRad.setNaziv(null);

        // Create the ZavrsniRad, which fails.

        restZavrsniRadMockMvc.perform(post("/api/zavrsni-rads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zavrsniRad)))
            .andExpect(status().isBadRequest());

        List<ZavrsniRad> zavrsniRadList = zavrsniRadRepository.findAll();
        assertThat(zavrsniRadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllZavrsniRads() throws Exception {
        // Initialize the database
        zavrsniRadRepository.saveAndFlush(zavrsniRad);

        // Get all the zavrsniRadList
        restZavrsniRadMockMvc.perform(get("/api/zavrsni-rads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zavrsniRad.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipStudija").value(hasItem(DEFAULT_TIP_STUDIJA.toString())))
            .andExpect(jsonPath("$.[*].mentor").value(hasItem(DEFAULT_MENTOR.toString())))
            .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV.toString())))
            .andExpect(jsonPath("$.[*].datumZavrsetkaRada").value(hasItem(sameInstant(DEFAULT_DATUM_ZAVRSETKA_RADA))));
    }
    
    @Test
    @Transactional
    public void getZavrsniRad() throws Exception {
        // Initialize the database
        zavrsniRadRepository.saveAndFlush(zavrsniRad);

        // Get the zavrsniRad
        restZavrsniRadMockMvc.perform(get("/api/zavrsni-rads/{id}", zavrsniRad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(zavrsniRad.getId().intValue()))
            .andExpect(jsonPath("$.tipStudija").value(DEFAULT_TIP_STUDIJA.toString()))
            .andExpect(jsonPath("$.mentor").value(DEFAULT_MENTOR.toString()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV.toString()))
            .andExpect(jsonPath("$.datumZavrsetkaRada").value(sameInstant(DEFAULT_DATUM_ZAVRSETKA_RADA)));
    }

    @Test
    @Transactional
    public void getNonExistingZavrsniRad() throws Exception {
        // Get the zavrsniRad
        restZavrsniRadMockMvc.perform(get("/api/zavrsni-rads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZavrsniRad() throws Exception {
        // Initialize the database
        zavrsniRadRepository.saveAndFlush(zavrsniRad);

        int databaseSizeBeforeUpdate = zavrsniRadRepository.findAll().size();

        // Update the zavrsniRad
        ZavrsniRad updatedZavrsniRad = zavrsniRadRepository.findById(zavrsniRad.getId()).get();
        // Disconnect from session so that the updates on updatedZavrsniRad are not directly saved in db
        em.detach(updatedZavrsniRad);
        updatedZavrsniRad
            .tipStudija(UPDATED_TIP_STUDIJA)
            .mentor(UPDATED_MENTOR)
            .naziv(UPDATED_NAZIV)
            .datumZavrsetkaRada(UPDATED_DATUM_ZAVRSETKA_RADA);

        restZavrsniRadMockMvc.perform(put("/api/zavrsni-rads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedZavrsniRad)))
            .andExpect(status().isOk());

        // Validate the ZavrsniRad in the database
        List<ZavrsniRad> zavrsniRadList = zavrsniRadRepository.findAll();
        assertThat(zavrsniRadList).hasSize(databaseSizeBeforeUpdate);
        ZavrsniRad testZavrsniRad = zavrsniRadList.get(zavrsniRadList.size() - 1);
        assertThat(testZavrsniRad.getTipStudija()).isEqualTo(UPDATED_TIP_STUDIJA);
        assertThat(testZavrsniRad.getMentor()).isEqualTo(UPDATED_MENTOR);
        assertThat(testZavrsniRad.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testZavrsniRad.getDatumZavrsetkaRada()).isEqualTo(UPDATED_DATUM_ZAVRSETKA_RADA);
    }

    @Test
    @Transactional
    public void updateNonExistingZavrsniRad() throws Exception {
        int databaseSizeBeforeUpdate = zavrsniRadRepository.findAll().size();

        // Create the ZavrsniRad

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZavrsniRadMockMvc.perform(put("/api/zavrsni-rads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zavrsniRad)))
            .andExpect(status().isBadRequest());

        // Validate the ZavrsniRad in the database
        List<ZavrsniRad> zavrsniRadList = zavrsniRadRepository.findAll();
        assertThat(zavrsniRadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteZavrsniRad() throws Exception {
        // Initialize the database
        zavrsniRadRepository.saveAndFlush(zavrsniRad);

        int databaseSizeBeforeDelete = zavrsniRadRepository.findAll().size();

        // Delete the zavrsniRad
        restZavrsniRadMockMvc.perform(delete("/api/zavrsni-rads/{id}", zavrsniRad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ZavrsniRad> zavrsniRadList = zavrsniRadRepository.findAll();
        assertThat(zavrsniRadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZavrsniRad.class);
        ZavrsniRad zavrsniRad1 = new ZavrsniRad();
        zavrsniRad1.setId(1L);
        ZavrsniRad zavrsniRad2 = new ZavrsniRad();
        zavrsniRad2.setId(zavrsniRad1.getId());
        assertThat(zavrsniRad1).isEqualTo(zavrsniRad2);
        zavrsniRad2.setId(2L);
        assertThat(zavrsniRad1).isNotEqualTo(zavrsniRad2);
        zavrsniRad1.setId(null);
        assertThat(zavrsniRad1).isNotEqualTo(zavrsniRad2);
    }
}
