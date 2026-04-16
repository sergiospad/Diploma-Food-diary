package org.kane.domain.service.recipe;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kane.database.entity.Recipe;
import org.kane.database.enum_types.CaloricityType;
import org.kane.database.repository.recipe_recource.image_model.ImageModelRepository;
import org.kane.database.repository.recipe.RecipeRepository;
import org.kane.database.repository.recipe_recource.tag.TagRepository;
import org.kane.database.repository.user.UserRepository;
import org.kane.domain.DTO.entityDTO.recipe.*;
import org.kane.domain.DTO.request.EnergyValueRequest;
import org.kane.domain.DTO.request.RecipePreviewRequest;
import org.kane.domain.mappers.recipe.CreateRecipeMapper;
import org.kane.domain.mappers.recipe.PreShowToShowMapper;
import org.kane.domain.mappers.recipe.RecipeEditMapper;
import org.kane.domain.service.diary.recipe_recource.cooking_stage.CookingStageService;
import org.kane.domain.service.energy_value.EnergyValueService;
import org.kane.domain.service.diary.recipe_recource.ingredient.IngredientService;
import org.kane.exceptions.not_found.RecipeNotFoundException;
import org.kane.exceptions.not_found.TagNotFoundException;
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
    private final CreateRecipeMapper createRecipeMapper;
    private final ImageModelRepository imageModelRepository;
    private final TagRepository tagRepository;
    private final IngredientService ingredientService;
    private final CookingStageService cookingStageService;
    private final RecipeEditMapper recipeEditMapper;
    private final PreShowToShowMapper preShowToShowMapper;
    private final EnergyValueService energyValueService;

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

    @Override
    public List<RecipeSummarySearchDTO> searchBySummary(String searchItem) {
        return recipeRepository.findSummaryDTOByItem(searchItem);
    }

    @Override
    public List<RecipeTitleSearchDTO> searchByTitle(String searchItem) {
        return recipeRepository.findTitleDTOByItem(searchItem);
    }

    @Override
    @Transactional
    public RecipeShowDTO createRecipe(RecipeCreateDTO recipeCreateDTO) {
        Recipe recipe = createRecipeMapper.map(recipeCreateDTO);
        var tags = recipeCreateDTO.getTags().stream()
                .map(tag->  tagRepository.findById(tag)
                        .orElseThrow(()-> new TagNotFoundException("Tag not found with id " + tag)))
                .toList();
        var ingredients = recipeCreateDTO.getIngredients().stream()
                .map(ingredientService::createIngredient)
                .toList();
        var stages = recipeCreateDTO.getStages().stream()
                .map(cookingStageService::createCookingStage)
                .toList();
        recipe.setIllustration(imageModelRepository.findById(recipeCreateDTO.getIllustrationID()).orElse(null));
        recipe.setTags(tags);
        recipe.setIngredients(ingredients);
        recipe.setCookingStages(stages);
        recipe = recipeRepository.save(recipe);
        return fromPreShowToShow(recipeRepository.getRecipePreShowProjByID(recipe.getId()));
    }

    @Override
    @Transactional
    public RecipeShowDTO updateRecipe(RecipeEditDTO recipeEditDTO) {
        var recipe = recipeRepository.findById(recipeEditDTO.getId())
                .map(r-> recipeEditMapper.copyMap(recipeEditDTO, r))
                .orElseThrow(()->new RecipeNotFoundException("recipe not found"));

        var tags = recipe.getTags();
        if( recipeEditDTO.getAddTags()!=null)
            recipeEditDTO.getAddTags()
                    .forEach(t-> tagRepository.findById(t)
                            .ifPresent(tags::add));
        if(recipeEditDTO.getRemoveTags()!=null)
            recipeEditDTO.getRemoveTags()
                    .forEach(t-> tagRepository.findById(t)
                            .ifPresent(tags::remove));
        recipe.setTags(tags);

        if(recipeEditDTO.getEditedIngredients()!=null)
            recipeEditDTO.getEditedIngredients()
                    .forEach(ingredientService::updateIngredient);
        if(recipeEditDTO.getEditedStages()!=null)
            recipeEditDTO.getEditedStages()
                    .forEach(cookingStageService::editCookingStage);
        recipe = recipeRepository.save(recipe);
        return fromPreShowToShow(recipeRepository.getRecipePreShowProjByID(recipe.getId()));
    }

    private RecipeShowDTO fromPreShowToShow(RecipePreShowProjection projection){
        var energyRequest = new EnergyValueRequest(projection.getId(), CaloricityType.PER_HUNDRED);
        var recipe = preShowToShowMapper.map(projection);
        recipe.setTags(tagRepository.findAllTagsOfRecipe(projection.getId()));
        recipe.setIngredients(ingredientService.getShowIngredients(projection.getId()));
        recipe.setEnergy(energyValueService.calculateEnergyValue(energyRequest));
        recipe.setCookingStages(cookingStageService.getAllShowDTOByRecipeID(projection.getId()));
        return recipe;
    }

    @Override
    public RecipeShowDTO showRecipe(Long recipeID){
        return fromPreShowToShow(recipeRepository.getRecipePreShowProjByID(recipeID));
    }




}
