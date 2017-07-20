package com.enjoyf.platform.userservice.service.exception;

/**
 * 关键字敏感异常
 * Created by shuguangcao on 2017/6/13.
 */
public class WordSanitizeException  extends UserException{
    public WordSanitizeException() {
    }

    public WordSanitizeException(String message) {
        super(message);
    }

    public WordSanitizeException(String message, Throwable cause) {
        super(message, cause);
    }
}
