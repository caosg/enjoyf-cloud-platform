package com.enjoyf.platform.userservice.service.exception;

/**
 * Created by shuguangcao on 2017/3/18.
 */
public class NickExistException extends UserException {
    public NickExistException(String message) {
        super(message);
    }

    public NickExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
