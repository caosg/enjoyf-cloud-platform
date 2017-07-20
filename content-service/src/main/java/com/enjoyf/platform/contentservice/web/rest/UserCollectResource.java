package com.enjoyf.platform.contentservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.common.ResultCodeConstants;
import com.enjoyf.platform.common.domain.comment.CommentDomain;
import com.enjoyf.platform.common.util.CollectionUtil;
import com.enjoyf.platform.common.util.CommentUtil;
import com.enjoyf.platform.common.util.HTTPUtil;
import com.enjoyf.platform.common.util.StringUtil;
import com.enjoyf.platform.contentservice.domain.CollectType;
import com.enjoyf.platform.contentservice.domain.Content;
import com.enjoyf.platform.contentservice.domain.UserCollect;
import com.enjoyf.platform.contentservice.service.ContentService;
import com.enjoyf.platform.contentservice.service.UserCollectService;
import com.enjoyf.platform.contentservice.service.dto.UserCollectDTO;
import com.enjoyf.platform.contentservice.web.rest.util.AskUtil;
import com.enjoyf.platform.page.PageRows;
import com.enjoyf.platform.page.Pagination;
import io.github.jhipster.web.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URISyntaxException;
import java.util.*;

/**
 * REST controller for managing UserCollect.
 */
@RestController
@RequestMapping("/api")
public class UserCollectResource {

    private final Logger log = LoggerFactory.getLogger(UserCollectResource.class);

    private static final String ENTITY_NAME = "userCollect";

    private final UserCollectService userCollectService;
    private final ContentService contentService;

    @Autowired
    private AskUtil askUtil;


    public UserCollectResource(UserCollectService userCollectService, ContentService contentService) {
        this.userCollectService = userCollectService;
        this.contentService = contentService;
    }

    /**
     * POST  /user-collects : Create a new userCollect.
     *
     * @param request
     * @return the ResponseEntity with status 201 (Created) and with body the new userCollect, or with status 400 (Bad Request) if the userCollect has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-collects")
    @Timed
    public String createUserCollect(HttpServletRequest request) throws URISyntaxException {
        JSONObject jsonObject = ResultCodeConstants.SUCCESS.getJsonObject();
        log.debug("REST request to save UserCollect  request: {}", request);
        String profileId = HTTPUtil.getParam(request, "pid");
        String appkey = HTTPUtil.getParam(request, "appkey");
        String ctype = HTTPUtil.getParam(request, "ctype");
        String contentid = HTTPUtil.getParam(request, "contentid");
        String title = HTTPUtil.getParam(request, "title");

        if (StringUtil.isEmpty(ctype)) {
            return ResultCodeConstants.PARAM_EMPTY.getJsonString();
        }
        CollectType collectType = CollectType.getByCode(Integer.parseInt(ctype));
        if (StringUtil.isEmpty(profileId) || StringUtil.isEmpty(appkey) || collectType == null) {
            return ResultCodeConstants.PARAM_EMPTY.getJsonString();
        }
        if ((collectType.equals(CollectType.CMS) && StringUtil.isEmpty(contentid)) || (collectType.equals(CollectType.WIKI) && (StringUtil.isEmpty(title) || StringUtil.isEmpty(contentid)))) {
            return ResultCodeConstants.PARAM_EMPTY.getJsonString();
        }
        String commentId = CommentUtil.genCommentId(contentid, CommentDomain.CMS_COMMENT);
        if (collectType.equals(CollectType.WIKI)) {
            commentId = CommentUtil.genCommentId(contentid + "|" + title, CommentDomain.UGCWIKI_COMMENT);
        }

        try {
            Content content = contentService.findByCommentId(commentId);
            if (content == null) {
                return ResultCodeConstants.CONTENT_NOT_EXIST.getJsonString();
            }

            UserCollect userCollect = userCollectService.findOneByContentId(content.getId());
            if (userCollect != null) {
                return ResultCodeConstants.USER_COLLECT_EXIST.getJsonString();
            }
            userCollect = new UserCollect();
            userCollect.setProfileId(profileId);
            userCollect.setAppkey(appkey);
            userCollect.setCreateTime(new Date());
            userCollect.setCollectType(collectType.getCode());
            userCollect.setContentId(content.getId());

            UserCollect result = userCollectService.save(userCollect);
            if (result.getId() <= 0) {
                return ResultCodeConstants.FAILED.getJsonString();
            }
            Map<String, Long> returnMap = new HashMap<>();
            returnMap.put("collect_id", result.getId());
            jsonObject.put("result", returnMap);
        } catch (ServiceException e) {
            log.error(this.getClass().getName() + " occured ServiceException.e:", e);
            return ResultCodeConstants.ERROR.getJsonString();
        } catch (Exception e) {
            log.error(this.getClass().getName() + " occured ServiceException.e:", e);
            return ResultCodeConstants.ERROR.getJsonString();
        }
        return jsonObject.toString();
    }

    /**
     * PUT  /user-collects : Updates an existing userCollect.
     *
     * @param userCollect the userCollect to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userCollect,
     * or with status 400 (Bad Request) if the userCollect is not valid,
     * or with status 500 (Internal Server Error) if the userCollect couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
//    @PutMapping("/user-collects")
//    @Timed
//    public ResponseEntity<UserCollect> updateUserCollect(@RequestBody UserCollect userCollect) throws URISyntaxException {
//        log.debug("REST request to update UserCollect : {}", userCollect);
//
//        UserCollect result = userCollectService.save(userCollect);
//        return ResponseEntity.ok()
//            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userCollect.getId().toString()))
//            .body(result);
//    }

    /**
     * GET  /user-collects : get all the userCollects.
     *
     * @param page the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userCollects in body
     */
    @GetMapping("/user-collects")
    @Timed
    public String getAllUserCollects(@RequestParam(value = "pnum", required = false, defaultValue = "1") Integer page,
                                     @RequestParam(value = "psize", required = false, defaultValue = "10") Integer count,
                                     HttpServletRequest request, HttpServletResponse response) {
        log.debug("REST request to get a page of UserCollects");

        JSONObject jsonObject = ResultCodeConstants.SUCCESS.getJsonObject();
        try {
            String profileId = HTTPUtil.getParam(request, "pid");
            Pagination pagination = new Pagination(count * page, page, count);
            if (StringUtil.isEmpty(profileId)) {
                return ResultCodeConstants.PARAM_EMPTY.getJsonString();
            }
            PageRows<UserCollect> pageRows = userCollectService.queryCollectByCache(profileId, pagination);
            Map<String, Object> returnMap = new HashMap<String, Object>();
            if (pageRows == null) {
                returnMap.put("rows", new ArrayList<UserCollectDTO>());
                returnMap.put("page", pagination);
                jsonObject.put("result", returnMap);
                return jsonObject.toString();
            }

            List<UserCollectDTO> list = askUtil.buildCollect(pageRows.getRows(), contentService);
            returnMap.put("rows", CollectionUtil.isEmpty(list) ? new ArrayList<UserCollectDTO>() : list);
            returnMap.put("page", pageRows.getPage());
            jsonObject.put("result", returnMap);
            return jsonObject.toString();
        } catch (ServiceException e) {
            log.error(this.getClass().getName() + " occured ServiceException.e:", e);
            return ResultCodeConstants.ERROR.getJsonString();
        } catch (Exception e) {
            log.error(this.getClass().getName() + " occured ServiceException.e:", e);
            return ResultCodeConstants.ERROR.getJsonString();
        }
    }

    /**
     * GET  /user-collects/:id : get the "id" userCollect.
     *
     * @param id the id of the userCollect to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userCollect, or with status 404 (Not Found)
     */
    @GetMapping("/user-collects/{id}")
    @Timed
    public ResponseEntity<UserCollect> getUserCollect(@PathVariable Long id) {
        log.debug("REST request to get UserCollect : {}", id);
        UserCollect userCollect = userCollectService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(userCollect));
    }

    /**
     * DELETE  /user-collects/:id : delete the "id" userCollect.
     *
     * @param
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-collects")
    @Timed
    public String deleteUserCollect(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = ResultCodeConstants.SUCCESS.getJsonObject();
        try {
            String profileId = HTTPUtil.getParam(request, "pid");
            String ids = HTTPUtil.getParam(request, "ids");
            log.debug("REST request to delete UserCollect : {},{}", ids, profileId);
            if (StringUtil.isEmpty(profileId) || StringUtil.isEmpty(ids)) {
                return ResultCodeConstants.PARAM_EMPTY.getJsonString();
            }
            String[] idArray = new String[0];
            try {
                idArray = ids.split("\\,");
            } catch (Exception e) {
                return ResultCodeConstants.FORMAT_ERROR.getJsonString();
            }
            Set<Long> idSet = new HashSet<Long>();
            for (int i = 0; i < idArray.length; i++) {
                idSet.add(Long.parseLong(idArray[i]));
            }
            boolean bool = userCollectService.deleteUserCollect(idSet, profileId);
            if (bool) {
                return jsonObject.toString();
            }
            return ResultCodeConstants.FAILED.getJsonString();
        } catch (ServiceException e) {
            return ResultCodeConstants.ERROR.getJsonString();
        } catch (Exception e) {
            log.error(this.getClass().getName() + " occured ServiceException.e:", e);
            return ResultCodeConstants.ERROR.getJsonString();
        }
    }


    @GetMapping(value = "/user-collects/querystatus")
    public String queryStatus(HttpServletRequest request, HttpServletResponse response) {
        JSONObject jsonObject = ResultCodeConstants.SUCCESS.getJsonObject();
        String callback = HTTPUtil.getParam(request, "callback");
        try {
            String profileId = HTTPUtil.getParam(request, "pid");
            String ctype = HTTPUtil.getParam(request, "ctype");
            String cotentid = HTTPUtil.getParam(request, "contentid");
            String title = HTTPUtil.getParam(request, "title");
            if (StringUtil.isEmpty(profileId) || StringUtil.isEmpty(ctype)) {
                return StringUtil.isEmpty(callback) ? ResultCodeConstants.PARAM_EMPTY.getJsonString() : ResultCodeConstants.PARAM_EMPTY.getJsonString(callback);
            }
            CollectType collectType = CollectType.getByCode(Integer.parseInt(ctype));
            if ((collectType.equals(CollectType.CMS) && StringUtil.isEmpty(cotentid)) || (collectType.equals(CollectType.WIKI) && (StringUtil.isEmpty(title) || StringUtil.isEmpty(cotentid)))) {
                return StringUtil.isEmpty(callback) ? ResultCodeConstants.PARAM_EMPTY.getJsonString() : ResultCodeConstants.PARAM_EMPTY.getJsonString(callback);
            }

            String commentId = CommentUtil.genCommentId(cotentid, CommentDomain.CMS_COMMENT);
            if (collectType.equals(CollectType.WIKI)) {
                commentId = CommentUtil.genCommentId(cotentid + "|" + title, CommentDomain.UGCWIKI_COMMENT);
            }
            Content content = contentService.findByCommentId(commentId);
            if (content == null) {
                return StringUtil.isEmpty(callback) ? ResultCodeConstants.CONTENT_NOT_EXIST.getJsonString() : ResultCodeConstants.CONTENT_NOT_EXIST.getJsonString(callback);
            }

            UserCollect userCollect = userCollectService.findByProfileIdAndContentId(profileId, content.getId());
            if (userCollect != null) {
                Map<String, Long> map = new HashMap<String, Long>();
                map.put("collect_id", userCollect.getId());
                jsonObject.put("result", map);
                return StringUtil.isEmpty(callback) ? jsonObject.toString() : callback + "([" + jsonObject.toString() + "])";
            } else {
                return StringUtil.isEmpty(callback) ? ResultCodeConstants.NOT_COLLECT.getJsonString() : ResultCodeConstants.NOT_COLLECT.getJsonString(callback);
            }
        } catch (Exception e) {
            log.error(this.getClass().getName() + " occured ServiceException.e:", e);
            return StringUtil.isEmpty(callback) ? ResultCodeConstants.ERROR.getJsonString() : ResultCodeConstants.ERROR.getJsonString(callback);

        }
    }


}
