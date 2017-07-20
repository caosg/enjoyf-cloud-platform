package com.enjoyf.platform.contentservice.domain.enumeration;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

/**
 * A UserCommentSum.
 */

public enum UserCommentSumFiled implements Serializable {
    USEFUL, COMMENT;
}
