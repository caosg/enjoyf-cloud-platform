package com.enjoyf.platform.autoconfigure.security.jwt;

import org.springframework.security.core.Authentication;

import java.util.Map;

/**
 * Created by shuguangcao on 2017/6/27.
 */
public interface TokenProvider {
    /**
     * 生成token
     *
     * @param loginInfo
     * @param authentication
     * @param rememberMe
     * @return
     */
    String createToken(Map<String, ?> loginInfo, Authentication authentication, Boolean rememberMe);

    /**
     * 从token里获取认证信息
     *
     * @param token
     * @return
     */
    Authentication getAuthentication(String token);

    /**
     * 验证token是否合法
     *
     * @param authToken
     * @return
     */
    boolean validateToken(String authToken);

}
