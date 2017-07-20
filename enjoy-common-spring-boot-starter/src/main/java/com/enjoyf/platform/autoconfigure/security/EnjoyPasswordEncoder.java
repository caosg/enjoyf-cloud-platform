package com.enjoyf.platform.autoconfigure.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;

/**
 * 着迷网旧系统密码编码算法
 * MD5{rawPassword+passwordTime}
 * Created by shuguangcao on 2017/3/21.
 */
public class EnjoyPasswordEncoder implements PasswordEncoder {
    private final Logger logger = LoggerFactory.getLogger(EnjoyPasswordEncoder.class);

    @Override
    public String encode(CharSequence rawPassword) {
        String md5Password = DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
        return md5Password;
    }

    /**
     * matches password md5(input+passwordtime).equals(password)
     *
     * @param rawPassword     inputpassword
     * @param encodedPassword password,passwordtime
     * @return true or false
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String[] pwd = encodedPassword.split(",");
        String saltPassword = rawPassword.toString() + (pwd.length == 2 ? pwd[1] : "");
        String rawEncodePassword = DigestUtils.md5DigestAsHex(saltPassword.getBytes());
        logger.info("\n ------ login password:{} ,db pwdTime:{} , db password:{} == {} ---------"
                , rawPassword.toString(), (pwd.length == 2 ? pwd[1] : ""), pwd[0], rawEncodePassword);
        return rawEncodePassword.equals(pwd[0]);
    }
}