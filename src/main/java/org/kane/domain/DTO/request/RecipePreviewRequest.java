package org.kane.domain.DTO.request;

import lombok.Builder;
import lombok.Data;
import org.kane.domain.DTO.enum_types.SortTypeRecipe;

@Data
@Builder
public class RecipePreviewRequest {
    SortTypeRecipe sortType;
    boolean favoriteOnly;
    Long[] tags;
    Short page;
    Long authorId;
}
