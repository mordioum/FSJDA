package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.FsjdaApp;

import io.github.jhipster.application.domain.Athlete;
import io.github.jhipster.application.domain.DojoClub;
import io.github.jhipster.application.repository.AthleteRepository;
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

import io.github.jhipster.application.domain.enumeration.Sexe;
/**
 * Test class for the AthleteResource REST controller.
 *
 * @see AthleteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FsjdaApp.class)
public class AthleteResourceIntTest {

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final Sexe DEFAULT_SEXE = Sexe.M;
    private static final Sexe UPDATED_SEXE = Sexe.F;

    private static final LocalDate DEFAULT_DATE_NAISSANCE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_NAISSANCE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LIEU_NAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_LIEU_NAISSANCE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_TELEPHONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEPHONE = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private AthleteRepository athleteRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAthleteMockMvc;

    private Athlete athlete;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AthleteResource athleteResource = new AthleteResource(athleteRepository);
        this.restAthleteMockMvc = MockMvcBuilders.standaloneSetup(athleteResource)
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
    public static Athlete createEntity(EntityManager em) {
        Athlete athlete = new Athlete()
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .dateCreation(DEFAULT_DATE_CREATION)
            .sexe(DEFAULT_SEXE)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .lieuNaissance(DEFAULT_LIEU_NAISSANCE)
            .adresse(DEFAULT_ADRESSE)
            .telephone(DEFAULT_TELEPHONE)
            .email(DEFAULT_EMAIL);
        // Add required entity
        DojoClub dojoclub = DojoClubResourceIntTest.createEntity(em);
        em.persist(dojoclub);
        em.flush();
        athlete.setDojoclub(dojoclub);
        return athlete;
    }

    @Before
    public void initTest() {
        athlete = createEntity(em);
    }

    @Test
    @Transactional
    public void createAthlete() throws Exception {
        int databaseSizeBeforeCreate = athleteRepository.findAll().size();

        // Create the Athlete
        restAthleteMockMvc.perform(post("/api/athletes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athlete)))
            .andExpect(status().isCreated());

        // Validate the Athlete in the database
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeCreate + 1);
        Athlete testAthlete = athleteList.get(athleteList.size() - 1);
        assertThat(testAthlete.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testAthlete.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testAthlete.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testAthlete.getSexe()).isEqualTo(DEFAULT_SEXE);
        assertThat(testAthlete.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testAthlete.getLieuNaissance()).isEqualTo(DEFAULT_LIEU_NAISSANCE);
        assertThat(testAthlete.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testAthlete.getTelephone()).isEqualTo(DEFAULT_TELEPHONE);
        assertThat(testAthlete.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createAthleteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = athleteRepository.findAll().size();

        // Create the Athlete with an existing ID
        athlete.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAthleteMockMvc.perform(post("/api/athletes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athlete)))
            .andExpect(status().isBadRequest());

        // Validate the Athlete in the database
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNomIsRequired() throws Exception {
        int databaseSizeBeforeTest = athleteRepository.findAll().size();
        // set the field null
        athlete.setNom(null);

        // Create the Athlete, which fails.

        restAthleteMockMvc.perform(post("/api/athletes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athlete)))
            .andExpect(status().isBadRequest());

        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPrenomIsRequired() throws Exception {
        int databaseSizeBeforeTest = athleteRepository.findAll().size();
        // set the field null
        athlete.setPrenom(null);

        // Create the Athlete, which fails.

        restAthleteMockMvc.perform(post("/api/athletes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athlete)))
            .andExpect(status().isBadRequest());

        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexeIsRequired() throws Exception {
        int databaseSizeBeforeTest = athleteRepository.findAll().size();
        // set the field null
        athlete.setSexe(null);

        // Create the Athlete, which fails.

        restAthleteMockMvc.perform(post("/api/athletes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athlete)))
            .andExpect(status().isBadRequest());

        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = athleteRepository.findAll().size();
        // set the field null
        athlete.setDateNaissance(null);

        // Create the Athlete, which fails.

        restAthleteMockMvc.perform(post("/api/athletes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athlete)))
            .andExpect(status().isBadRequest());

        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLieuNaissanceIsRequired() throws Exception {
        int databaseSizeBeforeTest = athleteRepository.findAll().size();
        // set the field null
        athlete.setLieuNaissance(null);

        // Create the Athlete, which fails.

        restAthleteMockMvc.perform(post("/api/athletes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athlete)))
            .andExpect(status().isBadRequest());

        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdresseIsRequired() throws Exception {
        int databaseSizeBeforeTest = athleteRepository.findAll().size();
        // set the field null
        athlete.setAdresse(null);

        // Create the Athlete, which fails.

        restAthleteMockMvc.perform(post("/api/athletes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athlete)))
            .andExpect(status().isBadRequest());

        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTelephoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = athleteRepository.findAll().size();
        // set the field null
        athlete.setTelephone(null);

        // Create the Athlete, which fails.

        restAthleteMockMvc.perform(post("/api/athletes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athlete)))
            .andExpect(status().isBadRequest());

        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = athleteRepository.findAll().size();
        // set the field null
        athlete.setEmail(null);

        // Create the Athlete, which fails.

        restAthleteMockMvc.perform(post("/api/athletes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athlete)))
            .andExpect(status().isBadRequest());

        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAthletes() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get all the athleteList
        restAthleteMockMvc.perform(get("/api/athletes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(athlete.getId().intValue())))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].sexe").value(hasItem(DEFAULT_SEXE.toString())))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].lieuNaissance").value(hasItem(DEFAULT_LIEU_NAISSANCE.toString())))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE.toString())))
            .andExpect(jsonPath("$.[*].telephone").value(hasItem(DEFAULT_TELEPHONE.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())));
    }

    @Test
    @Transactional
    public void getAthlete() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);

        // Get the athlete
        restAthleteMockMvc.perform(get("/api/athletes/{id}", athlete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(athlete.getId().intValue()))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.sexe").value(DEFAULT_SEXE.toString()))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE.toString()))
            .andExpect(jsonPath("$.lieuNaissance").value(DEFAULT_LIEU_NAISSANCE.toString()))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE.toString()))
            .andExpect(jsonPath("$.telephone").value(DEFAULT_TELEPHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAthlete() throws Exception {
        // Get the athlete
        restAthleteMockMvc.perform(get("/api/athletes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAthlete() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);
        int databaseSizeBeforeUpdate = athleteRepository.findAll().size();

        // Update the athlete
        Athlete updatedAthlete = athleteRepository.findOne(athlete.getId());
        // Disconnect from session so that the updates on updatedAthlete are not directly saved in db
        em.detach(updatedAthlete);
        updatedAthlete
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .dateCreation(UPDATED_DATE_CREATION)
            .sexe(UPDATED_SEXE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .lieuNaissance(UPDATED_LIEU_NAISSANCE)
            .adresse(UPDATED_ADRESSE)
            .telephone(UPDATED_TELEPHONE)
            .email(UPDATED_EMAIL);

        restAthleteMockMvc.perform(put("/api/athletes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAthlete)))
            .andExpect(status().isOk());

        // Validate the Athlete in the database
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeUpdate);
        Athlete testAthlete = athleteList.get(athleteList.size() - 1);
        assertThat(testAthlete.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testAthlete.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testAthlete.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testAthlete.getSexe()).isEqualTo(UPDATED_SEXE);
        assertThat(testAthlete.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testAthlete.getLieuNaissance()).isEqualTo(UPDATED_LIEU_NAISSANCE);
        assertThat(testAthlete.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testAthlete.getTelephone()).isEqualTo(UPDATED_TELEPHONE);
        assertThat(testAthlete.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingAthlete() throws Exception {
        int databaseSizeBeforeUpdate = athleteRepository.findAll().size();

        // Create the Athlete

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAthleteMockMvc.perform(put("/api/athletes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(athlete)))
            .andExpect(status().isCreated());

        // Validate the Athlete in the database
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAthlete() throws Exception {
        // Initialize the database
        athleteRepository.saveAndFlush(athlete);
        int databaseSizeBeforeDelete = athleteRepository.findAll().size();

        // Get the athlete
        restAthleteMockMvc.perform(delete("/api/athletes/{id}", athlete.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Athlete> athleteList = athleteRepository.findAll();
        assertThat(athleteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Athlete.class);
        Athlete athlete1 = new Athlete();
        athlete1.setId(1L);
        Athlete athlete2 = new Athlete();
        athlete2.setId(athlete1.getId());
        assertThat(athlete1).isEqualTo(athlete2);
        athlete2.setId(2L);
        assertThat(athlete1).isNotEqualTo(athlete2);
        athlete1.setId(null);
        assertThat(athlete1).isNotEqualTo(athlete2);
    }
}
