package com.enjoyf.platform.profileservice.web.rest.vm;

import com.enjoyf.platform.profileservice.service.contentservice.dto.GameTagDTO;
import com.enjoyf.platform.profileservice.service.dto.WikiAppProfileDTO;

import java.util.List;

/**
 * Created by ericliu on 2017/6/30.
 */
public class WikiAppProfileVM {
    private List<GameTagDTO> gameTags;
    private WikiAppProfileDTO profile;

    public List<GameTagDTO> getGameTags() {
        return gameTags;
    }

    public void setGameTags(List<GameTagDTO> gameTags) {
        this.gameTags = gameTags;
    }

    public WikiAppProfileDTO getProfile() {
        return profile;
    }

    public void setProfile(WikiAppProfileDTO profile) {
        this.profile = profile;
    }
}
