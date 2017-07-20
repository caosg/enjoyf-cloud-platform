package com.enjoyf.platform.messageservice.web.rest;

import com.enjoyf.platform.autoconfigure.web.error.ExceptionTranslator;
import com.enjoyf.platform.messageservice.MessageserviceApp;
import com.enjoyf.platform.messageservice.domain.AppUserMessage;
import com.enjoyf.platform.messageservice.domain.enumration.AppMessageType;
import com.enjoyf.platform.messageservice.repository.jpa.AppUserMessageRepository;
import com.enjoyf.platform.messageservice.service.AppUserMessageService;
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
 * Test class for the AppMessageResource REST controller.
 *
 * @see AppUserMessageResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessageserviceApp.class)
public class AppUserMessageResourceIntTest {

    private static final String DEFAULT_APPKEY = "AAAAAAAAAA";
    private static final String UPDATED_APPKEY = "BBBBBBBBBB";

    private static final AppMessageType DEFAULT_MESSAGE_TYPE = AppMessageType.replycomment;
    private static final AppMessageType UPDATED_MESSAGE_TYPE =  AppMessageType.replynews
        ;

    private static final Long DEFAULT_UID = 1L;
    private static final Long UPDATED_UID = 2L;

    private static final String DEFAULT_MESSAGE_BODY = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE_BODY = "BBBBBBBBBB";

    @Autowired
    private AppUserMessageRepository appUserMessageRepository;

    @Autowired
    private AppUserMessageService appUserMessageService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAppMessageMockMvc;

    private AppUserMessage appUserMessage;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AppUserMessageResource appMessageResource = new AppUserMessageResource(appUserMessageService);
        this.restAppMessageMockMvc = MockMvcBuilders.standaloneSetup(appMessageResource)
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
    public static AppUserMessage createEntity(EntityManager em) {
        AppUserMessage appUserMessage = new AppUserMessage()
            .appkey(DEFAULT_APPKEY)
            .messageType(DEFAULT_MESSAGE_TYPE)
            .uid(DEFAULT_UID)
            .messageBody(DEFAULT_MESSAGE_BODY);
        return appUserMessage;
    }

    @Before
    public void initTest() {
        appUserMessage = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppMessage() throws Exception {
        int databaseSizeBeforeCreate = appUserMessageRepository.findAll().size();

        // Create the AppUserMessage
        restAppMessageMockMvc.perform(post("/api/app-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appUserMessage)))
            .andExpect(status().isCreated());

        // Validate the AppUserMessage in the database
        List<AppUserMessage> appUserMessageList = appUserMessageRepository.findAll();
        assertThat(appUserMessageList).hasSize(databaseSizeBeforeCreate + 1);
        AppUserMessage testAppUserMessage = appUserMessageList.get(appUserMessageList.size() - 1);
        assertThat(testAppUserMessage.getAppkey()).isEqualTo(DEFAULT_APPKEY);
        assertThat(testAppUserMessage.getMessageType()).isEqualTo(DEFAULT_MESSAGE_TYPE);
        assertThat(testAppUserMessage.getUid()).isEqualTo(DEFAULT_UID);
        assertThat(testAppUserMessage.getMessageBody()).isEqualTo(DEFAULT_MESSAGE_BODY);
    }

    @Test
    @Transactional
    public void createAppMessageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appUserMessageRepository.findAll().size();

        // Create the AppUserMessage with an existing ID
        appUserMessage.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppMessageMockMvc.perform(post("/api/app-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appUserMessage)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<AppUserMessage> appUserMessageList = appUserMessageRepository.findAll();
        assertThat(appUserMessageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAppkeyIsRequired() throws Exception {
        int databaseSizeBeforeTest = appUserMessageRepository.findAll().size();
        // set the field null
        appUserMessage.setAppkey(null);

        // Create the AppUserMessage, which fails.

        restAppMessageMockMvc.perform(post("/api/app-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appUserMessage)))
            .andExpect(status().isBadRequest());

        List<AppUserMessage> appUserMessageList = appUserMessageRepository.findAll();
        assertThat(appUserMessageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMessageTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = appUserMessageRepository.findAll().size();
        // set the field null
        appUserMessage.setMessageType(null);

        // Create the AppUserMessage, which fails.

        restAppMessageMockMvc.perform(post("/api/app-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appUserMessage)))
            .andExpect(status().isBadRequest());

        List<AppUserMessage> appUserMessageList = appUserMessageRepository.findAll();
        assertThat(appUserMessageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUidIsRequired() throws Exception {
        int databaseSizeBeforeTest = appUserMessageRepository.findAll().size();
        // set the field null
        appUserMessage.setUid(null);

        // Create the AppUserMessage, which fails.

        restAppMessageMockMvc.perform(post("/api/app-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appUserMessage)))
            .andExpect(status().isBadRequest());

        List<AppUserMessage> appUserMessageList = appUserMessageRepository.findAll();
        assertThat(appUserMessageList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAppMessages() throws Exception {
        // Initialize the database
        appUserMessageRepository.saveAndFlush(appUserMessage);

        // Get all the appMessageList
        restAppMessageMockMvc.perform(get("/api/app-messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appUserMessage.getId().intValue())))
            .andExpect(jsonPath("$.[*].appkey").value(hasItem(DEFAULT_APPKEY.toString())))
            .andExpect(jsonPath("$.[*].messageType").value(hasItem(DEFAULT_MESSAGE_TYPE)))
            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.intValue())))
            .andExpect(jsonPath("$.[*].messageBody").value(hasItem(DEFAULT_MESSAGE_BODY.toString())));
    }

    @Test
    @Transactional
    public void getAppMessage() throws Exception {
        // Initialize the database
        appUserMessageRepository.saveAndFlush(appUserMessage);

        // Get the appUserMessage
        restAppMessageMockMvc.perform(get("/api/app-messages/{id}", appUserMessage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appUserMessage.getId().intValue()))
            .andExpect(jsonPath("$.appkey").value(DEFAULT_APPKEY.toString()))
            .andExpect(jsonPath("$.messageType").value(DEFAULT_MESSAGE_TYPE))
            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.intValue()))
            .andExpect(jsonPath("$.messageBody").value(DEFAULT_MESSAGE_BODY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAppMessage() throws Exception {
        // Get the appUserMessage
        restAppMessageMockMvc.perform(get("/api/app-messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppMessage() throws Exception {
        // Initialize the database
        appUserMessageService.save(appUserMessage);

        int databaseSizeBeforeUpdate = appUserMessageRepository.findAll().size();

        // Update the appUserMessage
        AppUserMessage updatedAppUserMessage = appUserMessageRepository.findOne(appUserMessage.getId());
        updatedAppUserMessage
            .appkey(UPDATED_APPKEY)
            .messageType(UPDATED_MESSAGE_TYPE)
            .uid(UPDATED_UID)
            .messageBody(UPDATED_MESSAGE_BODY);

        restAppMessageMockMvc.perform(put("/api/app-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAppUserMessage)))
            .andExpect(status().isOk());

        // Validate the AppUserMessage in the database
        List<AppUserMessage> appUserMessageList = appUserMessageRepository.findAll();
        assertThat(appUserMessageList).hasSize(databaseSizeBeforeUpdate);
        AppUserMessage testAppUserMessage = appUserMessageList.get(appUserMessageList.size() - 1);
        assertThat(testAppUserMessage.getAppkey()).isEqualTo(UPDATED_APPKEY);
        assertThat(testAppUserMessage.getMessageType()).isEqualTo(UPDATED_MESSAGE_TYPE);
        assertThat(testAppUserMessage.getUid()).isEqualTo(UPDATED_UID);
        assertThat(testAppUserMessage.getMessageBody()).isEqualTo(UPDATED_MESSAGE_BODY);
    }

    @Test
    @Transactional
    public void updateNonExistingAppMessage() throws Exception {
        int databaseSizeBeforeUpdate = appUserMessageRepository.findAll().size();

        // Create the AppUserMessage

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAppMessageMockMvc.perform(put("/api/app-messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appUserMessage)))
            .andExpect(status().isCreated());

        // Validate the AppUserMessage in the database
        List<AppUserMessage> appUserMessageList = appUserMessageRepository.findAll();
        assertThat(appUserMessageList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAppMessage() throws Exception {
        // Initialize the database
        appUserMessageService.save(appUserMessage);

        int databaseSizeBeforeDelete = appUserMessageRepository.findAll().size();

        // Get the appUserMessage
        restAppMessageMockMvc.perform(delete("/api/app-messages/{id}", appUserMessage.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AppUserMessage> appUserMessageList = appUserMessageRepository.findAll();
        assertThat(appUserMessageList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppUserMessage.class);
        AppUserMessage appUserMessage1 = new AppUserMessage();
        appUserMessage1.setId(1L);
        AppUserMessage appUserMessage2 = new AppUserMessage();
        appUserMessage2.setId(appUserMessage1.getId());
        assertThat(appUserMessage1).isEqualTo(appUserMessage2);
        appUserMessage2.setId(2L);
        assertThat(appUserMessage1).isNotEqualTo(appUserMessage2);
        appUserMessage1.setId(null);
        assertThat(appUserMessage1).isNotEqualTo(appUserMessage2);
    }
}
