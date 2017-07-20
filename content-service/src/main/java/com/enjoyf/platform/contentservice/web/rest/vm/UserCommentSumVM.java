package com.enjoyf.platform.contentservice.web.rest.vm;

import com.enjoyf.platform.contentservice.domain.enumeration.UserCommentSumFiled;

/**
 * Created by ericliu on 2017/6/23.
 */
public class UserCommentSumVM {
    private long uid;
    private UserCommentSumFiled filed;
    private long value;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public UserCommentSumFiled getFiled() {
        return filed;
    }

    public void setFiled(UserCommentSumFiled filed) {
        this.filed = filed;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
