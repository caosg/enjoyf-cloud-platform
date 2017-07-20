package com.enjoyf.platform.messageservice.service.dto.wikiapp;

import com.enjoyf.platform.messageservice.service.dto.MessageBody;

/**
 * Created by ericliu on 2017/6/19.
 */
public class FeedbackCommentMessageBody implements MessageBody {
    private Long feedbackUid;
    private Long gameId;
    private String body;
    private Long commentId;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getFeedbackUid() {
        return feedbackUid;
    }

    public void setFeedbackUid(Long feedbackUid) {
        this.feedbackUid = feedbackUid;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    @Override
    public boolean checkBody() {
        return gameId != null && feedbackUid != null && commentId != null;
    }
}
