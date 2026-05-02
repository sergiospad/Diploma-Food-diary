package org.kane.domain.DTO.entityDTO.recipe_recource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavouriteRecipeDTO {
    @Builder.Default
    private List<Long> recipes = new ArrayList<>();
}
