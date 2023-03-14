package io.takima.master3.store.core.pagination;


public class PageSearch<T> {
    private int limit;
    private int offset;
    private String search ;
    private Sort sort;

    public PageSearch(int limit, int offset, String search, Sort sort) {
        this.limit = limit;
        this.offset = offset;
        this.search = search;
        this.sort = sort;
    }

    public PageSearch(PageSearch pageSearch) {
        this(
            pageSearch.limit,
            pageSearch.offset,
            pageSearch.search,
            pageSearch.sort
        );
    }

    public PageSearch() {

    }

    public String getSearch() {
        return search;
    }
    public int getLimit() {
        return limit;
    }
    public int getOffset() {
        return offset;
    }

    public Sort getSort() {
        return sort;
    }


    public static final class Builder<T> {
        private int limit;
        private int offset;
        private String search ;
        private Sort sort;

        public Builder() {
        }

        public PageSearch.Builder limit(int limit) {
            this.limit = limit;
            return this;
        }
        public PageSearch.Builder offset(int offset) {
            this.offset = offset;
            return this;
        }
        public PageSearch.Builder search(String search) {
            this.search = search;
            return this;
        }public PageSearch.Builder sort(Sort sort) {
            this.sort = sort;
            return this;
        }
        public PageSearch build() {
            return new PageSearch<T>(this.limit, this.offset, this.search, this.sort);
        }
    }



}
