package com.enjoyf.platform.autoconfigure.web;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * 自定义方法参数绑定
 * Created by shuguangcao on 2017/6/29.
 */
public class CommonParamsResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(CommonParams.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        return new CommonParams(Integer.parseInt(webRequest.getHeader(CommonParams.PLATFORM) == null ? "-1" : webRequest.getHeader(CommonParams.PLATFORM)),
                webRequest.getHeader(CommonParams.VERSION), webRequest.getHeader(CommonParams.APPKEY), webRequest.getHeader(CommonParams.DEVICEID),
                webRequest.getHeader(CommonParams.CHANNEL), webRequest.getHeader(CommonParams.LOGIN_DOMAIN));
    }
}
