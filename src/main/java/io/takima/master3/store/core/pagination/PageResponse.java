package io.takima.master3.store.core.pagination;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

public class PageResponse<T> extends PageImpl<T> implements Page<T> {
    private long totalElements;
    private long totalPages;
    private List<T> content;

    public PageResponse(PageSearch<T> pageSearch, long totalElements, List<T> content) {
        super(content);
        this.totalElements = totalElements;
        this.totalPages = (long) Math.ceil((double) totalElements/ (double) pageSearch.getLimit());
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return (int) totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public @NotNull List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public static final class Builder<T> {
        private PageSearch<T> pageSearch;
        private long totalElements;
        private List<T> content;

        public Builder<T> pageSearch(PageSearch<T> pageSearch) {
            this.pageSearch = pageSearch;
            return this;
        }

        public Builder<T> totalElements(long totalElements) {
            this.totalElements = totalElements;
            return this;
        }

        public Builder<T> content(List<T> content) {
            this.content = content;
            return this;
        }

        public PageResponse<T> build() {
            return new PageResponse<T>(pageSearch, totalElements, content);
        }

    }
}
