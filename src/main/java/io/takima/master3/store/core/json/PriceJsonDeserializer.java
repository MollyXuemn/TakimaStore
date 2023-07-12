package io.takima.master3.store.core.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.takima.master3.store.core.models.Price;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonComponent
public class PriceJsonDeserializer extends JsonDeserializer<Price> {

    @Override
    public Price deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String str = jsonParser.getValueAsString();

        Matcher currencyMatcher = Pattern.compile("^([\\d]*[\\.|,][\\d]+|[\\d]+)(\\D+)$").matcher(str);
        if (currencyMatcher.matches()) {
            var value = currencyMatcher.group(1);
            var currency = currencyMatcher.group(2).trim();
            return new Price(Double.parseDouble(value), currency);
        } else {
            throw new InvalidFormatException(jsonParser, String.format("%s cannot be parse into Price", str), str, Price.class);
        }
    }
}

