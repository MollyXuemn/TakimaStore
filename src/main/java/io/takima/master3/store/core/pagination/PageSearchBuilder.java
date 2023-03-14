package io.takima.master3.store.core.pagination;

public class PageSearchBuilder<T> {
    private int limit;
    private int offset;
    private String search;
    private Sort sort;

    public PageSearchBuilder setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public PageSearchBuilder setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public PageSearchBuilder setSearch(String search) {
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