package io.takima.master3.store.core.pagination;

import org.springframework.data.domain.Sort;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface PageSearchDefault {
    int limit() default Integer.MAX_VALUE;

    int offset() default 0;

    String[] sort() default {};

    Sort.Direction direction() default Sort.Direction.ASC;
}
