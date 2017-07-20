package com.enjoyf.platform.event.message.wikiapp;

import com.enjoyf.platform.event.Event;
import com.enjoyf.platform.event.message.MessageEventConstants;

/**
 * Created by ericliu on 2017/5/10.
 */
public class WikiAppMessageCommentUsefulEvent extends WikiAppMessageEvent {

    public WikiAppMessageCommentUsefulEvent() {
        super(WikiAppMessageEventType.WIKI_APP_COMMENT_USEFUL, MessageEventConstants.BIND_KEY);
    }

    private String appkey;
    private Long uid;
    private Long gameId;
    private Long commentId;
    private Long destUid;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

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

    public Long getDestUid() {
        return destUid;
    }

    public void setDestUid(Long destUid) {
        this.destUid = destUid;
    }

    @Override
    public String toString() {
        return "WikiAppMessageCommentUsefulEvent{" +
                "appkey='" + appkey + '\'' +
                ", uid=" + uid +
                ", commentId='" + commentId + '\'' +
                ", destUid=" + destUid +
                '}';
    }
}
