package com.enjoyf.platform.contentservice.repository.redis;

import com.enjoyf.platform.contentservice.domain.Game;
import com.enjoyf.platform.contentservice.domain.GameTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by zhimingli on 2017/6/21.
 */
public interface RedisGameRepository extends CrudRepository<Game, Long> {

}
