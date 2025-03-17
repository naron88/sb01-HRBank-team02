package com.practice.hrbank.dto.changeLogs;

import java.util.List;

public class CursorPageResponseChangeLogDto {
    List<ItemsDto> content;
    String nextCursor;
    String nextIdAfter;
    Integer size;
    Integer totalElements;
    boolean hasNext;
}
