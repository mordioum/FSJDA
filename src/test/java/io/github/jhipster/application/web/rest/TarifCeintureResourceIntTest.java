package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.FsjdaApp;

import io.github.jhipster.application.domain.TarifCeinture;
import io.github.jhipster.application.repository.TarifCeintureRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TarifCeintureResource REST controller.
 *
 * @see TarifCeintureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FsjdaApp.class)
public class TarifCeintureResourceIntTest {

    private static final String DEFAULT_CEINTURE = "AAAAAAAAAA";
    private static final String UPDATED_CEINTURE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_MONTANT = 1;
    private static final Integer UPDATED_MONTANT = 2;

    @Autowired
    private TarifCeintureRepository tarifCeintureRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTarifCeintureMockMvc;

    private TarifCeinture tarifCeinture;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TarifCeintureResource tarifCeintureResource = new TarifCeintureResource(tarifCeintureRepository);
        this.restTarifCeintureMockMvc = MockMvcBuilders.standaloneSetup(tarifCeintureResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TarifCeinture createEntity(EntityManager em) {
        TarifCeinture tarifCeinture = new TarifCeinture()
            .ceinture(DEFAULT_CEINTURE)
            .dateCreation(DEFAULT_DATE_CREATION)
            .montant(DEFAULT_MONTANT);
        return tarifCeinture;
    }

    @Before
    public void initTest() {
        tarifCeinture = createEntity(em);
    }

    @Test
    @Transactional
    public void createTarifCeinture() throws Exception {
        int databaseSizeBeforeCreate = tarifCeintureRepository.findAll().size();

        // Create the TarifCeinture
        restTarifCeintureMockMvc.perform(post("/api/tarif-ceintures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifCeinture)))
            .andExpect(status().isCreated());

        // Validate the TarifCeinture in the database
        List<TarifCeinture> tarifCeintureList = tarifCeintureRepository.findAll();
        assertThat(tarifCeintureList).hasSize(databaseSizeBeforeCreate + 1);
        TarifCeinture testTarifCeinture = tarifCeintureList.get(tarifCeintureList.size() - 1);
        assertThat(testTarifCeinture.getCeinture()).isEqualTo(DEFAULT_CEINTURE);
        assertThat(testTarifCeinture.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testTarifCeinture.getMontant()).isEqualTo(DEFAULT_MONTANT);
    }

    @Test
    @Transactional
    public void createTarifCeintureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tarifCeintureRepository.findAll().size();

        // Create the TarifCeinture with an existing ID
        tarifCeinture.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTarifCeintureMockMvc.perform(post("/api/tarif-ceintures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifCeinture)))
            .andExpect(status().isBadRequest());

        // Validate the TarifCeinture in the database
        List<TarifCeinture> tarifCeintureList = tarifCeintureRepository.findAll();
        assertThat(tarifCeintureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCeintureIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarifCeintureRepository.findAll().size();
        // set the field null
        tarifCeinture.setCeinture(null);

        // Create the TarifCeinture, which fails.

        restTarifCeintureMockMvc.perform(post("/api/tarif-ceintures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifCeinture)))
            .andExpect(status().isBadRequest());

        List<TarifCeinture> tarifCeintureList = tarifCeintureRepository.findAll();
        assertThat(tarifCeintureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMontantIsRequired() throws Exception {
        int databaseSizeBeforeTest = tarifCeintureRepository.findAll().size();
        // set the field null
        tarifCeinture.setMontant(null);

        // Create the TarifCeinture, which fails.

        restTarifCeintureMockMvc.perform(post("/api/tarif-ceintures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifCeinture)))
            .andExpect(status().isBadRequest());

        List<TarifCeinture> tarifCeintureList = tarifCeintureRepository.findAll();
        assertThat(tarifCeintureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTarifCeintures() throws Exception {
        // Initialize the database
        tarifCeintureRepository.saveAndFlush(tarifCeinture);

        // Get all the tarifCeintureList
        restTarifCeintureMockMvc.perform(get("/api/tarif-ceintures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tarifCeinture.getId().intValue())))
            .andExpect(jsonPath("$.[*].ceinture").value(hasItem(DEFAULT_CEINTURE.toString())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT)));
    }

    @Test
    @Transactional
    public void getTarifCeinture() throws Exception {
        // Initialize the database
        tarifCeintureRepository.saveAndFlush(tarifCeinture);

        // Get the tarifCeinture
        restTarifCeintureMockMvc.perform(get("/api/tarif-ceintures/{id}", tarifCeinture.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tarifCeinture.getId().intValue()))
            .andExpect(jsonPath("$.ceinture").value(DEFAULT_CEINTURE.toString()))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT));
    }

    @Test
    @Transactional
    public void getNonExistingTarifCeinture() throws Exception {
        // Get the tarifCeinture
        restTarifCeintureMockMvc.perform(get("/api/tarif-ceintures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTarifCeinture() throws Exception {
        // Initialize the database
        tarifCeintureRepository.saveAndFlush(tarifCeinture);
        int databaseSizeBeforeUpdate = tarifCeintureRepository.findAll().size();

        // Update the tarifCeinture
        TarifCeinture updatedTarifCeinture = tarifCeintureRepository.findOne(tarifCeinture.getId());
        // Disconnect from session so that the updates on updatedTarifCeinture are not directly saved in db
        em.detach(updatedTarifCeinture);
        updatedTarifCeinture
            .ceinture(UPDATED_CEINTURE)
            .dateCreation(UPDATED_DATE_CREATION)
            .montant(UPDATED_MONTANT);

        restTarifCeintureMockMvc.perform(put("/api/tarif-ceintures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTarifCeinture)))
            .andExpect(status().isOk());

        // Validate the TarifCeinture in the database
        List<TarifCeinture> tarifCeintureList = tarifCeintureRepository.findAll();
        assertThat(tarifCeintureList).hasSize(databaseSizeBeforeUpdate);
        TarifCeinture testTarifCeinture = tarifCeintureList.get(tarifCeintureList.size() - 1);
        assertThat(testTarifCeinture.getCeinture()).isEqualTo(UPDATED_CEINTURE);
        assertThat(testTarifCeinture.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testTarifCeinture.getMontant()).isEqualTo(UPDATED_MONTANT);
    }

    @Test
    @Transactional
    public void updateNonExistingTarifCeinture() throws Exception {
        int databaseSizeBeforeUpdate = tarifCeintureRepository.findAll().size();

        // Create the TarifCeinture

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTarifCeintureMockMvc.perform(put("/api/tarif-ceintures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tarifCeinture)))
            .andExpect(status().isCreated());

        // Validate the TarifCeinture in the database
        List<TarifCeinture> tarifCeintureList = tarifCeintureRepository.findAll();
        assertThat(tarifCeintureList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTarifCeinture() throws Exception {
        // Initialize the database
        tarifCeintureRepository.saveAndFlush(tarifCeinture);
        int databaseSizeBeforeDelete = tarifCeintureRepository.findAll().size();

        // Get the tarifCeinture
        restTarifCeintureMockMvc.perform(delete("/api/tarif-ceintures/{id}", tarifCeinture.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TarifCeinture> tarifCeintureList = tarifCeintureRepository.findAll();
        assertThat(tarifCeintureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TarifCeinture.class);
        TarifCeinture tarifCeinture1 = new TarifCeinture();
        tarifCeinture1.setId(1L);
        TarifCeinture tarifCeinture2 = new TarifCeinture();
        tarifCeinture2.setId(tarifCeinture1.getId());
        assertThat(tarifCeinture1).isEqualTo(tarifCeinture2);
        tarifCeinture2.setId(2L);
        assertThat(tarifCeinture1).isNotEqualTo(tarifCeinture2);
        tarifCeinture1.setId(null);
        assertThat(tarifCeinture1).isNotEqualTo(tarifCeinture2);
    }
}
