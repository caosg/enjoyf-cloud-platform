package com.enjoyf.platform.autoconfigure.storage.local;

import com.enjoyf.platform.autoconfigure.storage.StorageProperties;
import com.enjoyf.platform.autoconfigure.storage.StorageService;

import java.io.File;
import java.io.InputStream;

/**
 * 本地存储服务实现
 * Created by shuguangcao on 2017/6/30.
 */
public class LocalStorageService extends StorageService{

    public LocalStorageService(StorageProperties storageProperties) {
        this.config = storageProperties;
    }

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @param path 文件路径，包含文件名: a/b/image.jpg
     * @return 返回http地址
     */
    @Override
    public String upload(byte[] data, String path) {
        return null;
    }

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @return 返回http地址
     */
    @Override
    public String upload(byte[] data) {
        return null;
    }

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @param path        文件路径，包含文件名
     * @return 返回http地址
     */
    @Override
    public String upload(InputStream inputStream, String path) {
        return null;
    }

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @return 返回http地址
     */
    @Override
    public String upload(InputStream inputStream) {
        return null;
    }

    /**
     * 文件上传
     *
     * @param file 文件
     * @param path 保存路径
     * @return 返回http url
     */
    @Override
    public String upload(File file, String path) {
        return null;
    }

    /**
     * 文件上传，按默认规则路径存放
     *
     * @param file
     * @return
     */
    @Override
    public String upload(File file) {
        return null;
    }

    /**
     * 抓取网络资源上传
     *
     * @param url
     * @param path
     * @return 返回http地址
     */
    @Override
    public String uploadFromUrl(String url, String path) {
        return null;
    }

    /**
     * 抓取网络资源上传
     *
     * @param url
     * @return 返回http地址
     */
    @Override
    public String uploadFromUrl(String url) {
        return null;
    }
}
