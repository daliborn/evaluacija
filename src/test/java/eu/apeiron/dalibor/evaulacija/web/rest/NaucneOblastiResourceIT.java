package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.EvaluacijaApp;
import eu.apeiron.dalibor.evaulacija.domain.NaucneOblasti;
import eu.apeiron.dalibor.evaulacija.repository.NaucneOblastiRepository;
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
 * Integration tests for the {@link NaucneOblastiResource} REST controller.
 */
@SpringBootTest(classes = EvaluacijaApp.class)
public class NaucneOblastiResourceIT {

    private static final String DEFAULT_OBLAST = "AAAAAAAAAA";
    private static final String UPDATED_OBLAST = "BBBBBBBBBB";

    @Autowired
    private NaucneOblastiRepository naucneOblastiRepository;

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

    private MockMvc restNaucneOblastiMockMvc;

    private NaucneOblasti naucneOblasti;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NaucneOblastiResource naucneOblastiResource = new NaucneOblastiResource(naucneOblastiRepository);
        this.restNaucneOblastiMockMvc = MockMvcBuilders.standaloneSetup(naucneOblastiResource)
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
    public static NaucneOblasti createEntity(EntityManager em) {
        NaucneOblasti naucneOblasti = new NaucneOblasti()
            .oblast(DEFAULT_OBLAST);
        return naucneOblasti;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NaucneOblasti createUpdatedEntity(EntityManager em) {
        NaucneOblasti naucneOblasti = new NaucneOblasti()
            .oblast(UPDATED_OBLAST);
        return naucneOblasti;
    }

    @BeforeEach
    public void initTest() {
        naucneOblasti = createEntity(em);
    }

    @Test
    @Transactional
    public void createNaucneOblasti() throws Exception {
        int databaseSizeBeforeCreate = naucneOblastiRepository.findAll().size();

        // Create the NaucneOblasti
        restNaucneOblastiMockMvc.perform(post("/api/naucne-oblastis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naucneOblasti)))
            .andExpect(status().isCreated());

        // Validate the NaucneOblasti in the database
        List<NaucneOblasti> naucneOblastiList = naucneOblastiRepository.findAll();
        assertThat(naucneOblastiList).hasSize(databaseSizeBeforeCreate + 1);
        NaucneOblasti testNaucneOblasti = naucneOblastiList.get(naucneOblastiList.size() - 1);
        assertThat(testNaucneOblasti.getOblast()).isEqualTo(DEFAULT_OBLAST);
    }

    @Test
    @Transactional
    public void createNaucneOblastiWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = naucneOblastiRepository.findAll().size();

        // Create the NaucneOblasti with an existing ID
        naucneOblasti.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNaucneOblastiMockMvc.perform(post("/api/naucne-oblastis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naucneOblasti)))
            .andExpect(status().isBadRequest());

        // Validate the NaucneOblasti in the database
        List<NaucneOblasti> naucneOblastiList = naucneOblastiRepository.findAll();
        assertThat(naucneOblastiList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNaucneOblastis() throws Exception {
        // Initialize the database
        naucneOblastiRepository.saveAndFlush(naucneOblasti);

        // Get all the naucneOblastiList
        restNaucneOblastiMockMvc.perform(get("/api/naucne-oblastis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(naucneOblasti.getId().intValue())))
            .andExpect(jsonPath("$.[*].oblast").value(hasItem(DEFAULT_OBLAST.toString())));
    }
    
    @Test
    @Transactional
    public void getNaucneOblasti() throws Exception {
        // Initialize the database
        naucneOblastiRepository.saveAndFlush(naucneOblasti);

        // Get the naucneOblasti
        restNaucneOblastiMockMvc.perform(get("/api/naucne-oblastis/{id}", naucneOblasti.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(naucneOblasti.getId().intValue()))
            .andExpect(jsonPath("$.oblast").value(DEFAULT_OBLAST.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNaucneOblasti() throws Exception {
        // Get the naucneOblasti
        restNaucneOblastiMockMvc.perform(get("/api/naucne-oblastis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNaucneOblasti() throws Exception {
        // Initialize the database
        naucneOblastiRepository.saveAndFlush(naucneOblasti);

        int databaseSizeBeforeUpdate = naucneOblastiRepository.findAll().size();

        // Update the naucneOblasti
        NaucneOblasti updatedNaucneOblasti = naucneOblastiRepository.findById(naucneOblasti.getId()).get();
        // Disconnect from session so that the updates on updatedNaucneOblasti are not directly saved in db
        em.detach(updatedNaucneOblasti);
        updatedNaucneOblasti
            .oblast(UPDATED_OBLAST);

        restNaucneOblastiMockMvc.perform(put("/api/naucne-oblastis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNaucneOblasti)))
            .andExpect(status().isOk());

        // Validate the NaucneOblasti in the database
        List<NaucneOblasti> naucneOblastiList = naucneOblastiRepository.findAll();
        assertThat(naucneOblastiList).hasSize(databaseSizeBeforeUpdate);
        NaucneOblasti testNaucneOblasti = naucneOblastiList.get(naucneOblastiList.size() - 1);
        assertThat(testNaucneOblasti.getOblast()).isEqualTo(UPDATED_OBLAST);
    }

    @Test
    @Transactional
    public void updateNonExistingNaucneOblasti() throws Exception {
        int databaseSizeBeforeUpdate = naucneOblastiRepository.findAll().size();

        // Create the NaucneOblasti

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNaucneOblastiMockMvc.perform(put("/api/naucne-oblastis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(naucneOblasti)))
            .andExpect(status().isBadRequest());

        // Validate the NaucneOblasti in the database
        List<NaucneOblasti> naucneOblastiList = naucneOblastiRepository.findAll();
        assertThat(naucneOblastiList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNaucneOblasti() throws Exception {
        // Initialize the database
        naucneOblastiRepository.saveAndFlush(naucneOblasti);

        int databaseSizeBeforeDelete = naucneOblastiRepository.findAll().size();

        // Delete the naucneOblasti
        restNaucneOblastiMockMvc.perform(delete("/api/naucne-oblastis/{id}", naucneOblasti.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NaucneOblasti> naucneOblastiList = naucneOblastiRepository.findAll();
        assertThat(naucneOblastiList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NaucneOblasti.class);
        NaucneOblasti naucneOblasti1 = new NaucneOblasti();
        naucneOblasti1.setId(1L);
        NaucneOblasti naucneOblasti2 = new NaucneOblasti();
        naucneOblasti2.setId(naucneOblasti1.getId());
        assertThat(naucneOblasti1).isEqualTo(naucneOblasti2);
        naucneOblasti2.setId(2L);
        assertThat(naucneOblasti1).isNotEqualTo(naucneOblasti2);
        naucneOblasti1.setId(null);
        assertThat(naucneOblasti1).isNotEqualTo(naucneOblasti2);
    }
}
