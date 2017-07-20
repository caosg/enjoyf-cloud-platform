package com.enjoyf.platform.contentservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.common.ResultCodeConstants;
import com.enjoyf.platform.common.domain.comment.CommentDomain;
import com.enjoyf.platform.common.util.CommentUtil;
import com.enjoyf.platform.contentservice.domain.Content;
import com.enjoyf.platform.contentservice.domain.FiledValue;
import com.enjoyf.platform.contentservice.domain.enumeration.AdvertiseDomain;
import com.enjoyf.platform.contentservice.domain.enumeration.ContentSource;
import com.enjoyf.platform.contentservice.domain.enumeration.ValidStatus;
import com.enjoyf.platform.contentservice.domain.enumeration.WikiappSearchType;
import com.enjoyf.platform.contentservice.service.AdvertiseService;
import com.enjoyf.platform.contentservice.service.ContentService;
import com.enjoyf.platform.contentservice.service.ContentSumService;
import com.enjoyf.platform.contentservice.web.rest.util.AskUtil;
import com.enjoyf.platform.contentservice.web.rest.util.HTTPUtil;
import com.enjoyf.platform.contentservice.web.rest.util.HeaderUtil;
import com.enjoyf.platform.contentservice.web.rest.util.PaginationUtil;
import com.enjoyf.platform.contentservice.web.rest.vm.ContentCmsVM;
import com.enjoyf.platform.contentservice.web.rest.vm.ContentVM;
import com.enjoyf.platform.contentservice.web.rest.vm.ContentWikiVM;
import com.enjoyf.platform.page.ScoreRange;
import com.enjoyf.platform.page.ScoreRangeDTO;
import com.enjoyf.platform.page.ScoreRangeRows;
import com.enjoyf.platform.page.ScoreSort;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * REST controller for managing Content.
 */
@RestController
@RequestMapping("/api")
public class ContentResource {

    private final Logger log = LoggerFactory.getLogger(ContentResource.class);

    private static final String ENTITY_NAME = "content";

    private static final int PCOUNT = 20;


    private final ContentService contentService;

    private final AdvertiseService advertiseService;

    private final ContentSumService contentSumService;

    @Autowired
    private AskUtil askUtil;


    public ContentResource(ContentService contentService, AdvertiseService advertiseService, ContentSumService contentSumService) {
        this.contentService = contentService;
        this.advertiseService = advertiseService;
        this.contentSumService = contentSumService;
    }

    /**
     * POST  /contents : Create a new content.
     *
     * @param content the content to create
     * @return the ResponseEntity with status 201 (Created) and with body the new content, or with status 400 (Bad Request) if the content has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contents")
    @Timed
    public ResponseEntity<Content> createContent(@RequestBody Content content) throws URISyntaxException {
        log.debug("REST request to save Content : {}", content);
        if (content.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new content cannot already have an ID")).body(null);
        }
        Content result = contentService.save(content);
        return ResponseEntity.created(new URI("/api/contents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contents : Updates an existing content.
     *
     * @param content the content to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated content,
     * or with status 400 (Bad Request) if the content is not valid,
     * or with status 500 (Internal Server Error) if the content couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contents")
    @Timed
    public ResponseEntity<Content> updateContent(@RequestBody Content content) throws URISyntaxException {
        log.debug("REST request to update Content : {}", content);
        if (content.getId() == null) {
            return createContent(content);
        }
        Content result = contentService.save(content);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, content.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contents : get all the contents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contents in body
     */
    @GetMapping("/contents")
    @Timed
    public ResponseEntity<List<Content>> getAllContents(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Contents");
        Page<Content> page = contentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contents");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contents/:id : get the "id" content.
     *
     * @param id the id of the content to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the content, or with status 404 (Not Found)
     */
    @GetMapping("/contents/{id}")
    @Timed
    public ResponseEntity<Content> getContent(@PathVariable Long id) {
        log.debug("REST request to get Content : {}", id);
        Content content = contentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(content));
    }


    /**
     * DELETE  /contents/:id : delete the "id" content.
     *
     * @param id the id of the content to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contents/{id}")
    @Timed
    public ResponseEntity<Void> deleteContent(@PathVariable Long id) {
        log.debug("REST request to delete Content : {}", id);
        contentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }


    /**
     * 查询推荐文章列表
     *
     * @param id
     * @param request
     * @return
     */
    @GetMapping("/contents/list/{id}")
    @Timed
    @ApiOperation(value = "查询推荐文章列表,wikiapp首页")
    public String getContentApp(@PathVariable Long id, HttpServletRequest request,
                                @RequestParam(value = "appkey", required = false, defaultValue = "") String appkey,
                                @RequestParam(value = "pid", required = false, defaultValue = "") String pid,
                                @RequestParam(value = "pnum", required = true, defaultValue = "1") Integer pnum,
                                @RequestParam(value = "platform", required = false, defaultValue = "-1") Integer platform) {
        log.debug("REST request to get Content : {}", id, appkey, pnum, platform);

        String queryFlag = HTTPUtil.getParam(request, "queryflag");//score
        //添加个默认值
        queryFlag = StringUtils.isEmpty(queryFlag) ? "-1" : queryFlag;

        double timeLimit = -1D;
        if (Double.valueOf(queryFlag) < 0) {//第一页
            timeLimit = System.currentTimeMillis();
        } else {
            timeLimit = new BigDecimal(queryFlag).doubleValue();
        }
        ScoreRange scoreRange = new ScoreRange(-1, timeLimit, PCOUNT, ScoreSort.DESC);

        ScoreRangeRows<ContentVM> returnContentVM = contentService.findAllContentVM(appkey, platform, scoreRange, id, pid, pnum);

        //标签轮播图
        List<ContentVM> headitems = advertiseService.queryContentVMeByLineKey(AskUtil.getAdvertiseLinekey(appkey, platform, AdvertiseDomain.CAROUSEL));

        JSONObject jsonObject = ResultCodeConstants.SUCCESS.getJsonObject();
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("range", new ScoreRangeDTO(returnContentVM.getRange()));
        returnMap.put("rows", returnContentVM.getRows());
        returnMap.put("pnum", ++pnum);
        returnMap.put("headitems", headitems);
        jsonObject.put("result", returnMap);
        return jsonObject.toString();
    }

    @PostMapping("/contents-wiki")
    @ApiOperation(value = "php调用,wiki保存", response = Boolean.class)
    public ResponseEntity<Boolean> wikipost(
        @Valid @RequestBody ContentWikiVM wikiVM) {
        contentService.postContentWiki(ContentWikiVMtoContent(wikiVM));
        return ResponseEntity.ok(true);
    }

    /**
     * {"archiveid": 100000,"title": "title:100000111111","describe": "describe:100000","pic": "pic","author": "author","gameid": "102159","weburl": "weburl","publishtime": 1493196033000}
     *
     * @param cmsVM
     * @return
     */
    @PostMapping("/contents-cms")
    @ApiOperation(value = "php调用,cms文章保存", response = Boolean.class)
    public ResponseEntity<Boolean> post(@Valid @RequestBody ContentCmsVM cmsVM) {
        contentService.postContent(toContent(cmsVM));
        return ResponseEntity.ok(true);
    }


    @PutMapping("/content-status")
    @ApiOperation(value = "修改状态，删除或者恢复", response = Boolean.class)
    public ResponseEntity<Boolean> updatestatus(
        @RequestParam(value = "archiveid", required = true) String archiveid,
        @RequestParam(value = "status", required = false, defaultValue = "removed") String status) {
        ValidStatus validStatus = ValidStatus.valueOf(status);


        boolean bval = contentService.updateContentStatus(CommentUtil.genCommentId(archiveid, CommentDomain.CMS_COMMENT), validStatus);
        return ResponseEntity.ok(bval);
    }


    @GetMapping("/contents-suggest-tools")
    @Timed
    @ApiOperation(value = "获取推荐搜索接口-tools后台使用", response = List.class)
    public ResponseEntity<Map> getContentSuggestTools() {
        log.debug("REST request to get getContentSuggestTools : {}");
        return new ResponseEntity(contentService.getContentSuggestTools(), HttpStatus.OK);
    }


    @GetMapping("/contents-suggest-set")
    @Timed
    @ApiOperation(value = "获取推荐搜索接口-tools后台使用", response = List.class)
    public ResponseEntity<Boolean> getContentSuggestSet(@RequestParam(value = "key", required = true) String key,
                                                        @RequestParam(value = "value", required = true) String value) {
        log.debug("REST request to get getContentSuggestSet : {}");
        contentService.hset(key, value);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/contents-suggest-sort")
    @Timed
    @ApiOperation(value = "获取推荐搜索接口-tools后台使用", response = List.class)
    public ResponseEntity<Boolean> getContentSuggestSort(@RequestParam(value = "firsttext", required = true) String firsttext,
                                                         @RequestParam(value = "nexttext", required = true) String nexttext) {
        log.debug("REST request to get getContentSuggestDel : {}");
        contentService.sort(firsttext, nexttext);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/contents-suggest-del")
    @Timed
    @ApiOperation(value = "获取推荐搜索接口-tools后台使用", response = List.class)
    public ResponseEntity<Boolean> getContentSuggestDel(@RequestParam(value = "key", required = true) String key) {
        log.debug("REST request to get getContentSuggestDel : {}");
        contentService.hdel(key);
        return ResponseEntity.ok(true);
    }

    @GetMapping("/contents-suggest")
    @Timed
    @ApiOperation(value = "获取推荐搜索接口", response = List.class)
    public ResponseEntity<List<String>> getContentSuggest() {
        log.debug("REST request to get getContentSuggest : {}");
        return new ResponseEntity(contentService.getContentSuggest(), HttpStatus.OK);
    }


    @GetMapping("/contents-search")
    @Timed
    @ApiOperation(value = "搜索WIki(游戏)接口")
    public ResponseEntity getContentSearch(@RequestParam(value = "pnum", required = false, defaultValue = "1") Integer pnum,
                                           @RequestParam(value = "psize", required = false, defaultValue = "20") Integer psize,
                                           @RequestParam(value = "type", required = false, defaultValue = "1") Integer type,
                                           @RequestParam(value = "text", required = false) String text) {
        log.debug("REST request to get getContentSuggest : {}");
        Pageable pageable = new PageRequest(pnum, psize);

        Page<ContentVM> contentVMPage = contentService.searchContent(pageable, text);

        Page page = new PageImpl(contentVMPage.getContent(), pageable, contentVMPage.getTotalElements());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contents-search");
        return new ResponseEntity(contentVMPage.getContent(), headers, HttpStatus.OK);
    }


    private Content toContent(ContentCmsVM cmsVM) {
        Content content = new Content();
        if (cmsVM.getSource() == ContentSource.CMS_ARCHIVE.getCode()) {
            content.setCommentId(CommentUtil.genCommentId(String.valueOf(cmsVM.getArchiveid()), CommentDomain.CMS_COMMENT));
        }
        content.setDescription(cmsVM.getDescribe());
        content.setSource(cmsVM.getSource());
        content.setRemoveStatus(cmsVM.getRemoveStatus());
        content.setTitle(cmsVM.getTitle());
        content.setPic(cmsVM.getPic());
        content.setAuthor(cmsVM.getAuthor());
        content.setGameId(cmsVM.getGameid());
        content.setPublishTime(new Date(cmsVM.getPublishtime()));
        content.setWebUrl(cmsVM.getWeburl());
        content.setCreateDate(new Date());
        return content;
    }


    private Content ContentWikiVMtoContent(ContentWikiVM wikiVM) {
        Content content = new Content();
        content.setCommentId(CommentUtil.genCommentId(wikiVM.getWikikey() + "|" + wikiVM.getTitle(), CommentDomain.UGCWIKI_COMMENT));
        content.setSource(ContentSource.WIKI.getCode());
        content.setTitle(wikiVM.getTitle());
        content.setDescription(wikiVM.getWikiname());
        content.setPublishTime(new Date(wikiVM.getPublishtime()));
        content.setWebUrl(wikiVM.getWeburl());
        content.setCreateDate(new Date());
        return content;
    }
}
