package org.kane.domain.service.recipe;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.database.repository.recipe.RecipeRepository;
import org.kane.database.repository.user.UserRepository;
import org.kane.domain.DTO.entityDTO.recipe.RecipePreviewDTO;
import org.kane.domain.DTO.request.RecipePreviewRequest;
import org.kane.domain.mappers.RecipePreviewMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

import static org.kane.database.entity.QRecipe.recipe;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final RecipePreviewMapper recipePreviewMapper;
    @Override
    public List<RecipePreviewDTO> findPreviews(Principal principal, RecipePreviewRequest request, Pageable pageable) {
        BooleanBuilder predicate = new BooleanBuilder();
        predicate.and(recipe.isPrivate).not();
        if(request.getTags() != null)
            predicate.and(recipe.tags.any().id.in(request.getTags()));
        if(request.getAuthorId() != null)
            predicate.and(recipe.author.id.eq(request.getAuthorId()));
        if(request.isFavoriteOnly()){
            var user = userRepository.getCurrentUser(principal);
            predicate.and(recipe.in(user.getFavouriteRecipes()));
        }
        return recipeRepository.findAllPreviewDTO(predicate, pageable).getContent();
    }

}
