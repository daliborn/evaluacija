package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.EvaluacijaApp;
import eu.apeiron.dalibor.evaulacija.domain.Nastavnik;
import eu.apeiron.dalibor.evaulacija.repository.NastavnikRepository;
import eu.apeiron.dalibor.evaulacija.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static eu.apeiron.dalibor.evaulacija.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link NastavnikResource} REST controller.
 */
@SpringBootTest(classes = EvaluacijaApp.class)
public class NastavnikResourceIT {

    private static final String DEFAULT_IME = "AAAAAAAAAA";
    private static final String UPDATED_IME = "BBBBBBBBBB";

    private static final String DEFAULT_PREZIME = "AAAAAAAAAA";
    private static final String UPDATED_PREZIME = "BBBBBBBBBB";

    private static final String DEFAULT_TITULA = "AAAAAAAAAA";
    private static final String UPDATED_TITULA = "BBBBBBBBBB";

    private static final byte[] DEFAULT_FOTOGRAFIJA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTOGRAFIJA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTOGRAFIJA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTOGRAFIJA_CONTENT_TYPE = "image/png";

    @Autowired
    private NastavnikRepository nastavnikRepository;

    @Mock
    private NastavnikRepository nastavnikRepositoryMock;

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

    private MockMvc restNastavnikMockMvc;

    private Nastavnik nastavnik;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NastavnikResource nastavnikResource = new NastavnikResource(nastavnikRepository);
        this.restNastavnikMockMvc = MockMvcBuilders.standaloneSetup(nastavnikResource)
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
    public static Nastavnik createEntity(EntityManager em) {
        Nastavnik nastavnik = new Nastavnik()
            .ime(DEFAULT_IME)
            .prezime(DEFAULT_PREZIME)
            .titula(DEFAULT_TITULA)
            .fotografija(DEFAULT_FOTOGRAFIJA)
            .fotografijaContentType(DEFAULT_FOTOGRAFIJA_CONTENT_TYPE);
        return nastavnik;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Nastavnik createUpdatedEntity(EntityManager em) {
        Nastavnik nastavnik = new Nastavnik()
            .ime(UPDATED_IME)
            .prezime(UPDATED_PREZIME)
            .titula(UPDATED_TITULA)
            .fotografija(UPDATED_FOTOGRAFIJA)
            .fotografijaContentType(UPDATED_FOTOGRAFIJA_CONTENT_TYPE);
        return nastavnik;
    }

    @BeforeEach
    public void initTest() {
        nastavnik = createEntity(em);
    }

    @Test
    @Transactional
    public void createNastavnik() throws Exception {
        int databaseSizeBeforeCreate = nastavnikRepository.findAll().size();

        // Create the Nastavnik
        restNastavnikMockMvc.perform(post("/api/nastavniks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nastavnik)))
            .andExpect(status().isCreated());

        // Validate the Nastavnik in the database
        List<Nastavnik> nastavnikList = nastavnikRepository.findAll();
        assertThat(nastavnikList).hasSize(databaseSizeBeforeCreate + 1);
        Nastavnik testNastavnik = nastavnikList.get(nastavnikList.size() - 1);
        assertThat(testNastavnik.getIme()).isEqualTo(DEFAULT_IME);
        assertThat(testNastavnik.getPrezime()).isEqualTo(DEFAULT_PREZIME);
        assertThat(testNastavnik.getTitula()).isEqualTo(DEFAULT_TITULA);
        assertThat(testNastavnik.getFotografija()).isEqualTo(DEFAULT_FOTOGRAFIJA);
        assertThat(testNastavnik.getFotografijaContentType()).isEqualTo(DEFAULT_FOTOGRAFIJA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createNastavnikWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nastavnikRepository.findAll().size();

        // Create the Nastavnik with an existing ID
        nastavnik.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNastavnikMockMvc.perform(post("/api/nastavniks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nastavnik)))
            .andExpect(status().isBadRequest());

        // Validate the Nastavnik in the database
        List<Nastavnik> nastavnikList = nastavnikRepository.findAll();
        assertThat(nastavnikList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkImeIsRequired() throws Exception {
        int databaseSizeBeforeTest = nastavnikRepository.findAll().size();
        // set the field null
        nastavnik.setIme(null);

        // Create the Nastavnik, which fails.

        restNastavnikMockMvc.perform(post("/api/nastavniks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nastavnik)))
            .andExpect(status().isBadRequest());

        List<Nastavnik> nastavnikList = nastavnikRepository.findAll();
        assertThat(nastavnikList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrezimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = nastavnikRepository.findAll().size();
        // set the field null
        nastavnik.setPrezime(null);

        // Create the Nastavnik, which fails.

        restNastavnikMockMvc.perform(post("/api/nastavniks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nastavnik)))
            .andExpect(status().isBadRequest());

        List<Nastavnik> nastavnikList = nastavnikRepository.findAll();
        assertThat(nastavnikList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitulaIsRequired() throws Exception {
        int databaseSizeBeforeTest = nastavnikRepository.findAll().size();
        // set the field null
        nastavnik.setTitula(null);

        // Create the Nastavnik, which fails.

        restNastavnikMockMvc.perform(post("/api/nastavniks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nastavnik)))
            .andExpect(status().isBadRequest());

        List<Nastavnik> nastavnikList = nastavnikRepository.findAll();
        assertThat(nastavnikList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNastavniks() throws Exception {
        // Initialize the database
        nastavnikRepository.saveAndFlush(nastavnik);

        // Get all the nastavnikList
        restNastavnikMockMvc.perform(get("/api/nastavniks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nastavnik.getId().intValue())))
            .andExpect(jsonPath("$.[*].ime").value(hasItem(DEFAULT_IME.toString())))
            .andExpect(jsonPath("$.[*].prezime").value(hasItem(DEFAULT_PREZIME.toString())))
            .andExpect(jsonPath("$.[*].titula").value(hasItem(DEFAULT_TITULA.toString())))
            .andExpect(jsonPath("$.[*].fotografijaContentType").value(hasItem(DEFAULT_FOTOGRAFIJA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].fotografija").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTOGRAFIJA))));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllNastavniksWithEagerRelationshipsIsEnabled() throws Exception {
        NastavnikResource nastavnikResource = new NastavnikResource(nastavnikRepositoryMock);
        when(nastavnikRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restNastavnikMockMvc = MockMvcBuilders.standaloneSetup(nastavnikResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restNastavnikMockMvc.perform(get("/api/nastavniks?eagerload=true"))
        .andExpect(status().isOk());

        verify(nastavnikRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllNastavniksWithEagerRelationshipsIsNotEnabled() throws Exception {
        NastavnikResource nastavnikResource = new NastavnikResource(nastavnikRepositoryMock);
            when(nastavnikRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restNastavnikMockMvc = MockMvcBuilders.standaloneSetup(nastavnikResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restNastavnikMockMvc.perform(get("/api/nastavniks?eagerload=true"))
        .andExpect(status().isOk());

            verify(nastavnikRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getNastavnik() throws Exception {
        // Initialize the database
        nastavnikRepository.saveAndFlush(nastavnik);

        // Get the nastavnik
        restNastavnikMockMvc.perform(get("/api/nastavniks/{id}", nastavnik.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nastavnik.getId().intValue()))
            .andExpect(jsonPath("$.ime").value(DEFAULT_IME.toString()))
            .andExpect(jsonPath("$.prezime").value(DEFAULT_PREZIME.toString()))
            .andExpect(jsonPath("$.titula").value(DEFAULT_TITULA.toString()))
            .andExpect(jsonPath("$.fotografijaContentType").value(DEFAULT_FOTOGRAFIJA_CONTENT_TYPE))
            .andExpect(jsonPath("$.fotografija").value(Base64Utils.encodeToString(DEFAULT_FOTOGRAFIJA)));
    }

    @Test
    @Transactional
    public void getNonExistingNastavnik() throws Exception {
        // Get the nastavnik
        restNastavnikMockMvc.perform(get("/api/nastavniks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNastavnik() throws Exception {
        // Initialize the database
        nastavnikRepository.saveAndFlush(nastavnik);

        int databaseSizeBeforeUpdate = nastavnikRepository.findAll().size();

        // Update the nastavnik
        Nastavnik updatedNastavnik = nastavnikRepository.findById(nastavnik.getId()).get();
        // Disconnect from session so that the updates on updatedNastavnik are not directly saved in db
        em.detach(updatedNastavnik);
        updatedNastavnik
            .ime(UPDATED_IME)
            .prezime(UPDATED_PREZIME)
            .titula(UPDATED_TITULA)
            .fotografija(UPDATED_FOTOGRAFIJA)
            .fotografijaContentType(UPDATED_FOTOGRAFIJA_CONTENT_TYPE);

        restNastavnikMockMvc.perform(put("/api/nastavniks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNastavnik)))
            .andExpect(status().isOk());

        // Validate the Nastavnik in the database
        List<Nastavnik> nastavnikList = nastavnikRepository.findAll();
        assertThat(nastavnikList).hasSize(databaseSizeBeforeUpdate);
        Nastavnik testNastavnik = nastavnikList.get(nastavnikList.size() - 1);
        assertThat(testNastavnik.getIme()).isEqualTo(UPDATED_IME);
        assertThat(testNastavnik.getPrezime()).isEqualTo(UPDATED_PREZIME);
        assertThat(testNastavnik.getTitula()).isEqualTo(UPDATED_TITULA);
        assertThat(testNastavnik.getFotografija()).isEqualTo(UPDATED_FOTOGRAFIJA);
        assertThat(testNastavnik.getFotografijaContentType()).isEqualTo(UPDATED_FOTOGRAFIJA_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingNastavnik() throws Exception {
        int databaseSizeBeforeUpdate = nastavnikRepository.findAll().size();

        // Create the Nastavnik

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNastavnikMockMvc.perform(put("/api/nastavniks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nastavnik)))
            .andExpect(status().isBadRequest());

        // Validate the Nastavnik in the database
        List<Nastavnik> nastavnikList = nastavnikRepository.findAll();
        assertThat(nastavnikList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNastavnik() throws Exception {
        // Initialize the database
        nastavnikRepository.saveAndFlush(nastavnik);

        int databaseSizeBeforeDelete = nastavnikRepository.findAll().size();

        // Delete the nastavnik
        restNastavnikMockMvc.perform(delete("/api/nastavniks/{id}", nastavnik.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Nastavnik> nastavnikList = nastavnikRepository.findAll();
        assertThat(nastavnikList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nastavnik.class);
        Nastavnik nastavnik1 = new Nastavnik();
        nastavnik1.setId(1L);
        Nastavnik nastavnik2 = new Nastavnik();
        nastavnik2.setId(nastavnik1.getId());
        assertThat(nastavnik1).isEqualTo(nastavnik2);
        nastavnik2.setId(2L);
        assertThat(nastavnik1).isNotEqualTo(nastavnik2);
        nastavnik1.setId(null);
        assertThat(nastavnik1).isNotEqualTo(nastavnik2);
    }
}
