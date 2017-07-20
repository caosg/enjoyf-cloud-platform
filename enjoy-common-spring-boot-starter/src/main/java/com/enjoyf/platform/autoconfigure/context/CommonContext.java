package com.enjoyf.platform.autoconfigure.context;

import com.enjoyf.platform.autoconfigure.web.CommonParams;

/**
 * 上下文环境，存储数据，线程安全
 * Created by shuguangcao on 2017/6/29.
 */
public interface CommonContext {

    CommonParams getCommonParams();

    void setCommonParams(CommonParams commonParams);
}
