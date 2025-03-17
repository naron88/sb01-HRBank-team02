package com.practice.hrbank.dto;

import java.util.List;

public class CursorPageResponseChangeLogDto {
    List<Object> content;
    String nextCursor;
    String nextIdAfter;
    Integer size;
    Integer totalElements;
    boolean hasNext;
}
