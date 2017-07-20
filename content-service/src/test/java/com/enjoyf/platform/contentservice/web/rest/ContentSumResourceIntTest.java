package com.enjoyf.platform.contentservice.web.rest;

import com.enjoyf.platform.autoconfigure.web.error.ExceptionTranslator;
import com.enjoyf.platform.contentservice.ContentServiceApp;

import com.enjoyf.platform.contentservice.domain.contentsum.ContentSum;
import com.enjoyf.platform.contentservice.repository.jpa.ContentSumRepository;
import com.enjoyf.platform.contentservice.service.ContentService;
import com.enjoyf.platform.contentservice.service.ContentSumService;

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
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ContentSumResource REST controller.
 *
 * @see ContentSumResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContentServiceApp.class)
public class ContentSumResourceIntTest {

    private static final Integer DEFAULT_AGREE_NUM = 1;
    private static final Integer UPDATED_AGREE_NUM = 2;

    private static final Instant DEFAULT_CREATE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private ContentSumRepository contentSumRepository;

    @Autowired
    private ContentSumService contentSumService;

    @Autowired
    private ContentService contentService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContentSumMockMvc;

    private ContentSum contentSum;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContentSumResource contentSumResource = new ContentSumResource(contentSumService,contentService);
        this.restContentSumMockMvc = MockMvcBuilders.standaloneSetup(contentSumResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     * <p>
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContentSum createEntity(EntityManager em) {
        ContentSum contentSum = new ContentSum()
            .agree_num(DEFAULT_AGREE_NUM);
        //.createDate(DEFAULT_CREATE_DATE);
        return contentSum;
    }

    @Before
    public void initTest() {
        contentSum = createEntity(em);
    }

    @Test
    @Transactional
    public void createContentSum() throws Exception {
        int databaseSizeBeforeCreate = contentSumRepository.findAll().size();

        // Create the ContentSum
        restContentSumMockMvc.perform(post("/api/content-sums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentSum)))
            .andExpect(status().isCreated());

        // Validate the ContentSum in the database
        List<ContentSum> contentSumList = contentSumRepository.findAll();
        assertThat(contentSumList).hasSize(databaseSizeBeforeCreate + 1);
        ContentSum testContentSum = contentSumList.get(contentSumList.size() - 1);
        assertThat(testContentSum.getAgree_num()).isEqualTo(DEFAULT_AGREE_NUM);
        assertThat(testContentSum.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createContentSumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contentSumRepository.findAll().size();

        // Create the ContentSum with an existing ID
        contentSum.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentSumMockMvc.perform(post("/api/content-sums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentSum)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ContentSum> contentSumList = contentSumRepository.findAll();
        assertThat(contentSumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContentSums() throws Exception {
        // Initialize the database
        contentSumRepository.saveAndFlush(contentSum);

        // Get all the contentSumList
        restContentSumMockMvc.perform(get("/api/content-sums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentSum.getId().intValue())))
            .andExpect(jsonPath("$.[*].agree_num").value(hasItem(DEFAULT_AGREE_NUM)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())));
    }

    @Test
    @Transactional
    public void getContentSum() throws Exception {
        // Initialize the database
        contentSumRepository.saveAndFlush(contentSum);

        // Get the contentSum
        restContentSumMockMvc.perform(get("/api/content-sums/{id}", contentSum.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contentSum.getId().intValue()))
            .andExpect(jsonPath("$.agree_num").value(DEFAULT_AGREE_NUM))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContentSum() throws Exception {
        // Get the contentSum
        restContentSumMockMvc.perform(get("/api/content-sums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContentSum() throws Exception {
        // Initialize the database
        contentSumService.save(contentSum);

        int databaseSizeBeforeUpdate = contentSumRepository.findAll().size();

        // Update the contentSum
        ContentSum updatedContentSum = contentSumRepository.findOne(contentSum.getId());
        updatedContentSum
            .agree_num(UPDATED_AGREE_NUM);
        //   .createDate(UPDATED_CREATE_DATE);

        restContentSumMockMvc.perform(put("/api/content-sums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContentSum)))
            .andExpect(status().isOk());

        // Validate the ContentSum in the database
        List<ContentSum> contentSumList = contentSumRepository.findAll();
        assertThat(contentSumList).hasSize(databaseSizeBeforeUpdate);
        ContentSum testContentSum = contentSumList.get(contentSumList.size() - 1);
        assertThat(testContentSum.getAgree_num()).isEqualTo(UPDATED_AGREE_NUM);
        assertThat(testContentSum.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingContentSum() throws Exception {
        int databaseSizeBeforeUpdate = contentSumRepository.findAll().size();

        // Create the ContentSum

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContentSumMockMvc.perform(put("/api/content-sums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentSum)))
            .andExpect(status().isCreated());

        // Validate the ContentSum in the database
        List<ContentSum> contentSumList = contentSumRepository.findAll();
        assertThat(contentSumList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContentSum() throws Exception {
        // Initialize the database
        contentSumService.save(contentSum);

        int databaseSizeBeforeDelete = contentSumRepository.findAll().size();

        // Get the contentSum
        restContentSumMockMvc.perform(delete("/api/content-sums/{id}", contentSum.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContentSum> contentSumList = contentSumRepository.findAll();
        assertThat(contentSumList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentSum.class);
        ContentSum contentSum1 = new ContentSum();
        contentSum1.setId(1L);
        ContentSum contentSum2 = new ContentSum();
        contentSum2.setId(contentSum1.getId());
        assertThat(contentSum1).isEqualTo(contentSum2);
        contentSum2.setId(2L);
        assertThat(contentSum1).isNotEqualTo(contentSum2);
        contentSum1.setId(null);
        assertThat(contentSum1).isNotEqualTo(contentSum2);
    }
}
