package io.takima.master3.store.core.presentation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@JsonComponent
@Profile("dev")
public class DebugExceptionEntitySerializer extends JsonSerializer<ExceptionEntity> {

    private final JavaType javaType = TypeFactory.defaultInstance().constructType(DebugExceptionEntity.class);

    @Override
    public void serialize(ExceptionEntity value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        DebugExceptionEntity debugExceptionEntity = new DebugExceptionEntity(value, getStackTrace(value.getCause()));
        JsonSerializer<Object> defaultSerializer = BeanSerializerFactory.instance.findBeanSerializer(provider, javaType, provider.getConfig().introspect(javaType));
        defaultSerializer.serialize(debugExceptionEntity, jgen, provider);
    }

    private String[] getStackTrace(Throwable throwable) {
        return Arrays
                .stream(throwable.getStackTrace())
                .map(StackTraceElement::toString)
                .toArray(String[]::new);
    }
}

