package com.enjoyf.platform.profileservice.service.contentservice.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A UserCommentSumDTO.
 */
public class UserCommentSumDTO implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long uid;

    private Long commentSum;

    private Long usefulSum;

    public Long getUid() {
        return uid;
    }

    public UserCommentSumDTO uid(Long uid) {
        this.uid = uid;
        return this;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getCommentSum() {
        return commentSum;
    }

    public UserCommentSumDTO commentSum(Long commentSum) {
        this.commentSum = commentSum;
        return this;
    }

    public void setCommentSum(Long commentSum) {
        this.commentSum = commentSum;
    }

    public Long getUsefulSum() {
        return usefulSum;
    }

    public UserCommentSumDTO usefulSum(Long usefulSum) {
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
        UserCommentSumDTO userCommentSumDTO = (UserCommentSumDTO) o;
        if (userCommentSumDTO.getUid() == null || getUid() == null) {
            return false;
        }
        return Objects.equals(getUid(), userCommentSumDTO.getUid());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUid());
    }

    @Override
    public String toString() {
        return "UserCommentSumDTO{" +
            "uid='" + getUid() + "'" +
            ", commentSum='" + getCommentSum() + "'" +
            ", usefulSum='" + getUsefulSum() + "'" +
            "}";
    }
}
