package com.enjoyf.platform.userservice.repository.redis;

import com.enjoyf.platform.userservice.service.dto.AccountDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by shuguangcao on 2017/3/25.
 */
public interface RedisAccountDTORepository extends CrudRepository<AccountDTO,Long>{
    Optional<AccountDTO> findOneByProfileNo(String profileNo);
}
