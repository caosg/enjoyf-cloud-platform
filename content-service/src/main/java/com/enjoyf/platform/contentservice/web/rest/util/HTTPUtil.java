package com.enjoyf.platform.contentservice.web.rest.util;


import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by zhimingli on 2017/5/12.
 */
public class HTTPUtil {

    // public static final String HEADER_JPARAM = "JParam";


    /**
     * 优先级  paramter-->head-->jParam
     *
     * @param request
     * @param key
     * @return
     */
    public static String getParam(HttpServletRequest request, String key) {
        String value = request.getParameter(key);
        if (!StringUtils.isEmpty(value)) {
            return value;
        }
        value = request.getHeader(key);
        if (!StringUtils.isEmpty(value)) {
            return value;
        }
        return value;
    }
}
