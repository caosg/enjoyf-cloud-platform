package com.enjoyf.platform.contentservice.domain;

import java.io.Serializable;
import java.util.Objects;

/**
 * A UserCommentSum.
 */
public class UserCommentSum implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long uid;

    private Long commentSum;

    private Long usefulSum;

    public Long getUid() {
        return uid;
    }

    public UserCommentSum uid(Long uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getCommentSum() {
        return commentSum;
    }

    public UserCommentSum commentSum(Long commentSum) {
        this.commentSum = commentSum;
        return this;
    }

    public void setCommentSum(Long commentSum) {
        this.commentSum = commentSum;
    }

    public Long getUsefulSum() {
        return usefulSum;
    }

    public UserCommentSum usefulSum(Long usefulSum) {
        this.usefulSum = usefulSum;
        return this;
    }

    public void setUsefulSum(Long usefulSum) {
        this.usefulSum = usefulSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserCommentSum userCommentSum = (UserCommentSum) o;
        if (userCommentSum.getUid() == null || getUid() == null) {
            return false;
        }
        return Objects.equals(getUid(), userCommentSum.getUid());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUid());
    }

    @Override
    public String toString() {
        return "UserCommentSum{" +
            "uid='" + getUid() + "'" +
            ", commentSum='" + getCommentSum() + "'" +
            ", usefulSum='" + getUsefulSum() + "'" +
            "}";
    }
}
