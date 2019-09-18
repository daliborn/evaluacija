package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.EvaluacijaApp;
import eu.apeiron.dalibor.evaulacija.domain.Institucija;
import eu.apeiron.dalibor.evaulacija.repository.InstitucijaRepository;
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
 * Integration tests for the {@link InstitucijaResource} REST controller.
 */
@SpringBootTest(classes = EvaluacijaApp.class)
public class InstitucijaResourceIT {

    private static final String DEFAULT_SIFRA = "AAAAAAAAAA";
    private static final String UPDATED_SIFRA = "BBBBBBBBBB";

    private static final String DEFAULT_NAZIV = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV = "BBBBBBBBBB";

    private static final String DEFAULT_MJESTO = "AAAAAAAAAA";
    private static final String UPDATED_MJESTO = "BBBBBBBBBB";

    @Autowired
    private InstitucijaRepository institucijaRepository;

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

    private MockMvc restInstitucijaMockMvc;

    private Institucija institucija;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InstitucijaResource institucijaResource = new InstitucijaResource(institucijaRepository);
        this.restInstitucijaMockMvc = MockMvcBuilders.standaloneSetup(institucijaResource)
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
    public static Institucija createEntity(EntityManager em) {
        Institucija institucija = new Institucija()
            .sifra(DEFAULT_SIFRA)
            .naziv(DEFAULT_NAZIV)
            .mjesto(DEFAULT_MJESTO);
        return institucija;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Institucija createUpdatedEntity(EntityManager em) {
        Institucija institucija = new Institucija()
            .sifra(UPDATED_SIFRA)
            .naziv(UPDATED_NAZIV)
            .mjesto(UPDATED_MJESTO);
        return institucija;
    }

    @BeforeEach
    public void initTest() {
        institucija = createEntity(em);
    }

    @Test
    @Transactional
    public void createInstitucija() throws Exception {
        int databaseSizeBeforeCreate = institucijaRepository.findAll().size();

        // Create the Institucija
        restInstitucijaMockMvc.perform(post("/api/institucijas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(institucija)))
            .andExpect(status().isCreated());

        // Validate the Institucija in the database
        List<Institucija> institucijaList = institucijaRepository.findAll();
        assertThat(institucijaList).hasSize(databaseSizeBeforeCreate + 1);
        Institucija testInstitucija = institucijaList.get(institucijaList.size() - 1);
        assertThat(testInstitucija.getSifra()).isEqualTo(DEFAULT_SIFRA);
        assertThat(testInstitucija.getNaziv()).isEqualTo(DEFAULT_NAZIV);
        assertThat(testInstitucija.getMjesto()).isEqualTo(DEFAULT_MJESTO);
    }

    @Test
    @Transactional
    public void createInstitucijaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = institucijaRepository.findAll().size();

        // Create the Institucija with an existing ID
        institucija.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInstitucijaMockMvc.perform(post("/api/institucijas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(institucija)))
            .andExpect(status().isBadRequest());

        // Validate the Institucija in the database
        List<Institucija> institucijaList = institucijaRepository.findAll();
        assertThat(institucijaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInstitucijas() throws Exception {
        // Initialize the database
        institucijaRepository.saveAndFlush(institucija);

        // Get all the institucijaList
        restInstitucijaMockMvc.perform(get("/api/institucijas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(institucija.getId().intValue())))
            .andExpect(jsonPath("$.[*].sifra").value(hasItem(DEFAULT_SIFRA.toString())))
            .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV.toString())))
            .andExpect(jsonPath("$.[*].mjesto").value(hasItem(DEFAULT_MJESTO.toString())));
    }
    
    @Test
    @Transactional
    public void getInstitucija() throws Exception {
        // Initialize the database
        institucijaRepository.saveAndFlush(institucija);

        // Get the institucija
        restInstitucijaMockMvc.perform(get("/api/institucijas/{id}", institucija.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(institucija.getId().intValue()))
            .andExpect(jsonPath("$.sifra").value(DEFAULT_SIFRA.toString()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV.toString()))
            .andExpect(jsonPath("$.mjesto").value(DEFAULT_MJESTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInstitucija() throws Exception {
        // Get the institucija
        restInstitucijaMockMvc.perform(get("/api/institucijas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInstitucija() throws Exception {
        // Initialize the database
        institucijaRepository.saveAndFlush(institucija);

        int databaseSizeBeforeUpdate = institucijaRepository.findAll().size();

        // Update the institucija
        Institucija updatedInstitucija = institucijaRepository.findById(institucija.getId()).get();
        // Disconnect from session so that the updates on updatedInstitucija are not directly saved in db
        em.detach(updatedInstitucija);
        updatedInstitucija
            .sifra(UPDATED_SIFRA)
            .naziv(UPDATED_NAZIV)
            .mjesto(UPDATED_MJESTO);

        restInstitucijaMockMvc.perform(put("/api/institucijas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInstitucija)))
            .andExpect(status().isOk());

        // Validate the Institucija in the database
        List<Institucija> institucijaList = institucijaRepository.findAll();
        assertThat(institucijaList).hasSize(databaseSizeBeforeUpdate);
        Institucija testInstitucija = institucijaList.get(institucijaList.size() - 1);
        assertThat(testInstitucija.getSifra()).isEqualTo(UPDATED_SIFRA);
        assertThat(testInstitucija.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testInstitucija.getMjesto()).isEqualTo(UPDATED_MJESTO);
    }

    @Test
    @Transactional
    public void updateNonExistingInstitucija() throws Exception {
        int databaseSizeBeforeUpdate = institucijaRepository.findAll().size();

        // Create the Institucija

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInstitucijaMockMvc.perform(put("/api/institucijas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(institucija)))
            .andExpect(status().isBadRequest());

        // Validate the Institucija in the database
        List<Institucija> institucijaList = institucijaRepository.findAll();
        assertThat(institucijaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInstitucija() throws Exception {
        // Initialize the database
        institucijaRepository.saveAndFlush(institucija);

        int databaseSizeBeforeDelete = institucijaRepository.findAll().size();

        // Delete the institucija
        restInstitucijaMockMvc.perform(delete("/api/institucijas/{id}", institucija.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Institucija> institucijaList = institucijaRepository.findAll();
        assertThat(institucijaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Institucija.class);
        Institucija institucija1 = new Institucija();
        institucija1.setId(1L);
        Institucija institucija2 = new Institucija();
        institucija2.setId(institucija1.getId());
        assertThat(institucija1).isEqualTo(institucija2);
        institucija2.setId(2L);
        assertThat(institucija1).isNotEqualTo(institucija2);
        institucija1.setId(null);
        assertThat(institucija1).isNotEqualTo(institucija2);
    }
}
