package io.takima.master3.store.core.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.takima.master3.store.core.models.Currency;
import io.takima.master3.store.core.models.Price;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
@JsonComponent
public class JsonPriceDeserializer extends JsonDeserializer<Price> {
    @Override
    public Price deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        // TODO use jsonParser.getValueAsString() to inflate a Price.
        String priceStr = jsonParser.getValueAsString();
        Matcher currencyMatcher = Pattern.compile("^([\\d]*[\\.|,][\\d]+|[\\d]+)(\\D+)$").matcher(priceStr);
        if (currencyMatcher.matches()) {
            Double amount = Double.parseDouble(currencyMatcher.group(1));
            Currency currency = Currency.fromSymbol(currencyMatcher.group(2).trim());
            return new Price(amount, currency);
        }
        throw new InvalidFormatException( jsonParser, "value %s cannot be converted into a price", priceStr, Price.class);

    }

}
