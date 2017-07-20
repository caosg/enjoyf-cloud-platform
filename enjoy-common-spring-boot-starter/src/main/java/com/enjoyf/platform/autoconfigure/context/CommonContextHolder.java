package com.enjoyf.platform.autoconfigure.context;

/**
 * 外部调用，获取上下文环境信息
 * Created by shuguangcao on 2017/6/29.
 */
public class CommonContextHolder {

    private static ContextHolderStategy<CommonContext> stategy;

    private CommonContextHolder() {
    }

    static {
        stategy = new LocalCommonContextHolder();
    }

    public static CommonContext getContext() {
        return stategy.getContext();
    }

    public static void setContext(CommonContext commonContext) {
        stategy.setContext(commonContext);
    }

    public static void clearContext() {
        stategy.clearContext();
    }


}
