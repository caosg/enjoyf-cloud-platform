package com.enjoyf.platform.contentservice.web.rest;

import com.enjoyf.platform.autoconfigure.web.error.ExceptionTranslator;
import com.enjoyf.platform.contentservice.ContentServiceApp;

import com.enjoyf.platform.contentservice.domain.Feedback;
import com.enjoyf.platform.contentservice.domain.enumeration.FeedbackType;
import com.enjoyf.platform.contentservice.repository.jpa.FeedbackRepository;
import com.enjoyf.platform.contentservice.service.FeedbackService;

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
import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FeedbackResource REST controller.
 *
 * @see FeedbackResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContentServiceApp.class)
public class FeedbackResourceIntTest {

    private static final Long DEFAULT_UID = 1L;
    private static final Long UPDATED_UID = 2L;

    private static final FeedbackType DEFAULT_REASON = FeedbackType.GAME;
    private static final FeedbackType UPDATED_REASON = FeedbackType.COMMENT;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.now();
    private static final ZonedDateTime UPDATED_CREATE_TIME =ZonedDateTime.now();

    private static final String DEFAULT_CREATE_IP = "AAAAAAAAAA";
    private static final String UPDATED_CREATE_IP = "BBBBBBBBBB";

    private static final String DEFAULT_APPKEY = "AAAAAAAAAA";
    private static final String UPDATED_APPKEY = "BBBBBBBBBB";

    private static final Long DEFAULT_DESTID = 1L;
    private static final Long UPDATED_DESTID = 2L;

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFeedbackMockMvc;

    private Feedback feedback;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FeedbackResource feedbackResource = new FeedbackResource(feedbackService);
        this.restFeedbackMockMvc = MockMvcBuilders.standaloneSetup(feedbackResource)
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
    public static Feedback createEntity(EntityManager em) {
        Feedback feedback = new Feedback()
            .uid(DEFAULT_UID)
            .description(DEFAULT_DESCRIPTION)
            .createTime(DEFAULT_CREATE_TIME)
            .create_ip(DEFAULT_CREATE_IP)
            .appkey(DEFAULT_APPKEY)
            .destid(DEFAULT_DESTID);
        return feedback;
    }

    @Before
    public void initTest() {
        feedback = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeedback() throws Exception {
        int databaseSizeBeforeCreate = feedbackRepository.findAll().size();

        // Create the Feedback
        restFeedbackMockMvc.perform(post("/api/feedbacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isCreated());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeCreate + 1);
        Feedback testFeedback = feedbackList.get(feedbackList.size() - 1);
        assertThat(testFeedback.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testFeedback.getFeedbackType()).isEqualTo(DEFAULT_REASON);
        assertThat(testFeedback.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFeedback.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
        assertThat(testFeedback.getCreate_ip()).isEqualTo(DEFAULT_CREATE_IP);
        assertThat(testFeedback.getAppkey()).isEqualTo(DEFAULT_APPKEY);
        assertThat(testFeedback.getDestid()).isEqualTo(DEFAULT_DESTID);
    }

    @Test
    @Transactional
    public void createFeedbackWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = feedbackRepository.findAll().size();

        // Create the Feedback with an existing ID
        feedback.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeedbackMockMvc.perform(post("/api/feedbacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUidIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setUid(null);

        // Create the Feedback, which fails.

        restFeedbackMockMvc.perform(post("/api/feedbacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isBadRequest());

        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkReasonIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.feedbackType(null);

        // Create the Feedback, which fails.

        restFeedbackMockMvc.perform(post("/api/feedbacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isBadRequest());

        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setDescription(null);

        // Create the Feedback, which fails.

        restFeedbackMockMvc.perform(post("/api/feedbacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isBadRequest());

        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCreateTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setCreateTime(null);

        // Create the Feedback, which fails.

        restFeedbackMockMvc.perform(post("/api/feedbacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isBadRequest());

        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAppkeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = feedbackRepository.findAll().size();
        // set the field null
        feedback.setAppkey(null);

        // Create the Feedback, which fails.

        restFeedbackMockMvc.perform(post("/api/feedbacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isBadRequest());

        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFeedbacks() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get all the feedbackList
        restFeedbackMockMvc.perform(get("/api/feedbacks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feedback.getId().intValue())))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.intValue())))
            .andExpect(jsonPath("$.[*].reason").value(hasItem(DEFAULT_REASON.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].createTime").value(hasItem(DEFAULT_CREATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].create_ip").value(hasItem(DEFAULT_CREATE_IP.toString())))
            .andExpect(jsonPath("$.[*].appkey").value(hasItem(DEFAULT_APPKEY.toString())))
            .andExpect(jsonPath("$.[*].destid").value(hasItem(DEFAULT_DESTID.intValue())));
    }

    @Test
    @Transactional
    public void getFeedback() throws Exception {
        // Initialize the database
        feedbackRepository.saveAndFlush(feedback);

        // Get the feedback
        restFeedbackMockMvc.perform(get("/api/feedbacks/{id}", feedback.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(feedback.getId().intValue()))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.intValue()))
            .andExpect(jsonPath("$.reason").value(DEFAULT_REASON.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.createTime").value(DEFAULT_CREATE_TIME.toString()))
            .andExpect(jsonPath("$.create_ip").value(DEFAULT_CREATE_IP.toString()))
            .andExpect(jsonPath("$.appkey").value(DEFAULT_APPKEY.toString()))
            .andExpect(jsonPath("$.destid").value(DEFAULT_DESTID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFeedback() throws Exception {
        // Get the feedback
        restFeedbackMockMvc.perform(get("/api/feedbacks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeedback() throws Exception {
        // Initialize the database
        feedbackService.save(feedback);

        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();

        // Update the feedback
        Feedback updatedFeedback = feedbackRepository.findOne(feedback.getId());
        updatedFeedback
            .uid(UPDATED_UID)
            .feedbackType(UPDATED_REASON)
            .description(UPDATED_DESCRIPTION)
            .createTime(UPDATED_CREATE_TIME)
            .create_ip(UPDATED_CREATE_IP)
            .appkey(UPDATED_APPKEY)
            .destid(UPDATED_DESTID);

        restFeedbackMockMvc.perform(put("/api/feedbacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFeedback)))
            .andExpect(status().isOk());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate);
        Feedback testFeedback = feedbackList.get(feedbackList.size() - 1);
        assertThat(testFeedback.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testFeedback.getFeedbackType()).isEqualTo(UPDATED_REASON);
        assertThat(testFeedback.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFeedback.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
        assertThat(testFeedback.getCreate_ip()).isEqualTo(UPDATED_CREATE_IP);
        assertThat(testFeedback.getAppkey()).isEqualTo(UPDATED_APPKEY);
        assertThat(testFeedback.getDestid()).isEqualTo(UPDATED_DESTID);
    }

    @Test
    @Transactional
    public void updateNonExistingFeedback() throws Exception {
        int databaseSizeBeforeUpdate = feedbackRepository.findAll().size();

        // Create the Feedback

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFeedbackMockMvc.perform(put("/api/feedbacks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(feedback)))
            .andExpect(status().isCreated());

        // Validate the Feedback in the database
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFeedback() throws Exception {
        // Initialize the database
        feedbackService.save(feedback);

        int databaseSizeBeforeDelete = feedbackRepository.findAll().size();

        // Get the feedback
        restFeedbackMockMvc.perform(delete("/api/feedbacks/{id}", feedback.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Feedback> feedbackList = feedbackRepository.findAll();
        assertThat(feedbackList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Feedback.class);
        Feedback feedback1 = new Feedback();
        feedback1.setId(1L);
        Feedback feedback2 = new Feedback();
        feedback2.setId(feedback1.getId());
        assertThat(feedback1).isEqualTo(feedback2);
        feedback2.setId(2L);
        assertThat(feedback1).isNotEqualTo(feedback2);
        feedback1.setId(null);
        assertThat(feedback1).isNotEqualTo(feedback2);
    }
}
