package com.enjoyf.platform.userservice.service.exception;

/**
 * Created by shuguangcao on 2017/3/22.
 */
public class ProfileBindedException extends UserException {

    public ProfileBindedException(String message) {
        super(message);
    }

    public ProfileBindedException(String message, Throwable cause) {
        super(message, cause);
    }
}
