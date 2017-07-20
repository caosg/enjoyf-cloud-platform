package com.enjoyf.platform.userservice.repository.redis;

import com.enjoyf.platform.userservice.domain.UserAccount;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by shuguangcao on 2017/3/27.
 */
public interface RedisUserAccountRepository extends CrudRepository<UserAccount,Long> {
    UserAccount findOneByAccountNo(String accountNo);
}
