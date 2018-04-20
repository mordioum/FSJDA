package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.FsjdaApp;

import io.github.jhipster.application.domain.Ligue;
import io.github.jhipster.application.domain.Discipline;
import io.github.jhipster.application.repository.LigueRepository;
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
 * Test class for the LigueResource REST controller.
 *
 * @see LigueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FsjdaApp.class)
public class LigueResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PRESIDENT = "AAAAAAAAAA";
    private static final String UPDATED_PRESIDENT = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private LigueRepository ligueRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLigueMockMvc;

    private Ligue ligue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LigueResource ligueResource = new LigueResource(ligueRepository);
        this.restLigueMockMvc = MockMvcBuilders.standaloneSetup(ligueResource)
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
    public static Ligue createEntity(EntityManager em) {
        Ligue ligue = new Ligue()
            .name(DEFAULT_NAME)
            .president(DEFAULT_PRESIDENT)
            .dateCreation(DEFAULT_DATE_CREATION)
            .description(DEFAULT_DESCRIPTION)
            .adresse(DEFAULT_ADRESSE)
            .telephone(DEFAULT_TELEPHONE)
            .email(DEFAULT_EMAIL);
        // Add required entity
        Discipline discipline = DisciplineResourceIntTest.createEntity(em);
        em.persist(discipline);
        em.flush();
        ligue.setDiscipline(discipline);
        return ligue;
    }

    @Before
    public void initTest() {
        ligue = createEntity(em);
    }

    @Test
    @Transactional
    public void createLigue() throws Exception {
        int databaseSizeBeforeCreate = ligueRepository.findAll().size();

        // Create the Ligue
        restLigueMockMvc.perform(post("/api/ligues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligue)))
            .andExpect(status().isCreated());

        // Validate the Ligue in the database
        List<Ligue> ligueList = ligueRepository.findAll();
        assertThat(ligueList).hasSize(databaseSizeBeforeCreate + 1);
        Ligue testLigue = ligueList.get(ligueList.size() - 1);
        assertThat(testLigue.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLigue.getPresident()).isEqualTo(DEFAULT_PRESIDENT);
        assertThat(testLigue.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testLigue.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLigue.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testLigue.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testLigue.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createLigueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ligueRepository.findAll().size();

        // Create the Ligue with an existing ID
        ligue.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLigueMockMvc.perform(post("/api/ligues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligue)))
            .andExpect(status().isBadRequest());

        // Validate the Ligue in the database
        List<Ligue> ligueList = ligueRepository.findAll();
        assertThat(ligueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = ligueRepository.findAll().size();
        // set the field null
        ligue.setName(null);

        // Create the Ligue, which fails.

        restLigueMockMvc.perform(post("/api/ligues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligue)))
            .andExpect(status().isBadRequest());

        List<Ligue> ligueList = ligueRepository.findAll();
        assertThat(ligueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPresidentIsRequired() throws Exception {
        int databaseSizeBeforeTest = ligueRepository.findAll().size();
        // set the field null
        ligue.setPresident(null);

        // Create the Ligue, which fails.

        restLigueMockMvc.perform(post("/api/ligues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligue)))
            .andExpect(status().isBadRequest());

        List<Ligue> ligueList = ligueRepository.findAll();
        assertThat(ligueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = ligueRepository.findAll().size();
        // set the field null
        ligue.setDescription(null);

        // Create the Ligue, which fails.

        restLigueMockMvc.perform(post("/api/ligues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligue)))
            .andExpect(status().isBadRequest());

        List<Ligue> ligueList = ligueRepository.findAll();
        assertThat(ligueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresseIsRequired() throws Exception {
        int databaseSizeBeforeTest = ligueRepository.findAll().size();
        // set the field null
        ligue.setAdresse(null);

        // Create the Ligue, which fails.

        restLigueMockMvc.perform(post("/api/ligues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligue)))
            .andExpect(status().isBadRequest());

        List<Ligue> ligueList = ligueRepository.findAll();
        assertThat(ligueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = ligueRepository.findAll().size();
        // set the field null
        ligue.setTelephone(null);

        // Create the Ligue, which fails.

        restLigueMockMvc.perform(post("/api/ligues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligue)))
            .andExpect(status().isBadRequest());

        List<Ligue> ligueList = ligueRepository.findAll();
        assertThat(ligueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = ligueRepository.findAll().size();
        // set the field null
        ligue.setEmail(null);

        // Create the Ligue, which fails.

        restLigueMockMvc.perform(post("/api/ligues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligue)))
            .andExpect(status().isBadRequest());

        List<Ligue> ligueList = ligueRepository.findAll();
        assertThat(ligueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLigues() throws Exception {
        // Initialize the database
        ligueRepository.saveAndFlush(ligue);

        // Get all the ligueList
        restLigueMockMvc.perform(get("/api/ligues?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ligue.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].president").value(hasItem(DEFAULT_PRESIDENT.toString())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getLigue() throws Exception {
        // Initialize the database
        ligueRepository.saveAndFlush(ligue);

        // Get the ligue
        restLigueMockMvc.perform(get("/api/ligues/{id}", ligue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ligue.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.president").value(DEFAULT_PRESIDENT.toString()))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLigue() throws Exception {
        // Get the ligue
        restLigueMockMvc.perform(get("/api/ligues/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLigue() throws Exception {
        // Initialize the database
        ligueRepository.saveAndFlush(ligue);
        int databaseSizeBeforeUpdate = ligueRepository.findAll().size();

        // Update the ligue
        Ligue updatedLigue = ligueRepository.findOne(ligue.getId());
        // Disconnect from session so that the updates on updatedLigue are not directly saved in db
        em.detach(updatedLigue);
        updatedLigue
            .name(UPDATED_NAME)
            .president(UPDATED_PRESIDENT)
            .dateCreation(UPDATED_DATE_CREATION)
            .description(UPDATED_DESCRIPTION)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL);

        restLigueMockMvc.perform(put("/api/ligues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLigue)))
            .andExpect(status().isOk());

        // Validate the Ligue in the database
        List<Ligue> ligueList = ligueRepository.findAll();
        assertThat(ligueList).hasSize(databaseSizeBeforeUpdate);
        Ligue testLigue = ligueList.get(ligueList.size() - 1);
        assertThat(testLigue.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLigue.getPresident()).isEqualTo(UPDATED_PRESIDENT);
        assertThat(testLigue.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testLigue.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLigue.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testLigue.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testLigue.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingLigue() throws Exception {
        int databaseSizeBeforeUpdate = ligueRepository.findAll().size();

        // Create the Ligue

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLigueMockMvc.perform(put("/api/ligues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ligue)))
            .andExpect(status().isCreated());

        // Validate the Ligue in the database
        List<Ligue> ligueList = ligueRepository.findAll();
        assertThat(ligueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLigue() throws Exception {
        // Initialize the database
        ligueRepository.saveAndFlush(ligue);
        int databaseSizeBeforeDelete = ligueRepository.findAll().size();

        // Get the ligue
        restLigueMockMvc.perform(delete("/api/ligues/{id}", ligue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ligue> ligueList = ligueRepository.findAll();
        assertThat(ligueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ligue.class);
        Ligue ligue1 = new Ligue();
        ligue1.setId(1L);
        Ligue ligue2 = new Ligue();
        ligue2.setId(ligue1.getId());
        assertThat(ligue1).isEqualTo(ligue2);
        ligue2.setId(2L);
        assertThat(ligue1).isNotEqualTo(ligue2);
        ligue1.setId(null);
        assertThat(ligue1).isNotEqualTo(ligue2);
    }
}
