package com.enjoyf.platform.profileservice.web.rest;

import com.enjoyf.platform.profileservice.ProfileserviceApp;

import com.enjoyf.platform.profileservice.domain.VertualProfile;
import com.enjoyf.platform.profileservice.repository.jpa.VertualProfileRepository;
import com.enjoyf.platform.profileservice.service.VertualProfileService;
import com.enjoyf.platform.profileservice.web.rest.errors.ExceptionTranslator;

import com.enjoyf.platform.profileservice.web.rest.vm.VertualProfileVM;
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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the VertualProfileResource REST controller.
 *
 * @see VertualProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProfileserviceApp.class)
public class VertualProfileResourceIntTest {

    private static final String DEFAULT_PROFILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_NICK = "AAAAAAAAAA";
    private static final String UPDATED_NICK = "BBBBBBBBBB";

    private static final String DEFAULT_DISCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DISCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_ICON = "AAAAAAAAAA";
    private static final String UPDATED_ICON = "BBBBBBBBBB";

    private static final int DEFAULT_SEX = 1;
    private static final int UPDATED_SEX = 0;

    @Autowired
    private VertualProfileRepository vertualProfileRepository;

    @Autowired
    private VertualProfileService vertualProfileService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restVertualProfileMockMvc;

    private VertualProfile vertualProfile;

    private VertualProfileVM userProfile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VertualProfileResource vertualProfileResource = new VertualProfileResource(vertualProfileService);
        this.restVertualProfileMockMvc = MockMvcBuilders.standaloneSetup(vertualProfileResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VertualProfile createEntity(EntityManager em) {
        VertualProfile vertualProfile = new VertualProfile()
            .nick(DEFAULT_NICK)
            .discription(DEFAULT_DISCRIPTION)
            .icon(DEFAULT_ICON)
            .sex(DEFAULT_SEX);
        return vertualProfile;
    }

    @Before
    public void initTest() {
        vertualProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createVertualProfile() throws Exception {
        int databaseSizeBeforeCreate = vertualProfileRepository.findAll().size();

        // Create the VertualProfile
        restVertualProfileMockMvc.perform(post("/api/vertual-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vertualProfile)))
            .andExpect(status().isCreated());

        // Validate the VertualProfile in the database
        List<VertualProfile> vertualProfileList = vertualProfileRepository.findAll();
        assertThat(vertualProfileList).hasSize(databaseSizeBeforeCreate + 1);
        VertualProfile testVertualProfile = vertualProfileList.get(vertualProfileList.size() - 1);
        assertThat(testVertualProfile.getNick()).isEqualTo(DEFAULT_NICK);
        assertThat(testVertualProfile.getDescription()).isEqualTo(DEFAULT_DISCRIPTION);
        assertThat(testVertualProfile.getIcon()).isEqualTo(DEFAULT_ICON);
        assertThat(testVertualProfile.getSex()).isEqualTo(DEFAULT_SEX);
    }

    @Test
    @Transactional
    public void createVertualProfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vertualProfileRepository.findAll().size();

        // Create the VertualProfile with an existing ID
        vertualProfile.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVertualProfileMockMvc.perform(post("/api/vertual-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vertualProfile)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<VertualProfile> vertualProfileList = vertualProfileRepository.findAll();
        assertThat(vertualProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProfileNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = vertualProfileRepository.findAll().size();
        // set the field null

        // Create the VertualProfile, which fails.

        restVertualProfileMockMvc.perform(post("/api/vertual-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vertualProfile)))
            .andExpect(status().isBadRequest());

        List<VertualProfile> vertualProfileList = vertualProfileRepository.findAll();
        assertThat(vertualProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNickIsRequired() throws Exception {
        int databaseSizeBeforeTest = vertualProfileRepository.findAll().size();
        // set the field null
        vertualProfile.setNick(null);

        // Create the VertualProfile, which fails.

        restVertualProfileMockMvc.perform(post("/api/vertual-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vertualProfile)))
            .andExpect(status().isBadRequest());

        List<VertualProfile> vertualProfileList = vertualProfileRepository.findAll();
        assertThat(vertualProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVertualProfiles() throws Exception {
        // Initialize the database
        vertualProfileRepository.saveAndFlush(vertualProfile);

        // Get all the vertualProfileList
        restVertualProfileMockMvc.perform(get("/api/vertual-profiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vertualProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].profileNo").value(hasItem(DEFAULT_PROFILE_NO.toString())))
            .andExpect(jsonPath("$.[*].nick").value(hasItem(DEFAULT_NICK.toString())))
            .andExpect(jsonPath("$.[*].discription").value(hasItem(DEFAULT_DISCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].icon").value(hasItem(DEFAULT_ICON.toString())))
            .andExpect(jsonPath("$.[*].sex").value(hasItem(DEFAULT_SEX)));
    }

    @Test
    @Transactional
    public void getVertualProfile() throws Exception {
        // Initialize the database
        vertualProfileRepository.saveAndFlush(vertualProfile);

        // Get the vertualProfile
        restVertualProfileMockMvc.perform(get("/api/vertual-profiles/{id}", vertualProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vertualProfile.getId().intValue()))
            .andExpect(jsonPath("$.profileNo").value(DEFAULT_PROFILE_NO.toString()))
            .andExpect(jsonPath("$.nick").value(DEFAULT_NICK.toString()))
            .andExpect(jsonPath("$.discription").value(DEFAULT_DISCRIPTION.toString()))
            .andExpect(jsonPath("$.icon").value(DEFAULT_ICON.toString()))
            .andExpect(jsonPath("$.sex").value(DEFAULT_SEX));
    }

    @Test
    @Transactional
    public void getNonExistingVertualProfile() throws Exception {
        // Get the vertualProfile
        restVertualProfileMockMvc.perform(get("/api/vertual-profiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVertualProfile() throws Exception {
        // Initialize the database
        vertualProfileService.createVertualProfile(userProfile);

        int databaseSizeBeforeUpdate = vertualProfileRepository.findAll().size();

        // Update the vertualProfile
        VertualProfile updatedVertualProfile = vertualProfileRepository.findOne(vertualProfile.getId());
        updatedVertualProfile
            .nick(UPDATED_NICK)
            .discription(UPDATED_DISCRIPTION)
            .icon(UPDATED_ICON)
            .sex(UPDATED_SEX);

        restVertualProfileMockMvc.perform(put("/api/vertual-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVertualProfile)))
            .andExpect(status().isOk());

        // Validate the VertualProfile in the database
        List<VertualProfile> vertualProfileList = vertualProfileRepository.findAll();
        assertThat(vertualProfileList).hasSize(databaseSizeBeforeUpdate);
        VertualProfile testVertualProfile = vertualProfileList.get(vertualProfileList.size() - 1);
        assertThat(testVertualProfile.getNick()).isEqualTo(UPDATED_NICK);
        assertThat(testVertualProfile.getDescription()).isEqualTo(UPDATED_DISCRIPTION);
        assertThat(testVertualProfile.getIcon()).isEqualTo(UPDATED_ICON);
        assertThat(testVertualProfile.getSex()).isEqualTo(UPDATED_SEX);
    }

    @Test
    @Transactional
    public void updateNonExistingVertualProfile() throws Exception {
        int databaseSizeBeforeUpdate = vertualProfileRepository.findAll().size();

        // Create the VertualProfile

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restVertualProfileMockMvc.perform(put("/api/vertual-profiles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vertualProfile)))
            .andExpect(status().isCreated());

        // Validate the VertualProfile in the database
        List<VertualProfile> vertualProfileList = vertualProfileRepository.findAll();
        assertThat(vertualProfileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteVertualProfile() throws Exception {
        // Initialize the database
        vertualProfileService.createVertualProfile(userProfile);

        int databaseSizeBeforeDelete = vertualProfileRepository.findAll().size();

        // Get the vertualProfile
        restVertualProfileMockMvc.perform(delete("/api/vertual-profiles/{id}", vertualProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VertualProfile> vertualProfileList = vertualProfileRepository.findAll();
        assertThat(vertualProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VertualProfile.class);
        VertualProfile vertualProfile1 = new VertualProfile();
        vertualProfile1.setId(1L);
        VertualProfile vertualProfile2 = new VertualProfile();
        vertualProfile2.setId(vertualProfile1.getId());
        assertThat(vertualProfile1).isEqualTo(vertualProfile2);
        vertualProfile2.setId(2L);
        assertThat(vertualProfile1).isNotEqualTo(vertualProfile2);
        vertualProfile1.setId(null);
        assertThat(vertualProfile1).isNotEqualTo(vertualProfile2);
    }
}
