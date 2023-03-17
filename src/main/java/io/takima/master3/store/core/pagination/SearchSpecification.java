package io.takima.master3.store.core.pagination;
import jakarta.persistence.criteria.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

@Getter
public class SearchSpecification<T> implements Specification<T> {

    private final String attribute;
    private final Operator operator;
    private final Object value;

    /**
     * Create a new SearchSpecification with given operator and operands
     *
     * @param field    the left operand
     * @param operator the operator
     * @param value    the right operand
     */
    public SearchSpecification(String field, Operator operator, Object value) {
        this.attribute = field;
        this.operator = operator;
        this.value = value;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

        // root.get(attribute)
        Path<String> path = resolvePath(root, attribute);
//        cb.or(cb.lessThan(null, 5), cb.lessThan(null, 5));
        switch (operator) {
            case LT:
            case NOT_GTE:
                return cb.lessThan(path, value.toString());
            case LTE:
            case NOT_GT:
                return cb.lessThanOrEqualTo(path, value.toString());
            case GT:
            case NOT_LTE:
                return cb.greaterThan(path, value.toString());
            case GTE:
            case NOT_LT:
                return cb.greaterThanOrEqualTo(path, value.toString());
            case EQ:
                return cb.equal(path, value);
            case LIKE: // why Operator doesn't accept the regExp LIKE ??
                return cb.like(cb.lower(path), "%" + value.toString().toLowerCase() + "%");
            default:
                throw new UnsupportedOperationException("Unsupported operator: " + operator);
                // TODO complete with operators you need.
        }
    }

    /**
     * Parses an expression to Specification
     *
     * @param exp the expression to parse
     * @param <T> type of specification
     * @return the specification that matches to the expression
     */
    public static <T> Specification<T> parse(String exp) {
        // go through Operator enums

        String[] parsed = exp.split(",");
        var test = parsed[0];

        ArrayList<Specification<T>> specs = new ArrayList<>();

        for (String current : parsed) {
            specs.add(Arrays.stream(Operator.values())
                    .map(o -> {
                        var matcher = o.getPattern().matcher(current);

                        if (matcher.matches()) {
                            String attributeName = matcher.group(1);
                            String value = matcher.group(2);
                            return new SearchSpecification<T>(attributeName, o, value);
                        }

                        return null;
                    })
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException(String.format("no operator matching expression '%s'", current))));
        }

        Specification<T> result = specs.get(0);
        for(int k = 1; k < specs.size(); k++) {
            result = result.and(specs.get(k));
        }

        return result;

    }
    private Path resolvePath(Root<T> root, String attribute) {
        String[] parts = attribute.split("\\.");

        Path<String> path = root.get(parts[0]);

        for (int k =1; k < parts.length;k++){
            path = path.get(parts[k]);
        }

        return path;
    }

    @Override
    public String toString() {
        return "SearchSpecification{" +
                "attribute='" + attribute + '\'' +
                ", operator=" + operator +
                ", value=" + value +
                '}';
    }
}
