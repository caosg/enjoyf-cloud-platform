//package com.enjoyf.platform.contentservice.web.rest;
//
//import com.enjoyf.platform.contentservice.ContentServiceApp;
//
//import com.enjoyf.platform.contentservice.domain.Comment;
//import com.enjoyf.platform.contentservice.repository.CommentRepository;
//import com.enjoyf.platform.contentservice.service.CommentService;
//import com.enjoyf.platform.contentservice.web.rest.errors.ExceptionTranslator;
//
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
//import java.time.ZonedDateTime;
//import java.time.ZoneOffset;
//import java.time.ZoneId;
//import java.util.List;
//
//import static com.enjoyf.platform.contentservice.web.rest.TestUtil.sameInstant;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.hasItem;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
///**
// * Test class for the CommentResource REST controller.
// *
// * @see CommentResource
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ContentServiceApp.class)
//public class CommentResourceIntTest {
//
//    private static final Long DEFAULT_GAME_ID = 1L;
//    private static final Long UPDATED_GAME_ID = 2L;
//
//    private static final Long DEFAULT_UID = 1L;
//    private static final Long UPDATED_UID = 2L;
//
//    private static final Integer DEFAULT_SCORE = 1;
//    private static final Integer UPDATED_SCORE = 2;
//
//    private static final String DEFAULT_BODY = "AAAAAAAAAA";
//    private static final String UPDATED_BODY = "BBBBBBBBBB";
//
//    private static final Integer DEFAULT_AGREE_NUM = 1;
//    private static final Integer UPDATED_AGREE_NUM = 2;
//
//    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
//    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
//
//    private static final ZonedDateTime DEFAULT_MODIFY_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
//    private static final ZonedDateTime UPDATED_MODIFY_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
//
//    private static final String DEFAULT_VALID_STATUS = "AAAAAAAAAA";
//    private static final String UPDATED_VALID_STATUS = "BBBBBBBBBB";
//
//    private static final Integer DEFAULT_HIGH_QUALITY = 1;
//    private static final Integer UPDATED_HIGH_QUALITY = 2;
//
//    private static final Integer DEFAULT_REPLY_NUM = 1;
//    private static final Integer UPDATED_REPLY_NUM = 2;
//
//    @Autowired
//    private CommentRepository commentRepository;
//
//    @Autowired
//    private CommentService commentService;
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
//    private MockMvc restCommentMockMvc;
//
//    private Comment comment;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        CommentResource commentResource = new CommentResource(commentService);
//        this.restCommentMockMvc = MockMvcBuilders.standaloneSetup(commentResource)
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
//    public static Comment createEntity(EntityManager em) {
//        Comment comment = new Comment()
//            .gameId(DEFAULT_GAME_ID)
//            .uid(DEFAULT_UID)
//            .score(DEFAULT_SCORE)
//            .body(DEFAULT_BODY)
//            .agreeNum(DEFAULT_AGREE_NUM)
//            .createTime(DEFAULT_CREATE_TIME)
//            .modifyTime(DEFAULT_MODIFY_TIME)
//            .validStatus(DEFAULT_VALID_STATUS)
//            .highQuality(DEFAULT_HIGH_QUALITY)
//            .replyNum(DEFAULT_REPLY_NUM);
//        return comment;
//    }
//
//    @Before
//    public void initTest() {
//        comment = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createComment() throws Exception {
//        int databaseSizeBeforeCreate = commentRepository.findAll().size();
//
//        // Create the Comment
//        restCommentMockMvc.perform(post("/api/comments")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(comment)))
//            .andExpect(status().isCreated());
//
//        // Validate the Comment in the database
//        List<Comment> commentList = commentRepository.findAll();
//        assertThat(commentList).hasSize(databaseSizeBeforeCreate + 1);
//        Comment testComment = commentList.get(commentList.size() - 1);
//        assertThat(testComment.getGameId()).isEqualTo(DEFAULT_GAME_ID);
//        assertThat(testComment.getUid()).isEqualTo(DEFAULT_UID);
//        assertThat(testComment.getScore()).isEqualTo(DEFAULT_SCORE);
//        assertThat(testComment.getBody()).isEqualTo(DEFAULT_BODY);
//        assertThat(testComment.getAgreeNum()).isEqualTo(DEFAULT_AGREE_NUM);
//        assertThat(testComment.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
//        assertThat(testComment.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
//        assertThat(testComment.getValidStatus()).isEqualTo(DEFAULT_VALID_STATUS);
//        assertThat(testComment.getHighQuality()).isEqualTo(DEFAULT_HIGH_QUALITY);
//        assertThat(testComment.getReplyNum()).isEqualTo(DEFAULT_REPLY_NUM);
//    }
//
//    @Test
//    @Transactional
//    public void createCommentWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = commentRepository.findAll().size();
//
//        // Create the Comment with an existing ID
//        comment.setId(1L);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restCommentMockMvc.perform(post("/api/comments")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(comment)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Alice in the database
//        List<Comment> commentList = commentRepository.findAll();
//        assertThat(commentList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    public void getAllComments() throws Exception {
//        // Initialize the database
//        commentRepository.saveAndFlush(comment);
//
//        // Get all the commentList
//        restCommentMockMvc.perform(get("/api/comments?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(comment.getId().intValue())))
//            .andExpect(jsonPath("$.[*].gameId").value(hasItem(DEFAULT_GAME_ID.intValue())))
//            .andExpect(jsonPath("$.[*].uid").value(hasItem(DEFAULT_UID.intValue())))
//            .andExpect(jsonPath("$.[*].score").value(hasItem(DEFAULT_SCORE)))
//            .andExpect(jsonPath("$.[*].body").value(hasItem(DEFAULT_BODY.toString())))
//            .andExpect(jsonPath("$.[*].agreeNum").value(hasItem(DEFAULT_AGREE_NUM)))
//            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
//            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(sameInstant(DEFAULT_MODIFY_TIME))))
//            .andExpect(jsonPath("$.[*].validStatus").value(hasItem(DEFAULT_VALID_STATUS.toString())))
//            .andExpect(jsonPath("$.[*].highQuality").value(hasItem(DEFAULT_HIGH_QUALITY)))
//            .andExpect(jsonPath("$.[*].replyNum").value(hasItem(DEFAULT_REPLY_NUM)));
//    }
//
//    @Test
//    @Transactional
//    public void getComment() throws Exception {
//        // Initialize the database
//        commentRepository.saveAndFlush(comment);
//
//        // Get the comment
//        restCommentMockMvc.perform(get("/api/comments/{id}", comment.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(comment.getId().intValue()))
//            .andExpect(jsonPath("$.gameId").value(DEFAULT_GAME_ID.intValue()))
//            .andExpect(jsonPath("$.uid").value(DEFAULT_UID.intValue()))
//            .andExpect(jsonPath("$.score").value(DEFAULT_SCORE))
//            .andExpect(jsonPath("$.body").value(DEFAULT_BODY.toString()))
//            .andExpect(jsonPath("$.agreeNum").value(DEFAULT_AGREE_NUM))
//            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
//            .andExpect(jsonPath("$.modifyTime").value(sameInstant(DEFAULT_MODIFY_TIME)))
//            .andExpect(jsonPath("$.validStatus").value(DEFAULT_VALID_STATUS.toString()))
//            .andExpect(jsonPath("$.highQuality").value(DEFAULT_HIGH_QUALITY))
//            .andExpect(jsonPath("$.replyNum").value(DEFAULT_REPLY_NUM));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingComment() throws Exception {
//        // Get the comment
//        restCommentMockMvc.perform(get("/api/comments/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateComment() throws Exception {
//        // Initialize the database
//        commentService.save(comment);
//
//        int databaseSizeBeforeUpdate = commentRepository.findAll().size();
//
//        // Update the comment
//        Comment updatedComment = commentRepository.findOne(comment.getId());
//        updatedComment
//            .gameId(UPDATED_GAME_ID)
//            .uid(UPDATED_UID)
//            .score(UPDATED_SCORE)
//            .body(UPDATED_BODY)
//            .agreeNum(UPDATED_AGREE_NUM)
//            .createTime(UPDATED_CREATE_TIME)
//            .modifyTime(UPDATED_MODIFY_TIME)
//            .validStatus(UPDATED_VALID_STATUS)
//            .highQuality(UPDATED_HIGH_QUALITY)
//            .replyNum(UPDATED_REPLY_NUM);
//
//        restCommentMockMvc.perform(put("/api/comments")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedComment)))
//            .andExpect(status().isOk());
//
//        // Validate the Comment in the database
//        List<Comment> commentList = commentRepository.findAll();
//        assertThat(commentList).hasSize(databaseSizeBeforeUpdate);
//        Comment testComment = commentList.get(commentList.size() - 1);
//        assertThat(testComment.getGameId()).isEqualTo(UPDATED_GAME_ID);
//        assertThat(testComment.getUid()).isEqualTo(UPDATED_UID);
//        assertThat(testComment.getScore()).isEqualTo(UPDATED_SCORE);
//        assertThat(testComment.getBody()).isEqualTo(UPDATED_BODY);
//        assertThat(testComment.getAgreeNum()).isEqualTo(UPDATED_AGREE_NUM);
//        assertThat(testComment.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
//        assertThat(testComment.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
//        assertThat(testComment.getValidStatus()).isEqualTo(UPDATED_VALID_STATUS);
//        assertThat(testComment.getHighQuality()).isEqualTo(UPDATED_HIGH_QUALITY);
//        assertThat(testComment.getReplyNum()).isEqualTo(UPDATED_REPLY_NUM);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingComment() throws Exception {
//        int databaseSizeBeforeUpdate = commentRepository.findAll().size();
//
//        // Create the Comment
//
//        // If the entity doesn't have an ID, it will be created instead of just being updated
//        restCommentMockMvc.perform(put("/api/comments")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(comment)))
//            .andExpect(status().isCreated());
//
//        // Validate the Comment in the database
//        List<Comment> commentList = commentRepository.findAll();
//        assertThat(commentList).hasSize(databaseSizeBeforeUpdate + 1);
//    }
//
//    @Test
//    @Transactional
//    public void deleteComment() throws Exception {
//        // Initialize the database
//        commentService.save(comment);
//
//        int databaseSizeBeforeDelete = commentRepository.findAll().size();
//
//        // Get the comment
//        restCommentMockMvc.perform(delete("/api/comments/{id}", comment.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isOk());
//
//        // Validate the database is empty
//        List<Comment> commentList = commentRepository.findAll();
//        assertThat(commentList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(Comment.class);
//        Comment comment1 = new Comment();
//        comment1.setId(1L);
//        Comment comment2 = new Comment();
//        comment2.setId(comment1.getId());
//        assertThat(comment1).isEqualTo(comment2);
//        comment2.setId(2L);
//        assertThat(comment1).isNotEqualTo(comment2);
//        comment1.setId(null);
//        assertThat(comment1).isNotEqualTo(comment2);
//    }
//}
