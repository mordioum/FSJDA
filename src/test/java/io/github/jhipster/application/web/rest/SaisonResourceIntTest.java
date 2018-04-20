package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.FsjdaApp;

import io.github.jhipster.application.domain.Saison;
import io.github.jhipster.application.repository.SaisonRepository;
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
 * Test class for the SaisonResource REST controller.
 *
 * @see SaisonResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FsjdaApp.class)
public class SaisonResourceIntTest {

    private static final Integer DEFAULT_SAISON = 1;
    private static final Integer UPDATED_SAISON = 2;

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private SaisonRepository saisonRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSaisonMockMvc;

    private Saison saison;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SaisonResource saisonResource = new SaisonResource(saisonRepository);
        this.restSaisonMockMvc = MockMvcBuilders.standaloneSetup(saisonResource)
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
    public static Saison createEntity(EntityManager em) {
        Saison saison = new Saison()
            .saison(DEFAULT_SAISON)
            .dateCreation(DEFAULT_DATE_CREATION);
        return saison;
    }

    @Before
    public void initTest() {
        saison = createEntity(em);
    }

    @Test
    @Transactional
    public void createSaison() throws Exception {
        int databaseSizeBeforeCreate = saisonRepository.findAll().size();

        // Create the Saison
        restSaisonMockMvc.perform(post("/api/saisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saison)))
            .andExpect(status().isCreated());

        // Validate the Saison in the database
        List<Saison> saisonList = saisonRepository.findAll();
        assertThat(saisonList).hasSize(databaseSizeBeforeCreate + 1);
        Saison testSaison = saisonList.get(saisonList.size() - 1);
        assertThat(testSaison.getSaison()).isEqualTo(DEFAULT_SAISON);
        assertThat(testSaison.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
    }

    @Test
    @Transactional
    public void createSaisonWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = saisonRepository.findAll().size();

        // Create the Saison with an existing ID
        saison.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSaisonMockMvc.perform(post("/api/saisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saison)))
            .andExpect(status().isBadRequest());

        // Validate the Saison in the database
        List<Saison> saisonList = saisonRepository.findAll();
        assertThat(saisonList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkSaisonIsRequired() throws Exception {
        int databaseSizeBeforeTest = saisonRepository.findAll().size();
        // set the field null
        saison.setSaison(null);

        // Create the Saison, which fails.

        restSaisonMockMvc.perform(post("/api/saisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saison)))
            .andExpect(status().isBadRequest());

        List<Saison> saisonList = saisonRepository.findAll();
        assertThat(saisonList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllSaisons() throws Exception {
        // Initialize the database
        saisonRepository.saveAndFlush(saison);

        // Get all the saisonList
        restSaisonMockMvc.perform(get("/api/saisons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(saison.getId().intValue())))
            .andExpect(jsonPath("$.[*].saison").value(hasItem(DEFAULT_SAISON)))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())));
    }

    @Test
    @Transactional
    public void getSaison() throws Exception {
        // Initialize the database
        saisonRepository.saveAndFlush(saison);

        // Get the saison
        restSaisonMockMvc.perform(get("/api/saisons/{id}", saison.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(saison.getId().intValue()))
            .andExpect(jsonPath("$.saison").value(DEFAULT_SAISON))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSaison() throws Exception {
        // Get the saison
        restSaisonMockMvc.perform(get("/api/saisons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSaison() throws Exception {
        // Initialize the database
        saisonRepository.saveAndFlush(saison);
        int databaseSizeBeforeUpdate = saisonRepository.findAll().size();

        // Update the saison
        Saison updatedSaison = saisonRepository.findOne(saison.getId());
        // Disconnect from session so that the updates on updatedSaison are not directly saved in db
        em.detach(updatedSaison);
        updatedSaison
            .saison(UPDATED_SAISON)
            .dateCreation(UPDATED_DATE_CREATION);

        restSaisonMockMvc.perform(put("/api/saisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSaison)))
            .andExpect(status().isOk());

        // Validate the Saison in the database
        List<Saison> saisonList = saisonRepository.findAll();
        assertThat(saisonList).hasSize(databaseSizeBeforeUpdate);
        Saison testSaison = saisonList.get(saisonList.size() - 1);
        assertThat(testSaison.getSaison()).isEqualTo(UPDATED_SAISON);
        assertThat(testSaison.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
    }

    @Test
    @Transactional
    public void updateNonExistingSaison() throws Exception {
        int databaseSizeBeforeUpdate = saisonRepository.findAll().size();

        // Create the Saison

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSaisonMockMvc.perform(put("/api/saisons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(saison)))
            .andExpect(status().isCreated());

        // Validate the Saison in the database
        List<Saison> saisonList = saisonRepository.findAll();
        assertThat(saisonList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSaison() throws Exception {
        // Initialize the database
        saisonRepository.saveAndFlush(saison);
        int databaseSizeBeforeDelete = saisonRepository.findAll().size();

        // Get the saison
        restSaisonMockMvc.perform(delete("/api/saisons/{id}", saison.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Saison> saisonList = saisonRepository.findAll();
        assertThat(saisonList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Saison.class);
        Saison saison1 = new Saison();
        saison1.setId(1L);
        Saison saison2 = new Saison();
        saison2.setId(saison1.getId());
        assertThat(saison1).isEqualTo(saison2);
        saison2.setId(2L);
        assertThat(saison1).isNotEqualTo(saison2);
        saison1.setId(null);
        assertThat(saison1).isNotEqualTo(saison2);
    }
}
