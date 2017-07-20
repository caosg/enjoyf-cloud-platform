package com.enjoyf.platform.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by pengxu on 2017/5/15.
 */
public class CookieUtil {
    public static final String UNO = "joyme_u";

    public CookieUtil() {
    }

    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null && name != null && name.length() != 0) {
            for(int i = 0; i < cookies.length; ++i) {
                if(name.equals(cookies[i].getName())) {
                    return cookies[i];
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null && name != null && name.length() != 0) {
            for(int i = 0; i < cookies.length; ++i) {
                if(name.equals(cookies[i].getName())) {
                    return cookies[i].getValue();
                }
            }

            return null;
        } else {
            return null;
        }
    }

    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, String domain, int maxAge) {
        Cookie cookie = new Cookie(name, value == null?"":value);
        cookie.setMaxAge(maxAge);
        cookie.setPath(getPath(request));
        cookie.setDomain("." + domain);
        response.addCookie(cookie);
    }

    private static String getPath(HttpServletRequest request) {
        String path = request.getContextPath();
        return path != null && path.length() != 0?path:"/";
    }
}
