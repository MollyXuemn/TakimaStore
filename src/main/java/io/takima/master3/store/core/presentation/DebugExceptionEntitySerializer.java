package io.takima.master3.store.core.presentation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Profile("dev")
@JsonComponent
public class DebugExceptionEntitySerializer extends JsonSerializer<ExceptionEntity> {
    private final JavaType javaType = TypeFactory.defaultInstance().constructType(DebugExceptionEntity.class);

    @Override
    public void serialize(ExceptionEntity value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        DebugExceptionEntity debugExceptionEntity = new DebugExceptionEntity(value, this.getStackTrace(value.getCause()));
        JsonSerializer<Object> defaultSerializer = BeanSerializerFactory.instance.findBeanSerializer(provider, javaType, provider.getConfig().introspect(javaType));
        defaultSerializer.serialize(debugExceptionEntity, jgen, provider);
    }
    private String[] getStackTrace(Throwable cause) {
        if (cause == null) {
            return new String[]{};
        }
        StackTraceElement[] stackTrace = cause.getStackTrace();
        String[] stack = new String[stackTrace.length];
        for (int i = 0; i < stackTrace.length; i++) {
            stack[i] = stackTrace[i].toString();
        }
        return stack;
    }



}
