package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.FsjdaApp;

import io.github.jhipster.application.domain.DojoClub;
import io.github.jhipster.application.domain.Ligue;
import io.github.jhipster.application.repository.DojoClubRepository;
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
 * Test class for the DojoClubResource REST controller.
 *
 * @see DojoClubResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FsjdaApp.class)
public class DojoClubResourceIntTest {

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
    private DojoClubRepository dojoClubRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDojoClubMockMvc;

    private DojoClub dojoClub;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DojoClubResource dojoClubResource = new DojoClubResource(dojoClubRepository);
        this.restDojoClubMockMvc = MockMvcBuilders.standaloneSetup(dojoClubResource)
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
    public static DojoClub createEntity(EntityManager em) {
        DojoClub dojoClub = new DojoClub()
            .name(DEFAULT_NAME)
            .president(DEFAULT_PRESIDENT)
            .dateCreation(DEFAULT_DATE_CREATION)
            .description(DEFAULT_DESCRIPTION)
            .adresse(DEFAULT_ADRESSE)
            .telephone(DEFAULT_TELEPHONE)
            .email(DEFAULT_EMAIL);
        // Add required entity
        Ligue ligue = LigueResourceIntTest.createEntity(em);
        em.persist(ligue);
        em.flush();
        dojoClub.setLigue(ligue);
        return dojoClub;
    }

    @Before
    public void initTest() {
        dojoClub = createEntity(em);
    }

    @Test
    @Transactional
    public void createDojoClub() throws Exception {
        int databaseSizeBeforeCreate = dojoClubRepository.findAll().size();

        // Create the DojoClub
        restDojoClubMockMvc.perform(post("/api/dojo-clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dojoClub)))
            .andExpect(status().isCreated());

        // Validate the DojoClub in the database
        List<DojoClub> dojoClubList = dojoClubRepository.findAll();
        assertThat(dojoClubList).hasSize(databaseSizeBeforeCreate + 1);
        DojoClub testDojoClub = dojoClubList.get(dojoClubList.size() - 1);
        assertThat(testDojoClub.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDojoClub.getPresident()).isEqualTo(DEFAULT_PRESIDENT);
        assertThat(testDojoClub.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testDojoClub.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDojoClub.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testDojoClub.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testDojoClub.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createDojoClubWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dojoClubRepository.findAll().size();

        // Create the DojoClub with an existing ID
        dojoClub.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDojoClubMockMvc.perform(post("/api/dojo-clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dojoClub)))
            .andExpect(status().isBadRequest());

        // Validate the DojoClub in the database
        List<DojoClub> dojoClubList = dojoClubRepository.findAll();
        assertThat(dojoClubList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = dojoClubRepository.findAll().size();
        // set the field null
        dojoClub.setName(null);

        // Create the DojoClub, which fails.

        restDojoClubMockMvc.perform(post("/api/dojo-clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dojoClub)))
            .andExpect(status().isBadRequest());

        List<DojoClub> dojoClubList = dojoClubRepository.findAll();
        assertThat(dojoClubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPresidentIsRequired() throws Exception {
        int databaseSizeBeforeTest = dojoClubRepository.findAll().size();
        // set the field null
        dojoClub.setPresident(null);

        // Create the DojoClub, which fails.

        restDojoClubMockMvc.perform(post("/api/dojo-clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dojoClub)))
            .andExpect(status().isBadRequest());

        List<DojoClub> dojoClubList = dojoClubRepository.findAll();
        assertThat(dojoClubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = dojoClubRepository.findAll().size();
        // set the field null
        dojoClub.setDescription(null);

        // Create the DojoClub, which fails.

        restDojoClubMockMvc.perform(post("/api/dojo-clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dojoClub)))
            .andExpect(status().isBadRequest());

        List<DojoClub> dojoClubList = dojoClubRepository.findAll();
        assertThat(dojoClubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresseIsRequired() throws Exception {
        int databaseSizeBeforeTest = dojoClubRepository.findAll().size();
        // set the field null
        dojoClub.setAdresse(null);

        // Create the DojoClub, which fails.

        restDojoClubMockMvc.perform(post("/api/dojo-clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dojoClub)))
            .andExpect(status().isBadRequest());

        List<DojoClub> dojoClubList = dojoClubRepository.findAll();
        assertThat(dojoClubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = dojoClubRepository.findAll().size();
        // set the field null
        dojoClub.setTelephone(null);

        // Create the DojoClub, which fails.

        restDojoClubMockMvc.perform(post("/api/dojo-clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dojoClub)))
            .andExpect(status().isBadRequest());

        List<DojoClub> dojoClubList = dojoClubRepository.findAll();
        assertThat(dojoClubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = dojoClubRepository.findAll().size();
        // set the field null
        dojoClub.setEmail(null);

        // Create the DojoClub, which fails.

        restDojoClubMockMvc.perform(post("/api/dojo-clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dojoClub)))
            .andExpect(status().isBadRequest());

        List<DojoClub> dojoClubList = dojoClubRepository.findAll();
        assertThat(dojoClubList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDojoClubs() throws Exception {
        // Initialize the database
        dojoClubRepository.saveAndFlush(dojoClub);

        // Get all the dojoClubList
        restDojoClubMockMvc.perform(get("/api/dojo-clubs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dojoClub.getId().intValue())))
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
    public void getDojoClub() throws Exception {
        // Initialize the database
        dojoClubRepository.saveAndFlush(dojoClub);

        // Get the dojoClub
        restDojoClubMockMvc.perform(get("/api/dojo-clubs/{id}", dojoClub.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dojoClub.getId().intValue()))
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
    public void getNonExistingDojoClub() throws Exception {
        // Get the dojoClub
        restDojoClubMockMvc.perform(get("/api/dojo-clubs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDojoClub() throws Exception {
        // Initialize the database
        dojoClubRepository.saveAndFlush(dojoClub);
        int databaseSizeBeforeUpdate = dojoClubRepository.findAll().size();

        // Update the dojoClub
        DojoClub updatedDojoClub = dojoClubRepository.findOne(dojoClub.getId());
        // Disconnect from session so that the updates on updatedDojoClub are not directly saved in db
        em.detach(updatedDojoClub);
        updatedDojoClub
            .name(UPDATED_NAME)
            .president(UPDATED_PRESIDENT)
            .dateCreation(UPDATED_DATE_CREATION)
            .description(UPDATED_DESCRIPTION)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL);

        restDojoClubMockMvc.perform(put("/api/dojo-clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDojoClub)))
            .andExpect(status().isOk());

        // Validate the DojoClub in the database
        List<DojoClub> dojoClubList = dojoClubRepository.findAll();
        assertThat(dojoClubList).hasSize(databaseSizeBeforeUpdate);
        DojoClub testDojoClub = dojoClubList.get(dojoClubList.size() - 1);
        assertThat(testDojoClub.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDojoClub.getPresident()).isEqualTo(UPDATED_PRESIDENT);
        assertThat(testDojoClub.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testDojoClub.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDojoClub.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testDojoClub.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testDojoClub.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingDojoClub() throws Exception {
        int databaseSizeBeforeUpdate = dojoClubRepository.findAll().size();

        // Create the DojoClub

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDojoClubMockMvc.perform(put("/api/dojo-clubs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dojoClub)))
            .andExpect(status().isCreated());

        // Validate the DojoClub in the database
        List<DojoClub> dojoClubList = dojoClubRepository.findAll();
        assertThat(dojoClubList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDojoClub() throws Exception {
        // Initialize the database
        dojoClubRepository.saveAndFlush(dojoClub);
        int databaseSizeBeforeDelete = dojoClubRepository.findAll().size();

        // Get the dojoClub
        restDojoClubMockMvc.perform(delete("/api/dojo-clubs/{id}", dojoClub.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DojoClub> dojoClubList = dojoClubRepository.findAll();
        assertThat(dojoClubList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DojoClub.class);
        DojoClub dojoClub1 = new DojoClub();
        dojoClub1.setId(1L);
        DojoClub dojoClub2 = new DojoClub();
        dojoClub2.setId(dojoClub1.getId());
        assertThat(dojoClub1).isEqualTo(dojoClub2);
        dojoClub2.setId(2L);
        assertThat(dojoClub1).isNotEqualTo(dojoClub2);
        dojoClub1.setId(null);
        assertThat(dojoClub1).isNotEqualTo(dojoClub2);
    }
}
