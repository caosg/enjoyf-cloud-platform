package com.enjoyf.platform.messageservice.service.exception;

/**
 * 登陆账号重复抛出该异常
 * Created by shuguangcao on 2017/3/18.
 */
public class PushAppNotExsitsException extends MessageException {

    public PushAppNotExsitsException(String message) {
        super(message);
    }

    public PushAppNotExsitsException(String message, Throwable cause) {
        super(message, cause);
    }
}
