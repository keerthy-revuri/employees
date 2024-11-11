package com.employee.department_service.entity;

import java.io.Serializable;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Provides a page request based on offset index.
 *
 * @author pravin.chaudhary
 */
public final class OffsetBasedPageRequest implements Pageable, Serializable {

    private static final long serialVersionUID = -25822477129613575L;

    private final int limit;
    private final long offset;
    private final Sort sort;

    /**
     * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
     *
     * @param offset zero-based offset.
     * @param limit the size of the elements to be returned.
     * @param sort can be {@literal null}.
     */
    public OffsetBasedPageRequest(long offset, int limit, Sort sort) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }

        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }
        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }

    /**
     * Creates a new {@link OffsetBasedPageRequest} with sort parameters applied.
     *
     * @param pageFilter {@link PageFilter} object
     */
    public OffsetBasedPageRequest(PageFilter pageFilter) {
        this(
                pageFilter.getOffset(),
                pageFilter.getCount(),
                Sort.by(
                        pageFilter.isDescending() ? Sort.Direction.DESC : Sort.Direction.ASC,
                        pageFilter.getSortBy()));
    }

    @Override
    public int getPageNumber() {
        return (int) offset / limit;
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public @NonNull Sort getSort() {
        return sort;
    }

    @Override
    public @NonNull Pageable next() {
        return new OffsetBasedPageRequest(getOffset() + getPageSize(), getPageSize(), getSort());
    }

    private OffsetBasedPageRequest previous() {
        return new OffsetBasedPageRequest(getOffset() - getPageSize(), getPageSize(), getSort());
    }

    @Override
    public @NonNull Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public @NonNull Pageable first() {
        return new OffsetBasedPageRequest(0, getPageSize(), getSort());
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return null;
    }

    @Override
    public boolean hasPrevious() {
        return offset >= limit;
    }


}
