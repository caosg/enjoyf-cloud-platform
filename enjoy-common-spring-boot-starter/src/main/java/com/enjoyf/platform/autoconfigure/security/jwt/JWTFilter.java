package com.enjoyf.platform.autoconfigure.security.jwt;

import com.enjoyf.platform.autoconfigure.context.CommonContextHolder;
import com.enjoyf.platform.autoconfigure.web.CommonParams;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理token ，获取公共参数信息
 */
public class JWTFilter extends GenericFilterBean {

    private final Logger log = LoggerFactory.getLogger(JWTFilter.class);

    private TokenProvider tokenProvider;

    public JWTFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            resoveCommonParams(httpServletRequest);
            String jwt = resolveToken(httpServletRequest);
            if (StringUtils.hasText(jwt) && this.tokenProvider.validateToken(jwt)) {
                Authentication authentication = this.tokenProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ExpiredJwtException eje) {
            log.info("Security exception for user {} - {}",
                    eje.getClaims().getSubject(), eje.getMessage());

            log.trace("Security exception trace: {}", eje);
            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    /**
     * 获取 token
     *
     * @param request
     * @return
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JWTConfigurer.AUTHORIZATION_HEADER);
        log.info("******** JWT token : {} ***********", bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }

    /**
     * 获取公共参数信息
     *
     * @param request
     */
    private void resoveCommonParams(HttpServletRequest request) {
        CommonParams params = new CommonParams(Integer.parseInt(request.getHeader(CommonParams.PLATFORM) == null ? "-1" : request.getHeader(CommonParams.PLATFORM)),
                request.getHeader(CommonParams.VERSION), request.getHeader(CommonParams.APPKEY), request.getHeader(CommonParams.DEVICEID),
                request.getHeader(CommonParams.CHANNEL), request.getHeader(CommonParams.LOGIN_DOMAIN));
        log.info("****** {} ********* ", params);
        CommonContextHolder.getContext().setCommonParams(params);
    }

}