package com.moais.todo.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CommonResponse<T> {

    private static final int SUCCESS_CODE = 0;

    private int code;
    private T body;
    private Pagination paginationData;

    // constructor //
    private CommonResponse(T body) {
        this.code = SUCCESS_CODE;
        this.body = body;
    }

    private CommonResponse(T body, Pagination pagination) {
        this(body);
        this.paginationData = pagination;
    }

    // static factory method //
    public static <T> CommonResponse<T> emptyBody() {
        return new CommonResponse<>();
    }

    public static <T> CommonResponse<T> withBody(T body) {
        return new CommonResponse<>(body);
    }

    public static <T> CommonResponse<T> withPaging(T body, Page<?> page) {
        return new CommonResponse<>(body, new Pagination(page));
    }

    // pagination class //
    @Getter
    private static class Pagination {
        private final long totalCount;
        private final int totalPage;
        private final boolean hasPrevious;
        private final boolean hasNext;
        private final int pageNumber;

        private Pagination(Page<?> page) {
            this.totalCount = page.getTotalElements();
            this.totalPage = page.getTotalPages();
            this.hasPrevious = page.hasPrevious();
            this.hasNext = page.hasNext();
            this.pageNumber = page.getNumber();
        }
    }
}