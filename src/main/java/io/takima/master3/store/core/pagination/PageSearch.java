package io.takima.master3.store.core.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class PageSearch<T> implements Pageable {
    private final long offset;
    private final long limit;
    @JsonIgnore
    private final Specification<T> search;
    private final Sort sorting;
    private final boolean counted;
    private final int prefetchCount;


    private PageSearch(long offset, long limit, Specification<T> search, Sort sorting, boolean counted, int prefetchCount) {
        this.offset = offset;
        this.limit = limit;
        this.search = search;
        this.sorting = sorting;
        this.counted = counted;
        this.prefetchCount = prefetchCount;
    }

    @Override
    public int getPageNumber() {
        return (int) Math.ceil((double) offset / limit);
    }

    @Override
    public int getPageSize() {
        return (int) limit;
    }

    public long getOffset() {
        return offset;
    }

    @Override
    public Sort getSort() {
        return sorting;
    }

    @Override
    public Pageable next() {
        return new Builder<>().offset(offset + limit).build();
    }

    @Override
    public Pageable previousOrFirst() {
        return new Builder<>().offset(Math.max(offset - limit, 0)).build();
    }

    @Override
    public Pageable first() {
        return new Builder<>().offset(0).build();
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return new Builder<>().offset(limit*pageNumber).limit(limit).build();
    }

    @Override
    public boolean hasPrevious() {
        return offset < limit;
    }

    public long getLimit() {
        return limit;
    }

    public Specification<T> getSearch() {
        return search;
    }

    public boolean isCounted() {
        return this.counted;
    }

    public int getPrefetchCount() {
        return this.prefetchCount;
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    public static final class Builder<T> {
        private long offset = 0;
        private long limit = Integer.MAX_VALUE;
        private Specification<T> search = Specification.where(null);
        private Sort sorting = Sort.unsorted();
        private boolean counted;
        private int prefetchCount;

        private Builder() {

        }

        public Builder<T> offset(long offset) {
            this.offset = offset;
            return this;
        }

        public Builder<T> limit(long limit) {
            this.limit = limit;
            return this;
        }

        public Builder<T> search(Specification<T> search) {
            this.search = search;
            return this;
        }

        public Builder<T> sort(Sort sorting) {
            this.sorting = sorting;
            return this;
        }

        public Builder<T> counted(boolean counted) {
            this.counted = counted;
            return this;
        }

        public Builder<T> prefetchCount(int prefetchCount) {
            this.prefetchCount = prefetchCount;
            return this;
        }

        public PageSearch<T> build() {
            return new PageSearch<T>(offset, limit, search, sorting, counted, prefetchCount);
        }
    }
}
