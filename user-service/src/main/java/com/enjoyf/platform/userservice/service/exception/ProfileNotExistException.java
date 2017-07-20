package com.enjoyf.platform.userservice.service.exception;

/**
 *
 * ProfileNo 不存在异常
 * Created by shuguangcao on 2017/3/22.
 */
public class ProfileNotExistException extends UserException {

    public ProfileNotExistException(String message) {
        super(message);
    }

    public ProfileNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
