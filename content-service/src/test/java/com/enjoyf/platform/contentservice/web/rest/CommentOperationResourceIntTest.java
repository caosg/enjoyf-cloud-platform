//package com.enjoyf.platform.contentservice.web.rest;
//
//import com.enjoyf.platform.contentservice.ContentServiceApp;
//import com.enjoyf.platform.contentservice.domain.CommentOperation;
//import com.enjoyf.platform.contentservice.repository.jpa.CommentOperationRepository;
//import com.enjoyf.platform.contentservice.service.CommentOperationService;
//import com.enjoyf.platform.contentservice.web.rest.errors.ExceptionTranslator;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
//import org.springframework.http.MediaType;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import java.time.Instant;
//import java.time.ZoneId;
//import java.time.ZoneOffset;
//import java.time.ZonedDateTime;
//import java.util.List;
//
//import static com.enjoyf.platform.contentservice.web.rest.TestUtil.sameInstant;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * Test class for the CommentOperationResource REST controller.
// *
// * @see CommentOperationResource
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ContentServiceApp.class)
//public class CommentOperationResourceIntTest {
//
//    private static final Long DEFAULT_COMMENT_ID = 1L;
//    private static final Long UPDATED_COMMENT_ID = 2L;
//
//    private static final Long DEFAULT_UID = 1L;
//    private static final Long UPDATED_UID = 2L;
//
//    private static final Long DEFAULT_DEST_UID = 1L;
//    private static final Long UPDATED_DEST_UID = 2L;
//
//    private static final Integer DEFAULT_OPERATE_TYPE = 1;
//    private static final Integer UPDATED_OPERATE_TYPE = 2;
//
//    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
//    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
//
//    private static final ZonedDateTime DEFAULT_UPDATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
//    private static final ZonedDateTime UPDATED_UPDATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
//
//    @Autowired
//    private CommentOperationRepository commentOperationRepository;
//
//    @Autowired
//    private CommentOperationService commentOperationService;
//
//    @Autowired
//    private MappingJackson2HttpMessageConverter jacksonMessageConverter;
//
//    @Autowired
//    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
//
//    @Autowired
//    private ExceptionTranslator exceptionTranslator;
//
//    @Autowired
//    private EntityManager em;
//
//    private MockMvc restCommentOperationMockMvc;
//
//    private CommentOperation commentOperation;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        CommentOperationResource commentOperationResource = new CommentOperationResource(commentOperationService);
//        this.restCommentOperationMockMvc = MockMvcBuilders.standaloneSetup(commentOperationResource)
//            .setCustomArgumentResolvers(pageableArgumentResolver)
//            .setControllerAdvice(exceptionTranslator)
//            .setMessageConverters(jacksonMessageConverter).build();
//    }
//
//    /**
//     * Create an entity for this test.
//     *
//     * This is a static method, as tests for other entities might also need it,
//     * if they test an entity which requires the current entity.
//     */
//    public static CommentOperation createEntity(EntityManager em) {
//        CommentOperation commentOperation = new CommentOperation()
//            .commentId(DEFAULT_COMMENT_ID)
//            .uid(DEFAULT_UID)
//            .destUid(DEFAULT_DEST_UID)
//            .operateType(DEFAULT_OPERATE_TYPE)
//            .createTime(DEFAULT_CREATE_TIME)
//            .updateTime(DEFAULT_UPDATE_TIME);
//        return commentOperation;
//    }
//
//    @Before
//    public void initTest() {
//        commentOperation = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createCommentOperation() throws Exception {
//        int databaseSizeBeforeCreate = commentOperationRepository.findAll().size();
//
//        // Create the CommentOperation
//        restCommentOperationMockMvc.perform(post("/api/comment-operations")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(commentOperation)))
//            .andExpect(status().isCreated());
//
//        // Validate the CommentOperation in the database
//        List<CommentOperation> commentOperationList = commentOperationRepository.findAll();
//        assertThat(commentOperationList).hasSize(databaseSizeBeforeCreate + 1);
//        CommentOperation testCommentOperation = commentOperationList.get(commentOperationList.size() - 1);
//        assertThat(testCommentOperation.getCommentId()).isEqualTo(DEFAULT_COMMENT_ID);
//        assertThat(testCommentOperation.getUid()).isEqualTo(DEFAULT_UID);
//        assertThat(testCommentOperation.getDestUid()).isEqualTo(DEFAULT_DEST_UID);
//        assertThat(testCommentOperation.getOperateType()).isEqualTo(DEFAULT_OPERATE_TYPE);
//        assertThat(testCommentOperation.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
//        assertThat(testCommentOperation.getUpdateTime()).isEqualTo(DEFAULT_UPDATE_TIME);
//    }
//
//    @Test
//    @Transactional
//    public void createCommentOperationWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = commentOperationRepository.findAll().size();
//
//        // Create the CommentOperation with an existing ID
//        commentOperation.setId(1L);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restCommentOperationMockMvc.perform(post("/api/comment-operations")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(commentOperation)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Alice in the database
//        List<CommentOperation> commentOperationList = commentOperationRepository.findAll();
//        assertThat(commentOperationList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    public void getAllCommentOperations() throws Exception {
//        // Initialize the database
//        commentOperationRepository.saveAndFlush(commentOperation);
//
//        // Get all the commentOperationList
//        restCommentOperationMockMvc.perform(get("/api/comment-operations?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(commentOperation.getId().intValue())))
//            .andExpect(jsonPath("$.[*].commentId").value(hasItem(DEFAULT_COMMENT_ID.intValue())))
//            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.intValue())))
//            .andExpect(jsonPath("$.[*].destUid").value(hasItem(DEFAULT_DEST_UID.intValue())))
//            .andExpect(jsonPath("$.[*].operateType").value(hasItem(DEFAULT_OPERATE_TYPE)))
//            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
//            .andExpect(jsonPath("$.[*].updateTime").value(hasItem(sameInstant(DEFAULT_UPDATE_TIME))));
//    }
//
//    @Test
//    @Transactional
//    public void getCommentOperation() throws Exception {
//        // Initialize the database
//        commentOperationRepository.saveAndFlush(commentOperation);
//
//        // Get the commentOperation
//        restCommentOperationMockMvc.perform(get("/api/comment-operations/{id}", commentOperation.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(commentOperation.getId().intValue()))
//            .andExpect(jsonPath("$.commentId").value(DEFAULT_COMMENT_ID.intValue()))
//            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.intValue()))
//            .andExpect(jsonPath("$.destUid").value(DEFAULT_DEST_UID.intValue()))
//            .andExpect(jsonPath("$.operateType").value(DEFAULT_OPERATE_TYPE))
//            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
//            .andExpect(jsonPath("$.updateTime").value(sameInstant(DEFAULT_UPDATE_TIME)));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingCommentOperation() throws Exception {
//        // Get the commentOperation
//        restCommentOperationMockMvc.perform(get("/api/comment-operations/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateCommentOperation() throws Exception {
//        // Initialize the database
//        commentOperationService.save(commentOperation);
//
//        int databaseSizeBeforeUpdate = commentOperationRepository.findAll().size();
//
//        // Update the commentOperation
//        CommentOperation updatedCommentOperation = commentOperationRepository.findOne(commentOperation.getId());
//        updatedCommentOperation
//            .commentId(UPDATED_COMMENT_ID)
//            .uid(UPDATED_UID)
//            .destUid(UPDATED_DEST_UID)
//            .operateType(UPDATED_OPERATE_TYPE)
//            .createTime(UPDATED_CREATE_TIME)
//            .updateTime(UPDATED_UPDATE_TIME);
//
//        restCommentOperationMockMvc.perform(put("/api/comment-operations")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedCommentOperation)))
//            .andExpect(status().isOk());
//
//        // Validate the CommentOperation in the database
//        List<CommentOperation> commentOperationList = commentOperationRepository.findAll();
//        assertThat(commentOperationList).hasSize(databaseSizeBeforeUpdate);
//        CommentOperation testCommentOperation = commentOperationList.get(commentOperationList.size() - 1);
//        assertThat(testCommentOperation.getCommentId()).isEqualTo(UPDATED_COMMENT_ID);
//        assertThat(testCommentOperation.getUid()).isEqualTo(UPDATED_UID);
//        assertThat(testCommentOperation.getDestUid()).isEqualTo(UPDATED_DEST_UID);
//        assertThat(testCommentOperation.getOperateType()).isEqualTo(UPDATED_OPERATE_TYPE);
//        assertThat(testCommentOperation.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
//        assertThat(testCommentOperation.getUpdateTime()).isEqualTo(UPDATED_UPDATE_TIME);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingCommentOperation() throws Exception {
//        int databaseSizeBeforeUpdate = commentOperationRepository.findAll().size();
//
//        // Create the CommentOperation
//
//        // If the entity doesn't have an ID, it will be created instead of just being updated
//        restCommentOperationMockMvc.perform(put("/api/comment-operations")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(commentOperation)))
//            .andExpect(status().isCreated());
//
//        // Validate the CommentOperation in the database
//        List<CommentOperation> commentOperationList = commentOperationRepository.findAll();
//        assertThat(commentOperationList).hasSize(databaseSizeBeforeUpdate + 1);
//    }
//
//    @Test
//    @Transactional
//    public void deleteCommentOperation() throws Exception {
//        // Initialize the database
//        commentOperationService.save(commentOperation);
//
//        int databaseSizeBeforeDelete = commentOperationRepository.findAll().size();
//
//        // Get the commentOperation
//        restCommentOperationMockMvc.perform(delete("/api/comment-operations/{id}", commentOperation.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isOk());
//
//        // Validate the database is empty
//        List<CommentOperation> commentOperationList = commentOperationRepository.findAll();
//        assertThat(commentOperationList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(CommentOperation.class);
//        CommentOperation commentOperation1 = new CommentOperation();
//        commentOperation1.setId(1L);
//        CommentOperation commentOperation2 = new CommentOperation();
//        commentOperation2.setId(commentOperation1.getId());
//        assertThat(commentOperation1).isEqualTo(commentOperation2);
//        commentOperation2.setId(2L);
//        assertThat(commentOperation1).isNotEqualTo(commentOperation2);
//        commentOperation1.setId(null);
//        assertThat(commentOperation1).isNotEqualTo(commentOperation2);
//    }
//}
