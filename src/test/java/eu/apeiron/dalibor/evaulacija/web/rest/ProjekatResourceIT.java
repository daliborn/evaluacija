package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.EvaluacijaApp;
import eu.apeiron.dalibor.evaulacija.domain.Projekat;
import eu.apeiron.dalibor.evaulacija.repository.ProjekatRepository;
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
 * Integration tests for the {@link ProjekatResource} REST controller.
 */
@SpringBootTest(classes = EvaluacijaApp.class)
public class ProjekatResourceIT {

    private static final String DEFAULT_NAZIV = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV = "BBBBBBBBBB";

    private static final String DEFAULT_VRSTA_PROJEKTA = "AAAAAAAAAA";
    private static final String UPDATED_VRSTA_PROJEKTA = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATUM_ZAVRSETKA_PROJEKTA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATUM_ZAVRSETKA_PROJEKTA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATUM_ZAVRSETKA_PROJEKTA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DATUM_POCETKA_PROJEKTA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATUM_POCETKA_PROJEKTA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATUM_POCETKA_PROJEKTA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private ProjekatRepository projekatRepository;

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

    private MockMvc restProjekatMockMvc;

    private Projekat projekat;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProjekatResource projekatResource = new ProjekatResource(projekatRepository);
        this.restProjekatMockMvc = MockMvcBuilders.standaloneSetup(projekatResource)
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
    public static Projekat createEntity(EntityManager em) {
        Projekat projekat = new Projekat()
            .naziv(DEFAULT_NAZIV)
            .vrstaProjekta(DEFAULT_VRSTA_PROJEKTA)
            .datumZavrsetkaProjekta(DEFAULT_DATUM_ZAVRSETKA_PROJEKTA)
            .datumPocetkaProjekta(DEFAULT_DATUM_POCETKA_PROJEKTA);
        return projekat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Projekat createUpdatedEntity(EntityManager em) {
        Projekat projekat = new Projekat()
            .naziv(UPDATED_NAZIV)
            .vrstaProjekta(UPDATED_VRSTA_PROJEKTA)
            .datumZavrsetkaProjekta(UPDATED_DATUM_ZAVRSETKA_PROJEKTA)
            .datumPocetkaProjekta(UPDATED_DATUM_POCETKA_PROJEKTA);
        return projekat;
    }

    @BeforeEach
    public void initTest() {
        projekat = createEntity(em);
    }

    @Test
    @Transactional
    public void createProjekat() throws Exception {
        int databaseSizeBeforeCreate = projekatRepository.findAll().size();

        // Create the Projekat
        restProjekatMockMvc.perform(post("/api/projekats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projekat)))
            .andExpect(status().isCreated());

        // Validate the Projekat in the database
        List<Projekat> projekatList = projekatRepository.findAll();
        assertThat(projekatList).hasSize(databaseSizeBeforeCreate + 1);
        Projekat testProjekat = projekatList.get(projekatList.size() - 1);
        assertThat(testProjekat.getNaziv()).isEqualTo(DEFAULT_NAZIV);
        assertThat(testProjekat.getVrstaProjekta()).isEqualTo(DEFAULT_VRSTA_PROJEKTA);
        assertThat(testProjekat.getDatumZavrsetkaProjekta()).isEqualTo(DEFAULT_DATUM_ZAVRSETKA_PROJEKTA);
        assertThat(testProjekat.getDatumPocetkaProjekta()).isEqualTo(DEFAULT_DATUM_POCETKA_PROJEKTA);
    }

    @Test
    @Transactional
    public void createProjekatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = projekatRepository.findAll().size();

        // Create the Projekat with an existing ID
        projekat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjekatMockMvc.perform(post("/api/projekats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projekat)))
            .andExpect(status().isBadRequest());

        // Validate the Projekat in the database
        List<Projekat> projekatList = projekatRepository.findAll();
        assertThat(projekatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNazivIsRequired() throws Exception {
        int databaseSizeBeforeTest = projekatRepository.findAll().size();
        // set the field null
        projekat.setNaziv(null);

        // Create the Projekat, which fails.

        restProjekatMockMvc.perform(post("/api/projekats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projekat)))
            .andExpect(status().isBadRequest());

        List<Projekat> projekatList = projekatRepository.findAll();
        assertThat(projekatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVrstaProjektaIsRequired() throws Exception {
        int databaseSizeBeforeTest = projekatRepository.findAll().size();
        // set the field null
        projekat.setVrstaProjekta(null);

        // Create the Projekat, which fails.

        restProjekatMockMvc.perform(post("/api/projekats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projekat)))
            .andExpect(status().isBadRequest());

        List<Projekat> projekatList = projekatRepository.findAll();
        assertThat(projekatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProjekats() throws Exception {
        // Initialize the database
        projekatRepository.saveAndFlush(projekat);

        // Get all the projekatList
        restProjekatMockMvc.perform(get("/api/projekats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projekat.getId().intValue())))
            .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV.toString())))
            .andExpect(jsonPath("$.[*].vrstaProjekta").value(hasItem(DEFAULT_VRSTA_PROJEKTA.toString())))
            .andExpect(jsonPath("$.[*].datumZavrsetkaProjekta").value(hasItem(sameInstant(DEFAULT_DATUM_ZAVRSETKA_PROJEKTA))))
            .andExpect(jsonPath("$.[*].datumPocetkaProjekta").value(hasItem(sameInstant(DEFAULT_DATUM_POCETKA_PROJEKTA))));
    }
    
    @Test
    @Transactional
    public void getProjekat() throws Exception {
        // Initialize the database
        projekatRepository.saveAndFlush(projekat);

        // Get the projekat
        restProjekatMockMvc.perform(get("/api/projekats/{id}", projekat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(projekat.getId().intValue()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV.toString()))
            .andExpect(jsonPath("$.vrstaProjekta").value(DEFAULT_VRSTA_PROJEKTA.toString()))
            .andExpect(jsonPath("$.datumZavrsetkaProjekta").value(sameInstant(DEFAULT_DATUM_ZAVRSETKA_PROJEKTA)))
            .andExpect(jsonPath("$.datumPocetkaProjekta").value(sameInstant(DEFAULT_DATUM_POCETKA_PROJEKTA)));
    }

    @Test
    @Transactional
    public void getNonExistingProjekat() throws Exception {
        // Get the projekat
        restProjekatMockMvc.perform(get("/api/projekats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProjekat() throws Exception {
        // Initialize the database
        projekatRepository.saveAndFlush(projekat);

        int databaseSizeBeforeUpdate = projekatRepository.findAll().size();

        // Update the projekat
        Projekat updatedProjekat = projekatRepository.findById(projekat.getId()).get();
        // Disconnect from session so that the updates on updatedProjekat are not directly saved in db
        em.detach(updatedProjekat);
        updatedProjekat
            .naziv(UPDATED_NAZIV)
            .vrstaProjekta(UPDATED_VRSTA_PROJEKTA)
            .datumZavrsetkaProjekta(UPDATED_DATUM_ZAVRSETKA_PROJEKTA)
            .datumPocetkaProjekta(UPDATED_DATUM_POCETKA_PROJEKTA);

        restProjekatMockMvc.perform(put("/api/projekats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProjekat)))
            .andExpect(status().isOk());

        // Validate the Projekat in the database
        List<Projekat> projekatList = projekatRepository.findAll();
        assertThat(projekatList).hasSize(databaseSizeBeforeUpdate);
        Projekat testProjekat = projekatList.get(projekatList.size() - 1);
        assertThat(testProjekat.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testProjekat.getVrstaProjekta()).isEqualTo(UPDATED_VRSTA_PROJEKTA);
        assertThat(testProjekat.getDatumZavrsetkaProjekta()).isEqualTo(UPDATED_DATUM_ZAVRSETKA_PROJEKTA);
        assertThat(testProjekat.getDatumPocetkaProjekta()).isEqualTo(UPDATED_DATUM_POCETKA_PROJEKTA);
    }

    @Test
    @Transactional
    public void updateNonExistingProjekat() throws Exception {
        int databaseSizeBeforeUpdate = projekatRepository.findAll().size();

        // Create the Projekat

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjekatMockMvc.perform(put("/api/projekats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(projekat)))
            .andExpect(status().isBadRequest());

        // Validate the Projekat in the database
        List<Projekat> projekatList = projekatRepository.findAll();
        assertThat(projekatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProjekat() throws Exception {
        // Initialize the database
        projekatRepository.saveAndFlush(projekat);

        int databaseSizeBeforeDelete = projekatRepository.findAll().size();

        // Delete the projekat
        restProjekatMockMvc.perform(delete("/api/projekats/{id}", projekat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Projekat> projekatList = projekatRepository.findAll();
        assertThat(projekatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Projekat.class);
        Projekat projekat1 = new Projekat();
        projekat1.setId(1L);
        Projekat projekat2 = new Projekat();
        projekat2.setId(projekat1.getId());
        assertThat(projekat1).isEqualTo(projekat2);
        projekat2.setId(2L);
        assertThat(projekat1).isNotEqualTo(projekat2);
        projekat1.setId(null);
        assertThat(projekat1).isNotEqualTo(projekat2);
    }
}
