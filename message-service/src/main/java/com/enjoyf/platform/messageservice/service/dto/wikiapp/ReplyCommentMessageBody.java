package com.enjoyf.platform.messageservice.service.dto.wikiapp;

import com.enjoyf.platform.messageservice.service.dto.MessageBody;

/**
 * Created by ericliu on 2017/6/19.
 */
public class ReplyCommentMessageBody implements MessageBody {
    private Long commentId;
    private Long gameId;
    private Long replyUid;
    private Long replyId;

    public long getCommentId() {
        return commentId;
    }

    public void setCommentId(long commentId) {
        this.commentId = commentId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public long getReplyUid() {
        return replyUid;
    }

    public void setReplyUid(long replyUid) {
        this.replyUid = replyUid;
    }

    public long getReplyId() {
        return replyId;
    }

    public void setReplyId(long replyId) {
        this.replyId = replyId;
    }

    @Override
    public boolean checkBody() {
        return gameId != null && commentId != null && replyUid != null && replyId != null;
    }
}
