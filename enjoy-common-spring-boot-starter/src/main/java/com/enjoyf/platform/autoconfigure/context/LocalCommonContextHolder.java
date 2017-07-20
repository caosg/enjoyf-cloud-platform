package com.enjoyf.platform.autoconfigure.context;

/**
 * 本地线程存储，子线程不可得
 * Created by shuguangcao on 2017/6/29.
 */
public class LocalCommonContextHolder implements ContextHolderStategy<CommonContext> {

    private static final ThreadLocal<CommonContext> commonContextHolder = new ThreadLocal<>();

    public LocalCommonContextHolder() {
    }

    @Override
    public void clearContext() {
        commonContextHolder.remove();
    }

    @Override
    public CommonContext getContext() {
        CommonContext ctx = commonContextHolder.get();
        if (ctx == null) {
            ctx = this.createEmptyContext();
            this.setContext(ctx);
        }
        return ctx;
    }

    @Override
    public void setContext(CommonContext context) {
        commonContextHolder.set(context);
    }

    @Override
    public CommonContext createEmptyContext() {
        return new CommonContextImpl();
    }
}
