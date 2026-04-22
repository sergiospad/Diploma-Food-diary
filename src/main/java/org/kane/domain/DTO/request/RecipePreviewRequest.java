package org.kane.domain.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kane.database.enum_types.SortTypeRecipe;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipePreviewRequest {
    SortTypeRecipe sortType;
    boolean favoriteOnly;
    Long[] tags;
    Long authorId;
}
