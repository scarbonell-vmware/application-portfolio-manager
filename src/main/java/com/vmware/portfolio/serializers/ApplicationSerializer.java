package com.vmware.portfolio.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.vmware.portfolio.models.Application;
import java.io.IOException;

public class ApplicationSerializer extends StdSerializer<Application> {

    public ApplicationSerializer() {
        this(null);
    }

    public ApplicationSerializer(Class<Application> t) {
        super(t);
    }


    @Override
    public void serialize(
            Application app, JsonGenerator jgen, SerializerProvider provider)
            throws IOException {

        jgen.writeStartObject();
        jgen.writeNumberField("id", app.getId());
        jgen.writeStringField("identifier", app.getIdentifier());
        jgen.writeStringField("name", app.getName());
        jgen.writeStringField("description", app.getDescription());
        jgen.writeStringField("businessUnit", app.getBusinessUnit());
        jgen.writeStringField("organization", app.getOrganization());
        jgen.writeStringField("businessOwner", app.getBusinessOwner());
        jgen.writeEndObject();
    }
}
