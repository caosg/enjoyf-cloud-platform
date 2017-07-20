package com.enjoyf.platform.messageservice.web.rest;


import com.codahale.metrics.annotation.Timed;
import com.enjoyf.platform.event.message.wikiapp.*;
import com.enjoyf.platform.messageservice.event.MessageEventProcess;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ericliu on 2017/6/12.
 */
@RestController
@RequestMapping("/api/wikiapp-message-events")
public class WikiappMessageEventResource {
    private final MessageEventProcess messageEventProcess;

    public WikiappMessageEventResource(MessageEventProcess messageEventProcess) {
        this.messageEventProcess = messageEventProcess;
    }

    @PostMapping("/replynews")
    @Timed
    @ApiOperation(value = "上报回复新闻的事件", response = String.class)
    public ResponseEntity<String> send(@RequestBody WikiAppMessageReplyNewsEvent event) {
        messageEventProcess.send(event);
        return ResponseEntity.ok("success");
    }


    @PostMapping("/replycomment")
    @Timed
    @ApiOperation(value = "上报回复点评的事件", response = String.class)
    public ResponseEntity<String> send(@RequestBody WikiAppMessageReplyCommentEvent event) {
        messageEventProcess.send(event);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/commentuseful")
    @Timed
    @ApiOperation(value = "上报点评有用的事件", response = String.class)
    public ResponseEntity<String> send(@RequestBody WikiAppMessageCommentUsefulEvent event) {
        messageEventProcess.send(event);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/feedbackcomment")
    @Timed
    @ApiOperation(value = "上报反馈点评处理的事件", response = String.class)
    public ResponseEntity<String> saveFeedbackComment(@RequestBody WikiAppMessageFeedbackCommentEvent event) {
        messageEventProcess.send(event);
        return ResponseEntity.ok("success");
    }


    @PostMapping("/feedbackuser")
    @Timed
    @ApiOperation(value = "上报反馈用户的事件", response = String.class)
    public ResponseEntity<String> saveFeedbackUser(@RequestBody WikiAppMessageFeedbackUserEvent event) {
        messageEventProcess.send(event);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/feedbackgame")
    @Timed
    @ApiOperation(value = "上报反馈游戏的事件", response = String.class)
    public ResponseEntity<String> saveFeedbackGame(@RequestBody WikiAppMessageFeedbackGameEvent event) {
        messageEventProcess.send(event);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/deletecomment")
    @Timed
    @ApiOperation(value = "上报删除点评的事件", response = String.class)
    public ResponseEntity<String> saveDeleteComment(@RequestBody WikiAppMessageDeleteCommentEvent event) {
        messageEventProcess.send(event);
        return ResponseEntity.ok("success");
    }

    @PostMapping("/recovercomment")
    @Timed
    @ApiOperation(value = "上报恢复点评的事件", response = String.class)
    public ResponseEntity<String> saveRecoverComment(@RequestBody WikiAppMessageRecoverCommentEvent event) {
        messageEventProcess.send(event);
        return ResponseEntity.ok("success");
    }
}
