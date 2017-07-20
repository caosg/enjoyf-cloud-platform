package com.enjoyf.platform.profileservice.web.rest;

import com.enjoyf.platform.profileservice.ProfileserviceApp;

import com.enjoyf.platform.profileservice.domain.VerifyProfile;
import com.enjoyf.platform.profileservice.domain.enumeration.VerifyProfileType;
import com.enjoyf.platform.profileservice.repository.jpa.VerifyProfileRepository;
import com.enjoyf.platform.profileservice.service.VerifyProfileService;
import com.enjoyf.platform.profileservice.service.dto.VerifyProfileDTO;
import com.enjoyf.platform.profileservice.service.mapper.VerifyProfileMapper;
import com.enjoyf.platform.profileservice.web.rest.errors.ExceptionTranslator;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.enjoyf.platform.profileservice.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TalentResource REST controller.
 *
 * @see VerifyProfileResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProfileserviceApp.class)
public class VerifyProfileResourceIntTest {

    private static final String DEFAULT_PROFILE_NO = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_NO = "BBBBBBBBBB";

    private static final String DEFAULT_VERIFY_INFO = "AAAAAAAAAA";
    private static final String UPDATED_VERIFY_INFO = "BBBBBBBBBB";

    private static final VerifyProfileType DEFAULT_VERIFY_TYPE = VerifyProfileType.VERIFY;
    private static final VerifyProfileType UPDATED_VERIFY_TYPE = VerifyProfileType.VERIFY;

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_CREATE_IP = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_IP = "BBBBBBBBBB";

    @Autowired
    private VerifyProfileRepository verifyProfileRepository;

    @Autowired
    private VerifyProfileMapper verifyProfileMapper;

    @Autowired
    private VerifyProfileService verifyProfileService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTalentMockMvc;

    private VerifyProfile verifyProfile;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VerifyProfileResource verifyProfileResource = new VerifyProfileResource(verifyProfileService);
        this.restTalentMockMvc = MockMvcBuilders.standaloneSetup(verifyProfileResource)
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
    public static VerifyProfile createEntity(EntityManager em) {
        VerifyProfile verifyProfile = new VerifyProfile()

            .verifyInfo(DEFAULT_VERIFY_INFO)
            .verifyType(DEFAULT_VERIFY_TYPE)
            .createTime(DEFAULT_CREATE_TIME)
            .createIp(DEFAULT_CREATE_IP);
        return verifyProfile;
    }

    @Before
    public void initTest() {
        verifyProfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createTalent() throws Exception {
        int databaseSizeBeforeCreate = verifyProfileRepository.findAll().size();

        // Create the VerifyProfile
        VerifyProfileDTO verifyProfileDTO = verifyProfileMapper.toVerifyProfileDTO(verifyProfile);
        restTalentMockMvc.perform(post("/api/talents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verifyProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the VerifyProfile in the database
        List<VerifyProfile> verifyProfileList = verifyProfileRepository.findAll();
        assertThat(verifyProfileList).hasSize(databaseSizeBeforeCreate + 1);
        VerifyProfile testVerifyProfile = verifyProfileList.get(verifyProfileList.size() - 1);
        assertThat(testVerifyProfile.getVerifyInfo()).isEqualTo(DEFAULT_VERIFY_INFO);
        assertThat(testVerifyProfile.getVerifyType()).isEqualTo(DEFAULT_VERIFY_TYPE);
        assertThat(testVerifyProfile.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testVerifyProfile.getCreateIp()).isEqualTo(DEFAULT_CREATE_IP);
    }

    @Test
    @Transactional
    public void createTalentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = verifyProfileRepository.findAll().size();

        // Create the VerifyProfile with an existing ID
        verifyProfile.setId(1L);
        VerifyProfileDTO verifyProfileDTO = verifyProfileMapper.toVerifyProfileDTO(verifyProfile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTalentMockMvc.perform(post("/api/talents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verifyProfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<VerifyProfile> verifyProfileList = verifyProfileRepository.findAll();
        assertThat(verifyProfileList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProfileNoIsRequired() throws Exception {
        int databaseSizeBeforeTest = verifyProfileRepository.findAll().size();
        // set the field null

        // Create the VerifyProfile, which fails.
        VerifyProfileDTO verifyProfileDTO = verifyProfileMapper.toVerifyProfileDTO(verifyProfile);

        restTalentMockMvc.perform(post("/api/talents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verifyProfileDTO)))
            .andExpect(status().isBadRequest());

        List<VerifyProfile> verifyProfileList = verifyProfileRepository.findAll();
        assertThat(verifyProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkVerifyTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = verifyProfileRepository.findAll().size();
        // set the field null
        verifyProfile.setVerifyType(null);

        // Create the VerifyProfile, which fails.
        VerifyProfileDTO verifyProfileDTO = verifyProfileMapper.toVerifyProfileDTO(verifyProfile);

        restTalentMockMvc.perform(post("/api/talents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verifyProfileDTO)))
            .andExpect(status().isBadRequest());

        List<VerifyProfile> verifyProfileList = verifyProfileRepository.findAll();
        assertThat(verifyProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = verifyProfileRepository.findAll().size();
        // set the field null
        verifyProfile.setCreateTime(null);

        // Create the VerifyProfile, which fails.
        VerifyProfileDTO verifyProfileDTO = verifyProfileMapper.toVerifyProfileDTO(verifyProfile);

        restTalentMockMvc.perform(post("/api/talents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verifyProfileDTO)))
            .andExpect(status().isBadRequest());

        List<VerifyProfile> verifyProfileList = verifyProfileRepository.findAll();
        assertThat(verifyProfileList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTalents() throws Exception {
        // Initialize the database
        verifyProfileRepository.saveAndFlush(verifyProfile);

        // Get all the talentList
        restTalentMockMvc.perform(get("/api/talents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(verifyProfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].profileNo").value(hasItem(DEFAULT_PROFILE_NO.toString())))
            .andExpect(jsonPath("$.[*].verifyInfo").value(hasItem(DEFAULT_VERIFY_INFO.toString())))
            .andExpect(jsonPath("$.[*].verifyType").value(hasItem(DEFAULT_VERIFY_TYPE)))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
            .andExpect(jsonPath("$.[*].createIp").value(hasItem(DEFAULT_CREATE_IP.toString())));
    }

    @Test
    @Transactional
    public void getTalent() throws Exception {
        // Initialize the database
        verifyProfileRepository.saveAndFlush(verifyProfile);

        // Get the verifyProfile
        restTalentMockMvc.perform(get("/api/talents/{id}", verifyProfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(verifyProfile.getId().intValue()))
            .andExpect(jsonPath("$.profileNo").value(DEFAULT_PROFILE_NO.toString()))
            .andExpect(jsonPath("$.verifyInfo").value(DEFAULT_VERIFY_INFO.toString()))
            .andExpect(jsonPath("$.verifyType").value(DEFAULT_VERIFY_TYPE))
            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
            .andExpect(jsonPath("$.createIp").value(DEFAULT_CREATE_IP.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTalent() throws Exception {
        // Get the verifyProfile
        restTalentMockMvc.perform(get("/api/talents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTalent() throws Exception {
        // Initialize the database
        verifyProfileRepository.saveAndFlush(verifyProfile);
        int databaseSizeBeforeUpdate = verifyProfileRepository.findAll().size();

        // Update the verifyProfile
        VerifyProfile updatedVerifyProfile = verifyProfileRepository.findOne(verifyProfile.getId());
        updatedVerifyProfile
            .verifyInfo(UPDATED_VERIFY_INFO)
            .verifyType(UPDATED_VERIFY_TYPE)
            .createTime(UPDATED_CREATE_TIME)
            .createIp(UPDATED_CREATE_IP);
        VerifyProfileDTO verifyProfileDTO = verifyProfileMapper.toVerifyProfileDTO(updatedVerifyProfile);

        restTalentMockMvc.perform(put("/api/talents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verifyProfileDTO)))
            .andExpect(status().isOk());

        // Validate the VerifyProfile in the database
        List<VerifyProfile> verifyProfileList = verifyProfileRepository.findAll();
        assertThat(verifyProfileList).hasSize(databaseSizeBeforeUpdate);
        VerifyProfile testVerifyProfile = verifyProfileList.get(verifyProfileList.size() - 1);
        assertThat(testVerifyProfile.getVerifyInfo()).isEqualTo(UPDATED_VERIFY_INFO);
        assertThat(testVerifyProfile.getVerifyType()).isEqualTo(UPDATED_VERIFY_TYPE);
        assertThat(testVerifyProfile.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testVerifyProfile.getCreateIp()).isEqualTo(UPDATED_CREATE_IP);
    }

    @Test
    @Transactional
    public void updateNonExistingTalent() throws Exception {
        int databaseSizeBeforeUpdate = verifyProfileRepository.findAll().size();

        // Create the VerifyProfile
        VerifyProfileDTO verifyProfileDTO = verifyProfileMapper.toVerifyProfileDTO(verifyProfile);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTalentMockMvc.perform(put("/api/talents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(verifyProfileDTO)))
            .andExpect(status().isCreated());

        // Validate the VerifyProfile in the database
        List<VerifyProfile> verifyProfileList = verifyProfileRepository.findAll();
        assertThat(verifyProfileList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTalent() throws Exception {
        // Initialize the database
        verifyProfileRepository.saveAndFlush(verifyProfile);
        int databaseSizeBeforeDelete = verifyProfileRepository.findAll().size();

        // Get the verifyProfile
        restTalentMockMvc.perform(delete("/api/talents/{id}", verifyProfile.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<VerifyProfile> verifyProfileList = verifyProfileRepository.findAll();
        assertThat(verifyProfileList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VerifyProfile.class);
        VerifyProfile verifyProfile1 = new VerifyProfile();
        verifyProfile1.setId(1L);
        VerifyProfile verifyProfile2 = new VerifyProfile();
        verifyProfile2.setId(verifyProfile1.getId());
        assertThat(verifyProfile1).isEqualTo(verifyProfile2);
        verifyProfile2.setId(2L);
        assertThat(verifyProfile1).isNotEqualTo(verifyProfile2);
        verifyProfile1.setId(null);
        assertThat(verifyProfile1).isNotEqualTo(verifyProfile2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VerifyProfileDTO.class);
        VerifyProfileDTO verifyProfileDTO1 = new VerifyProfileDTO();
        verifyProfileDTO1.setId(1L);
        VerifyProfileDTO verifyProfileDTO2 = new VerifyProfileDTO();
        assertThat(verifyProfileDTO1).isNotEqualTo(verifyProfileDTO2);
        verifyProfileDTO2.setId(verifyProfileDTO1.getId());
        assertThat(verifyProfileDTO1).isEqualTo(verifyProfileDTO2);
        verifyProfileDTO2.setId(2L);
        assertThat(verifyProfileDTO1).isNotEqualTo(verifyProfileDTO2);
        verifyProfileDTO1.setId(null);
        assertThat(verifyProfileDTO1).isNotEqualTo(verifyProfileDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(verifyProfileMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(verifyProfileMapper.fromId(null)).isNull();
    }
}
