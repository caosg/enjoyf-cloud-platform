package com.enjoyf.platform.autoconfigure.context;

/**
 * 存储策略
 * Created by shuguangcao on 2017/6/29.
 */
public interface ContextHolderStategy<T> {

    void clearContext();

    T getContext();

    void setContext(T context);

    T createEmptyContext();
}
