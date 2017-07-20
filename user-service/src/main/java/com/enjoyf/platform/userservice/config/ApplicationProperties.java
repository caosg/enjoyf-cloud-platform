package com.enjoyf.platform.userservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to JHipster.
 *
 * <p>
 *     Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Icon icon = new Icon();

    private final Mobile mobile = new Mobile();

    public Mobile getMobile(){
        return this.mobile;
    }

    public Icon getIcon() {
        return icon;
    }

    public static class Icon{
        /**
         * 默认无性别头像
         */
        private String defaultIcon;
        /**
         * 默认男头像
         */
        private String bodyIcon;
        /**
         * 默认女头像
         */
        private String girlIcon;

        public String getDefaultIcon() {
            return defaultIcon;
        }

        public void setDefaultIcon(String defaultIcon) {
            this.defaultIcon = defaultIcon;
        }

        public String getBodyIcon() {
            return bodyIcon;
        }

        public void setBodyIcon(String bodyIcon) {
            this.bodyIcon = bodyIcon;
        }

        public String getGirlIcon() {
            return girlIcon;
        }

        public void setGirlIcon(String girlIcon) {
            this.girlIcon = girlIcon;
        }

        @Override
        public String toString() {
            return "Icon{" +
                "defaultIcon='" + defaultIcon + '\'' +
                ", bodyIcon='" + bodyIcon + '\'' +
                ", girlIcon='" + girlIcon + '\'' +
                '}';
        }
    }

    /**
     * 手机短信发送配置参数
      */
    public static class Mobile {
       /**
        * 限制短信发送次数
        */
       private long rating ;
       /**
        * 验证码失效时长，单位：秒
        */
       private long expireSeconds;

        /**
         * 测试用验证码，永远有效
         */
        private String testCode;

       public long getRating() {
           return rating;
       }

       public void setRating(long rating) {
           this.rating = rating;
       }

       public long getExpireSeconds() {
           return expireSeconds;
       }

       public void setExpireSeconds(long expireSeconds) {
           this.expireSeconds = expireSeconds;
       }

        public String getTestCode() {
            return testCode;
        }

        public void setTestCode(String testCode) {
            this.testCode = testCode;
        }

        @Override
        public String toString() {
            return "Mobile{" +
                "rating=" + rating +
                ", expireSeconds=" + expireSeconds +
                ", testCode='" + testCode + '\'' +
                '}';
        }
    }

    @Override
    public String toString() {
        return "ApplicationProperties{" +
            "mobile=" + mobile +
            '}';
    }
}
