package com.enjoyf.platform.messageservice.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.common.domain.enumeration.ValidStatus;
import com.enjoyf.platform.event.message.MessageAppPushEvent;
import com.enjoyf.platform.event.message.enumration.PushType;
import com.enjoyf.platform.messageservice.domain.AppPushMessage;
import com.enjoyf.platform.messageservice.domain.enumration.SendType;
import com.enjoyf.platform.messageservice.event.MessageEventProcess;
import com.enjoyf.platform.messageservice.service.AppPushMessageService;
import com.enjoyf.platform.messageservice.service.mapper.PushMessageMapper;
import com.enjoyf.platform.messageservice.web.rest.util.HeaderUtil;
import com.enjoyf.platform.messageservice.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
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
import java.util.Optional;

/**
 * REST controller for managing AppPushMessage.
 */
@RestController
@RequestMapping("/api")
public class AppPushMessageResource {

    private final Logger log = LoggerFactory.getLogger(AppPushMessageResource.class);

    private static final String ENTITY_NAME = "appPushMessage";

    private final AppPushMessageService appPushMessageService;
    private final MessageEventProcess messageEventProcess;

    public AppPushMessageResource(AppPushMessageService appPushMessageService, MessageEventProcess messageEventProcess) {
        this.appPushMessageService = appPushMessageService;
        this.messageEventProcess = messageEventProcess;
    }

    /**
     * POST  /app-push-messages : Create a new appPushMessage.
     *
     * @param appPushMessage the appPushMessage to create
     * @return the ResponseEntity with status 201 (Created) and with body the new appPushMessage, or with status 400 (Bad Request) if the appPushMessage has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/app-push-messages")
    @Timed
    public ResponseEntity<AppPushMessage> createAppPushMessage(@Valid @RequestBody AppPushMessage appPushMessage) throws URISyntaxException {
        log.debug("REST request to save AppPushMessage : {}", appPushMessage);
        if (appPushMessage.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new appPushMessage cannot already have an ID")).body(null);
        }
        AppPushMessage result = appPushMessageService.save(appPushMessage);

        if (SendType.now.equals(result.getSendType())) {
            MessageAppPushEvent appPushEvent = PushMessageMapper.MAPPER.toMessageAppPushEvent(appPushMessage);
            messageEventProcess.send(appPushEvent);
        }

        return ResponseEntity.created(new URI("/api/app-push-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /app-push-messages : Updates an existing appPushMessage.
     *
     * @param appPushMessage the appPushMessage to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated appPushMessage,
     * or with status 400 (Bad Request) if the appPushMessage is not valid,
     * or with status 500 (Internal Server Error) if the appPushMessage couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/app-push-messages")
    @Timed
    public ResponseEntity<AppPushMessage> updateAppPushMessage(@Valid @RequestBody AppPushMessage appPushMessage) throws URISyntaxException {
        log.debug("REST request to update AppPushMessage : {}", appPushMessage);
        if (appPushMessage.getId() == null) {
            return createAppPushMessage(appPushMessage);
        }
        AppPushMessage result = appPushMessageService.save(appPushMessage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, appPushMessage.getId().toString()))
            .body(result);
    }

    /**
     * GET  /app-push-messages : get all the appPushMessages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of appPushMessages in body
     */
    @GetMapping("/app-push-messages")
    @Timed
    public ResponseEntity<List<AppPushMessage>> getAllAppPushMessages(
        @RequestParam(required = false) ValidStatus sendStatus,
        @RequestParam(required = false) ValidStatus removeStatus,
        @RequestParam(required = false) SendType sendType,
        @RequestParam(required = false) PushType pushType,
        @ApiParam Pageable pageable) {
        log.debug("REST request to get a page of AppPushMessages");
        Page<AppPushMessage> page = appPushMessageService.findAll(sendStatus, removeStatus, pushType, sendType, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/app-push-messages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /app-push-messages/:id : get the "id" appPushMessage.
     *
     * @param id the id of the appPushMessage to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the appPushMessage, or with status 404 (Not Found)
     */
    @GetMapping("/app-push-messages/{id}")
    @Timed
    public ResponseEntity<AppPushMessage> getAppPushMessage(@PathVariable Long id) {
        log.debug("REST request to get AppPushMessage : {}", id);
        AppPushMessage appPushMessage = appPushMessageService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(appPushMessage));
    }

    /**
     * DELETE  /app-push-messages/:id : delete the "id" appPushMessage.
     *
     * @param id the id of the appPushMessage to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/app-push-messages/{id}")
    @Timed
    public ResponseEntity<Void> deleteAppPushMessage(@PathVariable Long id) {
        log.debug("REST request to delete AppPushMessage : {}", id);
        appPushMessageService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
