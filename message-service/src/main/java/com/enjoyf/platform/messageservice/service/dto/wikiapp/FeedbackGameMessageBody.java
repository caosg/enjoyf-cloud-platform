package com.enjoyf.platform.messageservice.service.dto.wikiapp;

import com.enjoyf.platform.messageservice.service.dto.MessageBody;

/**
 * Created by ericliu on 2017/6/19.
 */
public class FeedbackGameMessageBody implements MessageBody {
    private Long gameId;
    private String body;

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

    @Override
    public boolean checkBody() {
        return gameId != null;
    }
}
