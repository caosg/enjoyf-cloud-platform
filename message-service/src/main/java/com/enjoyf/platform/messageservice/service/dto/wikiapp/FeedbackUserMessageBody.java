package com.enjoyf.platform.messageservice.service.dto.wikiapp;

import com.enjoyf.platform.messageservice.service.dto.MessageBody;

/**
 * Created by ericliu on 2017/6/19.
 */
public class FeedbackUserMessageBody implements MessageBody {
    private Long feedbackUid;
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getFeedbackUid() {
        return feedbackUid;
    }

    public void setFeedbackUid(Long feedbackUid) {
        this.feedbackUid = feedbackUid;
    }

    @Override
    public boolean checkBody() {
        return feedbackUid != null;
    }
}
