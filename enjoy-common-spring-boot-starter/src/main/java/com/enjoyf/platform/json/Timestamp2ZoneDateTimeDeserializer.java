package com.enjoyf.platform.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Created by ericliu on 2017/7/3.
 * 未测试时间戳
 */
public class Timestamp2ZoneDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {

    public Timestamp2ZoneDateTimeDeserializer() {
    }

    @Override
    public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        Number timeStamp = node.numberValue();

        return ZonedDateTime.ofInstant(Instant.ofEpochMilli((Long) timeStamp), ZoneId.systemDefault());
    }
}
