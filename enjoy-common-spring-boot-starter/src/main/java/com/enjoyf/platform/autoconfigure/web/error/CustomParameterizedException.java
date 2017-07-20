package com.enjoyf.platform.autoconfigure.web.error;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shuguangcao on 2017/7/3.
 */
public class CustomParameterizedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final String PARAM = "param";

    private final String message;

    private final Map<String, String> paramMap = new HashMap<>();

    public CustomParameterizedException(String message, String... params) {
        super(message);
        this.message = message;
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                paramMap.put(PARAM + i, params[i]);
            }
        }
    }

    public CustomParameterizedException(String message, Map<String, String> paramMap) {
        super(message);
        this.message = message;
        this.paramMap.putAll(paramMap);
    }

    public ParameterizedErrorVM getErrorVM() {
        return new ParameterizedErrorVM(message, paramMap);
    }
}
