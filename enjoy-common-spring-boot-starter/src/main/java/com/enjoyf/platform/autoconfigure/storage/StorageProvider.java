package com.enjoyf.platform.autoconfigure.storage;

/**
 * Created by shuguangcao on 2017/6/30.
 */
public enum StorageProvider {
    /**
     * 本地存储
     */
    LOCAL,
    /**
     * 分布式本地存储
     */
    FASTDFS,
    /**
     * 七牛云存储
     */
    QINIU,
    /**
     * 阿里云OSS存储
     */
    ALIOSS
}
