package io.github.jhipster.application.web.rest;

import io.github.jhipster.application.FsjdaApp;

import io.github.jhipster.application.domain.Licence;
import io.github.jhipster.application.domain.Athlete;
import io.github.jhipster.application.domain.TarifCeinture;
import io.github.jhipster.application.domain.Saison;
import io.github.jhipster.application.repository.LicenceRepository;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.github.jhipster.application.domain.enumeration.TypeLicence;
/**
 * Test class for the LicenceResource REST controller.
 *
 * @see LicenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FsjdaApp.class)
public class LicenceResourceIntTest {

    private static final TypeLicence DEFAULT_TYPE_LICENCE = TypeLicence.RENOUVELLEMENT;
    private static final TypeLicence UPDATED_TYPE_LICENCE = TypeLicence.NOUVELLE;

    private static final LocalDate DEFAULT_DATE_CREATION = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE_CREATION = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_PHOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_PHOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_PHOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_PHOTO_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_CERTIFICAT_MEDICAL = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_CERTIFICAT_MEDICAL = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_CERTIFICAT_MEDICAL_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_CERTIFICAT_MEDICAL_CONTENT_TYPE = "image/png";

    @Autowired
    private LicenceRepository licenceRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLicenceMockMvc;

    private Licence licence;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LicenceResource licenceResource = new LicenceResource(licenceRepository);
        this.restLicenceMockMvc = MockMvcBuilders.standaloneSetup(licenceResource)
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
    public static Licence createEntity(EntityManager em) {
        Licence licence = new Licence()
            .typeLicence(DEFAULT_TYPE_LICENCE)
            .dateCreation(DEFAULT_DATE_CREATION)
            .photo(DEFAULT_PHOTO)
            .photoContentType(DEFAULT_PHOTO_CONTENT_TYPE)
            .certificatMedical(DEFAULT_CERTIFICAT_MEDICAL)
            .certificatMedicalContentType(DEFAULT_CERTIFICAT_MEDICAL_CONTENT_TYPE);
        // Add required entity
        Athlete athlete = AthleteResourceIntTest.createEntity(em);
        em.persist(athlete);
        em.flush();
        licence.setAthlete(athlete);
        // Add required entity
        TarifCeinture tarifceinture = TarifCeintureResourceIntTest.createEntity(em);
        em.persist(tarifceinture);
        em.flush();
        licence.setTarifceinture(tarifceinture);
        // Add required entity
        Saison saison = SaisonResourceIntTest.createEntity(em);
        em.persist(saison);
        em.flush();
        licence.setSaison(saison);
        return licence;
    }

    @Before
    public void initTest() {
        licence = createEntity(em);
    }

    @Test
    @Transactional
    public void createLicence() throws Exception {
        int databaseSizeBeforeCreate = licenceRepository.findAll().size();

        // Create the Licence
        restLicenceMockMvc.perform(post("/api/licences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licence)))
            .andExpect(status().isCreated());

        // Validate the Licence in the database
        List<Licence> licenceList = licenceRepository.findAll();
        assertThat(licenceList).hasSize(databaseSizeBeforeCreate + 1);
        Licence testLicence = licenceList.get(licenceList.size() - 1);
        assertThat(testLicence.getTypeLicence()).isEqualTo(DEFAULT_TYPE_LICENCE);
        assertThat(testLicence.getDateCreation()).isEqualTo(DEFAULT_DATE_CREATION);
        assertThat(testLicence.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testLicence.getPhotoContentType()).isEqualTo(DEFAULT_PHOTO_CONTENT_TYPE);
        assertThat(testLicence.getCertificatMedical()).isEqualTo(DEFAULT_CERTIFICAT_MEDICAL);
        assertThat(testLicence.getCertificatMedicalContentType()).isEqualTo(DEFAULT_CERTIFICAT_MEDICAL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createLicenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = licenceRepository.findAll().size();

        // Create the Licence with an existing ID
        licence.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLicenceMockMvc.perform(post("/api/licences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licence)))
            .andExpect(status().isBadRequest());

        // Validate the Licence in the database
        List<Licence> licenceList = licenceRepository.findAll();
        assertThat(licenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeLicenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = licenceRepository.findAll().size();
        // set the field null
        licence.setTypeLicence(null);

        // Create the Licence, which fails.

        restLicenceMockMvc.perform(post("/api/licences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licence)))
            .andExpect(status().isBadRequest());

        List<Licence> licenceList = licenceRepository.findAll();
        assertThat(licenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhotoIsRequired() throws Exception {
        int databaseSizeBeforeTest = licenceRepository.findAll().size();
        // set the field null
        licence.setPhoto(null);

        // Create the Licence, which fails.

        restLicenceMockMvc.perform(post("/api/licences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licence)))
            .andExpect(status().isBadRequest());

        List<Licence> licenceList = licenceRepository.findAll();
        assertThat(licenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCertificatMedicalIsRequired() throws Exception {
        int databaseSizeBeforeTest = licenceRepository.findAll().size();
        // set the field null
        licence.setCertificatMedical(null);

        // Create the Licence, which fails.

        restLicenceMockMvc.perform(post("/api/licences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licence)))
            .andExpect(status().isBadRequest());

        List<Licence> licenceList = licenceRepository.findAll();
        assertThat(licenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLicences() throws Exception {
        // Initialize the database
        licenceRepository.saveAndFlush(licence);

        // Get all the licenceList
        restLicenceMockMvc.perform(get("/api/licences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(licence.getId().intValue())))
            .andExpect(jsonPath("$.[*].typeLicence").value(hasItem(DEFAULT_TYPE_LICENCE.toString())))
            .andExpect(jsonPath("$.[*].dateCreation").value(hasItem(DEFAULT_DATE_CREATION.toString())))
            .andExpect(jsonPath("$.[*].photoContentType").value(hasItem(DEFAULT_PHOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(Base64Utils.encodeToString(DEFAULT_PHOTO))))
            .andExpect(jsonPath("$.[*].certificatMedicalContentType").value(hasItem(DEFAULT_CERTIFICAT_MEDICAL_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].certificatMedical").value(hasItem(Base64Utils.encodeToString(DEFAULT_CERTIFICAT_MEDICAL))));
    }

    @Test
    @Transactional
    public void getLicence() throws Exception {
        // Initialize the database
        licenceRepository.saveAndFlush(licence);

        // Get the licence
        restLicenceMockMvc.perform(get("/api/licences/{id}", licence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(licence.getId().intValue()))
            .andExpect(jsonPath("$.typeLicence").value(DEFAULT_TYPE_LICENCE.toString()))
            .andExpect(jsonPath("$.dateCreation").value(DEFAULT_DATE_CREATION.toString()))
            .andExpect(jsonPath("$.photoContentType").value(DEFAULT_PHOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.photo").value(Base64Utils.encodeToString(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.certificatMedicalContentType").value(DEFAULT_CERTIFICAT_MEDICAL_CONTENT_TYPE))
            .andExpect(jsonPath("$.certificatMedical").value(Base64Utils.encodeToString(DEFAULT_CERTIFICAT_MEDICAL)));
    }

    @Test
    @Transactional
    public void getNonExistingLicence() throws Exception {
        // Get the licence
        restLicenceMockMvc.perform(get("/api/licences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLicence() throws Exception {
        // Initialize the database
        licenceRepository.saveAndFlush(licence);
        int databaseSizeBeforeUpdate = licenceRepository.findAll().size();

        // Update the licence
        Licence updatedLicence = licenceRepository.findOne(licence.getId());
        // Disconnect from session so that the updates on updatedLicence are not directly saved in db
        em.detach(updatedLicence);
        updatedLicence
            .typeLicence(UPDATED_TYPE_LICENCE)
            .dateCreation(UPDATED_DATE_CREATION)
            .photo(UPDATED_PHOTO)
            .photoContentType(UPDATED_PHOTO_CONTENT_TYPE)
            .certificatMedical(UPDATED_CERTIFICAT_MEDICAL)
            .certificatMedicalContentType(UPDATED_CERTIFICAT_MEDICAL_CONTENT_TYPE);

        restLicenceMockMvc.perform(put("/api/licences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLicence)))
            .andExpect(status().isOk());

        // Validate the Licence in the database
        List<Licence> licenceList = licenceRepository.findAll();
        assertThat(licenceList).hasSize(databaseSizeBeforeUpdate);
        Licence testLicence = licenceList.get(licenceList.size() - 1);
        assertThat(testLicence.getTypeLicence()).isEqualTo(UPDATED_TYPE_LICENCE);
        assertThat(testLicence.getDateCreation()).isEqualTo(UPDATED_DATE_CREATION);
        assertThat(testLicence.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testLicence.getPhotoContentType()).isEqualTo(UPDATED_PHOTO_CONTENT_TYPE);
        assertThat(testLicence.getCertificatMedical()).isEqualTo(UPDATED_CERTIFICAT_MEDICAL);
        assertThat(testLicence.getCertificatMedicalContentType()).isEqualTo(UPDATED_CERTIFICAT_MEDICAL_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingLicence() throws Exception {
        int databaseSizeBeforeUpdate = licenceRepository.findAll().size();

        // Create the Licence

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLicenceMockMvc.perform(put("/api/licences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(licence)))
            .andExpect(status().isCreated());

        // Validate the Licence in the database
        List<Licence> licenceList = licenceRepository.findAll();
        assertThat(licenceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLicence() throws Exception {
        // Initialize the database
        licenceRepository.saveAndFlush(licence);
        int databaseSizeBeforeDelete = licenceRepository.findAll().size();

        // Get the licence
        restLicenceMockMvc.perform(delete("/api/licences/{id}", licence.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Licence> licenceList = licenceRepository.findAll();
        assertThat(licenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Licence.class);
        Licence licence1 = new Licence();
        licence1.setId(1L);
        Licence licence2 = new Licence();
        licence2.setId(licence1.getId());
        assertThat(licence1).isEqualTo(licence2);
        licence2.setId(2L);
        assertThat(licence1).isNotEqualTo(licence2);
        licence1.setId(null);
        assertThat(licence1).isNotEqualTo(licence2);
    }
}
