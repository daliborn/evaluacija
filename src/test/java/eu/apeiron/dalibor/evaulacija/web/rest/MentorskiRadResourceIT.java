package eu.apeiron.dalibor.evaulacija.web.rest;

import eu.apeiron.dalibor.evaulacija.EvaluacijaApp;
import eu.apeiron.dalibor.evaulacija.domain.MentorskiRad;
import eu.apeiron.dalibor.evaulacija.repository.MentorskiRadRepository;
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
 * Integration tests for the {@link MentorskiRadResource} REST controller.
 */
@SpringBootTest(classes = EvaluacijaApp.class)
public class MentorskiRadResourceIT {

    private static final String DEFAULT_NAZIV = "AAAAAAAAAA";
    private static final String UPDATED_NAZIV = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DATUM_POCETKA_MENTORSKOG_RADA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATUM_POCETKA_MENTORSKOG_RADA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATUM_POCETKA_MENTORSKOG_RADA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    private static final ZonedDateTime DEFAULT_DATUM_ZAVRSETKA_MENTORSKOG_RADA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DATUM_ZAVRSETKA_MENTORSKOG_RADA = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_DATUM_ZAVRSETKA_MENTORSKOG_RADA = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private MentorskiRadRepository mentorskiRadRepository;

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

    private MockMvc restMentorskiRadMockMvc;

    private MentorskiRad mentorskiRad;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MentorskiRadResource mentorskiRadResource = new MentorskiRadResource(mentorskiRadRepository);
        this.restMentorskiRadMockMvc = MockMvcBuilders.standaloneSetup(mentorskiRadResource)
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
    public static MentorskiRad createEntity(EntityManager em) {
        MentorskiRad mentorskiRad = new MentorskiRad()
            .naziv(DEFAULT_NAZIV)
            .datumPocetkaMentorskogRada(DEFAULT_DATUM_POCETKA_MENTORSKOG_RADA)
            .datumZavrsetkaMentorskogRada(DEFAULT_DATUM_ZAVRSETKA_MENTORSKOG_RADA);
        return mentorskiRad;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MentorskiRad createUpdatedEntity(EntityManager em) {
        MentorskiRad mentorskiRad = new MentorskiRad()
            .naziv(UPDATED_NAZIV)
            .datumPocetkaMentorskogRada(UPDATED_DATUM_POCETKA_MENTORSKOG_RADA)
            .datumZavrsetkaMentorskogRada(UPDATED_DATUM_ZAVRSETKA_MENTORSKOG_RADA);
        return mentorskiRad;
    }

    @BeforeEach
    public void initTest() {
        mentorskiRad = createEntity(em);
    }

    @Test
    @Transactional
    public void createMentorskiRad() throws Exception {
        int databaseSizeBeforeCreate = mentorskiRadRepository.findAll().size();

        // Create the MentorskiRad
        restMentorskiRadMockMvc.perform(post("/api/mentorski-rads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mentorskiRad)))
            .andExpect(status().isCreated());

        // Validate the MentorskiRad in the database
        List<MentorskiRad> mentorskiRadList = mentorskiRadRepository.findAll();
        assertThat(mentorskiRadList).hasSize(databaseSizeBeforeCreate + 1);
        MentorskiRad testMentorskiRad = mentorskiRadList.get(mentorskiRadList.size() - 1);
        assertThat(testMentorskiRad.getNaziv()).isEqualTo(DEFAULT_NAZIV);
        assertThat(testMentorskiRad.getDatumPocetkaMentorskogRada()).isEqualTo(DEFAULT_DATUM_POCETKA_MENTORSKOG_RADA);
        assertThat(testMentorskiRad.getDatumZavrsetkaMentorskogRada()).isEqualTo(DEFAULT_DATUM_ZAVRSETKA_MENTORSKOG_RADA);
    }

    @Test
    @Transactional
    public void createMentorskiRadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mentorskiRadRepository.findAll().size();

        // Create the MentorskiRad with an existing ID
        mentorskiRad.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMentorskiRadMockMvc.perform(post("/api/mentorski-rads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mentorskiRad)))
            .andExpect(status().isBadRequest());

        // Validate the MentorskiRad in the database
        List<MentorskiRad> mentorskiRadList = mentorskiRadRepository.findAll();
        assertThat(mentorskiRadList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNazivIsRequired() throws Exception {
        int databaseSizeBeforeTest = mentorskiRadRepository.findAll().size();
        // set the field null
        mentorskiRad.setNaziv(null);

        // Create the MentorskiRad, which fails.

        restMentorskiRadMockMvc.perform(post("/api/mentorski-rads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mentorskiRad)))
            .andExpect(status().isBadRequest());

        List<MentorskiRad> mentorskiRadList = mentorskiRadRepository.findAll();
        assertThat(mentorskiRadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMentorskiRads() throws Exception {
        // Initialize the database
        mentorskiRadRepository.saveAndFlush(mentorskiRad);

        // Get all the mentorskiRadList
        restMentorskiRadMockMvc.perform(get("/api/mentorski-rads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(mentorskiRad.getId().intValue())))
            .andExpect(jsonPath("$.[*].naziv").value(hasItem(DEFAULT_NAZIV.toString())))
            .andExpect(jsonPath("$.[*].datumPocetkaMentorskogRada").value(hasItem(sameInstant(DEFAULT_DATUM_POCETKA_MENTORSKOG_RADA))))
            .andExpect(jsonPath("$.[*].datumZavrsetkaMentorskogRada").value(hasItem(sameInstant(DEFAULT_DATUM_ZAVRSETKA_MENTORSKOG_RADA))));
    }
    
    @Test
    @Transactional
    public void getMentorskiRad() throws Exception {
        // Initialize the database
        mentorskiRadRepository.saveAndFlush(mentorskiRad);

        // Get the mentorskiRad
        restMentorskiRadMockMvc.perform(get("/api/mentorski-rads/{id}", mentorskiRad.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(mentorskiRad.getId().intValue()))
            .andExpect(jsonPath("$.naziv").value(DEFAULT_NAZIV.toString()))
            .andExpect(jsonPath("$.datumPocetkaMentorskogRada").value(sameInstant(DEFAULT_DATUM_POCETKA_MENTORSKOG_RADA)))
            .andExpect(jsonPath("$.datumZavrsetkaMentorskogRada").value(sameInstant(DEFAULT_DATUM_ZAVRSETKA_MENTORSKOG_RADA)));
    }

    @Test
    @Transactional
    public void getNonExistingMentorskiRad() throws Exception {
        // Get the mentorskiRad
        restMentorskiRadMockMvc.perform(get("/api/mentorski-rads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMentorskiRad() throws Exception {
        // Initialize the database
        mentorskiRadRepository.saveAndFlush(mentorskiRad);

        int databaseSizeBeforeUpdate = mentorskiRadRepository.findAll().size();

        // Update the mentorskiRad
        MentorskiRad updatedMentorskiRad = mentorskiRadRepository.findById(mentorskiRad.getId()).get();
        // Disconnect from session so that the updates on updatedMentorskiRad are not directly saved in db
        em.detach(updatedMentorskiRad);
        updatedMentorskiRad
            .naziv(UPDATED_NAZIV)
            .datumPocetkaMentorskogRada(UPDATED_DATUM_POCETKA_MENTORSKOG_RADA)
            .datumZavrsetkaMentorskogRada(UPDATED_DATUM_ZAVRSETKA_MENTORSKOG_RADA);

        restMentorskiRadMockMvc.perform(put("/api/mentorski-rads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMentorskiRad)))
            .andExpect(status().isOk());

        // Validate the MentorskiRad in the database
        List<MentorskiRad> mentorskiRadList = mentorskiRadRepository.findAll();
        assertThat(mentorskiRadList).hasSize(databaseSizeBeforeUpdate);
        MentorskiRad testMentorskiRad = mentorskiRadList.get(mentorskiRadList.size() - 1);
        assertThat(testMentorskiRad.getNaziv()).isEqualTo(UPDATED_NAZIV);
        assertThat(testMentorskiRad.getDatumPocetkaMentorskogRada()).isEqualTo(UPDATED_DATUM_POCETKA_MENTORSKOG_RADA);
        assertThat(testMentorskiRad.getDatumZavrsetkaMentorskogRada()).isEqualTo(UPDATED_DATUM_ZAVRSETKA_MENTORSKOG_RADA);
    }

    @Test
    @Transactional
    public void updateNonExistingMentorskiRad() throws Exception {
        int databaseSizeBeforeUpdate = mentorskiRadRepository.findAll().size();

        // Create the MentorskiRad

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMentorskiRadMockMvc.perform(put("/api/mentorski-rads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mentorskiRad)))
            .andExpect(status().isBadRequest());

        // Validate the MentorskiRad in the database
        List<MentorskiRad> mentorskiRadList = mentorskiRadRepository.findAll();
        assertThat(mentorskiRadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMentorskiRad() throws Exception {
        // Initialize the database
        mentorskiRadRepository.saveAndFlush(mentorskiRad);

        int databaseSizeBeforeDelete = mentorskiRadRepository.findAll().size();

        // Delete the mentorskiRad
        restMentorskiRadMockMvc.perform(delete("/api/mentorski-rads/{id}", mentorskiRad.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MentorskiRad> mentorskiRadList = mentorskiRadRepository.findAll();
        assertThat(mentorskiRadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MentorskiRad.class);
        MentorskiRad mentorskiRad1 = new MentorskiRad();
        mentorskiRad1.setId(1L);
        MentorskiRad mentorskiRad2 = new MentorskiRad();
        mentorskiRad2.setId(mentorskiRad1.getId());
        assertThat(mentorskiRad1).isEqualTo(mentorskiRad2);
        mentorskiRad2.setId(2L);
        assertThat(mentorskiRad1).isNotEqualTo(mentorskiRad2);
        mentorskiRad1.setId(null);
        assertThat(mentorskiRad1).isNotEqualTo(mentorskiRad2);
    }
}
