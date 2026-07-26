package org.SprintForge.common.util;

import org.SprintForge.common.constants.AppConstants;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class PaginationUtil {

    private PaginationUtil() {
        // Utility class
    }

    public static Pageable createPageable(int page, int size, String sortBy, String sortDir) {
        int sanitizedPage = Math.max(0, page);
        int sanitizedSize = (size <= 0) ? AppConstants.DEFAULT_PAGE_SIZE : Math.min(size, AppConstants.MAX_PAGE_SIZE);

        String sanitizedSortBy = (sortBy == null || sortBy.isBlank()) ? AppConstants.DEFAULT_SORT_BY : sortBy;
        Sort.Direction direction = AppConstants.DEFAULT_SORT_DIRECTION_DESC.equalsIgnoreCase(sortDir) ? Sort.Direction.DESC : Sort.Direction.ASC;

        return PageRequest.of(sanitizedPage, sanitizedSize, Sort.by(direction, sanitizedSortBy));
    }
}
