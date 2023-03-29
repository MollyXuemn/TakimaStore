package io.takima.master3.store.core.pagination;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.SortArgumentResolver;
import org.springframework.data.web.SortHandlerMethodArgumentResolver;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Method;
import java.util.Optional;

public class PageSearchHandlerMethodArgumentResolver extends HateoasPageableHandlerMethodArgumentResolver {

    private static final SortHandlerMethodArgumentResolver DEFAULT_SORT_RESOLVER = new SortHandlerMethodArgumentResolver();
    private static final PageSearch<?> DEFAULT_PAGE_SEARCH = new PageSearch.Builder<>().limit(20).offset(0).build();
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

    public void setFallbackPageSearch(PageSearch<?> fallbackPageSearch) {
        Assert.notNull(fallbackPageSearch, "Fallback PageSearch must not be null!");
        this.fallbackPageSearch = fallbackPageSearch;
    }

    @Override
    public String getPageParameterName() {
        return "offset";
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

    /**
     * Enhance the given {@link UriComponentsBuilder} with the given value.
     *
     * @param builder will never be {@literal null}.
     * @param parameter will never be {@literal null}.
     * @param value can be {@literal null}.
     */
    public void enhance(UriComponentsBuilder builder, MethodParameter parameter, Object value) {
        builder.replaceQueryParam(getPageParameterName(), new PageSearch<>().getOffset());
    }

    /**
     * Returns whether the given {@link Pageable} is the fallback one.
     *
     * @param pageable can be {@literal null}.
     * @since 1.9
     * @return
     */
    public boolean isFallbackPageable(Pageable pageable) {
        return fallbackPageSearch != null && fallbackPageSearch.equals(pageable);
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
        String searchString = webRequest.getParameter("search");

        int limit = parse(limitString).map(Long::intValue).orElseGet(defaultOrFallback::getLimit);
        long offset = parse(offsetString).orElseGet(defaultOrFallback::getOffset);

        Sort sort = sortResolver.resolveArgument(methodParameter, mavContainer, webRequest, binderFactory);

        return new PageSearch<>.Builder<>()
                .sort(sort)
                .offset(Math.toIntExact(offset))
                .limit(limit)
                .search(SearchSpecification.parse(searchString))
                .build();
    }

    private PageSearch getDefaultFromAnnotationOrFallback(MethodParameter methodParameter) {
        PageSearchDefault defaults = methodParameter.getParameterAnnotation(PageSearchDefault.class);
        return defaults != null ? getDefaultPageRequestFrom(methodParameter, defaults) : fallbackPageSearch;
    }

    private static PageSearch getDefaultPageRequestFrom(MethodParameter parameter, PageSearchDefault defaults) {

        Integer defaultPageOffset = defaults.offset();
        Integer defaultPageLimit = defaults.limit();

        PageSearch<Object> ps = new PageSearch.Builder<>()
                .offset(defaultPageOffset)
                .limit(defaultPageLimit)
                .sort(Sort.unsorted())
                .build();

        if (defaultPageOffset < 0) {
            Method annotatedMethod = parameter.getMethod();
            throw new IllegalStateException(String.format("page offset must be greater than or equal to 0", annotatedMethod));
        }

        return ps;
    }

    private Optional<Long> parse(@Nullable String parameter) {

        if (!StringUtils.hasText(parameter)) {
            return Optional.empty();
        }

        try {
            long parsed = Long.parseLong(parameter);
            return Optional.of(parsed);
        } catch (NumberFormatException e) {
            return Optional.of(0L);
        }
    }
}
