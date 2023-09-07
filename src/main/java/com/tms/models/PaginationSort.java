package com.tms.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
@Getter
@RequiredArgsConstructor
public enum PaginationSort {
    ID_ASC(Sort.by(Sort.Direction.ASC, "id")),
    ID_DESC(Sort.by(Sort.Direction.DESC, "id")),
    CATEGORIES_ASC(Sort.by(Sort.Direction.ASC, "categories")),
    GENRES_ASC(Sort.by(Sort.Direction.ASC, "genres"));

    private final Sort sortValue;
}
