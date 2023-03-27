package io.takima.master3.store.core.pagination;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

@JsonComponent
class PageSerializer extends JsonSerializer<Page> {

    @Override
    public void serialize(Page page, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeFieldName("page");
        gen.writeStartObject();
        gen.writeNumberField("totalElements",page.getTotalElements());
        gen.writeNumberField("totalPages", page.getTotalPages());
        gen.writeNumberField("number", page.getNumber());
        gen.writeNumberField("size", page.getSize());

        gen.writeBooleanField("first", page.isFirst());
        gen.writeBooleanField("last", page.isLast());
        gen.writeEndObject();
        gen.writeFieldName("content");
        serializers.defaultSerializeValue(page.getContent(), gen);
        gen.writeEndObject();
    }
}
