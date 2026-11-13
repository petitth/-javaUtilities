package edu.collection;

import java.util.Collections;
import java.util.List;

public class CollectionUtil {

    private CollectionUtil() { }

    /**
     * Returns a sublist of a source list, prepared for pagination.
     * Custom error can be given instead of returning emptyList.
     * @param sourceList source list to get data from
     * @param page page number (should start from 1)
     * @param pageSize how many records per page
     * @return a sublist of sourceList
     */
    public static <T> List<T> getPage(List<T> sourceList, int page, int pageSize) {
        if(page <= 0) {
            throw new IllegalArgumentException("invalid page: " + page);
        }
        if(pageSize <= 0) {
            throw new IllegalArgumentException("invalid page size: " + pageSize);
        }

        int fromIndex = (page - 1) * pageSize;
        if(sourceList == null || sourceList.size() <= fromIndex){
            return Collections.emptyList();
        }

        return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
    }
}
