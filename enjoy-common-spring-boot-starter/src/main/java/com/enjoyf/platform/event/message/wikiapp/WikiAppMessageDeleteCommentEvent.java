package com.enjoyf.platform.event.message.wikiapp;

import com.enjoyf.platform.event.message.MessageEventConstants;

/**
 * Created by ericliu on 2017/5/10.
 */
public class WikiAppMessageDeleteCommentEvent extends WikiAppMessageEvent {

    public WikiAppMessageDeleteCommentEvent() {
        super(WikiAppMessageEventType.WIKI_APP_DELETE_COMMENT, MessageEventConstants.BIND_KEY);
    }

    private String appkey;
    private long uid;

//    private Long commentUid;
    private Long gameId;
    private Long commentId;
    private String reason;

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

//    public Long getCommentUid() {
//        return commentUid;
//    }
//
//    public void setCommentUid(Long commentUid) {
//        this.commentUid = commentUid;
//    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
