package com.vmware.portfolio.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.vmware.portfolio.models.TestConfig;
import java.io.IOException;

public class TestConfigSerializer extends StdSerializer<TestConfig> {

    public TestConfigSerializer() {
        this(null);
    }

    public TestConfigSerializer(Class<TestConfig> t) {
        super(t);
    }


    @Override
    public void serialize(
            TestConfig conf, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        jgen.writeStartObject();
        jgen.writeStringField("name", conf.getName());
        jgen.writeStringField("value", conf.getValue());
        jgen.writeEndObject();
    }
}
