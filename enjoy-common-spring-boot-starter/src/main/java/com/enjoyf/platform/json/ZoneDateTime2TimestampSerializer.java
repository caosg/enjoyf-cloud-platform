package com.enjoyf.platform.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.ZonedDateTime;

/**
 * Created by ericliu on 2017/7/3.
 */
public class ZoneDateTime2TimestampSerializer extends JsonSerializer<ZonedDateTime> {

    public ZoneDateTime2TimestampSerializer() {
    }

    @Override
    public void serialize(ZonedDateTime date, JsonGenerator gen, SerializerProvider provider)
        throws IOException {

        gen.writeNumber(String.valueOf(date.toInstant().toEpochMilli()));
    }

}
