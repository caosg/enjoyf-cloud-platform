package com.enjoyf.platform.userservice.domain.enumeration;

/**
 * 登陆类型
 * Created by shuguangcao on 2017/3/20.
 */
public enum LoginDomain {

    MOBILE("mobile"),EMAIL("email"),EXPLORE("explore"),CLIENT("client"),
    RENREN("renren"),YOUKU("youku"),WXLOGIN("wxlogin"),WXSERVICE("wxservice"),
    SINAWEIBO("sinaweibo"),QWEIBO("qweibo"),QQ("qq"),WEIXIN("weixin");

    LoginDomain(String code) {
        this.code = code;
    }

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
