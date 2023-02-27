package io.takima.master3.store.domain;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public record Money(
        double amount,
        String currency) {
    @Override
    public String toString() {
        return amount + " " + currency;
    }
}