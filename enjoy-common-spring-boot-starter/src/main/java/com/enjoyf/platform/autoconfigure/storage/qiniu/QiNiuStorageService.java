package com.enjoyf.platform.autoconfigure.storage.qiniu;

import com.enjoyf.platform.autoconfigure.storage.StorageProperties;
import com.enjoyf.platform.autoconfigure.storage.StorageService;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FetchRet;
import com.qiniu.util.Auth;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 七牛云存储实现上传
 * Created by shuguangcao on 2017/6/30.
 */
public class QiNiuStorageService extends StorageService {

    private final static Logger log = LoggerFactory.getLogger(QiNiuStorageService.class);

    private UploadManager uploadManager;
    private BucketManager bucketManager;
    private String token;

    public QiNiuStorageService(StorageProperties config) {
        this.config = config;
        init();
    }

    private void init(){
        Auth auth = Auth.create(config.getQiNiu().getAccessKey(), config.getQiNiu().getSecretKey());
        Configuration cfg = new Configuration(Zone.autoZone());
        uploadManager = new UploadManager(cfg);
        bucketManager = new BucketManager(auth, cfg);
        token = Auth.create(config.getQiNiu().getAccessKey(), config.getQiNiu().getSecretKey()).
                uploadToken(config.getQiNiu().getBucketName());
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
        try {
            Response res = uploadManager.put(data, path, token);
            if (!res.isOK()) {
                log.info("filePath:{} 上传七牛出错",path);
                return ERROR;
            }
        } catch (QiniuException e) {
            log.error("{}上传文件失败，请核对七牛配置信息及网络是否通常,错误信息:{}",path,e.response.toString());
            //throw new RuntimeException("上传文件失败，请核对七牛配置信息", e);
            return ERROR;
        }
        return config.getQiNiu().getDomain() + "/" + path;
    }

    /**
     * 文件上传
     *
     * @param data 文件字节数组
     * @return 返回http地址
     */
    @Override
    public String upload(byte[] data) {
        return upload(data, getPath(config.getQiNiu().getPrefix()));
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
        try {
            byte[] data =  IOUtils.toByteArray(inputStream);
            return this.upload(data, path);
        } catch (IOException e) {
            log.error("{} 上传文件失败",path);
            //throw new RuntimeException("上传文件失败", e);
            return ERROR;
        }
    }

    /**
     * 文件上传
     *
     * @param inputStream 字节流
     * @return 返回http地址
     */
    @Override
    public String upload(InputStream inputStream) {
        return upload(inputStream, getPath(config.getQiNiu().getPrefix()));
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
        try {
            Response res = uploadManager.put(file, path, token);
            if (!res.isOK()) {
                log.info("filePath:{} 上传七牛出错",path);
                return ERROR;
            }
        } catch (QiniuException e) {
            log.error("{}上传文件失败，请核对七牛配置信息及网络是否通常,错误信息:{}",path,e.response.toString());
            //throw new RuntimeException("上传文件失败，请核对七牛配置信息", e);
            return ERROR;
        }
        return config.getQiNiu().getDomain() + "/" + path;
    }

    /**
     * 文件上传，按默认规则路径存放
     *
     * @param file
     * @return
     */
    @Override
    public String upload(File file) {
        return this.upload(file,getPath(config.getQiNiu().getPrefix()));
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
        if(StringUtils.isEmpty(url)|| url.contains(this.config.getQiNiu().getDomain()))
            return url;
        try {
            FetchRet fetchRet = bucketManager.fetch(url,config.getQiNiu().getBucketName(),path);
        } catch (QiniuException e) {
            log.error("抓取远程文件{}上传文件失败,错误信息：{}",e.response.toString());
            //throw new RuntimeException("抓取远程文件上传文件失败", e);
            return ERROR;
        }
        return config.getQiNiu().getDomain() + "/" + path;
    }

    /**
     * 抓取网络资源上传
     *
     * @param url
     * @return 返回http地址
     */
    @Override
    public String uploadFromUrl(String url) {
        return uploadFromUrl(url,getPath(config.getQiNiu().getPrefix()));
    }


}
