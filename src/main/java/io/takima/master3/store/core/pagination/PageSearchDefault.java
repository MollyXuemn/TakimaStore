package io.takima.master3.store.core.pagination;
import org.springframework.data.domain.Sort;

import java.lang.annotation.*;

/**
 * Annotation to set defaults when injecting a {@link PageSearch} into a controller
 * method.
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface PageSearchDefault {

    /**
     * The default-size the injected {@link PageSearch} should get if no corresponding
     * parameter defined in request (default is 10).
     */
    int limit() default 10;

    /**
     * The default-offset the injected {@link PageSearch} should get if no corresponding
     * parameter defined in request (default is 0).
     */
    int offset() default 0;

    /**
     * The properties to sort by by default. If unset, no sorting will be applied at all.
     *
     * @return
     */
    String[] sort() default {};

    /**
     * The direction to sort by. Defaults to {@link Sort.Direction#ASC}.
     *
     * @return
     */
    Sort.Direction direction() default Sort.Direction.ASC;
}
