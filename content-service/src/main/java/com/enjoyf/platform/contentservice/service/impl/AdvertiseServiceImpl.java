package com.enjoyf.platform.contentservice.service.impl;

import com.enjoyf.platform.contentservice.domain.AdExtendJson;
import com.enjoyf.platform.contentservice.domain.enumeration.AdvertiseDomain;
import com.enjoyf.platform.contentservice.domain.enumeration.ValidStatus;
import com.enjoyf.platform.contentservice.repository.redis.AskRedisRepository;
import com.enjoyf.platform.contentservice.service.AdvertiseService;
import com.enjoyf.platform.contentservice.domain.Advertise;
import com.enjoyf.platform.contentservice.repository.jpa.AdvertiseRepository;
import com.enjoyf.platform.contentservice.web.rest.util.AskUtil;
import com.enjoyf.platform.contentservice.web.rest.vm.ContentVM;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Advertise.
 */
@Service
@Transactional
public class AdvertiseServiceImpl implements AdvertiseService {

    private final Logger log = LoggerFactory.getLogger(AdvertiseServiceImpl.class);

    private final AdvertiseRepository advertiseRepository;

    private final AskRedisRepository askRedisRepository;

    public AdvertiseServiceImpl(AdvertiseRepository advertiseRepository, AskRedisRepository askRedisRepository) {
        this.advertiseRepository = advertiseRepository;
        this.askRedisRepository = askRedisRepository;
    }

    /**
     * Save a advertise.
     *
     * @param advertise the entity to save
     * @return the persisted entity
     */
    @Override
    public Advertise save(Advertise advertise) {
        log.debug("Request to save Advertise : {}", advertise);
        Advertise result = advertiseRepository.save(advertise);
        return result;
    }

    /**
     * Get all the advertises.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Advertise> findAll() {
        log.debug("Request to get all Advertises");
        List<Advertise> result = (List<Advertise>) advertiseRepository.findAll();

        return result;
    }


    @Override
    public Page<Advertise> findByDomainAndSort(Integer domain, Pageable pageable) {
        // return advertiseRepository.findByDomainAndSort(domain, pageable);
        return null;
    }

    /**
     * Get one advertise by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Advertise findOne(Long id) {
        log.debug("Request to get Advertise : {}", id);
        Advertise advertise = advertiseRepository.findOne(id);
        return advertise;
    }

    /**
     * Delete the  advertise by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Advertise : {}", id);
        advertiseRepository.delete(id);
    }


    @Override
    public Map<Integer, Advertise> getAdMap(String appkey, Integer platform) {
        log.debug("Request to getAdMap : {}", appkey, platform);
        Map<Integer, Advertise> retutnMap = new HashMap<Integer, Advertise>();
        if (StringUtils.isEmpty(appkey) || platform < 0) {
            return retutnMap;
        }
        try {
            List<Advertise> list = queryAdvertiseByLineKey(AskUtil.getAdvertiseLinekey(appkey, platform, AdvertiseDomain.ARTICLE_ADVERTISE));
            for (Advertise advertise : list) {
                if (!StringUtils.isEmpty(advertise.getExtend())) {
                    AdExtendJson json = AdExtendJson.toObject(advertise.getExtend());
                    retutnMap.put(json.getIndex(), advertise);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retutnMap;
    }


    @Override
    public List<ContentVM> queryContentVMeByLineKey(String linekey) {
        List<Advertise> list = queryAdvertiseByLineKey(linekey);

        return list.stream().map(ContentVM::new).collect(Collectors.toList());
    }

    private List<Advertise> queryAdvertiseByLineKey(String linekey) {
        Set<String> advertiseLine = askRedisRepository.zrangeAdvertiseLine(linekey);
        List<Advertise> returnAdList = new ArrayList<Advertise>();
        for (String strid : advertiseLine) {
            Advertise advertise = getAdvertise(Long.valueOf(strid));
            if (advertise != null && advertise.getRemoveStatus().equals(ValidStatus.VALID.getCode())) {
                returnAdList.add(advertise);
            }
        }
        return returnAdList;
    }


    private Advertise getAdvertise(Long advertiseId) {
        Advertise advertise = askRedisRepository.getAdvertise(advertiseId);
        if (advertise == null) {
            advertise = findOne(advertiseId);
            if (advertise != null) {
                askRedisRepository.setAdvertise(advertise);
            }
        }
        return advertise;
    }


}
