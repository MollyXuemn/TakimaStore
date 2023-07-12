package io.takima.master3.store.core.pagination;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Function;

public class PageSearchHandlerMethodArgumentResolver implements PageableArgumentResolver {

    private static final SortHandlerMethodArgumentResolver DEFAULT_SORT_RESOLVER = new SortHandlerMethodArgumentResolver();
    private static final PageSearch<?> DEFAULT_PAGE_SEARCH = PageSearch.builder().limit(20).offset(0).build();
    private PageSearch<?> fallbackPageSearch = DEFAULT_PAGE_SEARCH;

    private final SortHandlerMethodArgumentResolver sortResolver;

    /**
     * Constructs an instance of this resolved with a default {@link SortHandlerMethodArgumentResolver} & {@link PageableHandlerMethodArgumentResolver}.
     */
    public PageSearchHandlerMethodArgumentResolver() {
        this(null);
    }

    /**
     * Constructs an instance of this resolver with the specified {@link SortArgumentResolver}.
     *
     * @param sortResolver the sort resolver to use
     * @since 1.13
     */
    public PageSearchHandlerMethodArgumentResolver(@Nullable SortHandlerMethodArgumentResolver sortResolver) {
        this.sortResolver = sortResolver == null ? DEFAULT_SORT_RESOLVER : sortResolver;
    }

    /**
     * Returns whether the {@link UriComponentsBuilder} supports the given {@link MethodParameter}.
     *
     * @param parameter will never be {@literal null}.
     * @return
     */
    public boolean supportsParameter(MethodParameter parameter) {
        return PageSearch.class.isAssignableFrom(parameter.getParameterType());
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.web.method.support.HandlerMethodArgumentResolver#resolveArgument(org.springframework.core.MethodParameter, org.springframework.web.method.support.ModelAndViewContainer, org.springframework.web.context.request.NativeWebRequest, org.springframework.web.bind.support.WebDataBinderFactory)
     */
    @Override
    public PageSearch<?> resolveArgument(MethodParameter methodParameter, @Nullable ModelAndViewContainer mavContainer,
                                         NativeWebRequest webRequest, @Nullable WebDataBinderFactory binderFactory) {


        PageSearch defaultOrFallback = getDefaultFromAnnotationOrFallback(methodParameter);

        String offsetString = webRequest.getParameter("offset");
        String limitString = webRequest.getParameter("limit");
        String countedString = webRequest.getParameter("counted");
        String searchString = webRequest.getParameter("search");
        String prefetchString = webRequest.getParameter("prefetch");

        long offset = parseLong(offsetString).orElseGet(defaultOrFallback::getOffset);
        long limit = parseLong(limitString).orElseGet(defaultOrFallback::getLimit);
        int prefetch = parseInt(prefetchString).orElseGet(defaultOrFallback::getPrefetchCount);
        boolean counted = parseBoolean(countedString).orElseGet(defaultOrFallback::isCounted);

        Sort sort = sortResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

        return PageSearch.builder()
                .sort(sort)
                .offset(offset)
                .limit(limit)
                .counted(counted)
                .prefetchCount(prefetch)
                .search(SearchSpecification.parse(searchString))
                .build();
    }

    private PageSearch getDefaultFromAnnotationOrFallback(MethodParameter methodParameter) {
        PageSearchDefault defaults = methodParameter.getParameterAnnotation(PageSearchDefault.class);
        return defaults != null ? getDefaultPageRequestFrom(methodParameter, defaults) : fallbackPageSearch;
    }

    private static PageSearch getDefaultPageRequestFrom(MethodParameter parameter, PageSearchDefault defaults) {

        int defaultPageOffset = defaults.offset();
        int defaultPageLimit = defaults.limit();

        PageSearch.Builder<Object> builder = PageSearch.builder()
                .offset(defaultPageOffset)
                .limit(defaultPageLimit);

        if (defaultPageOffset < 0) {
            Method annotatedMethod = parameter.getMethod();
            throw new IllegalStateException(String.format("On method %s: page offset must be greater than or equal to 0", annotatedMethod));
        }

        if (defaults.sort().length != 0) {
            builder.sort(Sort.by(defaults.direction(), defaults.sort()));
        }

        return builder.build();
    }

    private <T> Optional<T> parse(@Nullable String parameter, Function<String, T> parser) {
        if (!StringUtils.hasText(parameter)) {
            return Optional.empty();
        }

        return Optional.of(parser.apply(parameter));
    }

    private Optional<Long> parseLong(@Nullable String parameter) {
        return parse(parameter, Long::parseLong);
    }

    private Optional<Integer> parseInt(@Nullable String parameter) {
        return parse(parameter, Integer::parseInt);
    }

    private Optional<Boolean> parseBoolean(@Nullable String parameter) {
        return parse(parameter, Boolean::parseBoolean);
    }
}
