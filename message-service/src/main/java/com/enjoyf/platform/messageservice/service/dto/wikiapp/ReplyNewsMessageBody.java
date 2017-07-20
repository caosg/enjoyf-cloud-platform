package com.enjoyf.platform.messageservice.service.dto.wikiapp;

import com.enjoyf.platform.messageservice.service.dto.MessageBody;

/**
 * Created by ericliu on 2017/6/19.
 */
public class ReplyNewsMessageBody implements MessageBody {
    private String title;
    private String url;
    private Long replyUid;
    private Long replyId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
    public boolean checkBody() {
        return replyUid!=null && replyId!=null;
    }
}
