package com.enjoyf.platform.userservice.service.impl;

import com.enjoyf.platform.autoconfigure.security.EnjoySecurityUtils;
import com.enjoyf.platform.userservice.config.ApplicationProperties;
import com.enjoyf.platform.userservice.domain.UserProfile;
import com.enjoyf.platform.userservice.repository.UserProfileRepository;
import com.enjoyf.platform.userservice.repository.redis.RedisAccountDTORepository;
import com.enjoyf.platform.userservice.repository.redis.RedisUserProfileRepository;
import com.enjoyf.platform.userservice.security.SecurityUtils;
import com.enjoyf.platform.userservice.service.UserProfileService;
import com.enjoyf.platform.userservice.service.dto.AccountDTO;
import com.enjoyf.platform.userservice.service.exception.NickExistException;
import com.enjoyf.platform.userservice.service.exception.WordSanitizeException;
import com.enjoyf.platform.userservice.service.util.ProfileFlag;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing UserProfile.
 * <p>
 * 和profile相关的业务
 */
@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final Logger log = LoggerFactory.getLogger(UserProfileServiceImpl.class);

    private final UserProfileRepository userProfileRepository;

    private final RedisUserProfileRepository redisUserProfileRepository;

    private final RedisAccountDTORepository redisAccountDTORepository;

    @Autowired
    private ApplicationProperties applicationProperties;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, RedisUserProfileRepository redisUserProfileRepository, RedisAccountDTORepository redisAccountDTORepository) {
        this.userProfileRepository = userProfileRepository;
        this.redisUserProfileRepository = redisUserProfileRepository;
        this.redisAccountDTORepository = redisAccountDTORepository;
    }

    /**
     * Save a userProfile.
     *
     * @param userProfile the entity to save
     * @return the persisted entity
     */
    @Override
    public UserProfile save(UserProfile userProfile) {
        log.debug("Request to save UserProfile : {}", userProfile);
        if(StringUtils.isEmpty(userProfile.getNick()) ){
            userProfile.setNick("joyme"+ RandomUtils.nextInt());
            userProfile.setLowercaseNick(userProfile.getNick().toLowerCase());
        }
        UserProfile result = userProfileRepository.save(userProfile);
        AccountDTO accountDTO = redisAccountDTORepository.findOne(result.getId());
        if(accountDTO!=null) {
            accountDTO.setUserProfile(result);
            redisAccountDTORepository.save(accountDTO);
        }
        redisUserProfileRepository.save(result);
        return result;
    }

    /**
     * Get all the userProfiles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    public Page<UserProfile> findAll(Pageable pageable) {
        log.debug("Request to get all UserProfiles");
        Page<UserProfile> result = userProfileRepository.findAll(pageable);
        return result;
    }

    /**
     * 组合条件查询
     *
     * @param nick
     * @param inOrNot
     * @param profileNos
     * @param startTime
     * @param endTime
     * @return
     */
    @Override
    public Page<UserProfile> findAllWhere(String nick, String inOrNot, String profileNos, String startTime, String endTime,Pageable pageable) {

        return userProfileRepository.findAll(where(nick,inOrNot,profileNos,startTime,endTime),pageable);
    }

    private Specification<UserProfile> where(String nick, String inOrNot, String profileNos, String startTime, String endTime){
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if(!StringUtils.isEmpty(nick))
                predicates.add(criteriaBuilder.like(root.get("nick"),"%"+nick+"%"));
            if(!StringUtils.isEmpty(inOrNot)&&!StringUtils.isEmpty(profileNos)){
                if(inOrNot.equals("in"))
                    predicates.add(root.get("profileNo").in(profileNos.split(",")));
                if (inOrNot.equals("not"))
                    predicates.add(root.get("profileNo").in(profileNos.split(",")).not());
            }
            //默认只查询30天内创建的数据
            ZonedDateTime startZoneTime = ZonedDateTime.now().minusDays(30);
            ZonedDateTime endZoneTime = ZonedDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            if(!StringUtils.isEmpty(startTime))
                startZoneTime = ZonedDateTime.parse(startTime,dateTimeFormatter.withZone(ZoneId.systemDefault()));
            if(!StringUtils.isEmpty(endTime))
                endZoneTime = ZonedDateTime.parse(endTime,dateTimeFormatter.withZone(ZoneId.systemDefault()));
            predicates.add(criteriaBuilder.between(root.get("createdTime"),startZoneTime,endZoneTime));
            log.info("----search where nick:{}, in:{},profileNos:{},start:{},end:{}",nick,inOrNot,profileNos,startZoneTime,endZoneTime);
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        };
    }

    /**
     * Get one userProfile by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    public UserProfile findOne(Long id) {
        log.debug("Request to get UserProfile : {}", id);
        UserProfile userProfile = redisUserProfileRepository.findOne(id);
        if(userProfile==null) {
            userProfile = userProfileRepository.findOne(id);
        }
        return userProfile;
    }



    @Override
    public UserProfile findOneByProfileNo(String profileNo) {
        if (log.isDebugEnabled()) {
            log.debug("Request to get UserProfile : {}", profileNo);
        }
        UserProfile userProfile = redisUserProfileRepository.findOneByProfileNo(profileNo);
        if(userProfile == null) {
            Optional<UserProfile> userProfileOptional = userProfileRepository.findOneByProfileNo(profileNo);
            if(userProfileOptional.isPresent()){
                userProfile =  userProfileOptional.get();
                redisUserProfileRepository.save(userProfile);
            }

        }
        return userProfile;
    }

    /**
     * Get the userProfile by nick
     *
     * @param nick
     * @return
     */
    @Override
    public Optional<UserProfile> findOneByNick(String nick) {
        if (log.isDebugEnabled())
            log.debug("Request to get UserProfile by nick : {}", nick);
        UserProfile userProfile = redisUserProfileRepository.findOneBylowercaseNick(nick.toLowerCase());
        if(userProfile==null) {
            Optional<UserProfile> userProfileOptional = userProfileRepository.findOneBylowercaseNick(nick);
            if(userProfileOptional.isPresent()){
                userProfile =  userProfileOptional.get();
                redisUserProfileRepository.save(userProfile);
            }
        }
        return Optional.ofNullable(userProfile);
    }

    @Override
    public List<UserProfile> findAll(UserProfile userProfile) {
         return null ;
    }

    @Override
    public Page<UserProfile> findAll(UserProfile userProfile, Pageable pageable) {
        return null;
    }



    @Override
    public List<UserProfile> findByProfileNos(Set<String> profileNos) {
        if (log.isDebugEnabled()) {
            log.debug("Request to find UserProfile : {}", profileNos);
        }
        List<UserProfile> userProfileList = new ArrayList<>();
        if (CollectionUtils.isEmpty(profileNos)) {
            return userProfileList;
        }

        userProfileList = userProfileRepository.findByProfileNoIn(profileNos);
        return userProfileList;
    }

    /**
     * get profiles by ids
     *
     * @param ids
     * @return
     */
    @Override
    public List<UserProfile> findByIds(Set<Long> ids) {
        return userProfileRepository.findByIdIn(ids);
    }

    /**
     * get profiles by nick like %nick%
     * @param nick
     * @return
     */
    @Override
    public List<UserProfile> findByNickLike(String nick){
        return userProfileRepository.findByNickContaining(nick);
    }
    /**
     * Delete the  userProfile by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserProfile : {}", id);
        userProfileRepository.delete(id);
        redisUserProfileRepository.delete(id);
    }

    /**
     * 修改昵称
     *
     * @param newNick
     */
    @Override
    public UserProfile updateNick(String newNick) {
        if(!checkNick(newNick))
            throw new WordSanitizeException();
        Optional<UserProfile> userProfileOptional = this.findOneByNick(newNick);
        if(userProfileOptional.isPresent())
             throw  new NickExistException("nick.error.exist");
        String currentLoginProfileNo = EnjoySecurityUtils.getCurrentProfileNo();
        if(StringUtils.isEmpty(currentLoginProfileNo))
            throw new AccessDeniedException("没有登陆");
        UserProfile currentUserProfile = this.findOneByProfileNo(currentLoginProfileNo);
        ProfileFlag profileFlag = new ProfileFlag(currentUserProfile.getFlag());
        if(profileFlag.hasFlag(ProfileFlag.FLAG_NICK_HASCOMPLETE))
            throw new NickExistException("nick.error.complete");
        return this.save(currentUserProfile.nick(newNick).lowercaseNick(newNick.toLowerCase())
            .flag(profileFlag.has(ProfileFlag.FLAG_NICK_HASCOMPLETE).getValue()));
    }

    /**
     * todo
     * 昵称合法检查
     * @param nick
     * @return
     */
    private boolean checkNick(String nick) {
        return true;
    }

}
