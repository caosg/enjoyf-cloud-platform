package com.enjoyf.platform.messageservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.messageservice.domain.PushApp;
import com.enjoyf.platform.messageservice.service.PushAppService;
import com.enjoyf.platform.messageservice.web.rest.util.HeaderUtil;
import com.enjoyf.platform.messageservice.web.rest.util.PaginationUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing PushApp.
 */
@RestController
@RequestMapping("/api")
@Api(value = "管理APP和推送信息的信息，多用于后台管理")
public class PushAppResource {

    private final Logger log = LoggerFactory.getLogger(PushAppResource.class);

    private static final String ENTITY_NAME = "pushApp";

    private final PushAppService pushAppService;

    public PushAppResource(PushAppService pushAppService) {
        this.pushAppService = pushAppService;
    }

    /**
     * POST  /push-apps : Create a new pushApp.
     *
     * @param pushApp the pushApp to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pushApp, or with status 400 (Bad Request) if the pushApp has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/push-apps")
    @Timed
    @ApiOperation(value = "保存第三方推送app信息", response = PushApp.class)
    public ResponseEntity<PushApp> createPushApp(@Valid @RequestBody PushApp pushApp) throws URISyntaxException {
        log.debug("REST request to save PushApp : {}", pushApp);
        if (pushApp.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new pushApp cannot already have an ID")).body(null);
        }
        PushApp result = pushAppService.save(pushApp);
        return ResponseEntity.created(new URI("/api/push-apps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /push-apps : Updates an existing pushApp.
     *
     * @param pushApp the pushApp to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pushApp,
     * or with status 400 (Bad Request) if the pushApp is not valid,
     * or with status 500 (Internal Server Error) if the pushApp couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/push-apps")
    @Timed
    @ApiOperation(value = "修改第三方推送app信息", response = PushApp.class)
    public ResponseEntity<PushApp> updatePushApp(@Valid @RequestBody PushApp pushApp) throws URISyntaxException {
        log.debug("REST request to update PushApp : {}", pushApp);
        if (pushApp.getId() == null) {
            return createPushApp(pushApp);
        }
        PushApp result = pushAppService.save(pushApp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pushApp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /push-apps : get all the pushApps.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pushApps in body
     */
    @GetMapping("/push-apps")
    @Timed
    @ApiOperation(value = "获取第三方推送app信息列表", response = PushApp.class)
    public ResponseEntity<List<PushApp>> getAllPushApps(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of PushApps");
        Page<PushApp> page = pushAppService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/push-apps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * DELETE  /push-apps/:id : delete the "id" pushApp.
     *
     * @param appkey the id of the pushApp to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/push-apps/{appkey}")
    @Timed
    @ApiOperation(value = "删除第三方推送app信息", response = PushApp.class)
    public ResponseEntity<Void> deletePushApp(@PathVariable String appkey) {
        log.debug("REST request to delete PushApp : {}", appkey);
        pushAppService.delete(appkey);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME,appkey)).build();
    }

}
