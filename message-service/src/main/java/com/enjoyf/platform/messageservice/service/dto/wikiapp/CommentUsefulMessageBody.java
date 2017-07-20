package com.enjoyf.platform.messageservice.service.dto.wikiapp;

import com.enjoyf.platform.messageservice.service.dto.MessageBody;

/**
 * Created by ericliu on 2017/6/19.
 */
public class CommentUsefulMessageBody implements MessageBody {
    private Long gameId;
    private Long commentId;
    private Long destUid;

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

    public long getDestUid() {
        return destUid;
    }

    public void setDestUid(long destUid) {
        this.destUid = destUid;
    }

    @Override
    public boolean checkBody() {
        return gameId != null && commentId != null && destUid != null;
    }
}
