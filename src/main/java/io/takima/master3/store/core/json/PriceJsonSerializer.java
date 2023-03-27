package io.takima.master3.store.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import io.takima.master3.store.core.models.Price;

import java.io.IOException;

public class PriceJsonSerializer extends JsonSerializer<Price> {
    @Override
    public void serialize(Price price, JsonGenerator gen, SerializerProvider provider) throws IOException {
        // TODO use gen.writeString to serialize the Price
        gen.writeString(price.getAmount()+" " +price.getCurrency().symbol);
    }

}
