package com.enjoyf.platform.autoconfigure.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 存储服务配置
 * Created by shuguangcao on 2017/6/30.
 */
@ConfigurationProperties(prefix = "storage")
public class StorageProperties {

    private StorageProvider provider = StorageProvider.LOCAL; //存储提供者，默认本地存储

    private Local  local = new Local();

    private QiNiu qiNiu = new QiNiu();

    public StorageProvider getProvider() {
        return provider;
    }

    public void setProvider(StorageProvider provider) {
        this.provider = provider;
    }

    public Local getLocal() {
        return local;
    }

    public QiNiu getQiNiu() {
        return qiNiu;
    }

    public static class Local{

        private String path = System.getProperty("java.io.tmpdir");

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }

    public static class QiNiu{
        private String domain;//绑定的域名
        private String prefix;//路径前缀
        private String accessKey;
        private String secretKey;
        private String bucketName;//绑定的空间

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public String getBucketName() {
            return bucketName;
        }

        public void setBucketName(String bucketName) {
            this.bucketName = bucketName;
        }
    }
}
