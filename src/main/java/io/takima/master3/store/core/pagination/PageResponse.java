package io.takima.master3.store.core.pagination;

import io.takima.master3.store.article.models.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Objects;

public class PageResponse<T> extends PageImpl<T> implements Page<T>  {
    int totalElements;
    int totalPages;
    List<T> content;
    public PageResponse(PageSearch<T> pageSearch, List<T> content, int totalElements) {
        super((List<T>) pageSearch);
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / (double) pageSearch.getLimit());
    }

    public PageResponse(List<T> content) {
        super( content);
        this.content =content;
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

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public static final class Builder<T> {
        int totalElements;
        int totalPages;
        public List<T> content;

        public PageResponse.Builder totalElements(int totalElements) {
            this.totalElements = totalElements;
            return this;
        }

        public PageResponse.Builder totalPages(int totalPages) {
            this.totalPages = totalPages;
            return this;
        }
        public Builder(List<T> content) {
            this.content = content;
        }


        public PageResponse<T> build() {
            PageResponse<T> pageSearch= new PageResponse<T>(content);
            pageSearch.totalElements = this.totalElements;
            pageSearch.totalPages = this.totalPages;

            return pageSearch;

        }
    }
    @Override
    public String toString() {
        return "PageResponse{" +
                "totalElements=" + totalElements +
                ", totalPages=" + totalPages +
                ", content=" + content +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PageResponse<?> that = (PageResponse<?>) o;

        return totalElements == that.totalElements && totalPages == that.totalPages && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + totalElements;
        result = 31 * result + totalPages;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}