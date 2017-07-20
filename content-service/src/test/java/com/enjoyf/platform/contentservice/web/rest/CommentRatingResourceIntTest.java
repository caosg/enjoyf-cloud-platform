//package com.enjoyf.platform.contentservice.web.rest;
//
//import com.enjoyf.platform.contentservice.ContentServiceApp;
//
//import com.enjoyf.platform.contentservice.domain.CommentRating;
//import com.enjoyf.platform.contentservice.repository.jpa.CommentRatingRepository;
//import com.enjoyf.platform.contentservice.service.CommentRatingService;
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
// * Test class for the CommentRatingResource REST controller.
// *
// * @see CommentRatingResource
// */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ContentServiceApp.class)
//public class CommentRatingResourceIntTest {
//
//    private static final Long DEFAULT_GAME_ID = 1L;
//    private static final Long UPDATED_GAME_ID = 2L;
//
//    private static final Double DEFAULT_SCORE_SUM = 1D;
//    private static final Double UPDATED_SCORE_SUM = 2D;
//
//    private static final Integer DEFAULT_SCORE_NUM = 1;
//    private static final Integer UPDATED_SCORE_NUM = 2;
//
//    private static final Integer DEFAULT_FIVE_USER_SUM = 1;
//    private static final Integer UPDATED_FIVE_USER_SUM = 2;
//
//    private static final Integer DEFAULT_FOUR_USER_SUM = 1;
//    private static final Integer UPDATED_FOUR_USER_SUM = 2;
//
//    private static final Integer DEFAULT_THREE_USER_SUM = 1;
//    private static final Integer UPDATED_THREE_USER_SUM = 2;
//
//    private static final Integer DEFAULT_TWO_USER_SUM = 1;
//    private static final Integer UPDATED_TWO_USER_SUM = 2;
//
//    private static final Integer DEFAULT_ONE_USER_SUM = 1;
//    private static final Integer UPDATED_ONE_USER_SUM = 2;
//
//    private static final ZonedDateTime DEFAULT_CREATE_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
//    private static final ZonedDateTime UPDATED_CREATE_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
//
//    private static final ZonedDateTime DEFAULT_MODIFY_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
//    private static final ZonedDateTime UPDATED_MODIFY_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
//
//    @Autowired
//    private CommentRatingRepository commentRatingRepository;
//
//    @Autowired
//    private CommentRatingService commentRatingService;
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
//    private MockMvc restCommentRatingMockMvc;
//
//    private CommentRating commentRating;
//
//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        CommentRatingResource commentRatingResource = new CommentRatingResource(commentRatingService);
//        this.restCommentRatingMockMvc = MockMvcBuilders.standaloneSetup(commentRatingResource)
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
//    public static CommentRating createEntity(EntityManager em) {
//        CommentRating commentRating = new CommentRating()
//            .gameId(DEFAULT_GAME_ID)
//            .scoreSum(DEFAULT_SCORE_SUM)
//            .scoreNum(DEFAULT_SCORE_NUM)
//            .fiveUserSum(DEFAULT_FIVE_USER_SUM)
//            .fourUserSum(DEFAULT_FOUR_USER_SUM)
//            .threeUserSum(DEFAULT_THREE_USER_SUM)
//            .twoUserSum(DEFAULT_TWO_USER_SUM)
//            .oneUserSum(DEFAULT_ONE_USER_SUM)
//            .createTime(DEFAULT_CREATE_TIME)
//            .modifyTime(DEFAULT_MODIFY_TIME);
//        return commentRating;
//    }
//
//    @Before
//    public void initTest() {
//        commentRating = createEntity(em);
//    }
//
//    @Test
//    @Transactional
//    public void createCommentRating() throws Exception {
//        int databaseSizeBeforeCreate = commentRatingRepository.findAll().size();
//
//        // Create the CommentRating
//        restCommentRatingMockMvc.perform(post("/api/comment-ratings")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(commentRating)))
//            .andExpect(status().isCreated());
//
//        // Validate the CommentRating in the database
//        List<CommentRating> commentRatingList = commentRatingRepository.findAll();
//        assertThat(commentRatingList).hasSize(databaseSizeBeforeCreate + 1);
//        CommentRating testCommentRating = commentRatingList.get(commentRatingList.size() - 1);
//        assertThat(testCommentRating.getGameId()).isEqualTo(DEFAULT_GAME_ID);
//        assertThat(testCommentRating.getScoreSum()).isEqualTo(DEFAULT_SCORE_SUM);
//        assertThat(testCommentRating.getScoreNum()).isEqualTo(DEFAULT_SCORE_NUM);
//        assertThat(testCommentRating.getFiveUserSum()).isEqualTo(DEFAULT_FIVE_USER_SUM);
//        assertThat(testCommentRating.getFourUserSum()).isEqualTo(DEFAULT_FOUR_USER_SUM);
//        assertThat(testCommentRating.getThreeUserSum()).isEqualTo(DEFAULT_THREE_USER_SUM);
//        assertThat(testCommentRating.getTwoUserSum()).isEqualTo(DEFAULT_TWO_USER_SUM);
//        assertThat(testCommentRating.getOneUserSum()).isEqualTo(DEFAULT_ONE_USER_SUM);
//        assertThat(testCommentRating.getCreateTime()).isEqualTo(DEFAULT_CREATE_TIME);
//        assertThat(testCommentRating.getModifyTime()).isEqualTo(DEFAULT_MODIFY_TIME);
//    }
//
//    @Test
//    @Transactional
//    public void createCommentRatingWithExistingId() throws Exception {
//        int databaseSizeBeforeCreate = commentRatingRepository.findAll().size();
//
//        // Create the CommentRating with an existing ID
//        commentRating.setId(1L);
//
//        // An entity with an existing ID cannot be created, so this API call must fail
//        restCommentRatingMockMvc.perform(post("/api/comment-ratings")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(commentRating)))
//            .andExpect(status().isBadRequest());
//
//        // Validate the Alice in the database
//        List<CommentRating> commentRatingList = commentRatingRepository.findAll();
//        assertThat(commentRatingList).hasSize(databaseSizeBeforeCreate);
//    }
//
//    @Test
//    @Transactional
//    public void getAllCommentRatings() throws Exception {
//        // Initialize the database
//        commentRatingRepository.saveAndFlush(commentRating);
//
//        // Get all the commentRatingList
//        restCommentRatingMockMvc.perform(get("/api/comment-ratings?sort=id,desc"))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.[*].id").value(hasItem(commentRating.getId().intValue())))
//            .andExpect(jsonPath("$.[*].gameId").value(hasItem(DEFAULT_GAME_ID.intValue())))
//            .andExpect(jsonPath("$.[*].scoreSum").value(hasItem(DEFAULT_SCORE_SUM.doubleValue())))
//            .andExpect(jsonPath("$.[*].scoreNum").value(hasItem(DEFAULT_SCORE_NUM)))
//            .andExpect(jsonPath("$.[*].fiveUserSum").value(hasItem(DEFAULT_FIVE_USER_SUM)))
//            .andExpect(jsonPath("$.[*].fourUserSum").value(hasItem(DEFAULT_FOUR_USER_SUM)))
//            .andExpect(jsonPath("$.[*].threeUserSum").value(hasItem(DEFAULT_THREE_USER_SUM)))
//            .andExpect(jsonPath("$.[*].twoUserSum").value(hasItem(DEFAULT_TWO_USER_SUM)))
//            .andExpect(jsonPath("$.[*].oneUserSum").value(hasItem(DEFAULT_ONE_USER_SUM)))
//            .andExpect(jsonPath("$.[*].createTime").value(hasItem(sameInstant(DEFAULT_CREATE_TIME))))
//            .andExpect(jsonPath("$.[*].modifyTime").value(hasItem(sameInstant(DEFAULT_MODIFY_TIME))));
//    }
//
//    @Test
//    @Transactional
//    public void getCommentRating() throws Exception {
//        // Initialize the database
//        commentRatingRepository.saveAndFlush(commentRating);
//
//        // Get the commentRating
//        restCommentRatingMockMvc.perform(get("/api/comment-ratings/{id}", commentRating.getId()))
//            .andExpect(status().isOk())
//            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//            .andExpect(jsonPath("$.id").value(commentRating.getId().intValue()))
//            .andExpect(jsonPath("$.gameId").value(DEFAULT_GAME_ID.intValue()))
//            .andExpect(jsonPath("$.scoreSum").value(DEFAULT_SCORE_SUM.doubleValue()))
//            .andExpect(jsonPath("$.scoreNum").value(DEFAULT_SCORE_NUM))
//            .andExpect(jsonPath("$.fiveUserSum").value(DEFAULT_FIVE_USER_SUM))
//            .andExpect(jsonPath("$.fourUserSum").value(DEFAULT_FOUR_USER_SUM))
//            .andExpect(jsonPath("$.threeUserSum").value(DEFAULT_THREE_USER_SUM))
//            .andExpect(jsonPath("$.twoUserSum").value(DEFAULT_TWO_USER_SUM))
//            .andExpect(jsonPath("$.oneUserSum").value(DEFAULT_ONE_USER_SUM))
//            .andExpect(jsonPath("$.createTime").value(sameInstant(DEFAULT_CREATE_TIME)))
//            .andExpect(jsonPath("$.modifyTime").value(sameInstant(DEFAULT_MODIFY_TIME)));
//    }
//
//    @Test
//    @Transactional
//    public void getNonExistingCommentRating() throws Exception {
//        // Get the commentRating
//        restCommentRatingMockMvc.perform(get("/api/comment-ratings/{id}", Long.MAX_VALUE))
//            .andExpect(status().isNotFound());
//    }
//
//    @Test
//    @Transactional
//    public void updateCommentRating() throws Exception {
//        // Initialize the database
//        commentRatingService.save(commentRating);
//
//        int databaseSizeBeforeUpdate = commentRatingRepository.findAll().size();
//
//        // Update the commentRating
//        CommentRating updatedCommentRating = commentRatingRepository.findOne(commentRating.getId());
//        updatedCommentRating
//            .gameId(UPDATED_GAME_ID)
//            .scoreSum(UPDATED_SCORE_SUM)
//            .scoreNum(UPDATED_SCORE_NUM)
//            .fiveUserSum(UPDATED_FIVE_USER_SUM)
//            .fourUserSum(UPDATED_FOUR_USER_SUM)
//            .threeUserSum(UPDATED_THREE_USER_SUM)
//            .twoUserSum(UPDATED_TWO_USER_SUM)
//            .oneUserSum(UPDATED_ONE_USER_SUM)
//            .createTime(UPDATED_CREATE_TIME)
//            .modifyTime(UPDATED_MODIFY_TIME);
//
//        restCommentRatingMockMvc.perform(put("/api/comment-ratings")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(updatedCommentRating)))
//            .andExpect(status().isOk());
//
//        // Validate the CommentRating in the database
//        List<CommentRating> commentRatingList = commentRatingRepository.findAll();
//        assertThat(commentRatingList).hasSize(databaseSizeBeforeUpdate);
//        CommentRating testCommentRating = commentRatingList.get(commentRatingList.size() - 1);
//        assertThat(testCommentRating.getGameId()).isEqualTo(UPDATED_GAME_ID);
//        assertThat(testCommentRating.getScoreSum()).isEqualTo(UPDATED_SCORE_SUM);
//        assertThat(testCommentRating.getScoreNum()).isEqualTo(UPDATED_SCORE_NUM);
//        assertThat(testCommentRating.getFiveUserSum()).isEqualTo(UPDATED_FIVE_USER_SUM);
//        assertThat(testCommentRating.getFourUserSum()).isEqualTo(UPDATED_FOUR_USER_SUM);
//        assertThat(testCommentRating.getThreeUserSum()).isEqualTo(UPDATED_THREE_USER_SUM);
//        assertThat(testCommentRating.getTwoUserSum()).isEqualTo(UPDATED_TWO_USER_SUM);
//        assertThat(testCommentRating.getOneUserSum()).isEqualTo(UPDATED_ONE_USER_SUM);
//        assertThat(testCommentRating.getCreateTime()).isEqualTo(UPDATED_CREATE_TIME);
//        assertThat(testCommentRating.getModifyTime()).isEqualTo(UPDATED_MODIFY_TIME);
//    }
//
//    @Test
//    @Transactional
//    public void updateNonExistingCommentRating() throws Exception {
//        int databaseSizeBeforeUpdate = commentRatingRepository.findAll().size();
//
//        // Create the CommentRating
//
//        // If the entity doesn't have an ID, it will be created instead of just being updated
//        restCommentRatingMockMvc.perform(put("/api/comment-ratings")
//            .contentType(TestUtil.APPLICATION_JSON_UTF8)
//            .content(TestUtil.convertObjectToJsonBytes(commentRating)))
//            .andExpect(status().isCreated());
//
//        // Validate the CommentRating in the database
//        List<CommentRating> commentRatingList = commentRatingRepository.findAll();
//        assertThat(commentRatingList).hasSize(databaseSizeBeforeUpdate + 1);
//    }
//
//    @Test
//    @Transactional
//    public void deleteCommentRating() throws Exception {
//        // Initialize the database
//        commentRatingService.save(commentRating);
//
//        int databaseSizeBeforeDelete = commentRatingRepository.findAll().size();
//
//        // Get the commentRating
//        restCommentRatingMockMvc.perform(delete("/api/comment-ratings/{id}", commentRating.getId())
//            .accept(TestUtil.APPLICATION_JSON_UTF8))
//            .andExpect(status().isOk());
//
//        // Validate the database is empty
//        List<CommentRating> commentRatingList = commentRatingRepository.findAll();
//        assertThat(commentRatingList).hasSize(databaseSizeBeforeDelete - 1);
//    }
//
//    @Test
//    @Transactional
//    public void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(CommentRating.class);
//        CommentRating commentRating1 = new CommentRating();
//        commentRating1.setId(1L);
//        CommentRating commentRating2 = new CommentRating();
//        commentRating2.setId(commentRating1.getId());
//        assertThat(commentRating1).isEqualTo(commentRating2);
//        commentRating2.setId(2L);
//        assertThat(commentRating1).isNotEqualTo(commentRating2);
//        commentRating1.setId(null);
//        assertThat(commentRating1).isNotEqualTo(commentRating2);
//    }
//}
