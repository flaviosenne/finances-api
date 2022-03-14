package com.project.finances.app.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PageGenerics<T> {
    private long totalElements;
    private int totalPages;
    private int actualPage;
    private boolean last;
    private List<T> content;
}
