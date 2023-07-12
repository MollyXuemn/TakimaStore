package io.takima.master3.store.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.takima.master3.store.core.models.Price;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class PriceJsonSerializer extends JsonSerializer<Price> {    // serializer of Price

    @Override
    public void serialize(Price price, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(price.getAmount() + " " + price.getCurrency());
    }
}

