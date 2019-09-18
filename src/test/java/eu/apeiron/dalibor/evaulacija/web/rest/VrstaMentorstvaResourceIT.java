package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.EvaluacijaApp;
import eu.apeiron.dalibor.evaulacija.domain.VrstaMentorstva;
import eu.apeiron.dalibor.evaulacija.repository.VrstaMentorstvaRepository;
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
 * Integration tests for the {@link VrstaMentorstvaResource} REST controller.
 */
@SpringBootTest(classes = EvaluacijaApp.class)
public class VrstaMentorstvaResourceIT {

    private static final String DEFAULT_TIP = "AAAAAAAAAA";
    private static final String UPDATED_TIP = "BBBBBBBBBB";

    @Autowired
    private VrstaMentorstvaRepository vrstaMentorstvaRepository;

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

    private MockMvc restVrstaMentorstvaMockMvc;

    private VrstaMentorstva vrstaMentorstva;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VrstaMentorstvaResource vrstaMentorstvaResource = new VrstaMentorstvaResource(vrstaMentorstvaRepository);
        this.restVrstaMentorstvaMockMvc = MockMvcBuilders.standaloneSetup(vrstaMentorstvaResource)
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
    public static VrstaMentorstva createEntity(EntityManager em) {
        VrstaMentorstva vrstaMentorstva = new VrstaMentorstva()
            .tip(DEFAULT_TIP);
        return vrstaMentorstva;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VrstaMentorstva createUpdatedEntity(EntityManager em) {
        VrstaMentorstva vrstaMentorstva = new VrstaMentorstva()
            .tip(UPDATED_TIP);
        return vrstaMentorstva;
    }

    @BeforeEach
    public void initTest() {
        vrstaMentorstva = createEntity(em);
    }

    @Test
    @Transactional
    public void createVrstaMentorstva() throws Exception {
        int databaseSizeBeforeCreate = vrstaMentorstvaRepository.findAll().size();

        // Create the VrstaMentorstva
        restVrstaMentorstvaMockMvc.perform(post("/api/vrsta-mentorstvas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vrstaMentorstva)))
            .andExpect(status().isCreated());

        // Validate the VrstaMentorstva in the database
        List<VrstaMentorstva> vrstaMentorstvaList = vrstaMentorstvaRepository.findAll();
        assertThat(vrstaMentorstvaList).hasSize(databaseSizeBeforeCreate + 1);
        VrstaMentorstva testVrstaMentorstva = vrstaMentorstvaList.get(vrstaMentorstvaList.size() - 1);
        assertThat(testVrstaMentorstva.getTip()).isEqualTo(DEFAULT_TIP);
    }

    @Test
    @Transactional
    public void createVrstaMentorstvaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vrstaMentorstvaRepository.findAll().size();

        // Create the VrstaMentorstva with an existing ID
        vrstaMentorstva.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVrstaMentorstvaMockMvc.perform(post("/api/vrsta-mentorstvas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vrstaMentorstva)))
            .andExpect(status().isBadRequest());

        // Validate the VrstaMentorstva in the database
        List<VrstaMentorstva> vrstaMentorstvaList = vrstaMentorstvaRepository.findAll();
        assertThat(vrstaMentorstvaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTipIsRequired() throws Exception {
        int databaseSizeBeforeTest = vrstaMentorstvaRepository.findAll().size();
        // set the field null
        vrstaMentorstva.setTip(null);

        // Create the VrstaMentorstva, which fails.

        restVrstaMentorstvaMockMvc.perform(post("/api/vrsta-mentorstvas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vrstaMentorstva)))
            .andExpect(status().isBadRequest());

        List<VrstaMentorstva> vrstaMentorstvaList = vrstaMentorstvaRepository.findAll();
        assertThat(vrstaMentorstvaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVrstaMentorstvas() throws Exception {
        // Initialize the database
        vrstaMentorstvaRepository.saveAndFlush(vrstaMentorstva);

        // Get all the vrstaMentorstvaList
        restVrstaMentorstvaMockMvc.perform(get("/api/vrsta-mentorstvas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vrstaMentorstva.getId().intValue())))
            .andExpect(jsonPath("$.[*].tip").value(hasItem(DEFAULT_TIP.toString())));
    }
    
    @Test
    @Transactional
    public void getVrstaMentorstva() throws Exception {
        // Initialize the database
        vrstaMentorstvaRepository.saveAndFlush(vrstaMentorstva);

        // Get the vrstaMentorstva
        restVrstaMentorstvaMockMvc.perform(get("/api/vrsta-mentorstvas/{id}", vrstaMentorstva.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vrstaMentorstva.getId().intValue()))
            .andExpect(jsonPath("$.tip").value(DEFAULT_TIP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVrstaMentorstva() throws Exception {
        // Get the vrstaMentorstva
        restVrstaMentorstvaMockMvc.perform(get("/api/vrsta-mentorstvas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVrstaMentorstva() throws Exception {
        // Initialize the database
        vrstaMentorstvaRepository.saveAndFlush(vrstaMentorstva);

        int databaseSizeBeforeUpdate = vrstaMentorstvaRepository.findAll().size();

        // Update the vrstaMentorstva
        VrstaMentorstva updatedVrstaMentorstva = vrstaMentorstvaRepository.findById(vrstaMentorstva.getId()).get();
        // Disconnect from session so that the updates on updatedVrstaMentorstva are not directly saved in db
        em.detach(updatedVrstaMentorstva);
        updatedVrstaMentorstva
            .tip(UPDATED_TIP);

        restVrstaMentorstvaMockMvc.perform(put("/api/vrsta-mentorstvas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVrstaMentorstva)))
            .andExpect(status().isOk());

        // Validate the VrstaMentorstva in the database
        List<VrstaMentorstva> vrstaMentorstvaList = vrstaMentorstvaRepository.findAll();
        assertThat(vrstaMentorstvaList).hasSize(databaseSizeBeforeUpdate);
        VrstaMentorstva testVrstaMentorstva = vrstaMentorstvaList.get(vrstaMentorstvaList.size() - 1);
        assertThat(testVrstaMentorstva.getTip()).isEqualTo(UPDATED_TIP);
    }

    @Test
    @Transactional
    public void updateNonExistingVrstaMentorstva() throws Exception {
        int databaseSizeBeforeUpdate = vrstaMentorstvaRepository.findAll().size();

        // Create the VrstaMentorstva

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVrstaMentorstvaMockMvc.perform(put("/api/vrsta-mentorstvas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vrstaMentorstva)))
            .andExpect(status().isBadRequest());

        // Validate the VrstaMentorstva in the database
        List<VrstaMentorstva> vrstaMentorstvaList = vrstaMentorstvaRepository.findAll();
        assertThat(vrstaMentorstvaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVrstaMentorstva() throws Exception {
        // Initialize the database
        vrstaMentorstvaRepository.saveAndFlush(vrstaMentorstva);

        int databaseSizeBeforeDelete = vrstaMentorstvaRepository.findAll().size();

        // Delete the vrstaMentorstva
        restVrstaMentorstvaMockMvc.perform(delete("/api/vrsta-mentorstvas/{id}", vrstaMentorstva.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VrstaMentorstva> vrstaMentorstvaList = vrstaMentorstvaRepository.findAll();
        assertThat(vrstaMentorstvaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VrstaMentorstva.class);
        VrstaMentorstva vrstaMentorstva1 = new VrstaMentorstva();
        vrstaMentorstva1.setId(1L);
        VrstaMentorstva vrstaMentorstva2 = new VrstaMentorstva();
        vrstaMentorstva2.setId(vrstaMentorstva1.getId());
        assertThat(vrstaMentorstva1).isEqualTo(vrstaMentorstva2);
        vrstaMentorstva2.setId(2L);
        assertThat(vrstaMentorstva1).isNotEqualTo(vrstaMentorstva2);
        vrstaMentorstva1.setId(null);
        assertThat(vrstaMentorstva1).isNotEqualTo(vrstaMentorstva2);
    }
}
