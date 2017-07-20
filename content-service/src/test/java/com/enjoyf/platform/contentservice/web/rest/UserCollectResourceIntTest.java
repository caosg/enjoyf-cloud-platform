package com.enjoyf.platform.contentservice.web.rest;

import com.enjoyf.platform.autoconfigure.web.error.ExceptionTranslator;
import com.enjoyf.platform.contentservice.ContentServiceApp;

import com.enjoyf.platform.contentservice.domain.UserCollect;
import com.enjoyf.platform.contentservice.repository.jpa.UserCollectRepository;
import com.enjoyf.platform.contentservice.service.UserCollectService;

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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the UserCollectResource REST controller.
 *
 * @see UserCollectResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContentServiceApp.class)
public class UserCollectResourceIntTest {

    private static final String DEFAULT_PROFILE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_COLLECT_TYPE = 1;
    private static final Integer UPDATED_COLLECT_TYPE = 2;

    private static final String DEFAULT_APPKEY = "AAAAAAAAAA";
    private static final String UPDATED_APPKEY = "BBBBBBBBBB";

    private static final Date DEFAULT_CREATE_TIME = new Date();
    private static final Date UPDATED_CREATE_TIME =new  Date();

    private static final Long DEFAULT_CONTENT_ID = 1L;
    private static final Long UPDATED_CONTENT_ID = 2L;

    @Autowired
    private UserCollectRepository userCollectRepository;

    @Autowired
    private UserCollectService userCollectService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserCollectMockMvc;

    private UserCollect userCollect;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
//        UserCollectResource userCollectResource = new UserCollectResource(userCollectService);
//        this.restUserCollectMockMvc = MockMvcBuilders.standaloneSetup(userCollectResource)
//            .setCustomArgumentResolvers(pageableArgumentResolver)
//            .setControllerAdvice(exceptionTranslator)
//            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserCollect createEntity(EntityManager em) {
        UserCollect userCollect = new UserCollect()
            .profileId(DEFAULT_PROFILE_ID)
            .collectType(DEFAULT_COLLECT_TYPE)
            .appkey(DEFAULT_APPKEY)
            .createTime(DEFAULT_CREATE_TIME)
            .contentId(DEFAULT_CONTENT_ID);
        return userCollect;
    }

    @Before
    public void initTest() {
        userCollect = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserCollect() throws Exception {
        int databaseSizeBeforeCreate = userCollectRepository.findAll().size();

        // Create the UserCollect
        restUserCollectMockMvc.perform(post("/api/user-collects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userCollect)))
            .andExpect(status().isCreated());

        // Validate the UserCollect in the database
        List<UserCollect> userCollectList = userCollectRepository.findAll();
        assertThat(userCollectList).hasSize(databaseSizeBeforeCreate + 1);
        UserCollect testUserCollect = userCollectList.get(userCollectList.size() - 1);
        assertThat(testUserCollect.getProfileId()).isEqualTo(DEFAULT_PROFILE_ID);
        assertThat(testUserCollect.getCollectType()).isEqualTo(DEFAULT_COLLECT_TYPE);
        assertThat(testUserCollect.getAppkey()).isEqualTo(DEFAULT_APPKEY);
        assertThat(testUserCollect.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testUserCollect.getContentId()).isEqualTo(DEFAULT_CONTENT_ID);
    }

    @Test
    @Transactional
    public void createUserCollectWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userCollectRepository.findAll().size();

        // Create the UserCollect with an existing ID
        userCollect.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserCollectMockMvc.perform(post("/api/user-collects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userCollect)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<UserCollect> userCollectList = userCollectRepository.findAll();
        assertThat(userCollectList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserCollects() throws Exception {
        // Initialize the database
        userCollectRepository.saveAndFlush(userCollect);

        // Get all the userCollectList
        restUserCollectMockMvc.perform(get("/api/user-collects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userCollect.getId().intValue())))
            .andExpect(jsonPath("$.[*].profileId").value(hasItem(DEFAULT_PROFILE_ID.toString())))
            .andExpect(jsonPath("$.[*].collectType").value(hasItem(DEFAULT_COLLECT_TYPE)))
            .andExpect(jsonPath("$.[*].appkey").value(hasItem(DEFAULT_APPKEY.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem((DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].contentId").value(hasItem(DEFAULT_CONTENT_ID.intValue())));
    }

    @Test
    @Transactional
    public void getUserCollect() throws Exception {
        // Initialize the database
        userCollectRepository.saveAndFlush(userCollect);

        // Get the userCollect
        restUserCollectMockMvc.perform(get("/api/user-collects/{id}", userCollect.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userCollect.getId().intValue()))
            .andExpect(jsonPath("$.profileId").value(DEFAULT_PROFILE_ID.toString()))
            .andExpect(jsonPath("$.collectType").value(DEFAULT_COLLECT_TYPE))
            .andExpect(jsonPath("$.appkey").value(DEFAULT_APPKEY.toString()))
            .andExpect(jsonPath("$.createTime").value((DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.contentId").value(DEFAULT_CONTENT_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserCollect() throws Exception {
        // Get the userCollect
        restUserCollectMockMvc.perform(get("/api/user-collects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserCollect() throws Exception {
        // Initialize the database
        userCollectService.save(userCollect);

        int databaseSizeBeforeUpdate = userCollectRepository.findAll().size();

        // Update the userCollect
        UserCollect updatedUserCollect = userCollectRepository.findOne(userCollect.getId());
        updatedUserCollect
            .profileId(UPDATED_PROFILE_ID)
            .collectType(UPDATED_COLLECT_TYPE)
            .appkey(UPDATED_APPKEY)
            .createTime(UPDATED_CREATE_TIME)
            .contentId(UPDATED_CONTENT_ID);

        restUserCollectMockMvc.perform(put("/api/user-collects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserCollect)))
            .andExpect(status().isOk());

        // Validate the UserCollect in the database
        List<UserCollect> userCollectList = userCollectRepository.findAll();
        assertThat(userCollectList).hasSize(databaseSizeBeforeUpdate);
        UserCollect testUserCollect = userCollectList.get(userCollectList.size() - 1);
        assertThat(testUserCollect.getProfileId()).isEqualTo(UPDATED_PROFILE_ID);
        assertThat(testUserCollect.getCollectType()).isEqualTo(UPDATED_COLLECT_TYPE);
        assertThat(testUserCollect.getAppkey()).isEqualTo(UPDATED_APPKEY);
        assertThat(testUserCollect.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testUserCollect.getContentId()).isEqualTo(UPDATED_CONTENT_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUserCollect() throws Exception {
        int databaseSizeBeforeUpdate = userCollectRepository.findAll().size();

        // Create the UserCollect

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserCollectMockMvc.perform(put("/api/user-collects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userCollect)))
            .andExpect(status().isCreated());

        // Validate the UserCollect in the database
        List<UserCollect> userCollectList = userCollectRepository.findAll();
        assertThat(userCollectList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserCollect() throws Exception {
        // Initialize the database
        userCollectService.save(userCollect);

        int databaseSizeBeforeDelete = userCollectRepository.findAll().size();

        // Get the userCollect
        restUserCollectMockMvc.perform(delete("/api/user-collects/{id}", userCollect.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserCollect> userCollectList = userCollectRepository.findAll();
        assertThat(userCollectList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserCollect.class);
        UserCollect userCollect1 = new UserCollect();
        userCollect1.setId(1L);
        UserCollect userCollect2 = new UserCollect();
        userCollect2.setId(userCollect1.getId());
        assertThat(userCollect1).isEqualTo(userCollect2);
        userCollect2.setId(2L);
        assertThat(userCollect1).isNotEqualTo(userCollect2);
        userCollect1.setId(null);
        assertThat(userCollect1).isNotEqualTo(userCollect2);
    }
}
