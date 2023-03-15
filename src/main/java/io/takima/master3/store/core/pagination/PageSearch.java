package io.takima.master3.store.core.pagination;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Objects;

public class PageSearch<T> implements Pageable{
    private int limit;
    private int offset;
    private String search ;
    private Sort sort = Sort.unsorted();

    public PageSearch(int limit, int offset, String search, Sort sort) {
        super();
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

    @Override
    public int getPageNumber() {
        return 0;
    }

    @Override
    public int getPageSize() {
        return 0;
    }

    public long getOffset() {
        return offset;
    }
    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Pageable next() {
        return null;
    }

    @Override
    public Pageable previousOrFirst() {
        return null;
    }

    @Override
    public Pageable first() {
        return null;
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return false;
    }


    public static final class Builder<T> {
        private int limit;
        private int offset;
        private String search ;
        private Sort sort=Sort.unsorted();

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageSearch<?> that = (PageSearch<?>) o;

        return offset == that.offset && limit == that.limit && Objects.equals(search, that.search);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offset, limit, search);
    }
}
