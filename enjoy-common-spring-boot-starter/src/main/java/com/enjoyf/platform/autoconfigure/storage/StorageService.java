package com.enjoyf.platform.autoconfigure.storage;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import static org.apache.commons.lang.time.DateFormatUtils.format;

/**
 * 存储服务，可以有不同的存储实现，包扩本地存储，fastdfs,七牛云存储，阿里云存储等
 * Created by shuguangcao on 2017/6/30.
 */
public abstract class StorageService {

    /**
     * 存储配置
     */
    protected StorageProperties config;

    /**
     * 上传出错返回值
     */
    protected String ERROR = "error";

    /**
     * 默认文件路径
     * @param prefix 前缀
     * @return 返回上传路径
     */
    public String getPath(String prefix) {
        //生成uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //文件路径
        String path = format(new Date(), "yyyyMMdd")  + "/" + uuid;

        if(StringUtils.isNotBlank(prefix)){
            path = prefix + "/" + path;
        }

        return path;
    }

    /**
     * 文件上传
     * @param data    文件字节数组
     * @param path    文件路径，包含文件名: a/b/c
     * @return        返回http地址
     */
    public abstract String upload(byte[] data, String path);

    /**
     * 文件上传
     * @param data    文件字节数组
     * @return        返回http地址
     */
    public abstract String upload(byte[] data);

    /**
     * 文件上传
     * @param inputStream   字节流
     * @param path          文件路径，包含文件名
     * @return              返回http地址
     */
    public abstract String upload(InputStream inputStream, String path);

    /**
     * 文件上传
     * @param inputStream  字节流
     * @return             返回http地址
     */
    public abstract String upload(InputStream inputStream);

    /**
     * 文件上传
     * @param file  文件
     * @param path  保存路径
     * @return   返回http url
     */
    public abstract String upload(File file , String path);

    /**
     * 文件上传，按默认规则路径存放
     * @param file
     * @return
     */
    public abstract String upload(File file);

    /**
     * 抓取网络资源上传
     * @param url
     * @param path
     * @return 返回http地址
     */
    public abstract String uploadFromUrl(String url,String path);

    /**
     * 抓取网络资源上传
     * @param url
     * @return 返回http地址
     */
    public abstract String uploadFromUrl(String url);
}
