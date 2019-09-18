package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.EvaluacijaApp;
import eu.apeiron.dalibor.evaulacija.domain.Kandidat;
import eu.apeiron.dalibor.evaulacija.repository.KandidatRepository;
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
 * Integration tests for the {@link KandidatResource} REST controller.
 */
@SpringBootTest(classes = EvaluacijaApp.class)
public class KandidatResourceIT {

    private static final String DEFAULT_IME = "AAAAAAAAAA";
    private static final String UPDATED_IME = "BBBBBBBBBB";

    private static final String DEFAULT_PREZIME = "AAAAAAAAAA";
    private static final String UPDATED_PREZIME = "BBBBBBBBBB";

    private static final String DEFAULT_JMBG = "AAAAAAAAAA";
    private static final String UPDATED_JMBG = "BBBBBBBBBB";

    @Autowired
    private KandidatRepository kandidatRepository;

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

    private MockMvc restKandidatMockMvc;

    private Kandidat kandidat;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final KandidatResource kandidatResource = new KandidatResource(kandidatRepository);
        this.restKandidatMockMvc = MockMvcBuilders.standaloneSetup(kandidatResource)
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
    public static Kandidat createEntity(EntityManager em) {
        Kandidat kandidat = new Kandidat()
            .ime(DEFAULT_IME)
            .prezime(DEFAULT_PREZIME)
            .jmbg(DEFAULT_JMBG);
        return kandidat;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Kandidat createUpdatedEntity(EntityManager em) {
        Kandidat kandidat = new Kandidat()
            .ime(UPDATED_IME)
            .prezime(UPDATED_PREZIME)
            .jmbg(UPDATED_JMBG);
        return kandidat;
    }

    @BeforeEach
    public void initTest() {
        kandidat = createEntity(em);
    }

    @Test
    @Transactional
    public void createKandidat() throws Exception {
        int databaseSizeBeforeCreate = kandidatRepository.findAll().size();

        // Create the Kandidat
        restKandidatMockMvc.perform(post("/api/kandidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kandidat)))
            .andExpect(status().isCreated());

        // Validate the Kandidat in the database
        List<Kandidat> kandidatList = kandidatRepository.findAll();
        assertThat(kandidatList).hasSize(databaseSizeBeforeCreate + 1);
        Kandidat testKandidat = kandidatList.get(kandidatList.size() - 1);
        assertThat(testKandidat.getIme()).isEqualTo(DEFAULT_IME);
        assertThat(testKandidat.getPrezime()).isEqualTo(DEFAULT_PREZIME);
        assertThat(testKandidat.getJmbg()).isEqualTo(DEFAULT_JMBG);
    }

    @Test
    @Transactional
    public void createKandidatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = kandidatRepository.findAll().size();

        // Create the Kandidat with an existing ID
        kandidat.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restKandidatMockMvc.perform(post("/api/kandidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kandidat)))
            .andExpect(status().isBadRequest());

        // Validate the Kandidat in the database
        List<Kandidat> kandidatList = kandidatRepository.findAll();
        assertThat(kandidatList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkImeIsRequired() throws Exception {
        int databaseSizeBeforeTest = kandidatRepository.findAll().size();
        // set the field null
        kandidat.setIme(null);

        // Create the Kandidat, which fails.

        restKandidatMockMvc.perform(post("/api/kandidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kandidat)))
            .andExpect(status().isBadRequest());

        List<Kandidat> kandidatList = kandidatRepository.findAll();
        assertThat(kandidatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrezimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = kandidatRepository.findAll().size();
        // set the field null
        kandidat.setPrezime(null);

        // Create the Kandidat, which fails.

        restKandidatMockMvc.perform(post("/api/kandidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kandidat)))
            .andExpect(status().isBadRequest());

        List<Kandidat> kandidatList = kandidatRepository.findAll();
        assertThat(kandidatList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllKandidats() throws Exception {
        // Initialize the database
        kandidatRepository.saveAndFlush(kandidat);

        // Get all the kandidatList
        restKandidatMockMvc.perform(get("/api/kandidats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(kandidat.getId().intValue())))
            .andExpect(jsonPath("$.[*].ime").value(hasItem(DEFAULT_IME.toString())))
            .andExpect(jsonPath("$.[*].prezime").value(hasItem(DEFAULT_PREZIME.toString())))
            .andExpect(jsonPath("$.[*].jmbg").value(hasItem(DEFAULT_JMBG.toString())));
    }
    
    @Test
    @Transactional
    public void getKandidat() throws Exception {
        // Initialize the database
        kandidatRepository.saveAndFlush(kandidat);

        // Get the kandidat
        restKandidatMockMvc.perform(get("/api/kandidats/{id}", kandidat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(kandidat.getId().intValue()))
            .andExpect(jsonPath("$.ime").value(DEFAULT_IME.toString()))
            .andExpect(jsonPath("$.prezime").value(DEFAULT_PREZIME.toString()))
            .andExpect(jsonPath("$.jmbg").value(DEFAULT_JMBG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingKandidat() throws Exception {
        // Get the kandidat
        restKandidatMockMvc.perform(get("/api/kandidats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateKandidat() throws Exception {
        // Initialize the database
        kandidatRepository.saveAndFlush(kandidat);

        int databaseSizeBeforeUpdate = kandidatRepository.findAll().size();

        // Update the kandidat
        Kandidat updatedKandidat = kandidatRepository.findById(kandidat.getId()).get();
        // Disconnect from session so that the updates on updatedKandidat are not directly saved in db
        em.detach(updatedKandidat);
        updatedKandidat
            .ime(UPDATED_IME)
            .prezime(UPDATED_PREZIME)
            .jmbg(UPDATED_JMBG);

        restKandidatMockMvc.perform(put("/api/kandidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedKandidat)))
            .andExpect(status().isOk());

        // Validate the Kandidat in the database
        List<Kandidat> kandidatList = kandidatRepository.findAll();
        assertThat(kandidatList).hasSize(databaseSizeBeforeUpdate);
        Kandidat testKandidat = kandidatList.get(kandidatList.size() - 1);
        assertThat(testKandidat.getIme()).isEqualTo(UPDATED_IME);
        assertThat(testKandidat.getPrezime()).isEqualTo(UPDATED_PREZIME);
        assertThat(testKandidat.getJmbg()).isEqualTo(UPDATED_JMBG);
    }

    @Test
    @Transactional
    public void updateNonExistingKandidat() throws Exception {
        int databaseSizeBeforeUpdate = kandidatRepository.findAll().size();

        // Create the Kandidat

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restKandidatMockMvc.perform(put("/api/kandidats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(kandidat)))
            .andExpect(status().isBadRequest());

        // Validate the Kandidat in the database
        List<Kandidat> kandidatList = kandidatRepository.findAll();
        assertThat(kandidatList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteKandidat() throws Exception {
        // Initialize the database
        kandidatRepository.saveAndFlush(kandidat);

        int databaseSizeBeforeDelete = kandidatRepository.findAll().size();

        // Delete the kandidat
        restKandidatMockMvc.perform(delete("/api/kandidats/{id}", kandidat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Kandidat> kandidatList = kandidatRepository.findAll();
        assertThat(kandidatList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kandidat.class);
        Kandidat kandidat1 = new Kandidat();
        kandidat1.setId(1L);
        Kandidat kandidat2 = new Kandidat();
        kandidat2.setId(kandidat1.getId());
        assertThat(kandidat1).isEqualTo(kandidat2);
        kandidat2.setId(2L);
        assertThat(kandidat1).isNotEqualTo(kandidat2);
        kandidat1.setId(null);
        assertThat(kandidat1).isNotEqualTo(kandidat2);
    }
}
