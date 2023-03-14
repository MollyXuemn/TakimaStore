package io.takima.master3.store.core.pagination;

import io.takima.master3.store.article.models.Article;

import java.util.List;

public class PageResponse<T> extends PageSearch {
    int totalElements;
    int totalPages;
    List<T> content;


    public PageResponse(PageSearch pageSearch, List<T> content, int totalElements) {
        super(pageSearch);
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / (double) pageSearch.getLimit());
    }


    public PageResponse(int limit, int offset, String search, Sort sort, List<T> content,
                        int totalElements) {
        super(limit, offset, search, sort);
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / (double) limit);

    }

    public PageResponse(PageSearch pageSearch, int totalElements, int totalPages, List<T> content) {
    }

    public PageResponse(List<Article> content) {
    }


    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public static final class Builder<T> {
        PageSearch pageSearch;
        int totalElements;
        int totalPages;
        public List<T> content;

        public Builder() {
        }

        public PageResponse.Builder pageSearch(PageSearch pageSearch) {
            this.pageSearch = pageSearch;
            return this;
        }

        public PageResponse.Builder totalElements(int totalElements) {
            this.totalElements = totalElements;
            return this;
        }

        public PageResponse.Builder totalPages(int totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public PageResponse.Builder content(List<T> content) {
            this.content = content;
            return this;
        }

        public PageResponse build() {
            return new PageResponse<T>(
                    this.pageSearch,
                    this.totalElements,
                    this.totalPages,
                    this.content);
        }
    }
}