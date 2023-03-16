package io.takima.master3.store.core.pagination;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class PageSearchBuilder<T> {
    private int limit;
    private int offset;
    private Specification search;
    private Sort sort;

    public PageSearchBuilder setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public PageSearchBuilder setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public PageSearchBuilder setSearch(Specification search) {
        this.search = search;
        return this;
    }

    public PageSearchBuilder setSort(Sort sort) {
        this.sort = sort;
        return this;
    }

    public PageSearch createPageSearch() {
        return new PageSearch(limit, offset, search, sort);
    }
}