package com.enjoyf.platform.userservice.repository.redis;

/**
 * Spring Data JPA repository for the ProfileRating entity.
 */
public interface RedisProfileRatingRepository {

    /**
     * get rating by key and type
     *
     * @param key  profile or mobile
     * @param type (mobilerating)
     * @return
     */
    long getMobileRating(String key, String type);

    long incrMobileRating(String key, String type,long value);

}
