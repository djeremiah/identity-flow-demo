package rh.demo.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Map;

public class RawMapSerializer extends JsonSerializer<Map<String, Object>> {

    @Override
    public void serialize(Map<String, Object> value, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeStartObject();
        for (Map.Entry<String, Object> e : value.entrySet()) {
            gen.writeFieldName(e.getKey());
            // Write value as raw data, since it's already JSON text
            Object o = e.getValue();
            if (o instanceof String) {
                gen.writeRawValue((String) o);
            } else {
                gen.writeObject(o);
            }
        }
        gen.writeEndObject();
    }
}
