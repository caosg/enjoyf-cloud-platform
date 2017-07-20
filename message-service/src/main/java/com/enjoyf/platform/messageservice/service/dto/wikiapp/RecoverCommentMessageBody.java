package com.enjoyf.platform.messageservice.service.dto.wikiapp;

import com.enjoyf.platform.messageservice.service.dto.MessageBody;

/**
 * Created by ericliu on 2017/6/19.
 */
public class RecoverCommentMessageBody implements MessageBody {
    //    private Long commentUid;
    private Long gameId;
    private Long commentId;
    private String reason;

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

    @Override
    public boolean checkBody() {
        return gameId != null && commentId != null;
    }
}
