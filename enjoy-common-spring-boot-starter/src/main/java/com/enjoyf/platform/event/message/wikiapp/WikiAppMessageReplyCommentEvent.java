package com.enjoyf.platform.event.message.wikiapp;

import com.enjoyf.platform.event.Event;
import com.enjoyf.platform.event.message.MessageEventConstants;
import com.enjoyf.platform.event.message.MessageEventType;

/**
 * Created by ericliu on 2017/5/10.
 */
public class WikiAppMessageReplyCommentEvent extends WikiAppMessageEvent {

    public WikiAppMessageReplyCommentEvent() {
        super(WikiAppMessageEventType.WIKI_APP_REPLY_COMMENT, MessageEventConstants.BIND_KEY);
    }

    private String appkey;
    private long uid;

    private Long commentId;
    private Long gameId;
    private Long replyUid;
    private Long replyId;

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

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getReplyUid() {
        return replyUid;
    }

    public void setReplyUid(Long replyUid) {
        this.replyUid = replyUid;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    @Override
    public String toString() {
        return "WikiAppMessageReplyCommentEvent{" +
                "appkey='" + appkey + '\'' +
                ", uid=" + uid +
                ", commentId=" + commentId +
                ", gameId=" + gameId +
                ", replyUid=" + replyUid +
                ", replyId=" + replyId +
                '}';
    }
}
