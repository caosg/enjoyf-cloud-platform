package com.enjoyf.platform.event.message.wikiapp;

import com.enjoyf.platform.event.message.MessageEventConstants;

/**
 * Created by ericliu on 2017/5/10.
 */
public class WikiAppMessageFeedbackCommentEvent extends WikiAppMessageEvent {

    public WikiAppMessageFeedbackCommentEvent() {
        super(WikiAppMessageEventType.WIKI_APP_FEEDBACK_COMMENT, MessageEventConstants.BIND_KEY);
    }

    private String appkey;
    private long uid;

    private Long feedbackUid;
    private Long gameId;
    private String body;
    private Long commentId;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public Long getFeedbackUid() {
        return feedbackUid;
    }

    public void setFeedbackUid(Long feedbackUid) {
        this.feedbackUid = feedbackUid;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
}
