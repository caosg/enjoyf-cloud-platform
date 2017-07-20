package com.enjoyf.platform.autoconfigure.web.error;

/**
 * Created by shuguangcao on 2017/7/3.
 */
public class BusinessException extends RuntimeException {
    private final String message;
    private final String description;


    public BusinessException(String message) {
        this(message,"");
    }

    public BusinessException(String message, String description) {
        this.message = message;
        this.description = description;
    }


    @Override
    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }

    public ErrorVM getErrorVM(){
        return new ErrorVM(message,description);
    }
}
