package com.ventionteams.applicationexchange.dto.read;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MainPageCategoryReadDto(
        @JsonProperty("category_id")
        Integer id,
        String name,
        @JsonProperty("parent_id")
        Integer parentId) {
}
