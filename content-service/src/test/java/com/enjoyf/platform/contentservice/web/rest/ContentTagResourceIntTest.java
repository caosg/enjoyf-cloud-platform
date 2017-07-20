package com.enjoyf.platform.contentservice.web.rest;

import com.enjoyf.platform.autoconfigure.web.error.ExceptionTranslator;
import com.enjoyf.platform.contentservice.ContentServiceApp;

import com.enjoyf.platform.contentservice.domain.ContentTag;
import com.enjoyf.platform.contentservice.repository.jpa.ContentTagRepository;
import com.enjoyf.platform.contentservice.service.ContentTagService;

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
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ContentTagResource REST controller.
 *
 * @see ContentTagResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ContentServiceApp.class)
public class ContentTagResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TARGET = "AAAAAAAAAA";
    private static final String UPDATED_TARGET = "BBBBBBBBBB";

    private static final Integer DEFAULT_TAG_TYPE = 1;
    private static final Integer UPDATED_TAG_TYPE = 2;

    private static final String DEFAULT_TAG_LINE = "AAAAAAAAAA";
    private static final String UPDATED_TAG_LINE = "BBBBBBBBBB";

    private static final Long DEFAULT_DISPLAY_ORDER = 1L;
    private static final Long UPDATED_DISPLAY_ORDER = 2L;

    private static final String DEFAULT_VALID_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_VALID_STATUS = "BBBBBBBBBB";

    private static final Date DEFAULT_CREATE_DATE = new Date();
    private static final Date UPDATED_CREATE_DATE = new Date();

    @Autowired
    private ContentTagRepository contentTagRepository;

    @Autowired
    private ContentTagService contentTagService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restContentTagMockMvc;

    private ContentTag contentTag;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContentTagResource contentTagResource = new ContentTagResource(contentTagService);
        this.restContentTagMockMvc = MockMvcBuilders.standaloneSetup(contentTagResource)
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
    public static ContentTag createEntity(EntityManager em) {
        ContentTag contentTag = new ContentTag()
            .name(DEFAULT_NAME)
            .target(DEFAULT_TARGET)
            .tagType(DEFAULT_TAG_TYPE)
            .tagLine(DEFAULT_TAG_LINE)
            .displayOrder(DEFAULT_DISPLAY_ORDER)
            .validStatus(DEFAULT_VALID_STATUS)
            .createDate(DEFAULT_CREATE_DATE);
        return contentTag;
    }

    @Before
    public void initTest() {
        contentTag = createEntity(em);
    }

    @Test
    @Transactional
    public void createContentTag() throws Exception {
        int databaseSizeBeforeCreate = contentTagRepository.findAll().size();

        // Create the ContentTag
        restContentTagMockMvc.perform(post("/api/content-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentTag)))
            .andExpect(status().isCreated());

        // Validate the ContentTag in the database
        List<ContentTag> contentTagList = contentTagRepository.findAll();
        assertThat(contentTagList).hasSize(databaseSizeBeforeCreate + 1);
        ContentTag testContentTag = contentTagList.get(contentTagList.size() - 1);
        assertThat(testContentTag.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testContentTag.getTarget()).isEqualTo(DEFAULT_TARGET);
        assertThat(testContentTag.getTagType()).isEqualTo(DEFAULT_TAG_TYPE);
        assertThat(testContentTag.getTagLine()).isEqualTo(DEFAULT_TAG_LINE);
        assertThat(testContentTag.getDisplayOrder()).isEqualTo(DEFAULT_DISPLAY_ORDER);
        assertThat(testContentTag.getValidStatus()).isEqualTo(DEFAULT_VALID_STATUS);
        assertThat(testContentTag.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
    }

    @Test
    @Transactional
    public void createContentTagWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = contentTagRepository.findAll().size();

        // Create the ContentTag with an existing ID
        contentTag.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restContentTagMockMvc.perform(post("/api/content-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentTag)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<ContentTag> contentTagList = contentTagRepository.findAll();
        assertThat(contentTagList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllContentTags() throws Exception {
        // Initialize the database
        contentTagRepository.saveAndFlush(contentTag);

        // Get all the contentTagList
        restContentTagMockMvc.perform(get("/api/content-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contentTag.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].target").value(hasItem(DEFAULT_TARGET.toString())))
            .andExpect(jsonPath("$.[*].tagType").value(hasItem(DEFAULT_TAG_TYPE)))
            .andExpect(jsonPath("$.[*].tagLine").value(hasItem(DEFAULT_TAG_LINE.toString())))
            .andExpect(jsonPath("$.[*].displayOrder").value(hasItem(DEFAULT_DISPLAY_ORDER.intValue())))
            .andExpect(jsonPath("$.[*].validStatus").value(hasItem(DEFAULT_VALID_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE)));
    }

    @Test
    @Transactional
    public void getContentTag() throws Exception {
        // Initialize the database
        contentTagRepository.saveAndFlush(contentTag);

        // Get the contentTag
        restContentTagMockMvc.perform(get("/api/content-tags/{id}", contentTag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(contentTag.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.target").value(DEFAULT_TARGET.toString()))
            .andExpect(jsonPath("$.tagType").value(DEFAULT_TAG_TYPE))
            .andExpect(jsonPath("$.tagLine").value(DEFAULT_TAG_LINE.toString()))
            .andExpect(jsonPath("$.displayOrder").value(DEFAULT_DISPLAY_ORDER.intValue()))
            .andExpect(jsonPath("$.validStatus").value(DEFAULT_VALID_STATUS.toString()))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE));
    }

    @Test
    @Transactional
    public void getNonExistingContentTag() throws Exception {
        // Get the contentTag
        restContentTagMockMvc.perform(get("/api/content-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContentTag() throws Exception {
        // Initialize the database
        contentTagService.save(contentTag);

        int databaseSizeBeforeUpdate = contentTagRepository.findAll().size();

        // Update the contentTag
        ContentTag updatedContentTag = contentTagRepository.findOne(contentTag.getId());
        updatedContentTag
            .name(UPDATED_NAME)
            .target(UPDATED_TARGET)
            .tagType(UPDATED_TAG_TYPE)
            .tagLine(UPDATED_TAG_LINE)
            .displayOrder(UPDATED_DISPLAY_ORDER)
            .validStatus(UPDATED_VALID_STATUS)
            .createDate(UPDATED_CREATE_DATE);

        restContentTagMockMvc.perform(put("/api/content-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedContentTag)))
            .andExpect(status().isOk());

        // Validate the ContentTag in the database
        List<ContentTag> contentTagList = contentTagRepository.findAll();
        assertThat(contentTagList).hasSize(databaseSizeBeforeUpdate);
        ContentTag testContentTag = contentTagList.get(contentTagList.size() - 1);
        assertThat(testContentTag.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testContentTag.getTarget()).isEqualTo(UPDATED_TARGET);
        assertThat(testContentTag.getTagType()).isEqualTo(UPDATED_TAG_TYPE);
        assertThat(testContentTag.getTagLine()).isEqualTo(UPDATED_TAG_LINE);
        assertThat(testContentTag.getDisplayOrder()).isEqualTo(UPDATED_DISPLAY_ORDER);
        assertThat(testContentTag.getValidStatus()).isEqualTo(UPDATED_VALID_STATUS);
        assertThat(testContentTag.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingContentTag() throws Exception {
        int databaseSizeBeforeUpdate = contentTagRepository.findAll().size();

        // Create the ContentTag

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restContentTagMockMvc.perform(put("/api/content-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(contentTag)))
            .andExpect(status().isCreated());

        // Validate the ContentTag in the database
        List<ContentTag> contentTagList = contentTagRepository.findAll();
        assertThat(contentTagList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteContentTag() throws Exception {
        // Initialize the database
        contentTagService.save(contentTag);

        int databaseSizeBeforeDelete = contentTagRepository.findAll().size();

        // Get the contentTag
        restContentTagMockMvc.perform(delete("/api/content-tags/{id}", contentTag.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ContentTag> contentTagList = contentTagRepository.findAll();
        assertThat(contentTagList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentTag.class);
        ContentTag contentTag1 = new ContentTag();
        contentTag1.setId(1L);
        ContentTag contentTag2 = new ContentTag();
        contentTag2.setId(contentTag1.getId());
        assertThat(contentTag1).isEqualTo(contentTag2);
        contentTag2.setId(2L);
        assertThat(contentTag1).isNotEqualTo(contentTag2);
        contentTag1.setId(null);
        assertThat(contentTag1).isNotEqualTo(contentTag2);
    }
}
