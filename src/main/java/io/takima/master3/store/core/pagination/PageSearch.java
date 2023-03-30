package io.takima.master3.store.core.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;
public class PageSearch<T> implements Pageable{
    private int limit =1;
    private int offset=0;
    @JsonIgnore
    private final Specification<T> search;
    private Sort sort = Sort.unsorted();

    public PageSearch(int limit, int offset, Specification<T>  search, Sort sort) {
        super();
        this.limit = limit;
        this.offset = offset;
        this.search = search;
        this.sort = sort;
    }


    public PageSearch(Specification<T> search) {

        this.search = search;
    }


    public Specification<T>  getSearch() {
        return search;
    }
    public int getLimit() {
        return limit;
    }

    @Override
    public int getPageNumber() {
        return (int) Math.ceil(offset/limit);
    }

    @Override
    public int getPageSize() {
        return limit;
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
        private int limit = 1;
        private int offset=0;
        private Specification<T>  search = Specification.where(null);
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
        public PageSearch.Builder search(Specification<T>  search) {
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

    @Override
    public String toString() {
        return "PageSearch{" +
                "limit=" + limit +
                ", offset=" + offset +
                ", search=" + search +
                ", sort=" + sort +
                '}';
    }
}
