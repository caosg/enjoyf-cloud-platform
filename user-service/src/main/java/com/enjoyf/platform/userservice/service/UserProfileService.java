package com.enjoyf.platform.userservice.service;

import com.enjoyf.platform.userservice.domain.UserProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service Interface for managing UserProfile.
 */
public interface UserProfileService {

    /**
     * Save a userProfile.
     *
     * @param userProfile the entity to save
     * @return the persisted entity
     */
    UserProfile save(UserProfile userProfile);

    /**
     *  Get all the userProfiles.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UserProfile> findAll(Pageable pageable);

    /**
     * 组合条件查询
     * @param nick
     * @param inOrNot
     * @param profileNos
     * @param startTime
     * @param endTime
     * @return
     */
    Page<UserProfile> findAllWhere(String nick,String inOrNot,String profileNos,String startTime,String endTime,Pageable pageable);

    /**
     *  Get the "id" userProfile.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UserProfile findOne(Long id);

    /**
     * Get the "profileNo" userProfile
     *
     * @param profileNo thid id of the profileNo
     * @return the entity
     */
    UserProfile findOneByProfileNo(String profileNo);

    /**
     * Get the userProfile by nick
     * @param nick
     * @return
     */
    Optional<UserProfile>  findOneByNick(String nick);

    /**
     * 组合条件查询
     * @param userProfile
     * @return
     */
    List<UserProfile> findAll(UserProfile userProfile);

    /**
     * 组合条件分页查询
     * @param userProfile
     * @param pageable
     * @return
     */
    Page<UserProfile> findAll(UserProfile userProfile, Pageable pageable);

    /**
     * get profiles by ids
     * @param ids
     * @return
     */
    List<UserProfile> findByIds(Set<Long> ids);

    /**
     * get profiles by profileNos
     * @param profileNos
     * @return
     */
    List<UserProfile> findByProfileNos(Set<String> profileNos);

    /**
     * get profiles by nick like %nick%
     * @param nick
     * @return
     */
    List<UserProfile> findByNickLike(String nick);

    /**
     *  Delete the "id" userProfile.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * 修改昵称
     * @param newNick
     */
    UserProfile updateNick(String newNick);

}
