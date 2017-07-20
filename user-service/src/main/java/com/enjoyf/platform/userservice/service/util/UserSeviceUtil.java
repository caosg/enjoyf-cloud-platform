package com.enjoyf.platform.userservice.service.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

/**
 * 辅助工具类
 * Created by shuguangcao on 2017/3/20.
 */
public class UserSeviceUtil {

    private static final int DEF_COUNT = 36;

    private static final int MOBILE_CODE_COUNT=5;

    public final static String  DEFAULT_PASSWORD="123456";

    /**
     * 生成一个随机的36位长度的字母数字混合的uuid
     *
     * @return
     */
    public static String generateAccountNo() {

        return generateAccountNo(DEF_COUNT);
    }

    /**
     * 生成一个随机的36位长度的字母数字混合的uuid
     *
     * @return
     */
    public static String generateAccountNo(int length) {

        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String generateMobileCode() {

        return RandomStringUtils.randomNumeric(MOBILE_CODE_COUNT);
    }

    /**
     * 生成profile no
     *
     * @param accountNo
     * @param profileKey
     * @return
     */
    public static String generateProfileNo(String accountNo, String profileKey) {
        String str = accountNo + profileKey;
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

    /**
     * 密码md5加密
     *
     * @param password
     * @param passwordTime
     * @return
     */
    public static String hashPassword(String password, String passwordTime) {
        String str = password + (StringUtils.isEmpty(passwordTime) ? "" : passwordTime);
        return DigestUtils.md5DigestAsHex(str.getBytes());
    }

}
