package com.enjoyf.platform.contentservice.domain.enumeration;

import com.google.common.base.Strings;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pengxu on 2017/6/21.
 */
public enum CommentRatingType {
    SCORE_SUM("score_sum"),//总分数
    SCORE_NUM("score_num"),//总人数
    FIVE_USER_SUM("five_user_sum"),//五星人数，下同
    FOUR_USER_SUM("four_user_sum"),
    THREE_USER_SUM("three_user_sum"),
    TWO_USER_SUM("two_user_sum"),
    ONE_USER_SUM("one_user_sum");


    CommentRatingType() {
    }

    CommentRatingType(String code) {
        this.code = code;
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
