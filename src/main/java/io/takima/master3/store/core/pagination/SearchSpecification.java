package io.takima.master3.store.core.pagination;

import io.takima.master3.store.core.persistence.MetadataExtractor;
import jakarta.persistence.criteria.*;
import org.hibernate.mapping.PersistentClass;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.mapping.PropertyPath;
import org.springframework.data.mapping.PropertyReferenceException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

        Path<String> path = resolvePath(root, cb, this.attribute);

        Object value = convertValue(this.value, path);

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
            case IN:
                return cb.in(path).value((String) value);
            case LIKE:
                return cb.like(cb.lower(path), "%" + value.toString().toLowerCase() + "%");
            case SW:
                return cb.like(cb.lower(path), value.toString().toLowerCase() + "%");
            case EW:
                return cb.like(cb.lower(path), "%" + value.toString().toLowerCase());
            case NULL:
                return cb.isNull(path);
            case NOT_NULL:
                return cb.isNotNull(path);
            case EQ:
                return cb.equal(path, value);
            case NOT_IN:
                return cb.not(cb.in(path).value((String) value));
            case NOT_LIKE:
                return cb.not(cb.like(cb.lower(path), "%" + value.toString().toLowerCase() + "%"));
            case NOT_SW:
                return cb.not(cb.like(cb.lower(path), value.toString().toLowerCase() + "%"));
            case NOT_EW:
                return cb.not(cb.like(cb.lower(path), "%" + value.toString().toLowerCase()));
            case NOT_EQ:
                return cb.not(cb.equal(path, value));
            default:
                throw new UnsupportedOperationException("Unsupported operator: " + operator);
        }

    }

    private Path resolvePath(Root<T> root, CriteriaBuilder cb, String path) {
        return resolvePath(root, cb, path, root);
    }

    private Path resolvePath(Root<T> root, CriteriaBuilder cb, String path, Path expression) {
        PropertyPath propertyPath;

        try {
            propertyPath = PropertyPath.from(path, expression.getJavaType());
            for (PropertyPath p : propertyPath) {
                if (!p.equals(propertyPath.getLeafProperty())) {
                    expression = root.join(p.getSegment());
                }
            }
            expression = expression.get(propertyPath.getLeafProperty().getSegment());

            return expression;
        } catch (PropertyReferenceException e) {

            var candidateSubclasses = getSubclasses(e.getType().getType());
            for (var c : candidateSubclasses) {
                try {
                    return resolvePath(root, cb, e.getPropertyName(), cb.treat(root.join(e.getBaseProperty().getSegment()), c));
                } catch (Exception ex) {
                    // noop
                }
            }
            throw e;
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
        if (exp == null || "".equals(exp)) {
            return Specification.where(null);
        }
        // if found multiple conditions
        boolean isSplit = exp.contains(",");
        if (isSplit) {
            LinkedList<String> separatedExp = new LinkedList<>(Arrays.asList(exp.split(",")));
            // parse first condition
            AtomicReference<Specification<T>> spec = new AtomicReference<>(SearchSpecification.parse(separatedExp.pop()));

            // apply "and()" on every remaining condition
            separatedExp.forEach(f -> {
                spec.set(spec.get().and(SearchSpecification.parse(f)));
            });

            return spec.get();
        } else {
            return Arrays.stream(Operator.values())
                    .map(o -> {
                        var matcher = o.getPattern().matcher(exp);
                        if (matcher.matches()) {
                            String attributeName = matcher.group(1);
                            if (matcher.groupCount() < 2) {
                                return new SearchSpecification<T>(attributeName, o, "");
                            } else {
                                String value = matcher.group(2);
                                return new SearchSpecification<T>(attributeName, o, value);
                            }
                        }

                        return null;
                    })
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElseThrow(() -> new NoSuchElementException(String.format("no operator matching expression '%s'", exp)));
        }
    }

    private Object convertValue(Object value, Path<?> path) {

        if (value == null) {
            return null;
        }

        String str = value.toString();

        Matcher arrayMatcher = Pattern.compile("^\\{(.*?)\\}$").matcher(str);
        // if is an array
        if (arrayMatcher.matches()) {
            String[] parts = arrayMatcher.group(1).split(";");

            return Arrays.stream(parts).map(s -> convertValue(s, path)).collect(Collectors.toList());
        } else if (path.getJavaType().isEnum()) {
            try {
                Class<?> type = path.getJavaType();
                Method valueOfMethod = (type.getDeclaredMethod("valueOf", String.class));
                return valueOfMethod.invoke(type, str);
            } catch (NoSuchMethodException e) {
                throw new AssertionError(e);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new IllegalArgumentException(e);
            }
        }

        return value;
    }

    private List<Class> getSubclasses(Class<?> type) {
        // attribute not found in type
        String className = type.getName();

        var binding = MetadataExtractor.INSTANCE.getMetadata().getEntityBinding(className);

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize((Iterator<PersistentClass>) (binding.getSubclassClosureIterator()),
                                Spliterator.ORDERED),
                        false)
                .map(PersistentClass::getMappedClass)
                .filter(c -> !c.equals(type))
                .collect(Collectors.toList());
    }
}

