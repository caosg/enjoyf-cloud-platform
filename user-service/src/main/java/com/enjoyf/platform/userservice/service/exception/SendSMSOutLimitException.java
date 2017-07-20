package com.enjoyf.platform.userservice.service.exception;

/**
 * 登陆账号重复抛出该异常
 * Created by shuguangcao on 2017/3/18.
 */
public class SendSMSOutLimitException extends  UserException{

    public SendSMSOutLimitException(String message) {
        super(message);
    }

    public SendSMSOutLimitException(String message, Throwable cause) {
        super(message, cause);
    }
}
