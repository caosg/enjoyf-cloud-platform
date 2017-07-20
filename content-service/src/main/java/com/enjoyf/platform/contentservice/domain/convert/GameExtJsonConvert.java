package com.enjoyf.platform.contentservice.domain.convert;

import com.enjoyf.platform.contentservice.domain.Game;
import com.enjoyf.platform.contentservice.domain.GameExtJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import javax.persistence.AttributeConverter;
import java.io.IOException;

/**
 * Created by zhimingli on 2017/6/20.
 */
public class GameExtJsonConvert implements AttributeConverter<GameExtJson, String> {

    @Override
    public String convertToDatabaseColumn(GameExtJson gameExtJson) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(gameExtJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public GameExtJson convertToEntityAttribute(String s) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(s, GameExtJson.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
