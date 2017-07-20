package com.enjoyf.platform.userservice.service.exception;

/**
 * Created by shuguangcao on 2017/3/27.
 */
public class MobileException extends UserException {

    public MobileException(String message) {
        super(message);
    }

    public MobileException(String message, Throwable cause) {
        super(message, cause);
    }


    public MobileException() {
        super();
    }
}
