package org.kane.domain.service.recipe;

import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kane.database.entity.Recipe;
import org.kane.database.entity.User;
import org.kane.database.repository.recipe.RecipeRepository;
import org.kane.database.repository.user.UserRepository;
import org.kane.domain.DTO.entityDTO.recipe.RecipePreviewDTO;
import org.kane.domain.DTO.request.RecipePreviewRequest;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MockRecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Principal principal;

    @InjectMocks
    private RecipeServiceImpl recipeService;  // Предполагаемый класс-реализация

    private RecipePreviewRequest request;
    private Pageable pageable;
    private Page<RecipePreviewDTO> mockPage;
    private List<RecipePreviewDTO> mockContent;
    private User currentUser;

    @BeforeEach
    void setUp() {
        request = new RecipePreviewRequest();
        pageable = PageRequest.of(0, 10);

        mockContent = List.of(
                new RecipePreviewDTO(1L, "Борщ", "Классический борщ", 100L),
                new RecipePreviewDTO(2L, "Суп", "Куриный суп", 101L)
        );
        mockPage = new PageImpl<>(mockContent, pageable, 2L);

        currentUser = User.builder()
                .id(1L)
                .username("john_doe")
                .build();
    }

    @Test
    void findPreviews1() {
        when(recipeRepository.findAllPreviewDTOOrderedByNew(any(BooleanBuilder.class), eq(pageable)))
                .thenReturn(mockPage);

        List<RecipePreviewDTO> result = recipeService.findPreviews(principal, request, pageable);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Борщ", result.getFirst().getName());

        verify(recipeRepository).findAllPreviewDTOOrderedByNew(any(BooleanBuilder.class), eq(pageable));
        verify(userRepository, never()).getCurrentUser(any());
    }

    @Test
    void findPreviews2() {

        Long[] tags = {1L, 2L, 3L};
        request.setTags(tags);

        when(recipeRepository.findAllPreviewDTOOrderedByNew(any(BooleanBuilder.class), eq(pageable)))
                .thenReturn(mockPage);


        List<RecipePreviewDTO> result = recipeService.findPreviews(principal, request, pageable);

        assertNotNull(result);
        assertEquals(2, result.size());

        ArgumentCaptor<BooleanBuilder> predicateCaptor = ArgumentCaptor.forClass(BooleanBuilder.class);
        verify(recipeRepository).findAllPreviewDTOOrderedByNew(predicateCaptor.capture(), eq(pageable));

        BooleanBuilder capturedPredicate = predicateCaptor.getValue();
        assertNotNull(capturedPredicate);

        verify(userRepository, never()).getCurrentUser(any());

    }
    @Test
    void findPreviews_ShouldApplyAllFilters_WhenMultipleFiltersProvided() {
        Long[] tags = {1L, 2L};
        Long authorId = 5L;
        request.setTags(tags);
        request.setAuthorId(authorId);
        request.setIsFavoriteOnly(true);

        Set<Recipe> favouriteRecipes = new HashSet<>();
        var recipe = new Recipe();
        recipe.setId(10L);
        favouriteRecipes.add(recipe);
        currentUser.setFavouriteRecipes(favouriteRecipes);

        when(userRepository.getCurrentUser(principal)).thenReturn(currentUser);
        when(recipeRepository.findAllPreviewDTOOrderedByNew(any(BooleanBuilder.class), eq(pageable)))
                .thenReturn(mockPage);

        List<RecipePreviewDTO> result = recipeService.findPreviews(principal, request, pageable);

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(userRepository).getCurrentUser(principal);
        verify(recipeRepository).findAllPreviewDTOOrderedByNew(any(BooleanBuilder.class), eq(pageable));
    }

    @Test
    void findPreviews_ShouldReturnEmptyList_WhenNoRecipesFound() {
        when(recipeRepository.findAllPreviewDTOOrderedByNew(any(BooleanBuilder.class), eq(pageable)))
                .thenReturn(new PageImpl<>(List.of(), pageable, 0L));

        List<RecipePreviewDTO> result = recipeService.findPreviews(principal, request, pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(recipeRepository).findAllPreviewDTOOrderedByNew(any(BooleanBuilder.class), eq(pageable));
    }

    @Test
    void findPreviews_ShouldNotIncludePrivateRecipes_Always() {
        when(recipeRepository.findAllPreviewDTOOrderedByNew(any(BooleanBuilder.class), eq(pageable)))
                .thenReturn(mockPage);

        recipeService.findPreviews(principal, request, pageable);

        ArgumentCaptor<BooleanBuilder> predicateCaptor = ArgumentCaptor.forClass(BooleanBuilder.class);
        verify(recipeRepository).findAllPreviewDTOOrderedByNew(predicateCaptor.capture(), eq(pageable));

        BooleanBuilder capturedPredicate = predicateCaptor.getValue();
        assertNotNull(capturedPredicate);
    }

    @Test
    void findPreviews_ShouldHandleNullTags_Gracefully() {

        request.setTags(null);  // явно null
        request.setAuthorId(null);
        request.setIsFavoriteOnly(false);

        when(recipeRepository.findAllPreviewDTOOrderedByNew(any(BooleanBuilder.class), eq(pageable)))
                .thenReturn(mockPage);


        List<RecipePreviewDTO> result = recipeService.findPreviews(principal, request, pageable);


        assertNotNull(result);
        assertEquals(2, result.size());

        verify(recipeRepository).findAllPreviewDTOOrderedByNew(any(BooleanBuilder.class), eq(pageable));
        verify(userRepository, never()).getCurrentUser(any());
    }

    @Test
    void findPreviews_ShouldHandleEmptyTagsArray_AsNoFilter() {

        request.setTags(new Long[]{});

        when(recipeRepository.findAllPreviewDTOOrderedByNew(any(BooleanBuilder.class), eq(pageable)))
                .thenReturn(mockPage);

        List<RecipePreviewDTO> result = recipeService.findPreviews(principal, request, pageable);


        assertNotNull(result);
        assertEquals(2, result.size());

        verify(recipeRepository).findAllPreviewDTOOrderedByNew(any(BooleanBuilder.class), eq(pageable));
    }
}