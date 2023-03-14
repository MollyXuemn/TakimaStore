package io.takima.master3.store.core.pagination;

import java.util.List;

public class PageResponse<T> extends PageSearch {
    int totalElements;
    int totalPages;
    List<T> content;


    public PageResponse(PageSearch pageSearch, List<T> content, int totalElements) {
        super(pageSearch);
        this.content = content;
        this.totalElements = totalElements;
        this.totalPages = (int)Math.ceil((double)totalElements / (double)pageSearch.getLimit());
    }


    public PageResponse(int limit, int offset, String search, Sort sort, List<T> content,
                        int totalElements) {
        super(limit, offset, search, sort);
        this.content= content;
        this.totalElements = totalElements;
        this.totalPages = (int)Math.ceil((double)totalElements / (double)limit);

    }
}
