package com.enjoyf.platform.userservice.web.rest;

import com.enjoyf.platform.autoconfigure.web.error.ExceptionTranslator;
import com.enjoyf.platform.userservice.UserServiceApp;

import com.enjoyf.platform.userservice.domain.AppConf;
import com.enjoyf.platform.userservice.repository.AppConfRepository;

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

import com.enjoyf.platform.userservice.domain.enumeration.AppType;
import com.enjoyf.platform.userservice.domain.enumeration.ValidStatus;
import com.enjoyf.platform.userservice.domain.enumeration.ProfileKey;
/**
 * Test class for the AppConfResource REST controller.
 *
 * @see AppConfResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserServiceApp.class)
public class AppConfResourceIntTest {

    private static final String DEFAULT_APP_KEY = "AAAAAAAAAA";
    private static final String UPDATED_APP_KEY = "BBBBBBBBBB";

    private static final AppType DEFAULT_APP_TYPE = AppType.CLIENT;
    private static final AppType UPDATED_APP_TYPE = AppType.GAME;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ValidStatus DEFAULT_STATUS = ValidStatus.VALID;
    private static final ValidStatus UPDATED_STATUS = ValidStatus.INVALID;

    private static final ProfileKey DEFAULT_PROFILE_KEY = ProfileKey.WWW;
    private static final ProfileKey UPDATED_PROFILE_KEY = ProfileKey.WWW;

    private static final String DEFAULT_APP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_APP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONFIG = "AAAAAAAAAA";
    private static final String UPDATED_CONFIG = "BBBBBBBBBB";

    @Autowired
    private AppConfRepository appConfRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAppConfMockMvc;

    private AppConf appConf;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AppConfResource appConfResource = new AppConfResource(appConfRepository);
        this.restAppConfMockMvc = MockMvcBuilders.standaloneSetup(appConfResource)
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
    public static AppConf createEntity(EntityManager em) {
        AppConf appConf = new AppConf()
            .appKey(DEFAULT_APP_KEY)
            .appType(DEFAULT_APP_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .profileKey(DEFAULT_PROFILE_KEY)
            .appName(DEFAULT_APP_NAME)
            .config(DEFAULT_CONFIG);
        return appConf;
    }

    @Before
    public void initTest() {
        appConf = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppConf() throws Exception {
        int databaseSizeBeforeCreate = appConfRepository.findAll().size();

        // Create the AppConf
        restAppConfMockMvc.perform(post("/api/app-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appConf)))
            .andExpect(status().isCreated());

        // Validate the AppConf in the database
        List<AppConf> appConfList = appConfRepository.findAll();
        assertThat(appConfList).hasSize(databaseSizeBeforeCreate + 1);
        AppConf testAppConf = appConfList.get(appConfList.size() - 1);
        assertThat(testAppConf.getAppKey()).isEqualTo(DEFAULT_APP_KEY);
        assertThat(testAppConf.getAppType()).isEqualTo(DEFAULT_APP_TYPE);
        assertThat(testAppConf.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAppConf.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAppConf.getProfileKey()).isEqualTo(DEFAULT_PROFILE_KEY);
        assertThat(testAppConf.getAppName()).isEqualTo(DEFAULT_APP_NAME);
        assertThat(testAppConf.getConfig()).isEqualTo(DEFAULT_CONFIG);
    }

    @Test
    @Transactional
    public void createAppConfWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appConfRepository.findAll().size();

        // Create the AppConf with an existing ID
        appConf.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppConfMockMvc.perform(post("/api/app-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appConf)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AppConf> appConfList = appConfRepository.findAll();
        assertThat(appConfList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAppKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfRepository.findAll().size();
        // set the field null
        appConf.setAppKey(null);

        // Create the AppConf, which fails.

        restAppConfMockMvc.perform(post("/api/app-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appConf)))
            .andExpect(status().isBadRequest());

        List<AppConf> appConfList = appConfRepository.findAll();
        assertThat(appConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProfileKeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfRepository.findAll().size();
        // set the field null
        appConf.setProfileKey(null);

        // Create the AppConf, which fails.

        restAppConfMockMvc.perform(post("/api/app-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appConf)))
            .andExpect(status().isBadRequest());

        List<AppConf> appConfList = appConfRepository.findAll();
        assertThat(appConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAppNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = appConfRepository.findAll().size();
        // set the field null
        appConf.setAppName(null);

        // Create the AppConf, which fails.

        restAppConfMockMvc.perform(post("/api/app-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appConf)))
            .andExpect(status().isBadRequest());

        List<AppConf> appConfList = appConfRepository.findAll();
        assertThat(appConfList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAppConfs() throws Exception {
        // Initialize the database
        appConfRepository.saveAndFlush(appConf);

        // Get all the appConfList
        restAppConfMockMvc.perform(get("/api/app-confs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appConf.getId().intValue())))
            .andExpect(jsonPath("$.[*].appKey").value(hasItem(DEFAULT_APP_KEY.toString())))
            .andExpect(jsonPath("$.[*].appType").value(hasItem(DEFAULT_APP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].profileKey").value(hasItem(DEFAULT_PROFILE_KEY.toString())))
            .andExpect(jsonPath("$.[*].appName").value(hasItem(DEFAULT_APP_NAME.toString())))
            .andExpect(jsonPath("$.[*].config").value(hasItem(DEFAULT_CONFIG.toString())));
    }

    @Test
    @Transactional
    public void getAppConf() throws Exception {
        // Initialize the database
        appConfRepository.saveAndFlush(appConf);

        // Get the appConf
        restAppConfMockMvc.perform(get("/api/app-confs/{id}", appConf.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appConf.getId().intValue()))
            .andExpect(jsonPath("$.appKey").value(DEFAULT_APP_KEY.toString()))
            .andExpect(jsonPath("$.appType").value(DEFAULT_APP_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.profileKey").value(DEFAULT_PROFILE_KEY.toString()))
            .andExpect(jsonPath("$.appName").value(DEFAULT_APP_NAME.toString()))
            .andExpect(jsonPath("$.config").value(DEFAULT_CONFIG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAppConf() throws Exception {
        // Get the appConf
        restAppConfMockMvc.perform(get("/api/app-confs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppConf() throws Exception {
        // Initialize the database
        appConfRepository.saveAndFlush(appConf);
        int databaseSizeBeforeUpdate = appConfRepository.findAll().size();

        // Update the appConf
        AppConf updatedAppConf = appConfRepository.findOne(appConf.getId());
        updatedAppConf
            .appKey(UPDATED_APP_KEY)
            .appType(UPDATED_APP_TYPE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .profileKey(UPDATED_PROFILE_KEY)
            .appName(UPDATED_APP_NAME)
            .config(UPDATED_CONFIG);

        restAppConfMockMvc.perform(put("/api/app-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAppConf)))
            .andExpect(status().isOk());

        // Validate the AppConf in the database
        List<AppConf> appConfList = appConfRepository.findAll();
        assertThat(appConfList).hasSize(databaseSizeBeforeUpdate);
        AppConf testAppConf = appConfList.get(appConfList.size() - 1);
        assertThat(testAppConf.getAppKey()).isEqualTo(UPDATED_APP_KEY);
        assertThat(testAppConf.getAppType()).isEqualTo(UPDATED_APP_TYPE);
        assertThat(testAppConf.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAppConf.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAppConf.getProfileKey()).isEqualTo(UPDATED_PROFILE_KEY);
        assertThat(testAppConf.getAppName()).isEqualTo(UPDATED_APP_NAME);
        assertThat(testAppConf.getConfig()).isEqualTo(UPDATED_CONFIG);
    }

    @Test
    @Transactional
    public void updateNonExistingAppConf() throws Exception {
        int databaseSizeBeforeUpdate = appConfRepository.findAll().size();

        // Create the AppConf

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAppConfMockMvc.perform(put("/api/app-confs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appConf)))
            .andExpect(status().isCreated());

        // Validate the AppConf in the database
        List<AppConf> appConfList = appConfRepository.findAll();
        assertThat(appConfList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAppConf() throws Exception {
        // Initialize the database
        appConfRepository.saveAndFlush(appConf);
        int databaseSizeBeforeDelete = appConfRepository.findAll().size();

        // Get the appConf
        restAppConfMockMvc.perform(delete("/api/app-confs/{id}", appConf.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AppConf> appConfList = appConfRepository.findAll();
        assertThat(appConfList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppConf.class);
        AppConf appConf1 = new AppConf();
        appConf1.setId(1L);
        AppConf appConf2 = new AppConf();
        appConf2.setId(appConf1.getId());
        assertThat(appConf1).isEqualTo(appConf2);
        appConf2.setId(2L);
        assertThat(appConf1).isNotEqualTo(appConf2);
        appConf1.setId(null);
        assertThat(appConf1).isNotEqualTo(appConf2);
    }
}
